package com.zl.courseware.system.service.system;

import java.util.List;

import com.zl.courseware.system.model.data.MenuFun;


public interface IMenuFunService
{
	public List<Object> getData();
	
	public boolean savaMenuFun(MenuFun menfun);
	
	public boolean addTopMenu(MenuFun menfun);
	
	public Integer hasFunOrMenu(String id);
	
	public boolean addChild(MenuFun menfun);
	
	public boolean deleteOne(MenuFun menfun);
	
	public boolean hasChild(MenuFun menfun);
	
	public List<Object> getParentMenu(String flid);
	
	public String findIDByName(String rESULT_NAME);
}
