package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.MenuDaoI;
import model.Tmenu;
import pageModel.Menu;
import service.MenuServiceI;

@Service(value = "menuService")
public class MenuServiceImpl implements MenuServiceI {
	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private MenuDaoI menuDao;

	public MenuDaoI getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(MenuDaoI menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public List<Menu> getMenu(String id) {
		List<Menu> menuList = new ArrayList<Menu>();
		String hql = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (id == null || id.equals("")) {
			hql = "from Tmenu t where t.tmenu is null";
		} else {
			hql = "from Tmenu t where t.tmenu.id = :id";
			params.put("id", id);
		}
		List<Tmenu> l = menuDao.find(hql, params);
		if (l != null && l.size() > 0) {
			for (Tmenu t : l) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				if (t.getTmenus() != null & !t.getTmenus().isEmpty()) {
					m.setState("closed");
				} else {
					m.setState("open");
				}
				menuList.add(m);
			}
		}
		return menuList;
	}

	@Override
	public List<Menu> getAllMenu() {
		List<Menu> menuList = new ArrayList<Menu>();
		String hql = "from Tmenu t order by t.id";
		List<Tmenu> l = menuDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Tmenu t : l) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("url", t.getUrl());
				m.setAttributes(attributes);
				Tmenu tm = t.getTmenu();
				if (tm != null) {
					m.setPid(t.getTmenu().getId());
				}
				menuList.add(m);
			}
		}
		return menuList;
	}

}
