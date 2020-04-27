<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/header.jsp"%>
<title>角色管理</title>
</head>
<body>
	<form id="fm" method="post">
		<div class="fitem">
			<label style="width: 85px;" >角色名:</label> 
			<input style="width: 185px;" name="flname" class="easyui-validatebox" data-options="required:true"/>
			<br/><br/>
			<label style="width: 85px;">业务角色类型:</label> 
			<select style="width: 189px;" id="floperationrole" name="floperationrole"  data-options="required:true">
			<option value="1">学生</option>
			<option value="2">老师</option>
			<option value="3">实验室管理员</option>
			<option value="4">系统管理员</option>
			</select>
		</div>
		<div style="float: right;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="savaAdd()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin()">取消</a>
		</div>
	</form>
</body>
</html>
