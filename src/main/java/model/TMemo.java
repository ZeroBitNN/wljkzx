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
 * TMemo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_MEMO", schema = "")

public class TMemo implements java.io.Serializable {

	// Fields

	private String id;
	private TAccount TAccountByMemoto;
	private TAccount TAccountByMemofrom;
	private String classes;
	private Date createtime;
	private Date modifytime;
	private String content;
	private String othercontent;
	private String leaveover;
	private String memotype;
	private String status;

	// Constructors

	/** default constructor */
	public TMemo() {
	}

	/** minimal constructor */
	public TMemo(String id) {
		this.id = id;
	}

	/** full constructor */
	public TMemo(String id, TAccount TAccountByMemoto, TAccount TAccountByMemofrom, String classes, Date createtime,
			Date modifytime, String content, String othercontent, String leaveover, String memotype, String status) {
		this.id = id;
		this.TAccountByMemoto = TAccountByMemoto;
		this.TAccountByMemofrom = TAccountByMemofrom;
		this.classes = classes;
		this.createtime = createtime;
		this.modifytime = modifytime;
		this.content = content;
		this.othercontent = othercontent;
		this.leaveover = leaveover;
		this.memotype = memotype;
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
	@JoinColumn(name = "MEMOTO")

	public TAccount getTAccountByMemoto() {
		return this.TAccountByMemoto;
	}

	public void setTAccountByMemoto(TAccount TAccountByMemoto) {
		this.TAccountByMemoto = TAccountByMemoto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMOFROM")

	public TAccount getTAccountByMemofrom() {
		return this.TAccountByMemofrom;
	}

	public void setTAccountByMemofrom(TAccount TAccountByMemofrom) {
		this.TAccountByMemofrom = TAccountByMemofrom;
	}

	@Column(name = "CLASSES", length = 10)

	public String getClasses() {
		return this.classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
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

	@Column(name = "CONTENT", length = 1000)

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "OTHERCONTENT", length = 1000)

	public String getOthercontent() {
		return this.othercontent;
	}

	public void setOthercontent(String othercontent) {
		this.othercontent = othercontent;
	}

	@Column(name = "LEAVEOVER", length = 1000)

	public String getLeaveover() {
		return this.leaveover;
	}

	public void setLeaveover(String leaveover) {
		this.leaveover = leaveover;
	}

	@Column(name = "MEMOTYPE", length = 20)

	public String getMemotype() {
		return this.memotype;
	}

	public void setMemotype(String memotype) {
		this.memotype = memotype;
	}

	@Column(name = "STATUS", length = 10)

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}