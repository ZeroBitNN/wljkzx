package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.LinksDaoI;
import model.TLinks;
import pageModel.Links;
import pageModel.LinksDataList;
import service.LinksServiceI;

@Service(value = "linksService")
public class LinksServiceImpl implements LinksServiceI {
	private static final Logger logger = Logger.getLogger(LinksServiceImpl.class);
	private LinksDaoI linksDao;

	public LinksDaoI getLinksDao() {
		return linksDao;
	}

	@Autowired
	public void setLinksDao(LinksDaoI linksDao) {
		this.linksDao = linksDao;
	}

	@Override
	public List<LinksDataList> getDataList(Links links) {
		List<LinksDataList> list = new ArrayList<LinksDataList>();
		String hql = "from TLinks t where 1=1";
		if (links.getText() != null && !links.getText().trim().equals("")) {
			hql += " and upper(t.text) like '%" + links.getText().trim().toUpperCase() + "%'";
		}
		List<TLinks> tLinksList = linksDao.find(hql);
		if (tLinksList != null && tLinksList.size() > 0) {
			for (TLinks t : tLinksList) {
				LinksDataList ldl = new LinksDataList();
				// <a href='http://www.w3school.com.cn/' target='_blank'>Visit W3School</a>
				String tempText = "<a href='" + t.getUrl() + "' target='_blank'>" + t.getText() + "</a>";
				ldl.setText(tempText);
				ldl.setCategory(t.getCategory());
				list.add(ldl);
			}
		}
		return list;
	}

	@Override
	public List<Links> getDataGrid() {
		List<Links> list = new ArrayList<Links>();
		String hql = "from TLinks t";
		List<TLinks> tLinksList = linksDao.find(hql);
		if (tLinksList != null && tLinksList.size() > 0) {
			for (TLinks t : tLinksList) {
				Links l = new Links();
				BeanUtils.copyProperties(t, l);
				list.add(l);
			}
		}

		return list;
	}

	@Override
	public void save(Links links) {
		TLinks t = new TLinks();
		BeanUtils.copyProperties(links, t);
		linksDao.save(t);
	}

	@Override
	public void delete(String id) {
		String hql = "delete from TLinks t where t.id='" + id + "'";
		linksDao.executeHql(hql);
	}

	@Override
	public void edit(Links links) {
		TLinks t = linksDao.getForId(TLinks.class, links.getId());
		BeanUtils.copyProperties(links, t);

	}

}
