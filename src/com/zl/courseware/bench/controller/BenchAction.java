package com.zl.courseware.bench.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zl.courseware.bench.model.SysShortcut;
import com.zl.courseware.bench.service.IBenchService;
import com.zl.courseware.system.controller.base.BaseController;
import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.model.system.SysMenu;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.utils.StringUtil;
import com.zl.courseware.system.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.snc.modelmanage.service.changecontrol.IModelChangeQueryService;


@Controller
@RequestMapping("/bench")
public class BenchAction extends BaseController {
 
	@Autowired
	private IBenchService benchServiceImpl;
	
//	@Autowired
//	private IModelChangeQueryService modelChangeQueryServiceImpl;
	

	private static final Logger log = LoggerFactory.getLogger(BenchAction.class);
	
    /**
     * 查询用户当前待操作列表
     */
	@ResponseBody
	@RequestMapping("/getChange")
    public Map<String, Object> getChange(HttpSession sessionMap) {
		Map<String, Object> remap = new HashMap<String, Object>();
    	//SysUser user = (SysUser) sessionMap.getAttribute(GlobalConstant.LOGIN_USER);
        //String aduitman = user.getFlname();
        return remap;
    }
    
    /**
     * 查询用户可操作的模块
     */
	@ResponseBody
	@RequestMapping("/getMenu")
    public Map<String, Object> getMenu(HttpSession sessionMap) {
		SysUser user = (SysUser) sessionMap.getAttribute(GlobalConstant.LOGIN_USER);
        return benchServiceImpl.getMenu(user.getFlid());
    }
    
    /**
     * 查询用户的创建的快捷方式
     */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping("/getShortcut")
    public Map<String, Object> getShortcut(HttpSession sessionMap) {
		SysUser user = (SysUser) sessionMap.getAttribute(GlobalConstant.LOGIN_USER);
		List<Map<String,Object>> secondMenus =(List<Map<String,Object>>) sessionMap.getAttribute("menus");
	    Set<String> menuids=new HashSet<String>();
		for (Map<String, Object> map : secondMenus) {
			menuids.add((String)map.get("id"));
		}
		return benchServiceImpl.getShortcut(user.getFlid(),menuids);
    }
    
    /**
     * 添加快捷方式
     */
	@ResponseBody
	@RequestMapping("/addShortcut")
    public boolean addShortcut(HttpSession sessionMap,HttpServletRequest request) {
		SysShortcut tbbmshortcut = new SysShortcut();
        try {
        	SysUser user = (SysUser) sessionMap.getAttribute(GlobalConstant.LOGIN_USER);
            String flname = java.net.URLDecoder.decode((String) request.getParameter("flname"), "UTF-8");
            String flurl = request.getParameter("flurl");
            String flicon = request.getParameter("flicon");
            String flMenuId = request.getParameter("flMenuId");
            String id = user.getFlid();
            tbbmshortcut.setFlId(UUIDUtil.getUUID());
            tbbmshortcut.setFlIcon(flicon);
            tbbmshortcut.setFlName(flname);
            tbbmshortcut.setFlUrl(flurl);
            tbbmshortcut.setFlUserId(id);
            tbbmshortcut.setSysMenu(new SysMenu(flMenuId));
            tbbmshortcut.setFlCreateTime(new Date());
            benchServiceImpl.saveObject(tbbmshortcut);
            return true;
        } catch (Exception e) {
        	log.error("错误信息", e);
        	return false;
        }
    }
    
    /**
     * 批量添加快捷方式
     */
	@ResponseBody
	@RequestMapping("/addPatchShortcut")
    public boolean addPatchShortcut(HttpSession sessionMap,HttpServletRequest request) {
		String[] flname = request.getParameter("flname").split(",");
		String[] flicon = request.getParameter("flicon").split(",");
		String[] flurl = request.getParameter("flurl").split(",");
		String[] flMenuId = request.getParameter("flMenuId").split(",");
		try {
			for (int i = 0; i < flname.length; i++) {
				SysShortcut tbbmshortcut = new SysShortcut();
				SysUser user = (SysUser) sessionMap.getAttribute(GlobalConstant.LOGIN_USER);
				String id = user.getFlid();
				tbbmshortcut.setFlUserId(id);
				tbbmshortcut.setFlId(UUIDUtil.getUUID());
				tbbmshortcut.setFlCreateTime(new Date());
				tbbmshortcut.setFlIcon(flicon[i]);
				tbbmshortcut.setFlName(StringUtil.decodeUTF8(flname[i]));
				tbbmshortcut.setFlUrl(flurl[i]);
				tbbmshortcut.setSysMenu(new SysMenu(flMenuId[i]));
				benchServiceImpl.saveObject(tbbmshortcut);
			}
			return true;
		} catch (Exception e) {
			log.error("错误信息", e);
			return false;
		}
    }
    
    /**
     * 删除快捷方式
     */
	@ResponseBody
	@RequestMapping("/remove")
	public boolean remove(HttpServletRequest request) {
        String flid = request.getParameter("flid");
        try {
        	benchServiceImpl.remove(flid);
        	return true;
        } catch (Exception e) {
        	log.error("错误信息", e);
            return false;
        }
    }
    
    /**
     * 获取快捷方式关联菜单的最顶级父级菜单ID
     *
     * @return
     */
	@ResponseBody
	@RequestMapping("/getActiveMenu")
    public String getActiveMenu(HttpServletRequest request) {
        String flMenuId = request.getParameter("flMenuId");
        SysMenu ancestor = benchServiceImpl.getAncestorMenu(flMenuId);
        String activeMenu = ancestor.getFlid();
        activeMenu+=ancestor.getFlcode()!=null&&!"".equals(ancestor.getFlcode())?"_"+ancestor.getFlcode():"";
        return activeMenu;
    }
    
    /**
     * 跳转到待办事项
     */
	@RequestMapping("/showBacklog")
	public String showBacklog() {
		return "page/bench/benchbacklog.jsp";
	}
	
	/**
	 * 获得待办事项提醒次数的全局变量
	 */
	@ResponseBody
	@RequestMapping("/queryBacklogTimes")
	public String queryBacklogTimes(HttpSession sessionMap) {
		String back = (String)sessionMap.getAttribute("backlogTimes");
		return back;
	}
	
	/**
	 * 设置待办事项提醒次数的全局变量
	 */
	@ResponseBody
	@RequestMapping("/resetBacklogTimes")
	public void resetBacklogTimes(HttpSession sessionMap,HttpServletRequest request) {
		String times =request.getParameter("backlogTimes");		
		sessionMap.setAttribute("backlogTimes", times);	
	}
}