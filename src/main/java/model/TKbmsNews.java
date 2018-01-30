package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TKbmsNews entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_KBMS_NEWS", schema = "")

public class TKbmsNews implements java.io.Serializable {

	// Fields

	private String id;
	private TKbmsCategory TKbmsCategory;
	private String title;
	private String author;
	private String content;
	private Date createtime;
	private Date modifytime;
	private String attchement;

	// Constructors

	/** default constructor */
	public TKbmsNews() {
	}

	/** minimal constructor */
	public TKbmsNews(String id) {
		this.id = id;
	}

	/** full constructor */
	public TKbmsNews(String id, TKbmsCategory TKbmsCategory, String title, String author, String content,
			Date createtime, Date modifytime, String attchement) {
		this.id = id;
		this.TKbmsCategory = TKbmsCategory;
		this.title = title;
		this.author = author;
		this.content = content;
		this.createtime = createtime;
		this.modifytime = modifytime;
		this.attchement = attchement;
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
	@JoinColumn(name = "CATEGORYID")

	public TKbmsCategory getTKbmsCategory() {
		return this.TKbmsCategory;
	}

	public void setTKbmsCategory(TKbmsCategory TKbmsCategory) {
		this.TKbmsCategory = TKbmsCategory;
	}

	@Column(name = "TITLE")

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "AUTHOR")

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Lob
	@Column(name = "CONTENT")

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Column(name = "ATTCHEMENT", length = 4000)

	public String getAttchement() {
		return this.attchement;
	}

	public void setAttchement(String attchement) {
		this.attchement = attchement;
	}

}