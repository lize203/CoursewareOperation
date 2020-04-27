package com.zl.courseware.bench.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zl.courseware.system.model.system.SysMenu;

/**
 * SysShortcut generated by hbm2java
 */
@Entity
@Table(name = "sys_shortcut")
public class SysShortcut implements java.io.Serializable {

	/**
	 * 
	 */
	private String flId;
	private SysMenu sysMenu;
	private String flName;
	private String flUrl;
	private String flIcon;
	private Date flCreateTime;
	private String flUserId;
	private String flSpareA;
	private String flSpareB;

	public SysShortcut() {
	}

	public SysShortcut(String flId, Date flCreateTime) {
		this.flId = flId;
		this.flCreateTime = flCreateTime;
	}

	public SysShortcut(String flId, SysMenu sysMenu, String flName,
			String flUrl, String flIcon, Date flCreateTime, String flUserId,
			String flSpareA, String flSpareB) {
		this.flId = flId;
		this.sysMenu = sysMenu;
		this.flName = flName;
		this.flUrl = flUrl;
		this.flIcon = flIcon;
		this.flCreateTime = flCreateTime;
		this.flUserId = flUserId;
		this.flSpareA = flSpareA;
		this.flSpareB = flSpareB;
	}

	@Id
	@Column(name = "fl_id", unique = true, nullable = false, length = 32)
	public String getFlId() {
		return this.flId;
	}

	public void setFlId(String flId) {
		this.flId = flId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fl_menu_id")
	public SysMenu getSysMenu() {
		return this.sysMenu;
	}

	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}

	@Column(name = "fl_name", length = 50)
	public String getFlName() {
		return this.flName;
	}

	public void setFlName(String flName) {
		this.flName = flName;
	}

	@Column(name = "fl_url")
	public String getFlUrl() {
		return this.flUrl;
	}

	public void setFlUrl(String flUrl) {
		this.flUrl = flUrl;
	}

	@Column(name = "fl_icon", length = 50)
	public String getFlIcon() {
		return this.flIcon;
	}

	public void setFlIcon(String flIcon) {
		this.flIcon = flIcon;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fl_create_time", nullable = false, length = 19)
	public Date getFlCreateTime() {
		return this.flCreateTime;
	}

	public void setFlCreateTime(Date flCreateTime) {
		this.flCreateTime = flCreateTime;
	}

	@Column(name = "fl_user_id", length = 32)
	public String getFlUserId() {
		return this.flUserId;
	}

	public void setFlUserId(String flUserId) {
		this.flUserId = flUserId;
	}

	@Column(name = "fl_spare_a")
	public String getFlSpareA() {
		return this.flSpareA;
	}

	public void setFlSpareA(String flSpareA) {
		this.flSpareA = flSpareA;
	}

	@Column(name = "fl_spare_b")
	public String getFlSpareB() {
		return this.flSpareB;
	}

	public void setFlSpareB(String flSpareB) {
		this.flSpareB = flSpareB;
	}

}