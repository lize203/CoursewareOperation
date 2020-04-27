<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>用户日志列表</title>
<%@include file="../../public/header.jsp"%>
</head>
<body>
	<form id="fm" method="post">
 		<div class="fitem">
			<label>异常产生时间:</label>
			<input name="date" readonly="readonly" size="40"/>
		</div>
		<div class="fitem">
			<label style="vertical-align: top">所属模块:</label>
			<textarea name="module" readonly="readonly" readonly="readonly"
				rows="5" cols="50"></textarea>
		</div>
		<div class="fitem">
			<label style="vertical-align: top">异常详细情况:</label>
			<textarea name="detail" readonly="readonly" readonly="readonly" rows="10"
				cols="50"></textarea>
		</div>
		
	</form>
</body>
</html>
