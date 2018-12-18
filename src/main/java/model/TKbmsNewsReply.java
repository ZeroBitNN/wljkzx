package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TKbmsNewsReply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_KBMS_NEWS_REPLY", schema = "")

public class TKbmsNewsReply implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String content;
	private String author;
	private Date replytime;
	private String pid;

	// Constructors

	/** default constructor */
	public TKbmsNewsReply() {
	}

	/** minimal constructor */
	public TKbmsNewsReply(String id) {
		this.id = id;
	}

	/** full constructor */
	public TKbmsNewsReply(String id, String title, String content, String author, Date replytime, String pid) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.replytime = replytime;
		this.pid = pid;
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

	@Column(name = "TITLE")

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Column(name = "CONTENT")

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "AUTHOR")

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REPLYTIME", length = 7)

	public Date getReplytime() {
		return this.replytime;
	}

	public void setReplytime(Date replytime) {
		this.replytime = replytime;
	}

	@Column(name = "PID", length = 36)

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}