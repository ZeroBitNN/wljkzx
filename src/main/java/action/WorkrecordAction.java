package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import model.SessionInfo;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Workrecord;
import service.WorkrecordServiceI;

@Namespace("/")
@Action(value = "workAction")
public class WorkrecordAction extends BaseAction implements ModelDriven<Workrecord> {
	private Workrecord workrecord = new Workrecord();

	@Override
	public Workrecord getModel() {
		return workrecord;
	}

	private WorkrecordServiceI workrecordService;

	public WorkrecordServiceI getWorkrecordService() {
		return workrecordService;
	}

	@Autowired
	public void setWorkrecordService(WorkrecordServiceI workrecordService) {
		this.workrecordService = workrecordService;
	}

	private SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute("sessionInfo");

	public void doNotNeedSecurity_getAllDg() {
		DataGrid<Workrecord> g = workrecordService.getDatagrid(workrecord);
		super.writeJson(g);
	}

	public void add() {
		Json j = new Json();
		try {
			workrecordService.save(workrecord);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加成功！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		if (sessionInfo != null && sessionInfo.getUser().getUsername().equals(workrecord.getHandler())) {
			try {
				workrecordService.edit(workrecord);
				j.setSuccess(true);
				j.setMsg("修改成功！");
			} catch (Exception e) {
				j.setMsg("修改失败！" + e.getMessage());
			}
		} else {
			j.setMsg("修改失败！不能修改非本人记录。");
		}
		super.writeJson(j);

	}

	public void delete() {
		Json j = new Json();
		if (sessionInfo != null && sessionInfo.getUser().getUsername().equals(workrecord.getHandler())) {
			try {
				workrecordService.delete(workrecord.getId());
				j.setSuccess(true);
				j.setMsg("删除成功！");
			} catch (Exception e) {
				j.setMsg("删除失败！" + e.getMessage());
			}
		} else {
			j.setMsg("删除失败！不能删除非本人记录。");
		}
		super.writeJson(j);
	}

	public void doNotNeedSecurity_getUntreated() {
		DataGrid<Workrecord> g = new DataGrid<Workrecord>();
		if (sessionInfo != null && !sessionInfo.getUser().getUsername().trim().equals("")) {
			g = workrecordService.getUntreated(workrecord, sessionInfo.getUser().getUsername());
		}
		super.writeJson(g);
	}

	public void doNotNeedSecurity_send() {
		Json j = new Json();
		try {
			workrecordService.updateHandler(workrecord);
			j.setSuccess(true);
			j.setMsg("转派成功！");
		} catch (Exception e) {
			j.setMsg("转派失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
