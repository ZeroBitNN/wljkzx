package owogModel;

import java.util.Date;

public class OwogImages {

	//自定义页面显示
	private String gName;
	
	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	// 用于排序和分布
	private int page;
	private int rows;
	private String sort;
	private String order;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getClass_() {
		return class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}
}
