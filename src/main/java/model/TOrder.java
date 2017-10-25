package model;

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ORDER", schema = "")

public class TOrder implements java.io.Serializable {

	// Fields

	private String id;
	private TOrderType TOrderType;
	private TAccount TAccountByAuthorid;
	private TOrderCategory TOrderCategory;
	private TAccount TAccountByModifyid;
	private String title;
	private String ordernum;
	private String content;
	private Date createtime;
	private Date modifytime;
	private BigDecimal noticetime;
	private String isArchived;
	private Date archivedtime;
	private Set<TOrderNotice> TOrderNotices = new HashSet<TOrderNotice>(0);

	// Constructors

	/** default constructor */
	public TOrder() {
	}

	/** minimal constructor */
	public TOrder(String id) {
		this.id = id;
	}

	/** full constructor */
	public TOrder(String id, TOrderType TOrderType, TAccount TAccountByAuthorid, TOrderCategory TOrderCategory,
			TAccount TAccountByModifyid, String title, String ordernum, String content, Date createtime,
			Date modifytime, BigDecimal noticetime, String isArchived, Date archivedtime,
			Set<TOrderNotice> TOrderNotices) {
		this.id = id;
		this.TOrderType = TOrderType;
		this.TAccountByAuthorid = TAccountByAuthorid;
		this.TOrderCategory = TOrderCategory;
		this.TAccountByModifyid = TAccountByModifyid;
		this.title = title;
		this.ordernum = ordernum;
		this.content = content;
		this.createtime = createtime;
		this.modifytime = modifytime;
		this.noticetime = noticetime;
		this.isArchived = isArchived;
		this.archivedtime = archivedtime;
		this.TOrderNotices = TOrderNotices;
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
	@JoinColumn(name = "TYPEID")

	public TOrderType getTOrderType() {
		return this.TOrderType;
	}

	public void setTOrderType(TOrderType TOrderType) {
		this.TOrderType = TOrderType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHORID")

	public TAccount getTAccountByAuthorid() {
		return this.TAccountByAuthorid;
	}

	public void setTAccountByAuthorid(TAccount TAccountByAuthorid) {
		this.TAccountByAuthorid = TAccountByAuthorid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORYID")

	public TOrderCategory getTOrderCategory() {
		return this.TOrderCategory;
	}

	public void setTOrderCategory(TOrderCategory TOrderCategory) {
		this.TOrderCategory = TOrderCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODIFYID")

	public TAccount getTAccountByModifyid() {
		return this.TAccountByModifyid;
	}

	public void setTAccountByModifyid(TAccount TAccountByModifyid) {
		this.TAccountByModifyid = TAccountByModifyid;
	}

	@Column(name = "TITLE", length = 100)

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "ORDERNUM", length = 50)

	public String getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "CONTENT", length = 1000)

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

	@Column(name = "NOTICETIME", precision = 22, scale = 0)

	public BigDecimal getNoticetime() {
		return this.noticetime;
	}

	public void setNoticetime(BigDecimal noticetime) {
		this.noticetime = noticetime;
	}

	@Column(name = "IS_ARCHIVED", length = 10)

	public String getIsArchived() {
		return this.isArchived;
	}

	public void setIsArchived(String isArchived) {
		this.isArchived = isArchived;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ARCHIVEDTIME", length = 7)

	public Date getArchivedtime() {
		return this.archivedtime;
	}

	public void setArchivedtime(Date archivedtime) {
		this.archivedtime = archivedtime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TOrder")

	public Set<TOrderNotice> getTOrderNotices() {
		return this.TOrderNotices;
	}

	public void setTOrderNotices(Set<TOrderNotice> TOrderNotices) {
		this.TOrderNotices = TOrderNotices;
	}

}