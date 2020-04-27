package com.zl.courseware.system.service.system.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zl.courseware.system.model.data.Grid;
import com.zl.courseware.system.model.system.SysUserlog;
import com.zl.courseware.system.service.system.IUserLogService;
import com.zl.courseware.system.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zl.courseware.system.dao.BaseDaoI;
import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.framework.constant.LogAnnotation;


@Service
public class UserLogServiceImpl implements IUserLogService
{
	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysUserlog> logDao;
	private final Logger         LOG = LoggerFactory.getLogger(UserLogServiceImpl.class);
	
	
	@Override
	@LogAnnotation(opDescribe = "获取日志列表操作", opType = "list")
	public Grid getData(int page, int rows)
	{
		Grid g = new Grid();
		List<Object> value = new ArrayList<Object>();
		try
		{
			String hql = "select count(*) from sys_userlog";
			BigInteger total = logDao.countBySql(hql);
			hql = "from SysUserlog order by unix_timestamp(time) desc";
			List<SysUserlog> loglist = logDao.find(hql, page, rows);
			ListToNewList(value, loglist);
			g.setTotal(total.intValue());
			g.setRows(value);
		} catch (Exception e)
		{
			LOG.error("running error:", e);
		}
		return g;
	}
	
	@Override
	@LogAnnotation(opDescribe = "根据操条件获取日志列表操作", opType = "query")
	public Grid query(String query, String strs, int page, int rows)
	{
		Grid g = new Grid();
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
			String sql = "select count(*) from sys_userlog ";
			String hql = "from SysUserlog ";
			if (!"".equals(str))
			{
				String where = "";
				if (GlobalConstant.USER.equals(query))
				{
					where = " where operator like '%" + str + "%' order by unix_timestamp(time) desc" + esc;
				} else if (GlobalConstant.DATE.equals(query))
				{
					where = " where unix_timestamp(time) <unix_timestamp('" + str
					        + "') order by unix_timestamp(time) desc" + esc;
				} else if (GlobalConstant.OPTYPE.equals(query))
				{
					where = " where type ='" + str + "' order by unix_timestamp(time) desc" + esc;
				}
				sql += where;
				hql += where;
			}
			BigInteger total = logDao.countBySql(sql);
			List<SysUserlog> loglist = logDao.find(hql, page, rows);
			ListToNewList(value, loglist);
			g.setTotal(total.intValue());
			g.setRows(value);
		} catch (Exception e)
		{
			LOG.error("running error", e);
		}
		return g;
	}
	
	@Override
	@LogAnnotation(opDescribe = "查看日志详情", opType = "view")
	public SysUserlog getLog(String id)
	{
		try
		{
			return logDao.get(SysUserlog.class, id);
		} catch (Exception e)
		{
			LOG.error("running error", e);
			return null;
		}
	}
	
	private void ListToNewList(List<Object> value, List<SysUserlog> loglist)
	{
		Map<String, Object> row;
		for (SysUserlog log : loglist)
		{
			row = new HashMap<String, Object>();
			row.put("id", log.getId());
			row.put("time", log.getTime().toString().substring(0, 19));
			row.put("description", log.getDescription());
			row.put("url", log.getUrl());
			row.put("operator", log.getOperator());
			row.put("parameter", log.getParameter());
			row.put("ip", log.getIp());
			row.put("type", log.getType());
			value.add(row);
		}
	}
}
