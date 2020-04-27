<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page isELIgnored="false"%>
<html>
<head>
<title>处理失败</title>
<%@include file="header.jsp"%>
<script src="${basePath}jslib/artdialog/artDialog.source.js"></script>
<script src="${basePath}jslib/artdialog/iframeTools.source.js"></script>
<link href="${basePath}jslib/artdialog/skins/blue.css" rel="stylesheet" type="text/css" />
</head>
<body topmargin="1" leftmargin="3" bottommargin="5" rightmargin="0">
	<center>
		<table>
			<tr>
				<td valign="top"><br />
				<br /> <font style="font-size: 20pt; color: red">执行失败！</font><font
					style="font-size: 14pt" color=blue>&nbsp;&nbsp;稍等，系统正在返回................</font>
					<script type="text/javascript">
    
	  	setTimeout("sucesse()", "1000");	
	
	  	// 调用父窗口的方法.
	  	function sucesse() {
			art.dialog.close();
	  	}	
   
   </script></td>
			</tr>
		</table>
	</center>
</body>
</html>






















