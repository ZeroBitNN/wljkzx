package model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TRoles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ROLES", schema = "")

public class TRoles implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String resourcesIds;
	private Set<TAccount> TAccounts = new HashSet<TAccount>(0);

	// Constructors

	/** default constructor */
	public TRoles() {
	}

	/** minimal constructor */
	public TRoles(String id) {
		this.id = id;
	}

	/** full constructor */
	public TRoles(String id, String name, String resourcesIds, Set<TAccount> TAccounts) {
		this.id = id;
		this.name = name;
		this.resourcesIds = resourcesIds;
		this.TAccounts = TAccounts;
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

	@Column(name = "NAME", length = 50)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "RESOURCES_IDS", length = 800)

	public String getResourcesIds() {
		return this.resourcesIds;
	}

	public void setResourcesIds(String resourcesIds) {
		this.resourcesIds = resourcesIds;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TRoles")

	public Set<TAccount> getTAccounts() {
		return this.TAccounts;
	}

	public void setTAccounts(Set<TAccount> TAccounts) {
		this.TAccounts = TAccounts;
	}

}