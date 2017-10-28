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
@Table(name = "T_WORKRECORD", schema = "WLJKZX")

public class TWorkrecord implements java.io.Serializable {

	// Fields

	private String id;
	private TAccount TAccountByInputer;
	private TAccount TAccountByHandler;
	private Date createtime;
	private String describe;
	private String faultnumber;
	private Date modifytime;
	private String proposer;
	private String situation;
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
	public TWorkrecord(String id, TAccount TAccountByInputer, TAccount TAccountByHandler, Date createtime,
			String describe, String faultnumber, Date modifytime, String proposer, String situation, String status) {
		this.id = id;
		this.TAccountByInputer = TAccountByInputer;
		this.TAccountByHandler = TAccountByHandler;
		this.createtime = createtime;
		this.describe = describe;
		this.faultnumber = faultnumber;
		this.modifytime = modifytime;
		this.proposer = proposer;
		this.situation = situation;
		this.status = status;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", length = 7)

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "DESCRIBE", length = 1000)

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "FAULTNUMBER", length = 200)

	public String getFaultnumber() {
		return this.faultnumber;
	}

	public void setFaultnumber(String faultnumber) {
		this.faultnumber = faultnumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFYTIME", length = 7)

	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "PROPOSER", length = 50)

	public String getProposer() {
		return this.proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	@Column(name = "SITUATION", length = 1000)

	public String getSituation() {
		return this.situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	@Column(name = "STATUS", length = 10)

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}