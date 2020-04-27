<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>Error Page</title>
<!-- <script type="text/javascript">
	if (top.location != self.location){
		top.location=self.location;     
	}
</script> -->
<style>
body {
	background-color: #0367a2;
}

a {
	color: #fff;
}

a:hover {
	color: #eee;
	text-shadow: 0 0 3px #fff;
}

.container {
	margin: 15% auto;
	padding: 10px;
	/* width: 700px; 
	color: #fff;
	text-align: center;*/
}

.sad-face {
	font-size: 2.5em;
	padding-right: 10px;
}

.error-code {
	font-size: 3em;
	font-style: italic;
	padding-right: 10px;
}
</style>
</head>
<body>
	<div class="container">
		<p>${msg}</p>
		<script type="text/javascript" charset="utf-8">
			setTimeout("parent.location.href='${ctx}/admin/index'",2000);
		</script>	
	</div>
</body>
</html>


