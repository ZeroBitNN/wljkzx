package action;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Perf;
import service.PerfServiceI;
import util.ExcelUtil;

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

	public void doNotNeedSecurity_exportGrjx() {
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext()
				.get(StrutsStatics.HTTP_RESPONSE);
		// 设置表头
		Map<String, String> headMap = new LinkedHashMap<String, String>();
		headMap.put("name", "姓名");
		headMap.put("grjx", "个人绩效");

		// 获取数据集
		JSONArray ja = new JSONArray();
		DataGrid<Perf> grjxList = perfService.getGrjx();
		for (int i = 0; i < grjxList.getRows().size(); i++) {
			Map<String, String> dataMap = new LinkedHashMap<String, String>();
			Perf p = grjxList.getRows().get(i);
			dataMap.put("name", p.getName());
			dataMap.put("grjx", "");
			ja.add(dataMap);
		}

		// 导出EXCEL
		ExcelUtil.downloadExcelFile("个人绩效录入模板", headMap, ja, response);
	}
	
	public void doNotNeedSecurity_importGrjx(){
		Json j = perfService.importGrjx(ServletActionContext.getRequest());
		super.writeJson(j);
	}
}
