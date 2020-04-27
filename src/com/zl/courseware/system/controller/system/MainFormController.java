package com.zl.courseware.system.controller.system;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zl.courseware.system.controller.base.BaseController;
import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.model.data.Json;
import com.zl.courseware.system.model.system.SysMenu;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.service.system.IMainFormService;
import com.zl.courseware.system.utils.RandomValidateCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/main")
public class MainFormController extends BaseController
{
	@Autowired
	private IMainFormService mainformService;
	private static final Logger log = LoggerFactory.getLogger(MainFormController.class);
	
	
	/** 进入系统默认选择第一个菜单的第一个子菜单
	 * 
	 * @param parentid
	 * @return */
	@ResponseBody
	@RequestMapping("/parent")
	public Json parent(String parentid)
	{
		Json j = new Json();
		Json firstChild = mainformService.getFirstChildMenu(parentid);
		if (firstChild.isSuccess())
		{
			SysMenu m = (SysMenu) firstChild.getObj();
			int size = (int) firstChild.getCode();
			if (size > 0)
			{
				j.setMsg(m.getFlid() + GlobalConstant.YES_CHILD_MENU);
			} else
			{
				j.setMsg(m.getFlid() + GlobalConstant.NO_CHILD_MENU);
			}
		}
		return j;
	}
	
	/** 进入系统默认选择第一个菜单的第一个子菜单
	 * 
	 * @param parentid
	 * @return */
	@ResponseBody
	@RequestMapping("/child")
	public Json child(String childid)
	{
		Json j = new Json();
		Json firstChild = mainformService.getMenu(childid);
		if (firstChild.isSuccess())
		{
			SysMenu m = (SysMenu) firstChild.getObj();
			int size = (int) firstChild.getCode();
			if (size > 0)
			{
				j.setMsg(m.getFlid() + GlobalConstant.YES_CHILD_MENU);
			} else
			{
				j.setMsg(m.getFlid() + GlobalConstant.NO_CHILD_MENU);
			}
		}
		return j;
	}
	
	/** 获取顶级菜单的所有子菜单，在左边的菜单的div中显示 */
	@ResponseBody
	@RequestMapping("/menu")
	public Json menu(String parent, HttpSession sessionMap)
	{
		Json j = new Json();
		if (parent != null)
		{
			SysUser users = (SysUser) sessionMap.getAttribute("loginUser");
			Set<String> menusId = (Set<String>) sessionMap.getAttribute("menusId");
			List<Object> objects = mainformService.getData(parent, users, menusId);
			j.setObj(objects);
		}
		return j;
	}
	
	/** 选中一个子菜单，将该id存入session
	 * 
	 * @param menuid
	 * @param sessionMap
	 * @return */
	@ResponseBody
	@RequestMapping("/getMenuId")
	public Json getMenuId(String menuid, HttpSession sessionMap)
	{
		Json j = new Json();
		sessionMap.setAttribute("menuid", menuid);
		j.setSuccess(true);
		return j;
	}
	
	/** 获取验证码
	 * 
	 * @return */
	@ResponseBody
	@RequestMapping("/getCode")
	public void getCode(HttpServletRequest request, HttpServletResponse response)
	{
		response.reset();
		response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
		try
		{
			randomValidateCode.getRandcode(request, response);//输出图片方法
		} catch (Exception e)
		{
			log.error("错误信息", e);
		}
	}
}
