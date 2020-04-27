package com.zl.courseware.system.model.data;

public class Directory
{
	private String  id;
	private String  dirName;
	private String  dirPath;
	private String  lastModifyDate;
	private Integer type;// 文件类型，目录还是文件，0,1
	private String  size;//文件大小，MB单位自动转换
	private String  remark;//目录的备注
	
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getDirName()
	{
		return dirName;
	}
	public void setDirName(String dirName)
	{
		this.dirName = dirName;
	}
	public String getDirPath()
	{
		return dirPath;
	}
	public void setDirPath(String dirPath)
	{
		this.dirPath = dirPath;
	}
	public String getLastModifyDate()
	{
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate)
	{
		this.lastModifyDate = lastModifyDate;
	}
	public Integer getType()
	{
		return type;
	}
	public void setType(Integer type)
	{
		this.type = type;
	}
	public String getSize()
	{
		return size;
	}
	public void setSize(String size)
	{
		this.size = size;
	}
	 
	 
}
