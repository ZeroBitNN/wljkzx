package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * TNetmanage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_NETMANAGE", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = "NAME") )
@DynamicInsert(true)
@DynamicUpdate(true)
public class TNetmanage implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String link;
	private String username;
	private String pwd;

	// Constructors

	/** default constructor */
	public TNetmanage() {
	}

	/** minimal constructor */
	public TNetmanage(String id) {
		this.id = id;
	}

	/** full constructor */
	public TNetmanage(String id, String name, String link, String username, String pwd) {
		this.id = id;
		this.name = name;
		this.link = link;
		this.username = username;
		this.pwd = pwd;
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

	@Column(name = "NAME", length = 500)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LINK", length = 250)

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "USERNAME", length = 100)

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PWD", length = 50)

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}