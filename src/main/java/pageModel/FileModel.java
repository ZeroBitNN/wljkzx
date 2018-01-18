package pageModel;

import java.io.Serializable;

/**
 * 文件对象类
 * 
 * @author ZeroBit
 *
 */
public class FileModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8441101978312347916L;
	private String title; // 文件标题或文件名
	private String content; // 文件内容
	private String fileDate; // 文件日期
	private String keyWords; // 查询关键字

	public FileModel() {

	}

	public FileModel(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	public FileModel(String title, String content, String fileDate, String keyWords) {
		super();
		this.title = title;
		this.content = content;
		this.fileDate = fileDate;
		this.keyWords = keyWords;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}

}
