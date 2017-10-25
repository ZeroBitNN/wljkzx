package pageModel;

import java.util.Date;

public class Memo {
	private Date createtimeStart;	//创建时间开始
	private Date createtimeEnd;		//创建时间结束
	private Date modifytimeStart;	//修改时间开始
	private Date modifytimeEnd;		//修改时间结束

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

	private int page; // 页码
	private int rows; // 第页行数
	private String sort; // 排序字段
	private String order; // 升序或降序

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

	private String id; // ID
	private String Memofrom; // 交班人
	private String Memoto; // 接班人
	private String classes; // 班次
	private Date createtime; // 创建时间
	private Date modifytime; // 修改时间
	private String content; // 值班记事
	private String othercontent; // 其他记事
	private String leaveover; // 遗留问题
	private String memotype; // 日志类型--普通记录、领导交办
	private String status; // 状态

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemoto() {
		return Memoto;
	}

	public void setMemoto(String memoto) {
		Memoto = memoto;
	}

	public String getMemofrom() {
		return Memofrom;
	}

	public void setMemofrom(String memofrom) {
		Memofrom = memofrom;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOthercontent() {
		return othercontent;
	}

	public void setOthercontent(String othercontent) {
		this.othercontent = othercontent;
	}

	public String getLeaveover() {
		return leaveover;
	}

	public void setLeaveover(String leaveover) {
		this.leaveover = leaveover;
	}

	public String getMemotype() {
		return memotype;
	}

	public void setMemotype(String memotype) {
		this.memotype = memotype;
	}

	@Override
	public String toString() {
		return "Memo [id=" + id + ", Memofrom=" + Memofrom + ", Memoto=" + Memoto + ", classes=" + classes
				+ ", createtime=" + createtime + ", modifytime=" + modifytime + ", content=" + content
				+ ", othercontent=" + othercontent + ", leaveover=" + leaveover + ", memotype=" + memotype + ", status="
				+ status + "]";
	}

}
