package cn.edu.zjicm.pipeline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author winglive E-mail: winglive@126.com
 * @version 创建时间：2014-1-11 下午8:31:52 类说明:公共类返回控制台数据库连接
 */
public class sql2008 {

	public static String dbtype = "";

	/**
	 * @return 返回数据库连结，Connection对象
	 */
	@SuppressWarnings("unchecked")
	public Connection getConnection(String ManageSystemIP, String user, String pwd) {
		Connection conn = null;
		dbtype = "access";
		try {

			// JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
			String connectDB = "jdbc:sqlserver://" + ManageSystemIP + ":1433;DatabaseName=Onspider";// 控制平台数据库连接设置
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// 获取数据库链接
			conn = DriverManager.getConnection(connectDB, user, pwd);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库连接对象
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		try {
			conn.close(); // 关闭连接
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}