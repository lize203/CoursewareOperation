/**
 * 修改人：long
 * 修改时间：2015-03-06
 * 修改内容：使业务层声明事务异常时可回滚，业务层事务方法异常统一上浮；业务方法异常，返回false或null统一在操作日志拦截器中设置。
 * 详见：com.snc.system.framework.interceptors.OperationLogInterceptor#before
 */
package com.zl.courseware.system.service.system.impl;

import com.zl.courseware.system.dao.BaseDaoI;
import com.zl.courseware.system.framework.constant.LogAnnotation;
import com.zl.courseware.system.model.kz.KzCourseware;
import com.zl.courseware.system.model.kz.KzExperiment;
import com.zl.courseware.system.model.pojo.GzipDTO;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.service.system.ICoursewareService;
import com.zl.courseware.system.utils.*;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.commons.compress.archivers.ArchiveException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CoursewareServiceImpl implements ICoursewareService {
	private final Logger LOG = LoggerFactory
			.getLogger(CoursewareServiceImpl.class);
	@Autowired
	@Qualifier("base")
	private BaseDaoI<KzCourseware> kzCoursewareDao;

	@Autowired
	@Qualifier("base")
	private BaseDaoI<SysUser> userDao;
	@Autowired
	@Qualifier("base")
	private BaseDaoI<KzExperiment> kzExperimentDao;


	private void listToMap(List<KzCourseware> kzCourses, List<Object> value) {
		for (KzCourseware kzCourse : kzCourses) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("flid", kzCourse.getFlid());
			// if(kzCourse.getFlname().indexOf(".zip")>0)
			// {
			//
			// }
			row.put("flname", kzCourse.getFlname());
			row.put("flexplain", kzCourse.getFlexplain());
			row.put("flsrcpath", kzCourse.getFlsrcpath());
			row.put("flfullpath", kzCourse.getFlfullpath());
			String hql = "from SysUser where flid=?";
			List<SysUser> find = userDao.find(hql,
					new Object[] { kzCourse.getFluserid() });
			if (find != null && find.size() > 0) {
				SysUser sysUser = find.get(0);
				row.put("username", sysUser.getFlname());
			}
			row.put("fluserid", kzCourse.getFluserid());
			row.put("flremark", kzCourse.getFlremark());
			row.put("createtime", kzCourse.getCreatetime());
			row.put("modifytime", kzCourse.getModifytime());
			value.add(row);
		}
	}

	@Override
	@LogAnnotation(opDescribe = "修改XXXXXXXX", opType = "update")
	public boolean editCourses(KzCourseware kzCourseware, String newstr) {
		// String hashedPassword = MD5Util.md5(newstr);
		// String sql =
		// "update sys_user set flpassword=? where flaccount=? and flpassword=?";
		// userDao.executeSql(sql, new Object[] { hashedPassword,
		// user.getFlaccount(), user.getFlpassword() });
		return true;
	}

	/**
	 * 获取课件
	 */
	@Override
	public List<Object> getCoursewaresData(String id, String kjids) {
		List<Object> value = new ArrayList<Object>();
		try {
			// String hql =
			// "from KzCourserelation a left join fetch a.kzCourseware where a.kzCourses.flid=?";
			// List<KzCourserelation> kzCourserelations =
			// kzCourserelationDao.find(hql, new Object[] { id });
			List<KzCourseware> kzCoursewares = kzCoursewareDao
					.find("from KzCourseware");
			for (KzCourseware kzCourseware : kzCoursewares) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("kzCourseware.flname", kzCourseware.getFlname());
				row.put("flname", kzCourseware.getFlname());
				row.put("flid", kzCourseware.getFlid());
				if (kjids != null && kjids.length() > 0) {
					if (kjids.indexOf(",") > 0) {
						String[] split = kjids.split(",");
						@SuppressWarnings("unchecked")
						List<String> asList = Arrays.asList(split);
						if (asList.contains(kzCourseware.getFlid())) {
							row.put("checked", "true");
						}
					} else {
						if (kjids.equals(kzCourseware.getFlid())) {
							row.put("checked", "true");
						}
					}
				}
				// for (int i = 0; i < kzCourserelations.size(); i++)
				// {
				// if
				// (kzCourserelations.get(i).getKzCourseware().getFlid().equals(kzCourseware.getFlid()))
				// {
				// row.put("checked", "true");
				// break;
				// }
				// }
				value.add(row);
			}
		} catch (Exception e) {
			LOG.error("running error", e);
		}
		return value;
	}

	@Override
	@LogAnnotation(opDescribe = "获取课件列表信息", opType = "list")
	public List<Object> getData() {
		List<Object> value = new ArrayList<Object>();
		List<KzCourseware> kzCourses = kzCoursewareDao
				.find("from KzCourseware order by createtime desc");
		listToMap(kzCourses, value);
		return value;
	}

	/** 根据不同条件查找课件 */
	@SuppressWarnings("unchecked")
	@Override
	@LogAnnotation(opDescribe = "根据所有条件查询课件", opType = "query")
	public List<Object> query(String strs, String query) {
		String str = strs;
		List<Object> value = new ArrayList<Object>();
		try {
			boolean flag = StringUtil.decideSpecial(str);
			String esc = "";
			if (flag) {
				str = StringUtil.turnHqlCondition(str);
				esc = " escape '/'";
			}
			String ss = "";
			if ("*".equals(query)) {
				ss = " ( lower(kzcw.flname) like  lower('%" + str + "%') "
						+ esc + " or lower(kzcw.flexplain) like  lower('%"
						+ str + "%') " + esc
						+ " or lower(kzcw.flsrcpath) like  lower('%" + str
						+ "%') " + esc
						+ " or lower(kzcw.flremark) like  lower('%" + str
						+ "%') " + esc + " or lower(su.flname) like  lower('%"
						+ str + "%') " + ") ";
			} else if ("flname".equals(query)) {
				ss = " lower(kzcw.flname) like lower('%" + str + "%') " + esc
						+ " ";
			} else if ("flexplain".equals(query)) {
				ss = " lower(kzcw.flexplain) like  lower('%" + str + "%') "
						+ esc + " ";
			} else if ("flsrcpath".equals(query)) {
				ss = " lower(kzcw.flsrcpath) like lower('%" + str + "%') "
						+ esc + " ";
			} else if ("flremark".equals(query)) {
				ss = " lower(kzcw.flremark) like lower('%" + str + "%') " + esc
						+ " ";
			} else if ("createtime".equals(query)) {
				ss = " lower(kzcw.createtime) like lower('%" + str + "%') "
						+ esc + " ";
			} else if ("username".equals(query)) {
				ss = " lower(su.flname) like lower('%" + str + "%') " + esc
						+ " ";
			}
			String sql = "select kzcw.flid,kzcw.flname,kzcw.flexplain,kzcw.flsrcpath,kzcw.fluserid,kzcw.flremark,kzcw.createtime,kzcw.modifytime from kz_courseware kzcw inner join sys_user su on kzcw.fluserid = su.flid where "/*
																																																								 * fldisplay
																																																								 * =
																																																								 * 1
																																																								 * and
																																																								 */
					+ ss + "order by kzcw.createtime desc";
			// List<KzCourseware> kzCoursesList = kzCoursewareDao.find(sql);
			// String sql = " from KzCourseware where "/*fldisplay=1 and*/ + ss
			// + "order by kzcw.createtime desc";
			// Query q =
			// kzCoursewareDao.getCurrentSession().createSQLQuery(sql);
			// q.list();
			//
			SQLQuery createSQLQuery = kzCoursewareDao.getCurrentSession()
					.createSQLQuery(sql);
			createSQLQuery.addScalar("flid", StandardBasicTypes.STRING);
			createSQLQuery.addScalar("flname", StandardBasicTypes.STRING);
			createSQLQuery.addScalar("flexplain", StandardBasicTypes.STRING);
			createSQLQuery.addScalar("flsrcpath", StandardBasicTypes.STRING);
			createSQLQuery.addScalar("fluserid", StandardBasicTypes.STRING);
			createSQLQuery.addScalar("flremark", StandardBasicTypes.STRING);
			createSQLQuery
					.addScalar("createtime", StandardBasicTypes.TIMESTAMP);
			createSQLQuery
					.addScalar("modifytime", StandardBasicTypes.TIMESTAMP);
			Query setResultTransformer = createSQLQuery
					.setResultTransformer(Transformers
							.aliasToBean(KzCourseware.class));
			List<KzCourseware> list = setResultTransformer.list();
			listToMap(list, value);
		} catch (Exception e) {
			LOG.error("running error", e);
		}
		return value;
	}

	@Override
	public boolean checkFile(String name) {
		String hql = "from KzCourseware where flsrcpath like '%" + name + "%'";
		// Object[] obj = new Object[] { name};
		List<KzCourseware> kzCoursesList = kzCoursewareDao.find(hql);
		if (!kzCoursesList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean checkAccount(String name, String id) {
		String hql = "from KzCourseware where flname=?";
		Object[] obj = new Object[] { name };
		List<KzCourseware> kzCoursesList = kzCoursewareDao.find(hql, obj);
		if (!kzCoursesList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/** 新增课件 */
	@Override
	@LogAnnotation(opDescribe = "添加课件", opType = "add")
	public boolean addKzCourseware(KzCourseware kzCourseware) {
		kzCourseware.setFlid(UUIDUtil.getUUID());
		// kzCourses.setFldisplay(GlobalConstant.IS_DISPLAY);
		// String hashedPassword = MD5Util.md5(kzCourses.getFlpassword());
		// kzCourses.setFlpassword(hashedPassword);
		kzCourseware.setCreatetime(new Date());
		kzCourseware.setModifytime(new Date());
		kzCourseware.setUploadtime(new Date());	
		try {
			String flfullpath = kzCourseware.getFlfullpath();
			int indexOf = flfullpath.lastIndexOf(File.separatorChar);
			// WINDOWS - LINUX
			// int index = flfullpath.indexOf(":"+File.separatorChar);
			// String discPath = "";
			// if(index<=0)
			// {
			// index = flfullpath.indexOf("/");
			// discPath = flfullpath.substring(0, index+1);
			// }else{
			// discPath = flfullpath.substring(0, index+2);
			// }
			String parentPath = flfullpath.substring(0, indexOf);

			if (flfullpath.indexOf(GZipUtils.TARGZ) > 0) {
				Tarchive.unCompressArchiveGz(flfullpath);
			} else if (flfullpath.indexOf(GZipUtils.ZIP) > 0) {
				Tarchive.unZipFile(flfullpath);
			}

			List<GzipDTO> readZip = readZip(flfullpath);
			for (GzipDTO zipEntry : readZip) {
				String srcpath = zipEntry.getName();
				srcpath = srcpath.substring(0, srcpath.indexOf(".project") - 1);
				srcpath = srcpath.replace("/", File.separator);
				srcpath = srcpath.replace("\\", File.separator);
				String name = srcpath.substring(srcpath
						.lastIndexOf(File.separator) + 1);
				KzExperiment kzExperiment = new KzExperiment();
				kzExperiment.setFlid(UUIDUtil.getUUID());
				kzExperiment.setCoursewareid(kzCourseware.getFlid());
				kzExperiment.setExperimentname(name);
				kzExperiment.setModifytime(new Date());
				kzExperiment.setExperimentpath(parentPath + File.separator
						+ srcpath);
				kzExperiment.setExperimentsize(String.valueOf(zipEntry
						.getSize()));
				addExperiment(kzExperiment);
			}
			// else{
			// kzExperiment.setExperimentpath(discPath+name);
			// }
			kzCoursewareDao.save(kzCourseware);
		} catch (IOException | ArchiveException e) {
			return false;
		}
		return true;
	}

	/**
	 * 更新课件信息
	 *
	 *            课件ID串
	 * @return
	 */
	@Override
	@LogAnnotation(opDescribe = "更新保存课件信息", opType = "update")
	public boolean updateKzCourseware(KzCourseware kzCourseware, String flid, boolean isUpload) {
		
		String hql = "FROM KzCourseware WHERE flid=?";
		// 如果需要对比
		KzCourseware kz_Courseware = (KzCourseware) kzCoursewareDao.find(hql,
				new Object[] { flid }).get(0);
		kz_Courseware.setFlfullpath(kzCourseware.getFlfullpath());
		kz_Courseware.setFlsrcpath(kzCourseware.getFlsrcpath());
		kz_Courseware.setFlexplain(kzCourseware.getFlexplain());
		kz_Courseware.setFlname(kzCourseware.getFlname());
		kz_Courseware.setFlremark(kzCourseware.getFlremark());
		kz_Courseware.setModifytime(new Date());
	
		if(isUpload)
		{
			try {
				kz_Courseware.setUploadtime(new Date());	

				hql = "delete KzExperiment  where coursewareid=?";
				kzExperimentDao.executeHql(hql, new Object[] { flid });
				String flfullpath = kzCourseware.getFlfullpath();
				int indexOf = flfullpath.lastIndexOf(File.separatorChar);
				String parentPath = flfullpath.substring(0, indexOf);
				List<KzExperiment> kzExperiments = new ArrayList<KzExperiment>();
				List<GzipDTO> readZip = readZip(flfullpath);
				for (GzipDTO zipEntry : readZip) {
					String srcpath = zipEntry.getName();
					srcpath = srcpath.substring(0, srcpath.indexOf(".project") - 1);
					srcpath = srcpath.replace("/", File.separator);
					srcpath = srcpath.replace("\\", File.separator);
					String name = srcpath.substring(srcpath
							.lastIndexOf(File.separator) + 1);
					KzExperiment kzExperiment = new KzExperiment();
					kzExperiment.setFlid(UUIDUtil.getUUID());
					kzExperiment.setCoursewareid(kzCourseware.getFlid());
					kzExperiment.setExperimentname(name);
					kzExperiment.setModifytime(new Date());
					kzExperiment.setExperimentpath(parentPath + File.separator
							+ srcpath);
					kzExperiment.setExperimentsize(String.valueOf(zipEntry
							.getSize()));
					addExperiment(kzExperiment);
					kzExperiments.add(kzExperiment);
				}
				
			} catch (IOException | ArchiveException e) {
				return false;
			}
		}
		kzCoursewareDao.update(kz_Courseware);
		return true;
	}

	/**
	 * 删除课件。
	 * 
	 * @param flid
	 * @return
	 */
	@Override
	@LogAnnotation(opDescribe = "课件删除", opType = "del")
	public boolean delKzCourseware(String flid, String realPath) {
		KzCourseware kzCourseware = kzCoursewareDao.get(KzCourseware.class, flid);
		delFile(realPath, kzCourseware.getFlsrcpath());

		String hql = "delete KzCourseware  where flid=?";
		kzCoursewareDao.executeHql(hql, new Object[] { flid });
		hql = "delete KzExperiment  where coursewareid=?";
		kzExperimentDao.executeHql(hql, new Object[] { flid });
		return true;
	}

	private void delFile(String realPath, String flsrcpath) {
		String path = realPath + flsrcpath;//+new Date().getTime()
		path = path.replace("/", File.separator);
		path = path.replace("\\", File.separator);
		//System.out.println("移除目录："+path);
		File file = new File(path);
		int lastIndexOf = path.lastIndexOf(GZipUtils.TARGZ);
		int lastIndexOf2 = path.lastIndexOf(GZipUtils.ZIP);
		if (lastIndexOf < 0) {
			lastIndexOf = lastIndexOf2;
		}
		if (lastIndexOf > 0) {
			File dir = new File(path.substring(0, lastIndexOf));
			if (dir.exists()) {
				FileUtil.deleteFile(dir);
			}
		}
		if (file.exists())
		{
			file.delete();
		}
	}

	/**
	 * 批量预删除功能。
	 * 
	 * @param flid
	 * @return
	 */
	@Override
	@LogAnnotation(opDescribe = "批量删除", opType = "batchDel")
	public boolean batchDel(String flid, String realPath) {
		boolean flag = false;
		StringBuffer ids = new StringBuffer();
		String[] idsArray = flid.split(",");
		if (idsArray.length > 0) {
			for (int i = 0; i < idsArray.length; i++) {
				if (i < idsArray.length - 1) {
					ids.append("'").append(idsArray[i]).append("'").append(",");
				} else {
					ids.append("'").append(idsArray[i]).append("'");
				}

				KzCourseware kzCourseware = kzCoursewareDao.get(KzCourseware.class, idsArray[i]);
				delFile(realPath, kzCourseware.getFlsrcpath());
			}
			String hql2 = "delete KzCourseware where flid in(" + ids.toString()
					+ ")";
			kzCoursewareDao.executeHql(hql2);
			String hql3 = "delete KzExperiment  where coursewareid in("
					+ ids.toString() + ")";
			kzExperimentDao.executeHql(hql3);
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 查询课件列表
	 */
	@Override
	public List<KzCourseware> getCoursewares(String flids) {
		List<KzCourseware> kzCoursewares = null;
		if (flids != null && flids.length() > 0) {
			String[] strs;
			String flidStr = "";
			if (flids.indexOf(",") > 0) {
				strs = flids.split(",");
				for (String string : strs) {
					flidStr += "'" + string + "'";
				}
			} else {
				flidStr = "'" + flids + "'";
			}
			String hql = "from KzCourseware where flid in("
					+ flidStr.replace("''", "','") + ")";
			kzCoursewares = kzCoursewareDao.find(hql);
		}
		return kzCoursewares;
	}

	@Override
	public KzCourseware getCoursewareById(String flid) {
		String hql = "from KzCourseware where flid =?";
		KzCourseware kzCourseware = kzCoursewareDao.find(hql,
				new Object[] { flid }).get(0);
		return kzCourseware;
	}

	@Override
	public List<GzipDTO> readZip(String zipFilePath) throws IOException,
			ArchiveException {
		return GZipUtils.readGZip(zipFilePath);
	}

	@Override
	public boolean addExperiment(KzExperiment kzExperiment) {
		kzExperimentDao.save(kzExperiment);
		return true;
	}

	@Override
	public List<Object> getCoursewaresByTeacherId(String flid) {

		String sql = "SELECT DISTINCT kz_courseware.flid,kz_courseware.flname,kz_courseware.flexplain,kz_courseware.flfullpath,kz_courseware.flremark,kz_courseware.flsrcpath,"
				+ "kz_courseware.createtime,kz_courseware.modifytime,kz_courseware.uploadtime FROM kz_teacher,kz_teacherrelation,kz_courses,kz_courseware,kz_courserelation "
				+ "WHERE kz_teacher.flid=kz_teacherrelation.flteacherid AND kz_courses.flid=kz_teacherrelation.flcoursesid  "
				+ "AND kz_courses.flid=kz_courserelation.flcourseid AND kz_courserelation.flcoursewareid=kz_courseware.flid "
				+ "AND kz_teacher.flid='"
				+ flid
				+ "' ORDER BY kz_courseware.createtime DESC ";
		List<Object> value = new ArrayList<Object>();
		SQLQuery createSQLQuery = kzCoursewareDao.getCurrentSession()
				.createSQLQuery(sql);
		createSQLQuery.addScalar("flid", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flname", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flexplain", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flfullpath", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flsrcpath", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flremark", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("createtime", StandardBasicTypes.TIMESTAMP);
		createSQLQuery.addScalar("modifytime", StandardBasicTypes.TIMESTAMP);
		createSQLQuery.addScalar("uploadtime", StandardBasicTypes.TIMESTAMP);
		Query setResultTransformer = createSQLQuery
				.setResultTransformer(Transformers
						.aliasToBean(KzCourseware.class));
		@SuppressWarnings("unchecked")
		List<KzCourseware> list = setResultTransformer.list();
		listToMap(list, value);
		return value;
	}

	@Override
	public List<Object> getCoursewaresByStudentId(String flid) {
		String sql = "SELECT DISTINCT kz_courseware.flid,kz_courseware.flname,kz_courseware.flexplain,kz_courseware.flfullpath,kz_courseware.flremark,kz_courseware.flsrcpath,"
				+ "kz_courseware.createtime,kz_courseware.modifytime,kz_courseware.uploadtime FROM kz_student,kz_classcourses,kz_courses,kz_courseware,kz_courserelation "
				+ "WHERE kz_student.flclassid=kz_classcourses.flclassid AND kz_classcourses.flcoursesid=kz_courses.flid "
				+ "AND kz_courses.flid=kz_courserelation.flcourseid  AND kz_courses.flid=kz_courserelation.flcourseid "
				+ "AND kz_courserelation.flcoursewareid=kz_courseware.flid "
				+ "AND kz_student.flid='"
				+ flid
				+ "' ORDER BY kz_courseware.createtime DESC ";
		List<Object> value = new ArrayList<Object>();
		SQLQuery createSQLQuery = kzCoursewareDao.getCurrentSession()
				.createSQLQuery(sql);
		createSQLQuery.addScalar("flid", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flname", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flexplain", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flfullpath", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flsrcpath", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flremark", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("createtime", StandardBasicTypes.TIMESTAMP);
		createSQLQuery.addScalar("modifytime", StandardBasicTypes.TIMESTAMP);
		createSQLQuery.addScalar("uploadtime", StandardBasicTypes.TIMESTAMP);
		Query setResultTransformer = createSQLQuery
				.setResultTransformer(Transformers
						.aliasToBean(KzCourseware.class));
		@SuppressWarnings("unchecked")
		List<KzCourseware> list = setResultTransformer.list();
		listToMap(list, value);
		return value;
	}

	@Override
	public List<Object> getCoursewaresAll() {
		String sql = "SELECT DISTINCT kz_courseware.flid,kz_courseware.flname,kz_courseware.flexplain,kz_courseware.flfullpath,kz_courseware.flremark,kz_courseware.flsrcpath,"
				+ "kz_courseware.createtime,kz_courseware.modifytime,kz_courseware.uploadtime FROM kz_courseware ORDER BY kz_courseware.createtime DESC ";
		List<Object> value = new ArrayList<Object>();
		SQLQuery createSQLQuery = kzCoursewareDao.getCurrentSession()
				.createSQLQuery(sql);
		createSQLQuery.addScalar("flid", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flname", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flexplain", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flfullpath", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flsrcpath", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flremark", StandardBasicTypes.STRING);
		createSQLQuery.addScalar("flcreatetime", StandardBasicTypes.TIMESTAMP);
		createSQLQuery.addScalar("modifytime", StandardBasicTypes.TIMESTAMP);
		createSQLQuery.addScalar("uploadtime", StandardBasicTypes.TIMESTAMP);
		Query setResultTransformer = createSQLQuery
				.setResultTransformer(Transformers
						.aliasToBean(KzCourseware.class));
		@SuppressWarnings("unchecked")
		List<KzCourseware> list = setResultTransformer.list();
		listToMap(list, value);
		return value;
	}

	@Override
	public List<Object> checkCoursewareName(String name) {
		String hql = "from KzCourseware where flname = '"+name+"'";
		List<KzCourseware> kzCoursesList = kzCoursewareDao.find(hql);
		List<Object> value = new ArrayList<Object>();
		listToMap(kzCoursesList, value);
			return value;

	}

	@Override
	public List<Object> getExprimentsByCoursewareName(String name) {
		String hql = "from KzCourseware where flname = '"+name+"'";
		List<KzCourseware> kzCoursesList = kzCoursewareDao.find(hql);
		String hql2 = "from KzExperiment where coursewareid = '"+kzCoursesList.get(0).getFlid()+"'";
		List<KzExperiment> KzExperiments=kzExperimentDao.find(hql2);
		List<Object> value = new ArrayList<Object>();
		for (KzExperiment kzExperiment : KzExperiments) {
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("flid", kzExperiment.getFlid());
			row.put("coursewareid", kzExperiment.getCoursewareid());
			row.put("experimentlevel", kzExperiment.getExperimentlevel());
			row.put("experimentname", kzExperiment.getExperimentname());
			row.put("experimentparentid", kzExperiment.getExperimentparentid());
			row.put("experimentpath", kzExperiment.getExperimentpath());
			row.put("experimentsize", kzExperiment.getExperimentsize());
			row.put("modifytime", kzExperiment.getModifytime());
			value.add(row);
		}
		return value;
	}

	@Override
	public List<Map<String, Object>> getCoursewareData() {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String hql = "from KzCourseware order by createtime desc";
		List<KzCourseware> kzCoursesList = kzCoursewareDao.find(hql);
		for(KzCourseware courseware : kzCoursesList) {
//			Map<String, Object> node = new HashMap<String, Object>();
//			node.put("id", courseware.getFlid());
//			node.put("text", courseware.getFlname());
//			node.put("state", "closed");
//			List<Map<String, Object>> childs = getChilds(courseware.getFlid());
			List<Map<String, Object>> childs = getFileChilds(courseware.getFlfullpath(), courseware.getFlname());
//			if (childs.size() > 0) {
//				node.put("children", childs);
//			}
//			result.add(node);
			result.addAll(childs);
		}
		return result;
	}

	private List<Map<String, Object>> getFileChilds(String path, String name) {
		List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();
		String dirPath = Messages.couresewareUploadFiles;

		String p = path.substring(0, path.lastIndexOf(File.separatorChar));
		String dir = p + File.separatorChar + name;
		File dirFile = new File(dir);
		if (dirFile.exists()) {
			childs = XMLUtil.getKejainLibrary(dir);
		}

		return childs;
	}

	private List<Map<String, Object>> getChilds(String id) {
		List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();

		String hql2 = "from KzExperiment where coursewareid = '"+id+"'";
		List<KzExperiment> KzExperiments=kzExperimentDao.find(hql2);

		for (KzExperiment kzExperiment : KzExperiments) {
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id", kzExperiment.getFlid());
			node.put("text", kzExperiment.getExperimentname());
			childs.add(node);
		}
		return childs;
	}
}
