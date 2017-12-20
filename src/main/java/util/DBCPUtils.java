package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;

/**
 * 使用连接池及事务处理功能的数据库连接
 * 
 * @author Leo
 *
 */
public class DBCPUtils {
	private static final Logger log = Logger.getLogger(DBCPUtils.class);

	// 数据库事务处理
	// 开始事务
	public static void beginTx(Connection conn) {
		if (conn != null) {
			try {
				conn.setAutoCommit(false); // 取消数据库自动提交功能
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
	}

	// 提交事务
	public static void commit(Connection conn) {
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
	}

	// 回滚事务
	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
	}

	/**
	 * 获取记录数
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 根据SQL语句返回查询到的记录数
	 */
	public static int getTotal(String sql) {
		int rowCount = 0;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = DBCPUtils.getConnectionPool();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
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

	/**
	 * 通用的查询一条方法：可以根据传入的 SQL、Class 对象返回 SQL 对应的记录的对象，与具体类无关
	 * 
	 * @param clazz
	 *            描述对象的类型
	 * @param sql
	 *            SQL 语句。可能带占位符
	 * @param args
	 *            填充占位符的可变参数
	 * @return
	 */
	public static <T> T get(Class<T> clazz, String sql, Object... args) {
		T entity = null;

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// 1. 得到 ResultSet 对象
			conn = DBCPUtils.getConnectionPool();
			preparedStatement = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();

			// 2. 得到 ResultSetMetaData 对象
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			// 3. 创建一个 Map<String, Object> 对象, 键: SQL 查询的列的别名, 值: 列的值
			Map<String, Object> values = new HashMap<String, Object>();

			// 4. 处理结果集. 利用 ResultSetMetaData 填充 3 对应的 Map 对象
			if (resultSet.next()) {
				for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
					String columnLabel = resultSetMetaData.getColumnLabel(i + 1);
					Object columnValue = resultSet.getObject(i + 1);

					values.put(columnLabel, columnValue);
				}
			}

			// 5. 若 Map 不为空集, 利用反射创建 clazz 对应的对象
			if (values.size() > 0) {
				entity = clazz.newInstance();

				// 6. 遍历 Map 对象, 利用反射为 Class 对象的对应的属性赋值
				for (Map.Entry<String, Object> entry : values.entrySet()) {
					String propertyName = entry.getKey();
					Object value = entry.getValue();
					// 使用反射为类的属性赋值
					// ReflectionUtils.setFieldValue(entity,
					// propertyName,value);
					// 使用BeanUtils工具包为类的属性赋值
					BeanUtils.setProperty(entity, propertyName, value);
				}
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			DBCPUtils.release(resultSet, preparedStatement, conn);
		}

		return entity;
	}

	/**
	 * 更新数据，使用PreparedStatement对象，适用于INSERT、DELETE或UPDATE
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            SQL 占位符的可变参数
	 */
	public static void update(String sql, Object... args) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			conn = DBCPUtils.getConnectionPool();
			// 打开事务
			DBCPUtils.beginTx(conn);
			// 创建PreparedStatement对象
			preparedStatement = conn.prepareStatement(sql);

			// 填充占位符
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			// 执行SQL语句
			preparedStatement.executeUpdate();
			// 提交事务
			DBCPUtils.commit(conn);
		} catch (Exception e) {
			// 事务回滚
			if (conn != null) {
				DBCPUtils.rollback(conn);
			}
			log.info(e.getMessage());
		} finally {
			DBCPUtils.release(preparedStatement, conn);
		}
	}

	/**
	 * 释放数据库连接资源
	 * 
	 * @param statement
	 *            Statement对象
	 * @param connection
	 *            Connection对象
	 */
	public static void release(Statement statement, Connection connection) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
	}

	/**
	 * 释放数据库连接资源
	 * 
	 * @param rs
	 *            ResultSet对象
	 * @param statement
	 *            Statement对象
	 * @param connection
	 *            Connection对象
	 */
	public static void release(ResultSet rs, Statement statement, Connection connection) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
		}
	}

	/**
	 * 初始化数据库连接池
	 */
	private static DataSource dataSource = null;

	static {
		Properties properties = new Properties();
		InputStream inStream = DBCPUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");

		try {
			properties.load(inStream);
			dataSource = BasicDataSourceFactory.createDataSource(properties); // 使用DBCP数据源连接数据库
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	/**
	 * 从数据库连接池中获取连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnectionPool() throws Exception {
		return dataSource.getConnection();
	}
}
