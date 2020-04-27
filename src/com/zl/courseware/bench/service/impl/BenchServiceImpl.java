/**
 * 
 */
package com.zl.courseware.bench.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zl.courseware.bench.dao.IBenchDao;
import com.zl.courseware.system.model.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zl.courseware.bench.model.SysShortcut;
import com.zl.courseware.bench.service.IBenchService;

@Service
public class BenchServiceImpl implements IBenchService {
	
	@Autowired
	@Qualifier("benchDao")
	private IBenchDao benchDao;
	
	@Override
	public Map<String, Object> getChange(String aduitman) {
		return benchDao.getChange(aduitman);
	}

	@Override
	public Map<String, Object> getMenu(String flid) {
		return benchDao.getMenu(flid);
	}

	@Override
	public Map<String, Object> getShortcut(String id,Set<String> menuids) {
		return benchDao.getShortcut(id,menuids);
	}

	@Override
	public void save(SysShortcut tbbmshortcut) {
		benchDao.save(tbbmshortcut);
	}
	
	@Override
	public void remove(String flid){
		benchDao.remove(flid);
	}
	
	@Override
	public SysMenu getAncestorMenu(String menuId) {
		List<SysMenu> menus = benchDao.getAncestorMenu(menuId);
		
        if (!menus.isEmpty()) {
        	SysMenu menu = menus.get(0);
            while (menu.getSysMenu() != null) {
                menu = menu.getSysMenu();
            }
            return menu;
        } 
        return null;
	}


	@Override
	public void saveObject(SysShortcut tbbmshortcut) {
		benchDao.save(tbbmshortcut);
	}

}