package com.zl.courseware.system.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/** SysFunctionpower generated by hbm2java */
@Entity
@Table(name = "sys_functionpower")
public class SysFunctionpower implements java.io.Serializable
{
	/**
	 * 
	 */
	private String      flid;
	private SysFunction sysFunction;
	private SysRole     sysRole;
	
	
	public SysFunctionpower()
	{
	}
	
	public SysFunctionpower(String flid)
	{
		this.flid = flid;
	}
	
	public SysFunctionpower(String flid, SysFunction sysFunction, SysRole sysRole)
	{
		this.flid = flid;
		this.sysFunction = sysFunction;
		this.sysRole = sysRole;
	}
	
	@Id
	@Column(name = "flid", unique = true, nullable = false, length = 32)
	public String getFlid()
	{
		return this.flid;
	}
	
	public void setFlid(String flid)
	{
		this.flid = flid;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flfunctionid")
	public SysFunction getSysFunction()
	{
		return this.sysFunction;
	}
	
	public void setSysFunction(SysFunction sysFunction)
	{
		this.sysFunction = sysFunction;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flroleid")
	public SysRole getSysRole()
	{
		return this.sysRole;
	}
	
	public void setSysRole(SysRole sysRole)
	{
		this.sysRole = sysRole;
	}
}
