package com.zl.courseware.system.controller.system;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpSession;

import com.zl.courseware.system.controller.base.BaseController;
import com.zl.courseware.system.model.data.Grid;
import com.zl.courseware.system.model.system.SysUserlog;
import com.zl.courseware.system.service.system.IUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/log")
public class LogController extends BaseController
{
	@Autowired
	private IUserLogService logservice;
	
	
	/** 获取日志列表 */
	@ResponseBody
	@RequestMapping("/getData")
	public Grid getData(int page, int rows)
	{
		Grid g = new Grid();
		g = logservice.getData(page, rows);
		return g;
	}
	
	/** 根据条件查询日志
	 * 
	 * @param query
	 * @throws UnsupportedEncodingException */
	@ResponseBody
	@RequestMapping("/query")
	public Grid query(String query, String number, int page, int rows) throws UnsupportedEncodingException
	{
		Grid g = new Grid();
		number = URLDecoder.decode(number.trim(), "UTF-8");
		g = logservice.query(query, number, page, rows);
		return g;
	}
	
	/** 双击查看详情
	 * 
	 * @param id
	 * @return */
	@RequestMapping("/view")
	public String view(String id, HttpSession sessionMap)
	{
		SysUserlog log = logservice.getLog(id);
		sessionMap.setAttribute("log", log);
		return "/page/system/log/Log_View";
	}
}
