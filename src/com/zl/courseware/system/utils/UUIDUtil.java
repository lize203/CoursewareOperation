package com.zl.courseware.system.utils;

import java.util.UUID;


/** 获取UUID工具类
 * 
  */
public class UUIDUtil
{
	private UUIDUtil()
	{
		super();
	}
	
	/** 获取UUID
	 * 
	 * @return */
	public static String getUUID()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}
}