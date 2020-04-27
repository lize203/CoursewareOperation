package com.zl.courseware.system.service.system;

import java.util.List;
import java.util.Set;

import com.zl.courseware.system.model.data.Json;
import com.zl.courseware.system.model.system.SysUser;


public interface IMainFormService
{
	public Json getFirstChildMenu(String parentId);
	
	public List<Object> getData(String parent, SysUser user, Set<String> menusId);
	
	public Json getMenu(String childid);
}
