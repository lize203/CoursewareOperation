package com.zl.courseware.system.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zl.courseware.system.framework.constant.GlobalConstant;
import com.zl.courseware.system.framework.constant.LogAnnotation;
import com.zl.courseware.system.model.data.Grid;
import com.zl.courseware.system.model.data.Json;
import com.zl.courseware.system.model.system.SysUser;
import com.zl.courseware.system.service.system.ICoursewareService;
import com.zl.courseware.system.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.zl.courseware.system.controller.base.BaseController;
import com.zl.courseware.system.model.kz.KzCourseware;


@Controller
@RequestMapping("/courseware")
public class CoursewareController extends BaseController
{
	@Autowired
	private ICoursewareService iCoursewareService;
	
	
	/** 获取所有课件课件关系列表
	 * 
	 * 
	 * @param id
	 * @return */
	@ResponseBody
	@RequestMapping("/getCoursewares")
	public Grid getCoursewares(String id, String kjids)
	{
		Grid g = new Grid();
		List<Object> list = iCoursewareService.getCoursewaresData(id, kjids);
		g.setRows(list);
		g.setTotal(list.size());
		return g;
	}
	
	/** 条件查找课件
	 * 
	 * @param query
	 * @param number
	 * @return
	 * @throws UnsupportedEncodingException */
	@ResponseBody
	@RequestMapping("/query")
	public Grid query(String query, String number) throws UnsupportedEncodingException
	{
		Grid g = new Grid();
		List<Object> list = null;
		number = URLDecoder.decode(number, "UTF-8");
		number = number.trim();
		list = iCoursewareService.query(number, query);
		g.setRows(list);
		g.setTotal(list.size());
		return g;
	}
	
	/** 获取课件列表
	 * 
	 * @return */
	@ResponseBody
	@RequestMapping("/getData")
	public Grid getData()
	{
		Grid g = new Grid();
		List<Object> list = iCoursewareService.getData();
		g.setRows(list);
		g.setTotal(list.size());
		return g;
	}
	
	/** 添加课件
	 *
	 * @return */
	@ResponseBody
	@RequestMapping("/saveData")
	@LogAnnotation(opDescribe = "新增课件", opType = "add")
	public Json saveData(KzCourseware kzCourseware)
	{
		Json j = new Json();
		if (checkNameOnly(kzCourseware.getFlname(), null))
		{
			boolean rs = iCoursewareService.addKzCourseware(kzCourseware);
			if (rs)
			{
				j.setCode(GlobalConstant.SUCCESS);
			} else
			{
				j.setCode(GlobalConstant.FAIL);
			}
		} else
		{
			j.setCode(GlobalConstant.EXIST);
		}
		return j;
	}
	
	/** 批量删除课件。 */
	@ResponseBody
	@RequestMapping("/batchDel")
	@LogAnnotation(opDescribe = "批量删除课件", opType = "del")
	public Json batchDel(HttpServletRequest request,String array)
	{
		Json j = new Json();
        //删除附件
        ServletContext servletContext = request.getServletContext();//获取ServletContext的对象 代表当前WEB应用
        String dirPath = Messages.couresewareUploadFiles;
        String realPath = servletContext.getRealPath(dirPath);//得到文件上传目的位置的真实路径
        realPath = realPath.replace(dirPath.replace("/", File.separator),"");//去重复
        realPath = realPath.replace(File.separator + Messages.webapps + File.separator + Messages.projectname, "");

        if (iCoursewareService.batchDel(array, realPath))
		{
			j.setCode(GlobalConstant.SUCCESS);
		} else
		{
			j.setCode(GlobalConstant.FAIL);
		}
		return j;
	}
	
	/** 删除课件 */
	@ResponseBody
	@RequestMapping("/deleteData")
	@LogAnnotation(opDescribe = "删除课件", opType = "del")
	public Json deleteData(HttpServletRequest request,String id)
	{
		Json j = new Json();
        //删除附件
        ServletContext servletContext = request.getServletContext();//获取ServletContext的对象 代表当前WEB应用
        String dirPath = Messages.couresewareUploadFiles;
        String realPath = servletContext.getRealPath(dirPath);//得到文件上传目的位置的真实路径
        realPath = realPath.replace(dirPath.replace("/", File.separator),"");//去重复
        realPath = realPath.replace(File.separator + Messages.webapps + File.separator + Messages.projectname, "");

        if (iCoursewareService.delKzCourseware(id, realPath))
		{
			j.setCode(GlobalConstant.SUCCESS);
		} else
		{
			j.setCode(GlobalConstant.FAIL);
		}
		return j;
	}
	
	/** 检查课件否唯一 */
	private boolean checkNameOnly(String name, String id)
	{
		if (iCoursewareService.checkAccount(name, id))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	/** 检查课件文件是否唯一 */
	@SuppressWarnings("unused")
	private boolean checkFile(String name)
	{
		if (iCoursewareService.checkFile(name))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/uploadCourseware", method = RequestMethod.POST)
	@LogAnnotation(opDescribe = "上传课件", opType = "upload")
	public Json uploadCourseware(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//      response.getWriter().write(resMsg);
		Json j = new Json();
		String flremark = request.getParameter("flremark");
		String flexplain = request.getParameter("flexplain");
		String flname = request.getParameter("flname");
		int lastIndexOf = flname.lastIndexOf(GZipUtils.TARGZ);
		int lastIndexOf2 = flname.lastIndexOf(GZipUtils.ZIP);
		if (lastIndexOf < 0) {
			lastIndexOf = lastIndexOf2;
		}
		if (lastIndexOf > 0)
		{
			flname = flname.substring(0, lastIndexOf);
		}
		if (checkNameOnly(flname, null))// && checkFile(originalFilename)
		{
			KzCourseware kzCourseware = new KzCourseware();
			kzCourseware.setFlexplain(flexplain);
			kzCourseware.setFlname(flname);
			kzCourseware.setFlremark(flremark);
			if (!file.isEmpty())
			{
				uploadFile(request, file, kzCourseware);
			} else
			{
				j.setCode(GlobalConstant.FAIL);
				return j;
			}
			SysUser user = (SysUser) (request.getSession().getAttribute("loginUser"));
			kzCourseware.setFluserid(user.getFlid());
			//新增课件基础文本信息
			boolean rs = iCoursewareService.addKzCourseware(kzCourseware);
			if (rs)
			{
				j.setCode(GlobalConstant.SUCCESS);
			} else
			{
				j.setCode(GlobalConstant.FAIL);
			}
		} else
		{
			j.setCode(GlobalConstant.EXIST);
		}
		return j;
	}
	
	/** 保存更新课件 */
	@ResponseBody
	@RequestMapping(value = "/uploadCoursewareUpdate", method = RequestMethod.POST)
	@LogAnnotation(opDescribe = "更新修改课件", opType = "upload")
	public Json uploadCoursewareUpdate(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response, KzCourseware kzCourseware) throws IOException
	{
		//      response.getWriter().write(resMsg);
		Json j = new Json();
		String flid = kzCourseware.getFlid();
		boolean uploadFile = false;
		if (!file.isEmpty())
		{
			uploadFile = uploadFile(request, file, kzCourseware);
		}
		String flname = kzCourseware.getFlname();
		if (flname != null && !"".equals(flname) && flname.indexOf(GZipUtils.TARGZ) > 0)
		{
			int index0 = flname.lastIndexOf(".");
			flname = flname.substring(0, index0).toLowerCase();
			kzCourseware.setFlname(flname);
		}
		String flsrcpath = kzCourseware.getFlsrcpath();
		if (file.isEmpty() && (flsrcpath == null || "".equals(flsrcpath)))
		{
			j.setCode(GlobalConstant.FAIL);
			return j;
		}
	
			//新增课件基础文本信息
			boolean rs = iCoursewareService.updateKzCourseware(kzCourseware, flid, uploadFile);
			if (rs)
			{
				j.setCode(GlobalConstant.SUCCESS);
			} else
			{
				j.setCode(GlobalConstant.FAIL);
			}
		
		return j;
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param kzCourseware
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private boolean uploadFile(HttpServletRequest request, MultipartFile file, KzCourseware kzCourseware) throws IllegalStateException, IOException
	{
		long startTime = System.currentTimeMillis();
		System.out.println("fileName：" + file.getOriginalFilename());
		ServletContext servletContext = request.getServletContext();//获取ServletContext的对象 代表当前WEB应用
		String dirPath = Messages.couresewareUploadFiles;
		String realPath = servletContext.getRealPath(dirPath);//得到文件上传目的位置的真实路径
		realPath = realPath.replace("/", File.separator);
		realPath = realPath.replace("\\", File.separator);
		realPath = realPath.replace(File.separator + Messages.webapps + File.separator + Messages.projectname, "");
		System.out.println("realPath :" + realPath);
		File file1 = new File(realPath);
		if (!file1.exists())
		{
			file1.mkdirs();   //如果该目录不存在，就创建此抽象路径名指定的目录。
		}
		String filePath = File.separator + file.getOriginalFilename();//+kzCourseware.getFlname()+"_"+
		String path = realPath + filePath;//+new Date().getTime()
		path = path.replace("/", File.separator);
		path = path.replace("\\", File.separator);
		kzCourseware.setFlsrcpath(dirPath + filePath);
		kzCourseware.setFlfullpath(path);
		File newFile = new File(path);
		if (newFile.exists())
		{
			newFile.delete();
		}
		//通过CommonsMultipartFile的方法直接写文件
		file.transferTo(newFile);
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间：" + String.valueOf(endTime - startTime) + "ms");
		//		return GlobalConstant.SUCCESS;
		return true;
	}
	
	/**
	 * code 1 下载成功   -1 文件不存在   0  下载失败
	 * @param request
	 * @param response
	 * @param flids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/downloadFile")
	@LogAnnotation(opDescribe = "下载课件", opType = "download")
	public Json downloadFile(HttpServletRequest request, HttpServletResponse response, String flids) throws Exception
	{
		int BUFFER_SIZE = 1024 * 1024 * 10;
		InputStream in = null;
		OutputStream out = null;
		ZipOutputStream zipout = null;
		Json j = new Json();
		List<KzCourseware> list = iCoursewareService.getCoursewares(flids);
		if (list != null && list.size() > 0)
		{
			try
			{
				List<File> fileList = new ArrayList<File>();
				int len = 0;
				for (KzCourseware kzCourseware : list)
				{
					String realPath = kzCourseware.getFlfullpath();
					File file = new File(realPath);
					if (file.exists())
					{
						int length = (int) file.length();
						len += length;
						fileList.add(file);
					} else
					{
						throw new IOException(realPath.substring(realPath.lastIndexOf("\\")) + " can not find.");
					}
				}
				//    			response.reset();            
				response.setCharacterEncoding("ISO-8859-1");
				response.setContentType("application/x-download");
				response.addHeader("Content-Length", "" + len);
				response.setHeader("Accept-Ranges", "bytes");
				response.setContentType("application/octet-stream;charset=ISO-8859-1");
				String filename = "CoursewareDownload" + GZipUtils.TARGZ;
				if (fileList != null && fileList.size() == 1)
				{
					filename = fileList.get(0).getName();
					response.addHeader("Content-Disposition", "attachment;filename="
					        + new String(filename.getBytes(), "ISO-8859-1"));
					int i = 0;
					byte[] buffer = new byte[BUFFER_SIZE];
					in = new BufferedInputStream(new FileInputStream(fileList.get(0)), BUFFER_SIZE);
					out = new BufferedOutputStream(response.getOutputStream());
					while ((i = in.read(buffer)) > 0)
					{
						out.write(buffer, 0, i);
					}
					in.close();
					out.flush();
					out.close();
				} else
				{
					response.addHeader("Content-Disposition", "attachment;filename="
					        + new String(filename.getBytes(), "ISO-8859-1"));
					zipout = new ZipOutputStream(response.getOutputStream());
					ZipUtils.doCompress(fileList, zipout);
				}
				j.setCode(GlobalConstant.FAIL);
				j.setSuccess(true);
				j.setMsg("success!");
			} catch (IOException ex)
			{
				//失败
				j.setCode(GlobalConstant.FAIL);
				j.setMsg("not find the file" + ex.getMessage());
				j.setSuccess(false);
			}
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/getCoursewareData")
	public Object getCoursewareData(HttpServletRequest request){
		List<Map<String,Object>> result = iCoursewareService.getCoursewareData();
		return result;
	}

	/**
	 * 导出选择的课件
	 * @param request
	 * @param response
	 * @param files
	 * @return code 1 成功    0  失败
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/exportCourseware")
	@LogAnnotation(opDescribe = "导出选择的课件", opType = "download")
	public Json exportCourseware(HttpServletRequest request, HttpServletResponse response, String files, String prefix) throws Exception
	{
		int BUFFER_SIZE = 1024 * 1024 * 10;
		InputStream in = null;
		OutputStream out = null;
		ZipOutputStream zipout = null;
		Json j = new Json();
		if (StringUtils.isNotBlank(files)) {
			try {
				ServletContext servletContext = request.getServletContext();
				String dirPath = Messages.couresewareUploadFiles;
				String realPath = servletContext.getRealPath(dirPath);//得到文件所在位置
				realPath = realPath.replace("/", File.separator);
				realPath = realPath.replace("\\", File.separator);
				realPath = realPath.replace(File.separator + Messages.webapps + File.separator
						+ Messages.projectname, "");

				String[] fArray = files.split(",");

				String path = Tarchive.exportCourseware(fArray, realPath, prefix);
				File f = new File(path);
				String filename = f.getName();

				response.setCharacterEncoding("ISO-8859-1");
				response.setContentType("application/x-download");
				response.addHeader("Content-Length", "" + f.length());
				response.setHeader("Accept-Ranges", "bytes");
				response.setContentType("application/octet-stream;charset=ISO-8859-1");
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes(), "ISO-8859-1"));

				int i = 0;
				byte[] buffer = new byte[BUFFER_SIZE];
				in = new BufferedInputStream(new FileInputStream(f), BUFFER_SIZE);
				out = new BufferedOutputStream(response.getOutputStream());
				while ((i = in.read(buffer)) > 0)
				{
					out.write(buffer, 0, i);
				}
				in.close();
				out.flush();
				response.flushBuffer();
				out.close();
				f.delete();

				j.setCode(GlobalConstant.SUCCESS);
				j.setSuccess(true);
				j.setMsg("success!");
			} catch (IOException ex)
			{
				ex.printStackTrace();
				//失败
				j.setCode(GlobalConstant.FAIL);
				j.setMsg("not find the file" + ex.getMessage());
				j.setSuccess(false);
			}
		}
		return j;
	}

}
