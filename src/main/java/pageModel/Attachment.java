package pageModel;

import java.util.Date;

public class Attachment {
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	private String id;
	private String filename; // 文件名
	private String account; // 上传人ID
	private Date uploadtime; // 上传上时间
	private String describe; // 描述
	private String relatedid; // 关联ID
	private String url;// URL地址

	/**
	 * 用于搜索
	 */
	private int page; // 页码
	private int rows; // 每页行数
	private String sort; // 排序字段
	private String order; // 升序或降序
	private Date uploadtimeStart; // 上传时间开始
	private Date uploadtimeEnd; // 上传时间结束

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getRelatedid() {
		return relatedid;
	}

	public void setRelatedid(String relatedid) {
		this.relatedid = relatedid;
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

	public Date getUploadtimeStart() {
		return uploadtimeStart;
	}

	public void setUploadtimeStart(Date uploadtimeStart) {
		this.uploadtimeStart = uploadtimeStart;
	}

	public Date getUploadtimeEnd() {
		return uploadtimeEnd;
	}

	public void setUploadtimeEnd(Date uploadtimeEnd) {
		this.uploadtimeEnd = uploadtimeEnd;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
