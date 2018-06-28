package hjin.commons.web.interceptor;

import hjin.commons.web.constants.VisitBrowser;
import hjin.commons.web.constants.VisitDevice;
import hjin.commons.web.session.ISessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VisitTerminalInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISessionManager sessionUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug(request.getHeader("user-agent"));
        }

        String device = getVisitDevice(request);// 设备
        sessionUtils.setVisitDevice(device);

        String browser = getVisitBrowser(request);// 浏览器
        sessionUtils.setVisitBrowser(browser);

        if (!device.equals("pc")) {//非pc访问均转到wx移动浏览器方式访问
            sessionUtils.setViewPrefix("/wx/");
        } else {
            sessionUtils.setViewPrefix("");
        }

        if (isIE()) {
            response.sendRedirect("/browse-warning");
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    private boolean isIE() {
        return false;
    }

    /**
     * 访问浏览器,chrome,ie6/7/8/9/10/11/etc,微信浏览器,qq浏览器,火狐浏览器等
     *
     * @param request
     * @return
     */
    private String getVisitBrowser(HttpServletRequest request) {
        String ua = request.getHeader("user-agent").toLowerCase();
        if (ua.contains("micromessenger")) {
            //微信
            return VisitBrowser.wx.name();
        }
        return VisitBrowser.other.name();
    }

    /**
     * 访问设备:pc,安卓,ios,ipad等
     *
     * @param request
     * @return
     */
    private String getVisitDevice(HttpServletRequest request) {
        String ua = request.getHeader("user-agent").toLowerCase();
        if (ua.contains("mobile")) {
            if (ua.contains("iphone")) {
                return VisitDevice.iphone.name();
            } else if (ua.contains("ipad")) {
                return VisitDevice.ipad.name();
            } else if (ua.contains("android")) {
                return VisitDevice.android.name();
            } else {
                return VisitDevice.other.name();
            }
        } else {
            return VisitDevice.pc.name();
        }
    }
}
