<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);

 	String titlename = "课件分配系统";
	String loginImage = "url("+basePath+"style/images/login_center_gd.jpg)";
	String loginBackGround = "url("+basePath+"style/images/loginbg.jpg)";
	//项目名称
	request.setAttribute("appTitle", titlename);
	request.setAttribute("loginImage", loginImage);
	request.setAttribute("loginBackGround", loginBackGround);
%>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<link rel="stylesheet" type="text/css" href="${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/css/easyui.min.css">
<link rel="stylesheet" type="text/css" href="${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/css/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/css/rightui.css">
<link rel="stylesheet" type="text/css" href="${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/css/productui.min.css">
<link rel="stylesheet" type="text/css" href="${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/css/tooltip.css">
<link rel="stylesheet" type="text/css" href="${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/icon.css">
<link rel="stylesheet" type="text/css" href="${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/tabs.css">

<link rel="stylesheet" type="text/css" href="${basePath}style/css/progress.css">
<link rel="stylesheet" type="text/css" href="${basePath}style/css/css.css">
<link rel="stylesheet" type="text/css" href="${basePath}jslib/artdialog/skins/opera.css" />
<link rel="stylesheet" type="text/css" href="${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/css/resetui.css">

<script type="text/javascript" src="${basePath}jslib/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${basePath}jslib/Highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${basePath}jslib/jquery-easyui-1.3.2/jquery.easyui.js"></script>
<script type="text/javascript" src="${basePath}jslib/jquery-easyui-1.3.2/JShepatch.js"></script>
<script type="text/javascript" src="${basePath}jslib/jquery-easyui-1.3.2/plugins/jquery.tooltip.js"></script>
<script type="text/javascript" src="${basePath}jslib/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${basePath}jslib/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${basePath}jslib/js/underscore-min.js"></script>
<script type="text/javascript" src="${basePath}jslib/ext/extJquery.js"></script>
<script type="text/javascript" src="${basePath}jslib/ext/extEasyUI.js"></script>
<script type="text/javascript" src="${basePath}jslib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}jslib/util/jquery.form.js"></script>

<script type="text/javascript" src="${basePath}jslib/js/share.js"></script>
<script type="text/javascript" src="${basePath}jslib/artdialog/artDialog.source.js"></script>
<script type="text/javascript" src="${basePath}jslib/artdialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${basePath}jslib/sncjs/js/snc.js"></script>

<script>var basePath = '${basePath}';//项目根路劲</script>
<script type="text/javascript" src="${basePath}jslib/js/public.js"></script>
<script type="text/javascript" src="${basePath}jslib/util/progress.js"></script>

