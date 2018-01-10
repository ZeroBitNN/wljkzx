package service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import dao.BaseDaoI;
import dao.PerfDaoI;
import dao.PerfNumDaoI;
import dao.PerfParamDaoI;
import dao.UserDaoI;
import dao.impl.BaseDaoImpl;
import model.TAccount;
import model.TPerf;
import model.TPerfNum;
import model.TPerfParam;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.Perf;
import service.PerfServiceI;
import util.ResourceUtil;
import util.StringUtil;

@Service(value = "perfService")
public class PerfServiceImpl implements PerfServiceI {
	private static final Logger logger = Logger.getLogger(PerfServiceImpl.class);

	private PerfDaoI perfDao;

	public PerfDaoI getPerfDao() {
		return perfDao;
	}

	@Autowired
	public void setPerfDao(PerfDaoI perfDao) {
		this.perfDao = perfDao;
	}

	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	private PerfParamDaoI perfParamDao;

	public PerfParamDaoI getPerfParamDao() {
		return perfParamDao;
	}

	@Autowired
	public void setPerfParamDao(PerfParamDaoI perfParamDao) {
		this.perfParamDao = perfParamDao;
	}

	private PerfNumDaoI perfNumDao;

	public PerfNumDaoI getPerfNumDao() {
		return perfNumDao;
	}

	@Autowired
	public void setPerfNumDao(PerfNumDaoI perfNumDao) {
		this.perfNumDao = perfNumDao;
	}

	private BaseDaoI<String> baseDao;

	public BaseDaoI<String> getBaseDao() {
		return baseDao;
	}

	@Autowired
	public void setBaseDao(BaseDaoI<String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public DataGrid<Perf> getGrjx() {
		DataGrid<Perf> dg = new DataGrid<Perf>();
		String perfdate = StringUtil.dateToYMString(new Date());
		String hql = "from TPerf t where perfdate='" + perfdate + "'";

		List<TPerf> tList = perfDao.find(hql);
		List<Perf> pList = new ArrayList<Perf>();
		if (tList != null && tList.size() > 0) {
			String totalHql = "select count(*) " + hql;
			dg.setTotal(perfDao.count(totalHql));
			for (TPerf t : tList) {
				Perf p = new Perf();
				BeanUtils.copyProperties(t, p);
				if (t.getTAccount() != null) {
					p.setName(t.getTAccount().getUsername());
				}
				if (t.getIsperf().intValue() == 1) {
					p.setIsperf("是");
				}
				pList.add(p);
			}

			String userHql = "from TAccount t where t.username<>'admin'";
			List<TAccount> userList = userDao.find(userHql);
			if (tList.size() != userList.size()) {
				// 匹配用户ID是否一致
				for (TAccount t : userList) {
					String tempHql = "from TPerf t where t.TAccount.id='" + t.getId() + "'";
					List<TPerf> tempList = perfDao.find(tempHql);
					if (tempList == null || tempList.size() == 0) { // 如果没找到该用户则新生成数据
						TPerf tp = new TPerf();
						tp.setId(UUID.randomUUID().toString());
						tp.setTAccount(t);
						tp.setPerfdate(StringUtil.dateToYMString(new Date()));
						tp.setIsperf(new BigDecimal(1));
						perfDao.save(tp);

						Perf p = new Perf();
						BeanUtils.copyProperties(tp, p);
						p.setName(tp.getTAccount().getUsername());
						if (tp.getIsperf().intValue() == 1) {
							p.setIsperf("是");
						} else {
							p.setIsperf("否");
						}
						pList.add(p);
						dg.setTotal(dg.getTotal() + 1);
					}
				}
			}
		} else if (tList.size() == 0) {
			Long sumCreate = 0L;
			hql = "from TAccount t where t.username<>'admin'";
			List<TAccount> userList = userDao.find(hql);
			if (userList != null && userList.size() > 0) {
				for (TAccount user : userList) {
					TPerf tp = new TPerf();
					tp.setId(UUID.randomUUID().toString());
					tp.setTAccount(user);
					tp.setPerfdate(StringUtil.dateToYMString(new Date()));
					tp.setIsperf(new BigDecimal(1));
					perfDao.save(tp);

					Perf p = new Perf();
					BeanUtils.copyProperties(tp, p);
					p.setName(tp.getTAccount().getUsername());
					if (tp.getIsperf().intValue() == 1) {
						p.setIsperf("是");
					} else {
						p.setIsperf("否");
					}
					pList.add(p);
					sumCreate++;
				}
			}
			dg.setTotal(sumCreate);
		}

		dg.setRows(pList);

		return dg;
	}

	@Override
	public void saveGrjx(Perf perf) {
		if (perf.getChangeRows() != null && !perf.getChangeRows().equals("")) {
			List<Perf> pList = JSON.parseArray(perf.getChangeRows(), Perf.class);
			if (pList != null && pList.size() > 0) {
				for (Perf p : pList) {
					if (p != null) {
						TPerf tp = perfDao.getForId(TPerf.class, p.getId());
						if (p.getGrjx() != null) {
							tp.setGrjx(p.getGrjx());
						}
						if (p.getIsperf().equals("是")) {
							tp.setIsperf(new BigDecimal(1));
						} else {
							tp.setIsperf(new BigDecimal(0));
							// 将不计件人员的工单量清零
							String numHql = "from TPerfNum t where t.TAccount.id='" + tp.getTAccount().getId()
									+ "' and perfdate='" + StringUtil.dateToYMString(new Date()) + "'";
							List<TPerfNum> nList = perfNumDao.find(numHql);
							if (nList != null && nList.size() > 0) {
								for (TPerfNum t : nList) {
									t.setValue(0.0);
								}
							}
						}
					}
				}
			}
		}
		// 计算计件绩效总额和人均值
		calcPerfSumAndAvg();
	}

	private void calcPerfSumAndAvg() {
		double perfSum = 0.0, perfAvg = 0.0;
		String hql = "from TPerf t where perfdate='" + StringUtil.dateToYMString(new Date()) + "' and isperf=1";

		// 计算计件总人数
		String totalHql = "select count(*) " + hql;
		Long sumPerson = perfDao.count(totalHql);

		// 计算计件绩效总额
		List<TPerf> tList = perfDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TPerf t : tList) {
				if (t.getGrjx() != null) {
					perfSum += t.getGrjx();
				}
			}
		}

		// 获取计件占比值
		TPerfParam levelSum = perfParamDao.getForId(TPerfParam.class, "levelSum");
		logger.info("计件占总绩效百分比====" + levelSum.getPercent().doubleValue() / 100);
		perfSum = perfSum * (levelSum.getPercent().doubleValue() / 100);

		// 计算计件人均值
		perfAvg = perfSum / sumPerson;

		// 计算每档金额并保存
		for (int i = 1; i <= 4; i++) {
			TPerfParam level = perfParamDao.getForId(TPerfParam.class, "level" + i);
			if (level != null) {
				level.setValue(perfAvg * (level.getPercent().doubleValue() / 100));
			}
		}

		// 保存绩效总额和人均值
		TPerfParam perfParamSum = perfParamDao.getForId(TPerfParam.class, "perfSum");
		perfParamSum.setValue(perfSum);
		TPerfParam perfParamAvg = perfParamDao.getForId(TPerfParam.class, "perfAvg");
		perfParamAvg.setValue(perfAvg);
	}

	@Override
	public DataGrid<Perf> getPerf() {
		DataGrid<Perf> dg = new DataGrid<Perf>();
		String hql = "from TPerf t where perfdate='" + StringUtil.dateToYMString(new Date()) + "'";

		String totalHql = "select count(*) " + hql;
		dg.setTotal(perfDao.count(totalHql));

		List<TPerf> tList = perfDao.find(hql);
		List<Perf> pList = new ArrayList<Perf>();
		List<TPerf> rankList = new ArrayList<TPerf>();
		if (tList != null && tList.size() > 0) {
			for (int i = 0; i < tList.size(); i++) {
				// 计算每个人每类工单数值以及按实际占比计算工单分值
				TPerf t = calcItem(tList.get(i));
				// 计算最终汇总
				tList.get(i).setZzhz(new BigDecimal(t.getGdfz().doubleValue() + t.getOtheritem()));
				tList.get(i).setJjjx(0.0);
				tList.get(i).setKf(0.0);

				// 取出需要排名的人(需要计件的人员)
				if (tList.get(i).getIsperf().intValue() == 1) {
					rankList.add(tList.get(i));
				}
			}
			// for (TPerf t : tList) {
			// // 计算每个人每类工单数值以及按实际占比计算工单分值
			// TPerf newT = calcItem(t);
			// // 计算最终汇总
			// newT.setZzhz(new BigDecimal(newT.getGdfz().doubleValue() +
			// newT.getOtheritem()));
			// newT.setJjjx(0.0);
			// newT.setKf(0.0);
			// perfDao.saveOrUpdate(newT);
			// }
		}

		// String rankHql = "from TPerf t where perfdate='" +
		// StringUtil.dateToYMString(new Date()) + "' and isperf=1";
		// List<TPerf> rankList = perfDao.find(rankHql);

		// 计算排名
		if (rankList != null && rankList.size() > 0) {
			Collections.sort(rankList, new Comparator<TPerf>() {
				public int compare(TPerf p1, TPerf p2) {
					return p2.getZzhz().intValue() - p1.getZzhz().intValue();
				}
			});

			int rank = 0;
			for (int i = 0; i < rankList.size(); i++) {
				rankList.get(i).setRanking(Integer.toString(++rank));
			}
		}

		// 计算计件绩效和扣罚情况
		Double perfAvg = perfParamDao.getForId(TPerfParam.class, "perfAvg").getValue();
		Double level1 = perfParamDao.getForId(TPerfParam.class, "level1").getValue();
		Double level2 = perfParamDao.getForId(TPerfParam.class, "level2").getValue();
		Double level3 = perfParamDao.getForId(TPerfParam.class, "level3").getValue();
		Double level4 = perfParamDao.getForId(TPerfParam.class, "level4").getValue();
		for (int i = 0; i < rankList.size(); i++) {
			if (i == 0) {
				rankList.get(i).setJjjx(perfAvg + level1);
				rankList.get(i).setKf(level1);
			}
			if (i == 1) {
				rankList.get(i).setJjjx(perfAvg + level2);
				rankList.get(i).setKf(level2);
			}
			if (i == 2) {
				rankList.get(i).setJjjx(perfAvg + level3);
				rankList.get(i).setKf(level3);
			}
			if (i == 3) {
				rankList.get(i).setJjjx(perfAvg + level4);
				rankList.get(i).setKf(level4);
			}
			if (i == rankList.size() - 1) {
				rankList.get(i).setJjjx(perfAvg - level1);
				rankList.get(i).setKf(-(level1));
			}
			if (i == rankList.size() - 2) {
				rankList.get(i).setJjjx(perfAvg - level2);
				rankList.get(i).setKf(-(level2));
			}
			if (i == rankList.size() - 3) {
				rankList.get(i).setJjjx(perfAvg - level3);
				rankList.get(i).setKf(-(level3));
			}
			if (i == rankList.size() - 4) {
				rankList.get(i).setJjjx(perfAvg - level4);
				rankList.get(i).setKf(-(level4));
			}
			if (i > 3 && i < rankList.size() - 4) {
				rankList.get(i).setJjjx(perfAvg);
				rankList.get(i).setKf(0.0);
			}
		}

		// 保存排名、计件绩效和扣罚情况
		for (TPerf t : rankList) {
			perfDao.save(t);
			Perf p = new Perf();
			BeanUtils.copyProperties(t, p);
			p.setName(t.getTAccount().getUsername());
			p.setRanking(Integer.parseInt(t.getRanking()));
			if (t.getIsperf().intValue() == 1) {
				p.setIsperf("是");
			} else {
				p.setIsperf("否");
			}
			pList.add(p);
		}

		dg.setRows(pList);

		return dg;
	}

	// private void calcRanking() {
	// String rankSql = "update t_perf tp set tp.ranking=(select t.ranking from
	// (select id,dense_rank() over (order by zzhz desc) ranking from t_perf
	// where perfdate='"
	// + StringUtil.dateToYMString(new Date()) + "') t where t.id=tp.id)";
	// perfDao.excuteSql(rankSql);
	// }

	/**
	 * 计算每个人每类工单数值以及按实际占比计算工单分值
	 * 
	 * @param t
	 * @return
	 */
	private TPerf calcItem(TPerf t) {
		double gdfz = 0L;

		// 获取个人父类工单总数
		double pItemSum = 0.0;
		if (t.getItem1sum() != null) {
			pItemSum = t.getItem1sum();
		}
		// 获取全部父类工单总数
		double itemSum = 0.0;
		TPerfParam tp = perfParamDao.get("from TPerfParam t where id='item1'");
		if (tp.getValue() != null) {
			itemSum = tp.getValue();
		}
		// 计算数值
		if (itemSum != 0.0) {
			t.setItem1((pItemSum / itemSum) * 100);
		} else {
			t.setItem1(0.0);
		}
		if (tp.getPercent() != null) {
			gdfz += (t.getItem1().doubleValue() / 100) * (tp.getPercent().doubleValue() / 100);
		}

		// 获取个人父类工单总数
		pItemSum = 0.0;
		if (t.getItem2sum() != null) {
			pItemSum = t.getItem2sum();
		}
		// 获取全部父类工单总数
		itemSum = 0.0;
		tp = perfParamDao.get("from TPerfParam t where id='item2'");
		if (tp.getValue() != null) {
			itemSum = tp.getValue();
		}
		// 计算数值
		if (itemSum != 0.0) {
			t.setItem2((pItemSum / itemSum) * 100);
		} else {
			t.setItem2(0.0);
		}
		if (tp.getPercent() != null) {
			gdfz += (t.getItem2().doubleValue() / 100) * (tp.getPercent().doubleValue() / 100);
		}

		// 获取个人父类工单总数
		pItemSum = 0.0;
		if (t.getItem3sum() != null) {
			pItemSum = t.getItem3sum();
		}
		// 获取全部父类工单总数
		itemSum = 0.0;
		tp = perfParamDao.get("from TPerfParam t where id='item3'");
		if (tp.getValue() != null) {
			itemSum = tp.getValue();
		}
		// 计算数值
		if (itemSum != 0.0) {
			t.setItem3((pItemSum / itemSum) * 100);
		} else {
			t.setItem3(0.0);
		}
		if (tp.getPercent() != null) {
			gdfz += (t.getItem3().doubleValue() / 100) * (tp.getPercent().doubleValue() / 100);
		}

		// 获取个人父类工单总数
		pItemSum = 0.0;
		if (t.getItem4sum() != null) {
			pItemSum = t.getItem4sum();
		}
		// 获取全部父类工单总数
		itemSum = 0.0;
		tp = perfParamDao.get("from TPerfParam t where id='item4'");
		if (tp.getValue() != null) {
			itemSum = tp.getValue();
		}
		// 计算数值
		if (itemSum != 0.0) {
			t.setItem4((pItemSum / itemSum) * 100);
		} else {
			t.setItem4(0.0);
		}
		if (tp.getPercent() != null) {
			gdfz += (t.getItem4().doubleValue() / 100) * (tp.getPercent().doubleValue() / 100);
		}

		tp = perfParamDao.get("from TPerfParam t where id='levelSum'");
		if (tp.getValue() != null) {
			gdfz = gdfz * tp.getValue().doubleValue();
		}

		t.setGdfz(new BigDecimal(gdfz));
		return t;
	}

	@Override
	public Json importGrjx(HttpServletRequest request) {
		Json j = new Json();
		// 上传文件总大小
		Long fileSize = Long.valueOf(request.getHeader("Content-Length"));
		MultiPartRequestWrapper multiPartRequest = (MultiPartRequestWrapper) request;// 由于struts2上传文件时自动使用了request封装
		File[] files = multiPartRequest.getFiles(ResourceUtil.getUploadFieldName());// 上传的文件集合
		String[] fileNames = multiPartRequest.getFileNames(ResourceUtil.getUploadFieldName());// 上传文件名称集合

		// 校验文件
		if (files == null || files.length < 1) {
			j.setMsg("您没有上传任何文件！");
			return j;
		}
		if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
			j.setMsg("上传文件总大小超出限制！");
			return j;
		}

		// 读取文件内容
		for (int i = 0; i < files.length; i++) {
			// 校验文件扩展名
			String fileExt = fileNames[i].substring(fileNames[i].lastIndexOf(".") + 1).toLowerCase();
			if (!fileExt.equals("xlsx") && !fileExt.equals("xls")) {
				j.setMsg("只允许导入xlsx、xls格式文件！");
				return j;
			}
			// 获取工作薄
			Workbook workbook = null;
			if (fileExt.equals("xlsx")) {
				// 2007版本的excel，用.xlsx结尾
				try {
					workbook = new XSSFWorkbook(files[i]);
				} catch (InvalidFormatException e) {
					j.setMsg(e.getMessage());
					return j;
				} catch (IOException e) {
					j.setMsg(e.getMessage());
					return j;
				}
			} else if (fileExt.equals("xls")) {
				// 2003版本的excel，用.xls结尾
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(files[i]);
					workbook = new HSSFWorkbook(fis);
				} catch (FileNotFoundException e) {
					j.setMsg(e.getMessage());
					return j;
				} catch (IOException e) {
					j.setMsg(e.getMessage());
					return j;
				} finally {
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			// 获取工作表
			Sheet sheet = workbook.getSheetAt(0);
			// 获取表头
			Row rowHead = sheet.getRow(1);
			// 校验表头是否正确
			if (rowHead.getPhysicalNumberOfCells() != 2) {
				j.setMsg("表头数量不正确，请使用正确模板导入数据！");
				return j;
			}
			if (!rowHead.getCell(0).getStringCellValue().equals("姓名")
					|| !rowHead.getCell(1).getStringCellValue().equals("个人绩效")) {
				j.setMsg("表头字段不正确，请使用正确模板导入数据！");
				return j;
			}
			// 获得数据的总行数
			int totalRowNum = sheet.getLastRowNum();
			// 获得所有数据
			TAccount user = null;
			for (int k = 2; k <= totalRowNum; k++) {
				TPerf t = null;
				Row row = sheet.getRow(k);
				String userHql = "from TAccount t where t.username='" + row.getCell(0).getStringCellValue() + "'";
				user = userDao.get(userHql);
				if (user != null) {
					String tHql = "from TPerf t where t.TAccount.id='" + user.getId() + "' and t.perfdate='"
							+ StringUtil.dateToYMString(new Date()) + "'";
					t = perfDao.get(tHql);
					if (t != null) {
						try {
							t.setGrjx(row.getCell(1).getNumericCellValue());
						} catch (Exception e) {
							try {
								t.setGrjx(Double.valueOf(row.getCell(1).getStringCellValue()));
							} catch (NumberFormatException e1) {
								t.setGrjx(0.0);
							}
						} finally {
							perfDao.save(t);
						}
					}
				}
			}
		}
		// 数据导入成功
		j.setSuccess(true);
		j.setMsg("数据导入成功！<b>请点击保存按钮保存数据！</b>");

		return j;
	}

	@Override
	public List<String> getPerfDate() {
		String hql = "select distinct perfdate from TPerf t";
		return baseDao.findForSql(hql);
	}

	@Override
	public List<TPerf> getPerfForDate(String perfdate) {
		String hql = "from TPerf t where perfdate='" + perfdate + "' and isperf=1";
		return perfDao.find(hql);
	}

}
