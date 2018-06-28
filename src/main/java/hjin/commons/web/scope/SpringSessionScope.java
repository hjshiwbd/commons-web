package hjin.commons.web.scope;

import hjin.commons.web.bean.BaseUrlBean;
import hjin.commons.web.bean.BaseUserBean;
import hjin.commons.web.bean.BaseUserResourceViewBean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * 用spring的session scope替代原有的httpsession
 *
 * @author huangjin
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SpringSessionScope {
    private BaseUserBean loginedUser;
    private List<BaseUrlBean> menuList;//pc菜单
    private List<BaseUrlBean> flatMenuList;//扁平菜单
    private List<BaseUserResourceViewBean> privs;//pc个人权限
    private Map<String, Object> captcha;//登录验证码
    private String visitDevice;//访问设备
    private String visitBrowser;//访问浏览器
    private String viewPrefix; // spring UrlBasedViewResolver.prefix
    private List<BaseUrlBean> wxMenuList;//微信菜单

    public BaseUserBean getLoginedUser() {
        return loginedUser;
    }

    public void setLoginedUser(BaseUserBean loginedUser) {
        this.loginedUser = loginedUser;
    }

    public List<BaseUrlBean> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<BaseUrlBean> menuList) {
        this.menuList = menuList;
    }

    public void setFlatMenuList(List<BaseUrlBean> menuList2) {
        this.flatMenuList = menuList2;
    }

    public List<BaseUrlBean> getFlatMenuList() {
        return flatMenuList;
    }

    public List<BaseUserResourceViewBean> getPrivs() {
        return privs;
    }

    public void setPrivs(List<BaseUserResourceViewBean> privs) {
        this.privs = privs;
    }

    public Map<String, Object> getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Map<String, Object> captcha) {
        this.captcha = captcha;
    }

    public void clearCaptcha() {
        this.captcha = null;
    }

    public String getVisitBrowser() {
        return visitBrowser;
    }

    public void setVisitBrowser(String visitBrowser) {
        this.visitBrowser = visitBrowser;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    public void setViewPrefix(String viewPrefix) {
        this.viewPrefix = viewPrefix;
    }

    public List<BaseUrlBean> getWxMenuList() {
        return wxMenuList;
    }

    public void setWxMenuList(List<BaseUrlBean> wxMenuList) {
        this.wxMenuList = wxMenuList;
    }

    public String getVisitDevice() {
        return visitDevice;
    }

    public void setVisitDevice(String visitDevice) {
        this.visitDevice = visitDevice;
    }
}
