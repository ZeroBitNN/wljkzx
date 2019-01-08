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
	private String perfdate;
	private BigDecimal isperf;
	private Integer item1sum;
	private Integer item2sum;
	private Integer item3sum;
	private Integer item4sum;

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
			Double otheritem, BigDecimal zzhz, String ranking, Double grjx, Double jjjx, Double kf, String perfdate,
			BigDecimal isperf, Integer item1sum, Integer item2sum, Integer item3sum, Integer item4sum) {
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
		this.isperf = isperf;
		this.item1sum = item1sum;
		this.item2sum = item2sum;
		this.item3sum = item3sum;
		this.item4sum = item4sum;
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

	@Column(name = "ITEM1", precision = 9, scale = 2)

	public Double getItem1() {
		return this.item1;
	}

	public void setItem1(Double item1) {
		this.item1 = item1;
	}

	@Column(name = "ITEM2", precision = 9, scale = 2)

	public Double getItem2() {
		return this.item2;
	}

	public void setItem2(Double item2) {
		this.item2 = item2;
	}

	@Column(name = "ITEM3", precision = 9, scale = 2)

	public Double getItem3() {
		return this.item3;
	}

	public void setItem3(Double item3) {
		this.item3 = item3;
	}

	@Column(name = "ITEM4", precision = 9, scale = 2)

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

	@Column(name = "OTHERITEM", precision = 9, scale = 2)

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

	@Column(name = "PERFDATE", length = 50)

	public String getPerfdate() {
		return this.perfdate;
	}

	public void setPerfdate(String perfdate) {
		this.perfdate = perfdate;
	}

	@Column(name = "ISPERF", precision = 22, scale = 0)

	public BigDecimal getIsperf() {
		return this.isperf;
	}

	public void setIsperf(BigDecimal isperf) {
		this.isperf = isperf;
	}

	@Column(name = "ITEM1SUM", precision = 7, scale = 0)

	public Integer getItem1sum() {
		return this.item1sum;
	}

	public void setItem1sum(Integer item1sum) {
		this.item1sum = item1sum;
	}

	@Column(name = "ITEM2SUM", precision = 7, scale = 0)

	public Integer getItem2sum() {
		return this.item2sum;
	}

	public void setItem2sum(Integer item2sum) {
		this.item2sum = item2sum;
	}

	@Column(name = "ITEM3SUM", precision = 7, scale = 0)

	public Integer getItem3sum() {
		return this.item3sum;
	}

	public void setItem3sum(Integer item3sum) {
		this.item3sum = item3sum;
	}

	@Column(name = "ITEM4SUM", precision = 7, scale = 0)

	public Integer getItem4sum() {
		return this.item4sum;
	}

	public void setItem4sum(Integer item4sum) {
		this.item4sum = item4sum;
	}

}