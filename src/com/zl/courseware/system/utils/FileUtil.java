package com.zl.courseware.system.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FileUtil
{
	public static String findFile(String path, String suffix)
	{
		File f = new File(path);
		vec.clear();
		findFile(f);
		for (int i = 0; i < vec.size(); i++)
		{
			String fn = vec.get(i).toString();
			String fn2 = fn.toLowerCase();
			if (fn2.endsWith(suffix))
			{
				return fn;
			}
		}
		return "";
	}
	
	
	private static Vector<String> vec = new Vector<String>();
	private static ZipFile		  zip;
	
	
	private static void findFile(File file)
	{
		File[] file2 = file.listFiles();
		for (int i = 0; i < file2.length; i++)
		{
			String name = file2[i].getName();
			if (file2[i].isFile() && (name.length() > 4))
			{
				vec.add(name);
			}
			// else if (file2[i].isDirectory()) {
			// findFile(file2[i]);
			// }
		}
		// if (file.exists()) {
		// if (file.isFile()) {
		// vec.add(file.getName());
		// }
		// else if (file.isDirectory()) {
		// File files[] = file.listFiles();
		// for (int i = 0; i < files.length; i++) {
		// findFile(files[i]);
		// }
		// }
		//
		// }
	}
	
	/** 创建单个文件
	 * 
	 * @param destFileName 文件名
	 * @return 创建成功返回true，否则返回false */
	public static boolean CreateFile(String destFileName)
	{
		File file = new File(destFileName);
		if (file.exists())
		{
			return false;
		}
		if (destFileName.endsWith(File.separator))
		{
			return false;
		}
		if (!file.getParentFile().exists())
		{
			if (!file.getParentFile().mkdirs())
			{
				return false;
			}
		}
		// 创建目标文件
		try
		{
			if (file.createNewFile())
			{
				return true;
			} else
			{
				return false;
			}
		} catch (IOException e)
		{
			return false;
		}
	}
	
	/** 创建目录
	 * 
	 * @param destDirName 目标目录名
	 * @return 目录创建成功返回true，否则返回false */
	public static boolean createDir(String destDirName)
	{
		File dir = new File(destDirName);
		if (dir.exists())
		{
			dir.delete();
		}
		if (!destDirName.endsWith(File.separator))
		{
			destDirName = destDirName + File.separator;
		}
		// 创建单个目录
		if (dir.mkdirs())
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public static boolean isDirExist(String destDirName)
	{
		File dir = new File(destDirName);
		return dir.exists();
	}
	
	public static boolean isFileExist(String destFileName)
	{
		File file = new File(destFileName);
		return file.exists();
	}
	
	public static void copyTo(String sourceFileName, String targetDir, boolean bDel)
	{
		try
		{
			File sourceFile = new File(sourceFileName);
			File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + sourceFile.getName());
			if (targetFile.exists())
			{
				if (bDel)
				{
					targetFile.delete();
				} else
				{
					return;
				}
			}
			// -------add by sunxi ----------
			if (!targetFile.exists())
			{
				FileUtil.CreateFile(targetFile.getAbsolutePath());
			}
			// ---end -------------
			copyFile(sourceFile, targetFile);
		} catch (Exception _ex)
		{
			// //logger.error(_ex.toString());
		}
	}
	
	public static void copyFile(String sourceFileName, String targetFileName)
	{
		try
		{
			File f1 = new File(sourceFileName);
			File f2 = new File(targetFileName);
			copyFile(f1, f2);
		} catch (Exception _ex)
		{
		}
	}
	
	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException
	{
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);
		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);
		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1)
		{
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();
		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}
	
	// 复制文件夹
	public static void copyDirectiory2(String sourceDir, String targetDir, Vector<String> fliters) throws IOException
	{
		// 新建目标目录
		try
		{
			(new File(targetDir)).mkdirs();
		} catch (Exception _ex)
		{
			// //logger.error(_ex.toString());
		}
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++)
		{
			if (file[i].isFile())
			{
				// 源文件
				File sourceFile = file[i];
				boolean filterFlag = false;
				for (String ext : fliters)
				{
					filterFlag |= sourceFile.getName().endsWith(ext);
				}
				if (filterFlag)
				{
					// 目标文件
					File targetFile = new File(
					        new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
					copyFile(sourceFile, targetFile);
				}
			}
			if (file[i].isDirectory())
			{
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
	
	// 复制文件夹
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException
	{
		// 新建目标目录
		try
		{
			(new File(targetDir)).mkdirs();
		} catch (Exception _ex)
		{
			// //logger.error(_ex.toString());
		}
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++)
		{
			File sourceFile = file[i];
			if (sourceFile.isFile())
			{
				// 源文件
				// 目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			// && !sourceFile.getName().equals("package")
			if (sourceFile.isDirectory())
			{
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
	
	// 复制文件夹 ,带过滤文件
	public static void copyDirForFilters(String sourceDir, String targetDir, Vector<String> filters) throws IOException
	{
		// 新建目标目录
		try
		{
			(new File(targetDir)).mkdirs();
		} catch (Exception _ex)
		{
			// //logger.error(_ex.toString());
		}
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++)
		{
			// 源文件
			File sourceFile = file[i];
			if (sourceFile.isFile())
			{
				// 目标文件
				boolean flag = false;
				for (int j = 0; j < filters.size(); j++)
				{
					String fileName = sourceFile.getName();
					if (fileName.endsWith(filters.get(j)))
					{
						flag = true;
						break;
					}
				}
				if (flag)
				{
					continue;
				}
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			// && !sourceFile.getName().equals("package")
			if (file[i].isDirectory())
			{
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
	
	// 复制文件夹,拷贝dirFilters指定的子目录,过滤 fliters指定的文件
	public static void copyDirForDirFilters(String sourceDir, String targetDir, Vector<String> filters, Vector<String> dirFilters) throws IOException
	{
		// 新建目标目录
		try
		{
			(new File(targetDir)).mkdirs();
		} catch (Exception _ex)
		{
			// //logger.error(_ex.toString());
		}
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++)
		{
			if (file[i].isFile())
			{
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				boolean flag = false;
				for (int j = 0; j < filters.size(); j++)
				{
					String fileName = sourceFile.getName();
					if (fileName.endsWith(filters.get(j)))
					{
						flag = true;
						break;
					}
				}
				if (flag)
				{
					continue;
				}
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory())
			{
				// 源文件
				File sourceFile = file[i];
				boolean filterFlag = false;
				for (String ext : dirFilters)
				{
					filterFlag |= sourceFile.getName().endsWith(ext);
				}
				if (filterFlag)
				{
					// 准备复制的源文件夹
					String dir1 = sourceDir + "/" + file[i].getName();
					// 准备复制的目标文件夹
					String dir2 = targetDir + "/" + file[i].getName();
					copyDirectiory(dir1, dir2);
				}
			}
		}
	}
	
	public static void deleteFile(File file)
	{
		if (file.exists())
		{
			if (file.isFile())
			{
				file.delete();
			} else if (file.isDirectory())
			{
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++)
				{
					deleteFile(files[i]);
				}
				file.delete();//删除文件夹
			}
		}
	}
	
	public static void deleteFile(String filename)
	{
		File file = new File(filename);
		deleteFile(file);
	}
	
	public static void deleteFile(String dir, String macth, Vector<String> filters)
	{
		File file = new File(dir);
		if (file.exists())
		{
			if (file.isDirectory())
			{
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++)
				{
					if (files[i].isFile())
					{
						String fileName = files[i].getName();
						if (fileName.indexOf(macth) > 0)
						{
							int count = 0;
							for (int k = 0; k < filters.size(); k++)
							{
								if (fileName.equals(filters.get(k)))
								{
									break;
								} else
								{
									count++;
								}
							}
							if (count == filters.size())
							{
								deleteFile(files[i]);
							}
						}
					}
				}
			}
		}
	}
	
	public static void deleteDirectory(String filename, Vector<String> filters)
	{ // 只删除目录，不删除与目录同级下的文件
		File file = new File(filename);
		if (file.exists())
		{
			if (file.isDirectory())
			{
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++)
				{
					if (files[i].isDirectory())
					{
						String fileName = files[i].getName();
						boolean flag = false;
						for (int j = 0; j < filters.size(); j++)
						{
							if (fileName.equals(filters.get(j)))
							{
								flag = true;
								break;
							}
						}
						if (flag)
						{
							continue;
						}
						deleteFile(files[i]);
					}
				}
			}
		}
	}
	
	/** 创建临时文件
	 * 
	 * @param prefix 临时文件的前缀
	 * @param suffix 临时文件的后缀
	 * @param dirName 临时文件所在的目录，如果输入null，则在用户的文档目录下创建临时文件
	 * @return 临时文件创建成功返回抽象路径名的规范路径名字符串，否则返回null */
	public static String createTempFile(String prefix, String suffix, String dirName)
	{
		File tempFile = null;
		try
		{
			if (dirName == null)
			{
				// 在默认文件夹下创建临时文件
				tempFile = File.createTempFile(prefix, suffix);
				return tempFile.getCanonicalPath();
			} else
			{
				File dir = new File(dirName);
				// 如果临时文件所在目录不存在，首先创建
				if (!dir.exists())
				{
					if (!FileUtil.createDir(dirName))
					{
						return null;
					}
				}
				tempFile = File.createTempFile(prefix, suffix, dir);
				return tempFile.getCanonicalPath();
			}
		} catch (IOException e)
		{
			return null;
		}
	}
	
	/** 一行一行的读取文件中的数据
	 * 
	 * @param filePath
	 * @param fileName
	 * @throws IOException */
	public List<String> readLineFile(String filePathName)
	{
		List<String> li = new ArrayList<String>();
		try
		{
			FileReader fr = new FileReader(filePathName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null)
			{
				line = br.readLine();
				li.add(line);
			}
			br.close();
			fr.close();
		} catch (Exception _ex)
		{
		}
		return li;
	}
	
	/** 一行一行的读取文件中的数据
	 * 
	 * @param filePath
	 * @param fileName
	 * @throws IOException */
	public static String removeLine(File file, String paramString)
	{
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try
		{
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader fr = new InputStreamReader(fileInputStream, "gbk");
			br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null)
			{
				line = br.readLine();
				if (line.indexOf(paramString) >= 0)
				{
					continue;
				}
				sb.append(line);
			}
			br.close();
			fr.close();
		} catch (Exception _ex)
		{
			return null;
		} finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				} catch (IOException e)
				{
					////logger.warn(e.toString());
				}
			}
		}
		return sb.toString();
	}
	
	/** 一行一行的读取文件中的数据
	 * 
	 * @param filePath
	 * @param fileName
	 * @throws IOException */
	public static String readLineInputStream(InputStream inputStream)
	{
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try
		{
			InputStreamReader fr = new InputStreamReader(inputStream, "gbk");
			br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null)
			{
				line = br.readLine();
				sb.append(line);
			}
			br.close();
			fr.close();
		} catch (Exception _ex)
		{
			return null;
		} finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				} catch (IOException e)
				{
				}
			}
		}
		return sb.toString();
	}
	
	/** autor yf 20160602 将文件压缩成zip，rar包
	 * 
	 * @param sourceFilePath
	 * @param zipFilePath
	 * @param fileName xxx.zip,xxx.rar
	 * @return */
	public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName)
	{
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		String basePath = sourceFilePath.substring(0, sourceFilePath.lastIndexOf(File.separator) - 1);
		if (!sourceFile.exists())
		{
			//Log.info("压缩文件目录" + sourceFilePath + "不存在.");
		} else
		{
			try
			{
				fos = new FileOutputStream(zipFilePath + "/" + fileName);
				zos = new ZipOutputStream(new BufferedOutputStream(fos));
				zipFile(sourceFile, basePath, zos);
				flag = true;
			} catch (Exception e)
			{
			} finally
			{
				try
				{
					if (null != zos)
					{
						zos.close();
					}
				} catch (Exception e)
				{
				}
			}
		}
		return flag;
	}
	
	private static void zipFile(File sourceFile, String basePath, ZipOutputStream zos)
	{
		File[] files = null;
		if (sourceFile.isDirectory())
		{
			files = sourceFile.listFiles();
		} else
		{
			files = new File[1];
			files[0] = sourceFile;
		}
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		byte[] bufs = new byte[1024 * 10];
		String pathName = "";
		try
		{
			for (File file : files)
			{
				if (file.isDirectory())
				{
					pathName = file.getPath().substring(basePath.length() + 1) + "/";
					zos.putNextEntry(new ZipEntry(pathName));
					zipFile(file, basePath, zos);
				} else
				{
					pathName = file.getPath().substring(basePath.length() + 1);
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis, 1024 * 10);
					zos.putNextEntry(new ZipEntry(pathName));
					int read = 0;
					while ((read = bis.read(bufs, 0, 1024 * 10)) != -1)
					{
						zos.write(bufs, 0, read);
					}
					try
					{
						if (null != bis)
						{
							bis.close();
						}
						if (null != fis)
						{
							fis.close();
						}
					} catch (Exception e)
					{
						//logger.error(e.toString());
					}
				}
			}
		} catch (Exception e)
		{
			//logger.error(e.toString());
		} finally
		{
			try
			{
				if (null != bis)
				{
					bis.close();
				}
				if (null != fis)
				{
					fis.close();
				}
			} catch (Exception e)
			{
			}
		}
	}
	
	public static String readXmlInZip(String zipFilePath, String xmlFileName)
	{
		InputStream inputStream = null;
		ZipInputStream in = null;
		try
		{
			zip = new ZipFile(zipFilePath);
			in = new ZipInputStream(new FileInputStream(zipFilePath));
			ZipEntry z;
			while ((z = in.getNextEntry()) != null)
			{
				String name = z.getName();
				if (name.indexOf(xmlFileName) > -1)
				{
					inputStream = zip.getInputStream(z);
					break;
				}
			}
			if (inputStream == null)
			{
			}
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			InputSource inputSource = new InputSource(bufferedReader);
			DocumentBuilderFactory dom = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dom.newDocumentBuilder();
			org.w3c.dom.Document doc = build.parse(inputSource);
			if (doc != null)
			{
				NodeList nodeList = doc.getElementsByTagName("uuid");
				String uuid = nodeList.item(0).getFirstChild().getNodeValue();
				return uuid;
			}
		} catch (Exception e)
		{
		}
		try
		{
			if (null != inputStream)
			{
				inputStream.close();
			}
			if (null != in)
			{
				in.close();
			}
		} catch (Exception e)
		{
		}
		return null;
	}
	
	public static void main(String rag[])
	{
		readXmlInZip("E:\\LLC.rar", "pack.xml");
	}
	
	/** 获得类的基路径，打成jar包也可以正确获得路径
	 * 
	 * @return */
	public String getBasePath()
	{
		String filePath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (filePath.endsWith(".jar"))
		{
			filePath = filePath.substring(0, filePath.lastIndexOf("/"));
			try
			{
				filePath = URLDecoder.decode(filePath, "UTF-8"); // 解决路径中有空格%20的问题
			} catch (UnsupportedEncodingException ex)
			{
			}
		}
		File file = new File(filePath);
		filePath = file.getAbsolutePath();
		return filePath;
	}
	
	/** byte数组转为string
	 * 
	 * @param encrytpByte
	 * @return */
	protected String bytesToString(byte[] encrytpByte)
	{
		String result = "";
		for (Byte bytes : encrytpByte)
		{
			result += (char) bytes.intValue();
		}
		return result;
	}
	
	// 把文件读入byte数组
	@SuppressWarnings("resource")
	static public byte[] readFile(String filename) throws IOException
	{
		File file = new File(filename);
		long len = file.length();
		byte data[] = new byte[(int) len];
		FileInputStream fin = new FileInputStream(file);
		int r = fin.read(data);
		if (r != len)
		{
			throw new IOException("Only read " + r + " of " + len + " for " + file);
		}
		fin.close();
		return data;
	}
	
	// 把byte数组写出到文件
	static public void writeFile(String filename, byte data[]) throws Exception
	{
		FileOutputStream fout = new FileOutputStream(filename);
		fout.write(data);
		fout.close();
	}
}
