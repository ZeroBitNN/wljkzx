package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ResourcesDaoI;
import dao.RolesDaoI;
import model.TResources;
import model.TRoles;
import pageModel.DataGrid;
import pageModel.Roles;
import service.RolesServiceI;

@Service(value = "rolesService")
public class RolesServiceImpl implements RolesServiceI {
	private static final Logger logger = Logger.getLogger(RolesServiceImpl.class);
	private RolesDaoI rolesDao;
	private ResourcesDaoI resourcesDao;

	public ResourcesDaoI getResourcesDao() {
		return resourcesDao;
	}

	@Autowired
	public void setResourcesDao(ResourcesDaoI resourcesDao) {
		this.resourcesDao = resourcesDao;
	}

	public RolesDaoI getRolesDao() {
		return rolesDao;
	}

	@Autowired
	public void setRolesDao(RolesDaoI rolesDao) {
		this.rolesDao = rolesDao;
	}

	@Override
	public DataGrid<Roles> getRoles(Roles roles) {
		DataGrid<Roles> dg = new DataGrid<Roles>();
		String hql = "from TRoles t where t.id<>'0'";
		String totalHql = "select count(*) " + hql;
		List<TRoles> tList = rolesDao.find(hql);
		List<Roles> rList = new ArrayList<Roles>();
		if (tList != null && tList.size() > 0) {
			for (TRoles t : tList) {
				Roles r = new Roles();
				BeanUtils.copyProperties(t, r, new String[] { "resourcesIds" });
				String[] resIds = t.getResourcesIds().split(",");
				StringBuffer sb = new StringBuffer();
				for (String resId : resIds) {
					TResources tr = resourcesDao.getForId(TResources.class, resId);
					sb.append(tr.getName() + ",");
				}
				r.setResourcesIds(StringUtils.left(sb.toString(), sb.toString().length() - 1));
				// logger.info(r.getResourcesIds());
				rList.add(r);
			}
		}

		dg.setRows(rList);
		dg.setTotal(rolesDao.count(totalHql));
		return dg;
	}

	@Override
	public Roles edit(Roles roles) {
		/**
		 * [service.impl.RolesServiceImpl]1 
		 * [service.impl.RolesServiceImpl]班组长 
		 * [service.impl.RolesServiceImpl]yhlb, tjyh, scyh, bjyh
		 */
		roles.setResourcesIds(StringUtils.remove(roles.getResourcesIds(), " "));
		TRoles t = rolesDao.getForId(TRoles.class, roles.getId());
		BeanUtils.copyProperties(roles, t);
		rolesDao.saveOrUpdate(t);
		
		String[] resIds = roles.getResourcesIds().split(",");
		StringBuffer sb = new StringBuffer();
		for (String resId:resIds){
			TResources tr = resourcesDao.getForId(TResources.class, resId);
			sb.append(tr.getName() + ",");
		}
		roles.setResourcesIds(StringUtils.left(sb.toString(), sb.toString().length() - 1));

		return roles;
	}

	@Override
	public Roles add(Roles roles) {
		roles.setId(UUID.randomUUID().toString());
		roles.setResourcesIds(StringUtils.remove(roles.getResourcesIds(), " "));
		TRoles tR = new TRoles();
		BeanUtils.copyProperties(roles, tR);
		rolesDao.save(tR);
		
		String[] resIds = roles.getResourcesIds().split(",");
		StringBuffer sb = new StringBuffer();
		for (String resId:resIds){
			TResources tr = resourcesDao.getForId(TResources.class, resId);
			sb.append(tr.getName() + ",");
		}
		roles.setResourcesIds(StringUtils.left(sb.toString(), sb.toString().length() - 1));
		
		return roles;
	}

	@Override
	public void delete(String id) {
		String hql = "delete from TRoles t where t.id='" + id + "'";
		rolesDao.executeHql(hql);
	}

}
