package hjin.commons.web.interceptor;

import hjin.commons.web.service.IBaseUrlService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UrlValidationInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<String> ignoreExtentions;

    @Autowired
    private IBaseUrlService urlService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        // ctxpath
        if (!"/".equals(request.getContextPath())) {
            uri = uri.replace(request.getContextPath(), "");
        }
        // 部分后缀名访问不做拦截
        if (uri.contains(".")) {
            if (isIgnore(uri)) {
                return super.preHandle(request, response, handler);
            }
            uri = uri.substring(0, uri.lastIndexOf("."));// 去掉类似.html的后缀
        }
        // 去掉url中;jsessionid=xxxx的部分
        if (uri.contains(";jsessionid")) {
            uri = StringUtils.substringBefore(uri, ";jsessionid");
        }
        uri = resolve(uri);
        if (urlService.getActiveUrls(false).get(uri) == null) {
            // 404
            logger.info("@@@@@@@@@unknown_request:{}", uri);
            response.setStatus(404);
            response.sendRedirect(request.getContextPath() + "/404");
            return false;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 形如"/x/y/z/[number]","/x/y/z/[number].html"的url,去掉"/[number]"和".html"保留/x/y/z
     *
     * @param uri
     * @return
     */
    private String resolve(String uri) {
        // /500和/400的请求与number规则冲突,独立处理
        if (uri.startsWith("/500") || uri.startsWith("/404")) {
            return uri;
        }

        uri = uri.replaceAll("/\\d+$", "");
        return uri;
    }

    /**
     * 是否不检查
     *
     * @param uri
     * @return
     */
    private boolean isIgnore(String uri) {
        //后缀名不检查.图片js样式等
        String ext = uri.substring(uri.lastIndexOf(".") + 1);
        for (String reg : ignoreExtentions) {
            if (ext.toLowerCase().matches(reg)) {
                return true;
            }
        }
        return false;
    }

    public void setIgnoreExtentions(List<String> ignoreExtentions) {
        this.ignoreExtentions = ignoreExtentions;
    }
}
