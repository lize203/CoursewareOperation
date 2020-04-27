<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.zl.courseware.system.model.system.SysUser" %>
<%@ taglib uri="../../../WEB-INF/priveliege.tld" prefix="privilege"%> 
<html>
<head>
<%@include file="../../public/header.jsp"%>
<%
	SysUser user=(SysUser)request.getSession().getAttribute("loginUser"); 
%>
<title>课件修改</title>
</head>
<body>
	<form  id="fm" method="post"  enctype="multipart/form-data"  action="courseware/uploadCoursewareUpdate" >
 		<input type="hidden" id="flid" name="flid"/> <!--form表单里面如果出现两个相同的ID 。则后台对象会有重复拼接数据。2,2 -->
		<input type="hidden" id="fluserid" name="fluserid"/> <!--form表单里面如果出现两个相同的ID 。则后台对象会有重复拼接数据。2,2 -->
 		<input type="hidden" id="userID" value="<%= user.getFlid()%>">
 		<input type="hidden" id="userAccount" value="<%= user.getFlaccount()%>">
		<div class="fitem">
			<label style="width: 110px;"><font style="color: red;font-size: 12;">*</font>课件名称:</label>
			<input name="flname" id="flname" class="easyui-validatebox" readonly="readonly" data-options="required:false" disabled="disabled" >
		</div>
		<div class="fitem">
			<label style="width: 110px;">课件说明:</label>
<!-- 			<input name="flexplain" id="flexplain" class="easyui-validatebox"  data-options="required:false" disabled="disabled"/> -->
			<textarea name="flexplain" id="flexplain" data-options="required:false" disabled="disabled"  class="easyui-validatebox" style="height:100px;"  cols="3" rows="5" onmouseout="checkExplainTextarea()"/>
			
		</div>
		<div class="fitem">
			<label style="width: 110px;"><font style="color: red;font-size: 12;">*</font>资源磁盘路径:</label>
			<input name="flsrcpath"  id="flsrcpath"  class="easyui-validatebox" readonly="readonly"  data-options="required:true"  disabled="disabled" >
		</div>
		<div class="fitem">
			<label style="width: 110px;"><font style="color: red;font-size: 12;">*</font>上传者:</label>
			<input name="username"  id="username" class="easyui-validatebox" data-options="required:true"  disabled="disabled" readonly="readonly"/>
		</div>
<!-- 		<div class="fitem"> -->
<!-- 			<label>备注:</label> -->
<!-- 			<input name="flremark" id="flremark" class="easyui-validatebox" data-options="required:false" disabled="disabled" /> -->
<!-- 		</div> -->
		<div class="fitem">
			<label style="width: 110px;"><font style="color: red;font-size: 12;">*</font>创建时间:</label>
			<input name="createtime"  class="easyui-validatebox" data-options="required:true"  disabled="disabled" readonly="readonly"/>
		</div>
		<%--<div class="fitem">
			<label style="width: 110px;">文件:</label>
			<input type="file" style="width: 200px;"  disabled="disabled" id="file" name="file"/>
		</div>
		<div class="fitem">
			<label style="color: red; font-size: 12">提示:</label>
			<font style="color: red; font-size: 12">* 重新上传课件格式请选择tar.gz非空文件.</font>
		</div>
		<div class="wrapper"  style="display:none">
		<!-- 	 style="display:none" -->
			<div class="load-bar" style="margin-top:40px;">  
				<div class="load-bar-inner" data-loading="0"> <span id="counter"></span> </div> 
			</div>  
			<h1>上传中。 请等待... </h1>  
		</div>--%>
		
		<div  style="float: right;">
			<%--<span id="edit_show">
				<privilege:enable operateID="${menuid}" functions="${functions}" code="editRow()">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="editRowBtn"
						data-options="iconCls:'icon-edit'" onclick="editRow()">修改</a>
				</privilege:enable>
			</span>--%>
			<span id="edit_hide" style="display: none">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="savaBtn"
						data-options="iconCls:'icon-ok'" onclick="savaData()">保存</a>
			</span>
			<privilege:enable operateID="${menuid}" functions="${functions}" code="destroyData()">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="destroyBtn"
						data-options="iconCls:'icon-remove'" onclick="destroyData()">删除</a>
			</privilege:enable>
			<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel'" onclick="closeWin()">取消</a>
		</div>
	</form>
</body>
</html>
