package com.zl.courseware.system.utils;

import java.beans.PropertyEditorSupport;

import org.springframework.web.util.JavaScriptUtils;


/** html和js输入过滤工具类放XSS攻击
 * 
 * @author lb */
public class StringEscapeEditor extends PropertyEditorSupport
{
	//private boolean escapeHTML;// 编码HTML
	private boolean escapeJavaScript; // 编码javascript
	
	
	public StringEscapeEditor()
	{
		super();
	}
	
	public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript)
	{
		super();
		//this.escapeHTML = escapeHTML;// 编码HTML
		this.escapeJavaScript = escapeJavaScript;
	}
	
	@Override
	public String getAsText()
	{
		Object value = getValue();
		return value != null ? value.toString() : "";
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException
	{
		if (text == null)
		{
			setValue(null);
		} else
		{
			String value = text;
			/*
			 * if (escapeHTML) { value = HtmlUtils.htmlEscape(value);// 编码HTML } */
			if (escapeJavaScript)
			{
				value = JavaScriptUtils.javaScriptEscape(value);
			}
			setValue(value);
		}
	}
}
