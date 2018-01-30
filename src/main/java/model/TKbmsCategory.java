package model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TKbmsCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_KBMS_CATEGORY", schema = "")

public class TKbmsCategory implements java.io.Serializable {

	// Fields

	private String id;
	private TKbmsCategory TKbmsCategory;
	private String text;
	private String iconcls;
	private String url;
	private Set<TKbmsCategory> TKbmsCategories = new HashSet<TKbmsCategory>(0);
	private Set<TKbmsNews> TKbmsNewses = new HashSet<TKbmsNews>(0);

	// Constructors

	/** default constructor */
	public TKbmsCategory() {
	}

	/** minimal constructor */
	public TKbmsCategory(String id) {
		this.id = id;
	}

	/** full constructor */
	public TKbmsCategory(String id, TKbmsCategory TKbmsCategory, String text, String iconcls, String url,
			Set<TKbmsCategory> TKbmsCategories, Set<TKbmsNews> TKbmsNewses) {
		this.id = id;
		this.TKbmsCategory = TKbmsCategory;
		this.text = text;
		this.iconcls = iconcls;
		this.url = url;
		this.TKbmsCategories = TKbmsCategories;
		this.TKbmsNewses = TKbmsNewses;
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
	@JoinColumn(name = "PID")

	public TKbmsCategory getTKbmsCategory() {
		return this.TKbmsCategory;
	}

	public void setTKbmsCategory(TKbmsCategory TKbmsCategory) {
		this.TKbmsCategory = TKbmsCategory;
	}

	@Column(name = "TEXT", length = 100)

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "ICONCLS", length = 150)

	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "URL", length = 200)

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TKbmsCategory")

	public Set<TKbmsCategory> getTKbmsCategories() {
		return this.TKbmsCategories;
	}

	public void setTKbmsCategories(Set<TKbmsCategory> TKbmsCategories) {
		this.TKbmsCategories = TKbmsCategories;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TKbmsCategory")

	public Set<TKbmsNews> getTKbmsNewses() {
		return this.TKbmsNewses;
	}

	public void setTKbmsNewses(Set<TKbmsNews> TKbmsNewses) {
		this.TKbmsNewses = TKbmsNewses;
	}

}