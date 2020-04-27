package com.zl.courseware.system.utils;

public enum RoleEnum
{
	ONE(1,"学生"),TWO(2,"老师"),THREE(3,"实验室管理员"),FOUR(4,"系统管理员");
	private int i;
	private String name;
	
	public int getI()
    {
    	return i;
    }
	public void setI(int i)
    {
    	this.i = i;
    }
	private RoleEnum(int i, String name)
    {
	    this.i = i;
	    this.name = name;
    }
	private RoleEnum(String name)
    {
	    this.name = name;
    }
	public String getName()
    {
    	return name;
    }
	public void setName(String name)
    {
    	this.name = name;
    }
	
	public void getRoleEnumName(int i){
		
	}
	
}
