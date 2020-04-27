<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<%@include file="../../public/header.jsp"%>
<script type="text/javascript" src="${basePath}page/system/test/Test_Add.js"></script>
<title>用户管理</title>
</head>
<body>
	<form id="fmAdd" method="post">
		<div class="fitem">
			<label>昵称:</label>
			<input name="user.flname" class="easyui-validatebox" data-options="required:true">
		</div>
		<div class="fitem">
			<label>账户名:</label>
			<input name="user.flaccount" class="easyui-validatebox" data-options="required:true">
		</div>
		<div class="fitem">
			<label>密码:</label>
			<input name="user.flpassword" type="password" class="easyui-validatebox" data-options="required:true">
		</div>
		<div class="fitem">
			<label>电话号码:</label>
			<input name="user.fldisplay" type="hidden" value="1"/>
			<input name="user.fltelephone" id="fltelephoneadd" class="easyui-validatebox" data-options="required:true">
		</div>
		<!-- <div class="fitem">
			<label>角色名:</label>
			<input name="role.fllevel" type="hidden" value="0"/> 
			<input name="role.flname" class="easyui-validatebox" data-options="required:true"/>
		</div> -->
		<div class="fitem">
			<label>数据库名:</label>
			<input name="db.flname" class="easyui-validatebox" data-options="required:true"/>
		</div>
		<div style="float: right;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="testAdd()">保存</a> 
		</div>
	</form>
		
</body>
</html>
