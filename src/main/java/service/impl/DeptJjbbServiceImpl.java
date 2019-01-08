package service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
		Long total = (long) DBCPUtils.getTotal(totalSql);
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
			total = (long) DBCPUtils.getTotal(totalSql);
			sql += " and title like '%" + title + "%'";
		}
		// 是否按内容(content)搜索
		if (deptJjbb.getContent() != null && !deptJjbb.getContent().equals("")) {
			String content = deptJjbb.getContent();
			totalSql += " and content like '%" + content + "%'";
			total = (long) DBCPUtils.getTotal(totalSql);
			sql += " and content like '%" + content + "%'";
		}
		// 排序
		if (deptJjbb.getSort() != null && deptJjbb.getOrder() != null) {
			sql += " order by " + deptJjbb.getSort() + " " + deptJjbb.getOrder();
		}
		
		//log.info(sql);
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
				dJjbb.setSource(rs.getString("通知来源"));
				dJjbb.setComeFrom(rs.getString("交办人"));
				dJjbb.setContent(rs.getString("内容"));
				String attached=rs.getString("附件");
				//StringUtils.replace("sshhhs","ss","p");//全部替换--->结果是：phhhs
				attached=StringUtils.replace(attached, "file", "http://134.201.4.89:8080/wj/file");
				dJjbb.setAttachment(attached);
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

}
