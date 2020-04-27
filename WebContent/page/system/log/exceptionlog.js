/**
 * 初始化加载
 */


/**
 * 条件查询
 */
function select(){
	var query = $("#queryType").val();
	var parm;
	if(query=="1"){
		number = $("#toolQueryConditions").val();
		if(number){
		parm="module="+number;
		}
		
	}else if(query=="2"){
		number = $("#date_end").val();
		if(number){
		parm="datetime="+encodeURIComponent(number);
		}
		
		
	}
	else {
		number = $("#toolQueryConditions").val();
	}
	datagrid_query("dg",'exceptionlog/query?number='+encodeURIComponent(encodeURIComponent(number))+'&query='+query,true);
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

		rowData=value;
		OpenFrameLoad("win","page/system/log/exception_view.jsp",650,500,'异常信息详情',init);
	
	
	
	
}
//宽度
	function fixWidth(percent)  
	{  
	    return document.body.clientWidth * percent ; //这里你可以自己做调整  
	} 
/**
 * 批量删除
 * @param index
 * @param value
 * @returns
 */
function batchdelete(){
	var row = $("#dg").datagrid('getChecked');
	var array = new Array();
	for (var i = 0; i < row.length; i++) {
		array.push(row[i].id);
		
	}
	if (array.length > 0) {
		$.messager.confirm('提示', '确定删除该异常记录吗?', function(r) {
			if (r) {
				$.post(basePath+'exceptionlog/batchDel.action?array='
						+ array.toString(), function(result) {
					if (result.code == 0) {
						$.messager.show({ 
							title : '提示',
							msg : '删除异常信息失败！'
						});
					} else {
						$.messager.show({
							title : '提示',
							msg : '删除异常信息成功！'
						});
						$('#dg').datagrid('reload'); // reload the user data
					}
				}, 'json');
			}
		});
	} else {
		$.messager.alert('提示','请至少选择一条记录!');
	}
	}