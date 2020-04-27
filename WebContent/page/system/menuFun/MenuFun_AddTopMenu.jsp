<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<title>菜单按钮管理</title>
<%@include file="../../public/header.jsp"%>
</head>
<body>
	<form id="fmAdd" method="post">
		<div class="fitem">
			<label style="width: 90px">名称:</label>
			<input id="name" name="name" class="easyui-validatebox" data-options="required:true">
		</div>
		<div class="fitem" id="menu_code">
			<label style="width: 90px">子菜单数:</label> 
			<select id="code" name="code">
                 <option value="1" id="one">一个</option>
                 <option value="n" id="many" selected="selected">多个</option>
            </select>
		</div>
		<div class="fitem" id="menu_isvisiable">
			<label style="width: 90px">状态:</label> 
			<select id="isvisiable" name="isvisiable" >
				 <option value="0" selected="selected">停用</option> 
                 <option value="1">启用</option> 
            </select>
		</div>
		<div class="fitem">
			<label style="width: 90px">图标:</label>
			<input id="icon" name="icon" class="easyui-validatebox" data-options="required:true">
			</div>
		<div class="fitem">
			<label style="width: 90px">排序序号:</label>
			<input id="order" name="order" class="easyui-numberbox" data-options="required:true"  min="0" max="999999999">
			</div>
		<input id="type" name="type" type="hidden" value="1">
		<input id="pid" name="pid" type="hidden">
		<input id="url" name="url" type="hidden" value="#">
		<div style="float: right; margin-top: 5px">
 			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-ok'" onclick="ersavaAdd()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'" onclick="closeWin()">取消</a>
		</div>
	</form>
</body>
</html>
