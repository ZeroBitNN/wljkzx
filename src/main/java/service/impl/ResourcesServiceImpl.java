package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ResourcesDaoI;
import dao.RolesDaoI;
import model.TResources;
import model.TRoles;
import pageModel.Resources;
import service.ResourcesServiceI;

@Service(value = "resourcesService")
public class ResourcesServiceImpl implements ResourcesServiceI {
	private static final Logger logger = Logger.getLogger(ResourcesServiceImpl.class);
	private ResourcesDaoI resourcesDao;
	private RolesDaoI rolesDao;

	public RolesDaoI getRolesDao() {
		return rolesDao;
	}

	@Autowired
	public void setRolesDao(RolesDaoI rolesDao) {
		this.rolesDao = rolesDao;
	}

	public ResourcesDaoI getResourcesDao() {
		return resourcesDao;
	}

	@Autowired
	public void setResourcesDao(ResourcesDaoI resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	@Override
	public List<Resources> getAllRes() {
		List<Resources> rList = new ArrayList<Resources>();
		String hql = "from TResources t where t.TResources is not null order by pid";
		List<TResources> tList = resourcesDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TResources t : tList) {
				Resources r = new Resources();
				r.setId(t.getId());
				r.setName(t.getName());
				r.setUrl(t.getUrl());
				r.setDescription(t.getDescription());
				r.setPid(t.getTResources().getName());
				rList.add(r);
			}
		}

		return rList;
	}

	@Override
	public List<Resources> getAllRes(String roleId) {
		// logger.info(roleId);
		TRoles tRoles = rolesDao.getForId(TRoles.class, roleId);
		String[] roleRes = tRoles.getResourcesIds().split(",");

		List<Resources> rList = new ArrayList<Resources>();
		String hql = "from TResources t where t.TResources is not null order by pid";
		List<TResources> tList = resourcesDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TResources t : tList) {
				Resources r = new Resources();
				r.setId(t.getId());
				r.setName(t.getName());
				r.setUrl(t.getUrl());
				r.setDescription(t.getDescription());
				r.setPid(t.getTResources().getName());
				for (String tempRes : roleRes) {
					if (tempRes.equals(t.getId())) {
						r.setSelected(true);
					}
				}
				rList.add(r);
			}
		}

		return rList;
	}

}
