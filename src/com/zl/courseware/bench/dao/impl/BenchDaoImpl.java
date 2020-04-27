package com.zl.courseware.bench.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zl.courseware.system.model.system.SysMenu;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.zl.courseware.bench.dao.IBenchDao;
import com.zl.courseware.bench.model.SysShortcut;
import com.zl.courseware.system.dao.impl.BaseDaoImpl;

@Repository("benchDao")
public class BenchDaoImpl extends BaseDaoImpl<SysShortcut> implements IBenchDao {
	
	@Override
	public Map<String, Object> getChange(String aduitman) {
		String sql ="select work.fl_change_number,work.fl_change_title,work.fl_emergency_version,"
				+ "work.flworkflowstate,work.fl_aduitman_name,user.flname,date_format(work.fl_create_time,'%Y-%m-%d %H:%i:%s') , work.flid , date_format(t2.flworkflowupdatetime,'%Y-%m-%d %H:%i:%s'),work.fl_apply_type "
				+ "from (dmm_workflowinstance work left outer join dmm_workflowinstancedata t2 on work.flid=t2.flworkflowinstanceid )"
				+ " ,sys_user user where user.flid=work.flcreatorid "
				+ "and (work.fl_approver like '%"+aduitman+"%'";
		sql += ") group by work.flid order by work.fl_create_time desc";
		Map<String,Object > data = new HashMap<String, Object>();
		List<Object[]> list = findBySql(sql);
		data.put("total", list.size());
		List<Object> value = new ArrayList<Object>();
		data.put("rows", value);

		for (Object[] obj : list) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("flchangenumber", obj[0]);
			row.put("flchangetitle", obj[1]);
			row.put("flemergencyversion",obj[2]);
			row.put("flworkflowstate", obj[3]);
			row.put("flapprover", obj[4]);
			row.put("flname", obj[5]);
			row.put("flfirstsubmittime",obj[6]);
			row.put("flid", obj[7]);
			if (obj[8] != null) {
				row.put("flworkflowupdatetime", obj[8]);
			} else {
				row.put("flworkflowupdatetime", obj[6]);
			}
			row.put("type", obj[9]);
			value.add(row);
		}
		return data;
	}

	@Override
	public Map<String, Object> getMenu(String flid) {
		String sql = "select t1.flid, t1.flname, t1.flicon, t1.flurl from sys_menu t1 where flisvisiable=1 and t1.flurl !='#' " +
                "and t1.flname !='工作台' and flid in (select flmenuid from sys_menupower where flroleid in (select flroleid from sys_userrole " +
                "where fluserid ='"+flid+"')) and t1.flname not in (select fl_name from sys_shortcut where fl_user_id = '"+flid+"') and t1.flname !='公共信息展示' and t1.flname !='业务架构' and t1.flname !='小型机' and t1.flname !='网络设备' and t1.flname !='PC服务器' and t1.flname !='磁盘列阵' and t1.flname !='光纤交换机' ";
		List<Object[]> list = findBySql(sql);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("total", list.size());
		List<Object> value = new ArrayList<Object>();
		data.put("rows", value);
		for(Object[] obj : list){
			Map<String, Object> row = new HashMap<String, Object>();
            row.put("flid", obj[0]);
			row.put("flname",obj[1]);
			row.put("flicon", obj[2]);
			row.put("flurl", obj[3]);
			value.add(row);
		}
		return data;
	}
	
	@Override
	public HashMap<String, Object> getShortcut(String id,Set<String> menuids) {
		String sql = "select c.* from sys_shortcut c where c.fl_user_id ='"+id+"' and c.fl_menu_id in  "
				+ "(select p.flmenuid from sys_menupower p LEFT JOIN sys_menu m on p.flmenuid = m.flid  where p.flroleid in  "
				+ "(select r.flroleid from sys_userrole r where r.fluserid = '"+id+"') and m.flisvisiable = 1) order by c.fl_create_time desc";
		List<Object[]> list = findBySql(sql);
		HashMap<String,Object> data = new HashMap<String, Object>();
		
		List<Object> value = new ArrayList<Object>();
		data.put("rows", value);
		int i=0;
		if(list!=null){
			for(Object[] tbbmshortcut : list){
				HashMap<String, Object> row = new HashMap<String, Object>();
				row.put("flid", tbbmshortcut[0]);
				row.put("flname", tbbmshortcut[1]);
				row.put("flurl", tbbmshortcut[2]);
				row.put("flicon", tbbmshortcut[3]);
				row.put("flMenuId", tbbmshortcut[8]);
				row.put("flbr", i);
				value.add(row);
				i++;
			}
		}
		data.put("total", i);
		return data;
	}

	@Override
	public void remove(String flid){
		String hql = "delete from SysShortcut a where a.flId='" + flid + "'";
		executeHql(hql);
	}


	@Override
	public List<SysMenu> getAncestorMenu(String menuId) {
		String hql = "SELECT tm FROM SysMenu tm LEFT JOIN FETCH tm.sysMenu tmt WHERE tm.flid = ? AND tmt IS NOT NULL";
		Query q = getCurrentSession().createQuery(hql);
		q.setParameter(0, menuId);
		return q.list();
	}

}