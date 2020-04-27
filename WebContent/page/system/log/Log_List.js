/**
 * 初始化加载
 */
$(function() {
	datagrid_init("dg",'用户操作日志列表','log/getData',editData,true);
	//初始化条件查询的下拉类型选项
	document.getElementById("queryType").options.add(new Option("操作用户","1"));
	document.getElementById("queryType").options.add(new Option("操作日期","2"));
	document.getElementById("queryType").options.add(new Option("操作类型","3"));
	//初始化按操作类型的下拉类型选项
	document.getElementById("opType").options.add(new Option("添加","添加"));
	document.getElementById("opType").options.add(new Option("删除","删除"));
	document.getElementById("opType").options.add(new Option("查看","查看"));
	document.getElementById("opType").options.add(new Option("修改","修改"));
	document.getElementById("opType").options.add(new Option("登录","登录"));
	document.getElementById("opType").options.add(new Option("其他","其他"));
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
	    	 $("#opType").show();
	    	 $('#date_end').attr("value","");
	    	 $('#toolQueryConditions').attr("value","");
	    	 $('#date_end').unbind('click');
	     }
	});
});

/**
 * 条件查询
 */
function select(){
	var query = $("#queryType").val();
	var number;
	if(query=="1"){
		number = $("#toolQueryConditions").val();
	}else if(query=="2"){
		number = $("#date_end").val();
	}else{
		number = $("#opType").val();
	}
	datagrid_query("dg",'log/query?number='+encodeURIComponent(encodeURIComponent(number))+'&query='+query,true);
}


/**
 * 重置按钮
 */
function again(){
	$("#queryType").val("0");
	$("#toolQueryConditions").val("");
	$('#date_end').hide();
	 $("#opType").hide();
	 $("#toolQueryConditions").show();
	 $('#date_end').attr("value","");
	 $("#opType").attr("value","查看");
	 $('#date_end').unbind('click');
}

/**
 * 双击view
 * @param index
 * @param value
 * @returns
 */
function editData(index,value) {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		rowData=row;
		OpenFrameLoad("win","page/system/log/Log_View.jsp",500,450,'日志详情',init);
	}
}