package pageModel;

public class DeptTxl {
	/**
	 * 用于搜索
	 */
	private int page; // 页码
	private int rows; // 每页行数
	private String sort; // 排序字段
	private String order; // 升序或降序
	private String content;	// 搜索内容

	/**
	 * 部门通讯录表字段 编号-id 姓名-xm 小灵通-xlt 手机-sj 电话-dh 职务-dwzw 单位部门-dwbm
	 */
	private String id;
	private String xm;
	private String xlt;
	private String sj;
	private String dh;
	private String dwzw;
	private String dwbm;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getXlt() {
		return xlt;
	}
	public void setXlt(String xlt) {
		this.xlt = xlt;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getDh() {
		return dh;
	}
	public void setDh(String dh) {
		this.dh = dh;
	}
	public String getDwzw() {
		return dwzw;
	}
	public void setDwzw(String dwzw) {
		this.dwzw = dwzw;
	}
	public String getDwbm() {
		return dwbm;
	}
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	

}
