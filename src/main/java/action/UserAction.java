package action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import model.SessionInfo;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Roles;
import pageModel.User;
import service.UserServiceI;

@Namespace("/")
@Action(value = "userAction")
public class UserAction extends BaseAction implements ModelDriven<User> {
	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	private static final Logger logger = Logger.getLogger(UserAction.class);

	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	public void reg() {
		Json j = new Json();

		try {
			userService.save(user);
			j.setSuccess(true);
			j.setMsg("注册成功！");
		} catch (Exception e) {
			j.setMsg("注册失败！" + e.getMessage());
		}

		super.writeJson(j);
	}

	public void add() {
		Json j = new Json();

		try {
			User u = userService.save(user);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(u);
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}

		super.writeJson(j);
	}

	public void delete() {
		Json j = new Json();

		try {
			userService.delete(user.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}

		super.writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		try {
			User u = userService.edit(user);
			j.setSuccess(true);
			j.setObj(u);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}

		super.writeJson(j);
	}

	public void doNotNeedSecurity_modifyPwd() {
		Json j = new Json();
		try {
			userService.modifyPwd(user);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}

		super.writeJson(j);
	}

	public void doNotNeedSecurity_login() {
		User u = userService.login(user);
		Json j = new Json();
		if (u != null) {
			j.setSuccess(true);
			j.setMsg("登录成功！");

			// 存储登录信息到Session
			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setUser(u);
			getSession().setAttribute("sessionInfo", sessionInfo);

		} else {
			j.setMsg("登录失败，用户名或密码错误！");
		}
		super.writeJson(j);
	}

	public void datagrid() {
		DataGrid<User> g = userService.getAllUsers(user);
		super.writeJson(g);
	}

	public void doNotNeedSecurity_logout() {
		if (getSession() != null) {
			getSession().invalidate();
		}
		Json j = new Json();
		j.setMsg("注销成功！");
		j.setSuccess(true);
		super.writeJson(j);
	}

	public void doNotNeedSecurity_getRole() {
		List<Roles> list = userService.getRoles();
		super.writeJson(list);
	}

	public void doNotNeedSecurity_getAllUser() {
		List<User> list = userService.getUsers();
		super.writeJson(list);
	}
}
