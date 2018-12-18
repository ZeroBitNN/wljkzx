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
import pageModel.DeptTxl;
import service.DeptTxlServiceI;
import util.DBCPUtils;

@Service(value = "deptTxlService")
public class DeptTxlServiceImpl implements DeptTxlServiceI {
	private static final Logger log = Logger.getLogger(DeptTxlServiceImpl.class);

	@Override
	public DataGrid<DeptTxl> getDatagrid(DeptTxl deptTxl) {
		DataGrid<DeptTxl> dg = new DataGrid<DeptTxl>();
		Long total = (long) getTotal("SELECT COUNT(*) from txl"); // 数据库记录总数
		List<DeptTxl> dList = new ArrayList<DeptTxl>();
		String sql = "";
		String content = "";
		if (deptTxl.getContent() != null && !deptTxl.getContent().equals("")) {
			content = deptTxl.getContent();
			total = (long) getTotal("SELECT COUNT(*) from txl where xm like '%" + content + "%'");
			sql = "select top " + deptTxl.getRows() + " * from txl where xm like '%" + content + "%' order by "
					+ deptTxl.getSort() + " " + deptTxl.getOrder();
		} else {
			if (deptTxl.getPage() == 1) {
				sql = "select top " + deptTxl.getRows() + " * from txl order by " + deptTxl.getSort() + " "
						+ deptTxl.getOrder();
			} else {
				sql = "select top " + deptTxl.getRows() + " * from txl where ID not in(select top "
						+ (deptTxl.getRows() * (deptTxl.getPage() - 1)) + " ID from txl order by " + deptTxl.getSort()
						+ " desc) order by " + deptTxl.getSort() + " " + deptTxl.getOrder();
			}
		}
		// String sql = "select * from txl";
		// log.info(sql);
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = DBCPUtils.getConnectionPool();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				DeptTxl dTxl = new DeptTxl();
				dTxl.setId(rs.getString("id"));
				dTxl.setXm(rs.getString("xm"));
				dTxl.setXlt(rs.getString("xlt"));
				dTxl.setSj(rs.getString("sj"));
				dTxl.setDh(rs.getString("dh"));
				dTxl.setDwzw(rs.getString("dwzw"));
				dTxl.setDwbm(rs.getString("dwbm"));
				dList.add(dTxl);
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
