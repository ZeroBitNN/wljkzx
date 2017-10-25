package pageModel;

import java.util.Date;

public class Guarantee {
	private String attachment;// 附件信息

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	// Fields
	private String id;
	private String title; // 标题(*)
	private String content; // 保障内容(*)
	private String requirement; // 具体工作要求
	private String noticetemplate; // 易信通报模板(*)
	private String recorder; // 记录人ID(默认为当前用户ID)
	private Date timestart; // 保障开始时间
	private Date timeend; // 保障结束时间
	private String timepoint; // 通报时间点(格式：hh:mm，如：09:00，多个时间点用英文半角“|”分隔)
	private String status; // 状态：保障中、已结束

	// 用于搜索、分页及排序
	private int page; // 页码
	private int rows; // 每页行数
	private String sort; // 排序字段
	private String order; // 升序或降序
	private String keywords; // 搜索关键字

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getNoticetemplate() {
		return noticetemplate;
	}

	public void setNoticetemplate(String noticetemplate) {
		this.noticetemplate = noticetemplate;
	}

	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public Date getTimestart() {
		return timestart;
	}

	public void setTimestart(Date timestart) {
		this.timestart = timestart;
	}

	public Date getTimeend() {
		return timeend;
	}

	public void setTimeend(Date timeend) {
		this.timeend = timeend;
	}

	public String getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
