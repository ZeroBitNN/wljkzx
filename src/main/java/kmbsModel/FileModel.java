package kmbsModel;

import java.io.Serializable;
/**
 * 文件对象类
 * @author ZeroBit
 *
 */
public class FileModel implements Serializable {

	/**
	 * 自动生成序列ID
	 */
	private static final long serialVersionUID = 1317562434868936399L;

	private String fileName;	//文件名
	private String fileContent;	//文件内容
	private String filePath;	//文件路径
	private String fileUrl;		//文件下载路径
	
	public FileModel() {
		super();
	}

	public FileModel(String fileName, String fileContent) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
	}

	public FileModel(String fileName, String fileContent, String filePath) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.filePath = filePath;
	}

	
	public FileModel(String fileName, String fileContent, String filePath, String fileUrl) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.filePath = filePath;
		this.fileUrl = fileUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
