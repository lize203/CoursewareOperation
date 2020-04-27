package com.zl.courseware.system.utils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/** 模块常量，可以无视
 * 
 */
public interface Global
{
	public final static Set<String>      JOBS                 = Collections.synchronizedSet(new HashSet<String>()); //所有任务调度状态map
	public final static String           DATEFORMAT           = "yyyy.MM";
	public final static SimpleDateFormat SDF                  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        //格式化时间
	public final static SimpleDateFormat SDFDATE              = new SimpleDateFormat("yyyy-MM-dd");                 //格式化日期
	public final static SimpleDateFormat SDFDATEMONTH         = new SimpleDateFormat(DATEFORMAT);                   //格式化日期到月份
//	//脚本运行是否成功
	public static final Integer          SHOW                 = 1;                                                 // 表示显示
	public static final Integer          HIDE                 = 0;                                                 // 表示隐藏
	//session 键常量
	public static final String           SESSION_FUNCTIONS    = "functions";
	public static final String           SESSION_MENUS        = "menus";
	public static final String           SESSION_USER         = "loginUser";
	public static final String           SESSION_LEVEL        = "level";
	public static final String           SESSION_USER_ID      = "userID";
}
