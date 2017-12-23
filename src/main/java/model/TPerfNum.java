package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private Date perfdate;

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
			Date perfdate) {
		this.id = id;
		this.TAccount = TAccount;
		this.TPerfParam = TPerfParam;
		this.percent = percent;
		this.value = value;
		this.perfdate = perfdate;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "PERFDATE", length = 7)

	public Date getPerfdate() {
		return this.perfdate;
	}

	public void setPerfdate(Date perfdate) {
		this.perfdate = perfdate;
	}

}