<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统异常日志列表</title>
<%@include file="../../public/header.jsp"%>
<script type="text/javascript" src="${basePath}page/system/log/exceptionlog.js"></script>
<script type="text/javascript">
$(function() {
	
	
	
	$('#dg').datagrid({
		        nowrap: true, // 就会把数据显示在 一行里
		        striped: true,  //隔行换色
		        collapsible :false,//是否可折叠的  
		        fit: true,//自动大小  
		        fitColumns:true,
		        pageSize:10,
		        pageList:pageGroup,
		        onDblClickRow:editData,
		        loadMsg : '正在加载数据...',
		        url:"${basePath}exceptionlog/getData", 
		        remoteSort:false,  
		        singleSelect:false,//是否单选  
		        pagination:true,//分页控件  
		        rownumbers:true,//行号  
		        frozenColumns:[[  
		            {field:'ck',checkbox:true}
		        ]],
		        columns : [ [ {
					field : 'id',
					title : 'ID',
					width : fixWidth(0.05),
					sortable:false,
					hidden:true
				},{
					field : 'module',
					title : '所属模块管理',
					width : fixWidth(0.06),
					sortable:false
				},{
					field : 'detail',
					title : '异常信息',
					width : fixWidth(0.05),
					sortable:false
				},
					/* {
					field : 'operator',
					title : '操作人',
					width : fixWidth(0.05),
					sortable:false
				}, */{
					field : 'date',
					title : '发生时间',
					width : fixWidth(0.06),
					sortable:true,
	                formatter: function(value,row,index){
	                    if(value==null)
	                        return '';
	                    return newDate(value);
	                }
				}
		         
				] ],
				toolbar : '#toolbar'
			}); 
	 
	
	
	//初始化条件查询的下拉类型选项
	document.getElementById("queryType").options.add(new Option("所属模块管理","1"));
	document.getElementById("queryType").options.add(new Option("异常发生时间","2"));
	/* document.getElementById("queryType").options.add(new Option("操作人","3")); */
	//初始化查询条件事件
	$('#queryType').bind("change", function() {
	     if(this.value=="1"){
	    	 $('#date_end').hide();
	    	 $("#opType").hide();
	    	 $("#toolQueryConditions").show();
	    	 $('#date_end').attr("value","");
	    	 $("#opType").attr("value","查看");
	    	 $('#date_end').unbind('click');
	     }else if(this.value=="2"){
	    	 $("#toolQueryConditions").hide();
	    	 $("#opType").hide();
	    	 $('#date_end').show();
	    	 $('#toolQueryConditions').attr("value","");
	    	 $("#opType").attr("value","查看");
	    	 $('#date_end').bind('click',function(){WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'});});
	     }else if(this.value=="3"){
	    	 $('#date_end').hide();
	    	 $("#toolQueryConditions").hide();
	    	 $("#opType").hide();
	    	  $("#toolQueryConditions").show();
	    	 $('#date_end').attr("value","");
	    	 $('#toolQueryConditions').attr("value","");
	    	 $('#date_end').unbind('click');
	     }	     
	     
	     });
});

</script>
<body>
	<%@include file="../../public/Toolbar.jsp"%>
	<table id="dg">
      
    </table>
    <div id="win"></div>
</body>
</html>
