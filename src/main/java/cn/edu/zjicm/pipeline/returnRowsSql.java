package cn.edu.zjicm.pipeline;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author winglive E-mail: fiveme@126.com
 * @version 创建时间：2014-1-12 上午11:16:10 类说明：传入SQL语句返回多行结果
 */
public class returnRowsSql {
	public ResultSet getRowsData(Connection conn, String sql) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("数据库返回结果出错！无法完成种子列表初始化");
		}
		return rs;
	}

	public static void closeStatement(Statement stmt) {
		try {
			stmt.close(); // 关闭连接
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
