package com.zl.courseware.system.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUtility
{
	private FileUtility()
	{
		super();
	}
	
	
	private static final Logger log = LoggerFactory.getLogger(FileUtility.class);
	
	
	/** 上传文件，读取文件返回字符串数组
	 * 
	 * @param file 上传文件
	 * @return byte数组
	 * @throws IOException */
	public static byte[] uploadFile(File file) throws IOException
	{
		byte[] bs = null;
		InputStream reader = new FileInputStream(file);
		bs = new byte[(int) file.length()];
		reader.read(bs);
		reader.close();
		return bs;
	}
	
	/** 读取文件，返回文件所有行（字符串）的list
	 * 
	 * @param filePath 文件全路径名字符串
	 * @return */
	public static List<String> readFileByLines(String filePath)
	{
		List<String> list = new ArrayList<String>();
		File file = new File(filePath);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null)
			{
				// 显示行号
				list.add(tempString);
			}
			reader.close();
		} catch (IOException e)
		{
			log.error("错误信息", e);
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException e1)
				{
					log.error("错误信息", e1);
				}
			}
		}
		return list;
	}
	
	/** 在文件中追加内容
	 * 
	 * @param fileName 文件的全路径字符串名
	 * @param content */
	public static void fileAppendContent(String fileName, String content)
	{
		try
		{
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e)
		{
			log.error("错误信息", e);
		}
	}
	
	/** 文件下载
	 * 
	 * @param filePath 文件全路径名
	 * @param output 文件输出流
	 * @return 下载成功（true）或者失败(flase) */
	public static boolean downLoad(String filePath, OutputStream output)
	{
		InputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(filePath);
			int readByte = 0;
			byte[] buffer = new byte[8192];
			while ((readByte = inputStream.read(buffer, 0, 8192)) != -1)
			{
				output.write(buffer, 0, readByte);
			}
		} catch (FileNotFoundException e)
		{
			log.error("错误信息", e);
		} catch (IOException e)
		{
			log.error("错误信息", e);
		} finally
		{
			try
			{
				if (inputStream != null)
				{
					inputStream.close();
				}
			} catch (Exception e)
			{
				log.error("错误信息", e);
			}
			try
			{
				if (output != null)
				{
					output.close();
				}
			} catch (Exception e)
			{
				log.error("错误信息", e);
			}
		}
		return false;
	}
	
	/** 传入指定文件字节数组，再转换成文件下载下来
	 * 
	 * @param fileName 下载生成的文件名称
	 * @param fileStream 读取的字节数组
	 * @param response 响应类
	 * @throws IOException */
	public static void downLoadFile(String fileName, byte[] fileStream, HttpServletResponse response) throws IOException
	{
		OutputStream out = null;
		response.setContentType("application/octet-stream;charset=UTF-8");
		String name = URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Location", name);
		response.setHeader("Content-Disposition", "attachment; filename=" + name); // filename应该是编码后的(utf-8)
		out = response.getOutputStream();
		byte b[] = fileStream;// 只能输出byte数组，所以将字符串变为byte数组
		for (int i = 0; i < b.length; i++)
		{ // 采用循环方式写入
			out.write(b[i]); // 每次只写入一个内容
		}
		out.flush();
		out.close(); // 关闭输出流
	}
	
	/** 检查文件或目录是否存在，如果为目录且不存在则自动创建目录
	 * 
	 * @param file 传入的文件
	 * @param path 文件路径 */
	public static void checkDirectory(File file, String path)
	{
		if (file != null)
		{
			if (!file.isDirectory())
			{
				file.mkdirs();
			}
		} else if (path != null)
		{
			new File(path).mkdirs();
		}
	}
	
	/** 根据文件路劲，进行文件下载
	 * 
	 * @param filepath 文件路劲
	 * @param filename 文件下载下来的生成的名称
	 * @param request 请求体
	 * @param response 响应体
	 * @throws IOException
	 * @throws Exception */
	public static void downloadFile(final String filepath, String filename, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		FileInputStream in = null;
		OutputStream out = null;
		String name = filename;
		try
		{
			final HttpServletResponse res = response;
			final HttpServletRequest req = request;
			String agent = req.getHeader("user-agent");
			if (agent.contains("MSIE"))
			{
				name = URLEncoder.encode(filename, "UTF-8");
			} else if (agent.contains("Firefox"))
			{
			} else
			{
				name = URLEncoder.encode(filename, "UTF-8");
			}
			res.setContentType("application/x-download;charset=utf-8");
			res.setHeader("Pragma", "public");
			res.setHeader("Cache-Control", "pulbic");
			res.addHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
			in = new FileInputStream(filepath);
			out = res.getOutputStream();
			final byte[] bts = new byte[1024];
			int len = 0;
			while ((len = in.read(bts)) != -1)
			{
				out.write(bts, 0, len);
			}
			out.flush();
		} catch (final Exception e)
		{
			log.error("错误信息", e);
		} finally
		{
			if (out != null)
			{
				out.close();
			}
			if (in != null)
			{
				in.close();
			}
		}
	}
	
	//	public static void downLoadFile(String fileName, byte[] fileStream)
	//			throws IOException {
	//		OutputStream out = null;
	//		HttpUtility.getResponse().setContentType(
	//				"application/octet-stream;charset=UTF-8");
	//		String name = URLEncoder.encode(fileName, "UTF-8");
	//		HttpUtility.getResponse().setHeader("Location", name);
	//		HttpUtility.getResponse().setHeader("Content-Disposition",
	//				"attachment; filename=" + name); // filename应该是编码后的(utf-8)
	//
	//		out = HttpUtility.getResponse().getOutputStream();
	//		byte b[] = fileStream;// 只能输出byte数组，所以将字符串变为byte数组
	//		for (int i = 0; i < b.length; i++) { // 采用循环方式写入
	//			out.write(b[i]); // 每次只写入一个内容
	//		}
	//		out.flush();
	//		out.close(); // 关闭输出流
	//	}
	/** 以行为单位读取文件，常用于读面向行的格式化文件 */
	public static String readFileByLine(String filePath)
	{
		File file = new File(filePath);
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			//int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null)
			{
				// 显示行号
				sb.append(tempString + " \r\n");
				//line++;
			}
			reader.close();
		} catch (IOException e)
		{
			log.error("错误信息", e);
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException e1)
				{
					log.error("错误信息", e1);
				}
			}
		}
		return sb.toString();
	}
}
