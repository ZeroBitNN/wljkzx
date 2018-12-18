package model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TPerfNum entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PERF_NUM", schema = "")

public class TPerfNum implements java.io.Serializable {

	// Fields

	private String id;
	private TAccount TAccount;
	private TPerfParam TPerfParam;
	private BigDecimal percent;
	private Double value;
	private String perfdate;
	private String itemgroup;

	// Constructors

	/** default constructor */
	public TPerfNum() {
	}

	/** minimal constructor */
	public TPerfNum(String id) {
		this.id = id;
	}

	/** full constructor */
	public TPerfNum(String id, TAccount TAccount, TPerfParam TPerfParam, BigDecimal percent, Double value,
			String perfdate, String itemgroup) {
		this.id = id;
		this.TAccount = TAccount;
		this.TPerfParam = TPerfParam;
		this.percent = percent;
		this.value = value;
		this.perfdate = perfdate;
		this.itemgroup = itemgroup;
	}

	// Property accessors
	@Id

	@Column(name = "ID", nullable = false, length = 36)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NAME")

	public TAccount getTAccount() {
		return this.TAccount;
	}

	public void setTAccount(TAccount TAccount) {
		this.TAccount = TAccount;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM")

	public TPerfParam getTPerfParam() {
		return this.TPerfParam;
	}

	public void setTPerfParam(TPerfParam TPerfParam) {
		this.TPerfParam = TPerfParam;
	}

	@Column(name = "PERCENT", precision = 22, scale = 0)

	public BigDecimal getPercent() {
		return this.percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	@Column(name = "VALUE", precision = 9)

	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Column(name = "PERFDATE", length = 50)

	public String getPerfdate() {
		return this.perfdate;
	}

	public void setPerfdate(String perfdate) {
		this.perfdate = perfdate;
	}

	@Column(name = "ITEMGROUP", length = 50)

	public String getItemgroup() {
		return this.itemgroup;
	}

	public void setItemgroup(String itemgroup) {
		this.itemgroup = itemgroup;
	}

}