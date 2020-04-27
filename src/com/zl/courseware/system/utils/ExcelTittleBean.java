package com.zl.courseware.system.utils;

public class ExcelTittleBean
{
	private String rowfrom; //标题单元格的起始行坐标
	private String rowto;   //标题单元格的合并到行坐标
	private String colfrom; //标题单元格的起始列坐标
	private String colto;   //标题单元格的合并到列坐标
	private String value;   //标题单元格的内容
	
	
	public ExcelTittleBean()
	{
		super();
	}
	
	public String getRowfrom()
	{
		return rowfrom;
	}
	
	public void setRowfrom(String rowfrom)
	{
		this.rowfrom = rowfrom;
	}
	
	public String getRowto()
	{
		return rowto;
	}
	
	public void setRowto(String rowto)
	{
		this.rowto = rowto;
	}
	
	public String getColfrom()
	{
		return colfrom;
	}
	
	public void setColfrom(String colfrom)
	{
		this.colfrom = colfrom;
	}
	
	public String getColto()
	{
		return colto;
	}
	
	public void setColto(String colto)
	{
		this.colto = colto;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/** 构造函数
	 * 
	 * @param rowfrom 起始行坐标
	 * @param colfrom 合并到行坐标
	 * @param rowto 起始行坐标
	 * @param colto 合并到行坐标
	 * @param value 内容 */
	public ExcelTittleBean(String rowfrom, String colfrom, String rowto, String colto, String value)
	{
		super();
		this.rowfrom = rowfrom;
		this.rowto = rowto;
		this.colfrom = colfrom;
		this.colto = colto;
		this.value = value;
	}
}
