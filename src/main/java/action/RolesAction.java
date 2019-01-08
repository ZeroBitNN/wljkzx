package action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Roles;
import service.RolesServiceI;

@Action(value = "rolesAction")
public class RolesAction extends BaseAction implements ModelDriven<Roles> {
	private Roles roles = new Roles();
	private RolesServiceI rolesService;

	public RolesServiceI getRolesService() {
		return rolesService;
	}

	@Autowired
	public void setRolesService(RolesServiceI rolesService) {
		this.rolesService = rolesService;
	}

	@Override
	public Roles getModel() {
		return roles;
	}

	public void doNotNeedSecurity_datagrid() {
		DataGrid<Roles> g = rolesService.getRoles(roles);
		super.writeJson(g);
	}

	public void edit() {
		Json j = new Json();
		try {
			Roles r = rolesService.edit(roles);
			j.setSuccess(true);
			j.setObj(r);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void add() {
		Json j = new Json();
		try {
			Roles r = rolesService.add(roles);
			j.setSuccess(true);
			j.setObj(r);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void delete(){
		Json j = new Json();
		try {
			rolesService.delete(roles.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
