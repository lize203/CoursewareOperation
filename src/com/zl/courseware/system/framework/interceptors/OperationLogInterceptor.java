package com.zl.courseware.system.framework.interceptors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.zl.courseware.system.framework.constant.LogAnnotation;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.model.system.SysUserlog;
import com.zl.courseware.system.utils.UUIDUtil;
import net.sf.json.JSONObject;

import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class OperationLogInterceptor
{
	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	
	private static final Logger LOG = LoggerFactory.getLogger(OperationLogInterceptor.class);
	
	
	/** 用户操作日志记录
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable */
	public Object before(ProceedingJoinPoint joinPoint) throws Throwable
	{
		Object object = null;
		// 调用方法名称
		String methodName = null;
		String longMethodName = null;
		String[] parts = null;
		String returnType = null;
		SysUser user = null;
		HttpServletRequest request = null;
		boolean isSys = false;
		try
		{
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e1)
		{
			//LOG.error("任务调度,非http请求，非用户操作，系统自发！");
			isSys = true;
			//LOG.error("log记录报错:", e);
		}
		if (!isSys)
		{
			user = (SysUser) request.getSession().getAttribute("loginUser");
		}
		try
		{
			methodName = joinPoint.getSignature().getName();
			longMethodName = joinPoint.getSignature().toLongString();
			if (longMethodName != null && longMethodName.length() > 0)
			{
				parts = longMethodName.split(" ");
				//获取被拦截方法的返回值类型
				returnType = parts[parts.length - 2];
			}
			object = joinPoint.proceed();
		} catch (Exception e)
		{
			//LOG.info("方法执行异常:", e);
			/** 使业务层声明事务异常时可回滚，业务层事务方法异常统一上浮。设置操作记录拦截器优先级，异常先到达事务拦截器，再到达操作记录拦截器。
			 * 为了匹配原有的代码逻辑：控制器调用业务方法异常，业务方法返回false或null，此处在异常发生后匹配该逻辑。 */
			if (!methodName.startsWith("get") && !methodName.startsWith("find") && !methodName.startsWith("load")
			        && !methodName.startsWith("search") && !methodName.startsWith("datagrid"))
			{
				LOG.error("业务层事务方法调用异常", e);
				//业务方法异常，返回false或null
				if ("java.lang.Boolean".equals(returnType))
				{
					object = Boolean.FALSE;
				} else if ("boolean".equals(returnType))
				{
					object = false;
				} else if ("int".equals(returnType))
				{
					object = -1;
				} else if ("void".equals(returnType))
				{
					throw e;
				} else
				{
					object = null;
				}
			} else
			{//非事务方法,还按原来的逻辑处理异常
				throw e;
			}
			//throw e;
		} finally
		{
			try
			{
				SysUserlog log = new SysUserlog();
				String logtype = "";
				// 调用的类名
				String className = joinPoint.getTarget().getClass().toString();
				String url = "类名：" + className + "，方法名：" + methodName;
				String operation = "";
				String operator = "";
				Object[] args = joinPoint.getArgs();
				String parameters = "";
				Class<?>[] argsClass = new Class<?>[args.length];
				for (int i = 0; i < argsClass.length; i++)
				{
					if (args[i] != null)
					{
						argsClass[i] = args[i].getClass();
					}
				}
				Method[] methods = joinPoint.getTarget().getClass().getDeclaredMethods();
				Method method = null;
				for (Method m : methods)
				{
					if (m.getName().equals(methodName))
					{
						method = m;
						break;
					}
				}
				if (method.isAnnotationPresent(LogAnnotation.class))
				{
					String opDescribe = method.getAnnotation(LogAnnotation.class).opDescribe();
					String opType = method.getAnnotation(LogAnnotation.class).opType();
					operation = operation + "执行了" + opDescribe + "操作。";
					if ("login".equals(opType))
					{
						for (int i = 0; i < argsClass.length; i++)
						{
							String classType = argsClass[i].getName();
							if (classType.endsWith("SysUser"))
							{
								Field[] f = argsClass[i].getDeclaredFields();
								for (Field field : f)
								{
									field.setAccessible(true);
									String fieldName = field.getName();
									if ("flaccount".equals(fieldName))
									{
										parameters = parameters + "用户名：" + field.get(args[i]) + ",";
										operator = field.get(args[i]) + "";
										operation = "用户" + field.get(args[i]) + operation;
									} else if ("flpassword".equals(fieldName))
									{
										String pwd = field.get(args[i]) + "";
										parameters = parameters + "密码：" + pwd.substring(0, pwd.length() / 2) + "******";
									}
								}
							}
						}
						if (object != null)
						{
							operation = operation + "登录成功。";
						}
						logtype = "登录";
					} else if ("logout".equals(opType))
					{
						parameters = parameters + "用户名：" + user.getFlaccount();
						operator = user.getFlaccount();
						operation = "用户" + user.getFlaccount() + operation;
						logtype = "退出";
					} else
					{
						//JSONArray p= JSONArray.fromObject(args);
						JSONObject p = new JSONObject();
						for (Object temp : args)
						{
							if (temp == null)
							{
								continue;
							}
							// 获取参数类型,不同参数类型数据处理不一样
							Class<? extends Object> paramClazz = temp.getClass();
							String classType = paramClazz.getName();
							if (classType.endsWith("String"))
							{
								p.put("String", temp);
							} else if (classType.endsWith("Integer"))
							{
								p.put("Integer", temp);
							} else if (classType.endsWith("Long"))
							{
								p.put("Long", temp);
							} else if (classType.endsWith("Boolean"))
							{
								p.put("Boolean", temp);
							} else if (classType.endsWith("Date"))
							{
								p.put("Date", temp.toString());
							} else if (classType.startsWith("com.snc"))
							{
								Field[] f = paramClazz.getDeclaredFields();
								for (Field field : f)
								{
									field.setAccessible(true);
									String type = field.getType().toString();
									//LOG.info(type);
									if (type.endsWith("Set") || type.endsWith("List") || type.endsWith("Map")
									        || type.startsWith("shsnc.mvc.model"))
									{
										continue;
									} else if (type.endsWith("Date"))
									{
										p.put("Date", field.get(temp) == null ? "null" : field.get(temp).toString());
										continue;
									}
									String fieldName = field.getName();
									//LOG.info(fieldName);
									p.put(fieldName, field.get(temp));
								}
							}
						}
						parameters = p.toString();
						if (user != null)
						{
							operation = "用户" + user.getFlaccount() + "" + operation;
							operator = user.getFlaccount();
						} else
						{
							operation = "系统执行任务调度";
							operator = "调度程序";
						}
						if (object != null && object.getClass().getName().endsWith("Boolean"))
						{
							Boolean b = (Boolean) object;
							if (b)
							{
								operation = operation + "操作成功。";
							} else
							{
								operation = operation + "操作失败。";
							}
						} else
						{
							operation = operation + "操作成功。";
						}
						logtype = getType(opType);
					}
					log.setDescription(operation);
					log.setId(UUIDUtil.getUUID());
					log.setOperator(operator);
					log.setParameter(parameters);
					log.setTime(new Date());
					log.setUrl(url);
					log.setIp(request == null ? null : getIpAddr(request));
					log.setType(logtype);
					Session session = sessionFactory.openSession();
					session.save(log);
					session.flush();
					session.close();
					/* sessionFactory.getCurrentSession().save(); */
				}
			} catch (Exception e1)
			{
				LOG.info("log记录报错:", e1);
				//LOG.error("log记录报错:", e);
			}
		}
		return object;
	}
	
	/** 获取用户操作类型
	 * 
	 * @param opType
	 * @return */
	private String getType(String opType)
	{
		if ("batchAdd".equals(opType) || "add".equals(opType))
		{
			return "添加";
		} else if ("batchDel".equals(opType) || "del".equals(opType))
		{
			return "删除";
		} else if ("update".equals(opType) || "edit".equals(opType))
		{
			return "修改";
		} else if ("list".equals(opType) || "query".equals(opType) || "view".equals(opType))
		{
			return "查看";
		} else
		{
			return "其他";
		}
	}
	
	/** 获取用户真实ip
	 * 
	 * @param request
	 * @return */
	private String getIpAddr(HttpServletRequest request)
	{
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getRemoteAddr();
		}
		if ("0:0:0:0:0:0:0:1".equals(ipAddress))
		{//用localhost访问应用
			ipAddress = "localhost";
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15)
		{ //"***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0)
			{
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
