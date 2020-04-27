package com.zl.courseware.system.utils;

import com.pathhelper.PathHelper;
import com.propertyhelper.PropertyHelper;


/** oracle的JDBC操作工具类
 * 
 * @author lb */
public class MySQLHelper extends BaseSqlHelper
{
	/** 构造方法 默认从hibernate配置文件读取数据库信息 */
	public MySQLHelper()
	{
		PropertyHelper propertyHelper = new PropertyHelper(PathHelper.getClassesPath(MySQLHelper.class)
		        + "db.properties");
		db_url = propertyHelper.getProperty("hibernate.connection.url");
		db_userName = propertyHelper.getProperty("hibernate.connection.username");
		db_userPass = propertyHelper.getProperty("hibernate.connection.password");
	}
	
	/** 构造方法
	 * 
	 * @param url URL路径
	 * @param userName 用户名
	 * @param passWord 密码 */
	public MySQLHelper(String url, String userName, String passWord)
	{
		super();
		db_url = url;
		db_userName = userName;
		db_userPass = passWord;
	}
	
	/** 构造函数
	 * 
	 * @param url URL路径
	 * @param userName 用户名
	 * @param passWord 密码
	 * @param overTime */
	public MySQLHelper(String url, String userName, String passWord, int overTime)
	{
		super();
		db_url = url;
		db_userName = userName;
		db_userPass = passWord;
		db_overtime = overTime;
	}
	
	/** 构造函数
	 * 
	 * @param dbName 数据库名
	 * @param ip IP
	 * @param port 端口
	 * @param userName 用户名
	 * @param passWord 密码 */
	public MySQLHelper(String dbName, String ip, String port, String userName, String passWord)
	{
		super();
		db_url = getUrl(dbName, ip, port);
		db_userName = userName;
		db_userPass = passWord;
		db_ssid = dbName;
	}
	
	/** 获取连接url
	 * 
	 * @param dbName 数据库实例名
	 * @param ip 数据库ip
	 * @param port 数据库端口
	 * @param userName 用户名
	 * @param passWord 密码
	 * @return */
	public String getUrl(String dbName, String ip, String port)
	{
		return "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
	}
	
	@Override
	public boolean checkTable(String flusersource, String fltablesource)
	{
		return false;
	}
	
	@Override
	public String getCurrentTime()
	{
		return null;
	}
	
	@Override
	public String getOneColumnType(String username, String table, String column)
	{
		return null;
	}
}
