package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TLinks entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_LINKS", schema = "")

public class TLinks implements java.io.Serializable {

	// Fields

	private String id;
	private String text;
	private String url;
	private String category;

	// Constructors

	/** default constructor */
	public TLinks() {
	}

	/** minimal constructor */
	public TLinks(String id) {
		this.id = id;
	}

	/** full constructor */
	public TLinks(String id, String text, String url, String category) {
		this.id = id;
		this.text = text;
		this.url = url;
		this.category = category;
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

	@Column(name = "TEXT", length = 100)

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "URL")

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "CATEGORY", length = 100)

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}