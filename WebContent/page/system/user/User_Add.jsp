<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<%@include file="../../public/header.jsp"%>
<title>用户管理</title>
</head>
<body>
	<form id="fmAdd" method="post">
		<div class="fitem">
			<label>账号:</label>
			<input name="flaccount" class="easyui-validatebox" data-options="required:true">
		</div>
		<div class="fitem">
			<label>姓名:</label>
			<input name="flname" class="easyui-validatebox" data-options="required:true"/>
		</div>
		<div class="fitem">
			<label>密码:</label>
			<input name="flpassword" type="password" class="easyui-validatebox" data-options="required:true">
		</div>
		<div class="fitem">
			<label>电话号码:</label>
			<input name="fltelephone" id="fltelephoneadd" class="easyui-validatebox"  data-options="required:true,validType:'mobile'">
		</div>
		<div class="fitem">
			<!-- <input type="hidden" name="roles" id="rolesidAdd" />
			<label>角色:</label>
			<input  id="checkRoleAdd"  data-options="required:true" readonly="readonly" class="easyui-validatebox"> -->
			<input type="hidden" name="roleids" id="rolesid"/>
			<label>角色:</label>
			<input id="checkRole" name="flroles" data-options="required:true"  readonly="readonly" class="easyui-validatebox"/>
			<a class="easyui-linkbutton" onclick="openChangeRoles(true)" href="javascript:void(0)">选择</a>
		</div>
		<div style="float: right;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="savaAdd()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();">取消</a>
		</div>
	</form>
		
	<div id="dlg-search" class="easyui-window"
		data-options="modal:true,closed:true,minimizable: false,collapsible:false"
		style="width: 450px; height: 360px; padding: 10px 20px">
		<div style="height: 250px;">
			<table id="searchlist" class="easyui-datagrid"
				style="width: 400px; height: 250px" 
				data-options="fitColumns:true,rownumbers:true,checkOnSelect:true,
				selectOnCheck:true,singleSelect:false,multiSort:true,remoteSort:false,
				method:'get'">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:'true'"></th>
						<th data-options="field:'flname',width:100,sortable:'ture'">角色名</th>
					</tr>
				</thead>
			</table>
		</div>
		<br />
		<a class="easyui-linkbutton" style="float: right;" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
			onclick="closeRoles()">取消</a>
		<a class="easyui-linkbutton" style="float: right;" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
			onclick="checkRoles(true)">选择</a>
	</div>
</body>
</html>
