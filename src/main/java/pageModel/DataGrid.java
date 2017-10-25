package pageModel;

import java.util.ArrayList;
import java.util.List;

public class DataGrid<T> {
	private Long total = 0L;
	private List<T> rows = new ArrayList<T>();
	private List<T> footer = new ArrayList<T>();

	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	
}
