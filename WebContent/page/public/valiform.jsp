<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>表单验证</title>
<link href="${basePath }style/css/validform/dilatory.css" type="text/css" rel="stylesheet" />
<script type="text/javascript"	src="${basePath }jslib/validform/Validform_v4.0_min.js"></script>
</head>
<body>
<script type="text/javascript">
	var vform;
	$(function(){
	//$(".registerform").Validform();  //就这一行代码！;
	/**********************
	传入自定义datatype类型【方式一】;
	$.extend($.Datatype,{
		"z2-4" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,4}$/
	});
	**********************/
	
	vform=$(".registerform").Validform({
		tiptype:2,
		//showAllError:true,
		//postonce: true,
		dragonfly:true,
		ignoreHidden:true,
		ajaxPost:true,
		datatype:{//传入自定义datatype类型【方式二】;
			"z2-4" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,4}$/ ,
			"n+e1-20" : /^[a-zA-Z0-9_]{1,20}$/,
			// 1-10位的正整数
			"n1-10" : /^[0-9]*[1-9][0-9]{1,10}$/,
			"n1-4" : /^[0-9]*[1-9][0-9]{1,4}$/,
			"n+e1-50" :/^[a-zA-Z0-9_]{1,50}$/,
			"n+e1-100" :/^[a-zA-Z0-9_]{1,100}$/,
			"n+e1-300" :/^[a-zA-Z0-9_]{1,100}$/,
			"*+s1-50" :/^[\w\W\s\S]{1,50}$/,
			"*+s1-2000" :/^[\w\W\s\S]{1,2000}$/,
			"*+s1-4000" :/^[\w\W\s\S]{1,4000}$/,
			"*+s1-50000" :/^[\w\W\s\S]{1,50000}$/,
			// 验证IP地址
			"IP16" : /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
			//验证日期
			"date" : /^((^((1[8-9]\d{2})|([2-9]\d{3}))([-\\\._])(10|12|0?[13578])([-\\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\\\._])(11|0?[469])([-\\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\\\._])(0?2)([-\\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\\._])(0?2)([-\\\._])(29)$)|(^([3579][26]00)([-\\\._])(0?2)([-\\\._])(29)$)|(^([1][89][0][48])([-\\\._])(0?2)([-\\\._])(29)$)|(^([2-9][0-9][0][48])([-\\\._])(0?2)([-\\\._])(29)$)|(^([1][89][2468][048])([-\\\._])(0?2)([-\\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\\._])(0?2)([-\\\._])(29)$)|(^([1][89][13579][26])([-\\\._])(0?2)([-\\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\\._])(0?2)([-\\\._])(29)$))$/,
			"phone" : /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/
		},
		callback:function(data){
			$.Showmsg();
			//$.Hidemsg();
			if(data.success){
				$.messager.show({
					title : '提示',
					msg : '操作成功！'
				});
				closeWin();
				$('#dg').datagrid('reload');
			}else{
				$.messager.show({
					title : '提示',
					msg : '操作失败！'
				});
			}
			//返回数据data是json对象，{"info":"demo info","status":"y"}
			//info: 输出提示信息;
			//status: 返回提交数据的状态,是否提交成功。如可以用"y"表示提交成功，"n"表示提交失败，在ajax_post.php文件返回数据里自定字符，主要用在callback函数里根据该值执行相应的回调操作;
			//你也可以在ajax_post.php文件返回更多信息在这里获取，进行相应操作；
			//ajax遇到服务端错误时也会执行回调，这时的data是{ status:**, statusText:**, readyState:**, responseText:** }；
	 
			//这里执行回调操作;
			//注意：如果不是ajax方式提交表单，传入callback，这时data参数是当前表单对象，回调函数会在表单验证全部通过后执行，然后判断是否提交表单，如果callback里明确return false，则表单不会提交，如果return true或没有return，则会提交表单。
		}  
		
		/* beforeSubmit:function(curform){
			curform.ajaxSubmit({
				type:'post',
				url : basePath+"db/update",
				success : function(result) {
					result = $.parseJSON(result);
					if (result.success) {
						$.messager.show({
							title : '提示',
							msg : '更新成功！'
						});
						closeWin();
						$('#dg').datagrid('reload'); // reload the user data
						
					} else{
						$.messager.show({
							title : '提示',
							msg : '更新失败！'
						});
					} 
				}
			});
			return false;
			//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
			//这里明确return false的话表单将不会提交;	
		} */
		});
	

	});
</script>
</body>
</html>