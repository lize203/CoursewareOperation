package com.zl.courseware.system.service.system;

import java.util.List;
import java.util.Set;

import com.zl.courseware.system.model.system.SysFunction;
import com.zl.courseware.system.model.system.SysFunctionpower;
import com.zl.courseware.system.model.system.SysMenu;
import com.zl.courseware.system.model.system.SysMenupower;
import com.zl.courseware.system.model.system.SysRole;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.model.system.SysUserrole;


public interface IRoleManageService
{
	public boolean saveRole(SysRole role);
	
	/** 保存树菜单。
	 * 
	 * @param flid
	 * @param flids
	 * @return */
	public boolean saveRoleAuthority(String roleFild, String flids);
	
	public boolean updateRole(SysRole role);
	
	public boolean deleteRoleById(String flid);
	
	public List<Object> getData(SysUser users);
	
	/** @param flid
	 * @return */
	public String queryMenuXml(String flid);
	
	/** 根据传进来的菜单判断是否选中。 */
	public SysMenu pandu(SysMenu menu, List<SysMenupower> menupowerlist, List<SysFunctionpower> functionpowerlist, String level);
	
	/** 组装菜单字符串
	 * 
	 * @param menu
	 * @return */
	public String pingjieMenu(SysMenu menu);
	
	/** 组装功能字符串
	 * 
	 * @param menu
	 * @return */
	public String pingjieTbsysfunction(SysFunction menu);
	
	/** 判断菜单是否选中。
	 * 
	 * @param menu
	 * @param menupowerlist
	 * @return */
	public SysMenu panduMenu(SysMenu menu, List<SysMenupower> menupowerlist);
	
	/** 判断功能按钮是否选中
	 * 
	 * @param menu
	 * @param menupowerlist
	 * @return */
	public SysFunction panduFun(SysFunction function, List<SysFunctionpower> functionpowerlist);
	
	/** 把菜单按顺序保存。
	 * 
	 * @param menuSet
	 * @return */
	public List<SysMenu> orderByTbsysmenu(Set<SysMenu> menuSet);
	
	/** 按钮按顺序保存。
	 * 
	 * @param menuSet
	 * @return */
	public List<SysFunction> orderByTbsysfunction(Set<SysFunction> funSet);
	
	public boolean checkAccount(String name, String id);
	
	public boolean checkOpertionTypeOnly(int name, String id);
	
	public List<SysUserrole> getSysUserroleByID(String roleid, String userId);
}