package com.zl.courseware.system.service.system;

import java.util.List;

import com.zl.courseware.system.model.system.SysUser;


public interface IUserManageService
{
	public List<Object> getData();
	
	/* 根据所有条件查询用户 */
	public List<Object> query(String str, String query);
	
	/** 检测用户账号是否存在。
	 * 
	 * @param tbsysuser
	 * @return */
	public boolean checkAccount(String name, String id);
	
	
	
	/** @param list
	 * @return */
	public boolean batchSaveUser(List<SysUser> list);
	
	/** 新增用户 */
	public boolean addUser(SysUser tbsysuser, String rolesID);
	
	/** 新增用户（学生，老师，管理员用户） */
	public boolean addUserRoleInfo(SysUser tbsysuser);
	
	public boolean checkAccountByPersonid(String name, String flpersonid);
	
	public SysUser getUserById(int flusertype,String flpersonid);
	
	public boolean updateUserRoleInfo(SysUser tbsysuser);
	
	/** 编辑用户信息
	 * 
	 * @param tbsysuser 用户实例
	 * @param entityID 用户表主键
	 * @param rolesID 角色ID串
	 * @return */
	public boolean updateUser(SysUser tbsysuser, String rolesID);
	
	/** 删除用户。
	 * 
	 * @param flid
	 * @return */
	public boolean delUser(String flid);
	
	/** 批量删除用户。
	 * 
	 * @param flid
	 * @return */
	public boolean batchDel(String flid);
	
	public List<Object> getRoleData(String entityID);
	
	public int queryOldPassWord(SysUser user, String oldpassword);
	
	public boolean editPassWord(SysUser user, String newpassword);
}
