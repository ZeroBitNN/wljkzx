package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TAttachment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ATTACHMENT", schema = "")

public class TAttachment implements java.io.Serializable {

	// Fields

	private String id;
	private String filename;
	private String account;
	private Date uploadtime;
	private String describe;
	private String relatedid;
	private String url;

	// Constructors

	/** default constructor */
	public TAttachment() {
	}

	/** minimal constructor */
	public TAttachment(String id) {
		this.id = id;
	}

	/** full constructor */
	public TAttachment(String id, String filename, String account, Date uploadtime, String describe, String relatedid, String url) {
		this.id = id;
		this.filename = filename;
		this.account = account;
		this.uploadtime = uploadtime;
		this.describe = describe;
		this.relatedid = relatedid;
		this.url = url;
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

	@Column(name = "FILENAME", length = 100)

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "ACCOUNT", length = 36)

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOADTIME", length = 7)

	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	@Column(name = "DESCRIBE", length = 100)

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "RELATEDID", length = 36)

	public String getRelatedid() {
		return this.relatedid;
	}

	public void setRelatedid(String relatedid) {
		this.relatedid = relatedid;
	}

	@Column(name = "URL", length = 200)

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}