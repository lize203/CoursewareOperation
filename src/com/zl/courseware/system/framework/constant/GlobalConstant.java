package com.zl.courseware.system.framework.constant;

import java.text.SimpleDateFormat;


public class GlobalConstant
{
	private GlobalConstant()
	{
		super();
	}
	
	
	public static final SimpleDateFormat SDF               = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化时间
	public static final String           LOGIN_USER        = "loginUser";
	//数据操作
	public static final Integer          SUCCESS           = 1;                                          // 操作成功
	public static final Integer          FAIL              = 0;                                          // 操作失败
	public static final Integer          EXIST             = 3;                                          //已经存在
	public static final Integer          RELOAD            = 3;                                          // 操作成功
	public static final Integer          NOPOWER             = 99;                                          //无权限

	//数据查询类型
	public static final String           ALLTYPE           = "0";                                         //所有类型
	public static final String           ACCOUNT           = "1";                                         //用户名
	public static final String           NAME              = "2";                                         //名字
	public static final String           TEL               = "3";                                         //电话号码
	public static final String           USER              = "1";                                         //操作用户
	public static final String           DATE              = "2";                                         //操作时间
	public static final String           OPTYPE            = "3";                                         //操作时间
	//菜单和按钮
	public static final String           YES_CHILD_MENU    = "_yes";                                     //有子菜单
	public static final String           NO_CHILD_MENU     = "_no";                                      //无子菜单
	public static final Integer          MENU              = 1;                                           //表示是菜单
	public static final Integer          FUN               = 2;                                           //表示是按钮
	public static final String           BAR               = "1";                                         //表示是toobar中的按钮
	public static final Integer          HAS_MENU          = 1;                                           //表示有子菜单
	public static final Integer          HAS_FUN           = 2;                                           //表示有子按钮
	public static final Integer          HAS_NO            = 0;                                           //表示没有子菜单和按钮
	public static final Integer          ONLYONE           = 3;                                           //表示该顶级菜单只能有一个子菜单
	public static final String           PARENT_URL        = "#";                                         //表示该菜单下有了子节点，该菜单的url将被改为#
	public static final String           ONE               = "1";                                         //只有一个子菜单标记
	public static final String           IS_DISPLAY        = "1";                                         //表示是展示的
	public static final Integer          ISDISABLE         = 1;                                           //表示是展示的
	//是否管理员
	public static final String           ADMIN             = "admin";                                     //管理员标识字符串
	public static final Integer          IS_ADMIN          = 1;                                           //表示是管理员
	//操作
	public static final Integer          IS_ADD            = 1;                                           //是添加
	public static final Integer          IS_UPDATE         = 2;                                           //是更新
	public static final Integer          CANNOT            = 2;                                           //不能执行此操作
	//角色级别
	public static final char             role_level_1      = '1';                                         //一级角色
	public static final char             role_level_2      = '2';                                         //二级角色
	//账号类型
	public static final String           account_type_1    = "1";                                         //默认账号
	public static final String           account_type_2    = "2";                                         //程序账号
	public static final String           account_type_3    = "3";                                         //个人维护账号
	public static final String           account_type_4    = "4";                                         //临时账号
	//页面展示还是隐藏
	public static final Integer          SHOW              = 1;                                          // 表示显示
	public static final Integer          HIDE              = 0;                                          // 表示隐藏
	//权限类型
	public static final Integer          MENU_TPYE         = 0;                                           // 权限类型：菜单
	public static final Integer          FUN_TPYE          = 1;                                           // 权限类型：功能
	public static final Integer          ISDROP            = 1;                                           // 业务是否删除状态为：是
	public static final Integer          NODROP            = 0;                                           // 业务是否删除状态为：否
}