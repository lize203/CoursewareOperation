package com.zl.courseware.system.utils;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


public class HibernateUtility
{
	private HibernateUtility()
	{
		super();
	}
	
	public static boolean executeHQL(Session session, String hql)
	{
		session.createQuery(hql).executeUpdate();
		return true;
	}
	
	public static boolean executeHQL(Session session, String hql, Object[] parameters)
	{
		Query query = session.createQuery(hql);
		if (parameters != null)
		{
			for (int i = 0; i < parameters.length; i++)
			{
				query.setParameter(i, parameters[i]);
			}
		}
		query.executeUpdate();
		return true;
	}
	
	public static boolean executeHQL(Session session, String hql, List parameters)
	{
		Query query = session.createQuery(hql);
		if (parameters != null)
		{
			for (int i = 0; i < parameters.size(); i++)
			{
				query.setParameter(i, parameters.get(i));
			}
		}
		query.executeUpdate();
		return true;
	}
	
	public static boolean executeSQL(Session session, String sql)
	{
		session.createSQLQuery(sql).executeUpdate();
		return true;
	}
	
	public static boolean executeSQL(Session session, String sql, Object[] parameters)
	{
		Query query = session.createSQLQuery(sql);
		if (parameters != null)
		{
			for (int i = 0; i < parameters.length; i++)
			{
				query.setParameter(i, parameters[i]);
			}
		}
		query.executeUpdate();
		return true;
	}
	
	public static boolean saveEntity(Session session, Object... entity)
	{
		for (int i = 0; i < entity.length; i++)
		{
			session.save(entity[i]);
		}
		return true;
	}
	
	public static List findByHQL(Session session, String hql)
	{
		return session.createQuery(hql).list();
	}
	
	public static List findBySQL(Session session, String sql)
	{
		return session.createSQLQuery(sql).list();
	}
	
	public static List findBySQL(Session session, String sql, Object[] parameters)
	{
		Query query = session.createSQLQuery(sql);
		if (parameters != null)
		{
			for (int i = 0; i < parameters.length; i++)
			{
				query.setParameter(i, parameters[i]);
			}
		}
		return query.list();
	}
	
	/**
	 * 按位置参数查找
	 * 
	 */
	public static List findBySQL(Session session, String sql, List params)
	{
		Query query = session.createSQLQuery(sql);
		if (params != null && !params.isEmpty())
		{
			for (int i = 0; i < params.size(); i++)
			{
				query.setParameter(i, params.get(i));
			}
		}
		return query.list();
	}
	
	public static List findByHQL(Session session, String hql, Object... objects)
	{
		Query query = session.createQuery(hql);
		if (objects != null)
		{
			for (int i = 0; i < objects.length; i++)
			{
				query.setParameter(i, objects[i]);
			}
		}
		return query.list();
	}
	
	public static boolean mergeEntity(Session session, Object entity)
	{
		session.merge(entity);
		return true;
	}
	
	public static Object mergeEntityReturnObj(Session session, Object entity)
	{
		Object merge = session.merge(entity);
		return merge;
	}
	
	public static boolean updateEntity(Session session, Object entity)
	{
		session.update(entity);
		return true;
	}
	
	public static boolean deleteEntity(Session session, Object entity)
	{
		session.delete(entity);
		return true;
	}
	
	public static boolean saveBySql(Session session, String sql)
	{
		session.createSQLQuery(sql).executeUpdate();
		return true;
	}
	
	/** 分页查询
	 * 
	 * @param hql 查询的条件
	 * @param firstResult 开始记录
	 * @param maxResults 一次查询几条记录
	 * @return */
	public static List queryForPage(Session session, final String hql, final int firstResult, final int maxResults)
	{
		Query query = session.createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		List list = query.list();
		return list;
	}
	
	public static boolean updateByHQL(Session session, final String HQL, final Object... paramsValues)
	{
		Query query = session.createQuery(HQL);
		if (paramsValues != null)
		{
			for (int i = 0; i < paramsValues.length; i++)
			{
				query.setParameter(i, paramsValues[i]);
			}
		}
		query.executeUpdate();
		return true;
	}
	
	/* 返回唯一结果 如: select count(*) from xxx [where....] (可以有占位符) */
	public static Object findUniqueResult(Session session, final String HQL, final Object... paramsValues)
	{
		Query query = session.createQuery(HQL);
		if (paramsValues != null)
		{
			for (int i = 0; i < paramsValues.length; i++)
			{
				query.setParameter(i, paramsValues[i]);
			}
		}
		return query.uniqueResult();
	}
	
	public static void closeSession(Session session)
	{
		if (session != null)
		{
			session.close();
		}
	}
}
