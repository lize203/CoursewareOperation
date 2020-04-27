<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<body topmargin="5" leftmargin="3" bottommargin="5" rightmargin="0">
	<center>
		<br />
		<br />
		<br /> <font color="red">操作失败.系统正在返回!<br></font>
		<script>
     	function comeback() {
			window.location.href = 'javascript:history.go(-1)';
     	};
     	setTimeout("comeback()", "1500");
     </script>
	</center>
</body>
</html>