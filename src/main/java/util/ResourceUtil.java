package util;

import java.util.ResourceBundle;

public class ResourceUtil {
	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
	private ResourceUtil(){
	}
	
	/**
	 * 获得sessionInfo名字
	 * @return
	 */
	public static final String getSessionInfoName(){
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 获得上传表单域的名称
	 * @return
	 */
	public static final String getUploadFieldName(){
		return bundle.getString("uploadFieldName");
	}

	/**
	 * 获得上传文件的最大大小限制
	 * @return
	 */
	public static final long getUploadFileMaxSize(){
		return Long.valueOf(bundle.getString("uploadFileMaxSize"));
	}
	
	/**
	 * 获得上传文件的扩展名
	 * @return
	 */
	public static final String getUploadFileExts(){
		return bundle.getString("uploadFileExts");
	}
	
	/**
	 * 获得上传文件存放到哪个目录
	 * @return
	 */
	public static final String getUploadDirectory(){
		return bundle.getString("uploadDirectory");
	}
	
	/**
	 * 获取远程文档存入目录
	 * @return
	 */
	public static final String getDocsDirectory(){
		return bundle.getString("docsDir");
	}
	
	/**
	 * 获取远程FTP服务器地址前缀
	 * @return
	 */
	public static final String getFtpUrlPrefix(){
		return bundle.getString("ftpUrlPrefix");
	}
	
	public static final String getOwogImages(){
		return bundle.getString("owogImages");
	}
}
