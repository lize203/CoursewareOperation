package com.zl.courseware.system.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.imageio.ImageIO;


public class ImageUtil
{
	/** 保存图片 **/
	public static void saveImageFile(String name, byte data[])
	{
		String fatherURL = getImagesFatherURL();
		File dirFile = new File(fatherURL);
		if (!dirFile.exists() && !dirFile.isDirectory())
			dirFile.mkdirs();
		String filePath = (new StringBuilder(String.valueOf(fatherURL))).append(name).toString();
		FileOutputStream outputStream = null;
		try
		{
			outputStream = new FileOutputStream(new File(filePath));
			if (data != null)
			{
				outputStream.write(data);
			}
			outputStream.flush();
			outputStream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/** 合并图片，每张待合并的图片等长等宽。 注：行列数任意，保证每张图片等长等宽即可。
	 * 
	 * @作者: longtr
	 * @param name 文件名
	 * @param imageList 图片的文件流数组列表{[],[],[],...}
	 * @param width 待合并图片的宽度
	 * @param height 待合并图片的高度
	 * @param rows 图片矩阵的行数
	 * @param columns 图片矩阵的列数
	 * @exception [违例类型] [违例说明] */
	public static String saveImageFile(String name, List<byte[]> imageList, int width, int height, int rows, int columns)
	{
		String fatherURL = getImagesFatherURL();
		File dirFile = new File(fatherURL);
		if (!dirFile.exists() && !dirFile.isDirectory())
			dirFile.mkdirs();
		String filePath = (new StringBuilder(String.valueOf(fatherURL))).toString();
		return saveImageFile(filePath, name, imageList, width, height, rows, columns);
	}
	
	public static String saveImageFile(String parent, String name, List<byte[]> imageList, int width, int height, int rows, int columns)
	{
		int[] imagePartArray = null;
		BufferedImage imagePart = null;
		// 生成新图片
		BufferedImage imageInOne = null;
		ByteArrayInputStream bais = null;
		File outFile = null;
		if (rows < 1 || columns < 1)
		{
			throw new IllegalArgumentException("rows or columns < 1");
		}
		if (width < 0 || height < 0)
		{
			throw new IllegalArgumentException("width or height < 1");
		}
		if (imageList.size() != (rows * columns))
		{
			throw new IllegalArgumentException("imageList.length not match!");
		}
		try
		{
			imageInOne = new BufferedImage(width * columns, height * rows, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++)
				{
					bais = new ByteArrayInputStream(imageList.get(columns * i + j));
					imagePart = ImageIO.read(bais);
					imagePartArray = new int[width * height];
					imagePart.getRGB(0, 0, width, height, imagePartArray, 0, width);
					imageInOne.setRGB(j * width, i * height, width, height, imagePartArray, 0, width);
					bais.close();
				}
			}
			System.out.println("width:" + width + ", height : " + height);
			outFile = new File(parent + name);
			System.out.println("文件类型[" + name.substring(name.lastIndexOf(".")) + "]");
			ImageIO.write(imageInOne, name.substring(name.lastIndexOf(".") + 1), outFile);// 写图片
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("图片合并失败", e);
		} finally
		{
			if (bais != null)
			{
				try
				{
					bais.close();
					bais = null;
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		try
		{
			System.out.println(URLEncoder.encode(outFile.getAbsolutePath(), "UTF-8"));
			return URLEncoder.encode(outFile.getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static String getImagesFatherURL()
	{
		URL url = ImageUtil.class.getResource("");
		String fatherURL = url.getPath();
		fatherURL = fatherURL.substring(0, fatherURL.lastIndexOf("/WEB-INF"));
		fatherURL = (new StringBuilder(String.valueOf(fatherURL))).append("/images/node/").toString();
		return fatherURL;
	}
	
	/** 获取图片内容 **/
	public static byte[] getImageDataByName(String name)
	{
		String path = (new StringBuilder(String.valueOf(getImagesFatherURL()))).append(name).toString();
		File file = new File(path);
		byte bytes[] = null;
		try
		{
			FileInputStream inputStream = new FileInputStream(file);
			bytes = new byte[(int) file.length()];
			inputStream.read(bytes);
			inputStream.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return bytes;
	}
	
	/** 删除单个文件
	 * 
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false */
	public static boolean deleteFile(String sPath)
	{
		boolean flag = false;
		File file = new File((new StringBuilder(String.valueOf(getImagesFatherURL()))).append(sPath).toString());
		if (file.isFile() && file.exists())
		{
			file.delete();
			flag = true;
		}
		return flag;
	}
}
