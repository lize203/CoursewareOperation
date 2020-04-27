/**
 * 修改人：long
 * 修改时间：2015-03-06
 * 修改内容：使业务层声明事务异常时可回滚，业务层事务方法异常统一上浮；业务方法异常，返回false或null统一在操作日志拦截器中设置。
 * 详见：com.snc.system.framework.interceptors.OperationLogInterceptor#before
 */
package com.zl.courseware.system.service.system.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zl.courseware.system.model.data.MenuFun;
import com.zl.courseware.system.model.system.SysFunction;
import com.zl.courseware.system.model.system.SysMenu;
import com.zl.courseware.system.service.system.IMenuFunService;
import com.zl.courseware.system.utils.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zl.courseware.system.dao.BaseDaoI;
import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.framework.constant.LogAnnotation;


@Service
public class MenuFunServiceImpl implements IMenuFunService
{
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysFunction> funDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysMenu>     menuDao;
	private static final Logger   LOG = LoggerFactory.getLogger(MenuFunServiceImpl.class);
	
	
	/** 获取菜单和按钮树 */
	@Override
	@LogAnnotation(opDescribe = "获取菜单和按钮树", opType = "list")
	public List<Object> getData()
	{
		List<Object> value = new ArrayList<Object>();
		try
		{
			// 获取所有菜单第一级菜单
			String hql = "from SysMenu where sysMenu.flid=null";
			List<SysMenu> menulist = menuDao.find(hql);
			for (SysMenu tbsysmenu : menulist)
			{
				Map<String, Object> row = new HashMap<String, Object>();
				sysMenuToMap(row, tbsysmenu);
				row.put("pid", "");
				value.add(row);
				// 排序
				orderList(value, tbsysmenu, null);
				// 递归加载菜单和树
				buildTree(tbsysmenu, row);
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
		return value;
	}
	
	/** 递归加载菜单和按钮树
	 * 
	 * @param menu
	 * @param row2 */
	private void buildTree(SysMenu menu, Map<String, Object> data)
	{
		List<SysFunction> funlist = new ArrayList<SysFunction>(menu.getSysFunctions());
		List<Object> value = new ArrayList<Object>();
		if (!funlist.isEmpty())
		{
			for (SysFunction fun : funlist)
			{
				Map<String, Object> row = new HashMap<String, Object>();
				sysFunctionToMap(row, fun);
				row.put("pid", menu.getFlid());
				value.add(row);
				data.put("state", "closed");
				data.put("children", value);
				// 排序
				orderList(value, null, fun);
			}
		} else
		{
			List<SysMenu> menulist = new ArrayList<SysMenu>(menu.getSysMenus());
			for (SysMenu tbsysmenu : menulist)
			{
				Map<String, Object> row = new HashMap<String, Object>();
				sysMenuToMap(row, tbsysmenu);
				row.put("pid", menu.getFlid());
				row.put("state", "open");
				value.add(row);
				data.put("children", value);
				data.put("state", "closed");
				// 排序
				orderList(value, tbsysmenu, null);
				// 递归加载菜单和树
				buildTree(tbsysmenu, row);
			}
		}
	}
	
	/** 将list对象排序
	 * 
	 * @param value
	 * @param object */
	private void orderList(List<Object> value, SysMenu tbsysmenu, SysFunction fun)
	{
		int size = value.size();
		String florderString1 = "";
		if (tbsysmenu != null)
		{
			florderString1 = tbsysmenu.getFlorder().toString();
		} else if (fun != null)
		{
			florderString1 = fun.getFlorder().toString();
		}
		for (int j = 0; j < size - 1; j++)
		{
			Map<String, Object> temRow = (HashMap<String, Object>) value.get(j);
			String florderString = temRow.get("order").toString().trim();
			if (StringUtils.isBlank(florderString) == true || StringUtils.isBlank(florderString1) == true)
			{
				continue;
			}
			boolean flag = Integer.parseInt(florderString1) < Integer.parseInt(florderString) ? true : false;
			if (flag)
			{
				for (int k = size - 1; j < k; k--)
				{
					Collections.swap(value, k, k - 1);
				}
				break;
			}
		}
	}
	
	/** 更新菜单/按钮 */
	@Override
	@LogAnnotation(opDescribe = "更新菜单/按钮", opType = "update")
	public boolean savaMenuFun(MenuFun menufun)
	{
		if (menufun.getType().intValue() == GlobalConstant.MENU)
		{
			String hql = "from SysMenu where flid=?";
			SysMenu menu = menuDao.find(hql, new Object[] { menufun.getId() }).get(0);
			menu.setFlicon(menufun.getIcon());
			menu.setFlisvisiable(menufun.getIsvisiable());
			menu.setFlname(menufun.getName());
			menu.setFlorder(menufun.getOrder());
			if (!"".equals(menufun.getPid()) && menufun.getPid() != null)
			{
				menu.setSysMenu(new SysMenu(menufun.getPid()));
			} else
			{
				menu.setSysMenu(null);
			}
			if (!menu.getSysMenus().isEmpty())
			{
				menu.setFlurl(GlobalConstant.PARENT_URL);
			} else if (menufun.getUrl() == null || "".equals(menufun.getUrl()))
			{
				menu.setFlurl(GlobalConstant.PARENT_URL);
			} else
			{
				menu.setFlurl(menufun.getUrl());
			}
			updateOrder(menu, menufun, GlobalConstant.IS_UPDATE);
			menuDao.update(menu);
		} else
		{
			String hql = "from SysFunction where flid=?";
			SysFunction fun = funDao.find(hql, new Object[] { menufun.getId() }).get(0);
			fun.setFleventfunction(menufun.getEventfunction());
			fun.setFlfunctionname(menufun.getName());
			fun.setFlorder(menufun.getOrder());
			fun.setFlurl(menufun.getM_type());
			fun.setFlicon(menufun.getIcon());
			if (!"".equals(menufun.getPid()) && menufun.getPid() != null)
			{
				fun.setSysMenu(new SysMenu(menufun.getPid()));
			}
			updateOrder(fun, menufun, GlobalConstant.IS_UPDATE);
			funDao.update(fun);
		}
		return true;
	}
	
	/** 插入或者更新按钮时重新排序
	 * 
	 * @param menu */
	private void updateOrder(SysFunction fun, MenuFun menufun, Integer isUpdate)
	{
		String hql = "";
		if (GlobalConstant.BAR.equals(fun.getFlurl()))
		{
			hql = "from SysFunction where flurl='" + GlobalConstant.BAR + "' and florder=" + menufun.getOrder()
			        + " and sysMenu.flid='" + fun.getSysMenu().getFlid() + "'";
			String sql = "update sys_function set florder=florder+1 where flurl='" + GlobalConstant.BAR
			        + "' and florder>" + (menufun.getOrder() - 1) + " and flmenuid='" + fun.getSysMenu().getFlid()
			        + "'";
			if (isUpdate == GlobalConstant.IS_UPDATE)
			{
				hql = hql + " and flid!='" + fun.getFlid() + "'";
				sql = sql + " and flid!='" + fun.getFlid() + "'";
			}
			if (!funDao.find(hql).isEmpty())
			{
				funDao.executeSql(sql);
			}
		}
	}
	
	/** 插入或者更新菜单时重新排序
	 * 
	 * @param menu */
	private void updateOrder(SysMenu menu, MenuFun menufun, Integer addOrUpdate)
	{
		String sql = "";
		String hql = "";
		if (menu.getSysMenu() != null)
		{
			hql = "from SysMenu where sysMenu.flid='" + menu.getSysMenu().getFlid() + "' and florder="
			        + menufun.getOrder();
			sql = "update sys_menu set florder=florder+1 where flparentid='" + menu.getSysMenu().getFlid()
			        + "' and florder>" + (menufun.getOrder() - 1);
		} else
		{
			hql = "from SysMenu where flurl='#' and florder=" + menufun.getOrder();
			sql = "update sys_menu set florder=florder+1 where flurl='#' and florder>" + (menufun.getOrder() - 1);
		}
		if (addOrUpdate == GlobalConstant.IS_UPDATE)
		{
			hql = hql + " and flid!='" + menu.getFlid() + "'";
			sql = sql + " and flid!='" + menu.getFlid() + "'";
		}
		if (!menuDao.find(hql).isEmpty())
		{
			menuDao.executeSql(sql);
		}
	}
	
	/** 新增顶级菜单 */
	@Override
	@LogAnnotation(opDescribe = "新增顶级菜单", opType = "add")
	public boolean addTopMenu(MenuFun menufun)
	{
		SysMenu menu = new SysMenu();
		menu.setFlcode(menufun.getCode());
		menu.setFlicon(menufun.getIcon());
		menu.setFlid(UUIDUtil.getUUID());
		menu.setFlisvisiable(menufun.getIsvisiable());
		menu.setFlname(menufun.getName());
		menu.setFlorder(menufun.getOrder());
		menu.setFlurl(menufun.getUrl());
		updateOrder(menu, menufun, GlobalConstant.IS_ADD);
		menuDao.save(menu);
		return true;
	}
	
	/** 检查该菜单下时候包含按钮或菜单 */
	@Override
	public Integer hasFunOrMenu(String id)
	{
		try
		{
			String hql = "from SysMenu where flid=?";
			SysMenu m = menuDao.find(hql, new Object[] { id }).get(0);
			if (GlobalConstant.ONE.equals(m.getFlcode()))
			{
				if (!m.getSysMenus().isEmpty())
				{
					return GlobalConstant.ONLYONE;
				} else
				{
					if (!m.getSysFunctions().isEmpty())
					{
						return GlobalConstant.HAS_FUN;
					} else if (!m.getSysMenus().isEmpty())
					{
						return GlobalConstant.HAS_MENU;
					} else
					{
						return GlobalConstant.HAS_NO;
					}
				}
			} else
			{
				if (!m.getSysFunctions().isEmpty())
				{
					return GlobalConstant.HAS_FUN;
				} else if (!m.getSysMenus().isEmpty())
				{
					return GlobalConstant.HAS_MENU;
				} else
				{
					return GlobalConstant.HAS_NO;
				}
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
			return GlobalConstant.HAS_NO;
		}
	}
	
	/** 添加子菜单/按钮 */
	@Override
	@LogAnnotation(opDescribe = "追加子菜单/按钮", opType = "add")
	public boolean addChild(MenuFun menufun)
	{
		String hql = "from SysMenu where flid=?";
		SysMenu pm = menuDao.find(hql, new Object[] { menufun.getPid() }).get(0);
		if (menufun.getType().intValue() == GlobalConstant.MENU)
		{
			SysMenu menu = new SysMenu();;
			menu.setFlcode("".equalsIgnoreCase(menufun.getCode()) ? null : menufun.getCode());
			menu.setFlicon(menufun.getIcon());
			menu.setFlisvisiable(menufun.getIsvisiable());
			menu.setFlname(menufun.getName());
			menu.setFlorder(menufun.getOrder());
			if (menufun.getUrl() == null || "".equals(menufun.getUrl()))
			{
				menu.setFlurl(GlobalConstant.PARENT_URL);
			} else
			{
				menu.setFlurl(menufun.getUrl());
			}
			menu.setFlid(UUIDUtil.getUUID());
			menu.setSysMenu(pm);
			updateOrder(menu, menufun, GlobalConstant.IS_ADD);
			menuDao.save(menu);
			pm.setFlurl(GlobalConstant.PARENT_URL);
			menuDao.update(pm);
		} else
		{
			SysFunction fun = new SysFunction();
			fun.setFleventfunction(menufun.getEventfunction());
			fun.setFlfunctionname(menufun.getName());
			fun.setFlorder(menufun.getOrder());
			fun.setFlurl(menufun.getM_type());
			fun.setFlid(UUIDUtil.getUUID());
			fun.setFlicon(menufun.getIcon());
			fun.setSysMenu(pm);
			updateOrder(fun, menufun, GlobalConstant.IS_ADD);
			funDao.save(fun);
		}
		return true;
	}
	
	/** 删除一个子菜单/按钮 */
	@Override
	@LogAnnotation(opDescribe = "删除一个子菜单/按钮", opType = "del")
	public boolean deleteOne(MenuFun menufun)
	{
		if (menufun.getType().intValue() == GlobalConstant.MENU)
		{
			String hql = "delete from SysShortcut where sysMenu.flid=?";
			menuDao.executeHql(hql, new Object[] { menufun.getId() });
			hql = "delete from SysMenupower where sysMenu.flid=?";
			menuDao.executeHql(hql, new Object[] { menufun.getId() });
			hql = "delete from SysMenu where flid=?";
			menuDao.executeHql(hql, new Object[] { menufun.getId() });
		} else
		{
			String hql = "delete from SysFunctionpower where sysFunction.flid=?";
			funDao.executeHql(hql, new Object[] { menufun.getId() });
			hql = "delete from SysFunction where flid=?";
			funDao.executeHql(hql, new Object[] { menufun.getId() });
		}
		return true;
	}
	
	/** 删除时判断是否有子节点 */
	@Override
	public boolean hasChild(MenuFun menufun)
	{
		if (menufun.getType().intValue() == GlobalConstant.MENU)
		{
			String hql = "from SysMenu where flid=?";
			SysMenu m = menuDao.find(hql, new Object[] { menufun.getId() }).get(0);
			if (m.getSysMenus().isEmpty() && m.getSysFunctions().isEmpty())
			{
				return false;
			} else
			{
				return true;
			}
		} else
		{
			return false;
		}
	}
	
	/** 获取父级菜单 */
	@Override
	public List<Object> getParentMenu(String flid)
	{
		List<Object> value = new ArrayList<Object>();
		try
		{
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("name", "无");
			row.put("id", "");
			row.put("order", 0);
			value.add(row);
			// 获取所有菜单第一级菜单
			String hql = "from SysMenu where sysMenu.flid=null and flid <> '" + flid + "'";
			List<SysMenu> menulist = menuDao.find(hql);
			for (SysMenu tbsysmenu : menulist)
			{
				row = new HashMap<String, Object>();
				sysMenuToMap(row, tbsysmenu);
				row.put("pid", "");
				value.add(row);
				// 排序
				orderList(value, tbsysmenu, null);
				// 递归加载菜单和树
				buildParentMenuTree(tbsysmenu, row, flid);
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
		return value;
	}
	
	private void buildParentMenuTree(SysMenu menu, Map<String, Object> data, String flid)
	{
		List<Object> value = new ArrayList<Object>();
		List<SysMenu> menulist = new ArrayList<SysMenu>(menu.getSysMenus());
		for (SysMenu tbsysmenu : menulist)
		{
			if (flid.equals(tbsysmenu.getFlid()))
			{
				continue;
			}
			Map<String, Object> row = new HashMap<String, Object>();
			sysMenuToMap(row, tbsysmenu);
			row.put("pid", menu.getFlid());
			row.put("state", "open");
			value.add(row);
			data.put("children", value);
			data.put("state", "closed");
			// 排序
			orderList(value, tbsysmenu, null);
			// 递归加载菜单和树
			buildParentMenuTree(tbsysmenu, row, flid);
		}
	}
	
	private void sysMenuToMap(Map<String, Object> row, SysMenu tbsysmenu)
	{
		row.put("name", tbsysmenu.getFlname());
		row.put("id", tbsysmenu.getFlid());
		row.put("code", tbsysmenu.getFlcode());
		row.put("icon", tbsysmenu.getFlicon());
		row.put("isvisiable", tbsysmenu.getFlisvisiable());
		row.put("url", tbsysmenu.getFlurl());
		row.put("order", tbsysmenu.getFlorder());
		row.put("eventfunction", "");
		row.put("type", GlobalConstant.MENU);
	}
	
	private void sysFunctionToMap(Map<String, Object> row, SysFunction fun)
	{
		row.put("name", fun.getFlfunctionname());
		row.put("id", fun.getFlid());
		row.put("eventfunction", fun.getFleventfunction());
		row.put("icon", fun.getFlicon());
		row.put("code", "");
		row.put("isvisiable", GlobalConstant.ISDISABLE);
		row.put("m_type", fun.getFlurl());
		row.put("order", fun.getFlorder());
		row.put("type", GlobalConstant.FUN);
	}
	
	/** 根据菜单名称获取id */
	@Override
	public String findIDByName(String name)
	{
		String hql = "from SysMenu where flname=?";
		List<SysMenu> list = menuDao.find(hql, new Object[] { name });
		if (list.size() == 1)
		{
			return list.get(0).getFlid();
		}
		return null;
	}
}
