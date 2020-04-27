package com.zl.courseware.system.model.pojo;

import java.io.Serializable;

/**
 * GZIP实体
 * @author helix
 *
 */
public class GzipDTO implements Serializable
{
	private String name;
	private long size;
	public GzipDTO()
    {
	    super();
    }
	public String getName()
    {
    	return name;
    }
	public void setName(String name)
    {
    	this.name = name;
    }
	public long getSize()
    {
    	return size;
    }
	public void setSize(long size)
    {
    	this.size = size;
    }
	@Override
    public String toString()
    {
	    return "GzipDTO [name=" + name + ", size=" + size + "]";
    }
	public GzipDTO(String name, long size)
    {
	    super();
	    this.name = name;
	    this.size = size;
    }
	
}
