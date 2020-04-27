/**
 * 初始化加载
 */
$(function() {
	datagrid_init_pager("dg",'用户管理','user/getAdminData',editData,false);
	//初始化查询类型
	document.getElementById("queryType").options.add(new Option("按所有条件查询","0"));
	document.getElementById("queryType").options.add(new Option("按账号查询","1"));
	document.getElementById("queryType").options.add(new Option("按姓名查询","2"));
	document.getElementById("queryType").options.add(new Option("按电话查询","3"));
	
});


/**
 * 条件查询
 */
function select(){
	var query = $("#queryType").val();
	var number = $("#toolQueryConditions").val();
	datagrid_query_pager("dg",'user/query?number='+encodeURIComponent(encodeURIComponent(number))+'&query='+query,false);
}

/**
 * 重置按钮
 */
function again(){
	$("#queryType").val("0");
	$("#toolQueryConditions").val("");
}

/**
 * 批量删除用户
 */
function batchDel() {
	var row = $("#dg").datagrid('getChecked');
	var array = new Array();
	for (var i = 0; i < row.length; i++) {
		array.push(row[i].flid);
	}
	if (array.length > 0) {
		$.messager.confirm('提示', '确定删除该用户记录吗?', function(r) {
			if (r) {
				$.post(basePath+'user/batchDel?array='
						+ array.toString(), function(result) {
					if (result.code == 0) {
						$.messager.show({ 
							title : '提示',
							msg : '删除用户失败！'
						});
					} else {
						$.messager.show({
							title : '提示',
							msg : '删除用户成功！'
						});
						$('#dg').datagrid('reload'); // reload the user data
					}
				}, 'json');
			}
		});
	} else {
		$.messager.alert('提示','请至少选择一个用户!');
	}
}

/**
 * 添加用户
 */
function newData() {
	OpenFrame("win","page/system/user/User_Add.jsp",400,420,'新建用户');
}

/**
 * 双击view查看用户
 * @param index
 * @param value
 * @returns
 */
function editData(index, value) {
	$('#dg').datagrid('unselectAll');
	$('#dg').datagrid('selectRow',index);
	if (value) {
		rowData=value;
		OpenFrameLoad("win","page/system/user/User_View.jsp",400,420,'用户详细',init);
	}
}

function init_edit(){
	init();
	$('#checkRole').val(rowData.flroles);
	$("#rolesid").val(rowData.roleids);
}

/**
 *单个删除
 */
function destroyData() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$.messager.confirm('提示', '确定删除此记录?', function(r) {
			if (r) {
				$.post(basePath+'user/deleteData?id='
						+ row.flid, function(result) {
					if (result.code==0) {
						$.messager.show({ // show error message
							title : '提示',
							msg : '删除用户失败!'
						});
					} else {
						$.messager.show({ // show error message
							title : '提示',
							msg : '删除成功！'
						});
						closeWin(); 
						$('#dg').datagrid('reload'); // reload the user data
					}
				}, 'json');
			}
		});
	}
}

/**
 * 批量导入用户xls。
 */
function batchinput() {
	OpenFrame("win","page/system/user/User_Upload.jsp",600,200,'批量导入用户');
}