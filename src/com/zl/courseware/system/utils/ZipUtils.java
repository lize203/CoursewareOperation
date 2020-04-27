package com.zl.courseware.system.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class ZipUtils
{
	public static final String ZIP   = ".zip";
	
	
	private ZipUtils()
	{
	}
	
	public static void doCompress(String srcFile, String zipFile) throws IOException
	{
		doCompress(new File(srcFile), new File(zipFile));
	}
	
	/**
	 * 文件压缩
	 * @param srcFile 目录或者单个文件
	 * @param zipFile 压缩后的ZIP文件
	 */
	public static void doCompress(File srcFile, File zipFile) throws IOException
	{
		ZipOutputStream out = null;
		try
		{
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			doCompress(srcFile, out);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			out.close();//记得关闭资源
		}
	}
	
	public static void doCompress(String filelName, ZipOutputStream out) throws IOException
	{
		doCompress(new File(filelName), out);
	}
	
	public static void doCompress(File file, ZipOutputStream out) throws IOException
	{
		doCompress(file, out, "");
	}
	
	public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException
	{
		if (inFile.isDirectory())
		{
			File[] files = inFile.listFiles();
			if (files != null && files.length > 0)
			{
				for (File file : files)
				{
					String name = inFile.getName();
					if (!"".equals(dir))
					{
						name = dir + "/" + name;
					}
					ZipUtils.doCompress(file, out, name);
				}
			} else
			{
				out.putNextEntry(new ZipEntry(inFile.getName() + "/"));
				out.closeEntry();
			}
		} else
		{
			ZipUtils.doZip(inFile, out, dir);
		}
	}
	
	
	/**
	 * size
	 */
	static int BUFFER_SIZE = 1024; //* 1024 * 10
	
	
	/**
	 * 压缩
	 */
	public static void doCompress(List<File> inFiles, ZipOutputStream out) throws IOException
	{
		for (File file : inFiles)
		{
			//                ZipUtils.doZip(file, out, dir);
			String entryName = file.getName();
			ZipEntry entry = new ZipEntry(entryName);
			out.putNextEntry(entry);
			int len = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			//                 FileInputStream fis = new FileInputStream(file);
			InputStream in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
			while ((len = in.read(buffer)) != -1)
			{
				out.write(buffer, 0, len);
			}
			in.close();
			out.flush();
			out.closeEntry();
		}
	}

	public static void main(String[] args)
	{
		List<File> list = new ArrayList<File>();
		list.add(new File("I:\\0.out\\KZLabManager1.0.0\\tools\\apache-tomcat-8.0.9\\cfiles\\A - 副本 (2).zrc"));
		list.add(new File("I:\\0.out\\KZLabManager1.0.0\\tools\\apache-tomcat-8.0.9\\cfiles\\A.zrc"));
		list.add(new File("I:\\0.out\\KZLabManager1.0.0\\tools\\apache-tomcat-8.0.9\\cfiles\\b (2).zrc"));
		try
		{
			FileOutputStream output = new FileOutputStream(new File("I:\\DOWN\\aaa.zip"));
			ZipOutputStream output2 = new ZipOutputStream(output);
			ZipUtils.doCompress(list, output2);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException
	{
		String entryName = null;
		if (!"".equals(dir))
		{
			entryName = dir + "/" + inFile.getName();
		} else
		{
			entryName = inFile.getName();
		}
		ZipEntry entry = new ZipEntry(entryName);
		out.putNextEntry(entry);
		int len = 0;
		byte[] buffer = new byte[1024];
		FileInputStream fis = new FileInputStream(inFile);
		while ((len = fis.read(buffer)) > 0)
		{
			out.write(buffer, 0, len);
			out.flush();
		}
		out.closeEntry();
		fis.close();
	}
	
	/**
	 * 读取zip包
	 */
	public static List<ZipEntry> readZip(String zipFilePath) throws IOException
	{
		List<ZipEntry> zipEntries = new ArrayList<ZipEntry>();
		if (zipFilePath.indexOf(GZipUtils.TARGZ) > 0)
		{
			Charset gbk = Charset.forName("gbk");
			InputStream inputStream = new BufferedInputStream(new FileInputStream(zipFilePath));
			ZipInputStream zipInputStream = new ZipInputStream(inputStream, gbk);
			ZipEntry zipEntry;
			while ((zipEntry = zipInputStream.getNextEntry()) != null)
			{
				if (zipEntry.getName().indexOf(".project") > 0)
				{
					zipEntries.add(zipEntry);
					System.out.println("zipEntry.path:" + zipFilePath);
					System.out.println("zipEntry.getName():" + zipEntry.getName());
					System.out.println("zipEntry.getCompressedSize():" + zipEntry.getCompressedSize());
					System.out.println("zipEntry.getCrc():" + zipEntry.getCrc());
					System.out.println("zipEntry.getComment():" + zipEntry.getComment());
					System.out.println("zipEntry.getTime():" + zipEntry.getTime());
					System.out.println("zipEntry.getTime():" + zipEntry.toString());
					System.out.println("zipEntry.getSize():" + zipEntry.getSize());
					System.out.println("------------------------------------------------------------------");
				}
			}
			zipInputStream.closeEntry();
			inputStream.close();
		}
		return zipEntries;
	}
	
	/**
	 *
	 */
	public static boolean decompressZip(File originFile, String targetDir) throws IOException
	{
		if (originFile.getName().indexOf(ZIP) <= 0)
		{
			return false;
		}
		ZipFile zipFile = new ZipFile(originFile);
		ZipEntry zipEntry;
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements())
		{
			zipEntry = (ZipEntry) entries.nextElement();
			String fileName = zipEntry.getName();
			File outputFile = new File(targetDir + fileName);
			if (zipEntry.isDirectory())
			{
				//				forceMkdirs(outputFile);
				continue;
			} else if (!outputFile.getParentFile().exists())
			{
				//				forceMkdirs(outputFile.getParent());
			}
			OutputStream outputStream = new FileOutputStream(outputFile);
			InputStream inputStream = zipFile.getInputStream(zipEntry);
			int len;
			byte[] buffer = new byte[8192];
			while (-1 != (len = inputStream.read(buffer)))
			{
				outputStream.write(buffer, 0, len);
			}
			outputStream.close();
			inputStream.close();
		}
		zipFile.close();
		return true;
	}
	//    public static void main(String[] args) throws IOException {
	////        doCompress("D:/瑁呮満蹇呭.zip", "D:/瑁呮満蹇呭2/");
	//    	readZip("D:/瑁呮満蹇呭.zip");
	//    }
	//    
}