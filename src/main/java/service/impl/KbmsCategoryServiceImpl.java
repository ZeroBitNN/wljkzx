package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.KbmsCategoryDaoI;
import kmbsModel.KbmsCategory;
import model.TKbmsCategory;
import service.KbmsCategoryServiceI;

@Service(value = "kbmsCategoryService")
public class KbmsCategoryServiceImpl implements KbmsCategoryServiceI {

	private static final Logger logger = Logger.getLogger(KbmsCategoryServiceImpl.class);
	private KbmsCategoryDaoI kbmsCategoryDao;

	public KbmsCategoryDaoI getKbmsCategoryDao() {
		return kbmsCategoryDao;
	}

	@Autowired
	public void setKbmsCategoryDao(KbmsCategoryDaoI kbmsCategoryDao) {
		this.kbmsCategoryDao = kbmsCategoryDao;
	}

	@Override
	public List<KbmsCategory> getTree(String id) {
		/*
		 * 异常加载菜单树 List<KbmsCategory> treeList = new ArrayList<KbmsCategory>();
		 * String hql = null; Map<String, Object> params = new HashMap<String,
		 * Object>(); if (id == null || id.equals("")) { hql =
		 * "from TKbmsCategory t where t.TKbmsCategory is null"; } else { hql =
		 * "from TKbmsCategory t where t.TKbmsCategory.id = :id";
		 * params.put("id", id); } List<TKbmsCategory> l =
		 * kbmsCategoryDao.find(hql, params); if (l != null && l.size() > 0) {
		 * for (TKbmsCategory t : l) { KbmsCategory k = new KbmsCategory();
		 * BeanUtils.copyProperties(t, k); if (t.getTKbmsCategories() != null &
		 * !t.getTKbmsCategories().isEmpty()) { k.setState("closed"); } else {
		 * k.setState("open"); } treeList.add(k); } } else { TKbmsCategory t =
		 * new TKbmsCategory(); t.setId("0"); t.setText("首页");
		 * kbmsCategoryDao.save(t); KbmsCategory k = new KbmsCategory();
		 * BeanUtils.copyProperties(t, k); treeList.add(k); }
		 * 
		 * return treeList;
		 */
		List<KbmsCategory> treeList = new ArrayList<KbmsCategory>();
		String hql = "from TKbmsCategory t order by t.TKbmsCategory.id";
		List<TKbmsCategory> l = kbmsCategoryDao.find(hql);
		if (l != null && l.size() > 0) {
			for (TKbmsCategory t : l) {
				KbmsCategory k = new KbmsCategory();
				BeanUtils.copyProperties(t, k);
				TKbmsCategory pt = t.getTKbmsCategory();
				if (pt != null) {
					k.setPid(t.getTKbmsCategory().getId());
				}
				treeList.add(k);
			}
		} else {
			TKbmsCategory t = new TKbmsCategory();
			t.setId("0");
			t.setText("首页");
			kbmsCategoryDao.save(t);
			KbmsCategory k = new KbmsCategory();
			BeanUtils.copyProperties(t, k);
			treeList.add(k);
		}
		return treeList;
	}

	@Override
	public KbmsCategory addOrUpdate(KbmsCategory kbmsCategory) {
		// 如果ID为空则为新增节点
		if (kbmsCategory.getId() == null || kbmsCategory.getId().equals("")) {
			TKbmsCategory tkc = new TKbmsCategory();
			tkc.setId(UUID.randomUUID().toString());
			tkc.setText(kbmsCategory.getText());
			tkc.setTKbmsCategory(kbmsCategoryDao.getForId(TKbmsCategory.class, kbmsCategory.getPid()));
			kbmsCategoryDao.save(tkc);
			kbmsCategory.setId(tkc.getId());
		} else {
			// 如果不为空则为修改节点
			TKbmsCategory tkc = kbmsCategoryDao.getForId(TKbmsCategory.class, kbmsCategory.getId());
			tkc.setText(kbmsCategory.getText());
		}

		return kbmsCategory;
	}

	@Override
	public void delete(KbmsCategory kbmsCategory) {
		logger.info("删除知识库类目：" + kbmsCategory.getId());
		if (kbmsCategory.getId() != null) {
			kbmsCategoryDao.delete(kbmsCategoryDao.getForId(TKbmsCategory.class, kbmsCategory.getId()));
		}
	}

}
