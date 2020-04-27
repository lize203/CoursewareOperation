<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="toolbar" class="toolbar">
	<div style="float: left;">
		<c:forEach items="${functions}" var="fun">
			<c:if test="${fun.sysMenu.flid == menuid && fun.flfunctionname == '新建业务数据' && fun.flurl == '1'}">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="destroyBtn"
						data-options="iconCls:'icon-add'" onclick="createLifeCycle()">新建业务数据</a>
			</c:if>
			<c:if test="${fun.sysMenu.flid == menuid && fun.flfunctionname == '查询' && fun.flurl == '1'}">
				<label>&nbsp;业务类型： </label>
				<select name="businessType" class="easyui-combobox" id="businessType" editable="false"  style="width: 180px;"></select> 
				<label>&nbsp;数据类型： </label>
				<select name="dataType" class="easyui-combobox" id="dataType" editable="false"  style="width: 180px;"></select> 
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
				onclick="queryByConditions()">查询</a>
			</c:if>
			<c:if test="${fun.sysMenu.flid == menuid && fun.flfunctionname == '查询' && fun.flurl == '1'}">
				&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-reload" plain="true"
					onclick="again()">重置</a>
			</c:if>
		</c:forEach>
	</div>
</div>
