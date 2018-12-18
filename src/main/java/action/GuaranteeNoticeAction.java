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
import pageModel.Guarantee;
import pageModel.GuaranteeNotice;
import pageModel.Json;
import service.GuaranteeServiceI;
import util.ExcelUtil;

@Namespace("/")
@Action(value = "guaranteeNoticeAction")
public class GuaranteeNoticeAction extends BaseAction implements ModelDriven<GuaranteeNotice> {
	private static final Logger logger = Logger.getLogger(GuaranteeNoticeAction.class);
	private GuaranteeNotice guaranteeNotice = new GuaranteeNotice();

	@Override
	public GuaranteeNotice getModel() {
		return guaranteeNotice;
	}

	private GuaranteeServiceI guaranteeService;

	public GuaranteeServiceI getGuaranteeService() {
		return guaranteeService;
	}

	@Autowired
	public void setGuaranteeService(GuaranteeServiceI guaranteeService) {
		this.guaranteeService = guaranteeService;
	}

	public void doNotNeedSecurity_getNoticeDg() {
		DataGrid<GuaranteeNotice> g = guaranteeService.getNoticeByRelated(guaranteeNotice);
		super.writeJson(g);
	}

	public void add() {
		Json j = new Json();
		try {
			guaranteeService.saveNotice(guaranteeNotice);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void delete() {
		Json j = new Json();
		try {
			guaranteeService.delNotice(guaranteeNotice.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void edit() {
		Json j = new Json();
		try {
			GuaranteeNotice g = guaranteeService.editNotice(guaranteeNotice);
			j.setSuccess(true);
			j.setObj(g);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void doNotNeedSecurity_exportExcel() {
		// 获取Response对象
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext()
				.get(StrutsStatics.HTTP_RESPONSE);
		// 获设置表头
		Map<String, String> headMap = new LinkedHashMap<String, String>();
		headMap.put("noticedate", "日期");
		headMap.put("noticetime", "时间");
		headMap.put("content", "易信通报内容");
		headMap.put("recorder", "通报人");
		headMap.put("remarks", "备注或问题记录");
		// 获取数据集
		JSONArray ja = new JSONArray();
		// List<RecReport> rList = workrecordService.getReport(recReport);
		List<GuaranteeNotice> rList = guaranteeService.getDataForExcel(guaranteeNotice);
		for (int i = 0; i < rList.size(); i++) {
			ja.add(rList.get(i));
		}

		Guarantee g = guaranteeService.getGuarantee(guaranteeNotice.getRelated());
		logger.info("开始导出EXCEL \"" + g.getTitle() + ".xlsx\"");
		ExcelUtil.downloadExcelFile(g.getTitle(), headMap, ja, response);
	}
}
