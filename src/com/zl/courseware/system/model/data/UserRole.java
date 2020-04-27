package com.zl.courseware.system.model.data;

import com.zl.courseware.system.model.system.SysRole;
import com.zl.courseware.system.model.system.SysUser;


public class UserRole
{
	private SysRole role;
	private SysUser user;
	
	
	public SysRole getRole()
	{
		return role;
	}
	
	public void setRole(SysRole role)
	{
		this.role = role;
	}
	
	public SysUser getUser()
	{
		return user;
	}
	
	public void setUser(SysUser user)
	{
		this.user = user;
	}
	
	public UserRole()
	{
		super();
	}
	
	public UserRole(SysRole role, SysUser user)
	{
		super();
		this.role = role;
		this.user = user;
	}
}
