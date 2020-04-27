package com.zl.courseware.system.model.data;

public class Json implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6109867926667603040L;
	private boolean           success          = false;
	private String            msg              = "";
	private Integer           code             = null;
	private Object            obj              = null;
	
	
	public boolean isSuccess()
	{
		return success;
	}
	
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	public String getMsg()
	{
		return msg;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public Object getObj()
	{
		return obj;
	}
	
	public void setObj(Object obj)
	{
		this.obj = obj;
	}
	
	public Integer getCode()
	{
		return code;
	}
	
	public void setCode(Integer code)
	{
		this.code = code;
	}
}
