package model;

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
 * TDailyworkDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_DAILYWORK_DETAILS", schema = "WLJKZX")

public class TDailyworkDetails implements java.io.Serializable {

	// Fields

	private String id;
	private TDailywork TDailywork;
	private TAccount TAccount;
	private Date dailydate;
	private String status;
	private Date recodtime;
	private String remark;

	// Constructors

	/** default constructor */
	public TDailyworkDetails() {
	}

	/** minimal constructor */
	public TDailyworkDetails(String id) {
		this.id = id;
	}

	/** full constructor */
	public TDailyworkDetails(String id, TDailywork TDailywork, TAccount TAccount, Date dailydate, String status,
			Date recodtime, String remark) {
		this.id = id;
		this.TDailywork = TDailywork;
		this.TAccount = TAccount;
		this.dailydate = dailydate;
		this.status = status;
		this.recodtime = recodtime;
		this.remark = remark;
	}

	// Property accessors
	@Id

	@Column(name = "ID", unique = true, nullable = false, length = 36)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RID")

	public TDailywork getTDailywork() {
		return this.TDailywork;
	}

	public void setTDailywork(TDailywork TDailywork) {
		this.TDailywork = TDailywork;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECODER")

	public TAccount getTAccount() {
		return this.TAccount;
	}

	public void setTAccount(TAccount TAccount) {
		this.TAccount = TAccount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DAILYDATE", length = 7)

	public Date getDailydate() {
		return this.dailydate;
	}

	public void setDailydate(Date dailydate) {
		this.dailydate = dailydate;
	}

	@Column(name = "STATUS", length = 20)

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RECODTIME", length = 7)

	public Date getRecodtime() {
		return this.recodtime;
	}

	public void setRecodtime(Date recodtime) {
		this.recodtime = recodtime;
	}

	@Column(name = "REMARK", length = 1000)

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}