package com.zl.courseware.system.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil
{
	private DateUtil()
	{
		super();
	}
	
	
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
	
	
	/** 将时间转换成字段串格式
	 * 
	 * @param sourceTime 原时间
	 * @return 字符串时间格式 */
	public static String formatTime(Date sourceTime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(sourceTime);
	}
	
	//获取当前时间的字符串格式
	public static String getCurrTime()
	{
		Date sourceTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		return sdf.format(sourceTime);
	}
	
	/** 将字符串格式时间转换成js时间代码字符串
	 * 
	 * @param formatDate 字符串格式时间
	 * @return js时间代码字符串 */
	public static String getTimes(String formatDate)
	{
		Long time = null;
		if (formatDate != null)
		{
			SimpleDateFormat k = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try
			{
				time = k.parse(formatDate).getTime();
			} catch (java.text.ParseException e)
			{
				log.error("错误信息", e);
			}
		} else
		{
			time = new Date().getTime();
		}
		return "\\/Date(" + time + ")\\/";
	}
	
	/** 根据目标时间返回所在的时间段的字符串
	 * 
	 * @param sourceTime 目标时间
	 * @return 所在时间段字符串
	 * @throws java.text.ParseException */
	public static String[] getTimeSlot(Date sourceTime)
	{
		String[] targetTime = new String[2];
		String[] dates = new String[] { "00:00:00-1:00:00", "1:00:00-2:00:00", "2:00:00-3:00:00", "3:00:00-4:00:00",
		        "4:00:00-5:00:00", "5:00:00-6:00:00", "6:00:00-7:00:00", "7:00:00-8:00:00", "8:00:00-9:00:00",
		        "9:00:00-10:00:00", "10:00:00-11:00:00", "11:00:00-12:00:00", "12:00:00-13:00:00", "13:00:00-14:00:00",
		        "14:00:00-15:00:00", "15:00:00-16:00:00", "16:00:00-17:00:00", "17:00:00-18:00:00",
		        "18:00:00-19:00:00", "19:00:00-20:00:00", "20:00:00-21:00:00", "21:00:00-22:00:00",
		        "22:00:00-23:00:00", "23:00:00-23:59:59" };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = sdf.format(sourceTime);
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		String date = dateTime.split(" ")[0];// 年月日
		String time = dateTime.split(" ")[1];// 时分秒
		for (int i = 0; i < dates.length; i++)
		{
			String dateStr = dates[i];
			String[] dateArr = dateStr.split("-");
			Date currDate = null;
			try
			{
				currDate = sdfTime.parse(time);
			} catch (java.text.ParseException e)
			{
				log.error("错误信息", e); // To change body of catch statement
				                      // use File | Settings | File
				                      // Templates.
			}
			Date startDate = null;// 每节开始时间
			try
			{
				startDate = sdfTime.parse(dateArr[0]);
			} catch (java.text.ParseException e)
			{
				log.error("错误信息", e); // To change body of catch statement
				                      // use File | Settings | File
				                      // Templates.
			}
			Date endDate = null;// 每节结束时间
			try
			{
				endDate = sdfTime.parse(dateArr[1]);
			} catch (java.text.ParseException e)
			{
				log.error("错误信息", e); // To change body of catch statement
				                      // use File | Settings | File
				                      // Templates.
			}
			if (currDate.after(startDate) && currDate.before(endDate))
			{
				targetTime[0] = date + " " + sdfTime.format(startDate);
				targetTime[1] = date + " " + sdfTime.format(endDate);
			}
		}
		return targetTime;
	}
	
	/** 将当前时间按一定的格式转换成字符串，日期月份和天数不变，年份在原来的年份加上num
	 * 
	 * @param num 月份追加数
	 * @param format 转换的字符串时间新格式
	 * @return 重新转换的时间字符串 */
	public static String getYear(int num, String format)
	{
		Date d = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat(format);
		gc.setTime(d);
		gc.add(1, num);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return sf.format(gc.getTime());
	}
	
	/** 将当前时间按一定的格式转换成字符串，日期年份和天数不变，月份在原来的月份加上num，如果大于12或者少于1，进一位在年份上加减
	 * 
	 * @param num 月份追加数
	 * @param format 转换的字符串时间新格式
	 * @return 重新转换的时间字符串 */
	public static String getMonth(int num, String format)
	{
		Date d = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat(format);
		gc.setTime(d);
		gc.add(2, num);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return sf.format(gc.getTime());
	}
	
	/** 将当前时间按一定的格式转换成字符串，日期年份和月份不变，天在原来的天加上num，如果低于1号或者大于当前月最大天数，进一位在月份上加减
	 * 
	 * @param num 天数追加数
	 * @param format 转换的字符串时间新格式
	 * @return 重新转换的时间字符串 */
	public static String getDay(int num, String format)
	{
		Date d = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat(format);
		gc.setTime(d);
		gc.add(5, num);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return sf.format(gc.getTime());
	}
	
	/** 将指定的时间字符串按指定的格式转换成心的时间字符串，日期年份和月份不变，天在原来的天加上num，如果低于1号或者大于当前月最大天数，
	 * 则进一位在月份上加减，如2015-07-31，num为2，则变为2015-08-02
	 * 
	 * @param dateStr 原字符串格式日期
	 * @param num 天数追加数
	 * @param format 转换的字符串时间新格式
	 * @return 重新转换的时间字符串 */
	public static String getDay(String dateStr, int num, String format)
	{
		try
		{
			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date d = sf.parse(dateStr);
			gc.setTime(d);
			gc.add(5, num);
			gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
			return sf.format(gc.getTime());
		} catch (Exception e)
		{
			log.error("错误信息", e);
		}
		return null;
	}
	
	/** 将指定的时间字符串按指定的格式转换成心的时间字符串，日期年份不变，月份在原来的月份加上num，如果大于12或者小于1，则进一位在年份上加减，
	 * 如果包含天将转换成1号，如2015-07-11，num为2，则变为2015-09-01
	 * 
	 * @param dateStr 原字符串格式日期
	 * @param num 月份追加数
	 * @param format 转换的字符串时间新格式
	 * @return 重新转换的时间字符串 */
	public static String getMonth(String dateStr, int num, String format)
	{
		try
		{
			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date d = sf.parse(dateStr);
			gc.setTime(d);
			gc.add(2, num);
			gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), 01);
			return sf.format(gc.getTime());
		} catch (Exception e)
		{
			log.error("错误信息", e);
		}
		return null;
	}
	
	/** 将指定的时间字符串按指定的格式转换成心的时间字符串，日期年份在原来的年份加上num，如果包含月份或者天，都将转换成1月和1号，如2015-07-
	 * 11，num为2，则变为2017-01-01
	 * 
	 * @param dateStr 原字符串格式日期
	 * @param num 年份追加数
	 * @param format 转换的字符串时间新格式
	 * @return 重新转换的时间字符串 */
	public static String getYear(String dateStr, int num, String format)
	{
		try
		{
			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date d = sf.parse(dateStr);
			gc.setTime(d);
			gc.add(1, num);
			gc.set(gc.get(Calendar.YEAR), 0, 1);
			return sf.format(gc.getTime());
		} catch (Exception e)
		{
			log.error("错误信息", e);
		}
		return null;
	}
	
	/** 时间字符串转换成日期
	 * 
	 * @param str 时间字符串
	 * @return date 日期类型时间 */
	public static Date StrToDate(String str)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try
		{
			if (str != null && !"".equals(str.trim()))
			{
				date = format.parse(str);
			}
		} catch (ParseException e)
		{
			log.error("错误信息", e);
		}
		return date;
	}
}
