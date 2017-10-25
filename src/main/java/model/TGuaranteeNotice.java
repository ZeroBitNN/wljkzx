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
 * TGuaranteeNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_GUARANTEE_NOTICE", schema = "")

public class TGuaranteeNotice implements java.io.Serializable {

	// Fields

	private String id;
	private TGuarantee TGuarantee;
	private Date noticedate;
	private String noticetime;
	private String content;
	private String recorder;
	private String remarks;

	// Constructors

	/** default constructor */
	public TGuaranteeNotice() {
	}

	/** minimal constructor */
	public TGuaranteeNotice(String id) {
		this.id = id;
	}

	/** full constructor */
	public TGuaranteeNotice(String id, TGuarantee TGuarantee, Date noticedate, String noticetime, String content,
			String recorder, String remarks) {
		this.id = id;
		this.TGuarantee = TGuarantee;
		this.noticedate = noticedate;
		this.noticetime = noticetime;
		this.content = content;
		this.recorder = recorder;
		this.remarks = remarks;
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
	@JoinColumn(name = "RELATEDID")

	public TGuarantee getTGuarantee() {
		return this.TGuarantee;
	}

	public void setTGuarantee(TGuarantee TGuarantee) {
		this.TGuarantee = TGuarantee;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "NOTICEDATE", length = 7)

	public Date getNoticedate() {
		return this.noticedate;
	}

	public void setNoticedate(Date noticedate) {
		this.noticedate = noticedate;
	}

	@Column(name = "NOTICETIME", length = 20)

	public String getNoticetime() {
		return this.noticetime;
	}

	public void setNoticetime(String noticetime) {
		this.noticetime = noticetime;
	}

	@Column(name = "CONTENT", length = 2000)

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "RECORDER", length = 36)

	public String getRecorder() {
		return this.recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	@Column(name = "REMARKS", length = 1000)

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}