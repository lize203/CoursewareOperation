<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="padding-top:5px;padding-left:45px;">
	<span ><h3>空间容量管理</h3></span>
</div>
<div id="toolbar" class="toolbar" style="padding-top:10px;padding-left:42px;">
	<div style="float: left;">
		<label>&nbsp;库： </label>
		<select name="dbName" class="easyui-combobox" id="dbName" editable="false"  style="width: 140px;"></select> 
		<label>&nbsp;用户： </label>
		<select name="owner" class="easyui-combobox" id="owner" editable="false"  style="width: 140px;"></select>
		<label>&nbsp;类型： </label>
		<select name="type" class="easyui-combobox" id="type" editable="false"  style="width: 140px;"></select>
		<span id="edit_tableSpace" style="display: none">
			<select name="tableSpaceName" id="tableSpaceName" class="easyui-combobox" editable="false"  style="width: 140px;"></select>
		</span>
		<span id="edit_tableName" style="display: none" >
			<input id="tableName" name="tableName" class="easyui-validatebox" style="width: 170px;height:25px;" data-options="">
		</span>
		
		<!-- <label>&nbsp;起始时间： </label>
		<input id="startTime" name="startTime" type="text" class="easyui-datebox" style="width: 110px;"></input>
		<input id="endTime" name="endTime" type="text" class="easyui-datebox" style="width: 110px;"></input> -->
		
		&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
		onclick="queryByConditions()">查询</a>
		&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-reload" plain="true"
			onclick="again()">重置</a>
	</div>
</div>
