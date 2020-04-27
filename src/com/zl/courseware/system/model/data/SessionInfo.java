package com.zl.courseware.system.model.data;

import java.util.HashMap;

import com.zl.courseware.system.model.system.SysUser;


public class SessionInfo implements java.io.Serializable
{
	/**
	 * 登录Id验证
	 */
    private static final long serialVersionUID = 1L;
	public static HashMap<String,SysUser>	sessionUserInfo    = new HashMap<String,SysUser>();
}
