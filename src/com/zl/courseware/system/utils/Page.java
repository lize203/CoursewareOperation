package com.zl.courseware.system.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;


/** 分页对象
 * 
 * @author zhengxl
 * @param <T>
 * @since 2013-12-02 */
@SuppressWarnings("serial")
public class Page<T> implements Serializable
{
	//默认每页10条数据记录
	public static final int RECORD_PER_PAGE_DEFAULT = 10;
	//当前页码
	private int             currentPage             = 1;
	//前一页
	private int             prev;
	//后一页
	private int             next;
	//每页记录数
	private int             recordPerPage           = RECORD_PER_PAGE_DEFAULT;
	//总页数
	private int             totalPage               = 1;
	//总记录数，用于计算总页数
	private int             totalRecord;
	//是否有下一页
	private boolean         hasNext;
	//以否有上一页
	private boolean         hasPrev;
	//分页数据
	private List<T>         records                 = Collections.emptyList();
	
	
	public Page(int currentPage)
	{
	}
	
	/** 根据总记录数，每页记录数和当前页码构造分页对象
	 * 
	 * @param recordPerPage 每页记录数
	 * @param currentPage 当前页码 */
	public Page(int currentPage, int recordPerPage)
	{
		this.recordPerPage = recordPerPage;
		if (this.recordPerPage < 1)
		{
			this.recordPerPage = RECORD_PER_PAGE_DEFAULT;
		}
		this.currentPage = currentPage;
		if (this.currentPage < 1)
		{
			this.currentPage = 1;
		}
		countPage(this.recordPerPage);
	}
	
	/** 根据每页记录数计算总页数
	 * 
	 * @param recordPerPage 每页记录数 */
	public void countPage(int recordPerPage)
	{
		if (this.totalRecord < 1)
		{
			this.totalPage = 1;
			this.currentPage = 1;
		} else
		{
			this.totalPage = (this.totalRecord / recordPerPage) + ((this.totalRecord % recordPerPage) == 0 ? 0 : 1);
		}
	}
	
	public int getCurrentPage()
	{
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}
	
	public int getPrev()
	{
		prev = this.currentPage - 1;
		if (prev < 1)
		{
			return this.currentPage;
		}
		return prev;
	}
	
	public int getNext()
	{
		next = this.currentPage + 1;
		if (next > this.totalPage)
		{
			return this.currentPage;
		}
		return next;
	}
	
	public int getRecordPerPage()
	{
		return recordPerPage;
	}
	
	public void setRecordPerPage(int recordPerPage)
	{
		this.recordPerPage = recordPerPage;
		if (this.recordPerPage < 1)
		{
			this.recordPerPage = RECORD_PER_PAGE_DEFAULT;
		}
		countPage(this.recordPerPage); //根据指定的每页记录数重新计算总页数
	}
	
	public int getTotalPage()
	{
		return totalPage;
	}
	
	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}
	
	public int getTotalRecord()
	{
		return totalRecord;
	}
	
	public void setTotalRecord(int totalRecord)
	{
		this.totalRecord = totalRecord;
	}
	
	public boolean hasNext()
	{
		hasNext = false;
		if (this.currentPage < this.totalPage)
		{
			hasNext = true;
		}
		return hasNext;
	}
	
	public boolean hasPrev()
	{
		hasPrev = false;
		if (this.currentPage > 1)
		{
			hasPrev = true;
		}
		return hasPrev;
	}
	
	public List<T> getRecords()
	{
		return records;
	}
	
	public void setRecords(List<T> records)
	{
		this.records = records;
	}
}
