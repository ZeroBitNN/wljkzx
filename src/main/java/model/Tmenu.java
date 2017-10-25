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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Tmenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_MENU", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tmenu implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1487445266890046121L;
	
	private String id;
	private Tmenu tmenu;
	private String text;
	private String iconCls;
	private String url;
	private Set<Tmenu> tmenus = new HashSet<Tmenu>(0);

	// Constructors

	/** default constructor */
	public Tmenu() {
	}

	/** minimal constructor */
	public Tmenu(String id) {
		this.id = id;
	}

	/** full constructor */
	public Tmenu(String id, Tmenu tmenu, String text, String iconCls, String url, Set<Tmenu> tmenus) {
		this.id = id;
		this.tmenu = tmenu;
		this.text = text;
		this.iconCls = iconCls;
		this.url = url;
		this.tmenus = tmenus;
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

	public Tmenu getTmenu() {
		return this.tmenu;
	}

	public void setTmenu(Tmenu tmenu) {
		this.tmenu = tmenu;
	}

	@Column(name = "TEXT", length = 100)

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "ICONCLS", length = 150)

	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Column(name = "URL", length = 200)

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tmenu")

	public Set<Tmenu> getTmenus() {
		return this.tmenus;
	}

	public void setTmenus(Set<Tmenu> tmenus) {
		this.tmenus = tmenus;
	}

}