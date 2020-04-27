/**
 * 修改人：long
 * 修改时间：2015-03-06
 * 修改内容：使业务层声明事务异常时可回滚，业务层事务方法异常统一上浮；业务方法异常，返回false或null统一在操作日志拦截器中设置。
 * 详见：com.snc.system.framework.interceptors.OperationLogInterceptor#before
 */
package com.zl.courseware.system.service.system.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zl.courseware.system.service.system.IRoleManageService;
import com.zl.courseware.system.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zl.courseware.system.dao.BaseDaoI;
import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.framework.constant.LogAnnotation;
import com.zl.courseware.system.model.system.SysFunction;
import com.zl.courseware.system.model.system.SysFunctionpower;
import com.zl.courseware.system.model.system.SysMenu;
import com.zl.courseware.system.model.system.SysMenupower;
import com.zl.courseware.system.model.system.SysRole;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.model.system.SysUserrole;


@Service
public class RoleManageServiceImpl implements IRoleManageService
{
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysUser>          userDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysUserrole>      userRoleDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysRole>          roleDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysMenupower>     menuPowerDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysFunctionpower> funPowerDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysMenu>          menuDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysUserrole>      roleuserDao;
	private final Logger               LOG = LoggerFactory.getLogger(RoleManageServiceImpl.class);
	
	
	/** 保存角色信息 */
	@Override
	@LogAnnotation(opDescribe = "新增角色操作", opType = "add")
	public boolean saveRole(SysRole role)
	{
		String hql = "from SysRole where flname=?";
		if (roleDao.find(hql, new Object[] { role.getFlname() }).isEmpty())
		{
			role.setFlid(UUIDUtil.getUUID());
			role.setFllevel(0);
			roleDao.save(role);
			return true;
		} else
		{
			return false;
		}
	}
	
	/** 保存树菜单。
	 * 
	 * @param flid
	 * @param flids
	 * @return */
	@Override
	public boolean saveRoleAuthority(String roleFild, String flids)
	{
		String hql = "delete SysMenupower where sysRole.flid=?";
		menuPowerDao.executeHql(hql, new Object[] { roleFild });
		hql = "delete SysFunctionpower where sysRole.flid=?";
		funPowerDao.executeHql(hql, new Object[] { roleFild });
		SysRole role = new SysRole();
		role.setFlid(roleFild);
		String[] array = flids.split(",");
		List<String> menulist = new ArrayList<String>();
		List<String> funlist = new ArrayList<String>();
		for (String flid : array)
		{
			String[] tempArray = flid.split("_");
			if (!"".equals(flid) && tempArray.length > 1)
			{
				if ("0".equals(tempArray[1]))
				{
					menulist.add(tempArray[0]);
				} else
				{
					funlist.add(tempArray[0]);
				}
			}
		}
		for (String string : menulist)
		{
			SysMenu menu = new SysMenu();
			menu.setFlid(string);
			SysMenupower menupower = new SysMenupower();
			menupower.setFlid(UUIDUtil.getUUID());
			menupower.setSysRole(role);
			menupower.setSysMenu(menu);
			menuPowerDao.save(menupower);
		}
		for (String string : funlist)
		{
			SysFunction function = new SysFunction();
			function.setFlid(string);
			SysFunctionpower functionpower = new SysFunctionpower();
			functionpower.setFlid(UUIDUtil.getUUID());
			functionpower.setSysRole(role);
			functionpower.setSysFunction(function);
			funPowerDao.save(functionpower);
		}
		return true;
	}
	
	/** 更新角色信息 */
	@Override
	@LogAnnotation(opDescribe = "更新角色信息操作", opType = "update")
	public boolean updateRole(SysRole role)
	{
		SysRole r = roleDao.get(SysRole.class, role.getFlid());
		r.setFlname(role.getFlname());
		r.setFloperationrole(role.getFloperationrole());
		roleDao.update(r);
		return true;
	}
	
	/** 根据id删除角色 */
	@Override
	@LogAnnotation(opDescribe = "角色删除操作", opType = "del")
	public boolean deleteRoleById(String flid)
	{
		String hql = "delete from SysUserrole a where a.sysRole.flid=?";
		userRoleDao.executeHql(hql, new Object[] { flid });
		hql = "delete from SysMenupower a where a.sysRole.flid=?";
		menuPowerDao.executeHql(hql, new Object[] { flid });
		hql = "delete from SysFunctionpower a where a.sysRole.flid=?";
		funPowerDao.executeHql(hql, new Object[] { flid });
		hql = "delete from SysRole a where a.flid=?";
		roleDao.executeHql(hql, new Object[] { flid });
		return true;
	}
	
	/** 获取角色列表 */
	@Override
	@LogAnnotation(opDescribe = "获取的角色列表信息", opType = "list")
	public List<Object> getData(SysUser users)
	{
		List<Object> value = new ArrayList<Object>();
		try
		{
			// 角色管理模块修改为：如果用户没有系统管理员的角色，进入角色的页面时不显示系统管理角色的数据。
			String hql = "from SysUser where flid=?";
			List<SysUser> list = userDao.find(hql, new Object[] { users.getFlid() });
			//如果是系统管理员
			boolean roleid = false;
			for (SysUser tbsysuser : list)
			{
				if (tbsysuser.getFlisadmin() != null && tbsysuser.getFlisadmin().intValue() == 1)
				{
					roleid = true;
					break;
				}
			}
			List<SysRole> rolelist = new ArrayList<SysRole>();
			rolelist = roleDao.find("from SysRole");
			for (SysRole tbsysrole : rolelist)
			{
				if (GlobalConstant.ADMIN.equals(tbsysrole.getFlcomment()) && !roleid)
				{
					// 如果用户有系统管理员的角色，进入角色的页面时显示系统管理角色的数据。
					continue;
				}
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("flname", tbsysrole.getFlname());
				row.put("fllevel", tbsysrole.getFllevel());
				row.put("flmark", tbsysrole.getFlmark() == null ? "" : tbsysrole.getFlmark());
				row.put("flid", tbsysrole.getFlid());
				row.put("floperationrole", tbsysrole.getFloperationrole());
				
				String operationroleStr="";
				int type=tbsysrole.getFloperationrole()==null?1:tbsysrole.getFloperationrole();
				if(type==1)
				{
					operationroleStr="学生";
				}
				if(type==2)
				{
					operationroleStr="老师";
				}
				if(type==3)
				{
					operationroleStr="实验室管理员";
				}
				if(type==4)
				{
					operationroleStr="系统管理员";
				}
				row.put("floperationroleStr", operationroleStr);
				value.add(row);
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
		return value;
	}
	
	/** 获取权限菜单树
	 * 
	 * @param flid
	 * @return */
	public String queryMenuXml(String flid)
	{
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		xml.append("<tree id=\"0\">\n");
		try
		{
			// 根据角色ID查找菜单权限列表
			String menupowerhql = "from SysMenupower where sysRole.flid=?";
			List<SysMenupower> menupowerlist = menuPowerDao.find(menupowerhql, new Object[] { flid });
			// 根据角色ID查找功能权限列表
			String functionpowerhql = "from SysFunctionpower where  sysRole.flid=? order by sysFunction.florder asc";
			List<SysFunctionpower> functionpowerlist = funPowerDao.find(functionpowerhql, new Object[] { flid });
			// 查找第一级菜单。
			List<SysMenu> list = menuDao
			        .find("from SysMenu where sysMenu is null and flisvisiable=1 order by florder asc");
			SysMenu menu = null;
			for (SysMenu firstMenu : list)
			{
				// 拼第一级菜单。
				menu = panduMenu(firstMenu, menupowerlist);
				xml.append(pingjieMenu(menu).toString());
				Set<SysMenu> secMenuSet = menu.getSysMenus();
				/* 递归拼接所有菜单和按钮 */
				pingjieMenuAndFun(secMenuSet, menupowerlist, functionpowerlist, xml);
				// 第一级菜单结束符号
				xml.append("</item>\n");
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
		xml.append("</tree>\n");
		return xml.toString();
	}
	
	/** 递归拼接所有子菜单和按钮
	 * 
	 * @param secMenuSet
	 * @param functionpowerlist
	 * @param menupowerlist
	 * @param xml */
	private void pingjieMenuAndFun(Set<SysMenu> secMenuSet, List<SysMenupower> menupowerlist, List<SysFunctionpower> functionpowerlist, StringBuilder xml)
	{
		List<SysMenu> secMenuList = orderByTbsysmenu(secMenuSet);
		SysMenu menu = null;
		SysFunction function = null;
		for (SysMenu tbsysmenu : secMenuList)
		{
			if (tbsysmenu.getFlisvisiable() == GlobalConstant.SHOW)
			{
				menu = panduMenu(tbsysmenu, menupowerlist);
				xml.append(pingjieMenu(menu).toString());
				// 第二级菜单的按钮，如果第二级没有按钮，就组装第三级菜单
				Set<SysFunction> funSet = tbsysmenu.getSysFunctions();
				List<SysFunction> funList = orderByTbsysfunction(funSet);
				if (!funList.isEmpty())
				{
					for (SysFunction fun : funList)
					{
						// 判断角色是否有该按钮。
						function = panduFun(fun, functionpowerlist);
						xml.append(pingjieTbsysfunction(function).toString());
					}
				} else if (!tbsysmenu.getSysMenus().isEmpty())
				{
					pingjieMenuAndFun(tbsysmenu.getSysMenus(), menupowerlist, functionpowerlist, xml);
				}
				xml.append("</item>\n");
			}
		}
	}
	
	/** 根据传进来的菜单判断是否选中。 */
	@Override
	public SysMenu pandu(SysMenu menu, List<SysMenupower> menupowerlist, List<SysFunctionpower> functionpowerlist, String level)
	{
		// 第一级菜单没有按钮，所以只比较菜单。
		if ("1".equals(level))
		{
			menu = panduMenu(menu, menupowerlist);
		}
		return menu;
	}
	
	/** 组装菜单字符串
	 * 
	 * @param menu
	 * @return */
	@Override
	public String pingjieMenu(SysMenu menu)
	{
		StringBuilder xml = new StringBuilder();
		xml.append("<item text=\"").append(menu.getFlname()).append("\" id=\"")
		        .append(menu.getFlid() + "_" + GlobalConstant.MENU_TPYE).append("\" open=\"").append(1).append("\"");
		if (menu.getChecked() != null)
		{
			xml.append(" checked=\"").append(1).append("\">");
		} else
		{
			xml.append(">");
		}
		return xml.toString();
	}
	
	/** 组装功能字符串
	 * 
	 * @param menu
	 * @return */
	@Override
	public String pingjieTbsysfunction(SysFunction menu)
	{
		StringBuilder xml = new StringBuilder();
		if (!menu.getFlurl().equals("0"))
		{
			xml.append("<item text=\"").append(menu.getFlfunctionname()).append("\" id=\"")
			        .append(menu.getFlid() + "_" + GlobalConstant.FUN_TPYE).append("\" open=\"").append(1).append("\"");
			if (menu.getChecked() != null)
			{
				xml.append(" checked=\"").append(1).append("\"></item>");
			} else
			{
				xml.append("></item>");
			}
		}
		return xml.toString();
	}
	
	/** 判断菜单是否选中。
	 * 
	 * @param menu
	 * @param menupowerlist
	 * @return */
	@Override
	public SysMenu panduMenu(SysMenu menu, List<SysMenupower> menupowerlist)
	{
		for (SysMenupower tbsysmenupower : menupowerlist)
		{
			if (menu.getFlid().equals(tbsysmenupower.getSysMenu().getFlid()))
			{
				menu.setChecked("1");
				break;
			} else
			{
				menu.setChecked(null);
			}
		}
		return menu;
	}
	
	/** 判断功能按钮是否选中
	 * 
	 * @param menu
	 * @param menupowerlist
	 * @return */
	@Override
	public SysFunction panduFun(SysFunction function, List<SysFunctionpower> functionpowerlist)
	{
		for (SysFunctionpower tbsysfunctionpower : functionpowerlist)
		{
			if (function.getFlid().equals(tbsysfunctionpower.getSysFunction().getFlid()))
			{
				function.setChecked("1");
				break;
			} else
			{
				function.setChecked(null);
			}
		}
		return function;
	}
	
	/** 把菜单按顺序保存。
	 * 
	 * @param menuSet
	 * @return */
	@Override
	public List<SysMenu> orderByTbsysmenu(Set<SysMenu> menuSet)
	{
		List<SysMenu> menuList = new ArrayList<SysMenu>();
		for (SysMenu menu : menuSet)
		{
			if (menu.getFlisvisiable() == GlobalConstant.SHOW)
			{
				menuList.add(menu);
			}
		}
		Collections.sort(menuList, new Comparator<SysMenu>()
		{
			@Override
			public int compare(SysMenu o1, SysMenu o2)
			{
				return o1.getFlorder().compareTo(o2.getFlorder());
			}
		});
		return menuList;
	}
	
	/** 按钮按顺序保存。
	 * 
	 * @param menuSet
	 * @return */
	@Override
	public List<SysFunction> orderByTbsysfunction(Set<SysFunction> funSet)
	{
		List<SysFunction> funList = new ArrayList<SysFunction>();
		for (SysFunction fun : funSet)
		{
			funList.add(fun);
		}
		Collections.sort(funList, new Comparator<SysFunction>()
		{
			@Override
			public int compare(SysFunction o1, SysFunction o2)
			{
				return o1.getFlorder().compareTo(o2.getFlorder());
			}
		});
		return funList;
	}
	
	@Override
	public boolean checkAccount(String name, String id)
	{
		try
		{
			String hql = "from SysRole where flname=?";
			Object[] obj;
			if (id != null)
			{
				hql = hql + " and flid!=?";
				obj = new Object[] { name, id };
			} else
			{
				obj = new Object[] { name };
			}
			List<SysRole> userlist = roleDao.find(hql, obj);
			if (!userlist.isEmpty())
			{
				return false;
			} else
			{
				return true;
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
			return false;
		}
	}
	
	@Override
	public boolean checkOpertionTypeOnly(int type, String id)
	{
		try
		{
			String hql = "from SysRole where floperationrole=?";
			Object[] obj;
			if (id != null)
			{
				hql = hql + " and flid!=?";
				obj = new Object[] { type, id };
			} else
			{
				obj = new Object[] { type };
			}
			List<SysRole> userlist = roleDao.find(hql, obj);
			if (!userlist.isEmpty())
			{
				return false;
			} else
			{
				return true;
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
			return false;
		}
	}
	
	@Override
	public List<SysUserrole> getSysUserroleByID(String roleid, String userid)
	{
		String hql = "from SysUserrole s  left join fetch s.sysRole left join fetch s.sysUser where s.sysRole.flid=? and s.sysUser.flid=?";
		return roleuserDao.find(hql, new Object[] { roleid, userid });
	}
}
