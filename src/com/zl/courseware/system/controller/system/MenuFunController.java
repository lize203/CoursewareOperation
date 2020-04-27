package com.zl.courseware.system.controller.system;

import java.util.List;

import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.model.data.Grid;
import com.zl.courseware.system.model.data.Json;
import com.zl.courseware.system.model.data.MenuFun;
import com.zl.courseware.system.service.system.IMenuFunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zl.courseware.system.controller.base.BaseController;


@Controller
@RequestMapping("/menufun")
public class MenuFunController extends BaseController
{
	@Autowired
	private IMenuFunService menuFunService;
	
	
	/** 获取菜单和按钮树数据
	 * 
	 * @param sessionMap
	 * @return */
	@ResponseBody
	@RequestMapping("/getData")
	public Grid getData()
	{
		Grid g = new Grid();
		List<Object> list = menuFunService.getData();
		g.setRows(list);
		g.setTotal(list.size());
		return g;
	}
	
	/** 获取父级菜单
	 * 
	 * @param sessionMap
	 * @return */
	@ResponseBody
	@RequestMapping("/getParentMenu")
	public Grid getParentMenu(String flid)
	{
		Grid g = new Grid();
		List<Object> list = menuFunService.getParentMenu(flid);
		g.setRows(list);
		g.setTotal(list.size());
		return g;
	}
	
	/** 更新保存菜单或者按钮
	 * 
	 * @return */
	@ResponseBody
	@RequestMapping("/savaMenuFun")
	public Json savaMenuFun(MenuFun menfun)
	{
		Json j = new Json();
		if (menuFunService.savaMenuFun(menfun))
		{
			j.setSuccess(true);
		} else
		{
			j.setSuccess(false);
		}
		return j;
	}
	
	/** 添加顶级菜单
	 * 
	 * @return */
	@ResponseBody
	@RequestMapping("/addTopMenu")
	public Json addTopMenu(MenuFun menfun)
	{
		Json j = new Json();
		if (menuFunService.addTopMenu(menfun))
		{
			j.setSuccess(true);
		} else
		{
			j.setSuccess(false);
		}
		return j;
	}
	
	/** 检查改菜单下是否含有子菜单/按钮
	 * 
	 * @param id
	 * @return */
	@ResponseBody
	@RequestMapping("/hasFunOrMenu")
	public Json hasFunOrMenu(String id)
	{
		Json j = new Json();
		j.setCode(menuFunService.hasFunOrMenu(id));
		return j;
	}
	
	/** 追加一个子菜单/按钮
	 * 
	 * @param menfun
	 * @return */
	@ResponseBody
	@RequestMapping("/addChild")
	public Json addChild(MenuFun menfun)
	{
		Json j = new Json();
		if (menuFunService.addChild(menfun))
		{
			j.setSuccess(true);
		} else
		{
			j.setSuccess(false);
		}
		return j;
	}
	
	/** 删除一个子菜单/按钮
	 * 
	 * @param menuid
	 * @param sessionMap
	 * @return */
	@ResponseBody
	@RequestMapping("/deleteOne")
	public Json deleteOne(MenuFun menfun)
	{
		Json j = new Json();
		if (menuFunService.hasChild(menfun))
		{
			j.setCode(GlobalConstant.CANNOT);
		} else
		{
			if (menuFunService.deleteOne(menfun))
			{
				j.setCode(GlobalConstant.SUCCESS);
			} else
			{
				j.setCode(GlobalConstant.FAIL);
			}
		}
		return j;
	}
	
	/** test
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException */
	@RequestMapping("/test")
	public String test() throws InterruptedException
	{
		Thread.sleep(1000);
		return "/page/system/menuFun/MenuFun_AddTopMenu";
	}
}
