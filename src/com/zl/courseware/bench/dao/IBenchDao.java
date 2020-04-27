package com.zl.courseware.bench.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zl.courseware.bench.model.SysShortcut;
import com.zl.courseware.system.dao.BaseDaoI;
import com.zl.courseware.system.model.system.SysMenu;

public interface IBenchDao extends BaseDaoI<SysShortcut>{

	/**
	 * 查询受理人是自己的工单
	 * @return
	 */
	public Map<String, Object> getChange(String aduitman);
	
	/**
	 * 查询可操作的模块
	 * @return
	 */
	public Map<String, Object> getMenu(String flid);
	
	/**
	 * 查询快捷方式
	 * @param id
	 * @param menuids 
	 * @return
	 */
	public Map<String, Object> getShortcut(String id, Set<String> menuids);
	
	/**
	 * 删除快捷方式
	 * @param flid
	 * @return
	 */
	public void remove(String flid);

	/**
	 * 获取该菜单的上次菜单
	 * @param menuId
	 * @return
	 */
	public List<SysMenu> getAncestorMenu(String menuId);

}