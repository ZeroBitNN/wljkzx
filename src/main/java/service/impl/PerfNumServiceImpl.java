package service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import dao.PerfDaoI;
import dao.PerfNumDaoI;
import dao.PerfParamDaoI;
import dao.UserDaoI;
import model.TAccount;
import model.TPerf;
import model.TPerfNum;
import model.TPerfParam;
import pageModel.DataGrid;
import pageModel.Json;
import pageModel.PerfNum;
import service.PerfNumServiceI;
import util.ResourceUtil;
import util.StringUtil;

@Service(value = "perfNumService")
public class PerfNumServiceImpl implements PerfNumServiceI {
	private static final Logger logger = Logger.getLogger(PerfNumServiceImpl.class);

	private PerfNumDaoI perfNumDao;

	public PerfNumDaoI getPerfNumDao() {
		return perfNumDao;
	}

	@Autowired
	public void setPerfNumDao(PerfNumDaoI perfNumDao) {
		this.perfNumDao = perfNumDao;
	}

	private PerfParamDaoI perfParamDao;

	public PerfParamDaoI getPerfParamDao() {
		return perfParamDao;
	}

	@Autowired
	public void setPerfParamDao(PerfParamDaoI perfParamDao) {
		this.perfParamDao = perfParamDao;
	}

	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	private PerfDaoI perfDao;

	public PerfDaoI getPerfDao() {
		return perfDao;
	}

	@Autowired
	public void setPerfDao(PerfDaoI perfDao) {
		this.perfDao = perfDao;
	}

	@Override
	public DataGrid<PerfNum> getDatagrid(PerfNum perfNum) {
		DataGrid<PerfNum> dg = new DataGrid<PerfNum>();
		String hql = "from TPerfNum t where 1=1";
		if (perfNum.getPerfdate() != null && !perfNum.getPerfdate().equals("")) {
			hql += " and t.perfdate='" + perfNum.getPerfdate() + "'";
		} else {
			// 读取当前月份数据
			String perfdate = StringUtil.dateToYMString(new Date());
			logger.info("读取当前月份 (" + perfdate + ") 的数据");
			hql += " and t.perfdate='" + perfdate + "' order by itemgroup";
		}

		String totalHql = "select count(*) " + hql;
		dg.setTotal(perfNumDao.count(totalHql));

		String userHql = "from TAccount t where t.username<>'admin'";
		List<TAccount> userList = userDao.find(userHql);

		List<TPerfNum> tList = perfNumDao.find(hql);
		List<PerfNum> pList = new ArrayList<PerfNum>();
		if (tList != null && tList.size() > 0) {
			// 如果有数据则读取
			for (TPerfNum t : tList) {
				PerfNum p = new PerfNum();
				BeanUtils.copyProperties(t, p);
				if (t.getTAccount() != null) {
					p.setName(t.getTAccount().getUsername());
				}
				p.setItem(t.getTPerfParam().getName());
				pList.add(p);
			}
			// 匹配用户ID是否一致
			for (TAccount t : userList) {
				String tempHql = "from TPerfNum t where t.TAccount.id='" + t.getId() + "'";
				List<TPerfNum> tempList = perfNumDao.find(tempHql);
				if (tempList == null || tempList.size() == 0) { // 如果没找到该用户则新生成数据
					String itemHql = "from TPerfParam t where type='类目' and pid is not null";
					List<TPerfParam> itemList = perfParamDao.find(itemHql);
					for (TPerfParam item : itemList) {
						TPerfNum tp = new TPerfNum();
						tp.setId(UUID.randomUUID().toString());
						tp.setTAccount(t);
						tp.setTPerfParam(item);
						tp.setPerfdate(StringUtil.dateToYMString(new Date()));
						tp.setItemgroup(item.getTPerfParam().getName());
						perfNumDao.save(tp);

						PerfNum p = new PerfNum();
						BeanUtils.copyProperties(tp, p);
						p.setName(t.getUsername());
						p.setItem(item.getName());
						pList.add(p);
						dg.setTotal(dg.getTotal() + 1);
					}
				}
			}

			// 清空工单数量
			// clearParamValue("from TPerfParam t where type='类目'");
			// clearParamValue("from TPerfParam t where id='levelSum'");
		} else if (tList.size() == 0) {
			Long sumCreate = 0L;
			// 如果没有数据则初始化当月数据
			if (userList != null && userList.size() > 0) {
				for (TAccount user : userList) {
					String itemHql = "from TPerfParam t where type='类目' and pid is not null";
					List<TPerfParam> itemList = perfParamDao.find(itemHql);
					if (itemList != null && itemList.size() > 0) {
						for (TPerfParam item : itemList) {
							TPerfNum tp = new TPerfNum();
							tp.setId(UUID.randomUUID().toString());
							tp.setTAccount(user);
							tp.setTPerfParam(item);
							tp.setPerfdate(StringUtil.dateToYMString(new Date()));
							tp.setItemgroup(item.getTPerfParam().getName());
							perfNumDao.save(tp);

							PerfNum p = new PerfNum();
							BeanUtils.copyProperties(tp, p);
							p.setName(user.getUsername());
							p.setItem(item.getName());
							pList.add(p);
							sumCreate++;
						}
					}
				}
			}
			dg.setTotal(sumCreate);

			// 清空工单数量
			clearParamValue("from TPerfParam t where type='类目'");
			clearParamValue("from TPerfParam t where id='levelSum'");
		}

		dg.setRows(pList);

		return dg;
	}

	@Override
	public void saveNum(PerfNum perfNum) {
		// 录入的数据
		if (perfNum.getChangeRows() != null && !perfNum.getChangeRows().equals("")) {
			List<PerfNum> itemList = JSON.parseArray(perfNum.getChangeRows(), PerfNum.class);
			if (itemList != null && itemList.size() > 0) {
				for (PerfNum p : itemList) {
					if (p != null) {
						TPerfNum tp = perfNumDao.getForId(TPerfNum.class, p.getId());
						if (p.getPercent() != null) {
							tp.setPercent(p.getPercent());
						}
						if (p.getValue() != null) {
							tp.setValue(p.getValue());
						}
					}
				}
			}
		}

		// 将个人工单量算入子类工单总量
		calcSubItem();
		// 计算个人父类工单总量
		calcPersonItemSum();
		// 计算所有人父类工单总量
		calcParentItem();
		// 计算所有工单总量
		calcItemSum();
	}

	private void calcPersonItemSum() {
		String hql = "from TPerf t where perfdate='" + StringUtil.dateToYMString(new Date()) + "'";
		List<TPerf> tList = perfDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TPerf t : tList) {
				// 先清空每个人原有值
				t.setItem1sum(0);
				t.setItem2sum(0);
				t.setItem3sum(0);
				t.setItem4sum(0);
				t.setOtheritem(0.0);
				// 获取该人的所有工单量
				String numHql = "from TPerfNum t where t.TAccount.id='" + t.getTAccount().getId() + "' and perfdate='"
						+ StringUtil.dateToYMString(new Date()) + "'";
				List<TPerfNum> numList = perfNumDao.find(numHql);
				if (numList != null && numList.size() > 0) {
					for (TPerfNum tpn : numList) {
						logger.info(tpn.getTPerfParam().getTPerfParam().getId());
						switch (tpn.getTPerfParam().getTPerfParam().getId()) {
						case "item1":
							if (tpn.getValue() != null) {
								t.setItem1sum(t.getItem1sum() + tpn.getValue().intValue());
							}
							break;
						case "item2":
							if (tpn.getValue() != null) {
								t.setItem2sum(t.getItem2sum() + tpn.getValue().intValue());
							}
							break;
						case "item3":
							if (tpn.getValue() != null) {
								t.setItem3sum(t.getItem3sum() + tpn.getValue().intValue());
							}
							break;
						case "item4":
							if (tpn.getValue() != null) {
								t.setItem4sum(t.getItem4sum() + tpn.getValue().intValue());
							}
							break;
						case "otheritem":
							if (tpn.getValue() != null) {
								t.setOtheritem(t.getOtheritem() + tpn.getValue().doubleValue());
							}
							break;
						default:
							break;
						}
					}
				}
			}
		}

	}

	private void calcItemSum() {
		// 先清空所有工单总量
		clearParamValue("from TPerfParam t where id='levelSum'");

		String hql = "from TPerfParam t where type='类目' and pid is null";
		List<TPerfParam> iList = perfParamDao.find(hql);
		if (iList != null && iList.size() > 0) {
			for (TPerfParam t : iList) {
				if (t.getValue() != null) {
					TPerfParam itemSum = perfParamDao.get("from TPerfParam t where id='levelSum'");
					if (itemSum.getValue() != null) {
						itemSum.setValue(itemSum.getValue() + t.getValue());
					} else {
						itemSum.setValue(t.getValue());
					}
				}
			}
		}
	}

	private void calcParentItem() {
		// 先清空父类目原有值
		clearParamValue("from TPerfParam t where type='类目' and pid is null");

		String hql = "from TPerfParam t where type='类目' and pid is not null";
		List<TPerfParam> tList = perfParamDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TPerfParam t : tList) {
				if (t.getValue() != null) {
					TPerfParam pItem = perfParamDao.getForId(TPerfParam.class, t.getTPerfParam().getId());
					if (pItem.getValue() != null) {
						pItem.setValue(pItem.getValue() + t.getValue());
					} else {
						pItem.setValue(t.getValue());
					}
				}
			}
		}
	}

	private void calcSubItem() {
		// 先清空子类目原有值
		clearParamValue("from TPerfParam t where type='类目' and pid is not null");

		String hql = "from TPerfNum t where perfdate='" + StringUtil.dateToYMString(new Date()) + "'";
		List<TPerfNum> tList = perfNumDao.find(hql);
		if (tList != null && tList.size() > 0) {
			for (TPerfNum t : tList) {
				if (t.getValue() != null) {
					TPerfParam sItem = perfParamDao.getForId(TPerfParam.class, t.getTPerfParam().getId());
					if (sItem.getValue() != null) {
						sItem.setValue(sItem.getValue() + t.getValue());
					} else {
						sItem.setValue(t.getValue());
					}
				}
			}
		}
	}

	private void clearParamValue(String hql) {
		List<TPerfParam> paramList = perfParamDao.find(hql);
		if (paramList != null && paramList.size() > 0) {
			for (TPerfParam t : paramList) {
				t.setValue(0.0);
			}
		}
	}

	@Override
	public Json importExcel(HttpServletRequest request) {
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
			// logger.info(rowHead.getPhysicalNumberOfCells());// 3
			String hql = "from TPerfParam t where t.type='类目' and t.TPerfParam.id is not null";
			List<TPerfParam> itemList = perfParamDao.find(hql);
			if (itemList != null && itemList.size() > 0) {
				// 将工单类名存为列表
				List<String> itemNameList = new ArrayList<String>();
				for (TPerfParam t : itemList) {
					itemNameList.add(t.getName());
				}
				// 校验表头数量
				if (rowHead.getPhysicalNumberOfCells() != itemList.size() + 1) {
					j.setMsg("表头数量不正确，请使用正确模板导入数据！");
					return j;
				}
				// 校验表头字段
				for (int k = 0; k < rowHead.getPhysicalNumberOfCells(); k++) {
					Cell cell = rowHead.getCell(k);
					if (k == 0) {
						if (!cell.getStringCellValue().equals("姓名")) {
							j.setMsg("表头字段不正确，请使用正确模板导入数据！");
							return j;
						}
					} else {
						if (!itemNameList.contains(cell.getStringCellValue())) {
							logger.info("=====" + cell.getStringCellValue() + "字段不正确=====");
							j.setMsg("表头字段不正确，请使用正确模板导入数据！");
							return j;
						}
					}
				}
			}
			// 获得数据的总行数
			int totalRowNum = sheet.getLastRowNum();
			// 获得所有数据
			TAccount user = null;
			for (int k = 2; k <= totalRowNum; k++) { // 从第3行开始读取数据
				TPerfParam item = null;
				Row row = sheet.getRow(k);
				for (int l = 0; l < row.getPhysicalNumberOfCells(); l++) {
					Cell cell = row.getCell(l);
					if (l == 0) {
						// 获取用户
						String userHql = "from TAccount t where t.username='" + cell.getStringCellValue() + "'";
						user = userDao.get(userHql);
						logger.info("===========导入" + user.getUsername() + "的数据============");
					} else {
						// 通过表头获取字段名
						Cell headCell = rowHead.getCell(l);
						// 获取该字段
						String itemHql = "from TPerfParam t where t.name='" + headCell.getStringCellValue()
								+ "' and t.TPerfParam.id is not null";
						item = perfParamDao.get(itemHql);
						// logger.info("...导入" + headCell.getStringCellValue()
						// +"类数据...");
						// 通过用户和字段获取工单
						if (user != null && item != null) {
							String numHql = "from TPerfNum t where t.TAccount.id='" + user.getId()
									+ "' and t.TPerfParam.id='" + item.getId() + "' and t.perfdate='"
									+ StringUtil.dateToYMString(new Date()) + "'";
							TPerfNum perfNum = perfNumDao.get(numHql);
							// 将数据存入数据库
							if (perfNum != null) {
								try {
									perfNum.setValue(cell.getNumericCellValue());
								} catch (Exception e) {
									try {
										perfNum.setValue(Double.valueOf(cell.getStringCellValue()));
									} catch (NumberFormatException e1) {
										perfNum.setValue(0.0);
									}
								} finally {
									perfNumDao.save(perfNum);
								}
							}
						}
					}
				}
				logger.info("===========" + user.getUsername() + "的数据导入完成============");
			}
		}
		// 数据导入成功
		j.setSuccess(true);
		j.setMsg("数据导入成功！<b>请点击保存按钮保存数据！</b>");

		return j;
	}

	@Override
	public List<TPerfNum> getPerfNum(String id, String perfdate) {
		String hql = "from TPerfNum t where t.TAccount.id='" + id + "' and t.perfdate='" + perfdate + "' order by itemgroup";
		return perfNumDao.find(hql);
	}

}
