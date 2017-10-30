package pageModel;

import java.util.Date;

public class DailyworkDetails {

	// Fields

	private String id;
	private String rId; // 关联日管控列表ID
	private String recoder; // 记录人ID
	private Date dailydate; // 日期
	private String status; // 状态(未处理-默认、正常、异常、已发通报)
	private Date recodtime; // 记录时间
	private String remark; // 备注
	/**
	 * 用于搜索
	 */
	private int page; // 页码
	private int rows; // 每页行数
	private String sort; // 排序字段
	private String order; // 升序或降序

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getRecoder() {
		return recoder;
	}

	public void setRecoder(String recoder) {
		this.recoder = recoder;
	}

	public Date getDailydate() {
		return dailydate;
	}

	public void setDailydate(Date dailydate) {
		this.dailydate = dailydate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRecodtime() {
		return recodtime;
	}

	public void setRecodtime(Date recodtime) {
		this.recodtime = recodtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
