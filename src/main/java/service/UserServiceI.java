package service;

import java.util.List;

import pageModel.DataGrid;
import pageModel.Roles;
import pageModel.User;

public interface UserServiceI {

	public User save(User user);

	public User login(User user);

	public DataGrid<User> getAllUsers(User user);

	public void delete(String id);

	public User edit(User user);

	public List<Roles> getRoles();

	public void modifyPwd(User user);

	public List<User> getUsers();

}
