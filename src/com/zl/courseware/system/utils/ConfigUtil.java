package com.zl.courseware.system.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigUtil
{
	private static Properties props;
	
	
	private static Properties getInstance()
	{
		FileInputStream fis = null;
		try
		{
			if (props == null)
			{
				props = new Properties();
				fis = new FileInputStream(new File(PathUtil.getClassPath() + "config.properties"));
				props.load(fis);
				return props;
			} else
			{
				return props;
			}
		} catch (Exception e)
		{
			return null;
		} finally
		{
			if (fis != null)
			{
				try
				{
					fis.close();
					fis = null;
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getParamValue(String key)
	{
		Properties dbProps = getInstance();
		if (dbProps == null)
		{
			return null;
		} else
		{
			return dbProps.getProperty(key);
		}
	}
}