package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TOrderNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ORDER_NOTICE", schema = "WLJKZX")

public class TOrderNotice implements java.io.Serializable {

	// Fields

	private String id;
	private TAccount TAccount;
	private TOrder TOrder;
	private String content;
	private Date time;

	// Constructors

	/** default constructor */
	public TOrderNotice() {
	}

	/** minimal constructor */
	public TOrderNotice(String id) {
		this.id = id;
	}

	/** full constructor */
	public TOrderNotice(String id, TAccount TAccount, TOrder TOrder, String content, Date time) {
		this.id = id;
		this.TAccount = TAccount;
		this.TOrder = TOrder;
		this.content = content;
		this.time = time;
	}

	// Property accessors
	@Id

	@Column(name = "ID", unique = true, nullable = false, length = 36)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHORID")

	public TAccount getTAccount() {
		return this.TAccount;
	}

	public void setTAccount(TAccount TAccount) {
		this.TAccount = TAccount;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDERID")

	public TOrder getTOrder() {
		return this.TOrder;
	}

	public void setTOrder(TOrder TOrder) {
		this.TOrder = TOrder;
	}

	@Column(name = "CONTENT", length = 1000)

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TIME", length = 7)

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}