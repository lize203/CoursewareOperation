package com.zl.courseware.system.utils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
* xml model
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "i")
@XmlType(propOrder = { "name", "path", "ii" })
public class SortIndexModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name = "";
	private String path = "";
	private List<String> ii = new LinkedList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getIi() {
		return ii;
	}

	public void setIi(List<String> ii) {
		this.ii = ii;
	}

	@Override
	public String toString() {
		return "ExperimentIndex [name=" + name + ", path=" + path + ", ii=" + ii + "]";
	}

}
