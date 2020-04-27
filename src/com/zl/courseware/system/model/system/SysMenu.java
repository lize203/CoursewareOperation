package com.zl.courseware.system.model.system;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zl.courseware.bench.model.SysShortcut;


/** SysMenu generated by hbm2java */
@Entity
@Table(name = "sys_menu")
public class SysMenu implements java.io.Serializable
{
	/**
	 * 
	 */
	private String            flid;
	private SysMenu           sysMenu;
	private String            flname;
	private String            flurl;
	private Integer           florder;
	private Integer           flisvisiable;
	private String            flicon;
	private String            flcode;
	private Set<SysMenupower> sysMenupowers = new HashSet<SysMenupower>(0);
	private Set<SysFunction>  sysFunctions  = new HashSet<SysFunction>(0);
	private Set<SysMenu>      sysMenus      = new HashSet<SysMenu>(0);
	private Set<SysShortcut>  sysShortcuts  = new HashSet<SysShortcut>(0);
	private String            checked;
	
	
	public SysMenu()
	{
	}
	
	public SysMenu(String flid)
	{
		this.flid = flid;
	}
	
	public SysMenu(String flid, SysMenu sysMenu, String flname, String flurl, Integer florder, Integer flisvisiable, String flicon, String flcode, Set<SysMenupower> sysMenupowers, Set<SysFunction> sysFunctions, Set<SysMenu> sysMenus, Set<SysShortcut> sysShortcuts, String checked)
	{
		super();
		this.flid = flid;
		this.sysMenu = sysMenu;
		this.flname = flname;
		this.flurl = flurl;
		this.florder = florder;
		this.flisvisiable = flisvisiable;
		this.flicon = flicon;
		this.flcode = flcode;
		this.sysMenupowers = sysMenupowers;
		this.sysFunctions = sysFunctions;
		this.sysMenus = sysMenus;
		this.sysShortcuts = sysShortcuts;
		this.checked = checked;
	}
	
	@Id
	@Column(name = "flid", unique = true, nullable = false, length = 32)
	public String getFlid()
	{
		return this.flid;
	}
	
	public void setFlid(String flid)
	{
		this.flid = flid;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flparentid")
	public SysMenu getSysMenu()
	{
		return this.sysMenu;
	}
	
	public void setSysMenu(SysMenu sysMenu)
	{
		this.sysMenu = sysMenu;
	}
	
	@Column(name = "flname")
	public String getFlname()
	{
		return this.flname;
	}
	
	public void setFlname(String flname)
	{
		this.flname = flname;
	}
	
	@Column(name = "flurl")
	public String getFlurl()
	{
		return this.flurl;
	}
	
	public void setFlurl(String flurl)
	{
		this.flurl = flurl;
	}
	
	@Column(name = "florder")
	public Integer getFlorder()
	{
		return this.florder;
	}
	
	public void setFlorder(Integer florder)
	{
		this.florder = florder;
	}
	
	@Column(name = "flisvisiable")
	public Integer getFlisvisiable()
	{
		return this.flisvisiable;
	}
	
	public void setFlisvisiable(Integer flisvisiable)
	{
		this.flisvisiable = flisvisiable;
	}
	
	@Column(name = "flicon")
	public String getFlicon()
	{
		return this.flicon;
	}
	
	public void setFlicon(String flicon)
	{
		this.flicon = flicon;
	}
	
	@Column(name = "flcode")
	public String getFlcode()
	{
		return this.flcode;
	}
	
	public void setFlcode(String flcode)
	{
		this.flcode = flcode;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysMenu")
	public Set<SysMenupower> getSysMenupowers()
	{
		return this.sysMenupowers;
	}
	
	public void setSysMenupowers(Set<SysMenupower> sysMenupowers)
	{
		this.sysMenupowers = sysMenupowers;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysMenu")
	public Set<SysFunction> getSysFunctions()
	{
		return this.sysFunctions;
	}
	
	public void setSysFunctions(Set<SysFunction> sysFunctions)
	{
		this.sysFunctions = sysFunctions;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysMenu")
	public Set<SysMenu> getSysMenus()
	{
		return this.sysMenus;
	}
	
	public void setSysMenus(Set<SysMenu> sysMenus)
	{
		this.sysMenus = sysMenus;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysMenu")
	public Set<SysShortcut> getSysShortcuts()
	{
		return sysShortcuts;
	}
	
	public void setSysShortcuts(Set<SysShortcut> sysShortcuts)
	{
		this.sysShortcuts = sysShortcuts;
	}
	
	@Transient
	public String getChecked()
	{
		return checked;
	}
	
	public void setChecked(String checked)
	{
		this.checked = checked;
	}
}
