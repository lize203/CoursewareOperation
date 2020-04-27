package com.zl.courseware.system.service.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.zl.courseware.system.model.system.SysFunction;
import com.zl.courseware.system.model.system.SysMenu;
import com.zl.courseware.system.model.system.SysUser;


public interface ILoginService
{
	public SysUser LoginUser(SysUser user);
	
	public Integer getRoleLevel(String userid);
	
	public void loginMethod(SysUser user, List<SysFunction> functionlist, List<Map<String, Object>> secondMenus);
	
	public List<SysMenu> getTopMenus(SysUser user);
	
	public void logout(HttpSession session);
	
	public List<SysMenu> getMenus(SysUser user);
}
