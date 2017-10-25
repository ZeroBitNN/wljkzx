package action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.Menu;
import service.MenuServiceI;

@Action(value="menuAction")
public class MenuAction extends BaseAction implements ModelDriven<Menu> {
	private Menu menu = new Menu();
	private MenuServiceI menuService;

	public MenuServiceI getMenuService() {
		return menuService;
	}

	@Autowired
	public void setMenuService(MenuServiceI menuService) {
		this.menuService = menuService;
	}

	@Override
	public Menu getModel() {
		return menu;
	}
	
	/**
	 * 异步获取菜点节点
	 */
	public void doNotNeedSecurity_getMenu(){
		super.writeJson(menuService.getMenu(menu.getId()));;
	}

	public void doNotNeedSecurity_getAllMenu(){
		super.writeJson(menuService.getAllMenu());;
	}
}
