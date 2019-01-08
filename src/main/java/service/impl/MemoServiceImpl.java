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

import dao.MemoDaoI;
import dao.UserDaoI;
import model.TAccount;
import model.TMemo;
import pageModel.DataGrid;
import pageModel.Memo;
import service.MemoServiceI;

@Service(value = "memoService")
public class MemoServiceImpl implements MemoServiceI {
	private static final Logger logger = Logger.getLogger(MemoServiceImpl.class);

	private MemoDaoI memoDao;
	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	public MemoDaoI getMemoDao() {
		return memoDao;
	}

	@Autowired
	public void setMemoDao(MemoDaoI memoDao) {
		this.memoDao = memoDao;
	}

	@Override
	public DataGrid<Memo> getAll(Memo memo) {
		DataGrid<Memo> dg = new DataGrid<Memo>();
		String hql = "from TMemo t where 1=1";
		find(memo, dg, hql);
		return dg;
	}

	@Override
	public DataGrid<Memo> getLdjb(Memo memo) {
		DataGrid<Memo> dg = new DataGrid<Memo>();
		String hql = "from TMemo t where t.memotype='领导交办'";
		find(memo, dg, hql);
		return dg;
	}

	@Override
	public DataGrid<Memo> getPtjb(Memo memo) {
		DataGrid<Memo> dg = new DataGrid<Memo>();
		String hql = "from TMemo t where t.memotype='普通交班'";
		find(memo, dg, hql);
		return dg;
	}

	private void find(Memo memo, DataGrid<Memo> dg, String hql) {
		Map<String, Object> values = new HashMap<String, Object>();
		// 查询条件
		if (memo.getContent() != null && !memo.getContent().trim().equals("")) {
			hql += " and (t.content like :content or t.othercontent like :content or t.leaveover like :content)";
			values.put("content", "%%" + memo.getContent().trim() + "%%");
		}
		if (memo.getMemotype() != null && !memo.getMemotype().trim().equals("")) {
			hql += " and t.memotype like :memotype";
			values.put("memotype", "%%" + memo.getMemotype().trim() + "%%");
		}
		if (memo.getCreatetimeStart() != null) {
			hql += " and t.createtime>=:createtimestart";
			values.put("createtimestart", memo.getCreatetimeStart());
		}
		if (memo.getCreatetimeEnd() != null) {
			hql += " and t.createtime<=:createtimeend";
			values.put("createtimeend", memo.getCreatetimeEnd());
		}
		// 获取交班人
		if (memo.getMemofrom() != null && !memo.getMemofrom().trim().equals("")) {
			String hqlAcc = "from TAccount t where t.username='" + memo.getMemofrom() + "'";
			TAccount t = userDao.get(hqlAcc);
			if (t != null) {
				hql += " and t.TAccountByMemofrom.id=:memofrom";
				values.put("memofrom", t.getId());
			}

		}
		if (memo.getModifytimeStart() != null) {
			hql += " and t.modifytime>=:modifytimestart";
			values.put("modifytimestart", memo.getModifytimeStart());
		}
		if (memo.getModifytimeEnd() != null) {
			hql += " and t.modifytime<=:modifytimeend";
			values.put("modifytimeend", memo.getModifytimeEnd());
		}
		// 获取接班人
		if (memo.getMemoto() != null && !memo.getMemoto().trim().equals("")) {
			String hqlAcc = "from TAccount t where t.username='" + memo.getMemoto() + "'";
			TAccount t = userDao.get(hqlAcc);
			if (t != null) {
				hql += " and t.TAccountByMemoto.id=:memoto";
				values.put("memoto", t.getId());
			}

		}
		// 查询排序
		if (memo.getSort() != null && memo.getOrder() != null) {
			hql += " order by " + memo.getSort() + " " + memo.getOrder();
		}

		// 根据HQL获取记录
		List<TMemo> tList = memoDao.find(hql, values, memo.getPage(), memo.getRows());
		List<Memo> mList = new ArrayList<Memo>();
		if (tList != null && tList.size() > 0) {
			for (TMemo t : tList) {
				Memo m = new Memo();
				BeanUtils.copyProperties(t, m);
				if (t.getTAccountByMemofrom() != null) {
					m.setMemofrom(t.getTAccountByMemofrom().getUsername());
				}
				if (t.getTAccountByMemoto() != null) {
					m.setMemoto(t.getTAccountByMemoto().getUsername());
				}
				mList.add(m);
			}
		}
		dg.setRows(mList);

		// 查询总数
		String totalHql = "select count(*) " + hql;
		dg.setTotal(memoDao.count(totalHql, values));
	}

	@Override
	public void save(Memo memo) {
		TMemo tMemo = new TMemo();
		copyMemo(memo, tMemo);

		memoDao.save(tMemo);
	}

	@Override
	public void edit(Memo memo) {
		TMemo tMemo = memoDao.getForId(TMemo.class, memo.getId());
		copyMemo(memo, tMemo);
		memoDao.saveOrUpdate(tMemo);
	}

	private void copyMemo(Memo memo, TMemo tMemo) {
		BeanUtils.copyProperties(memo, tMemo);
		// 获取交班人
		if (memo.getMemofrom() != null && !memo.getMemofrom().trim().equals("")) {
			String hqlAcc = "from TAccount t where t.username='" + memo.getMemofrom() + "'";
			TAccount tAcc = userDao.get(hqlAcc);
			if (tAcc != null) {
				tMemo.setTAccountByMemofrom(tAcc);
			}
		}
		// 获取接班人
		if (memo.getMemoto() != null && !memo.getMemoto().trim().equals("")) {
			String hqlAcc = "from TAccount t where t.username='" + memo.getMemoto() + "'";
			TAccount tAcc = userDao.get(hqlAcc);
			if (tAcc != null) {
				tMemo.setTAccountByMemoto(tAcc);
			}
		}
		tMemo.setModifytime(new Date());
	}

	@Override
	public void delete(String id) {
		String hql = "delete from TMemo t where t.id='" + id + "'";
		memoDao.executeHql(hql);
	}

	@Override
	public DataGrid<Memo> getUnfinish(Memo memo) {
		DataGrid<Memo> dg = new DataGrid<Memo>();
		String hql = "from TMemo t where t.status='未完成'";
		find(memo, dg, hql);
		return dg;
	}

}
