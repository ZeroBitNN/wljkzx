package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Perf;
import service.PerfServiceI;

@Namespace("/")
@Action(value = "perfAction")
public class PerfAction extends BaseAction implements ModelDriven<Perf> {
	private Perf perf = new Perf();

	@Override
	public Perf getModel() {
		return perf;
	}

	private PerfServiceI perfService;

	public PerfServiceI getPerfService() {
		return perfService;
	}

	@Autowired
	public void setPerfService(PerfServiceI perfService) {
		this.perfService = perfService;
	}

	public void getGrjxDg() {
		DataGrid<Perf> dg = perfService.getGrjx();
		super.writeJson(dg);
	}

	public void doNotNeedSecurity_save() {
		Json j = new Json();
		try {
			perfService.saveGrjx(perf);
			j.setSuccess(true);
			j.setMsg("保存成功！");
		} catch (Exception e) {
			j.setMsg("保存失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void getPerfDg() {
		DataGrid<Perf> dg = null;
		try {
			dg = perfService.getPerf();
		} catch (Exception e) {
			dg = new DataGrid<Perf>();
			dg.setTotal(-1L);
		} finally {
			super.writeJson(dg);
		}
	}
}
