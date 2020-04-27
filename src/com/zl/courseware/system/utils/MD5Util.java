package com.zl.courseware.system.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MD5加密工具类
 * 
  */
public class MD5Util
{
	private MD5Util()
	{
		super();
	}
	
	
	private static final Logger log = LoggerFactory.getLogger(MD5Util.class);
	
	
	public static void main(String[] args)
	{
		/* String s = "Gmcc_1603"; */
	}
	
	/** md5加密
	 * 
	 * @param str 需要加密的字符串
	 * @return 加密后的字符串 */
	public static String md5(String str)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuilder buf = new StringBuilder("");
			for (byte element : byteDigest)
			{
				i = element;
				if (i < 0)
				{
					i += 256;
				}
				if (i < 16)
				{
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
		} catch (NoSuchAlgorithmException e)
		{
			log.error("错误信息", e);
			return null;
		}
	}
	
	public static String get32sizeMD5(String text)
	{
		return md5(text);
	}
	
	public static String get16sizeMD5(String text)
	{
		String s = md5(text);
		if (s != null)
		{
			return s.substring(8, 24);
		} else
		{
			return s;
		}
	}
}
