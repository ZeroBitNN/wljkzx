package pageModel;

public class DeptJjbb {
	/**
	 * 用于搜索
	 */
	private int page; // 页码
	private int rows; // 每页行数
	private String sort; // 排序字段
	private String order; // 升序或降序

	/**
	 * 表名-jjbb
	 * 
	 * id 日期-datetime 发起通知人-notifier 类型-type 专业-domain 通知来源-source
	 * 工单编号-serialNum 交办人-comeFrom 标题-title 内容-content 附件-attachment
	 * 
	 * createuser createdatetime updateuser updatetime
	 */
	private String id;
	private String datetime;
	private String notifier;
	private String type;
	private String domain;
	private String source;
	private String serialNum;
	private String comeFrom;
	private String title;
	private String content;
	private String attachment;
	private String createuser;
	private String createdatetime;
	private String updateuser;
	private String updatetime;

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotifier() {
		return notifier;
	}

	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
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

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(String createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		return "DeptJjbb [page=" + page + ", rows=" + rows + ", sort=" + sort + ", order=" + order + ", id=" + id
				+ ", datetime=" + datetime + ", notifier=" + notifier + ", type=" + type + ", domain=" + domain
				+ ", source=" + source + ", serialNum=" + serialNum + ", comeFrom=" + comeFrom + ", title=" + title
				+ ", content=" + content + ", attachment=" + attachment + ", createuser=" + createuser
				+ ", createdatetime=" + createdatetime + ", updateuser=" + updateuser + ", updatetime=" + updatetime
				+ "]";
	}

}
