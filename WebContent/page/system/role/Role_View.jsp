<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="../../../WEB-INF/priveliege.tld" prefix="privilege"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/header.jsp"%>
<title>角色管理</title>
</head>
<body>
	<form id="fm" method="post">
		<input type="hidden" id="flid" name="flid"/>
		<input type="hidden" id="flcomment" name="flcomment"/>
		<div class="fitem">
			<label style="width: 85px;" >角色名:</label> 
			<input style="width: 185px;" name="flname" class="easyui-validatebox" data-options="required:true" disabled="disabled"/>
			<br/><br/>
			<label style="width: 85px;" >业务角色类型:</label> 
			<select style="width: 189px;" id="floperationrole" name="floperationrole"  data-options="required:true" disabled="disabled">
			<option value="1">学生</option>
			<option value="2">老师</option>
			<option value="3">实验室管理员</option>
			<option value="4">系统管理员</option>
			</select>
			
		</div>
		<div style="float: right;">
			<span id="edit_show">
			<privilege:enable operateID="${menuid}" functions="${functions}" code="editRole()">
				<a href="javascript:void(0)" class="easyui-linkbutton"
						id="editRoleBtn" data-options="iconCls:'icon-edit'" onclick="editRole()">修改</a>
			</privilege:enable>
			</span>
			<span id="edit_hide" style="display: none">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="savaBtn"
					data-options="iconCls:'icon-ok'" onclick="savaData()">保存</a>
			</span>
			<privilege:enable operateID="${menuid}" functions="${functions}" code="destroyData()"> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
						id="destroyBtn" data-options="iconCls:'icon-remove'" onclick="destroyData()">删除</a>
			</privilege:enable>
			<privilege:enable operateID="${menuid}" functions="${functions}" code="editRoles()">
				<a href="javascript:void(0)" class="easyui-linkbutton"
						id="editRolesBtn" data-options="iconCls:'icon-edit'" onclick="editRoles()">分配权限</a>
			</privilege:enable>
				<a	href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel'" onclick="closeWin()">取消</a>
			</div>
	</form>

	<div id="dlg-roles" class="easyui-window"
		style="width: 380px; height: auto; overflow: auto; padding: 10px 20px"
		data-options="modal:true,closed:true,minimizable: false,collapsible:false,buttons:'#dlg-roles-buttons'">

		<div class="ftitle">
			为【<label style="color: red;" id="rolename"></label>】角色分配权限
		</div>
		<div style="height: 400px; border: 1px solid Silver; overflow: auto;" id="divTree"></div>
		<div id="dlg-roles-buttons" style="float: right; margin-top: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" 
				onclick="openAllNodes()">展开</a> 
			<a href="javascript:void(0)"
				class="easyui-linkbutton"  onclick="closeAllNodes()">收起</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="saveRoleAuthority()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'"
				onclick="javascript:$('#dlg-roles').dialog('close')">取消</a>
		</div>
	</div>
</body>
</html>
