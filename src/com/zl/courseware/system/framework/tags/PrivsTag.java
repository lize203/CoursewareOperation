package com.zl.courseware.system.framework.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zl.courseware.system.model.system.SysFunction;


public class PrivsTag extends TagSupport
{
	private String            operateID;
	private List<SysFunction> functions;
	private String            code;
	
	
	public void setOperateID(String operateID)
	{
		this.operateID = operateID;
	}
	
	public void setFunctions(List<SysFunction> functions)
	{
		this.functions = functions;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public int doStartTag() throws JspException
	{
		if (operateID != null && functions != null && code != null)
		{
			for (SysFunction fun : functions)
			{
				if (operateID.equals(fun.getSysMenu().getFlid()) && "2".equals(fun.getFlurl())
				        && code.equals(fun.getFleventfunction()))
				{
					return EVAL_PAGE;
				}
			}
		}
		// 跳过body,body部分不会显示
		return SKIP_BODY;
	}
}
