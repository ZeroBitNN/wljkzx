package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.Dailywork;
import pageModel.DataGrid;
import pageModel.Json;
import service.DailyworkServiceI;

@Namespace("/")
@Action(value = "dailyworkAction")
public class DailyworkAction extends BaseAction implements ModelDriven<Dailywork> {
	private Dailywork dailywork = new Dailywork();

	@Override
	public Dailywork getModel() {
		return dailywork;
	}

	private DailyworkServiceI dailyworkService;

	public DailyworkServiceI getDailyworkService() {
		return dailyworkService;
	}

	@Autowired
	public void setDailyworkService(DailyworkServiceI dailyworkService) {
		this.dailyworkService = dailyworkService;
	}

	public void doNotNeedSecurity_datagrid() {
		DataGrid<Dailywork> dg = dailyworkService.getDatagrid(dailywork);
		super.writeJson(dg);
	}

	public void add() {
		Json j = new Json();
		try {
			dailyworkService.save(dailywork);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void del() {
		Json j = new Json();
		String ids = dailywork.getIds();
		try {
			dailyworkService.delete(ids);
			j.setSuccess(true);
			j.setMsg("记录删除成功！");
		} catch (Exception e) {
			j.setMsg("记录删除失败！" + e.getMessage());
		}
		super.writeJson(j);

	}
	
	public void edit(){
		Json j = new Json();
		try {
			dailyworkService.edit(dailywork);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改成功！" + e.getMessage());
		}
		super.writeJson(j);
	}
}
