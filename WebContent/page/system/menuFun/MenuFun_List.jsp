<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>菜单按钮管理</title>
<%@include file="../../public/header.jsp"%>
<script type="text/javascript"
	src="${basePath}jslib/js/datagrid-groupview.js"></script>
<script type="text/javascript"
	src="${basePath}page/system/menuFun/MenuFun_Add.js"></script>
<script type="text/javascript"
	src="${basePath}page/system/menuFun/MenuFun_View.js"></script>
<script type="text/javascript"
	src="${basePath}page/system/menuFun/MenuFun_List.js"></script>
</head>
<body>
	<%@include file="../../public/Toolbar.jsp"%>
	<table id="dg">
		<thead>
            <tr>
                <th data-options="field:'name',width:100,">名称</th>
                <th data-options="field:'type',width:20,align:'center',formatter:type_init">类型</th>
                <th data-options="field:'url',width:50,align:'center',formatter:url_init">URL/JS函数</th>
                <th data-options="field:'icon',width:30,align:'center',">图标</th>
                <th data-options="field:'isvisiable',width:20,align:'center',formatter:status_init">状态</th>
            </tr>
        </thead>
	</table>
	<div id="win"></div>
</body>
</html>