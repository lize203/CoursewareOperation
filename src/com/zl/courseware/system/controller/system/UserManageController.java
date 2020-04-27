package com.zl.courseware.system.controller.system;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zl.courseware.system.controller.base.BaseController;
import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.model.data.Grid;
import com.zl.courseware.system.model.data.Json;
import com.zl.courseware.system.model.data.SessionInfo;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.service.system.IUserManageService;
import com.zl.courseware.system.utils.ConfigUtil;
import com.zl.courseware.system.utils.ExcelUtility;
import com.zl.courseware.system.utils.FileUtility;
import com.zl.courseware.system.utils.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/user")
public class UserManageController extends BaseController
{
	private final Logger       LOG = LoggerFactory.getLogger(UserManageController.class);
	@Autowired
	private IUserManageService userManageServiceImpl;
	
	
	/** 获取所有角色列表
	 * 
	 * @param id
	 * @return */
	@ResponseBody
	@RequestMapping("/getRoleData")
	public Grid getRoleData(String id)
	{
		Grid g = new Grid();
		List<Object> list = userManageServiceImpl.getRoleData(id);
		List<Object> adList = new ArrayList<Object>();
		for (Object object : list) {
			if(object.toString().contains("系统管理员"))
			{
				adList.add(object);
			}
		}
		g.setRows(adList);
		g.setTotal(adList.size());
		return g;
	}
	
	/** 条件查找用户
	 * 
	 * @param query
	 * @param number
	 * @return
	 * @throws UnsupportedEncodingException */
	@ResponseBody
	@RequestMapping("/query")
	public Grid query(String query, String number) throws UnsupportedEncodingException
	{
		Grid g = new Grid();
		List<Object> list = null;
		number = URLDecoder.decode(number, "UTF-8");
		number = number.trim();
		list = userManageServiceImpl.query(number, query);
		g.setRows(list);
		g.setTotal(list.size());
		return g;
	}
	
	/** 获取用户列表
	 * 
	 * @return */
	@ResponseBody
	@RequestMapping("/getData")
	public Grid getData()
	{
		Grid g = new Grid();
		List<Object> list = userManageServiceImpl.getData();
		g.setRows(list);
		g.setTotal(list.size());
		return g;
	}
	
	/**
	 * 查询用户列表，sdk接口方法
	 * @param sessionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserDataSDK")
	public Json getUserDataSDK(String sessionId)
	{
		Json j=new Json();
		
		if(sessionId==null||(SessionInfo.sessionUserInfo.get(sessionId)==null)){
			j.setMsg("未授权登录的请求，请求失败！");
			j.setSuccess(false);
			return j;
		}
		
		Grid g = new Grid();
		List<Object> list = userManageServiceImpl.getData();
		g.setRows(list);
		g.setTotal(list.size());
		
		
		j.setMsg("获取用户列表成功");
		j.setSuccess(true);
		j.setObj(list);
		
		return j;
	}
	
	/** 添加用户
	 * 
	 * @param tbsysuser
	 * @param roles
	 * @return */
	@ResponseBody
	@RequestMapping("/saveData")
	public Json saveData(SysUser tbsysuser, String roleids)
	{
		Json j = new Json();
		if (checkNameOnly(tbsysuser.getFlaccount(), null))
		{
			boolean rs = userManageServiceImpl.addUser(tbsysuser, roleids);
			if (rs)
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
	
	/** 保存更新用户 */
	@ResponseBody
	@RequestMapping("/updateData")
	public Json updateData(SysUser tbsysuser, String roleids)
	{
		Json j = new Json();
		if (checkNameOnly(tbsysuser.getFlaccount(), tbsysuser.getFlid()))
		{
			if (userManageServiceImpl.updateUser(tbsysuser, roleids))
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
	
	/** 批量删除用户。 */
	@ResponseBody
	@RequestMapping("/batchDel")
	public Json batchDel(String array)
	{
		Json j = new Json();
		if (userManageServiceImpl.batchDel(array))
		{
			j.setCode(GlobalConstant.SUCCESS);
		} else
		{
			j.setCode(GlobalConstant.FAIL);
		}
		return j;
	}
	
	/** 删除用户 */
	@ResponseBody
	@RequestMapping("/deleteData")
	public Json deleteData(String id)
	{
		Json j = new Json();
		if (userManageServiceImpl.delUser(id))
		{
			j.setCode(GlobalConstant.SUCCESS);
		} else
		{
			j.setCode(GlobalConstant.FAIL);
		}
		return j;
	}
	
	/** 匹配旧密码是否正确 */
	@ResponseBody
	@RequestMapping("/queryOldPassWord")
	public Json queryOldPassWord(String oldpassword, HttpSession sessionMap)
	{
		Json j = new Json();
		SysUser user = (SysUser) sessionMap.getAttribute("loginUser");
		j.setCode(userManageServiceImpl.queryOldPassWord(user, oldpassword));
		return j;
	}
	
	/** 保存新密码 */
	@ResponseBody
	@RequestMapping("/editPassWord")
	public Json editPassWord(String newpassword, HttpSession sessionMap)
	{
		Json j = new Json();
		SysUser user = (SysUser) sessionMap.getAttribute("loginUser");
		if (userManageServiceImpl.editPassWord(user, newpassword))
		{
			j.setCode(GlobalConstant.SUCCESS);
		} else
		{
			j.setCode(GlobalConstant.FAIL);
		}
		return j;
	}
	
	/** 批量导入用户xls..
	 * 
	 * @return */
	@RequestMapping("/parseXls")
	public String parseXls(@RequestParam MultipartFile upload, HttpServletRequest request)
	{
		boolean flag = false;
		StringBuilder str = new StringBuilder();
		List<SysUser> list = new ArrayList<SysUser>();
		SysUser u = null;
		Set<String> accounts = new HashSet<String>();
		try
		{
			if (upload != null && upload.getSize() > 0)
			{
				// 如果是文件。
				InputStream inStream = upload.getInputStream();
				List<Object[]> data = ExcelUtility.readExcelContent(inStream, 1);
				for (Object[] objs : data)
				{
					u = new SysUser();
					for (int i = 0; i < objs.length; i++)
					{
						if (i == 0)
						{
							if (accounts.contains(objs[i].toString()))
							{
								str.append("账号").append(u.getFlaccount()).append("有重复.");
							}
							u.setFlaccount(objs[i].toString());
						} else if (i == 1)
						{
							u.setFlname(objs[i].toString());
						} else if (i == 2)
						{
							u.setFltelephone(objs[i].toString());
						}
					}
					list.add(u);
				}
				if (str.toString().equals(""))
				{
					for (SysUser user : list)
					{
						// 检查账号是否重复。
						flag = userManageServiceImpl.checkAccount(user.getFlaccount(), null);
						if (!flag)
						{
							str.append("姓名为").append(user.getFlname()).append("的用户账号名有重复,请修改后再导入.\n");
						}
					}
				}
			}
			// 批量导入用户信息，密码暂时设定为1，角色由管理员再修改
			if (!str.toString().equals(""))
			{
				request.setAttribute("backUrl", "/page/system/user/User_List.jsp");
				request.setAttribute("errorDesc", str.toString());
				return "/page/public/errorDesc";
			} else
			{
				// 开始导入用户信息。
				userManageServiceImpl.batchSaveUser(list);
				return "/page/system/user/User_List";
			}
		} catch (Exception e)
		{
			LOG.error("running error:", e);
			request.setAttribute("errorDesc", "/page/system/user/User_List.jsp");
			request.setAttribute("errorDesc", "导入失败！");
			return "/page/public/errorDesc";
		}
	}
	
	/** 用户批量导入xml模板下载
	 * 
	 * @return */
	@ResponseBody
	@RequestMapping("/downDemo")
	public void downDemo(HttpServletResponse response)
	{
		try
		{
			String downuserxls = PathUtil.getPath() + ConfigUtil.getParamValue("downuserxls");
			File file = new File(downuserxls);
			response.setHeader(
			        "Content-Disposition",
			        "attachment;fileName=" + java.net.URLEncoder.encode("userdemo.xls", "UTF-8"));
			response.setContentType("application/x-msdownload; charset = UTF-8");
			response.setContentLength((int) file.length());
			FileUtility.downLoad(downuserxls, response.getOutputStream());
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
	}
	
	/** 检查用户账号否唯一 */
	private boolean checkNameOnly(String name, String id)
	{
		if (userManageServiceImpl.checkAccount(name, id))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	@ResponseBody
	@RequestMapping("/getAdminData")
	public Grid getAdminData(HttpServletRequest request)
	{
		SysUser user = (SysUser)request.getSession().getAttribute("loginUser");
		int type = user.getFlusertype();
		Grid g = new Grid();
		List<Object> list = userManageServiceImpl.getData();
		List<Object> adminList = new ArrayList<Object>();
		for (Object object : list) {
			if(object.toString().contains("系统管理员"))
			{
				adminList.add(object);
			}
		}
		g.setRows(adminList);
		g.setTotal(adminList.size());
		return g;
	}
}
