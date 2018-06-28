package hjin.commons.web.interceptor;

import commons.tool.utils.WildcardUtil;
import hjin.commons.web.accesslog.HttpRequestBean;
import hjin.commons.web.accesslog.LogSavingThread;
import hjin.commons.web.scope.RequestScope;
import hjin.commons.web.session.ISessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AccessLogInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<String> notWriteLogCutpoint;

    @Autowired
    private RequestScope requestScope;
    /**
     * 线程池,在spring中托管
     */
    @Autowired
    private ThreadPoolTaskExecutor threadExecutor;
    /**
     * 用于记录日志的线程
     */
    @Autowired
    private LogSavingThread logSavingThread;
    @Autowired
    private ISessionManager session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        requestScope.setSessionUser(session.getLoginUser());// 预存user信息,避免handle过程中user信息发生变化
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            // 非常规请求
            logger.info("unexpected handler:" + handler.toString());
            super.afterCompletion(request, response, handler, ex);
            return;
        }

        HandlerMethod c = (HandlerMethod) handler;
        // 被拦截类名
        String clzName = c.getBeanType().getSimpleName();
        // 被拦截方法名
        String methodName = c.getMethod().getName();
        // 被拦截点
        String interceptPoint = clzName + "." + methodName;

        // 当前地址不需要拦截
        if (isDoNotBeIntercepted(interceptPoint)) {
            super.afterCompletion(request, response, handler, ex);
            return;
        }
        saveAccessLog(request, response, clzName, methodName);
        super.afterCompletion(request, response, handler, ex);
    }

    private void saveAccessLog(HttpServletRequest request, HttpServletResponse response, String clzName,
                               String methodName) {
        HttpRequestBean req = new HttpRequestBean(request, response);
        logSavingThread.setRequest(req);
        logSavingThread.setClzName(clzName);
        logSavingThread.setMethodName(methodName);
        logSavingThread.setUserid(getUserid());
        logSavingThread.setLogContent(requestScope.getLogContent());
        // 开启线程保存日志
        threadExecutor.execute(logSavingThread);
    }

    private String getUserid() {
        return requestScope.getSessionUser() == null ? "" : requestScope.getSessionUser().getId().toString();
    }

    private boolean isDoNotBeIntercepted(String interceptPoint) {
        if (!CollectionUtils.isEmpty(notWriteLogCutpoint)) {
            for (String wildcard : notWriteLogCutpoint) {
                if (WildcardUtil.isWildMatch(wildcard, interceptPoint)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public List<String> getNotWriteLogCutpoint() {
        return notWriteLogCutpoint;
    }

    public void setNotWriteLogCutpoint(List<String> notWriteLogCutpoint) {
        this.notWriteLogCutpoint = notWriteLogCutpoint;
    }
}
