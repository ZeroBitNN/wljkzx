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
 * TWorkrecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_WORKRECORD", schema = "")

public class TWorkrecord implements java.io.Serializable {

	// Fields

	private String id;
	private TAccount TAccountByInputer;
	private TAccount TAccountByHandler;
	private String proposer;
	private String faultnumber;
	private String describe;
	private String situation;
	private Date createtime;
	private Date modifytime;
	private String status;

	// Constructors

	/** default constructor */
	public TWorkrecord() {
	}

	/** minimal constructor */
	public TWorkrecord(String id) {
		this.id = id;
	}

	/** full constructor */
	public TWorkrecord(String id, TAccount TAccountByInputer, TAccount TAccountByHandler, String proposer,
			String faultnumber, String describe, String situation, Date createtime, Date modifytime, String status) {
		this.id = id;
		this.TAccountByInputer = TAccountByInputer;
		this.TAccountByHandler = TAccountByHandler;
		this.proposer = proposer;
		this.faultnumber = faultnumber;
		this.describe = describe;
		this.situation = situation;
		this.createtime = createtime;
		this.modifytime = modifytime;
		this.status = status;
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
	@JoinColumn(name = "INPUTER")

	public TAccount getTAccountByInputer() {
		return this.TAccountByInputer;
	}

	public void setTAccountByInputer(TAccount TAccountByInputer) {
		this.TAccountByInputer = TAccountByInputer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HANDLER")

	public TAccount getTAccountByHandler() {
		return this.TAccountByHandler;
	}

	public void setTAccountByHandler(TAccount TAccountByHandler) {
		this.TAccountByHandler = TAccountByHandler;
	}

	@Column(name = "PROPOSER", length = 50)

	public String getProposer() {
		return this.proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	@Column(name = "FAULTNUMBER", length = 200)

	public String getFaultnumber() {
		return this.faultnumber;
	}

	public void setFaultnumber(String faultnumber) {
		this.faultnumber = faultnumber;
	}

	@Column(name = "DESCRIBE", length = 1000)

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "SITUATION", length = 1000)

	public String getSituation() {
		return this.situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", length = 7)

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYTIME", length = 7)

	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "STATUS", length = 10)

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}