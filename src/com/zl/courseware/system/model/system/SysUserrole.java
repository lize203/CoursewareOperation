package com.zl.courseware.system.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/** SysUserrole generated by hbm2java */
@Entity
@Table(name = "sys_userrole")
public class SysUserrole implements java.io.Serializable
{
	/**
	 * 
	 */
	private String  flid;
	private SysRole sysRole;
	private SysUser sysUser;
	
	
	public SysUserrole()
	{
	}
	
	public SysUserrole(String flid)
	{
		this.flid = flid;
	}
	
	public SysUserrole(String flid, SysRole sysRole, SysUser sysUser)
	{
		this.flid = flid;
		this.sysRole = sysRole;
		this.sysUser = sysUser;
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
	@JoinColumn(name = "flroleid")
	public SysRole getSysRole()
	{
		return this.sysRole;
	}
	
	public void setSysRole(SysRole sysRole)
	{
		this.sysRole = sysRole;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fluserid")
	public SysUser getSysUser()
	{
		return this.sysUser;
	}
	
	public void setSysUser(SysUser sysUser)
	{
		this.sysUser = sysUser;
	}
}
