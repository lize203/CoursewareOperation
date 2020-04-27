package com.zl.courseware.system.model.pojo;

import java.io.Serializable;

/**
 * 教案管理实体
 * @author helix
 *
 */
public class CoursesClassRelationDO implements Serializable
{
	private String flid;
	private String flclassid;
	private String flcoursesid;
	private String coursesname;
	private String teachername;
	private String classname;
	
	
	public CoursesClassRelationDO()
    {
	    super();
    }
	public CoursesClassRelationDO(String coursesname, String teachername, String classname)
    {
	    super();
	    this.coursesname = coursesname;
	    this.teachername = teachername;
	    this.classname = classname;
    }
	
	
	public CoursesClassRelationDO(String flid, String flclassid, String flcoursesid, String coursesname, String teachername, String classname)
    {
	    super();
	    this.flid = flid;
	    this.flclassid = flclassid;
	    this.flcoursesid = flcoursesid;
	    this.coursesname = coursesname;
	    this.teachername = teachername;
	    this.classname = classname;
    }
	public String getFlid()
    {
    	return flid;
    }
	public void setFlid(String flid)
    {
    	this.flid = flid;
    }
	public String getFlclassid()
    {
    	return flclassid;
    }
	public void setFlclassid(String flclassid)
    {
    	this.flclassid = flclassid;
    }
	public String getFlcoursesid()
    {
    	return flcoursesid;
    }
	public void setFlcoursesid(String flcoursesid)
    {
    	this.flcoursesid = flcoursesid;
    }
	public String getCoursesname()
    {
    	return coursesname;
    }
	public void setCoursesname(String coursesname)
    {
    	this.coursesname = coursesname;
    }
	public String getTeachername()
    {
    	return teachername;
    }
	public void setTeachername(String teachername)
    {
    	this.teachername = teachername;
    }
	public String getClassname()
    {
    	return classname;
    }
	public void setClassname(String classname)
    {
    	this.classname = classname;
    }
	
	
}
