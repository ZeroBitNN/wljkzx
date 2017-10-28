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
 * TDemand entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_DEMAND", schema = "WLJKZX")

public class TDemand implements java.io.Serializable {

	// Fields

	private String id;
	private TAccount TAccount;
	private String content;
	private Date createtime;
	private Date modifytime;
	private String isshow;

	// Constructors

	/** default constructor */
	public TDemand() {
	}

	/** minimal constructor */
	public TDemand(String id) {
		this.id = id;
	}

	/** full constructor */
	public TDemand(String id, TAccount TAccount, String content, Date createtime, Date modifytime, String isshow) {
		this.id = id;
		this.TAccount = TAccount;
		this.content = content;
		this.createtime = createtime;
		this.modifytime = modifytime;
		this.isshow = isshow;
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
	@JoinColumn(name = "RECORDER")

	public TAccount getTAccount() {
		return this.TAccount;
	}

	public void setTAccount(TAccount TAccount) {
		this.TAccount = TAccount;
	}

	@Column(name = "CONTENT", length = 1000)

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", length = 7)

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFYTIME", length = 7)

	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "ISSHOW", length = 10)

	public String getIsshow() {
		return this.isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

}