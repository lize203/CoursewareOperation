<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="../../../WEB-INF/priveliege.tld" prefix="privilege"%>
<head>
<title>菜单按钮管理</title>
<%@include file="../../public/header.jsp"%>
</head>
<body>
	<!-- 编辑菜单/按钮 -->
	<form id="fm" method="post">
			<div class="fitem">
				<label style="width: 90px;text-align:right">上级菜单名称:</label>
				<input id="parentText" class="easyui-validatebox" readonly="readonly"></input>
				<span id="pb_choise" style="visibility: hidden">
					<a class="easyui-linkbutton" onclick="openchoose()">选择</a>
				</span>
			</div>
			<div class="fitem">
				<label style="width: 90px;text-align:right">名称:</label> <input id="name" name="name"
					class="easyui-validatebox" data-options="required:true">
			</div>
			<div class="fitem">
				<label style="width: 90px;text-align:right">类型:</label>
				 <input id="_type"  class="easyui-validatebox" readonly="readonly" data-options="required:true" disabled="disabled">
			</div>
			<div id="menu_show">
			<div class="fitem">
				<label style="width: 90px;text-align:right">请求URL:</label>
				 <input id="url" name="url" class="easyui-validatebox" data-options="required:true">
			</div>
			<div class="fitem">
				<label style="width: 90px;text-align:right">状态:</label> 
				 <select id="isvisiable" name="isvisiable" >
				 		<option value="0">停用</option>
                 		<option value="1">启用</option> 
                 </select>
			</div>
			</div>
			<div id="fun_show" style="display: none">
			<div class="fitem">
				<label style="width: 90px;text-align:right">按钮控制:</label>
				 <select id="m_type" name="m_type">
                 		<option value="1">Toobar</option> 
                 		<option value="2">Tag</option>   
                 </select>
			</div>
			<div class="fitem" id="fun_event">
				<label style="width: 90px;text-align:right">JS函数:</label> 
				<input id="eventfunction" name="eventfunction" class="easyui-validatebox">
			</div>
			</div>
			<div class="fitem">
				<label style="width: 90px;text-align:right">图标:</label> <input id="icon"
					name="icon" class="easyui-validatebox" data-options="required:true">
			</div>
			<div class="fitem">
				<label style="width: 90px;text-align:right">排序序号:</label> <input id="order"
					name="order" class="easyui-numberbox" data-options="required:true"  min="0" max="999999999">
			</div>
			<input id="pid" name="pid" type="hidden">
			<input id="id" name="id" type="hidden">
			<input id="type" name="type" type="hidden">
			<div style="float: right; margin-top: 5px">
				<span id="edit_show">
				<privilege:enable operateID="${menuid}" functions="${functions}" code="editerData()">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="editBtn"
						data-options="iconCls:'icon-edit'" onclick="editerData()">修改</a>
				</privilege:enable>
				</span>
				<span id="edit_hide" style="display: none">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="savaBstn"
					data-options="iconCls:'icon-ok'" onclick="ersavaData()">保存</a>
				</span>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel'" onclick="closeWin()">取消</a>
			</div>
		</form>
		
		<!--添加选择上级菜单 -->
	<div id="dlg-search" class="easyui-window" title="上级菜单选择"
		style="width: 480px; height: 400px; padding: 10px"
		data-options="modal:true,closed:true,minimizable: false,collapsible:false">
		<%-- <table id="dg-search" class="easyui-treegrid" style="height: 300px;"
			data-options="onDblClickRow:function(event){chooseParent();},
				url: '${basePath}menufun/getParentMenu?flid=${flid}',fitColumns:true,singleSelect:true,
				idField: 'id',
				treeField: 'name'">
			<thead>
				<tr>
					<th data-options="field:'name',width:100,">名称</th>
				</tr>
			</thead>
		</table> --%>
		<table id="dg-search" class="easyui-treegrid" style="height: 300px;"
			data-options="onDblClickRow:function(event){chooseParent();},
				fitColumns:true,singleSelect:true,
				idField: 'id',
				treeField: 'name'">
			<thead>
				<tr>
					<th data-options="field:'name',width:100,">名称</th>
				</tr>
			</thead>
		</table>
		<div style="margin-top: 5px">
			<a class="easyui-linkbutton" style="float: right; margin-left: 3px"
				data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#dlg-search').dialog('close')">取消</a>
			<a class="easyui-linkbutton" style="float: right; margin-left: 3px"
				data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="chooseParent()">选择</a>
		</div>
	</div>
</body>
</html>
