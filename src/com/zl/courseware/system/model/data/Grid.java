package com.zl.courseware.system.model.data;

import java.util.ArrayList;
import java.util.List;


public class Grid implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3157949104918362650L;
	private Integer           total            = 0;
	private List<Object>      rows             = new ArrayList<Object>();
	
	
	public Integer getTotal()
	{
		return total;
	}
	
	public void setTotal(Integer total)
	{
		this.total = total;
	}
	
	public List<Object> getRows()
	{
		return rows;
	}
	
	public void setRows(List<Object> rows)
	{
		this.rows = rows;
	}
}
