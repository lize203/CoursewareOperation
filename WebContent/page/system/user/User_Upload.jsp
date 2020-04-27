<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<%@include file="../../public/header.jsp"%>
<title>用户管理</title>
</head>
<body>
	<form id="fm" method="post" enctype="multipart/form-data" action="${basePath}user/parseXls">
		<div class="fitem">
			<font style="color: red; font-size: 12">导入excel文件格式请按模板填写. </font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img alt="下载模板" title="下载模板" src="${basePath}style/images/icon/icon_download.gif"> &nbsp;
			<a href="javascript:downDemo();">批量导入用户模板.xls</a>
		</div>
		<div class="fitem">
			<label>选择文件:</label>
			<input type="file" style="width: 400px;" id="uploadXls" name="upload" />
		</div>
		<div style="float: left; margin-left: 250px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="commitFile()">确&nbsp;定</a>
		</div>
	</form>
	<div id="loading_Detail" class="easyui-window"
		data-options="modal:true,closed:true,minimizable: false,collapsible:false"
		style="width: 600px; height: 180px; padding: 16px;">
		<table>
			<tr>
				<td height="15px;" colspan="3"></td>
			</tr>
			<tr style="text-align: center;">
				<td width="150px;"></td>
				<td align="center" height="20px;">
					<img alt="" src="${basePath}style/images/icon/5-121204193R5-50.gif">
				</td>
				<td>
					<font style="font-size: 12">&nbsp;&nbsp; 正在导入用户信息，请稍候...... </font>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
