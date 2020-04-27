package com.zl.courseware.system.model.kz;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * KzCourseware entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "kz_courseware")
public class KzCourseware implements java.io.Serializable
{
	// Fields
	/**
	 * 
	 */
	private static final long     serialVersionUID              = 1L;
	private String                flid;
	private String                flname;
	private String                flexplain;
	private String                flsrcpath;
	private String                fluserid;
	private String                flremark;
	private Date                  createtime;
	private Date                  modifytime;
	private String                flfullpath;
	private Date				  uploadtime;

	// Constructors
	/** default constructor */
	public KzCourseware()
	{
	}
	
	public KzCourseware(String flid, String flname, String flexplain, String flsrcpath, String fluserid, String flremark, Date createtime, Date modifytime, String flfullpath,Date uploadtime)
	{
		super();
		this.flid = flid;
		this.flname = flname;
		this.flexplain = flexplain;
		this.flsrcpath = flsrcpath;
		this.fluserid = fluserid;
		this.flremark = flremark;
		this.createtime = createtime;
		this.modifytime = modifytime;
		this.flfullpath = flfullpath;
		this.uploadtime = uploadtime;
	}

	// Property accessors
	@Id
	//	//@GeneratedValue(strategy = IDENTITY)
	@Column(name = "flid", unique = true, nullable = false, length = 32)
	public String getFlid()
	{
		return this.flid;
	}
	
	public void setFlid(String flid)
	{
		this.flid = flid;
	}
	
	@Column(name = "flname", length = 64)
	public String getFlname()
	{
		return this.flname;
	}
	
	public void setFlname(String flname)
	{
		this.flname = flname;
	}
	
	@Column(name = "flexplain", length = 1024)
	public String getFlexplain()
	{
		return this.flexplain;
	}
	
	public void setFlexplain(String flexplain)
	{
		this.flexplain = flexplain;
	}
	
	@Column(name = "flsrcpath", length = 256)
	public String getFlsrcpath()
	{
		return this.flsrcpath;
	}
	
	public void setFlsrcpath(String flsrcpath)
	{
		this.flsrcpath = flsrcpath;
	}
	
	@Column(name = "fluserid", length = 32)
	public String getFluserid()
	{
		return this.fluserid;
	}
	
	public void setFluserid(String fluserid)
	{
		this.fluserid = fluserid;
	}
	
	@Column(name = "flremark", length = 128)
	public String getFlremark()
	{
		return this.flremark;
	}
	
	public void setFlremark(String flremark)
	{
		this.flremark = flremark;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", nullable = false, length = 19)
	public Date getCreatetime()
	{
		return createtime;
	}
	
	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifytime", nullable = false, length = 19)
	public Date getModifytime()
	{
		return modifytime;
	}
	
	public void setModifytime(Date modifytime)
	{
		this.modifytime = modifytime;
	}
	
	public String getFlfullpath()
	{
		return flfullpath;
	}
	
	public void setFlfullpath(String flfullpath)
	{
		this.flfullpath = flfullpath;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploadtime", nullable = false, length = 19)
	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}
	
}