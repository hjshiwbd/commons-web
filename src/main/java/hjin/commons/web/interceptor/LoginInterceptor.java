package hjin.commons.web.interceptor;

import commons.tool.utils.WildcardUtil;
import hjin.commons.web.constants.VisitBrowser;
import hjin.commons.web.session.ISessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISessionManager sessionUtils;
    @Value("${loginurl}")
    private String loginUrl;
    @Value("${wx.loginurl}")
    private String wxLoginUrl;
    private List<String> doNotIntercept;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug(request.getRequestURL().toString());
        }
        if (handler instanceof DefaultServletHttpRequestHandler) {
            return super.preHandle(request, response, handler);
        }

        if (!(handler instanceof HandlerMethod)) {
            // 非常规请求
            logger.debug("unexpected handler:" + handler.toString());
            return super.preHandle(request, response, handler);
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
            return super.preHandle(request, response, handler);
        }

        // 是否登录
        if (!sessionUtils.isUserLogined()) {
            logger.info("unlogin -> {}", interceptPoint);
            // 未登录,解析当前地址
            String currentUrl = getCurrentUrl(request);
            String redirectUrl;
            if (VisitBrowser.wx.name().equals(sessionUtils.getVisitBrowser())) {
                // 微信端
                redirectUrl = request.getContextPath() + wxLoginUrl + "?continueurl=" + currentUrl;
            } else {
                // pc端
                redirectUrl = request.getContextPath() + loginUrl + "?continueurl=" + currentUrl;
            }
            // 重定向到登录并携带当前地址
            logger.info("not login, redirect to:{}", redirectUrl);
            response.sendRedirect(redirectUrl);
            return false;
        } else {
            logger.info("{} -> {}", sessionUtils.getLoginUser().getCode(), interceptPoint);
            // 已登录继续
            return super.preHandle(request, response, handler);
        }

    }

    /**
     * 是否不需要被拦截
     *
     * @param interceptPoint 类名.方法名
     * @return
     */
    private boolean isDoNotBeIntercepted(String interceptPoint) {
        for (String wildcard : doNotIntercept) {
            if (WildcardUtil.isWildMatch(wildcard, interceptPoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得当前请求地址
     *
     * @param request
     * @return
     * @author hjin
     */
    private String getCurrentUrl(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        String queryString = "";
        if (request.getMethod().equals("GET")) {
            queryString = request.getQueryString();
            queryString = queryString == null ? "" : "?" + queryString;
        }
        String url = null;
        try {
            url = URLEncoder.encode(requestUrl + queryString, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return url;
    }

    public List<String> getDoNotIntercept() {
        return doNotIntercept;
    }

    public void setDoNotIntercept(List<String> doNotIntercept) {
        this.doNotIntercept = doNotIntercept;
    }

}
