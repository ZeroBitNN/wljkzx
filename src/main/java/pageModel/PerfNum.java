package pageModel;

import java.math.BigDecimal;

public class PerfNum {
	// 工单类目
	private String itemgroup;

	public String getItemgroup() {
		return itemgroup;
	}

	public void setItemgroup(String itemgroup) {
		this.itemgroup = itemgroup;
	}

	// 录入的数据
	private String changeRows;

	public String getChangeRows() {
		return changeRows;
	}

	public void setChangeRows(String changeRows) {
		this.changeRows = changeRows;
	}

	// Fields
	private String id;
	private String name;
	private String item;
	private BigDecimal percent;
	private Double value;
	private String perfdate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getPerfdate() {
		return perfdate;
	}

	public void setPerfdate(String perfdate) {
		this.perfdate = perfdate;
	}

	@Override
	public String toString() {
		return "PerfNum [id=" + id + ", name=" + name + ", item=" + item + ", percent=" + percent + ", value=" + value
				+ ", perfdate=" + perfdate + "]";
	}

}
