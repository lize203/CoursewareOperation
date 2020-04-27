package com.zl.courseware.system.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zl.courseware.system.controller.base.BaseController;
import com.zl.courseware.system.model.data.Json;
import com.zl.courseware.system.model.data.SessionInfo;
import com.zl.courseware.system.model.system.SysFunction;
import com.zl.courseware.system.model.system.SysMenu;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.service.system.ILoginService;
import com.zl.courseware.system.utils.MD5Util;
import com.zl.courseware.system.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController
{
	@Autowired
	private ILoginService loginService;
	
	/** 检测是否已经登陆
	 * 
	 * @param request
	 * @return */
	@RequestMapping("/index")
	public String index(HttpServletRequest request)
	{
		SysUser user = (SysUser) request.getSession().getAttribute("loginUser");
		if ((user != null) && (user.getFlid() != null))
		{
			return "/page/main/MainForm";
		}
		return "/page/login";
	}
	
	/** 用户登录
	 * 
	 * @param u
	 * @param sessionMap
	 * @return */
	@ResponseBody
	@RequestMapping("/login")
	public Json login(SysUser u, HttpSession sessionMap, String yzm)
	{
		Json j = new Json();
		/** 屏蔽验证码验证功能 if(yzm==null ||
		 * !sessionMap.getAttribute(RandomValidateCodeUtil
		 * .RANDOMCODEKEY).toString().toLowerCase().equals(yzm.toLowerCase())){
		 * j.setMsg("验证码填写有误"); return j; } */
		SysUser user = loginService.LoginUser(u);
		if (user == null)
		{
			j.setMsg("登录失败，用户名或密码错误");
		} else
		{
			List<SysFunction> functionlist = new ArrayList<SysFunction>();
			List<Map<String, Object>> secondMenus = new ArrayList<Map<String, Object>>();
			List<SysMenu> allMenus = loginService.getMenus(user);
			Set<String> allMenusIds = new HashSet<String>();
			for (SysMenu tbsysmenu : allMenus)
			{
				allMenusIds.add(tbsysmenu.getFlid());
			}
			loginService.loginMethod(user, functionlist, secondMenus);
			Integer level = loginService.getRoleLevel(user.getFlid());
			// 将用户有权访问的一级菜单保存到session中
			List<SysMenu> topMenus = loginService.getTopMenus(user);
			List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
			String activeMenu = null;
			if (!topMenus.isEmpty())
			{
				activeMenu = topMenus.get(0).getFlid() + "_" + topMenus.get(0).getFlcode();
				for (SysMenu menu : topMenus)
				{
					Map<String, Object> entry = new HashMap<String, Object>();
					String url = "/admin/getToBackPage?activeMenu=" + menu.getFlid() + "_" + menu.getFlcode();
					entry.put("id", menu.getFlid() + "_" + menu.getFlcode());
					entry.put("url", url);
					entry.put("name", menu.getFlname());
					entry.put("icon", menu.getFlicon() + ".png");
					menuList.add(entry);
				}
			}
			sessionMap.setAttribute("menusId", allMenusIds);//所有菜单的id-map
			sessionMap.setAttribute("topMenus", menuList);//所有top菜单对象-map
			sessionMap.setAttribute("backlogTimes", "0");
			sessionMap.setAttribute("functions", functionlist);//所有按钮对像
			sessionMap.setAttribute("menus", secondMenus);//所有包含有子菜单的菜单，对用左侧的div来加载它的子菜单树
			sessionMap.setAttribute("loginUser", user);
			sessionMap.setAttribute("level", level);
			sessionMap.setAttribute("userID", user.getFlid());
			sessionMap.setAttribute("activeMenu", activeMenu);//选中的top菜单
			j.setSuccess(true);
			j.setMsg("登陆成功！");
		}
		return j;
	}
	
	
	/** 提供sdk外部访问，用户登录
	 * 
	 * @param u
	 * @return */
	@ResponseBody
	@RequestMapping("/loginSDK")
	public Json loginSDK(SysUser u)
	{
		Json j = new Json();
		SysUser user = loginService.LoginUser(u);
		if (user == null)
		{
			j.setMsg("登录失败，用户名或密码错误");
		} else
		{
			String uuid= UUIDUtil.getUUID();
			String sessionId= MD5Util.md5(uuid);
			
			j.setSuccess(true);
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("sessionId", sessionId);
			
			SysUser userinfo=new SysUser();
			userinfo.setFlaccount(user.getFlaccount());
			userinfo.setFlusertype(user.getFlusertype());
			userinfo.setFlpersonid(user.getFlpersonid());
			userinfo.setFlname(user.getFlname());
			userinfo.setFlid(user.getFlid());
			userinfo.setFlname(user.getFlname());
			map.put("userinfo", userinfo);
			
			SessionInfo.sessionUserInfo.put(sessionId,userinfo);
			j.setObj(map);
			j.setMsg("登陆成功！");
		}
		return j;
	}
	
	/** 选中一个top将该id存入session并跳转页面
	 *
	 * @param sessionMap
	 * @return */
	@RequestMapping("/getToBackPage")
	public String getToBackPage(HttpServletRequest request, HttpSession sessionMap)
	{
		String activeMenu = request.getParameter("activeMenu");
		if (activeMenu != null && !"".equals(activeMenu))
		{
			sessionMap.setAttribute("activeMenu", activeMenu);
		}
		String selectedSubmenu = request.getParameter("selectedSubmenu");
		if (activeMenu != null && !"".equals(selectedSubmenu))
		{
			sessionMap.setAttribute("selectedSubmenu", selectedSubmenu);
		}
		return "/page/main/MainForm";
	}
	
	/** 重新加载菜单
	 * 
	 * @param request
	 * @param sessionMap
	 * @return */
	@RequestMapping("/reload")
	public String reload(HttpServletRequest request, HttpSession sessionMap)
	{
		SysUser user = (SysUser) sessionMap.getAttribute("loginUser");
		if (user == null)
		{
			return "/page/login";
		}
		List<SysFunction> functionlist = new ArrayList<SysFunction>();
		List<Map<String, Object>> secondMenus = new ArrayList<Map<String, Object>>();
		List<SysMenu> allMenus = loginService.getMenus(user);
		Set<String> allMenusIds = new HashSet<String>();
		for (SysMenu tbsysmenu : allMenus)
		{
			allMenusIds.add(tbsysmenu.getFlid());
		}
		String activeMenu = (String) sessionMap.getAttribute("activeMenu");
		loginService.loginMethod(user, functionlist, secondMenus);
		//Integer level = loginService.getRoleLevel(user.getFlid());
		// 将用户有权访问的一级菜单保存到session中
		List<SysMenu> topMenus = loginService.getTopMenus(user);
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		//
		if (!topMenus.isEmpty())
		{
			if (!allMenusIds.contains(activeMenu.split("_")[0]))
			{
				activeMenu = topMenus.get(0).getFlid() + "_" + topMenus.get(0).getFlcode();
				sessionMap.setAttribute("activeMenu", activeMenu);
			}
			//
			for (SysMenu menu : topMenus)
			{
				Map<String, Object> entry = new HashMap<String, Object>();
				String url = "/admin/getToBackPage?activeMenu=" + menu.getFlid() + "_" + menu.getFlcode();
				entry.put("id", menu.getFlid() + "_" + menu.getFlcode());
				entry.put("url", url);
				entry.put("name", menu.getFlname());
				entry.put("icon", menu.getFlicon() + ".png");
				menuList.add(entry);
			}
		}
		sessionMap.setAttribute("menusId", allMenusIds);//所有菜单的id-map
		sessionMap.setAttribute("topMenus", menuList);//所有top菜单对象-map
		//sessionMap.setAttribute("backlogTimes", "0");
		sessionMap.setAttribute("functions", functionlist);//所有按钮对像
		sessionMap.setAttribute("menus", secondMenus);//所有包含有子菜单的菜单，对用左侧的div来加载它的子菜单树
		//sessionMap.setAttribute("loginUser", user);
		//sessionMap.setAttribute("level", level);
		//sessionMap.setAttribute("userID", user.getFlid());
		////选中的top菜单
		return "/page/main/MainForm";
	}
	
	/** 安全退出系统
	 * 
	 * @param session
	 * @return */
	@ResponseBody
	@RequestMapping("/logout")
	public Json logout(HttpSession session)
	{
		Json j = new Json();
		if (session != null)
		{
			loginService.logout(session);
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}
	
	/** 安全退出系统，sdk接口方法
	 * 
	 * @param sessionId
	 * @return */
	@ResponseBody
	@RequestMapping("/logoutSDK")
	public Json logoutSDK(String sessionId)
	{
		Json j = new Json();
		if(sessionId==null||(SessionInfo.sessionUserInfo.get(sessionId)==null)){
			j.setMsg("用户还未登录，或登录sessionId已失效，请重新登录！");
			j.setSuccess(false);
			return j;
		}
		
		SessionInfo.sessionUserInfo.remove(sessionId);
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}
	
}
