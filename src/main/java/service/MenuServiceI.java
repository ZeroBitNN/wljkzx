package service;

import java.util.List;

import pageModel.Menu;

public interface MenuServiceI {
	
	public List<Menu> getMenu(String id);
	
	public List<Menu> getAllMenu();

}
