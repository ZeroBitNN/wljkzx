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
 * TResources entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RESOURCES", schema = "")

public class TResources implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String url;
	private String description;
	private TResources TResources;
	private Set<TResources> TResourceses = new HashSet<TResources>(0);

	// Constructors

	/** default constructor */
	public TResources() {
	}

	/** minimal constructor */
	public TResources(String id) {
		this.id = id;
	}

	/** full constructor */
	public TResources(String id, TResources TResources, String name, String url, String description,
			Set<TResources> TResourceses) {
		this.id = id;
		this.TResources = TResources;
		this.name = name;
		this.url = url;
		this.description = description;
		this.TResourceses = TResourceses;
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

	public TResources getTResources() {
		return this.TResources;
	}

	public void setTResources(TResources TResources) {
		this.TResources = TResources;
	}

	@Column(name = "NAME", length = 50)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "URL", length = 100)

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "DESCRIPTION", length = 50)

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TResources")

	public Set<TResources> getTResourceses() {
		return this.TResourceses;
	}

	public void setTResourceses(Set<TResources> TResourceses) {
		this.TResourceses = TResourceses;
	}

}