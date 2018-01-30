package action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import action.BaseAction;
import kmbsModel.KbmsUser;
import pageModel.DataGrid;
import pageModel.Json;
import service.KbmsUserServiceI;

@Namespace("/")
@Action(value = "kbmsUserAction")
public class KbmsUserAction extends BaseAction implements ModelDriven<KbmsUser> {
	private static final Logger logger = Logger.getLogger(KbmsUserAction.class);

	private KbmsUser kbmsUser = new KbmsUser();

	@Override
	public KbmsUser getModel() {
		return kbmsUser;
	}

	private KbmsUserServiceI kbmsUserService;

	public KbmsUserServiceI getKbmsUserService() {
		return kbmsUserService;
	}

	@Autowired
	public void setKbmsUserService(KbmsUserServiceI kbmsUserService) {
		this.kbmsUserService = kbmsUserService;
	}

	public void datagrid() {
		DataGrid<KbmsUser> g = kbmsUserService.getUser(kbmsUser);
		super.writeJson(g);
	}

	public void add() {
		Json j = new Json();
		try {
			KbmsUser ku = kbmsUserService.save(kbmsUser);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(ku);
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
	
	public void edit(){
		Json j = new Json();
		try {
			KbmsUser ku = kbmsUserService.edit(kbmsUser);
			j.setSuccess(true);
			j.setMsg("修改成功！");
			j.setObj(ku);
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
	
	public void delete(){
		Json j = new Json();
		try {
			kbmsUserService.delete(kbmsUser);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
