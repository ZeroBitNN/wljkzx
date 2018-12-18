package action;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import model.TOwogImages;
import owogModel.OwogImages;
import pageModel.DataGrid;
import pageModel.Json;
import service.OwogImagesServiceI;
import util.FileUtil;
import util.ResourceUtil;
import util.StringUtil;

@Namespace("/")
@Action(value = "owogImagesAction")
public class OwogImagesAction extends BaseAction implements ModelDriven<OwogImages> {
	private static final Logger logger = Logger.getLogger(OwogImagesAction.class);
	private SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute("sessionInfo");
	private OwogImages owogImages = new OwogImages();

	@Override
	public OwogImages getModel() {
		return owogImages;
	}

	private OwogImagesServiceI owogImagesService;

	public OwogImagesServiceI getOwogImagesService() {
		return owogImagesService;
	}

	@Autowired
	public void setOwogImagesService(OwogImagesServiceI owogImagesService) {
		this.owogImagesService = owogImagesService;
	}

	public void upload() {
		Json j = new Json();
		String savePath;// 文件保存目录路径
		String saveUrl;// 文件保存目录URL
		// 上传的文件总大小
		Long fileSize = Long.valueOf(ServletActionContext.getRequest().getHeader("Content-Length"));
		// 由于struts2上传文件时自动使用了request封装
		MultiPartRequestWrapper multiPartRequest = (MultiPartRequestWrapper) ServletActionContext.getRequest();
		// 上传的文件集合
		File[] files = multiPartRequest.getFiles(ResourceUtil.getUploadFieldName());
		// 上传文件名称集合
		String[] fileNames = multiPartRequest.getFileNames(ResourceUtil.getUploadFieldName());
		// 校验是否上传文件和文件总大小
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
		// 循环读取所有文件
		for (int i = 0; i < files.length; i++) {
			if (owogImages.getStartdate() != null) {
				// 文件保存在服务的目录路径
				savePath = ServletActionContext.getServletContext().getRealPath("/") + ResourceUtil.getOwogImages()
						+ "/" + StringUtil.getWeekStartEnd(owogImages.getStartdate()) + "/";
				// 文件保存目录的URL路径即img src
				saveUrl = ResourceUtil.getOwogImages() + "/" + StringUtil.getWeekStartEnd(owogImages.getStartdate())
						+ "/";
			} else {
				j.setMsg("没有指定时间周期");
				super.writeJson(j);
				return;
			}
			// 上传的每个临时文件
			File file = files[i];
			// 上传的每个文件名
			String fileName = fileNames[i];
			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(",")).contains(fileExt)) {
				j.setMsg("上传文件扩展名是不允许的扩展名。\n只允许" + ResourceUtil.getUploadFileExts() + "格式！");
				super.writeJson(j);
				return;
			}
			// 检查该班组该时间段是否已上传图片
			int dataNum = owogImagesService.getDataNum(owogImages);
			if (dataNum != 0) {
				j.setMsg("所选班组该时间周期内只能有一位标杆员工，如上传错误，请先删除后再重新上传！");
				super.writeJson(j);
				return;
			}
			// 创建要上传文件到指定的目录
			logger.info("上传图片保存路径：" + savePath);
			logger.info("上传图片IMG SRC：" + saveUrl);
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
			owogImages.setId(UUIDFileName);
			owogImages.setImgsrc(saveUrl + newFileName);
			owogImages.setFilename(fileName);
			owogImages.setUploader(sessionInfo.getUser().getUsername());
			owogImages.setUploadtime(new Date());
			try {
				owogImagesService.addFileToDB(owogImages);
			} catch (Exception e) {
				FileUtil.delete(savePath + newFileName);
				j.setMsg(fileName + " 文件上传失败!");
				super.writeJson(j);
				return;
			}

		}

		j.setSuccess(true);
		j.setMsg(fileNames[0] + " 图片上传成功！");
		super.writeJson(j);
	}

	public void doNotNeedSecurity_getAllDg() {
		DataGrid<OwogImages> g = owogImagesService.getAllDg(owogImages);
		super.writeJson(g);
	}

	public void delete() {
		String savePath = ServletActionContext.getServletContext().getRealPath("/");
		Json j = new Json();
		try {
			owogImagesService.delete(owogImages.getId(), sessionInfo.getUser().getUsername(), savePath);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg("删除失败！" + e.getMessage());
		}
		super.writeJson(j);
	}

	public void doNotNeedSecurity_listForShow() {
		List<TOwogImages> tList = owogImagesService.getListForShow();
		super.writeJson(tList);
	}
}
