<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>用户日志列表</title>
<%@include file="../../public/header.jsp"%>
</head>
<body>
	<form id="fm" method="post">
 		<div class="fitem">
			<label>操作时间:</label>
			<input name="time" readonly="readonly" size="40"/>
		</div>
		<div class="fitem">
			<label style="vertical-align: top">操作描述:</label>
			<textarea name="description" readonly="readonly" readonly="readonly"
				rows="4" cols="40"></textarea>
		</div>
		<div class="fitem">
			<label style="vertical-align: top">操作请求:</label>
			<textarea name="url" readonly="readonly" readonly="readonly" rows="4"
				cols="40"></textarea>
		</div>
		<div class="fitem">
			<label style="vertical-align: top">请求参数:</label>
			<textarea name="parameter" readonly="readonly" readonly="readonly"
				rows="6" cols="40"></textarea>
		</div>
	</form>
</body>
</html>
