<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户日志列表</title>
<%@include file="../../public/header.jsp"%>
<script type="text/javascript" src="${basePath}page/system/log/Log_List.js"></script>
<body>
	<%@include file="../../public/Toolbar.jsp"%>
	<table id="dg">
       <thead>
            <tr>
                <th data-options="field:'description',width:90,align:'center',sortable:'true'">描述</th>
                <th data-options="field:'type',width:20,align:'center',sortable:'true'">类型</th>
                <th data-options="field:'operator',width:30,align:'center',sortable:'true'">操作用户</th>
                <th data-options="field:'ip',width:30,align:'center',sortable:'true'">IP</th>
                <th data-options="field:'time',width:30,align:'center',sortable:'true'">操作时间</th>
            </tr>
        </thead>
    </table>
    <div id="win"></div>
</body>
</html>
