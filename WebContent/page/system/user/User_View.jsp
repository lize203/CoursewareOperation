<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="../../../WEB-INF/priveliege.tld" prefix="privilege"%> 
<html>
<head>
<%@include file="../../public/header.jsp"%>
<title>用户管理</title>
</head>
<body>
	<form id="fm" method="post">
		<input type="hidden" id="flid" name="flid"/>
		<div class="fitem">
			<label>帐号:</label>
			<input name="flaccount" id="flaccount" disabled="disabled" readonly="readonly"
				class="easyui-validatebox" data-options="required:true"/> 
		</div>			
		<div class="fitem">
			<label>姓名:</label>
			<input name="flname" disabled="disabled" class="easyui-validatebox" data-options="required:true"/>
		</div>
		<div class="fitem">
			<label>密码:</label>
			<input name="flpassword" disabled="disabled" type="password" class="easyui-validatebox" data-options="required:true"/>
		</div>
		<div class="fitem">
			<label>电话号码:</label>
			<input name="fltelephone" id="fltelephone" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:'mobile'"/>
		</div>
		<div class="fitem">
			<input type="hidden" name="roleids" id="rolesid" disabled="disabled"/>
			<label>角色:</label>
			<input id="checkRole" name="flroles" data-options="required:true" disabled="disabled" readonly="readonly" class="easyui-validatebox"/>
			<span id="role_choise" style="visibility: hidden">
			<a class="easyui-linkbutton" onclick="openChangeRoles(false)" id="checkRoleBtn"  href="javascript:void(0)">选择</a>
			</span>
		</div>
		<div  style="float: right;">
			<span id="edit_show">
				<privilege:enable operateID="${menuid}" functions="${functions}" code="editUser()">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="editUserBtn"
						data-options="iconCls:'icon-edit'" onclick="editUser()">修改</a>
				</privilege:enable>
			</span>
			<span id="edit_hide" style="display: none">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="savaBtn"
						data-options="iconCls:'icon-ok'" onclick="savaData()">保存</a>
			</span>
			<privilege:enable operateID="${menuid}" functions="${functions}" code="destroyData()">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="destroyBtn"
						data-options="iconCls:'icon-remove'" onclick="destroyData()">删除</a>
			</privilege:enable>
			<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel'" onclick="closeWin()">取消</a>
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
		<br/> 
		<a class="easyui-linkbutton" style="float: right;"
			data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
			onclick="closeRoles()">取消</a>
		<a	class="easyui-linkbutton" style="float: right;"
			data-options="iconCls:'icon-ok'" href="javascript:void(0)"
			onclick="checkRoles()">选择</a>
	</div>
</body>
</html>
