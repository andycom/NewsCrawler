package cn.edu.zjicm.pipeline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author winglive E-mail: winglive@126.com
 * @version 创建时间：2014-1-11 下午6:05:48 类说明
 */
public class sql2005 {

	public static String dbtype = "access";

	/**
	 * @return 返回数据库连结，Connection对象
	 */
	@SuppressWarnings("unchecked")
	public int insert(String spiderId, String ManageSystemIP) throws SQLException { // 此函数最后被调用，接受一个SQL语句，最执行这个语句即可。
		Connection conn = null;
		Statement stmt = null;
		int i = 0;
		// JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库驱动
		String connectDB = "jdbc:sqlserver://" + ManageSystemIP + ":1433;DatabaseName=Onspider";// 控制平台数据库连接设置
		String user = "sa"; // 数据库
		String pwd = "wing1989"; // 数据库密码
									// 控制中心的数据库用户名/密码也得写在XML中所以必须加密。现在先写在程序里运行试一下
		try {
			// 动态导入数据库的驱动
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// 获取数据库链接
			conn = DriverManager.getConnection(connectDB, user, pwd);
			// 创造SQL语句

			// 执行SQL语句
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT operateType FROM TaskInfo WHERE id=" + spiderId);
			while (rs.next()) {
				// retrieve and print the values for the current row
				i = rs.getInt("operateType");
				System.out.println("数据获取结果" + i + " ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			stmt.close();
			conn.close();
		}

		return i; // 返回数据库改变状态，设计规则如1：开始，2：结束 3.暂停，4：清空当前队列
	}
}
