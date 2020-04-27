<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>处理完成</title>
<%-- <jsp:include page="${basePath}page/public/share.jsp" /> --%>
<!-- <link rel="stylesheet" type="text/css" href="styles.css"/> -->
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
				<br /> <font style="font-size: 20pt; color: red">执行成功！</font><font
					style="font-size: 14pt" color=blue>&nbsp;&nbsp;稍等，系统正在返回................</font>
					<script type="text/javascript">
    
	  	setTimeout("sucesse()", "1000");	
	
	  	// 调用父窗口的方法.
	  	function sucesse() {
	  		var url = '${skipUrl}';
	  		var parent = '${parent}'; 		
	  		if(url==''){
	  			art.dialog.opener.location.reload();	  			
	  			if(parent != ''){
	  				window.parent.getSystemList();
	  			}
	  		}else{
	  			art.dialog.opener.location.href=url;
	  		}
			art.dialog.close();	
			//}
	  	}	
   
   </script></td>
			</tr>
		</table>
	</center>
</body>
</html>
