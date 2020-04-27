package com.zl.courseware.system.service.system;

import com.zl.courseware.system.model.data.Grid;
import com.zl.courseware.system.model.system.SysUserlog;


public interface IUserLogService
{
	public Grid getData(int page, int rows);
	
	public Grid query(String query, String str, int page, int rows);
	
	public SysUserlog getLog(String id);
}