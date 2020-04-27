<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.zl.courseware.system.model.system.SysUser" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../public/header.jsp"%>
<script type="text/javascript" src="${basePath}jslib/util/progress.js"></script>
<script type="text/javascript" src="${basePath}page/teachinfo/courseware/courseware_list.js"></script>
<script type="text/javascript" src="${basePath}page/teachinfo/courseware/courseware_add.js"></script>
<script type="text/javascript" src="${basePath}page/teachinfo/courseware/courseware_view.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}style/css/progress.css">
<script type="text/javascript" src="${basePath}jslib/util/validatestr.js"></script>
<%
	SysUser user=(SysUser)request.getSession().getAttribute("loginUser");
%>

</head>
<body>
	<%@include file="../../public/Toolbar.jsp"%>
	<table id="dg">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:'true'"></th>
				<th data-options="field:'flname',width:50,align:'center',sortable:'ture'">课件名称</th>
				<th data-options="field:'flexplain',width:30,align:'center',sortable:'ture'">课件说明</th>
				<th data-options="field:'flsrcpath',width:50,align:'center',sortable:'ture'">资源磁盘路径</th>
				<th data-options="field:'username',width:50,align:'center',sortable:'ture'">上传者</th>
<!-- 				<th data-options="field:'flremark',width:50,align:'center',sortable:'ture'">备注</th> -->
				<th data-options="field:'createtime',width:50,align:'center',sortable:'ture'">创建时间</th>
<!-- 				<th data-options="field:'modifytime',width:50,align:'center',sortable:'ture'">修改时间</th> -->
			</tr>
		</thead>
	</table>
	<div id="win"></div>
	<input type="hidden" id="userID" value="<%= user.getFlid()%>">
	<input type="hidden" id="userAccount" value="<%= user.getFlaccount()%>">
</body>
</html>
