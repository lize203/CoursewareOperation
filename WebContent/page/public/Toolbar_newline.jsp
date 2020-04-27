<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="toolbar" class="toolbar">
	<%-- <c:if test="${loginUser == null}">
	 “与toolbar.jsp相比，多了换行  20151124 杭州  lz”
		<script>
			art.dialog({
				content : '当前会话已超时，请重新登录',
				title : '提示',
				button : [ {
					name : '关闭',
					callback : function() {
						top.location = '../Login.jsp';
					}
				} ]
			});
		</script>
	</c:if> --%>
	<div class="oldwrap" style="float: left;">
		<c:forEach items="${functions}" var="fun">
			<c:if test="${fun.sysMenu.flid == menuid && fun.flfunctionname == '查询' && fun.flurl == '1'}">
	<!-- </div>
	<div style="float: right;"> -->
		<label>&nbsp;查询类型： </label> 
			<select id="queryType"style="width: 150px;"> </select>
		<label>&nbsp;查询条件： </label>
			<input type="text" name="queryConditions" id="toolQueryConditions" style="width: 150px" />
			<input id="date_end"  type="text" style="width:100px;display:none;" />
			<input id="date_start"  type="text" style="width:100px;display:none;" />
			<select id="opType" style="width:160px;display:none;">
			</select>
		</c:if>
		<c:if test="${fun.sysMenu.flid == menuid && fun.flfunctionname != '查看列表' && fun.flfunctionname != '模型系统'
		&& fun.flfunctionname != '查询类型' && fun.flfunctionname != '条件' && fun.flfunctionname != '生产库'
		&& fun.flfunctionname != '生产库用户' && fun.flfunctionname != '生产库表名' && fun.flurl == '1'}">
			&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="${fun.flicon}" plain="true"
				onclick="${fun.fleventfunction}">${fun.flfunctionname}</a>
		</c:if>
		<c:if test="${fun.sysMenu.flid == menuid && fun.flfunctionname == '查询' && fun.flurl == '1'}">
			&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true"
				onclick="again()">重置</a>
		</c:if>
		<c:if test="${fun.sysMenu.flid == menuid && fun.flfunctionname == '模型系统' && fun.flurl == '1'}">
			<label>&nbsp; | 系统过滤 </label>
			<input class="easyui-combobox" name="flname" id="toolPDMSystype" style="width: 250px" />
		</c:if>

		<c:if test="${fun.sysMenu.flid == menuid && fun.flfunctionname == '查询类型' && fun.flurl == '1'}">
			<label>&nbsp;${fun.flfunctionname}：</label>
			<select id="queryType" style="width: 200px;" ></select>
		</c:if>
		
		<c:if test="${fun.sysMenu.flid == menuid && fun.flfunctionname == '条件' && fun.flurl == '1'}">
			<label>&nbsp;${fun.flfunctionname}：</label>
			<input type="text"  name="queryConditions" id="toolQueryConditions" style="width: 160px"/>
		</c:if>
		


		</c:forEach>
		
		<!-- 筛选按钮 黏在一块，通用配置 -->
		<br/>
			<label>&nbsp;源库：</label>
			<input class="easyui-combobox" style="width:260px;" panelHeight="200px" id="dbsourceid" value="${dlcDatabaseSource}"/>
			<label>&nbsp;源库用户：</label>
			<input type="text" id="sourceuser" >
			<label>&nbsp;源库表名：</label>
			<input type="text" id="sourcetable" >
	</div>
	 
	
</div>
