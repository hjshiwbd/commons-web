package hjin.commons.web.session.impl;

import hjin.commons.web.bean.BaseUrlBean;
import hjin.commons.web.bean.BaseUserBean;
import hjin.commons.web.bean.BaseUserResourceViewBean;
import hjin.commons.web.constants.SuperUser;
import hjin.commons.web.scope.SpringSessionScope;
import hjin.commons.web.session.ISessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author huangjin
 */
public class SpringScopeSessionManager implements ISessionManager {

    @Autowired
    private SpringSessionScope sessionScopeManager;

    /**
     * 用户是否已经登录
     *
     * @return
     */
    @Override
    public boolean isUserLogined() {
        return sessionScopeManager.getLoginedUser() != null;
    }

    /**
     * 获取已登录用户
     *
     * @return
     */
    @Override
    public BaseUserBean getLoginUser() {
        return sessionScopeManager.getLoginedUser();
    }

    /**
     * 用户登录成功,设置session
     *
     * @param user
     */
    @Override
    public void setUserLogined(BaseUserBean user) {
        sessionScopeManager.setLoginedUser(user);
    }

    /**
     * 判断是否是superuser
     *
     * @return
     */
    @Override
    public boolean isSuperUser() {
        for (SuperUser su : SuperUser.values()) {
            if (su.name().equals(getLoginUser().getCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 登录后该用户可见的菜单
     *
     * @param list
     */
    @Override
    public void setMenuList(List<BaseUrlBean> list) {
        sessionScopeManager.setMenuList(list);
    }

    /**
     * 登录后该用户可见的菜单
     *
     * @return
     */
    @Override
    public List<BaseUrlBean> getMenuList() {
        return sessionScopeManager.getMenuList();
    }

    @Override
    public void setFlatMenuList(List<BaseUrlBean> menuList) {
        sessionScopeManager.setFlatMenuList(menuList);
    }

    @Override
    public List<BaseUrlBean> getFlatMenuList() {
        return sessionScopeManager.getFlatMenuList();
    }

    @Override
    public void setPrivs(List<BaseUserResourceViewBean> privs) {
        sessionScopeManager.setPrivs(privs);
    }

    @Override
    public List<BaseUserResourceViewBean> getPrivs() {
        return sessionScopeManager.getPrivs();
    }

    @Override
    public Map<String, Object> getCaptcha() {
        return sessionScopeManager.getCaptcha();
    }

    @Override
    public void setCaptcha(Map<String, Object> captcha) {
        sessionScopeManager.setCaptcha(captcha);
    }

    @Override
    public void clearCaptcha() {
        sessionScopeManager.clearCaptcha();
    }

    @Override
    public String getVisitBrowser() {
        return sessionScopeManager.getVisitBrowser();
    }

    @Override
    public void setVisitBrowser(String visitBrowser) {
        sessionScopeManager.setVisitBrowser(visitBrowser);
    }

    @Override
    public String getVisitDevice() {
        return sessionScopeManager.getVisitDevice();
    }

    @Override
    public void setVisitDevice(String visitDevice) {
        sessionScopeManager.setVisitDevice(visitDevice);
    }

    @Override
    public String getViewPrefix() {
        return sessionScopeManager.getViewPrefix();
    }

    @Override
    public void setViewPrefix(String viewPrefix) {
        sessionScopeManager.setViewPrefix(viewPrefix);
    }

    @Override
    public List<BaseUrlBean> getWxMenuList() {
        return sessionScopeManager.getWxMenuList();
    }

    @Override
    public void setWxMenuList(List<BaseUrlBean> wxMenuList) {
        sessionScopeManager.setWxMenuList(wxMenuList);
    }

}
