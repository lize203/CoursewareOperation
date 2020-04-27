package com.zl.courseware.system.service.system;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.zl.courseware.system.model.pojo.GzipDTO;
import org.apache.commons.compress.archivers.ArchiveException;

import com.zl.courseware.system.model.kz.KzCourseware;
import com.zl.courseware.system.model.kz.KzExperiment;

public interface ICoursewareService {
	public List<Object> getData();

	/* 根据所有条件查询课课件 */
	public List<Object> query(String str, String query);

	/**
	 * 检测课件是否存在。
	 * 
	 * @param tbsysKzCourseware
	 * @return
	 */
	public boolean checkAccount(String name, String id);

	public boolean checkFile(String name);

	/** 新增课件 */
	public boolean addKzCourseware(KzCourseware kzCourseware);

	/**
	 * 编辑课件信息
	 * 
	 * @param tbsysKzCourseware
	 *            课件实例
	 * @param entityID
	 *            课件表主键
	 * @param courseID
	 *            角色ID串
	 * @return
	 */
	public boolean updateKzCourseware(KzCourseware kzCourseware, String flid, boolean isUpload);

	/**
	 * 删除课件。
	 * 
	 * @param flid
	 * @return
	 */
	public boolean delKzCourseware(String flid, String realPath);

	/**
	 * 批量删除课件。
	 * 
	 * @param flid
	 * @return
	 */
	public boolean batchDel(String flid, String realPath);

	public List<KzCourseware> getCoursewares(String flids);

	// 获取课件对应的所有的课件
	public List<Object> getCoursewaresData(String id, String kjids);

	public KzCourseware getCoursewareById(String flid);

	public boolean editCourses(KzCourseware kzCourseware, String newstr);

	public List<GzipDTO> readZip(String zipFilePath) throws IOException,
			ArchiveException;

	public boolean addExperiment(KzExperiment kzExperiment);

	public List<Object> getCoursewaresByTeacherId(String flid);

	public List<Object> getCoursewaresByStudentId(String flid);
	
	public List<Object> getCoursewaresAll();
	
	public List<Object> checkCoursewareName(String name);
	
	public List<Object> getExprimentsByCoursewareName(String name);

	List<Map<String,Object>> getCoursewareData();

}
