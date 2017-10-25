package service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.RolesDaoI;
import dao.UserDaoI;
import model.TAccount;
import model.TRoles;
import pageModel.DataGrid;
import pageModel.Roles;
import pageModel.User;
import service.UserServiceI;
import util.Encrypt;

@Service(value = "userService")
public class UserServiceImpl implements UserServiceI {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	private UserDaoI userDao;
	private RolesDaoI rolesDao;

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
	public User save(User user) {
		TAccount t = new TAccount();
		BeanUtils.copyProperties(user, t, new String[] { "pwd" });
		t.setId(UUID.randomUUID().toString());
		t.setPwd(Encrypt.e(user.getPwd()));
		t.setTRoles(new TRoles(user.getRole()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		t.setCreatetime(df.format(new Date()));
		userDao.save(t);
		BeanUtils.copyProperties(t, user);

		TRoles tRoles = rolesDao.getForId(TRoles.class, user.getRole());
		user.setRole(tRoles.getName());
		return user;
	}

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", user.getUsername());
		params.put("pwd", Encrypt.e(user.getPwd()));
		TAccount t = userDao.get("from TAccount t where t.username = :username and t.pwd = :pwd", params);
		if (t != null) {
			user.setId(t.getId());
			if (t.getTRoles().getName() != null && !t.getTRoles().getName().equals("")) {
				user.setRole(t.getTRoles().getName());
			} else {
				user.setRole("");
			}
			return user;
		} else {
			return null;
		}
	}

	@Override
	public DataGrid<User> getAllUsers(User user) {
		DataGrid<User> dg = new DataGrid<User>();
		String hql = "from TAccount t where t.username<>'admin'";
		// 判断是否按用户名查询
		if (user.getUsername() != null && !user.getUsername().trim().equals("")) {
			hql += " and t.username like '%" + user.getUsername() + "%'";
		}
		String totalHql = "select count(*) " + hql;
		// 判断是否按用户名排序
		if (user.getSort() != null) {
			if (user.getSort().equals("role")) {
				hql += " order by t.TRoles.id " + user.getOrder();
			} else {
				hql += " order by " + user.getSort() + " " + user.getOrder();
			}
		}

		// 查询用户
		logger.info(hql);
		List<TAccount> accountList = userDao.find(hql, user.getPage(), user.getRows());
		List<User> userList = new ArrayList<User>();
		// 将accountList的每条记录转换成userList
		if (accountList != null && accountList.size() > 0) {
			for (TAccount t : accountList) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				if (t.getTRoles() != null) {
					u.setRole(t.getTRoles().getName());
				}
				userList.add(u);
			}
		}
		dg.setRows(userList);

		// 查询结果记录数
		logger.info(totalHql);
		dg.setTotal(userDao.count(totalHql));
		logger.info("共找到" + dg.getTotal() + "个用户");

		return dg;
	}

	@Override
	public void delete(String id) {
		/*
		 * TAccount t = userDao.getForId(TAccount.class, id); if (t!=null){ userDao.delete(t); }
		 */
		String hql = "delete from TAccount t where t.id='" + id + "'";
		userDao.executeHql(hql);
	}

	@Override
	public User edit(User user) {
		String newPwd = user.getPwd();
		// logger.info("提交的密码：" + newPwd);
		TAccount t = userDao.getForId(TAccount.class, user.getId());
		// logger.info(t.getTRoles().getName());
		String oldPwd = t.getPwd();
		// logger.info("数据库的密码：" + oldPwd);
		BeanUtils.copyProperties(user, t, new String[] { "pwd" });

		TRoles tr = null;
		if (t.getTRoles() == null || !t.getTRoles().getName().equals(user.getRole())) {
			tr = rolesDao.getForId(TRoles.class, user.getRole());
			if (tr != null) {
				t.setTRoles(tr);
			}
		}

		if (!newPwd.equals(oldPwd)) {
			// logger.info("密码不相同，修改密码");
			t.setPwd(Encrypt.e(newPwd));
			BeanUtils.copyProperties(t, user);
		}

		// t.getTRoles().getName()为空
		if (tr != null) {
			user.setRole(tr.getName());
		}
		return user;
	}

	@Override
	public List<Roles> getRoles() {
		List<Roles> rolesList = new ArrayList<Roles>();
		String hql = "from TRoles t where t.id<>'0'";
		List<TRoles> tList = rolesDao.find(hql);
		for (TRoles t : tList) {
			Roles r = new Roles();
			BeanUtils.copyProperties(t, r);
			rolesList.add(r);
		}
		return rolesList;
	}

	@Override
	public void modifyPwd(User user) {
		TAccount t = userDao.getForId(TAccount.class, user.getId());
		t.setPwd(Encrypt.e(user.getPwd()));
	}

	@Override
	public List<User> getUsers() {
		List<User> uList = new ArrayList<User>();
		String hql = "from TAccount t where t.username<>'admin'";
		List<TAccount> tList = userDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TAccount t : tList) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				uList.add(u);
			}
		}

		return uList;
	}

}
