package pageModel;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

import model.TPerfParam;

public class PerfParam {

	// 字段
	private String id;
	private String name;
	private BigDecimal percent;
	private Double value;
	private String describe;
	private String type;
	private String pid;
	private String _parentId;

	@JSONField(name = "_parentId")
	public String get_parentId() {
		return _parentId;
	}

	@JSONField(name = "_parentId")
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "PerfParam [id=" + id + ", name=" + name + ", percent=" + percent + ", value=" + value + ", describe="
				+ describe + ", type=" + type + ", pid=" + pid + ", _parentId=" + _parentId + "]";
	}
	
}
