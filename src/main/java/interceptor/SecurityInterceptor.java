package interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import dao.ResourcesDaoI;
import dao.RolesDaoI;
import dao.UserDaoI;
import model.SessionInfo;
import model.TAccount;
import model.TResources;
import model.TRoles;

public class SecurityInterceptor extends MethodFilterInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -711638158738099052L;
	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);
	private UserDaoI userDao;
	private RolesDaoI rolesDao;
	private ResourcesDaoI resourcesDao;

	public ResourcesDaoI getResourcesDao() {
		return resourcesDao;
	}

	@Autowired
	public void setResourcesDao(ResourcesDaoI resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	public RolesDaoI getRolesDao() {
		return rolesDao;
	}

	@Autowired
	public void setRolesDao(RolesDaoI rolesDao) {
		this.rolesDao = rolesDao;
	}

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession()
				.getAttribute("sessionInfo");
		String servletPath = ServletActionContext.getRequest().getServletPath();

		servletPath = StringUtils.substringBeforeLast(servletPath, ".");// 去掉后面的后缀 *.action之类的

		//logger.info("进入权限拦截器->访问的资源为：[" + servletPath + "]");
		// [action.UserAction]进入权限拦截器->访问的资源为：[/userAction!doTest]

		// 1.获取当前用户的角色ID(TRoles.id)
		TAccount tA = userDao.getForId(TAccount.class, sessionInfo.getUser().getId());
		TRoles tR = rolesDao.getForId(TRoles.class, tA.getTRoles().getId());
		// 2.根据角色ID查询角色权限ID(resourcesIds)
		String[] resIds = tR.getResourcesIds().split(",");
		// admin账号权限
		if (resIds[0].equals("All")) {
			return invocation.invoke();
		}
		// 3.根据权限ID循环获取权限URL
		for (String resId : resIds) {
			TResources tRes = resourcesDao.getForId(TResources.class, resId);
			// 4.将获取到的URL与servletPath对比，有相同的则允许通过
			if (servletPath.equals(tRes.getUrl())) {
				return invocation.invoke(); // 允许通过
			}
		}

		// 5.否则不允许通过
		String errMsg = "您没有访问此功能的权限！功能资源路径为[" + servletPath + "]请联系管理员给你赋予相应权限。";
		//logger.info(errMsg);
		ServletActionContext.getRequest().setAttribute("msg", errMsg);
		return "noSecurity";
	}

}
