package model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TPerfParam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PERF_PARAM", schema = "")

public class TPerfParam implements java.io.Serializable {

	// Fields

	private String id;
	private TPerfParam TPerfParam;
	private String name;
	private BigDecimal percent;
	private Double value;
	private String describe;
	private String type;
	private Set<TPerfParam> TPerfParams = new HashSet<TPerfParam>(0);
	private Set<TPerfNum> TPerfNums = new HashSet<TPerfNum>(0);

	// Constructors

	/** default constructor */
	public TPerfParam() {
	}

	/** minimal constructor */
	public TPerfParam(String id) {
		this.id = id;
	}

	/** full constructor */
	public TPerfParam(String id, TPerfParam TPerfParam, String name, BigDecimal percent, Double value, String describe,
			String type, Set<TPerfParam> TPerfParams, Set<TPerfNum> TPerfNums) {
		this.id = id;
		this.TPerfParam = TPerfParam;
		this.name = name;
		this.percent = percent;
		this.value = value;
		this.describe = describe;
		this.type = type;
		this.TPerfParams = TPerfParams;
		this.TPerfNums = TPerfNums;
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
	@JoinColumn(name = "PID")

	public TPerfParam getTPerfParam() {
		return this.TPerfParam;
	}

	public void setTPerfParam(TPerfParam TPerfParam) {
		this.TPerfParam = TPerfParam;
	}

	@Column(name = "NAME", length = 100)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Column(name = "DESCRIBE", length = 500)

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "TYPE", length = 500)

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPerfParam")

	public Set<TPerfParam> getTPerfParams() {
		return this.TPerfParams;
	}

	public void setTPerfParams(Set<TPerfParam> TPerfParams) {
		this.TPerfParams = TPerfParams;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPerfParam")

	public Set<TPerfNum> getTPerfNums() {
		return this.TPerfNums;
	}

	public void setTPerfNums(Set<TPerfNum> TPerfNums) {
		this.TPerfNums = TPerfNums;
	}

}