package pageModel;

import java.util.Date;

public class RecReport {
	private int page; // 页码
	private int rows; // 每页行数
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

	private Date createtimeStart;
	private Date createtimeEnd;

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

	private String name; // 姓名
	private Long untreated; // 未处理
	private Long processed; // 已处理
	private Long sum; // 总计

	public Long getUntreated() {
		return untreated;
	}

	public void setUntreated(Long untreated) {
		this.untreated = untreated;
	}

	public Long getProcessed() {
		return processed;
	}

	public void setProcessed(Long processed) {
		this.processed = processed;
	}

	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
