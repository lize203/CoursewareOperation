package com.zl.courseware.system.model.kz;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "kz_experiment")
public class KzExperiment implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String            flid;
	private String            experimentname;
	private Integer           experimentlevel;
	private String            experimentparentid;
	private String            experimentpath;
	private String            experimentsize;
	private String            coursewareid;
	private Date              modifytime;
	
	
	/** default constructor */
	public KzExperiment()
	{
	}
	
	public KzExperiment(String flid, String experimentname, Integer experimentlevel, String experimentparentid, String experimentpath, String experimentsize, String coursewareid, Date modifytime)
	{
		super();
		this.flid = flid;
		this.experimentname = experimentname;
		this.experimentlevel = experimentlevel;
		this.experimentparentid = experimentparentid;
		this.experimentpath = experimentpath;
		this.experimentsize = experimentsize;
		this.coursewareid = coursewareid;
		this.modifytime = modifytime;
	}
	
	@Id
	@Column(name = "flid", unique = true, nullable = false, length = 32)
	public String getFlid()
	{
		return flid;
	}
	
	public void setFlid(String flid)
	{
		this.flid = flid;
	}
	
	@Column(name = "experimentlevel", length = 2)
	public Integer getExperimentlevel()
	{
		return experimentlevel;
	}
	
	public void setExperimentlevel(Integer experimentlevel)
	{
		this.experimentlevel = experimentlevel;
	}
	
	@Column(name = "experimentname", length = 100)
	public String getExperimentname()
	{
		return experimentname;
	}
	
	public void setExperimentname(String experimentname)
	{
		this.experimentname = experimentname;
	}
	
	@Column(name = "experimentparentid", length = 32)
	public String getExperimentparentid()
	{
		return experimentparentid;
	}
	
	public void setExperimentparentid(String experimentparentid)
	{
		this.experimentparentid = experimentparentid;
	}
	
	@Column(name = "experimentpath", length = 500)
	public String getExperimentpath()
	{
		return experimentpath;
	}
	
	public void setExperimentpath(String experimentpath)
	{
		this.experimentpath = experimentpath;
	}
	
	@Column(name = "experimentsize", length = 32)
	public String getExperimentsize()
	{
		return experimentsize;
	}
	
	public void setExperimentsize(String experimentsize)
	{
		this.experimentsize = experimentsize;
	}
	
	@Column(name = "coursewareid", length = 256)
	public String getCoursewareid()
	{
		return coursewareid;
	}
	
	public void setCoursewareid(String coursewareid)
	{
		this.coursewareid = coursewareid;
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
}