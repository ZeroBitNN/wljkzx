package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TGuarantee entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_GUARANTEE", schema = "")

public class TGuarantee implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String content;
	private String requirement;
	private String noticetemplate;
	private String recorder;
	private Date timestart;
	private Date timeend;
	private String timepoint;
	private String status;
	private Set<TGuaranteeNotice> TGuaranteeNotices = new HashSet<TGuaranteeNotice>(0);

	// Constructors

	/** default constructor */
	public TGuarantee() {
	}

	/** minimal constructor */
	public TGuarantee(String id) {
		this.id = id;
	}

	/** full constructor */
	public TGuarantee(String id, String title, String content, String requirement, String noticetemplate,
			String recorder, Date timestart, Date timeend, String timepoint, String status,
			Set<TGuaranteeNotice> TGuaranteeNotices) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.requirement = requirement;
		this.noticetemplate = noticetemplate;
		this.recorder = recorder;
		this.timestart = timestart;
		this.timeend = timeend;
		this.timepoint = timepoint;
		this.status = status;
		this.TGuaranteeNotices = TGuaranteeNotices;
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

	@Column(name = "TITLE", length = 200)

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Column(name = "CONTENT")

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "REQUIREMENT", length = 2000)

	public String getRequirement() {
		return this.requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	@Column(name = "NOTICETEMPLATE", length = 2000)

	public String getNoticetemplate() {
		return this.noticetemplate;
	}

	public void setNoticetemplate(String noticetemplate) {
		this.noticetemplate = noticetemplate;
	}

	@Column(name = "RECORDER", length = 36)

	public String getRecorder() {
		return this.recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TIMESTART", length = 7)

	public Date getTimestart() {
		return this.timestart;
	}

	public void setTimestart(Date timestart) {
		this.timestart = timestart;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TIMEEND", length = 7)

	public Date getTimeend() {
		return this.timeend;
	}

	public void setTimeend(Date timeend) {
		this.timeend = timeend;
	}

	@Column(name = "TIMEPOINT", length = 100)

	public String getTimepoint() {
		return this.timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}

	@Column(name = "STATUS", length = 20)

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TGuarantee")

	public Set<TGuaranteeNotice> getTGuaranteeNotices() {
		return this.TGuaranteeNotices;
	}

	public void setTGuaranteeNotices(Set<TGuaranteeNotice> TGuaranteeNotices) {
		this.TGuaranteeNotices = TGuaranteeNotices;
	}

}