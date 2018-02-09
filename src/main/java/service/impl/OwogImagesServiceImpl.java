package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.OwogImagesDaoI;
import model.TOwogImages;
import owogModel.OwogImages;
import pageModel.DataGrid;
import service.OwogImagesServiceI;
import util.FileUtil;
import util.StringUtil;

@Service(value = "owogImagesService")
public class OwogImagesServiceImpl implements OwogImagesServiceI {
	private static final Logger logger = Logger.getLogger(OwogImagesServiceImpl.class);
	private OwogImagesDaoI owogImagesDao;

	public OwogImagesDaoI getOwogImagesDao() {
		return owogImagesDao;
	}

	@Autowired
	public void setOwogImagesDao(OwogImagesDaoI owogImagesDao) {
		this.owogImagesDao = owogImagesDao;
	}

	@Override
	public int getDataNum(OwogImages owogImages) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TOwogImages t where t.class_=:class and t.startdate>=:startdate and t.enddate<=:enddate";
		params.put("class", owogImages.getClass_());
		params.put("startdate", owogImages.getStartdate());
		params.put("enddate", owogImages.getEnddate());
		List<TOwogImages> tList = owogImagesDao.find(hql, params);
		if (tList != null && tList.size() > 0) {
			return tList.size();
		} else {
			return 0;
		}
	}

	@Override
	public void addFileToDB(OwogImages owogImages) {
		TOwogImages t = new TOwogImages();
		BeanUtils.copyProperties(owogImages, t);
		owogImagesDao.save(t);
	}

	@Override
	public DataGrid<OwogImages> getAllDg(OwogImages owogImages) {
		DataGrid<OwogImages> dg = new DataGrid<OwogImages>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TOwogImages t where 1=1";
		// 查询条件
		if (owogImages.getClass_() != null && !owogImages.getClass_().equals("")) {
			hql += " and t.class_=:class";
			params.put("class", owogImages.getClass_());
		}
		if (owogImages.getStartdate() != null) {
			hql += " and t.startdate>=:startdate";
			params.put("startdate", owogImages.getStartdate());
		}
		if (owogImages.getEnddate() != null) {
			hql += " and t.enddate<=:enddate";
			params.put("enddate", owogImages.getEnddate());
		}
		if (owogImages.getName() != null && !owogImages.getName().equals("")) {
			hql += " and t.name like '%" + owogImages.getName() + "%'";
		}
		if (owogImages.getUploader() != null && !owogImages.getUploader().equals("")) {
			hql += " and t.uploader like '%" + owogImages.getUploader() + "&'";
		}
		// 记录总数
		String totalHql = "select count(*) " + hql;
		// 查询排序
		dg.setTotal(owogImagesDao.count(totalHql, params));
		if (owogImages.getSort() != null) {
			hql += " order by " + owogImages.getSort() + " " + owogImages.getOrder();
		}
		// 查询结果集
		List<TOwogImages> tList = owogImagesDao.find(hql, params, owogImages.getPage(), owogImages.getRows());
		List<OwogImages> oList = new ArrayList<OwogImages>();
		if (tList != null && tList.size() > 0) {
			for (TOwogImages t : tList) {
				OwogImages o = new OwogImages();
				BeanUtils.copyProperties(t, o);
				o.setgName(t.getClass_() + t.getName());
				oList.add(o);
			}
		}

		dg.setRows(oList);
		return dg;
	}

	@Override
	public void delete(String id, String currentUser, String savePath) throws Exception {
		TOwogImages t = owogImagesDao.getForId(TOwogImages.class, id);
		if (t.getUploader().equals(currentUser)) {
			owogImagesDao.delete(t);
			FileUtil.delete(savePath + t.getImgsrc());
		} else {
			throw new Exception("只能删除本人上传的图片！");
		}
	}

	@Override
	public List<TOwogImages> getListForShow() {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TOwogImages t where t.startdate>=:startdate and t.enddate<=:enddate";
		params.put("startdate", StringUtil.getLastMonday(new Date()));
		params.put("enddate", StringUtil.getLastWeekend(new Date()));
		List<TOwogImages> tList = owogImagesDao.find(hql, params);
		if (tList != null && tList.size() > 0) {
			return tList;
		} else {
			return null;
		}
	}

}
