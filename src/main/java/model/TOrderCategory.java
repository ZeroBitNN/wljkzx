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
 * TOrderCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ORDER_CATEGORY", schema = "")

public class TOrderCategory implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Set<TDailywork> TDailyworks = new HashSet<TDailywork>(0);
	private Set<TOrder> TOrders = new HashSet<TOrder>(0);

	// Constructors

	/** default constructor */
	public TOrderCategory() {
	}

	/** minimal constructor */
	public TOrderCategory(String id) {
		this.id = id;
	}

	/** full constructor */
	public TOrderCategory(String id, String name, Set<TDailywork> TDailyworks, Set<TOrder> TOrders) {
		this.id = id;
		this.name = name;
		this.TDailyworks = TDailyworks;
		this.TOrders = TOrders;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TOrderCategory")

	public Set<TDailywork> getTDailyworks() {
		return this.TDailyworks;
	}

	public void setTDailyworks(Set<TDailywork> TDailyworks) {
		this.TDailyworks = TDailyworks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TOrderCategory")

	public Set<TOrder> getTOrders() {
		return this.TOrders;
	}

	public void setTOrders(Set<TOrder> TOrders) {
		this.TOrders = TOrders;
	}

}