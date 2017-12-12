package service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import pageModel.DataGrid;
import pageModel.DeptJjbb;
import service.DeptJjbbServiceI;
import util.DBCPUtils;

@Service(value = "deptJjbbService")
public class DeptJjbbServiceImpl implements DeptJjbbServiceI {
	private static final Logger log = Logger.getLogger(DeptJjbbServiceImpl.class);

	@Override
	public DataGrid<DeptJjbb> getDatagrid(DeptJjbb deptJjbb) {
		DataGrid<DeptJjbb> dg = new DataGrid<DeptJjbb>();
		String totalSql = "SELECT COUNT(*) from jjbb where 1=1";
		Long total = (long) getTotal(totalSql);
		List<DeptJjbb> dList = new ArrayList<DeptJjbb>();

		String sql = "";
		// 分页
		if (deptJjbb.getPage()==1){
			sql = "select top " + deptJjbb.getRows() + " * from jjbb where 1=1";
		}else{
			sql = "select top " + deptJjbb.getRows() + " * from jjbb where ID not in(select top "
					+ (deptJjbb.getRows() * (deptJjbb.getPage() - 1)) + " ID from jjbb order by ID desc)";
		}
		// 是否按标题(title)搜索
		if (deptJjbb.getTitle() != null && !deptJjbb.getTitle().equals("")) {
			String title = deptJjbb.getTitle();
			totalSql += " and title like '%" + title + "%'";
			total = (long) getTotal(totalSql);
			sql += " and title like '%" + title + "%'";
		}
		// 是否按内容(content)搜索
		if (deptJjbb.getContent() != null && !deptJjbb.getContent().equals("")) {
			String content = deptJjbb.getContent();
			totalSql += " and content like '%" + content + "%'";
			total = (long) getTotal(totalSql);
			sql += " and content like '%" + content + "%'";
		}
		// 排序
		if (deptJjbb.getSort() != null && deptJjbb.getOrder() != null) {
			sql += " order by " + deptJjbb.getSort() + " " + deptJjbb.getOrder();
		}
		
		log.info(sql);
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = DBCPUtils.getConnectionPool();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				DeptJjbb dJjbb = new DeptJjbb();
				dJjbb.setId(rs.getString("id"));
				dJjbb.setTitle(rs.getString("标题"));
				dJjbb.setSerialNum(rs.getString("工单编号"));
				dJjbb.setDatetime(rs.getString("日期"));
				dJjbb.setNotifier(rs.getString("发起通知人"));
				dJjbb.setType(rs.getString("类型"));
				dJjbb.setDomain(rs.getString("专业"));
				dList.add(dJjbb);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			DBCPUtils.release(rs, state, conn);
		}

		dg.setTotal(total);
		dg.setRows(dList);
		return dg;
	}

	private int getTotal(String sql) {
		int rowCount = 0;
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		try {
			conn = DBCPUtils.getConnectionPool();
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while (rs.next()) {
				rowCount = rs.getInt(1);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			DBCPUtils.release(rs, state, conn);
		}
		return rowCount;
	}

}
