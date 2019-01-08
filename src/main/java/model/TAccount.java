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
import javax.persistence.UniqueConstraint;

/**
 * TAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ACCOUNT", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME") )

public class TAccount implements java.io.Serializable {

	// Fields

	private String id;
	private TRoles TRoles;
	private String createtime;
	private String modifytime;
	private String pwd;
	private String username;
	private Set<TMemo> TMemosForMemoto = new HashSet<TMemo>(0);
	private Set<TOrderNotice> TOrderNotices = new HashSet<TOrderNotice>(0);
	private Set<TDailyworkDetails> TDailyworkDetailses = new HashSet<TDailyworkDetails>(0);
	private Set<TOrder> TOrdersForModifyid = new HashSet<TOrder>(0);
	private Set<TDemand> TDemands = new HashSet<TDemand>(0);
	private Set<TPerfNum> TPerfNums = new HashSet<TPerfNum>(0);
	private Set<TMemo> TMemosForMemofrom = new HashSet<TMemo>(0);
	private Set<TOrder> TOrdersForAuthorid = new HashSet<TOrder>(0);
	private Set<TPerf> TPerfs = new HashSet<TPerf>(0);
	private Set<TWorkrecord> TWorkrecordsForInputer = new HashSet<TWorkrecord>(0);
	private Set<TWorkrecord> TWorkrecordsForHandler = new HashSet<TWorkrecord>(0);
	private Set<TDailywork> TDailyworks = new HashSet<TDailywork>(0);

	// Constructors

	/** default constructor */
	public TAccount() {
	}

	/** minimal constructor */
	public TAccount(String id, String pwd, String username) {
		this.id = id;
		this.pwd = pwd;
		this.username = username;
	}

	/** full constructor */
	public TAccount(String id, TRoles TRoles, String createtime, String modifytime, String pwd, String username,
			Set<TMemo> TMemosForMemoto, Set<TOrderNotice> TOrderNotices, Set<TDailyworkDetails> TDailyworkDetailses,
			Set<TOrder> TOrdersForModifyid, Set<TDemand> TDemands, Set<TPerfNum> TPerfNums,
			Set<TMemo> TMemosForMemofrom, Set<TOrder> TOrdersForAuthorid, Set<TPerf> TPerfs,
			Set<TWorkrecord> TWorkrecordsForInputer, Set<TWorkrecord> TWorkrecordsForHandler,
			Set<TDailywork> TDailyworks) {
		this.id = id;
		this.TRoles = TRoles;
		this.createtime = createtime;
		this.modifytime = modifytime;
		this.pwd = pwd;
		this.username = username;
		this.TMemosForMemoto = TMemosForMemoto;
		this.TOrderNotices = TOrderNotices;
		this.TDailyworkDetailses = TDailyworkDetailses;
		this.TOrdersForModifyid = TOrdersForModifyid;
		this.TDemands = TDemands;
		this.TPerfNums = TPerfNums;
		this.TMemosForMemofrom = TMemosForMemofrom;
		this.TOrdersForAuthorid = TOrdersForAuthorid;
		this.TPerfs = TPerfs;
		this.TWorkrecordsForInputer = TWorkrecordsForInputer;
		this.TWorkrecordsForHandler = TWorkrecordsForHandler;
		this.TDailyworks = TDailyworks;
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
	@JoinColumn(name = "ROLES_ID")

	public TRoles getTRoles() {
		return this.TRoles;
	}

	public void setTRoles(TRoles TRoles) {
		this.TRoles = TRoles;
	}

	@Column(name = "CREATETIME")

	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "MODIFYTIME")

	public String getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "PWD", nullable = false, length = 50)

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "USERNAME", unique = true, nullable = false, length = 36)

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccountByMemoto")

	public Set<TMemo> getTMemosForMemoto() {
		return this.TMemosForMemoto;
	}

	public void setTMemosForMemoto(Set<TMemo> TMemosForMemoto) {
		this.TMemosForMemoto = TMemosForMemoto;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccount")

	public Set<TOrderNotice> getTOrderNotices() {
		return this.TOrderNotices;
	}

	public void setTOrderNotices(Set<TOrderNotice> TOrderNotices) {
		this.TOrderNotices = TOrderNotices;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccount")

	public Set<TDailyworkDetails> getTDailyworkDetailses() {
		return this.TDailyworkDetailses;
	}

	public void setTDailyworkDetailses(Set<TDailyworkDetails> TDailyworkDetailses) {
		this.TDailyworkDetailses = TDailyworkDetailses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccountByModifyid")

	public Set<TOrder> getTOrdersForModifyid() {
		return this.TOrdersForModifyid;
	}

	public void setTOrdersForModifyid(Set<TOrder> TOrdersForModifyid) {
		this.TOrdersForModifyid = TOrdersForModifyid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccount")

	public Set<TDemand> getTDemands() {
		return this.TDemands;
	}

	public void setTDemands(Set<TDemand> TDemands) {
		this.TDemands = TDemands;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccount")

	public Set<TPerfNum> getTPerfNums() {
		return this.TPerfNums;
	}

	public void setTPerfNums(Set<TPerfNum> TPerfNums) {
		this.TPerfNums = TPerfNums;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccountByMemofrom")

	public Set<TMemo> getTMemosForMemofrom() {
		return this.TMemosForMemofrom;
	}

	public void setTMemosForMemofrom(Set<TMemo> TMemosForMemofrom) {
		this.TMemosForMemofrom = TMemosForMemofrom;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccountByAuthorid")

	public Set<TOrder> getTOrdersForAuthorid() {
		return this.TOrdersForAuthorid;
	}

	public void setTOrdersForAuthorid(Set<TOrder> TOrdersForAuthorid) {
		this.TOrdersForAuthorid = TOrdersForAuthorid;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccount")

	public Set<TPerf> getTPerfs() {
		return this.TPerfs;
	}

	public void setTPerfs(Set<TPerf> TPerfs) {
		this.TPerfs = TPerfs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccountByInputer")

	public Set<TWorkrecord> getTWorkrecordsForInputer() {
		return this.TWorkrecordsForInputer;
	}

	public void setTWorkrecordsForInputer(Set<TWorkrecord> TWorkrecordsForInputer) {
		this.TWorkrecordsForInputer = TWorkrecordsForInputer;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccountByHandler")

	public Set<TWorkrecord> getTWorkrecordsForHandler() {
		return this.TWorkrecordsForHandler;
	}

	public void setTWorkrecordsForHandler(Set<TWorkrecord> TWorkrecordsForHandler) {
		this.TWorkrecordsForHandler = TWorkrecordsForHandler;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccount")

	public Set<TDailywork> getTDailyworks() {
		return this.TDailyworks;
	}

	public void setTDailyworks(Set<TDailywork> TDailyworks) {
		this.TDailyworks = TDailyworks;
	}

}