<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../public/header.jsp"%>
<title>${appTitle}</title>
<base href="${basePath}">  
<link rel="stylesheet" type="text/css" href="${basePath}style/css/smp.min.css">
<link rel="stylesheet"	href="${basePath}jslib/zTree/css/zTreeStyle/zTreeStyle.css"	type="text/css">
<link rel="stylesheet" type="text/css"	href="${basePath}style/css/top_menu.css">
<script type="text/javascript"	src="${basePath}jslib/zTree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript">	
	function logout(){
		$.messager.confirm('提示','确定要退出?',function(r){
			if (r){
				progressLoad();
				$.post( 'admin/logout', function(result) {
					if(result.success){
						progressClose();
						window.location.href='${basePath}index.jsp';
					}
				}, 'json');
			}
		});
	}
</script>
<style type="text/css">

.wil {
	//width: 30%;
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	background: #fff;
	font-size: 12px;
	padding: 6px 6px 6px 12px;
	color:#000;
}
.leftwalt {
	//width: 20%;
	background: #F5FAFA;
	color: black;
	border-left: 1px solid #C1DAD7;
	border-right: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	font-size: 12px;
	padding: 6px 6px 6px 6px;
}
.logo{
	width:80px
}

.drop-backlognum{
    font-size:9px !important;
	background-color: #1AB394;
	width:16px;
	height:15px;
	padding:0 2px;
	position: absolute;
	right:20px;
	top:13px;
	text-align: center;
	line-height: 15px;
}
span.backlognum{
	text-align:center;
	display:inline-block;
    position:absolute;
    left:15px;
    font-size:8px !important;
	background-color: #F27935;
	color:white !important;
	height:12px;
	padding:1px 2px;
	line-height: 12px;
	top:2px;
}
.empty{
   display:none !important;
}
.drop{
	width: 140px;
	position: absolute;
	z-index: 10;
	color:white;
	top:45px;
	background: url("${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/icons/modelmanage/bench/user-info.png");
}
.dropdown-userinfo{
	height: 90px;
	right:100px;
}
.dropdown-backlog{
    height: 50px;
	right:90px;
}
.drop ul{
	margin-top: 9px;
}
.dropdown-backlog li{
height:40px;
line-height: 40px;
position: relative;
padding-left: 40px;
background: url('${basePath}style/images/menu_top/backlognum.png') no-repeat 20px center;
margin-top: 10px;
}
.dropdown-userinfo li{
	height: 30px;
	line-height: 30px;	
	margin: 0 2px;
	padding-left: 20px !important;
}
.drop .hand:HOVER{
    background-color: #2893CA;
}
.dropdown-userinfo ul li:FIRST-CHILD, .change-password{
	padding: 5px 0;
}

.drop li img{
 	margin-right:10px;
 
}
.change-password{
  	border-top:1px #73BCE1 solid;

}
.backlogli{
	position:relative;
}
.hand{
cursor: pointer;
}

.tree-node-selected-for-third-menu {
  background: #b4ddf7;
  color: #000;
}


#btn_er{
	cursor: pointer;
}

#layoutWest  {
   	background: #f8f8f8;
   	width: 188px;
}
.layout-panel-west{
	overflow: visible;
	width: auto !important;
	/*min-width: 54px;*/
}
#layoutWest li.resume div.tree-node-hover{
 	background: #339ad4;
 	color: #fff;
}

#layoutWest li.resume span.tree-title{
	display:none;
}

#layoutWest li.resume span.tree-collapsed{
	 background: none;
	 visibility: hidden;

}

#layoutWest .tree li ,#layoutWest .tree-node li {
	border: none;
}

#drawer_window span.tree-title{
	
	display:block;
	padding: 0 0 0 25px;
	
}

#drawer_window span.tree-title:hover{
	cursor: pointer;
	background: #edf1f5;
}
#drawer_window .tree-icon{
	 margin: 0 10px 0 0;
}

.tree-node-selected-c{
	    border-left: 5px solid #339ad4;
	    background: #edf0f5 right 0 no-repeat ;
}

.tree-node-selected {
    border-top: 1px solid #f8f8f8;
    border-bottom: 1px solid #f8f8f8;
}
   

</style>
</head>
<body class="easyui-layout" id="ccc">
	<div class="dropdown-userinfo drop" style="right:95px;">
		<ul>
			<li><span style="padding-bottom: 3px;font-weight: bold;">您好，${loginUser.flname} </span></li>
			<li class='change-password hand'>
				<img src='jslib/jquery-easyui-1.3.2/themes/metro_AH/icons/modelmanage/bench/lock.png'/>
				<span  onclick="editpass()">修改密码<a style="cursor:hand"></a></span>
			</li>
						
		</ul>
	</div>
	<!-- <div class="dropdown-backlog drop">
		<ul>
			<li class='hand' onclick="backlogjump()">
				<span class='text'>待办事项</span><span class="drop-backlognum" id="dropbacklognum"></span>
			</li>
		</ul>
	</div> -->
	<!--头部栏-->
	<div data-options="region:'north'" style="height: 60px;">
		<div class="top-bar" id="topBar">
			<div class="logo" id="topMenuLeft">
				<img src="style/images/menu_top/logo2.jpg" alt="" />
			</div>
			<div id="logoText">${appTitle}</div>
			<div id="topMenu">
				<ul>
					<c:forEach items="${topMenus}" var="menu">
						<li style="max-width: 100px" <c:if test="${menu.id==activeMenu}">class="menu-item-selected"</c:if>>
							<a href="${path}${menu.url}"> <img	src="style/images/menu_top/${menu.icon}" alt="" />
								<span>${menu.name}</span>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div id="topMenuRight">
				<div id="logOut">
					<ul>
						<li class="user-info hand"><img src="style/images/menu_top/icon-user.png" alt=""  /> </li>
						<!-- <li class="backlogli hand">
							<img src='jslib/jquery-easyui-1.3.2/themes/metro_AH/icons/modelmanage/bench/ring.png' />
							<span class="backlognum" id="backlognum"></span>
						</li> -->
						<li class='hand'>
							<a onclick="logout()"><div style="width: 24px; height: 24px;"></div></a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!--end 头部栏-->

	<!--下栏-->
	<div id="main"	data-options="region:'south',split:true,collapsible:false" style="display:none; text-align: center;"></div>
	<!--end 下栏-->
	
	<!--左边栏-->
	
	<div data-options="region:'west',split:false" id="layoutWest" style="background: #f8f8f8 !important; border-right: none;">
			<input type="hidden" id="parentid" value="${activeMenu}">
            <input type="hidden" id="submenuId" value="${selectedSubmenu}">
            	
			<c:forEach items="${menus}" var="menu">
				<c:if test="${menu.flid==activeMenu}">
						<ul id='${menu.flid}' class="easyui-tree"></ul>
				</c:if>
			</c:forEach>
			<!--伸缩按钮-->
			<div id="btn_er_wrap" style="width: 26px;height: 26px;position: absolute;right:-13px;top:50%;margin-top: -13px;z-index: 99;display: none;">
				<!-- <div style="border-bottom: 1px solid #ededed; margin: 0 0 -23px 0;"></div> -->
				<div style="width: 26px;">
						<img id="btn_er" src="${basePath}style/images/resume.png" />
				</div>
			</div>
			
	</div>
<!--end 左边栏-->

<!--中间内容-->
	<div id="cc" data-options="region:'center'"	style="overflow: hidden; height: 0px;background: #eff7fd;">
		<iframe id="panels"  class="frme" frameBorder=0 scrolling=yes style="width: 100%; height: 100%; overflow: auto;"></iframe>
	</div>
<!--end 中间内容-->

    <script type="text/javascript" src="${basePath}page/main/MainForm.js"></script>
    <div id="editpassword" class="easyui-window" data-options=" modal:true,resizable:false,
    		closed:true,collapsible:false,minimizable:false,maximizable:false"  
   				style="height: 220px;width: 380px;padding: 20px; display:none;" >
			<table>
				<tr>
					<td class="leftwalt" style="border-top: 1px solid #C1DAD7;width: 100px;">旧&nbsp;&nbsp;密&nbsp;码：</td>
					<td class="wil" style="border-top: 1px solid #C1DAD7;width: 200px;">
					<input type="password" size="20" id="oldPassWord"/><font color="red">&nbsp;*</font>
					</td></tr><tr>
					<td class="leftwalt" >新&nbsp;&nbsp;密&nbsp;码：</td>
					<td class="wil" ><input size="20" type="password" id="newPassWord"  /><font color="red">&nbsp;*</font></td>
					</tr><tr>
					<td class="leftwalt" >再输一次：</td>
					<td class="wil" >
					<input size="20" type="password" id="again"/><font color="red">&nbsp;*</font>
					</td>
				</tr>
			</table>
			<div  align="center" style="padding-top: 12px">
			<a href="javascript:void(0)" class="easyui-linkbutton" id="saveBtn"
				onclick="savaPassWord()">保存</a> &nbsp;
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="javascript:$('#editpassword').dialog('close')">取消</a><br>&nbsp;
			</div>
	</div>
		<div id="drawer_window" style="display: block; z-index: 1000 ; width: 184px; position: absolute; border-right: 2px solid #dddddd; background: #f8f8f8;">
	</div>
</body>
</html>
