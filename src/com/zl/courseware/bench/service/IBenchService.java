/**
 * 
 */
package com.zl.courseware.bench.service;

import java.util.Map;
import java.util.Set;

import com.zl.courseware.bench.model.SysShortcut;
import com.zl.courseware.system.model.system.SysMenu;


public interface IBenchService {

	/**
	 * 查询受理人是自己的工单
	 * @return
	 */
	public Map<String, Object> getChange(String aduitman);


	/**
	 * 查询可操作的模块
	 * @return
	 */
	public Map<String, Object>  getMenu(String flid);


	/**
	 * 
	 * 查询快捷方式
	 * @param id
	 * @param menuids 
	 * @return
	 */
	public Map<String, Object>  getShortcut(String id, Set<String> menuids);


	/**
	 * 添加快捷方式
	 * @param tbbmshortcut
	 * @return boolean
	 */
	public void save(SysShortcut tbbmshortcut);

	/**
	 * 删除快捷方式
	 * @param flid
	 * @return
	 */
	public void remove(String flid);
	
    /**
     * 获取快捷方式关联菜单的最顶级菜单
     *
     * @param menuId 快捷方式关联菜单的ID
     * @return
     */
    public SysMenu getAncestorMenu(String menuId);

    /**
     * 
     * @param tbbmshortcut
     * @return
     */
	public void saveObject(SysShortcut tbbmshortcut);
}
