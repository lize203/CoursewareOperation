package com.zl.courseware.system.controller.system;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.zl.courseware.system.controller.base.BaseController;
import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.model.data.Grid;
import com.zl.courseware.system.model.data.Json;
import com.zl.courseware.system.model.system.SysRole;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.service.system.IRoleManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/** 角色管理 */
@Controller
@RequestMapping("/role")
public class RoleManageController extends BaseController
{
	@Autowired
	private IRoleManageService roleService;
	private static final Logger LOG = LoggerFactory.getLogger(RoleManageController.class);
	
	
	/** 获取所有角色信息 */
	@ResponseBody
	@RequestMapping("/getData")
	public Grid getData(HttpSession sessionMap)
	{
		Grid g = new Grid();
		SysUser user = (SysUser) sessionMap.getAttribute("loginUser");
		List<Object> list = roleService.getData(user);
		g.setRows(list);
		g.setTotal(list.size());
		return g;
	}
	
	/** 保存角色信息 */
	@ResponseBody
	@RequestMapping("/saveData")
	public Json saveData(SysRole sysrole)
	{
		Json j = new Json();
		
		if (checkNameOnly(sysrole.getFlname(), null))
		{
			int type=sysrole.getFloperationrole();
			
			if(type==1||type==2||type==3){
				if(!checkOpertionTypeOnly(sysrole.getFloperationrole(),null)){
					j.setCode(4);
					return j;
				}
			}
			
			if (roleService.saveRole(sysrole))
			{
				j.setCode(GlobalConstant.SUCCESS);
			} else
			{
				j.setCode(GlobalConstant.FAIL);
			}
		} else
		{
			j.setCode(GlobalConstant.EXIST);
		}
		return j;
	}
	
	/** 更新保存角色 */
	@ResponseBody
	@RequestMapping("/updateData")
	public Json updateData(SysRole sysrole)
	{
		Json j = new Json();
		if (checkNameOnly(sysrole.getFlname(), sysrole.getFlid()))
		{
			int type=sysrole.getFloperationrole();
			if(type==1||type==2||type==3){
				if(!checkOpertionTypeOnly(sysrole.getFloperationrole(),sysrole.getFlid())){
					j.setCode(4);
					return j;
				}
			}
			
			if (roleService.updateRole(sysrole))
			{
				j.setCode(GlobalConstant.SUCCESS);
			} else
			{
				j.setCode(GlobalConstant.FAIL);
			}
		} else
		{
			j.setCode(GlobalConstant.EXIST);
		}
		return j;
	}
	
	/** 删除角色 */
	@ResponseBody
	@RequestMapping("/deleteData")
	public Json deleteData(String id)
	{
		Json j = new Json();
		if (roleService.deleteRoleById(id))
		{
			j.setCode(GlobalConstant.SUCCESS);
		} else
		{
			j.setCode(GlobalConstant.FAIL);
		}
		return j;
	}
	
	/** 分配权限
	 * 
	 * @param roleid
	 * @return */
	@ResponseBody
	@RequestMapping("/queryRolesMenu")
	public Json queryRolesMenu(String roleid)
	{
		Json j = new Json();
		String xml = roleService.queryMenuXml(roleid);
		j.setMsg(xml);
		return j;
	}
	
	/** 修改后的保存角色权限方法。 */
	@ResponseBody
	@RequestMapping("/saveRoleAuthority")
	public Json saveRoleAuthority(HttpSession sessionMap, String roleid, String check)
	{
		Json j = new Json();
		try
		{
			String userId = (String) sessionMap.getAttribute("userID");
			if (roleService.saveRoleAuthority(roleid, check))
			{
				if (roleService.getSysUserroleByID(roleid, userId).size() > 0)
				{
					j.setCode(GlobalConstant.RELOAD);
				} else
				{
					j.setCode(GlobalConstant.SUCCESS);
				}
			} else
			{
				j.setCode(GlobalConstant.FAIL);
			}
		} catch (Exception e)
		{
			LOG.error("错误信息", e);
			j.setCode(GlobalConstant.FAIL);
		}
		return j;
	}
	
	/** 检查用角色名是否唯一 */
	private boolean checkNameOnly(String name, String id)
	{
		if (roleService.checkAccount(name, id))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	/** 检查业务角色是否唯一 */
	private boolean checkOpertionTypeOnly(int type, String id)
	{
		if (roleService.checkOpertionTypeOnly(type, id))
		{
			return true;
		} else
		{
			return false;
		}
	}
}
