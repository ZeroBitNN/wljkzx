package model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TDailywork entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_DAILYWORK", schema = "")

public class TDailywork implements java.io.Serializable {

	// Fields

	private String id;
	private TAccount TAccount;
	private TOrderCategory TOrderCategory;
	private String timepoint;
	private String content;
	private Date releasetime;
	private Set<TDailyworkDetails> TDailyworkDetailses = new HashSet<TDailyworkDetails>(0);

	// Constructors

	/** default constructor */
	public TDailywork() {
	}

	/** minimal constructor */
	public TDailywork(String id) {
		this.id = id;
	}

	/** full constructor */
	public TDailywork(String id, TAccount TAccount, TOrderCategory TOrderCategory, String timepoint, String content,
			Date releasetime, Set<TDailyworkDetails> TDailyworkDetailses) {
		this.id = id;
		this.TAccount = TAccount;
		this.TOrderCategory = TOrderCategory;
		this.timepoint = timepoint;
		this.content = content;
		this.releasetime = releasetime;
		this.TDailyworkDetailses = TDailyworkDetailses;
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
	@JoinColumn(name = "PUBLISHER")

	public TAccount getTAccount() {
		return this.TAccount;
	}

	public void setTAccount(TAccount TAccount) {
		this.TAccount = TAccount;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY")

	public TOrderCategory getTOrderCategory() {
		return this.TOrderCategory;
	}

	public void setTOrderCategory(TOrderCategory TOrderCategory) {
		this.TOrderCategory = TOrderCategory;
	}

	@Column(name = "TIMEPOINT", length = 20)

	public String getTimepoint() {
		return this.timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}

	@Column(name = "CONTENT", length = 2000)

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RELEASETIME", length = 7)

	public Date getReleasetime() {
		return this.releasetime;
	}

	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TDailywork")

	public Set<TDailyworkDetails> getTDailyworkDetailses() {
		return this.TDailyworkDetailses;
	}

	public void setTDailyworkDetailses(Set<TDailyworkDetails> TDailyworkDetailses) {
		this.TDailyworkDetailses = TDailyworkDetailses;
	}

}