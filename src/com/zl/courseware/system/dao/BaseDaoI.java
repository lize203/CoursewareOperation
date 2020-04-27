package com.zl.courseware.system.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public interface BaseDaoI<T> {
	
	public Session getCurrentSession();

	public Serializable save(T o);

	public void delete(T o);

	public void update(T o);

	public void saveOrUpdate(T o);

	public T get(Class<T> c, Serializable id);

	public T get(String hql);

	public T get(String hql, Map<String, Object> params);
	
	public T get(String hql, Object... params);

	public List<T> find(String hql);
	
	public List<T> find(String hql, Object... params);

	public List<T> find(String hql, Map<String, Object> params);
	
	/**
	 * 支持 name绑定参数的HQL查询方法
	 * @param hql hql语句
	 * @param key_params 存放基本类型参数的map
	 * @param in_params 存放类似于：in(...) 表达式中的list类型的map
	 * @return
	 */
	List<T> find(Map<String, Object> key_params, Map<String, List> in_params,String hql);
	
	public List<T> find(String hql, int page, int rows);
	
	public List<T> find(String hql, int page, int rows, Object... params);

	public List<T> find(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 支持 name绑定参数的HQL分页查询方法
	 * @param hql hql语句
	 * @param key_params 存放基本类型参数的map
	 * @param in_params 存放类似于：in(...) 表达式中的list类型的map
	 * @return
	 */
	List<T> find( Map<String, Object> key_params, Map<String, List> in_params,int page, int rows,String hql);

	public Long count(String hql);

	public Long count(String hql, Map<String, Object> params);
	
	public Long count( Map<String, Object> params,Map<String, List> in_params,String hql);
	
	public Long count(String hql, Object... params);

	public int executeHql(String hql);
	
	public int executeHql(String hql, Object... params);

	public int executeHql(String hql, Map<String, Object> params);
	
	public int executeHql(Map<String, Object> params,Map<String, List> in_params,String hql);

	public List<Object[]> findBySql(String sql);
	
	public List<Object> findBySQL(String sql);
	
	public List<Object[]> findBySql(String sql, int page, int rows);
	
	public List<Object[]> findBySql(String sql, Map<String, Object> params);
	
	public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows);
	
	/**
	 * 支持 name绑定参数的sql查询方法
	 * @param sql  sql语句
	 * @param key_params  存放基本类型参数的map
	 * @param in_params   存放类似于：in(...) 表达式中的list类型的map
	 * @return
	 */
	List findBySql(Map<String, Object> key_params, Map<String, List> in_params,String sql);
	
	public List<T> queryForPage(String hql, int firstResult, int maxResults);

	/**
	 * 支持 name绑定参数的sql查询方法
	 * @param sql  sql语句
	 * @param key_params  存放基本类型参数的map
	 * @param in_params   存放类似于：in(...) 表达式中的list类型的map
	 * @return
	 */
	List findBySql( Map<String, Object> key_params, Map<String, List> in_params, int page, int rows,String sql);

	public int executeSql(String sql);
	
	public int executeSql(String sql, Object... params);

	public int executeSql(String sql, Map<String, Object> params);
	
	public int executeSql( Map<String, Object> params,Map<String, List> in_params,String sql);

	public BigInteger countBySql(String sql);
	
	public BigInteger countBySql(String sql, Map<String, Object> params);

	public BigInteger countBySql(Map<String, Object> params,Map<String, List> in_params,String sql);

	public Object findUniqueBySql(String sql);
	
	public Object findUniqueBySql(String sql, Object... recordid);
	
	public Object findUniqueBySql(String sql, Map<String, Object> params);
	
	public Object findUniqueByHQL(String hql);
	
	public Object findUniqueByHQL(String hql, Object... params);
	
	public Object findUniqueByHQL(String hql, Map<String, Object> params);
	
	public List<Map<String, Object>> findBySqlForMap(String sql, Map<String, Object> params);
	
	public List<Map<String, Object>> findBySqlForMap(Map<String, Object> params,Map<String, List> in_params,String sql);
	
	public List<Map<String, Object>> findBySqlForMap(String sql, Object... params);
	
    public List<Map<String, Object>> findBySqlForMap(String sql, int page, int rows);
	
	public List<Map<String, Object>> findBySqlForMap(String sql, Map<String, Object> params, int page, int rows);
	
	public List<Map<String, Object>> findBySqlForMap(String sql,  int page, int rows,Object... params);
	
	public List<Map<String, Object>> findBySqlForMap( Map<String, Object> key_params, Map<String, List> in_params, int page, int rows,String sql);
	
}
