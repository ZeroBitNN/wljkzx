package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TOwogImages entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_OWOG_IMAGES", schema = "")

public class TOwogImages implements java.io.Serializable {

	// Fields

	private String id;
	private String imgsrc;
	private String class_;
	private String name;
	private Date startdate;
	private Date enddate;
	private String filename;
	private String uploader;
	private Date uploadtime;

	// Constructors

	/** default constructor */
	public TOwogImages() {
	}

	/** minimal constructor */
	public TOwogImages(String id) {
		this.id = id;
	}

	/** full constructor */
	public TOwogImages(String id, String imgsrc, String class_, String name, Date startdate, Date enddate,
			String filename, String uploader, Date uploadtime) {
		this.id = id;
		this.imgsrc = imgsrc;
		this.class_ = class_;
		this.name = name;
		this.startdate = startdate;
		this.enddate = enddate;
		this.filename = filename;
		this.uploader = uploader;
		this.uploadtime = uploadtime;
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

	@Column(name = "IMGSRC", length = 500)

	public String getImgsrc() {
		return this.imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	@Column(name = "CLASS", length = 200)

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@Column(name = "NAME", length = 200)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE", length = 7)

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE", length = 7)

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	@Column(name = "FILENAME", length = 200)

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "UPLOADER", length = 36)

	public String getUploader() {
		return this.uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOADTIME", length = 7)

	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

}