package pageModel;

import java.util.Date;

public class GuaranteeNotice {

	// Fields

	private String id;
	private String related; // 关联保障记录ID
	private Date noticedate; // 通报日期
	private String noticetime; // 通报时间
	private String content; // 易信通报内容
	private String recorder; // 通报人
	private String remarks; // 备注或问题记录

	// 用于搜索、分页及排序
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

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	public Date getNoticedate() {
		return noticedate;
	}

	public void setNoticedate(Date noticedate) {
		this.noticedate = noticedate;
	}

	public String getNoticetime() {
		return noticetime;
	}

	public void setNoticetime(String noticetime) {
		this.noticetime = noticetime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
