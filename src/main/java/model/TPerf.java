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
 * TPerf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PERF", schema = "")

public class TPerf implements java.io.Serializable {

	// Fields

	private String id;
	private TAccount TAccount;
	private Double item1;
	private Double item2;
	private Double item3;
	private Double item4;
	private BigDecimal gdfz;
	private Double otheritem;
	private BigDecimal zzhz;
	private String ranking;
	private Double grjx;
	private Double jjjx;
	private Double kf;
	private Date perfdate;

	// Constructors

	/** default constructor */
	public TPerf() {
	}

	/** minimal constructor */
	public TPerf(String id) {
		this.id = id;
	}

	/** full constructor */
	public TPerf(String id, TAccount TAccount, Double item1, Double item2, Double item3, Double item4, BigDecimal gdfz,
			Double otheritem, BigDecimal zzhz, String ranking, Double grjx, Double jjjx, Double kf, Date perfdate) {
		this.id = id;
		this.TAccount = TAccount;
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;
		this.gdfz = gdfz;
		this.otheritem = otheritem;
		this.zzhz = zzhz;
		this.ranking = ranking;
		this.grjx = grjx;
		this.jjjx = jjjx;
		this.kf = kf;
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

	@Column(name = "ITEM1", precision = 8, scale = 1)

	public Double getItem1() {
		return this.item1;
	}

	public void setItem1(Double item1) {
		this.item1 = item1;
	}

	@Column(name = "ITEM2", precision = 8, scale = 1)

	public Double getItem2() {
		return this.item2;
	}

	public void setItem2(Double item2) {
		this.item2 = item2;
	}

	@Column(name = "ITEM3", precision = 8, scale = 1)

	public Double getItem3() {
		return this.item3;
	}

	public void setItem3(Double item3) {
		this.item3 = item3;
	}

	@Column(name = "ITEM4", precision = 8, scale = 1)

	public Double getItem4() {
		return this.item4;
	}

	public void setItem4(Double item4) {
		this.item4 = item4;
	}

	@Column(name = "GDFZ", precision = 22, scale = 0)

	public BigDecimal getGdfz() {
		return this.gdfz;
	}

	public void setGdfz(BigDecimal gdfz) {
		this.gdfz = gdfz;
	}

	@Column(name = "OTHERITEM", precision = 8, scale = 1)

	public Double getOtheritem() {
		return this.otheritem;
	}

	public void setOtheritem(Double otheritem) {
		this.otheritem = otheritem;
	}

	@Column(name = "ZZHZ", precision = 22, scale = 0)

	public BigDecimal getZzhz() {
		return this.zzhz;
	}

	public void setZzhz(BigDecimal zzhz) {
		this.zzhz = zzhz;
	}

	@Column(name = "RANKING", length = 10)

	public String getRanking() {
		return this.ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	@Column(name = "GRJX", precision = 9)

	public Double getGrjx() {
		return this.grjx;
	}

	public void setGrjx(Double grjx) {
		this.grjx = grjx;
	}

	@Column(name = "JJJX", precision = 9)

	public Double getJjjx() {
		return this.jjjx;
	}

	public void setJjjx(Double jjjx) {
		this.jjjx = jjjx;
	}

	@Column(name = "KF", precision = 9)

	public Double getKf() {
		return this.kf;
	}

	public void setKf(Double kf) {
		this.kf = kf;
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