<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>工作台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../public/header.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
$(document).ready(function(){ 
});

function type(value, row, index) {
	if (value == "1") {
		return "模型变更";
	} else {
		return "接口变更";
	}
}

function go(row,value){
	if(value.type=="2"){
		window.location.href = basePath+"interfaceChange/showWorOrder.action?flid="+value.flid;
	}
	else{
		window.location.href =basePath+ "changeQuery/showWorOrder.action?flid="+value.flid;
	}
}
</script>
</head>
<body>
<div id="p" class="easyui-panel" style="padding:0px;background-color: #EFF7FD;"  fit="true">
	
	<div class="easyui-layout"  fit="true">
		
      
        <div data-options="region:'center'" title="待办列表" style="width: 520px;height: 440px">
            <table id="col" class="easyui-datagrid" fit="true" 
					data-options="onDblClickRow:go,singleSelect:true,url:'${basePath}bench/getChange',collapsible:true,loadFilter : pagerFilter" 
					pagination="true" 
					rownumbers="true" fitColumns="true" singleSelect="true">
					<thead>
						<tr>
							<th field="flid" hidden="true"> </th>
							<th field="flchangenumber" sortable="true" align="left" width="18">变更编号</th>
							<th field="flchangetitle" sortable="true" align="left" width="55">变更标题</th>
							<th sortable="true" field="flname" align="left" width="20">创建人</th>
							<th sortable="true" field="flApplyType" align="left" width="20" formatter="type">工单类型</th>
						</tr>
					</thead>
				</table>
        </div>
    </div>
</div>
</body>
</html>

