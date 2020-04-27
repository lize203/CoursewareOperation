<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>菜单按钮管理</title>
<%@include file="../../public/header.jsp"%>
</head>
<body>
	<!--追加子菜单/按钮  -->
	<form id="fmAdd" method="post">
		<div class="fitem">
			<label style="width: 90px;">本级菜单名称:</label>
			<input id="parentText" readonly="readonly" class="easyui-validatebox"></input>
		</div>
		<div class="fitem">
			<label style="width: 90px">类型:</label>
			<select id="_type" name="_type" onchange="getType()">
                 <option value="1" selected="selected">菜单</option>
                 <option value="2" >按钮</option>
            </select>
		</div>
		<div class="fitem">
			<label style="width: 90px">名称:</label> 
			<input id="name" name="name" class="easyui-validatebox" data-options="required:true">
		</div>
		<div id="menu_show">
		<div class="fitem">
			<label style="width: 90px">请求URL:</label>
			<input id="url" name="url" class="easyui-validatebox" data-options="required:true">
		</div>
		<div class="fitem">
			<label style="width: 90px">状态:</label> 
			<select id="isvisiable" name="isvisiable" >
				 <option value="0">停用</option> 
                 <option value="1">启用</option> 
               </select>
		</div>
		</div>
		<div id="fun_show" style="display:none">
		<div class="fitem">
			<label style="width: 90px">按钮控制:</label>
				<select id="m_type" name="m_type">
                 	<option value="1" selected="selected">Toobar</option> 
                 	<option value="2" >Tag</option>   
                </select>
		</div>
		<div class="fitem" id="fun_event">
			<label style="width: 90px">JS函数:</label>
			<input id="eventfunction" name="eventfunction" class="easyui-validatebox" data-options="required:true">
		</div>
		</div>
		<div class="fitem">
			<label style="width: 90px">图标:</label>
			<input id="icon" name="icon" class="easyui-validatebox" data-options="required:true">
		</div>
		<div class="fitem">
			<label style="width: 90px">排序序号:</label>
			<input id="order" name="order" class="easyui-numberbox" data-options="required:true"  min="0" max="999999999">
		</div>
		<input id="pid" name="pid" type="hidden">
		<input id="id" name="id" type="hidden">
		<input id="type" name="type" type="hidden">
		<div style="float: right; margin-top: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="saveFunMenu()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin()">取消</a>
		</div>
	</form>
</body>
</html>
