<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link type="text/css" href="../style/css/login.css" rel="stylesheet" />
<link type="text/css" href="../style/css/shsncmvc.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="../style/css/smp.css">
<script type="text/javascript" src="../jslib/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../jslib/js/jquery-ui-1.7.2.custom.min.js"></script>
<%@include file="public/header.jsp"%>
<title>欢迎登陆</title>
<script>

	if (top.location != self.location){     
		top.location=self.location;     
	}

	var sessionInfo_userId = '${loginUser.flid}';
	if (sessionInfo_userId) {//如果已经登录,直接跳转到主页
		window.location.href='${basePath}admin/index';
	}
	
	
	function login(){
		progressLoad();
		$.post('${basePath}admin/login', {
			"flaccount" : $('#name').val(),
			"flpassword" : $('#password').val(),
			"yzm" : $('#yzm').val()
		}, function(result){
	    	progressClose();
	    	if (result.success) {
	    		window.location.href='${basePath}admin/index';
	    	}else{
	    		$.messager.show({
	    			title:'提示',
	    			msg:'<div><div class="light-tip icon-tip"></div><div>'+result.msg+'</div></div>',
	    		});
	    	}
		}, 'json');
	}

</script>

</head>
<body
	style="background:no-repeat ${loginBackGround} center; background-attachment: fixed;background-color: #0170e4; overflow: hidden;">
	<div class="login_bgcolor">
		<div class="loginbg">
			<div class="login_center" id="loginContent"
				style="background: ${loginImage} no-repeat 158px 170px">

				<form method="post" id="loginform">
					<table class="loginbox" style="width:285;border:0">
						<tr>
							<td class="boxright"><input id="name" type="text" value="admin"
								name="flaccount"     
								style="border-radius: 5px; border: 1px solid #a8e4ed"
								tabindex="2" /></td>
						</tr>
						<tr>
							<td class="boxright"><input id="password" type="password"  value="admin"
								name="flpassword"    
								style="border-radius: 5px; border: 1px solid #a8e4ed"
								tabindex="3"></td>
						</tr>
						<!-- <tr>
                        	<td>
                        		<input id="yzm" type="text"  name="yzm" size="6" style="vertical-align:top;height:25px;border-radius: 1px;  border: 1px solid #a8e4ed">
                            	<img title="点击更换"  id="code" onclick="refresh();" src="${basePath}main/getCode"> 
                            </td>
                   		 </tr> 
                   		 -->
						<tr>
							<td><input class="btnLogin" onclick="login()" type="button" value="登 录"
								name="enter"></td>
						</tr>
					</table>
				</form>

				<div style="position: absolute; top: 530px; left: 50%; width: 300px;margin-left: -150px;text-align: center;">
					<span style="color: #ffffff;"></span>
				</div>
			</div>
		</div>
	</div>
</body>
<script>

	$(function() {
		$('#loginform').form({
		    url:'${basePath}admin/login',
		    onSubmit : function() {
		    	progressLoad();
				/* var isValid = $(this).form('validate');
				 if(!isValid){
					progressClose();
				}  */
				
				return true;
			},
		    success:function(result){
		    	//alert(result.success);
		    	result = $.parseJSON(result);
		    	progressClose();
		    	if (result.success) {
		    		window.location.href='${basePath}admin/index';
		    	}else{
		    		$.messager.show({
		    			title:'提示',
		    			msg:'<div><div class="light-tip icon-tip"></div><div>'+result.msg+'</div></div>',
		    			//showType:'show'
		    		});
		    	}
		    }
		});
	});
	
	function submitForm(){
		$('#loginform').submit();
	}
	
	function refresh() {
    	/* var obj=document.getElementById("code");
    	obj.src = "mainForm/mainForm!getCode.action?m="+Math.random(); */
    	$("#code").attr("src","${basePath}main/getCode?m="+Math.random());
        
    }
	
	//添加Enter快捷键
	document.onkeyup=dologin;
	function dologin()
	{
		if(event.keyCode==13)
		{
			login();
		}
	}
	
	/* 清除按钮 */
	/* function clearForm(){
		$('#loginform').form('clear');
	} */
	
</script>
</html>
