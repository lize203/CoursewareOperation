package com.zl.courseware.system.utils;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** oracle的JDBC操作工具类
 * 
 * @author lb */
public class OracleHelper extends BaseSqlHelper
{
	private static final Logger log = LoggerFactory.getLogger(OracleHelper.class);
	
	
	/** 构造方法
	 * 
	 * @param url URL路径
	 * @param userName 用户名
	 * @param passWord 密码 */
	public OracleHelper(String url, String userName, String passWord)
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
	public OracleHelper(String url, String userName, String passWord, int overTime)
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
	public OracleHelper(String dbName, String ip, String port, String userName, String passWord)
	{
		super();
		db_url = getUrl(dbName, ip, port);
		db_userName = userName;
		db_userPass = passWord;
		db_ssid = dbName;
	}
	
	/** 获取URL */
	public String getUrl(String dbName, String ip, String port)
	{
		return "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dbName;
	}
	
	/** 获取一个字段类型 */
	public String getOneColumnType(String username, String table, String column)
	{
		String type = null;
		String sql = "select COLUMN_NAME,DATA_TYPE from DBA_TAB_COLUMNS t where T.TABLE_NAME='" + table
		        + "' and t.OWNER='" + username + "'";
		List<Map<String, Object>> dateForMap = getDateForMap(sql);
		for (Map<String, Object> map : dateForMap)
		{
			if (map.get("COLUMN_NAME").equals(column))
			{
				type = map.get("DATA_TYPE").toString();
				break;
			}
		}
		return type;
	}
	
	/** 检查表是否存在
	 * 
	 * @return
	 * @throws Exception */
	public boolean checkTable(String username, String table)
	{
		try
		{
			String sql2 = "SELECT * FROM DBA_TABLES t WHERE t.TABLE_NAME = '" + table + "'" + " and t.OWNER='"
			        + username + "'";
			List<Map<String, Object>> dateForMap = getDateForMap(sql2);
			if (!dateForMap.isEmpty())
				return true;
			else
				return false;
		} catch (Exception e)
		{
			log.error("错误信息", e);
			return false;
		}
	}
	
	/** 获当前时间 */
	@Override
	public String getCurrentTime()
	{
		String sql2 = "select to_char(sysdate,'yyyy-MM-dd') as CURRENTTIME from dual";
		List<Map<String, Object>> dateForMap = getDateForMap(sql2);
		if (!dateForMap.isEmpty())
			return dateForMap.get(0).get("CURRENTTIME").toString();
		else
			return StringUtil.turnTimeToStringByFormat(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/** 指定格式获当前时间 */
	public String getCurrentTime(String format)
	{
		String sql2 = "select to_char(sysdate,'" + format + "') as CURRENTTIME from dual";
		List<Map<String, Object>> dateForMap = getDateForMap(sql2);
		if (!dateForMap.isEmpty())
			return dateForMap.get(0).get("CURRENTTIME").toString();
		else
			return StringUtil.turnTimeToStringByFormat(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
	}
}
