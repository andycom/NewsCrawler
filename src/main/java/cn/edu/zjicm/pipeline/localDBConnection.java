package cn.edu.zjicm.pipeline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author winglive E-mail: winglive@163.com
 * @version 创建时间：2014-1-11 下午1:29:51 类说明 数据库连接类
 */
public class localDBConnection {
	public static String dbtype = "access";

	/**
	 * @return 返回数据库连结，Connection对象
	 */
	@SuppressWarnings("unchecked")
	public int insert(String sql, String user, String passWord) throws SQLException { // 传入三个参数构建本地数据库连接，解析结果写入数据库
		Connection conn = null;
		Statement stmt = null;
		int i = 0;
		String connectDB = "jdbc:sqlserver://localhost:1433;DatabaseName=Onspider";// 链接配置
		try {
			// 动态导入数据库的驱动
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// 获取数据库链接
			conn = DriverManager.getConnection(connectDB, user, passWord);
			// 执行SQL语句*****此部分需要最终修改才可插入数据库
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// retrieve and print the values for the current row
				i = rs.getInt("TaskRemark");
				String s = rs.getString("TaskName");
				// float f = rs.getFloat("c");
				System.out.println("数据获取结果" + i + " " + s + " ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			stmt.close();
			conn.close();
		}

		return i; // 返回数据库改变状态，设计规则如1：开始，2：结束 3.暂停，4：清空当前队列
	}

}
