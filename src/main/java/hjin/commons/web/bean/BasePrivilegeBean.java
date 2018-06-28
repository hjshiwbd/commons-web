package hjin.commons.web.bean;

import commons.tool.utils.SpringContextUtil;
import hjin.commons.web.session.ISessionManager;

public class BasePrivilegeBean {
	private String menu_authority_sql;// 菜单权限查询语句
	private String isadmin;

	public String getMenu_authority_sql() {
		return menu_authority_sql;
	}

	public void setMenu_authority_sql(String menu_authority_sql) {
		this.menu_authority_sql = menu_authority_sql;
	}

	public String getIsadmin() {
		if (isadmin == null) {
			ISessionManager session = SpringContextUtil.getInstance().getBean("sessionManager");
			isadmin = session.isSuperUser() ? "1" : "0";
		}
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public Integer getLoginUserId() {
		ISessionManager session = SpringContextUtil.getInstance().getBean("sessionManager");
		return session.getLoginUser().getId();
	}
}
