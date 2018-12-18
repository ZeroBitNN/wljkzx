package action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import model.TPerf;
import model.TPerfNum;
import model.TPerfParam;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Perf;
import service.PerfNumServiceI;
import service.PerfParamServiceI;
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

	private PerfNumServiceI perfNumService;

	public PerfNumServiceI getPerfNumService() {
		return perfNumService;
	}

	@Autowired
	public void setPerfNumService(PerfNumServiceI perfNumService) {
		this.perfNumService = perfNumService;
	}

	private PerfParamServiceI perfParamService;

	public PerfParamServiceI getPerfParamService() {
		return perfParamService;
	}

	@Autowired
	public void setPerfParamService(PerfParamServiceI perfParamService) {
		this.perfParamService = perfParamService;
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
			dg = perfService.getPerf(perf);
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

	public void doNotNeedSecurity_importGrjx() {
		Json j = perfService.importGrjx(ServletActionContext.getRequest());
		super.writeJson(j);
	}

	public void doNotNeedSecurity_getPerfdate() {
		JSONArray ja = new JSONArray();
		List<String> pList = perfService.getPerfDate();
		if (pList != null && pList.size() > 0) {
			for (String s : pList) {
				Map<String, String> dataMap = new LinkedHashMap<String, String>();
				dataMap.put("perfdate", s);
				ja.add(dataMap);
			}
		}
		super.writeJson(ja);
	}

	public void doNotNeedSecurity_exportIntro() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext()
				.get(StrutsStatics.HTTP_RESPONSE);
		try {
			request.setCharacterEncoding("utf-8");
			String perfdate = new String(request.getParameter("perfdate").getBytes("iso8859-1"), "utf-8");
			//String perfdate = request.getParameter("perfdate");
			// 通过日期获取绩效信息(t_perf)
			List<TPerf> perfList = perfService.getPerfForDate(perfdate);
			// 通过绩效信息的名字和日期获取工单量(t_perf_num)
			List<TPerfNum> perfNumList = perfNumService.getPerfNum(perfList.get(0).getTAccount().getId(), perfdate);
			// 通过工单量获取工单类目名称
			List<String> paramNameList = new ArrayList<String>();
			if (perfNumList != null && perfNumList.size() > 0) {
				for (TPerfNum t : perfNumList) {
					TPerfParam tpp = perfParamService.getParam(t.getTPerfParam().getId());
					paramNameList.add(tpp.getName());
				}
			}
			// 生成表头
			Map<String, String> headMap = new LinkedHashMap<String, String>();
			headMap.put("name", "姓名");
			if (paramNameList != null && paramNameList.size() > 0) {
				for (String s : paramNameList) {
					headMap.put(s, s);
				}
			}
			headMap.put("zzhz", "汇总分值");
			headMap.put("jjjx", "计件绩效金额");
			headMap.put("ranking", "排名");
			// 生成数据集
			JSONArray ja = new JSONArray();
			if (perfList != null && perfList.size() > 0) {
				for (TPerf t : perfList) {
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("name", t.getTAccount().getUsername());
					List<TPerfNum> perfNum = perfNumService.getPerfNum(t.getTAccount().getId(), perfdate);
					if (perfNum != null && perfNum.size() > 0) {
						for (TPerfNum tpn : perfNum) {
							dataMap.put(tpn.getTPerfParam().getName(), Integer.toString(tpn.getValue().intValue()));
						}
					}
					dataMap.put("zzhz", Integer.toString(t.getZzhz().intValue()));
					dataMap.put("jjjx", t.getJjjx().toString());
					dataMap.put("ranking", t.getRanking());
					ja.add(dataMap);
				}
			}
			// 导出
			ExcelUtil.downloadExcelFile("工单量详单", headMap, ja, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
