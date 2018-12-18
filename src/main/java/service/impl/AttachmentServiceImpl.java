package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AttachmentDaoI;
import dao.UserDaoI;
import model.TAccount;
import model.TAttachment;
import pageModel.Attachment;
import pageModel.DataGrid;
import service.AttachmentServiceI;
import util.FileUtil;

@Service(value = "attachmentService")
public class AttachmentServiceImpl implements AttachmentServiceI {
	private static final Logger logger = Logger.getLogger(AttachmentServiceImpl.class);
	private AttachmentDaoI attachmentDao;
	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	public AttachmentDaoI getAttachmentDao() {
		return attachmentDao;
	}

	@Autowired
	public void setAttachmentDao(AttachmentDaoI attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	@Override
	public void addFileToDB(Attachment attachment) {
		TAttachment t = new TAttachment();
		BeanUtils.copyProperties(attachment, t);
		attachmentDao.save(t);
	}

	@Override
	public DataGrid<Attachment> getDatagrid(Attachment attachment) {
		DataGrid<Attachment> dg = new DataGrid<Attachment>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TAttachment t where 1=1";
		if (attachment.getFilename() != null && !attachment.getFilename().trim().equals("")) {
			hql += " and t.filename like :filename";
			params.put("filename", "%%" + attachment.getFilename().trim() + "%%");
		}
		if (attachment.getAccount() != null && !attachment.getAccount().trim().equals("")) {
			hql += " and t.account like :account";
			params.put("account", "%%" + attachment.getAccount().trim() + "%%");
		}
		if (attachment.getDescribe() != null && !attachment.getDescribe().trim().equals("")) {
			hql += " and t.describe like :describe";
			params.put("describe", "%%" + attachment.getDescribe().trim() + "%%");
		}
		if (attachment.getUploadtimeStart() != null) {
			hql += " and t.uploadtime>=:uploadtimestart";
			params.put("uploadtimestart", attachment.getUploadtimeStart());
		}
		if (attachment.getUploadtimeEnd() != null) {
			hql += " and t.uploadtime<=:uploadtimeend";
			params.put("uploadtimeend", attachment.getUploadtimeEnd());
		}
		if (attachment.getRelatedid() != null && !attachment.getRelatedid().trim().equals("")) {
			hql += " and t.relatedid=:relatedid";
			params.put("relatedid", attachment.getRelatedid());
		}
		// 查询记录总数
		String totalHql = "select count(*) " + hql;
		dg.setTotal(attachmentDao.count(totalHql, params));
		// 排序条件
		if (attachment.getSort() != null && attachment.getOrder() != null) {
			hql += " order by " + attachment.getSort() + " " + attachment.getOrder();
		}
		// 根据HQL获取记录
		List<TAttachment> tList = attachmentDao.find(hql, params, attachment.getPage(), attachment.getRows());
		List<Attachment> aList = new ArrayList<Attachment>();
		if (tList != null && tList.size() > 0) {
			for (TAttachment t : tList) {
				Attachment a = new Attachment();
				BeanUtils.copyProperties(t, a);
				TAccount user = userDao.getForId(TAccount.class, t.getAccount());
				a.setAccount(user.getUsername());
				aList.add(a);
			}
		}
		dg.setRows(aList);

		return dg;
	}

	@Override
	public void delete(String ids) throws Exception {
		String[] id = ids.split(",");
		for (String s : id) {
			TAttachment t = attachmentDao.getForId(TAttachment.class, s);
			String fileName = ServletActionContext.getServletContext().getRealPath("/") + t.getUrl().substring(1);
			// logger.info(fileName);
			// 删除真实文件
			if (!FileUtil.delete(fileName)) {
				throw new Exception("删除物理文件失败！");
			}
			// 删除数据库记录
			attachmentDao.delete(t);
		}
	}

	@Override
	public void delByRelatedid(String relatedid) throws Exception {
		String hql;
		hql = "from TAttachment t where t.relatedid='" + relatedid + "'";
		List<TAttachment> tList = attachmentDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TAttachment t : tList) {
				// 删除真实文件
				String fileName = ServletActionContext.getServletContext().getRealPath("/") + t.getUrl().substring(1);
				if (!FileUtil.delete(fileName)) {
					throw new Exception("删除物理文件失败！");
				}
				// 删除数据库记录
				attachmentDao.delete(t);
			}
		}
	}

}
