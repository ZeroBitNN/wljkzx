package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * TKbmsUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_KBMS_USER", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME") )

public class TKbmsUser implements java.io.Serializable {

	// Fields

	private String id;
	private String username;
	private String pwd;
	private Date createtime;
	private Date modifytime;

	// Constructors

	/** default constructor */
	public TKbmsUser() {
	}

	/** minimal constructor */
	public TKbmsUser(String id, String username, String pwd) {
		this.id = id;
		this.username = username;
		this.pwd = pwd;
	}

	/** full constructor */
	public TKbmsUser(String id, String username, String pwd, Date createtime, Date modifytime) {
		this.id = id;
		this.username = username;
		this.pwd = pwd;
		this.createtime = createtime;
		this.modifytime = modifytime;
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

	@Column(name = "USERNAME", unique = true, nullable = false, length = 100)

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PWD", nullable = false, length = 100)

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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

}