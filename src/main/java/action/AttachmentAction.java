package action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ModelDriven;

import model.SessionInfo;
import pageModel.Attachment;
import pageModel.DataGrid;
import pageModel.Json;
import service.AttachmentServiceI;
import util.ResourceUtil;

@Namespace("/")
@Action(value = "attachmentAction")
public class AttachmentAction extends BaseAction implements ModelDriven<Attachment> {
	private static final Logger logger = Logger.getLogger(AttachmentAction.class);
	private File filedata;
	private String filedataContentType;
	private String filedataFileName;

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(String filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	private Attachment attachment = new Attachment();

	@Override
	public Attachment getModel() {
		return attachment;
	}

	private AttachmentServiceI attachmentService;

	public AttachmentServiceI getAttachmentService() {
		return attachmentService;
	}

	@Autowired
	public void setAttachmentService(AttachmentServiceI attachmentService) {
		this.attachmentService = attachmentService;
	}

	private SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute("sessionInfo");

	public void upload() {
		Json j = new Json();
		String savePath;// 文件保存目录路径
		String saveUrl;// 文件保存目录URL
		Long fileSize = Long.valueOf(ServletActionContext.getRequest().getHeader("Content-Length"));// 上传的文件总大小

		// logger.info("filedataContentType：" + filedataContentType);
		// logger.info("filedataFileName：" + filedataFileName);
		// logger.info("=====================================");
		// logger.info("文件保存目录路径：" + savePath);
		// logger.info("文件保存目录URL：" + saveUrl);
		// logger.info("上传大小：" + fileSize);

		MultiPartRequestWrapper multiPartRequest = (MultiPartRequestWrapper) ServletActionContext
				.getRequest();// 由于struts2上传文件时自动使用了request封装
		File[] files = multiPartRequest.getFiles(ResourceUtil.getUploadFieldName());// 上传的文件集合
		String[] fileNames = multiPartRequest.getFileNames(ResourceUtil.getUploadFieldName());// 上传文件名称集合

		// logger.info("=====================================");
		// logger.info("文件数量：" + files.length);
		// for (String s : fileNames) {
		// logger.info("文件名：" + s);
		// }

		if (files == null || files.length < 1) {
			j.setMsg("您没有上传任何文件！");
			super.writeJson(j);
			return;
		}
		if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
			j.setMsg("上传文件总大小超出限制！");
			super.writeJson(j);
			return;
		}

		// 循环所有文件
		for (int i = 0; i < files.length; i++) {
			savePath = ServletActionContext.getServletContext().getRealPath("/")
					+ ResourceUtil.getUploadDirectory() + "/";// 文件保存目录路径
			saveUrl = "/" + ResourceUtil.getUploadDirectory() + "/";// 文件保存目录URL
			File file = files[i]; // 上传的每个临时文件
			String fileName = fileNames[i];// 上传的每个文件名
			// 校验文件大小
			if (file.length() > ResourceUtil.getUploadFileMaxSize()) {
				j.setMsg(fileName + " 文件超出限制大小！");
				super.writeJson(j);
				return;
			}
			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(",")).contains(fileExt)) {
				j.setMsg("上传文件扩展名是不允许的扩展名。\n只允许" + ResourceUtil.getUploadFileExts() + "格式！");
				super.writeJson(j);
				return;
			}
			// 生成文件保存目录路径和文件保存目录URL
			SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthDf = new SimpleDateFormat("MM");
			SimpleDateFormat dateDf = new SimpleDateFormat("dd");
			Date date = new Date();
			String ymd = yearDf.format(date) + monthDf.format(date) + dateDf.format(date) + "/";
			savePath += ymd;
			saveUrl += ymd;
			// logger.info("savePath: " + savePath);
			// logger.info("saveUrl: " + saveUrl);
			// 创建要上传文件到指定的目录
			File uploadDir = new File(savePath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			// 新的文件名称
			String UUIDFileName = UUID.randomUUID().toString();
			String newFileName = UUIDFileName.replaceAll("-", "") + "." + fileExt;
			File uploadedFile = new File(savePath, newFileName);
			// 拷贝文件
			try {
				FileCopyUtils.copy(file, uploadedFile);// 利用spring的文件工具上传
			} catch (Exception e) {
				j.setMsg(fileName + " 文件上传失败！");
				super.writeJson(j);
				return;
			}

			// 将上传文件数据存入数据库
			attachment.setId(UUIDFileName);
			attachment.setFilename(fileName);
			attachment.setAccount(sessionInfo.getUser().getId());
			attachment.setUploadtime(date);
			attachment.setUrl(saveUrl + newFileName);
			try {
				attachmentService.addFileToDB(attachment);
			} catch (Exception e) {
				j.setMsg(fileName + " 文件上传失败！");
			}

		}
		j.setSuccess(true);
		j.setMsg(filedataFileName + " 文件上传成功！");
		super.writeJson(j);
	}

	public void doNotNeedSecurity_getAllDg() {
		DataGrid<Attachment> g = attachmentService.getDatagrid(attachment);
		super.writeJson(g);
	}

	public void del() {
		Json j = new Json();
		String ids = attachment.getIds();
		try {
			attachmentService.delete(ids);
			j.setSuccess(true);
			j.setMsg("文件删除成功！");
		} catch (Exception e) {
			j.setMsg("文件删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}
	
	public void doNotNeedSecurity_delByRelatedid(){
		Json j = new Json();
		try {
			attachmentService.delByRelatedid(attachment.getRelatedid());
			j.setSuccess(true);
			j.setMsg("文件删除成功！");
		} catch (Exception e) {
			j.setMsg("文件删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

}
