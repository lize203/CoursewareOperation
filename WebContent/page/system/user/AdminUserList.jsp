<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/header.jsp"%>
<script type="text/javascript" src="${basePath}page/system/user/AdminUserList.js"></script>
<script type="text/javascript" src="${basePath}page/system/user/User_Upload.js"></script>
<script type="text/javascript" src="${basePath}page/system/user/User_Add.js"></script>
<script type="text/javascript" src="${basePath}page/system/user/User_View.js"></script>
<title>管理员用户管理</title>
</head>
<body>
	<%@include file="../../public/Toolbar.jsp"%>
	<table id="dg">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:'true'"></th>
				<th data-options="field:'flaccount',width:50,align:'center',sortable:'ture'">帐号</th>
				<th data-options="field:'flname',width:50,align:'center',sortable:'ture'">姓名</th>
				<th data-options="field:'fltelephone',width:50,align:'center',sortable:'ture'">电话号码</th>
			</tr>
		</thead>
	</table>
	<div id="win"></div>
</body>
</html>
