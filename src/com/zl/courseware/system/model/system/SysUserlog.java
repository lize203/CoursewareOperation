package com.zl.courseware.system.model.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/** SysUserlog generated by hbm2java */
@Entity
@Table(name = "sys_userlog")
public class SysUserlog implements java.io.Serializable
{
	/**
	 * 
	 */
	private String id;
	private Date   time;
	private String description;
	private String url;
	private String parameter;
	private String operator;
	private String ip;
	private String type;
	private String userid;
	
	
	public SysUserlog()
	{
	}
	
	public SysUserlog(String id)
	{
		this.id = id;
	}
	
	public SysUserlog(String id, Date time, String description, String url, String parameter, String operator, String ip, String type,String userid)
	{
		this.id = id;
		this.time = time;
		this.description = description;
		this.url = url;
		this.parameter = parameter;
		this.operator = operator;
		this.ip = ip;
		this.type = type;
		this.userid = userid;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time", length = 19)
	public Date getTime()
	{
		return this.time;
	}
	
	public void setTime(Date time)
	{
		this.time = time;
	}
	
	@Column(name = "description", length = 500)
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	@Column(name = "url", length = 500)
	public String getUrl()
	{
		return this.url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	@Column(name = "parameter", length = 500)
	public String getParameter()
	{
		return this.parameter;
	}
	
	public void setParameter(String parameter)
	{
		this.parameter = parameter;
	}
	
	@Column(name = "operator", length = 200)
	public String getOperator()
	{
		return this.operator;
	}
	
	public void setOperator(String operator)
	{
		this.operator = operator;
	}
	
	@Column(name = "ip", length = 200)
	public String getIp()
	{
		return this.ip;
	}
	
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	@Column(name = "type", length = 200)
	public String getType()
	{
		return this.type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}

	@Column(name = "userid", length = 32)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}