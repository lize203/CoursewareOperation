package com.zl.courseware.system.service.system.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zl.courseware.system.model.data.Json;
import com.zl.courseware.system.model.system.SysMenu;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.service.system.IMainFormService;
import com.zl.courseware.system.utils.Global;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zl.courseware.system.dao.BaseDaoI;
import com.zl.courseware.system.framework.constant.GlobalConstant;


@Service
public class MainFormServiceImpl implements IMainFormService
{
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysMenu> menuDao;
	private final Logger      LOG = LoggerFactory.getLogger(MainFormServiceImpl.class);
	
	
	/** 获取一级菜单下第一个子菜单ID
	 * 
	 * @param parentId
	 * @return */
	@Override
	public Json getFirstChildMenu(String parentId)
	{
		Json obj = new Json();
		try
		{
			String hql = "FROM SysMenu m WHERE m.sysMenu.flid = ? AND m.flisvisiable = " + GlobalConstant.SHOW
			        + " ORDER BY m.florder ASC";
			List<SysMenu> menu = menuDao.find(hql, new Object[] { parentId });
			if (!menu.isEmpty())
			{
				List<SysMenu> menu1 = menuDao.find(hql, new Object[] { menu.get(0).getFlid() });
				if (!menu1.isEmpty())
				{
					obj.setObj(menu1.get(0));
					obj.setCode(menu1.get(0).getSysMenus().size());
					obj.setSuccess(true);
				} else
				{
					obj.setObj(menu.get(0));
					obj.setCode(menu.get(0).getSysMenus().size());
					obj.setSuccess(true);
				}
			} else
			{
				obj.setSuccess(false);
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
			obj.setSuccess(false);
		}
		return obj;
	}
	
	/** 加载一个top菜单下面的所有子菜单 */
	@Override
	public List<Object> getData(String parent, SysUser user, Set<String> menusId)
	{
		List<Object> objects = new ArrayList<Object>();
		try
		{
			List<SysMenu> menulist = findMenupower(parent, user);
			for (SysMenu menu : menulist)
			{
				if (menu.getSysMenu() != null)
				{
					Map<String, Object> treeNodes = new HashMap<String, Object>();
					treeNodes.put("text", menu.getFlname());
					treeNodes.put("iconCls", menu.getFlicon());
					treeNodes.put("order", menu.getFlorder());
					Map<String, Object> attributeHashMap = new HashMap<String, Object>();
					attributeHashMap.put("url", menu.getFlurl() + "?nowtime=" + new Date().getTime());
					treeNodes.put("attributes", attributeHashMap);
					objects.add(treeNodes);
					// 排序
					orderList(objects, menu);
					// 递归添加所有子菜单
					buildMenus(menu, treeNodes, menusId);
				}
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
		return objects;
	}
	
	/** 排序
	 * 
	 * @param objects
	 * @param menu */
	private void orderList(List<Object> value, SysMenu menu)
	{
		int size = value.size();
		String florderString1 = menu.getFlorder().toString();
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
	
	/** 递归添加所有子菜单
	 * 
	 * @param menu
	 * @param tn
	 * @param menusId */
	private void buildMenus(SysMenu menu, Map<String, Object> tn, Set<String> menusId)
	{
		if (!menu.getSysMenus().isEmpty())
		{
			tn.put("id", menu.getFlid() + GlobalConstant.YES_CHILD_MENU);
			List<Object> treeNodeChildrensThreeMenus = new ArrayList<Object>();
			for (SysMenu m : menu.getSysMenus())
			{
				if (menusId.contains(m.getFlid()))
				{
					if (m.getFlisvisiable() == Global.HIDE)
					{
						continue;
					}
					Map<String, Object> treeNodes = new HashMap<String, Object>();
					treeNodes.put("text", m.getFlname());
					treeNodes.put("iconCls", m.getFlicon());
					treeNodes.put("order", m.getFlorder());
					Map<String, Object> attributeHashMap = new HashMap<String, Object>();
					attributeHashMap.put("url", m.getFlurl() + "?nowtime=" + new Date().getTime());
					treeNodes.put("attributes", attributeHashMap);
					buildMenus(m, treeNodes, menusId);
					treeNodeChildrensThreeMenus.add(treeNodes);
					orderList(treeNodeChildrensThreeMenus, m);
				}
			}
			tn.put("children", treeNodeChildrensThreeMenus);
		} else
		{
			tn.put("id", menu.getFlid() + GlobalConstant.NO_CHILD_MENU);
		}
	}
	
	public List<SysMenu> findMenupower(String parentId, SysUser user)
	{
		List<SysMenu> menus = new ArrayList<SysMenu>();
		try
		{
			String hql = "";
			if ("admin".equals(user.getFlaccount()))
			{
				hql = "SELECT DISTINCT m FROM SysMenu m " + " INNER JOIN m.sysMenupowers AS mp "
				        + "WHERE  m.flisvisiable = " + GlobalConstant.SHOW + " AND m.sysMenu.flid = ?"
				        + " ORDER BY m.florder ASC";
				menus = menuDao.find(hql, new Object[] { parentId });
			} else
			{
				hql = "SELECT DISTINCT m FROM SysMenu m " + " INNER JOIN m.sysMenupowers AS mp"
				        + " INNER JOIN mp.sysRole.sysUserroles AS ur" + " WHERE ur.sysUser.flid = ?"
				        + " AND m.flisvisiable = " + GlobalConstant.SHOW + " AND m.sysMenu.flid = ?"
				        + " ORDER BY m.florder ASC";
				menus = menuDao.find(hql, new Object[] { user.getFlid(), parentId });
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
		return menus;
	}
	
	/** 获取该子菜单 */
	@Override
	public Json getMenu(String childid)
	{
		Json obj = new Json();
		try
		{
			String hql = "FROM SysMenu m WHERE m.flid = ? AND m.flisvisiable = " + GlobalConstant.SHOW;
			List<SysMenu> menu = menuDao.find(hql, new Object[] { childid });
			if (!menu.isEmpty())
			{
				SysMenu menu1 = menu.get(0);
				obj.setObj(menu1);
				obj.setCode(menu1.getSysMenus().size());
				obj.setSuccess(true);
			} else
			{
				obj.setSuccess(false);
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
			obj.setSuccess(false);
		}
		return obj;
	}
}
