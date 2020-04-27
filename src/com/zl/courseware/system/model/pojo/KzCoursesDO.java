package com.zl.courseware.system.model.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 教案管理实体
 * @author helix
 *
 */
public class KzCoursesDO implements Serializable
{
	private String kzcwusername;
	private String kzcflname;
	private String kzcflgrade;
	private String kzcwname;
	private String kzcwflexplain;
	private String kzcwflsrcpath;
	private Date kzcwcreatetime;
	private Date	kzcwmodifytime;
	
	public KzCoursesDO()
    {
	    super();
    }
	public KzCoursesDO(String kzcwusername, String kzcflname, String kzcflgrade, String kzcwname, String kzcwflexplain, String kzcwflsrcpath, Date kzcwcreatetime, Date kzcwmodifytime)
    {
	    super();
	    this.kzcwusername = kzcwusername;
	    this.kzcflname = kzcflname;
	    this.kzcflgrade = kzcflgrade;
	    this.kzcwname = kzcwname;
	    this.kzcwflexplain = kzcwflexplain;
	    this.kzcwflsrcpath = kzcwflsrcpath;
	    this.kzcwcreatetime = kzcwcreatetime;
	    this.kzcwmodifytime = kzcwmodifytime;
    }
	public String getKzcwusername()
    {
    	return kzcwusername;
    }
	public void setKzcwusername(String kzcwusername)
    {
    	this.kzcwusername = kzcwusername;
    }
	public String getKzcflname()
    {
    	return kzcflname;
    }
	public void setKzcflname(String kzcflname)
    {
    	this.kzcflname = kzcflname;
    }
	public String getKzcflgrade()
    {
    	return kzcflgrade;
    }
	public void setKzcflgrade(String kzcflgrade)
    {
    	this.kzcflgrade = kzcflgrade;
    }
	public String getKzcwname()
    {
    	return kzcwname;
    }
	public void setKzcwname(String kzcwname)
    {
    	this.kzcwname = kzcwname;
    }
	public String getKzcwflexplain()
    {
    	return kzcwflexplain;
    }
	public void setKzcwflexplain(String kzcwflexplain)
    {
    	this.kzcwflexplain = kzcwflexplain;
    }
	public String getKzcwflsrcpath()
    {
    	return kzcwflsrcpath;
    }
	public void setKzcwflsrcpath(String kzcwflsrcpath)
    {
    	this.kzcwflsrcpath = kzcwflsrcpath;
    }
	public Date getKzcwcreatetime()
    {
    	return kzcwcreatetime;
    }
	public void setKzcwcreatetime(Date kzcwcreatetime)
    {
    	this.kzcwcreatetime = kzcwcreatetime;
    }
	public Date getKzcwmodifytime()
    {
    	return kzcwmodifytime;
    }
	public void setKzcwmodifytime(Date kzcwmodifytime)
    {
    	this.kzcwmodifytime = kzcwmodifytime;
    }
	
	
}
