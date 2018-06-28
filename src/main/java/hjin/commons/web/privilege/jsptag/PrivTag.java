package hjin.commons.web.privilege.jsptag;

import commons.tool.utils.JsonUtil;
import commons.tool.utils.SpringContextUtil;
import hjin.commons.web.bean.BaseUserResourceViewBean;
import hjin.commons.web.session.ISessionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import java.util.List;

@SuppressWarnings("serial")
public class PrivTag extends RequestContextAwareTag {

    private String resourceJson;

    @Override
    protected int doStartTagInternal() throws Exception {
        if (isUserHasResourceId()) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * 多个权限时,有一个满足即返回true
     *
     * @return
     */
    private boolean isUserHasResourceId() {
        ISessionManager sessionManager = SpringContextUtil.getInstance().getBean("sessionManager");
        if (sessionManager.isSuperUser()) {
            return true;
        }
        List<BaseUserResourceViewBean> pagePrivs = getParsedList();
        for (BaseUserResourceViewBean b1 : pagePrivs) {// 请求中的权限
            if (b1.getResourcetype() == null) {
                b1.setResourcetype("action");
            }
            if (sessionManager.getPrivs() != null) {
                for (BaseUserResourceViewBean b2 : sessionManager.getPrivs()) {// 用户所有权限
                    if (b1.getResourceid().equals(b2.getResourceid())
                            && b1.getResourcetype().equals(b2.getResourcetype())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<BaseUserResourceViewBean> getParsedList() {
        if (StringUtils.isBlank(resourceJson)) {
            throw new RuntimeException("resourceJson are empty");
        }

        return JsonUtil.toObjList(resourceJson, BaseUserResourceViewBean.class);
    }

    public String getResourceJson() {
        return resourceJson;
    }

    public void setResourceJson(String resourceJson) {
        this.resourceJson = resourceJson;
    }
}
