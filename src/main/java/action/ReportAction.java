package action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import pageModel.DataGrid;
import pageModel.RecReport;
import service.WorkrecordServiceI;
import util.ExcelUtil;

@Namespace("/")
@Action(value = "reportAction")
public class ReportAction extends BaseAction implements ModelDriven<RecReport> {
	private static final Logger logger = Logger.getLogger(ReportAction.class);

	private RecReport recReport = new RecReport();

	@Override
	public RecReport getModel() {
		return recReport;
	}

	private WorkrecordServiceI workrecordService;

	public WorkrecordServiceI getWorkrecordService() {
		return workrecordService;
	}

	@Autowired
	public void setWorkrecordService(WorkrecordServiceI workrecordService) {
		this.workrecordService = workrecordService;
	}

	public void doNotNeedSecurity_getReport() {
		DataGrid<RecReport> rData = workrecordService.getReportDg(recReport);
		super.writeJson(rData);
	}

	public void doNotNeedSecurity_exportExcel() {
		// 导出EXCEL功能
		// 获取Response对象
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
		// 获设置表头
		Map<String, String> headMap = new LinkedHashMap<String, String>();
		headMap.put("name", "姓名");
		headMap.put("untreated", "未处理");
		headMap.put("processed", "已处理");
		headMap.put("sum", "总计");
		// 获取数据集
		JSONArray ja = new JSONArray();
		List<RecReport> rList = workrecordService.getReport(recReport);
		for (int i = 0; i < rList.size(); i++) {
			ja.add(rList.get(i));
		}

		String title = "工作记录统计表";
		logger.info("开始导出EXCEL \"" + title + ".xlsx\"");
		ExcelUtil.downloadExcelFile(title, headMap, ja, response);
	}
}
