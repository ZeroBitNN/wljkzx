package service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BaseDaoI;
import dao.OwogJkbDaoI;
import model.TOwogJkb;
import owogModel.OwogJkb;
import pageModel.Json;
import service.OwogJkbServiceI;
import util.ResourceUtil;
import util.StringUtil;

@Service(value = "owogJkbService")
public class OwogJkbServiceImpl implements OwogJkbServiceI {

	private static final Logger logger = Logger.getLogger(OwogJkbServiceImpl.class);

	private OwogJkbDaoI owogJkbDao;

	public OwogJkbDaoI getOwogJkbDao() {
		return owogJkbDao;
	}

	@Autowired
	public void setOwogJkbDao(OwogJkbDaoI owogJkbDao) {
		this.owogJkbDao = owogJkbDao;
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
	public List<OwogJkb> getDatagrid(OwogJkb owogJkb) {
		List<OwogJkb> list = new ArrayList<OwogJkb>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TOwogJkb t where 1=1";

		if (owogJkb.getStartdate() != null) {
			// 有指定则按时间查询
			hql += " and t.startdate>=:startdate";
			params.put("startdate", owogJkb.getStartdate());
		} else {
			// 没有指定时间则按当前时间查询
			hql += " and t.startdate>=:startdate";
			params.put("startdate", StringUtil.getMondayDate(new Date()));
		}
		if (owogJkb.getEnddate() != null) {
			// 有指定则按时间查询
			hql += " and t.enddate<=:enddate";
			params.put("enddate", owogJkb.getEnddate());
		} else {
			// 没有指定时间则按当前时间查询
			hql += " and t.enddate<=:enddate";
			params.put("enddate", StringUtil.getWeekendDate(new Date()));
		}
		hql += " order by t.ranking asc";

		List<TOwogJkb> tList = owogJkbDao.find(hql, params);
		if (tList != null && tList.size() > 0) {
			for (TOwogJkb t : tList) {
				OwogJkb o = new OwogJkb();
				BeanUtils.copyProperties(t, o);
				list.add(o);
			}
		} else {
			TOwogJkb tempT = new TOwogJkb();
			tempT.setId(UUID.randomUUID().toString());
			tempT.setStartdate(StringUtil.getMondayDate(new Date()));
			tempT.setEnddate(StringUtil.getWeekendDate(new Date()));
			tempT.setRangetime(StringUtil.getMonday(new Date()) + "至" + StringUtil.getWeekend(new Date()));
			owogJkbDao.save(tempT);
			OwogJkb tempO = new OwogJkb();
			BeanUtils.copyProperties(tempT, tempO);
			list.add(tempO);
		}

		return list;
	}

	@Override
	public void edit(OwogJkb owogJkb) {
		TOwogJkb toj = owogJkbDao.getForId(TOwogJkb.class, owogJkb.getId());
		BeanUtils.copyProperties(owogJkb, toj, new String[] { "id" });
	}

	@Override
	public Json importExcel(HttpServletRequest request) {
		Json j = new Json();
		// 上传文件总大小
		Long fileSize = Long.valueOf(request.getHeader("Content-Length"));
		MultiPartRequestWrapper multiPartRequest = (MultiPartRequestWrapper) request;// 由于struts2上传文件时自动使用了request封装
		File[] files = multiPartRequest.getFiles(ResourceUtil.getUploadFieldName());// 上传的文件集合
		String[] fileNames = multiPartRequest.getFileNames(ResourceUtil.getUploadFieldName());// 上传文件名称集合

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
			// 获取模板表头
			Row templateRowHead = getTemplateRowHead("owog/template/支撑班一周一标杆录入模板.xlsx");
			// 校验表头数量
			if (rowHead.getPhysicalNumberOfCells() != templateRowHead.getPhysicalNumberOfCells()) {
				j.setMsg("表头数量不正确，请使用正确模板导入数据！");
				return j;
			}
			// 校验表头字段
			for (int k = 0; k < rowHead.getPhysicalNumberOfCells(); k++) {
				Cell cell = rowHead.getCell(k);
				Cell templateCell = templateRowHead.getCell(k);
				if (!cell.getStringCellValue().equals(templateCell.getStringCellValue())) {
					j.setMsg("表头字段不正确，请使用正确模板导入数据！");
					return j;
				}
			}
			// 获得数据的总行数
			int totalRowNum = sheet.getLastRowNum();
			// 获得所有数据
			for (int k = 2; k <= totalRowNum; k++) { // 从第3行开始读取数据
				Row row = sheet.getRow(k);
				TOwogJkb importT = new TOwogJkb();
				Cell startCell = row.getCell(0);
				Cell endCell = row.getCell(1);
				// 检查是否有该时间周期数据
				Map<String, Object> params = new HashMap<String, Object>();
				String hql = "from TOwogJkb t where t.startdate = :startdate and t.enddate = :enddate";
				params.put("startdate", startCell.getDateCellValue());
				params.put("enddate", endCell.getDateCellValue());
				List<TOwogJkb> tList = owogJkbDao.find(hql, params);
				if (tList != null && tList.size() > 0) {
					// 如果有则清除
					for (TOwogJkb t : tList) {
						owogJkbDao.delete(t);
					}
				}
				// 保存时间周期
				importT.setStartdate(startCell.getDateCellValue());
				importT.setEnddate(endCell.getDateCellValue());
				importT.setRangetime(
						sdf.format(startCell.getDateCellValue()) + "至" + sdf.format(endCell.getDateCellValue()));
				// 保存姓名
				importT.setName(row.getCell(2).getStringCellValue());
				// 保存指标11
				try {
					importT.setZb11(new BigDecimal(row.getCell(3).getNumericCellValue()));
				} catch (Exception e) {
					importT.setZb11(new BigDecimal(row.getCell(3).getStringCellValue()));
				}
				// 保存指标12
				try {
					importT.setZb12(new BigDecimal(row.getCell(4).getNumericCellValue()));
				} catch (Exception e) {
					importT.setZb12(new BigDecimal(row.getCell(4).getStringCellValue()));
				}
				// 保存指标21
				try {
					importT.setZb21(new BigDecimal(row.getCell(5).getNumericCellValue()));
				} catch (Exception e) {
					importT.setZb21(new BigDecimal(row.getCell(5).getStringCellValue()));
				}
				// 保存指标22
				try {
					importT.setZb22(new BigDecimal(row.getCell(6).getNumericCellValue()));
				} catch (Exception e) {
					importT.setZb22(new BigDecimal(row.getCell(6).getStringCellValue()));
				}
				// 保存指标31
				try {
					importT.setZb31(new BigDecimal(row.getCell(7).getNumericCellValue()));
				} catch (Exception e) {
					importT.setZb31(new BigDecimal(row.getCell(7).getStringCellValue()));
				}
				// 保存指标32
				try {
					importT.setZb32(new BigDecimal(row.getCell(8).getNumericCellValue()));
				} catch (Exception e) {
					importT.setZb32(new BigDecimal(row.getCell(8).getStringCellValue()));
				}
				// 保存指标41
				try {
					importT.setZb41(new BigDecimal(row.getCell(9).getNumericCellValue()));
				} catch (Exception e) {
					importT.setZb41(new BigDecimal(row.getCell(9).getStringCellValue()));
				}
				// 保存指标42
				try {
					importT.setZb42(new BigDecimal(row.getCell(10).getNumericCellValue()));
				} catch (Exception e) {
					importT.setZb42(new BigDecimal(row.getCell(10).getStringCellValue()));
				}
				// 保存领导评分
				try {
					importT.setLdpf(new BigDecimal(row.getCell(11).getNumericCellValue()));
				} catch (Exception e) {
					importT.setLdpf(new BigDecimal(row.getCell(11).getStringCellValue()));
				}
				// 保存加分1
				try {
					importT.setJf1(new BigDecimal(row.getCell(12).getNumericCellValue()));
				} catch (Exception e) {
					importT.setJf1(new BigDecimal(row.getCell(12).getStringCellValue()));
				}
				// 保存加分2
				try {
					importT.setJf2(new BigDecimal(row.getCell(13).getNumericCellValue()));
				} catch (Exception e) {
					importT.setJf2(new BigDecimal(row.getCell(13).getStringCellValue()));
				}
				// 保存加分3
				try {
					importT.setJf3(new BigDecimal(row.getCell(14).getNumericCellValue()));
				} catch (Exception e) {
					importT.setJf3(new BigDecimal(row.getCell(14).getStringCellValue()));
				}
				// 保存加分4
				try {
					importT.setJf4(new BigDecimal(row.getCell(15).getNumericCellValue()));
				} catch (Exception e) {
					importT.setJf4(new BigDecimal(row.getCell(15).getStringCellValue()));
				}
				// 保存加分5
				try {
					importT.setJf5(new BigDecimal(row.getCell(16).getNumericCellValue()));
				} catch (Exception e) {
					importT.setJf5(new BigDecimal(row.getCell(16).getStringCellValue()));
				}
				// 保存加分6
				try {
					importT.setJf6(new BigDecimal(row.getCell(17).getNumericCellValue()));
				} catch (Exception e) {
					importT.setJf6(new BigDecimal(row.getCell(17).getStringCellValue()));
				}
				// 保存一票否决1
				importT.setYpfj1(row.getCell(18).getStringCellValue());
				// 保存一票否决2
				importT.setYpfj2(row.getCell(19).getStringCellValue());
				// 保存一票否决3
				importT.setYpfj3(row.getCell(20).getStringCellValue());
				// 保存一票否决4
				importT.setYpfj4(row.getCell(21).getStringCellValue());
				// 保存一票否决5
				importT.setYpfj5(row.getCell(22).getStringCellValue());
				importT.setId(UUID.randomUUID().toString());
				owogJkbDao.save(importT);
			}
		}
		// 数据导入成功
		j.setSuccess(true);
		j.setMsg("数据导入成功！");

		return j;
	}

	private Row getTemplateRowHead(String templateFilePath) {
		Row rowHead = null;
		String filePathName = ServletActionContext.getServletContext().getRealPath("/") + templateFilePath;
		File templateFile = new File(filePathName);
		String fileName = templateFile.getName();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

		Workbook workbook = null;
		if (fileExt.equals("xlsx")) {
			try {
				workbook = new XSSFWorkbook(templateFile);
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (fileExt.equals("xls")) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(templateFile);
				workbook = new HSSFWorkbook(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
		if (workbook != null) {
			Sheet sheet = workbook.getSheetAt(0);
			rowHead = sheet.getRow(1);
		}

		return rowHead;
	}

	@Override
	public void calc() throws Exception {
		// 获取本周指标分数
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TOwogJkb t where t.startdate>=:startdate and t.enddate<=:enddate";
		params.put("startdate", StringUtil.getMondayDate(new Date()));
		params.put("enddate", StringUtil.getWeekendDate(new Date()));
		List<TOwogJkb> tList = owogJkbDao.find(hql, params);
		if (tList != null && tList.size() > 0) {
			List<TOwogJkb> rankList = new ArrayList<TOwogJkb>();
			for (TOwogJkb t : tList) {
				// 计算权重指标分数合计qzzbsum
				int zb11 = t.getZb11().intValue();
				int zb12 = t.getZb12().intValue();
				int zb21 = t.getZb21().intValue();
				int zb22 = t.getZb22().intValue();
				int zb31 = t.getZb31().intValue();
				int zb32 = t.getZb32().intValue();
				int zb41 = t.getZb41().intValue();
				int zb42 = t.getZb42().intValue();
				int ldpf = t.getLdpf().intValue();
				int qzzbsum = zb11 + zb12 + zb21 + zb22 + zb31 + zb32 + zb41 + zb42 + ldpf;
				t.setQzzbsum(new BigDecimal(qzzbsum));
				// 计算加分后所有分数合计allsum
				int jf1 = t.getJf1().intValue();
				int jf2 = t.getJf2().intValue();
				int jf3 = t.getJf3().intValue();
				int jf4 = t.getJf4().intValue();
				int jf5 = t.getJf5().intValue();
				int jf6 = t.getJf6().intValue();
				int allsum = qzzbsum + jf1 + jf2 + jf3 + jf4 + jf5 + jf6;
				t.setAllsum(new BigDecimal(allsum));
				owogJkbDao.saveOrUpdate(t);
				// 判断是否有一票否决的情况
				if (!t.getYpfj1().equals("是") && !t.getYpfj2().equals("是") && !t.getYpfj3().equals("是")
						&& !t.getYpfj4().equals("是") && !t.getYpfj5().equals("是")) {
					rankList.add(t);
				}
			}
			// 计算排名
			if (rankList != null && rankList.size() > 0) {
				// 排序
				Collections.sort(rankList, new Comparator<TOwogJkb>() {
					@Override
					public int compare(TOwogJkb o1, TOwogJkb o2) {
						return o2.getAllsum().intValue() - o1.getAllsum().intValue();
					}
				});

				// 根据排序计算排名
				int rank = 0;
				for (int i = 0; i < rankList.size(); i++) {
					rankList.get(i).setRanking(new BigDecimal(++rank));
					// 计算后保存数据
					owogJkbDao.saveOrUpdate(rankList.get(i));
				}
			}

		} else {
			throw new Exception("本周没有数据，请导入数据后再计算！");
		}
	}

	@Override
	public List<String> getRangetime() {
		String hql = "select distinct rangetime from TOwogJkb t";
		return baseDao.findForSql(hql);
	}
}
