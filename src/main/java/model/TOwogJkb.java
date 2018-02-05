package model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TOwogJkb entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_OWOG_JKB", schema = "")

public class TOwogJkb implements java.io.Serializable {

	// Fields

	private String id;
	private BigDecimal zb11;
	private BigDecimal zb12;
	private BigDecimal zb21;
	private BigDecimal zb22;
	private BigDecimal zb31;
	private BigDecimal zb32;
	private BigDecimal zb41;
	private BigDecimal zb42;
	private BigDecimal ldpf;
	private BigDecimal qzzbsum;
	private BigDecimal jf1;
	private BigDecimal jf2;
	private BigDecimal jf3;
	private BigDecimal jf4;
	private BigDecimal jf5;
	private BigDecimal jf6;
	private BigDecimal allsum;
	private String ypfj1;
	private String ypfj2;
	private String ypfj3;
	private String ypfj4;
	private String ypfj5;
	private String name;
	private String rangetime;
	private BigDecimal ranking;

	// Constructors

	/** default constructor */
	public TOwogJkb() {
	}

	/** minimal constructor */
	public TOwogJkb(String id) {
		this.id = id;
	}

	/** full constructor */
	public TOwogJkb(String id, BigDecimal zb11, BigDecimal zb12, BigDecimal zb21, BigDecimal zb22, BigDecimal zb31,
			BigDecimal zb32, BigDecimal zb41, BigDecimal zb42, BigDecimal ldpf, BigDecimal qzzbsum, BigDecimal jf1,
			BigDecimal jf2, BigDecimal jf3, BigDecimal jf4, BigDecimal jf5, BigDecimal jf6, BigDecimal allsum,
			String ypfj1, String ypfj2, String ypfj3, String ypfj4, String ypfj5, String name, String rangetime,
			BigDecimal ranking) {
		this.id = id;
		this.zb11 = zb11;
		this.zb12 = zb12;
		this.zb21 = zb21;
		this.zb22 = zb22;
		this.zb31 = zb31;
		this.zb32 = zb32;
		this.zb41 = zb41;
		this.zb42 = zb42;
		this.ldpf = ldpf;
		this.qzzbsum = qzzbsum;
		this.jf1 = jf1;
		this.jf2 = jf2;
		this.jf3 = jf3;
		this.jf4 = jf4;
		this.jf5 = jf5;
		this.jf6 = jf6;
		this.allsum = allsum;
		this.ypfj1 = ypfj1;
		this.ypfj2 = ypfj2;
		this.ypfj3 = ypfj3;
		this.ypfj4 = ypfj4;
		this.ypfj5 = ypfj5;
		this.name = name;
		this.rangetime = rangetime;
		this.ranking = ranking;
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

	@Column(name = "ZB11", precision = 22, scale = 0)

	public BigDecimal getZb11() {
		return this.zb11;
	}

	public void setZb11(BigDecimal zb11) {
		this.zb11 = zb11;
	}

	@Column(name = "ZB12", precision = 22, scale = 0)

	public BigDecimal getZb12() {
		return this.zb12;
	}

	public void setZb12(BigDecimal zb12) {
		this.zb12 = zb12;
	}

	@Column(name = "ZB21", precision = 22, scale = 0)

	public BigDecimal getZb21() {
		return this.zb21;
	}

	public void setZb21(BigDecimal zb21) {
		this.zb21 = zb21;
	}

	@Column(name = "ZB22", precision = 22, scale = 0)

	public BigDecimal getZb22() {
		return this.zb22;
	}

	public void setZb22(BigDecimal zb22) {
		this.zb22 = zb22;
	}

	@Column(name = "ZB31", precision = 22, scale = 0)

	public BigDecimal getZb31() {
		return this.zb31;
	}

	public void setZb31(BigDecimal zb31) {
		this.zb31 = zb31;
	}

	@Column(name = "ZB32", precision = 22, scale = 0)

	public BigDecimal getZb32() {
		return this.zb32;
	}

	public void setZb32(BigDecimal zb32) {
		this.zb32 = zb32;
	}

	@Column(name = "ZB41", precision = 22, scale = 0)

	public BigDecimal getZb41() {
		return this.zb41;
	}

	public void setZb41(BigDecimal zb41) {
		this.zb41 = zb41;
	}

	@Column(name = "ZB42", precision = 22, scale = 0)

	public BigDecimal getZb42() {
		return this.zb42;
	}

	public void setZb42(BigDecimal zb42) {
		this.zb42 = zb42;
	}

	@Column(name = "LDPF", precision = 22, scale = 0)

	public BigDecimal getLdpf() {
		return this.ldpf;
	}

	public void setLdpf(BigDecimal ldpf) {
		this.ldpf = ldpf;
	}

	@Column(name = "QZZBSUM", precision = 22, scale = 0)

	public BigDecimal getQzzbsum() {
		return this.qzzbsum;
	}

	public void setQzzbsum(BigDecimal qzzbsum) {
		this.qzzbsum = qzzbsum;
	}

	@Column(name = "JF1", precision = 22, scale = 0)

	public BigDecimal getJf1() {
		return this.jf1;
	}

	public void setJf1(BigDecimal jf1) {
		this.jf1 = jf1;
	}

	@Column(name = "JF2", precision = 22, scale = 0)

	public BigDecimal getJf2() {
		return this.jf2;
	}

	public void setJf2(BigDecimal jf2) {
		this.jf2 = jf2;
	}

	@Column(name = "JF3", precision = 22, scale = 0)

	public BigDecimal getJf3() {
		return this.jf3;
	}

	public void setJf3(BigDecimal jf3) {
		this.jf3 = jf3;
	}

	@Column(name = "JF4", precision = 22, scale = 0)

	public BigDecimal getJf4() {
		return this.jf4;
	}

	public void setJf4(BigDecimal jf4) {
		this.jf4 = jf4;
	}

	@Column(name = "JF5", precision = 22, scale = 0)

	public BigDecimal getJf5() {
		return this.jf5;
	}

	public void setJf5(BigDecimal jf5) {
		this.jf5 = jf5;
	}

	@Column(name = "JF6", precision = 22, scale = 0)

	public BigDecimal getJf6() {
		return this.jf6;
	}

	public void setJf6(BigDecimal jf6) {
		this.jf6 = jf6;
	}

	@Column(name = "ALLSUM", precision = 22, scale = 0)

	public BigDecimal getAllsum() {
		return this.allsum;
	}

	public void setAllsum(BigDecimal allsum) {
		this.allsum = allsum;
	}

	@Column(name = "YPFJ1", length = 50)

	public String getYpfj1() {
		return this.ypfj1;
	}

	public void setYpfj1(String ypfj1) {
		this.ypfj1 = ypfj1;
	}

	@Column(name = "YPFJ2", length = 50)

	public String getYpfj2() {
		return this.ypfj2;
	}

	public void setYpfj2(String ypfj2) {
		this.ypfj2 = ypfj2;
	}

	@Column(name = "YPFJ3", length = 50)

	public String getYpfj3() {
		return this.ypfj3;
	}

	public void setYpfj3(String ypfj3) {
		this.ypfj3 = ypfj3;
	}

	@Column(name = "YPFJ4", length = 50)

	public String getYpfj4() {
		return this.ypfj4;
	}

	public void setYpfj4(String ypfj4) {
		this.ypfj4 = ypfj4;
	}

	@Column(name = "YPFJ5", length = 50)

	public String getYpfj5() {
		return this.ypfj5;
	}

	public void setYpfj5(String ypfj5) {
		this.ypfj5 = ypfj5;
	}

	@Column(name = "NAME", length = 36)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "RANGETIME", length = 50)

	public String getRangetime() {
		return this.rangetime;
	}

	public void setRangetime(String rangetime) {
		this.rangetime = rangetime;
	}

	@Column(name = "RANKING", precision = 22, scale = 0)

	public BigDecimal getRanking() {
		return this.ranking;
	}

	public void setRanking(BigDecimal ranking) {
		this.ranking = ranking;
	}

}