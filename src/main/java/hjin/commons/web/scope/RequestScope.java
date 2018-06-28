package hjin.commons.web.scope;

import hjin.commons.web.bean.BaseUserBean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScope {
    private String logContent;
    private BaseUserBean sessionUser;

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public BaseUserBean getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(BaseUserBean sessionUser) {
        this.sessionUser = sessionUser;
    }
}
