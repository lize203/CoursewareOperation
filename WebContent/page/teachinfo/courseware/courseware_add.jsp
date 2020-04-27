<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<%@include file="../../public/header.jsp"%>
<title>教案上传</title>
</head>
<body>
<%-- ${basePath}courseware/uploadFileZip --%>
	<form id="uploadPic" method="post" enctype="multipart/form-data"  action="#" >
<!-- 		<div class="fitem"> -->
<!-- 				<label style="width: 110px;">课件说明:</label> -->
<!-- 				<input name="flexplain" class="easyui-validatebox" > -->
<!-- 		</div> -->
		<div class="fitem">
				<label style="width: 110px;">教案说明:</label>
				<textarea name="flexplain" id="flexplain" class="easyui-validatebox" style="height:100px;"  cols="3" rows="5" onmouseout="checkExplainTextarea()"/>
		</div>
		
		<div class="fitem">
			<label style="width: 110px;"><font style="color: red;font-size: 12;">*</font>文件:</label>
			<input type="file" style="width: 200px;" id="file" name="file" />
		</div>
		<div class="fitem">
			<label style="color: red;font-size: 12;width: 110px;">提示:</label>
			<font style="color: red;font-size: 12;">* 上传教案格式windows系统请选择zip，</font><br/>
			<font style="color: red;font-size: 12;margin-left: 125px;">linux系统请选择tar.gz</font>
		</div>
		
		<div class="fitem" style="display:none">
			<label style="width: 110px;"><font style="color: red;font-size: 12;">*</font>课件名称:</label>
			<input id="flname" name="flname" class="easyui-validatebox" data-options="required:true">
		</div> 
		<div class="wrapper"  style="display:none">
			<div class="load-bar" style="margin-top:40px;">  
				<div class="load-bar-inner" data-loading="0"> <span id="counter"></span> </div> 
			</div>  
			<h1>上传中。 请等待... </h1>  
		</div>
		<div style="float: right;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="savaAdd()">保存</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();">取消</a>
		</div>
	</form>
</body>
</html>
