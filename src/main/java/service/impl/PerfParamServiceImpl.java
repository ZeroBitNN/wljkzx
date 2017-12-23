package service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.PerfParamDaoI;
import model.TPerfParam;
import pageModel.DataGrid;
import pageModel.PerfParam;
import service.PerfParamServiceI;

@Service(value = "perfParamService")
public class PerfParamServiceImpl implements PerfParamServiceI {
	private static final Logger logger = Logger.getLogger(PerfParamServiceImpl.class);

	private PerfParamDaoI perfParamDao;

	public PerfParamDaoI getPerfParamDao() {
		return perfParamDao;
	}

	@Autowired
	public void setPerfParamDao(PerfParamDaoI perfParamDao) {
		this.perfParamDao = perfParamDao;
	}

	@Override
	public DataGrid<PerfParam> getParams() {
		double sumValue = 0.0;
		int sumPercent = 0;
		List<PerfParam> footer = new ArrayList<PerfParam>();

		DataGrid<PerfParam> dg = new DataGrid<PerfParam>();
		String hql = "from TPerfParam t where type='类目'";

		String totalHql = "select count(*) " + hql;
		dg.setTotal(perfParamDao.count(totalHql));

		List<TPerfParam> tList = perfParamDao.find(hql);
		List<PerfParam> pList = new ArrayList<PerfParam>();
		if (tList != null && tList.size() > 0) {
			for (TPerfParam t : tList) {
				if (t.getValue() != null) {
					sumValue += t.getValue();
				}
				if (t.getPercent() != null) {
					sumPercent += t.getPercent().intValue();
				}
				PerfParam p = new PerfParam();
				BeanUtils.copyProperties(t, p);
				if (t.getTPerfParam() != null && t.getTPerfParam().getId() != null
						&& !t.getTPerfParam().getId().equals("")) {
					p.setPid(t.getTPerfParam().getId());
					p.set_parentId(t.getTPerfParam().getId());
				}
				pList.add(p);
			}
		}
		dg.setRows(pList);

		PerfParam sumP = new PerfParam();
		sumP.setName("合计");
		sumP.setValue(sumValue);
		sumP.setPercent(new BigDecimal(sumPercent));
		footer.add(sumP);
		dg.setFooter(footer);

		return dg;
	}

	@Override
	public void addOrUpdate(PerfParam perfParam) {
		/**
		 * [id=7a8e9bd8-8414-8e5f-d2cd-54a4d559f9ec, name=新增类别, percent=null,
		 * value=null, describe=, type=null, pid=item2, _parentId=item2]
		 */
		TPerfParam t = perfParamDao.getForId(TPerfParam.class, perfParam.getId());
		if (t == null) {
			logger.info("新增类目");
			t = new TPerfParam();
			BeanUtils.copyProperties(perfParam, t);
			t.setTPerfParam(perfParamDao.getForId(TPerfParam.class, perfParam.getPid()));
			t.setType("类目");
			perfParamDao.save(t);
		} else {
			logger.info("修改类目");
			BeanUtils.copyProperties(perfParam, t, new String[] { "id", "type" });
			if (perfParam.getPid() != null && !perfParam.getPid().equals("")) {
				t.setTPerfParam(perfParamDao.getForId(TPerfParam.class, perfParam.getPid()));
			}
		}
	}

	@Override
	public void delete(PerfParam perfParam) throws Exception {
		/**
		 * [id=a44862ac-dee8-5cd9-c978-23032277a6c1, name=新增子类, percent=null,
		 * value=null, describe=, type=null, pid=item1, _parentId=item1]
		 */
		try {
			if (perfParam.getPid() != null && !perfParam.getPid().equals("")) {
				TPerfParam t = perfParamDao.getForId(TPerfParam.class, perfParam.getId());
				perfParamDao.delete(t);
			} else {
				throw new Exception("不允许删除初始化类目");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

}
