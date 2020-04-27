package com.zl.courseware.system.service.system.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.zl.courseware.system.service.system.ILoginService;
import com.zl.courseware.system.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zl.courseware.system.dao.BaseDaoI;
import com.zl.courseware.system.framework.constant.LogAnnotation;
import com.zl.courseware.system.model.system.SysFunction;
import com.zl.courseware.system.model.system.SysFunctionpower;
import com.zl.courseware.system.model.system.SysMenu;
import com.zl.courseware.system.model.system.SysMenupower;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.model.system.SysUserrole;


@Service
public class LoginServiceImpl implements ILoginService
{
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysUser>          userDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysMenu>          menuDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysUserrole>      userRoleDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysMenupower>     menuPowerDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysFunctionpower> funDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysFunction>      functionDao;
	private final Logger               LOG = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	
	/** 用户登录 */
	@Override
	@LogAnnotation(opDescribe = "用户登录", opType = "login")
	public SysUser LoginUser(SysUser user)
	{
		try
		{
			String hashedPassword = MD5Util.md5(user.getFlpassword());
			String hql = "from SysUser a where a.flaccount=?  and a.flpassword=? and fldisplay=1";
			List<SysUser> list = userDao.find(hql, new Object[] { user.getFlaccount(), hashedPassword });
			if (!list.isEmpty())
			{
				return list.get(0);
			} else
			{
				return null;
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
			return null;
		}
	}
	
	/** 获取用户最大的角色等级 */
	@Override
	public Integer getRoleLevel(String userid)
	{
		try
		{
			String hql = "select min(a.sysRole.fllevel) from SysUserrole a " + "where a.sysUser.flid=?";
			List list = userRoleDao.find(hql, new Object[] { userid });
			return Integer.parseInt(list.get(0).toString());
		} catch (Exception e)
		{
			LOG.error("running error:", e);
			return null;
		}
	}
	
	/** 装载有子菜单的所有菜单集合和用户有权限的功能按钮集合 */
	@Override
	public void loginMethod(SysUser user, List<SysFunction> functionlist, List<Map<String, Object>> secondMenus)
	{
		try
		{
			List<SysMenupower> tbsysmenupower = null;
			String hql = "";
			List<SysFunctionpower> functionpowerlist = null;
			List<SysMenu> firstMenus = new ArrayList<SysMenu>();
			if ("admin".equals(user.getFlaccount()))
			{
				hql = "from SysMenu where flisvisiable=1 order by florder asc";
				firstMenus = menuDao.find(hql);
				hql = "from SysFunction order by florder asc";
				List<SysFunction> funs = functionDao.find(hql);
				for (SysFunction fun : funs)
				{
					if (!functionlist.contains(fun))
					{
						SysMenu m = new SysMenu();
						m.setFlid(fun.getSysMenu().getFlid());
						fun.setSysMenu(m);
						functionlist.add(fun);
					}
				}
			} else
			{
				hql = "from SysUserrole where sysUser.flid=?";
				List<SysUserrole> userroleList = userRoleDao.find(hql, new Object[] { user.getFlid() });
				String rolesid = "";
				for (SysUserrole userrole : userroleList)
				{
					if (rolesid != "")
						rolesid += ",";
					rolesid += "'" + userrole.getSysRole().getFlid() + "'";
				}
				hql = "from SysMenupower b left join fetch b.sysMenu "
				        + "where b.sysMenu.flisvisiable=1 and b.sysRole.flid in(" + rolesid + ")"
				        + " order by b.sysMenu.florder asc";
				tbsysmenupower = menuPowerDao.find(hql);
				hql = "from SysFunctionpower a left join fetch a.sysFunction where a.sysRole.flid in (" + rolesid
				        + ") order by florder asc";
				functionpowerlist = funDao.find(hql);
				for (int i = 0; i < functionpowerlist.size(); i++)
				{
					SysFunction function = functionpowerlist.get(i).getSysFunction();
					if (!functionlist.contains(function))
					{
						SysMenu m = new SysMenu();
						m.setFlid(function.getSysMenu().getFlid());
						function.setSysMenu(m);
						functionlist.add(function);
					}
				}
				for (SysMenupower power : tbsysmenupower)
				{
					firstMenus.add(power.getSysMenu());
				}
			}
			for (SysMenu tbsysmenu : firstMenus)
			{
				SysMenu m = null;
				Map<String, Object> map = new HashMap<String, Object>();
				if (tbsysmenu.getSysMenu() == null)
				{
					m = tbsysmenu;
				}
				if (tbsysmenu.getSysMenu() != null)
				{
					m = tbsysmenu.getSysMenu();
				}
				map.put("flname", tbsysmenu.getFlname());
				map.put("flicon", tbsysmenu.getFlicon());
				map.put("florder", tbsysmenu.getFlorder() + "");
				map.put("id", tbsysmenu.getFlid());
				map.put(
				        "flid",
				        tbsysmenu.getFlid() + (tbsysmenu.getFlcode() == null ? "" : ("_" + tbsysmenu.getFlcode())));
				secondMenus.add(map);
				/*
				 * if (!secondMenus.contains(m)) { 此判断不服逻辑 } */
			}
			Collections.sort(secondMenus, new Comparator<Map<String, Object>>()
			{
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2)
				{
					Integer o11 = Integer.parseInt((String) o1.get("florder"));
					Integer o22 = Integer.parseInt((String) o2.get("florder"));
					return o11.compareTo(o22);
				}
			});
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
	}
	
	/** 获取当前用户有权访问的一级菜单集合
	 * 
	 * @since 2014-01-15
	 * @author zhengxl
	 * @return */
	@Override
	public List<SysMenu> getTopMenus(SysUser user)
	{
		List<SysMenu> menus = new ArrayList<SysMenu>();
		try
		{
			String hql = "";
			if ("admin".equals(user.getFlaccount()))
			{
				hql = "SELECT DISTINCT menu FROM SysMenu menu " + "LEFT JOIN menu.sysMenupowers AS menuPowers "
				        + "WHERE menuPowers.sysRole IN "
				        + "(SELECT userRole.sysRole FROM SysUserrole userRole WHERE userRole.sysUser.flid = ?) "
				        + "AND menu.flisvisiable = 1 " + "AND menu.sysMenu IS NULL " + "ORDER BY menu.florder ASC";
				menus = menuDao.find(hql, new Object[] { user.getFlid() });
				boolean has = false;
				for (SysMenu sysMenu : menus)
				{
					if ("系统管理".equals(sysMenu.getFlname()))
					{
						has = true;
					}
				}
				if (!has)
				{
					hql = "FROM SysMenu WHERE flname='系统管理'";
					SysMenu sysmenu = menuDao.find(hql).get(0);
					menus.add(sysmenu);
				}
			} else
			{
				hql = "SELECT DISTINCT menu FROM SysMenu menu " + "LEFT JOIN menu.sysMenupowers AS menuPowers "
				        + "WHERE menuPowers.sysRole IN "
				        + "(SELECT userRole.sysRole FROM SysUserrole userRole WHERE userRole.sysUser.flid = ?) "
				        + "AND menu.flisvisiable = 1 " + "AND menu.sysMenu IS NULL " + "ORDER BY menu.florder ASC";
				menus = menuDao.find(hql, new Object[] { user.getFlid() });
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
		return menus;
	}
	
	/** 用户退出 */
	@Override
	@LogAnnotation(opDescribe = "退出系统", opType = "logout")
	public void logout(HttpSession session)
	{
		session.invalidate();
	}
	
	/** 获取所有菜单集合 */
	@Override
	public List<SysMenu> getMenus(SysUser user)
	{
		List<SysMenu> menus = new ArrayList<SysMenu>();
		try
		{
			String hql = "";
			if ("admin".equals(user.getFlaccount()))
			{
				hql = "SELECT DISTINCT menu FROM SysMenu menu ORDER BY menu.florder ASC";
				menus = menuDao.find(hql);
			} else
			{
				hql = "SELECT DISTINCT menu FROM SysMenu menu " + "LEFT JOIN menu.sysMenupowers AS menuPowers "
				        + "WHERE menuPowers.sysRole IN "
				        + "(SELECT userRole.sysRole FROM SysUserrole userRole WHERE userRole.sysUser.flid = ?) "
				        + "AND menu.flisvisiable = 1 " + "ORDER BY menu.florder ASC";
				menus = menuDao.find(hql, new Object[] { user.getFlid() });
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
		return menus;
	}
}
