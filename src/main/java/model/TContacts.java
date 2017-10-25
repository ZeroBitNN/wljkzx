package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TContacts entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CONTACTS", schema = "")

public class TContacts implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String phonenumber;
	private String dept;

	// Constructors

	/** default constructor */
	public TContacts() {
	}

	/** minimal constructor */
	public TContacts(String id) {
		this.id = id;
	}

	/** full constructor */
	public TContacts(String id, String name, String phonenumber, String dept) {
		this.id = id;
		this.name = name;
		this.phonenumber = phonenumber;
		this.dept = dept;
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

	@Column(name = "NAME", length = 100)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PHONENUMBER")

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Column(name = "DEPT", length = 100)

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}