package com.zl.courseware.system.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pathhelper.PathHelper;
import com.propertyhelper.PropertyHelper;


/** 获取本系统数据库的jdbc专用工具 公共封装 拿 db.properties的 mysql配置连接
 * 
 * 废弃，统一使用MysqlHelper */
public class JDBCUtil
{
	private static final Logger log            = LoggerFactory.getLogger(JDBCUtil.class);
	static String               url;                                                     // 连接url
	static String               name;                                                    // 用户名
	static String               password;                                                // 密码
	static Connection           conn           = null;
	private static JDBCUtil     jdbcUtilSingle = new JDBCUtil();
	
	
	public static JDBCUtil getInitJDBCUtil()
	{
		return jdbcUtilSingle;
	}
	
	private JDBCUtil()
	{
	}
	
	
	// 通过静态代码块注册数据库驱动，保证注册只执行一次
	static
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");// 推荐使用方式
			PropertyHelper propertyHelper = new PropertyHelper(PathHelper.getClassesPath(JDBCUtil.class)
			        + "db.properties");
			url = propertyHelper.getProperty("hibernate.connection.url");
			name = propertyHelper.getProperty("hibernate.connection.username");
			password = propertyHelper.getProperty("hibernate.connection.password");
		} catch (ClassNotFoundException e)
		{
			log.error("错误信息", e);
		}
	}
	
	
	/** 获取连接
	 * 
	 * @return */
	public Connection getConnection()
	{
		try
		{
			conn = DriverManager.getConnection(url, name, password);
		} catch (SQLException e)
		{
			log.error("错误信息", e);
		}
		return conn;
	}
	
	/** 关闭连接
	 * 
	 * @param rs
	 * @param statement
	 * @param con */
	public void closeConnection(ResultSet rs, Statement statement, Connection con)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
		} catch (SQLException e)
		{
			log.error("错误信息", e);
		} finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			} catch (Exception e)
			{
				log.error("错误信息", e);
			} finally
			{
				try
				{
					if (con != null)
					{
						con.close();
					}
				} catch (SQLException e)
				{
					log.error("错误信息", e);
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			JDBCUtil.getInitJDBCUtil().getConnection();
			MySQLHelper mysql = new MySQLHelper();
			Connection conn = mysql.getConnection();
		} catch (Exception e)
		{
			log.error("错误信息", e);
		}
	}
}
