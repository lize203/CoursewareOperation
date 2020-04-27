package com.zl.courseware.system.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HtmlParserUtility
{
	HttpClient                  httpClient = null;                                            // HttpClient实例
	GetMethod                   getMethod  = null;                                            // GetMethod实例
	BufferedWriter              fw         = null;
	String                      page       = null;
	String                      webappname = null;
	BufferedReader              br         = null;
	InputStream                 in         = null;
	StringBuilder               sb         = null;
	String                      line       = null;
	private static final Logger log        = LoggerFactory.getLogger(HtmlParserUtility.class);
	
	
	// 构造方法
	public HtmlParserUtility(String webappname)
	{
		this.webappname = webappname;
	}
	
	/** 根据模版及参数产生静态页面 */
	public boolean createHtmlPage(String url, String htmlFileName)
	{
		boolean status = false;
		int statusCode = 0;
		try
		{
			// 创建一个HttpClient实例充当模拟浏览器
			httpClient = new HttpClient();
			// 设置httpclient读取内容时使用的字符集
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			// 创建GET方法的实例
			getMethod = new GetMethod(url);
			// 使用系统提供的默认的恢复策略，在发生异常时候将自动重试3次
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			// 设置Get方法提交参数时使用的字符集,以支持中文参数的正常传递
			getMethod.addRequestHeader("Content-Type", "text/html;charset=utf-8");
			// 执行Get方法并取得返回状态码，200表示正常，其它代码为异常
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != 200)
			{
				log.error("静态页面引擎在解析" + url + "产生静态页面" + htmlFileName + "时出错!");
			} else
			{
				// 读取解析结果
				sb = new StringBuilder();
				in = getMethod.getResponseBodyAsStream();
				br = new BufferedReader(new InputStreamReader(in));
				while ((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				if (br != null)
					br.close();
				page = sb.toString();
				// 将页面中的相对路径替换成绝对路径，以确保页面资源正常访问
				page = formatPage(page);
				// 将解析结果写入指定的静态HTML文件中，实现静态HTML生成
				writeHtml(htmlFileName, page);
				status = true;
			}
		} catch (Exception e)
		{
			log.error("静态页面引擎在解析" + url + "产生静态页面" + htmlFileName + "时出错:", e);
		} finally
		{
			// 释放http连接
			getMethod.releaseConnection();
		}
		return status;
	}
	
	// 将解析结果写入指定的静态HTML文件中
	private synchronized void writeHtml(String htmlFileName, String content) throws IOException
	{
		fw = new BufferedWriter(new FileWriter(htmlFileName));
		fw.write(content);
		if (fw != null)
			fw.close();
	}
	
	// 将页面中的相对路径替换成绝对路径，以确保页面资源正常访问
	private String formatPage(String page)
	{
		return page.replaceAll("\\.\\./\\.\\./\\.\\./", webappname + "/")
		        .replaceAll("\\.\\./\\.\\./", webappname + "/").replaceAll("\\.\\./", webappname + "/");
	}
	
	// 测试方法
	public static void main(String[] args)
	{
		HtmlParserUtility h = new HtmlParserUtility("");
		h.createHtmlPage("http://www.baidu.com", "c:/abc.html");
	}
}
