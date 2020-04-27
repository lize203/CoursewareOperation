<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<jsp:include page="header.jsp" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
<script>
    function comeback() {
		window.location.href = '${basePath}${backUrl}';
    }
    function reload_batins(){
    	window.location.href = "../../userManagement/downDemo";
    }
    setTimeout("comeback()", "2000");
</script>
<body topmargin="5" leftmargin="3" bottommargin="5" rightmargin="0">
	<center>
		<br />
		<br />
		<br />
		<div style="">
			<div style="width: 360px;">
				<font color="red">${errorDesc}<br></font>
			</div>
			<br /> <br /> <br />
		</div>
	</center>
</body>
</html>







