package com.zl.courseware.system.utils;

import com.zl.courseware.system.model.kz.KzExperiment;


/** 路径处理工具类
 * 
 * @author lb */
public class PathUtil
{
	private PathUtil()
	{
		super();
	}
	
	/** 获取 classes文件夹下的全路径
	 * 
	 * @return */
	public static String getClassPath()
	{
		String path = PathUtil.class.getResource("/").getPath();
		if (isWindows())
			path = path.substring(1, path.length());
		return path;
	}
	
	/** 获取WEB-INF的全路径 */
	public static String getPath()
	{
		String path = PathUtil.class.getResource("/").getPath();
		path = path.substring(0, path.indexOf("WEB-INF"));
		if (isWindows())
			path = path.substring(1, path.length());
		return path;
	}
	
	/** 判断系统是win还是linux */
	public static boolean isWindows()
	{
		if (System.getProperties().getProperty("os.name").toLowerCase().indexOf("windows") != -1)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public static String getFullPath(KzExperiment kzExperimentById) {
		String experimentpath = kzExperimentById.getExperimentpath();
		experimentpath = experimentpath.replace("\\","/");
		int indexOf = experimentpath.indexOf(Messages.couresewareUploadFiles);
		int length = Messages.couresewareUploadFiles.length();
		String flfullpath = experimentpath.substring(indexOf+length+1);
		flfullpath = flfullpath.replaceAll("\\\\", "/");
		return flfullpath;
	}
}
