package com.zl.courseware.system.model.data;

public class MenuFun
{
	private String  id;
	private String  name;
	private String  url;
	private String  icon;
	private Integer order;
	private String  pid;
	private Integer isvisiable;    // 菜单是否启用，按钮默认为启用
	private String  m_type;        // 按钮类型tag or bar
	private Integer type;          // 标记是菜单还是按钮
	private String  code;          // 区分菜单的是否只有一个子菜单的字段
	private String  eventfunction; // 按钮的js事件字段
	
	
	public MenuFun()
	{
		super();
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public Integer getType()
	{
		return type;
	}
	
	public void setType(Integer type)
	{
		this.type = type;
	}
	
	public String getIcon()
	{
		return icon;
	}
	
	public void setIcon(String icon)
	{
		this.icon = icon;
	}
	
	public Integer getOrder()
	{
		return order;
	}
	
	public void setOrder(Integer order)
	{
		this.order = order;
	}
	
	public Integer getIsvisiable()
	{
		return isvisiable;
	}
	
	public void setIsvisiable(Integer isvisiable)
	{
		this.isvisiable = isvisiable;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public String getEventfunction()
	{
		return eventfunction;
	}
	
	public void setEventfunction(String eventfunction)
	{
		this.eventfunction = eventfunction;
	}
	
	public String getPid()
	{
		return pid;
	}
	
	public void setPid(String pid)
	{
		this.pid = pid;
	}
	
	public String getM_type()
	{
		return m_type;
	}
	
	public void setM_type(String m_type)
	{
		this.m_type = m_type;
	}
	
	public MenuFun(String id, String name, String url, String icon, Integer order, Integer isvisiable, String pid, String m_type, Integer type, String code, String eventfunction)
	{
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.order = order;
		this.isvisiable = isvisiable;
		this.pid = pid;
		this.m_type = m_type;
		this.type = type;
		this.code = code;
		this.eventfunction = eventfunction;
	}
}
