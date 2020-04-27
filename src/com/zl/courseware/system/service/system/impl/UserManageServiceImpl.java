/**
 * 修改人：long
 * 修改时间：2015-03-06
 * 修改内容：使业务层声明事务异常时可回滚，业务层事务方法异常统一上浮；业务方法异常，返回false或null统一在操作日志拦截器中设置。
 * 详见：com.snc.system.framework.interceptors.OperationLogInterceptor#before
 */
package com.zl.courseware.system.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zl.courseware.system.model.system.SysRole;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.model.system.SysUserrole;
import com.zl.courseware.system.service.system.IUserManageService;
import com.zl.courseware.system.utils.MD5Util;
import com.zl.courseware.system.utils.StringUtil;
import com.zl.courseware.system.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zl.courseware.system.dao.BaseDaoI;
import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.framework.constant.LogAnnotation;


@Service
public class UserManageServiceImpl implements IUserManageService
{
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysUser>     userDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysUserrole> userRoleDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysRole>     roleDao;
	private final Logger          LOG = LoggerFactory.getLogger(UserManageServiceImpl.class);
	
	
	@Override
	@LogAnnotation(opDescribe = "获取用户列表信息", opType = "list")
	public List<Object> getData()
	{
		List<Object> value = new ArrayList<Object>();
		List<SysUser> userlist = userDao.find("from SysUser order by flcreatetime desc");
		listToMap(userlist, value);
		return value;
	}
	
	/** 根据不同条件查找用户 */
	@Override
	@LogAnnotation(opDescribe = "根据所有条件查询用户", opType = "query")
	public List<Object> query(String strs, String query)
	{
		String str = strs;
		List<Object> value = new ArrayList<Object>();
		try
		{
			boolean flag = StringUtil.decideSpecial(str);
			String esc = "";
			if (flag)
			{
				str = StringUtil.turnHqlCondition(str);
				esc = " escape '/'";
			}
			String ss = "";
			if (GlobalConstant.ALLTYPE.equals(query))
			{
				ss = " ( lower(flaccount) like  lower('%" + str + "%') " + esc + " or lower(flname) like  lower('%"
				        + str + "%') " + esc + " or lower(fltelephone) like  lower('%" + str + "%') "  +") ";
			} else if (GlobalConstant.ACCOUNT.equals(query))
			{
				ss = " lower(flaccount) like lower('%" + str + "%') " + esc + " ";
			} else if (GlobalConstant.NAME.equals(query))
			{
				ss = " lower(flname) like  lower('%" + str + "%') " + esc + " ";
			} else if (GlobalConstant.TEL.equals(query))
			{
				ss = " lower(fltelephone) like lower('%" + str + "%') " + esc + " ";
			}
			List<SysUser> userlist = userDao.find("from SysUser where fldisplay=1 and " + ss
			        + "order by flcreatetime desc");
			listToMap(userlist, value);
		} catch (Exception e)
		{
			LOG.error("running error", e);
		}
		return value;
	}
	
	/** 检测用户账号是否存在。
	 * 
	 * @param tbsysuser
	 * @return */
	@Override
	public boolean checkAccount(String name, String id)
	{
		String hql = "from SysUser where flaccount=?";
		Object[] obj;
		if (id != null)
		{
			hql = hql + " and flid!=?";
			obj = new Object[] { name, id };
		} else
		{
			obj = new Object[] { name };
		}
		List<SysUser> userlist = userDao.find(hql, obj);
		if (!userlist.isEmpty())
		{
			return false;
		} else
		{
			return true;
		}
	}
	
	
	/** 检测用户账号是否存在。
	 * 
	 * @param tbsysuser
	 * @return */
	@Override
	public boolean checkAccountByPersonid(String name, String flpersonid)
	{
		String hql = "from SysUser where flaccount=?  and flpersonid!=? ";
		Object[] obj = new Object[] { name, flpersonid };
		List<SysUser> userlist = userDao.find(hql, obj);
		if (!userlist.isEmpty())
		{
			return false;
		} else
		{
			return true;
		}
	}
	
	
	@Override
	@LogAnnotation(opDescribe = "根据档案ID获取用户信息", opType = "query")
	public SysUser getUserById(int flusertype,String flpersonid){
		String hql="from SysUser where flusertype=?  and flpersonid=? ";
		Object[] obj = new Object[] { flusertype, flpersonid };
		List<SysUser> userlist = userDao.find(hql, obj);
		if (!userlist.isEmpty())
		{
			return userlist.get(0);
		} else
		{
			return null;
		}
	}
	
	
	/** 批量导入
	 * 
	 * @param list
	 * @return */
	@Override
	@LogAnnotation(opDescribe = "批量添加用户", opType = "batchAdd")
	public boolean batchSaveUser(List<SysUser> list)
	{
		for (SysUser tbsysuser : list)
		{
			tbsysuser.setFlid(UUIDUtil.getUUID());
			tbsysuser.setFldisplay("1");
			tbsysuser.setFlpassword(MD5Util.md5("1"));
			tbsysuser.setFlcreatetime(new Date());
			userDao.save(tbsysuser);
		}
		return true;
	}
	
	/** 新增用户 */
	@Override
	@LogAnnotation(opDescribe = "添加用户", opType = "add")
	public boolean addUser(SysUser tbsysuser, String rolesID)
	{
		tbsysuser.setFlid(UUIDUtil.getUUID());
		tbsysuser.setFldisplay(GlobalConstant.IS_DISPLAY);
		String hashedPassword = MD5Util.md5(tbsysuser.getFlpassword());
		tbsysuser.setFlpassword(hashedPassword);
		tbsysuser.setFlcreatetime(new Date());
		userDao.save(tbsysuser);
		if (StringUtils.isBlank(rolesID) == false)
		{
			String[] roles = rolesID.split(",");
			for (int i = 0; i < roles.length; i++)
			{
				SysUserrole userrole = new SysUserrole();
				userrole.setFlid(UUIDUtil.getUUID());
				userrole.setSysUser(tbsysuser);
				String hql = "from SysRole where flid=?";
				SysRole role = roleDao.find(hql, new Object[] { roles[i] }).get(0);
				userrole.setSysRole(role);
				if (GlobalConstant.ADMIN.equals(role.getFlcomment()))
				{
					tbsysuser.setFlisadmin(GlobalConstant.IS_ADMIN);
					userDao.update(tbsysuser);
				}
				userRoleDao.save(userrole);
			}
		}
		return true;
	}
	
	
	/** 新增用户（学生，老师，管理员用户） */
	@Override
	@LogAnnotation(opDescribe = "新增学生，老师，管理员关联的登录账号与角色", opType = "add")
	public boolean addUserRoleInfo(SysUser tbsysuser)
	{
		//保存学生
		tbsysuser.setFldisplay(GlobalConstant.IS_DISPLAY);
		String hashedPassword = MD5Util.md5(tbsysuser.getFlpassword());
		tbsysuser.setFlpassword(hashedPassword);
		userDao.save(tbsysuser);
		
		//账号分配角色
		SysUserrole userrole = new SysUserrole();
		userrole.setFlid(UUIDUtil.getUUID());
		userrole.setSysUser(tbsysuser);
		String hql = "from SysRole where floperationrole="+tbsysuser.getFlusertype();
		SysRole role = roleDao.find(hql).get(0);
		userrole.setSysRole(role);
		userRoleDao.save(userrole);
		return true;
	}
	
	
	/** 更新用户（学生，老师，管理员用户） */
	@Override
	@LogAnnotation(opDescribe = "更新学生，老师，管理员关联的登录账号与角色", opType = "update")
	public boolean updateUserRoleInfo(SysUser tbsysuser)
	{
		String hql = "delete SysUserrole where sysUser.flid=?";
		userRoleDao.executeHql(hql, new Object[] { tbsysuser.getFlid() });
		
		//账号分配角色
		SysUserrole userrole = new SysUserrole();
		userrole.setFlid(UUIDUtil.getUUID());
		userrole.setSysUser(tbsysuser);
		hql = "from SysRole where floperationrole="+tbsysuser.getFlusertype();
		SysRole role = roleDao.find(hql).get(0);
		userrole.setSysRole(role);
		userRoleDao.save(userrole);
		
		userDao.update(tbsysuser);
		return true;
	}
	
	
	/** 更新用户信息
	 * 
	 * @param tbsysuser 用户实例
	 * @param entityID 用户表主键
	 * @param rolesID 角色ID串
	 * @return */
	@Override
	@LogAnnotation(opDescribe = "更新保存用户信息", opType = "update")
	public boolean updateUser(SysUser tbsysuser, String rolesID)
	{
		String userhql = "FROM SysUser WHERE flid=?";
		SysUser user = (SysUser) userDao.find(userhql, new Object[] { tbsysuser.getFlid() }).get(0);
		// 判断密码是否改变，如果是新密码则加密
		if (!tbsysuser.getFlpassword().equals(user.getFlpassword()))
		{
			String hashedPassword = MD5Util.md5(tbsysuser.getFlpassword());
			user.setFlpassword(hashedPassword);
		}
		user.setFltelephone(tbsysuser.getFltelephone());
		user.setFlname(tbsysuser.getFlname());
		if (StringUtils.isNotBlank(rolesID))
		{
			String hql = "delete SysUserrole where sysUser.flid=?";
			userRoleDao.executeHql(hql, new Object[] { tbsysuser.getFlid() });
			String[] roles = rolesID.split(",");
			for (int i = 0; i < roles.length; i++)
			{
				SysUserrole userrole = new SysUserrole();
				userrole.setFlid(UUIDUtil.getUUID());
				userrole.setSysUser(user);
				hql = "from SysRole where flid=?";
				SysRole role = roleDao.find(hql, new Object[] { roles[i] }).get(0);
				userrole.setSysRole(role);
				if (GlobalConstant.ADMIN.equals(role.getFlcomment()))
				{
					tbsysuser.setFlisadmin(GlobalConstant.IS_ADMIN);
				}
				userRoleDao.save(userrole);
			}
		}
		userDao.update(user);
		return true;
	}
	
	/** 删除用户。
	 * 
	 * @param flid
	 * @return */
	@Override
	@LogAnnotation(opDescribe = "用户删除", opType = "del")
	public boolean delUser(String flid)
	{
		String hql = "delete SysUserrole  where sysUser.flid=?";
		userRoleDao.executeHql(hql, new Object[] { flid });
		hql = "delete SysUser  where flid=?";
		userDao.executeHql(hql, new Object[] { flid });
		return true;
	}
	
	/** 批量预删除用户功能。
	 * 
	 * @param flid
	 * @return */
	@Override
	@LogAnnotation(opDescribe = "批量删除用户", opType = "batchDel")
	public boolean batchDel(String flid)
	{
		boolean flag = false;
		StringBuffer ids = new StringBuffer();
		String[] idsArray = flid.split(",");
		if (idsArray.length > 0)
		{
			for (int i = 0; i < idsArray.length; i++)
			{
				if (i < idsArray.length - 1)
				{
					ids.append("'").append(idsArray[i]).append("'").append(",");
				} else
				{
					ids.append("'").append(idsArray[i]).append("'");
				}
			}
			String hql1 = "delete SysUserrole  where sysUser.flid in(" + ids.toString() + ")";
			userRoleDao.executeHql(hql1);
			String hql2 = "delete SysUser where flid in(" + ids.toString() + ")";
			userDao.executeHql(hql2);
			flag = true;
		} else
		{
			flag = false;
		}
		return flag;
	}
	
	/** 获取所有角色列表 */
	@Override
	public List<Object> getRoleData(String entityID)
	{
		List<Object> value = new ArrayList<Object>();
		try
		{
			String hql = "from SysUserrole a left join fetch a.sysRole where a.sysUser.flid=?";
			List<SysUserrole> userrolelist = userRoleDao.find(hql, new Object[] { entityID });
			List<SysRole> rolelist = roleDao.find("from SysRole");
			for (SysRole tbsysrole : rolelist)
			{
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("sysrole.flname", tbsysrole.getFlname());
				row.put("flname", tbsysrole.getFlname());
				row.put("flid", tbsysrole.getFlid());
				for (int i = 0; i < userrolelist.size(); i++)
				{
					if (userrolelist.get(i).getSysRole().getFlid().equals(tbsysrole.getFlid()))
					{
						row.put("checked", "true");
						break;
					}
				}
				value.add(row);
			}
		} catch (Exception e)
		{
			LOG.error("running error", e);
		}
		return value;
	}
	
	/** 匹配旧密码是否正确 */
	@Override
	public int queryOldPassWord(SysUser user, String oldpassword)
	{
		try
		{
			String hashedPassword = MD5Util.md5(oldpassword);
			String hql = "from SysUser a where a.flaccount=? and a.flpassword=?";
			List<SysUser> list = userDao.find(hql, new Object[] { user.getFlaccount(), hashedPassword });
			if (!list.isEmpty())
			{
				return 1;
			} else
			{
				return 0;
			}
		} catch (Exception e)
		{
			LOG.error("running error", e);
			return 0;
		}
	}
	
	/** 修改密码 */
	@Override
	@LogAnnotation(opDescribe = "修改登录密码", opType = "update")
	public boolean editPassWord(SysUser user, String newpassword)
	{
		String hashedPassword = MD5Util.md5(newpassword);
		String sql = "update sys_user set flpassword=? where flaccount=? and flpassword=?";
		userDao.executeSql(sql, new Object[] { hashedPassword, user.getFlaccount(), user.getFlpassword() });
		return true;
	}
	
	private void listToMap(List<SysUser> userlist, List<Object> value)
	{
		for (SysUser tbsysuser : userlist)
		{
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("flname", tbsysuser.getFlname());
			row.put("flpassword", tbsysuser.getFlpassword());
			row.put("flaccount", tbsysuser.getFlaccount());
			row.put("fltelephone", tbsysuser.getFltelephone());
			row.put("flisadmin", tbsysuser.getFlisadmin());
			row.put("fldisplay", tbsysuser.getFldisplay());
			row.put("flid", tbsysuser.getFlid());
			row.put("username", tbsysuser.getFlaccount());
			String roles = "";
			String roleids = "";
			for (SysUserrole role : new ArrayList<SysUserrole>(tbsysuser.getSysUserroles()))
			{
				roles += roles == "" ? role.getSysRole().getFlname() : ("," + role.getSysRole().getFlname());
				roleids += roleids == "" ? role.getSysRole().getFlid() : ("," + role.getSysRole().getFlid());
			}
			row.put("flroles", roles);
			row.put("roleids", roleids);
			value.add(row);
		}
	}
}
