<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/header.jsp"%>
<script type="text/javascript"
	src="${basePath}page/system/role/Role_List.js"></script>
<script type="text/javascript"
	src="${basePath}page/system/role/Role_Add.js"></script>
<script type="text/javascript"
	src="${basePath}page/system/role/Role_View.js"></script>
<script type="text/javascript"
	src="${basePath}jslib/dhtmltree/js/dhtmlxcommon.js"></script>
<script type="text/javascript"
	src="${basePath}jslib/dhtmltree/js/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="${basePath}jslib/dhtmltree/css/dhtmlXTree.css">
<title>角色管理</title>
</head>
<body>
	<%@include file="../../public/Toolbar.jsp"%>
	<table id="dg">
		<thead>
			<tr>
				<th align="center" data-options="field:'flname',width:100,align:'center',sortable:'ture'">角色名</th>
				<th align="center" data-options="field:'floperationroleStr',width:100,align:'center',sortable:'ture'">业务角色类型</th>
			</tr>
		</thead>
	</table>
	<div id="win" style="padding: 10px 20px"></div>
</body>
</html>