package pageModel;

import java.util.Date;

public class Workrecord {
	private String dateStr;

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	private int page; // 页码
	private int rows; // 每页行数
	private String sort; // 排序字段
	private String order; // 升序或降序
	private Date createtimeStart; // 创建时间开始
	private Date createtimeEnd; // 创建时间结束
	private Date modifytimeStart; // 修改时间开始
	private Date modifytimeEnd; // 修改时间结束
	private String recordtype;	//工单类别

	public String getRecordtype() {
		return recordtype;
	}

	public void setRecordtype(String recordtype) {
		this.recordtype = recordtype;
	}

	public Date getCreatetimeStart() {
		return createtimeStart;
	}

	public void setCreatetimeStart(Date createtimeStart) {
		this.createtimeStart = createtimeStart;
	}

	public Date getCreatetimeEnd() {
		return createtimeEnd;
	}

	public void setCreatetimeEnd(Date createtimeEnd) {
		this.createtimeEnd = createtimeEnd;
	}

	public Date getModifytimeStart() {
		return modifytimeStart;
	}

	public void setModifytimeStart(Date modifytimeStart) {
		this.modifytimeStart = modifytimeStart;
	}

	public Date getModifytimeEnd() {
		return modifytimeEnd;
	}

	public void setModifytimeEnd(Date modifytimeEnd) {
		this.modifytimeEnd = modifytimeEnd;
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

	private String id;
	private String proposer; // 申告人及联系号码
	private String faultnumber; // 故障号码
	private String describe; // 故障描述
	private String situation; // 故障处理情况
	private Date createtime; // 创建时间
	private Date modifytime; // 修改时间
	private String handler; // 处理人
	private String status; // 状态：未处理、已处理
	private String inputer;// 记录人

	public String getInputer() {
		return inputer;
	}

	public void setInputer(String inputer) {
		this.inputer = inputer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getFaultnumber() {
		return faultnumber;
	}

	public void setFaultnumber(String faultnumber) {
		this.faultnumber = faultnumber;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Workrecord [id=" + id + ", proposer=" + proposer + ", faultnumber=" + faultnumber + ", describe="
				+ describe + ", situation=" + situation + ", createtime=" + createtime + ", modifytime=" + modifytime
				+ ", handler=" + handler + ", status=" + status + "]";
	}

}
