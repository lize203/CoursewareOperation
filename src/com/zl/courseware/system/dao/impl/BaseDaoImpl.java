package com.zl.courseware.system.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.zl.courseware.system.dao.BaseDaoI;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("base")
public class BaseDaoImpl<T> implements BaseDaoI<T> {

//	private static final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Autowired
	protected SessionFactory sessionFactory;

	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public Serializable save(T o) {
		if (o != null) {
			return this.getCurrentSession().save(o);
		}
		return null;
	}
	
	@Override
	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	@Override
	public T get(String hql) {
		
//		long d1=System.currentTimeMillis();
//		log.info("执行hql："+hql);
		Query q = this.getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if ((l != null) && (!l.isEmpty())) {
			return l.get(0);
		}
//		log.info("执行hql耗时："+(System.currentTimeMillis()-d1)+"毫秒");
		return null;
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		
//		long d1=System.currentTimeMillis();
//		log.info("执行hql："+hql);
		
		Query q = this.getCurrentSession().createQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
//		log.info("执行hql耗时："+(System.currentTimeMillis()-d1)+"毫秒");
		
		if ((l != null) && (!l.isEmpty())) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public void delete(T o) {
		if (o != null) {
			this.getCurrentSession().delete(o);
		}
	}

	@Override
	public void update(T o) {
		if (o != null) {
			this.getCurrentSession().merge(o);
			
		}
	}
	
	@Override
	public void saveOrUpdate(T o) {
		if (o != null) {
			this.getCurrentSession().saveOrUpdate(o);
		}
	}

	@Override
	public List<T> find(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}
	
	@Override
	public List<T> find(String hql, Object[] params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null) {
			for(int i = 0; i < params.length; i ++){
				q.setParameter(i, params[i]);
			}
		}
		return q.list();
	}
	
	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> find(String hql, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	@Override
	public List<T> find(String hql, int page, int rows, Object... paramsValues) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (paramsValues != null) {
			for (int i = 0; i < paramsValues.length; i++) {
				q.setParameter(i, paramsValues[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	@Override
	public Long count(String hql, Object... paramsValues) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (paramsValues != null) {
			for (int i = 0; i < paramsValues.length; i++) {
				q.setParameter(i, paramsValues[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public int executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}
	
	@Override
	public int executeHql(String hql, Object... params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null) {
			for(int i = 0; i < params.length; i ++){
				q.setParameter(i, params[i]);
			}
		}
		return q.executeUpdate();
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}


	@Override
	public List findBySql(String sql, int page, int rows) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<Object[]> findBySql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.list();
	}
	
	@Override
	public List<Object> findBySQL(String sql) {
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		return query.list();
	}
	
	/**
	 * 分页查询
	 * 
	 * @param hql
	 *            查询的条件
	 * @param firstResult
	 *            开始记录
	 * @param maxResults
	 *            一次查询几条记录
	 * @return
	 */
	@Override
	public List<T> queryForPage(String hql, int firstResult, int maxResults) {
		Query query = this.getCurrentSession().createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		List<T> list = query.list();
		return list;

	}


	@Override
	public List<Object[]> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}


	@Override
	public int executeSql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}
	@Override
	public int executeSql(String sql, Object[] params) {
		Query q = getCurrentSession().createSQLQuery(sql);
		if (params != null) {
			for(int i = 0; i < params.length; i ++){
				q.setParameter(i, params[i]);
			}
		}
		return q.executeUpdate();
	}
		

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public BigInteger countBySql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

	@Override
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}


	@Override
	public Object findUniqueBySql(String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		return q.uniqueResult();
	}

	@Override
	public Object findUniqueBySql(String sql, Object... params){
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if (params != null) {
			for(int i = 0; i < params.length; i ++){
				q.setParameter(i, params[i]);
			}
		}
		return q.uniqueResult();
	}

	@Override
	public Object findUniqueBySql(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.uniqueResult();
	}

	@Override
	public Object findUniqueByHQL(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.uniqueResult();
	}

	@Override
	public Object findUniqueByHQL(String hql, Object[] params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null) {
			for(int i = 0; i < params.length; i ++){
				q.setParameter(i, params[i]);
			}
		}
		return q.uniqueResult();
	}

	@Override
	public Object findUniqueByHQL(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.uniqueResult();
	}

	@Override
	public List<Map<String, Object>> findBySqlForMap(String sql, Map<String, Object> params) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		//设定结果结果集中的每个对象为Map类型   
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<Map<String, Object>> findBySqlForMap(String sql,
			Object... params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if (params != null) {
			for(int i = 0; i < params.length; i ++){
				q.setParameter(i, params[i]);
			}
		}
		return q.list();
	}

	@Override
	public T get(String hql, Object... params) {
//		long d1=System.currentTimeMillis();
//		log.info("执行hql："+hql);
		
		Query q = this.getCurrentSession().createQuery(hql);
		if ((params != null) && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		List<T> l = q.list();
//		log.info("执行hql耗时："+(System.currentTimeMillis()-d1)+"毫秒");
		
		if ((l != null) && (!l.isEmpty())) {
			return l.get(0);
		}
		return null;
	}
	
	
	
	@Override
	public List<T> find( Map<String, Object> key_params, Map<String, List> in_params,String hql) {
		Query q = getCurrentSession().createQuery(hql);
		
		if (key_params != null) {
			for (String key : key_params.keySet()) {
				q.setParameter(key, key_params.get(key));
			}
		}
		
		if (in_params != null) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		
		return q.list();
	}
	
	
	@Override
	public List findBySql( Map<String, Object> key_params, Map<String, List> in_params,String sql) {
		Query q = getCurrentSession().createSQLQuery(sql);
		
		if (key_params != null) {
			for (String key : key_params.keySet()) {
				q.setParameter(key, key_params.get(key));
			}
		}
		
		if (in_params != null) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(Map<String, Object> key_params,
			Map<String, List> in_params, int page, int rows,String hql) {
		Query q = getCurrentSession().createQuery(hql);
		
		if (key_params != null) {
			for (String key : key_params.keySet()) {
				q.setParameter(key, key_params.get(key));
			}
		}
		
		if (in_params != null) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Long count( Map<String, Object> params, Map<String, List> in_params,String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if ((in_params != null) && !in_params.isEmpty()) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public int executeHql(Map<String, Object> params,
			Map<String, List> in_params, String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if ((in_params != null) && !in_params.isEmpty()) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public List findBySql( Map<String, Object> key_params,
			Map<String, List> in_params, int page, int rows,String sql) {
		Query q = getCurrentSession().createSQLQuery(sql);
		if (key_params != null) {
			for (String key : key_params.keySet()) {
				q.setParameter(key, key_params.get(key));
			}
		}
		if (in_params != null) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public int executeSql( Map<String, Object> params,
			Map<String, List> in_params,String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if (in_params != null) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public BigInteger countBySql(Map<String, Object> params,
			Map<String, List> in_params,String sql) {
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if (in_params != null) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}

	@Override
	public List<Map<String, Object>> findBySqlForMap(Map<String, Object> params, Map<String, List> in_params,String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		if (in_params != null) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<Map<String, Object>> findBySqlForMap(String sql, int page,
			int rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<Map<String, Object>> findBySqlForMap(String sql,
			Map<String, Object> params, int page, int rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<Map<String, Object>> findBySqlForMap(String sql, int page,
			int rows, Object... params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if ((params != null) && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<Map<String, Object>> findBySqlForMap(
			Map<String, Object> key_params, Map<String, List> in_params,
			int page, int rows, String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		q.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		if ((key_params != null) && !key_params.isEmpty()) {
			for (String key : key_params.keySet()) {
				q.setParameter(key, key_params.get(key));
			}
		}
		if (in_params != null) {
			for (String key : in_params.keySet()) {
				q.setParameterList(key, in_params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}



	
}
