package hjin.commons.web.session;

import hjin.commons.web.bean.BaseUrlBean;
import hjin.commons.web.bean.BaseUserBean;
import hjin.commons.web.bean.BaseUserResourceViewBean;

import java.util.List;
import java.util.Map;

/**
 * session工具类,避免在其他代码中直接使用原生态的session
 *
 * @author huangjin
 */
public interface ISessionManager {

    /**
     * 用户是否已经登录
     *
     * @return
     */
    boolean isUserLogined();

    /**
     * 获取已登录用户
     *
     * @return
     */
    BaseUserBean getLoginUser();

    /**
     * 用户登录成功,设置session
     *
     * @param user
     */
    void setUserLogined(BaseUserBean user);

    /**
     * 判断是否是superuser
     *
     * @return
     */
    boolean isSuperUser();

    /**
     * 登录后该用户可见的菜单
     *
     * @param list
     */
    void setMenuList(List<BaseUrlBean> list);

    /**
     * 登录后该用户可见的菜单
     *
     * @return
     */
    List<BaseUrlBean> getMenuList();

    /**
     * 扁平化的菜单列表
     *
     * @param menuList
     */
    void setFlatMenuList(List<BaseUrlBean> menuList);

    /**
     * 设置
     *
     * @return
     */
    List<BaseUrlBean> getFlatMenuList();

    /**
     * 设置资源权限
     *
     * @return
     */
    void setPrivs(List<BaseUserResourceViewBean> privs);

    /**
     * 资源权限
     *
     * @return
     */
    List<BaseUserResourceViewBean> getPrivs();

    Map<String, Object> getCaptcha();

    void setCaptcha(Map<String, Object> captcha);

    void clearCaptcha();

    /**
     * 访问浏览器
     * @return
     */
    String getVisitBrowser();

    void setVisitBrowser(String visitTerminal);

    /**
     * 访问设备
     * @return
     */
    String getVisitDevice();

    void setVisitDevice(String visitDevice);

    String getViewPrefix();

    void setViewPrefix(String viewPrefix);

    List<BaseUrlBean> getWxMenuList();

    void setWxMenuList(List<BaseUrlBean> wxMenuList);
}
