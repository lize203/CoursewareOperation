/**
 * 初始化加载
 */



function initteachmgr() {
	
	datagrid_init_pager("dg",'教案管理','courseware/getData',editData,false);
	
};
jQuery(function($){initteachmgr()});

$(function(){
	document.getElementById("queryType").options.add(new Option("按所有条件查询","*"));
	document.getElementById("queryType").options.add(new Option("按课件名称查询","flname"));
	document.getElementById("queryType").options.add(new Option("按课件说明查询","flexplain"));
	document.getElementById("queryType").options.add(new Option("按资源磁盘路径查询","flsrcpath"));
//	document.getElementById("queryType").options.add(new Option("按备注查询","flremark"));
	document.getElementById("queryType").options.add(new Option("按上传者查询","username"));
//	document.getElementById("queryType").options.add(new Option("按时间查询","createtime"));
	});

/**
 * 条件查询
 */
function select(){
	var query = $("#queryType").val();
	var number = $("#toolQueryConditions").val();
	datagrid_query_pager("dg",'courseware/query?number='+encodeURIComponent(encodeURIComponent(number))+'&query='+query,false);
}

/**
 * 重置按钮
 */
function again(){
	$("#queryType").val("0");
	$("#toolQueryConditions").val("");
	initteachmgr();
}

/**
 * 批量删除课件
 */
function batchDel() {
	var userID = $("#userID").val();
	var userAccount = $("#userAccount").val();
	var row = $("#dg").datagrid('getChecked');
	var array = new Array();
	var filepaths = new Array();
	for (var i = 0; i < row.length; i++) {
		if(row[i].fluserid != userID && userAccount != "admin"){
			alert("您没有权限删除\""+row[i].flname+"\"课件");
		}else{
			array.push(row[i].flid);
			filepaths.push(row[i].flsrcpath);
		}
	}
	if (array.length > 0) {
		$.messager.confirm('提示', '确定删除该课件记录吗?', function(r) {
			if (r) {
				$.post(basePath+'courseware/batchDel?array='
						+ array.toString(), function(result) {
					if (result.code == 0) {
						$.messager.show({ 
							title : '提示',
							msg : '删除课件失败,请进行解绑再次尝试删除！(绑定如：课程,实验模板)'
						});
					} else {
						$.messager.show({
							title : '提示',
							msg : '删除课件成功！'
						});
						$('#dg').datagrid('reload'); // reload the user data
						$('#dg').datagrid('unselectAll');
						$("#dg").datagrid('uncheckAll');
					}
				}, 'json');
			}
		});
	} else {
		if(navigator.userAgent.toLowerCase().indexOf("chrome") == -1){
			alert('请至少选择一个课件!');
		}else{
			$.messager.alert('提示','请至少选择一个课件!');
		}
	}
}

/**
 * 添加课件
 */
function newData() {
	OpenFrame("win","page/teachinfo/courseware/courseware_add.jsp",450,350,'教案上传');
}

/**
 * 双击view查看课件
 * @param index
 * @param value
 * @returns
 */
function editData(index, value) {
	$('#dg').datagrid('unselectAll');
	$('#dg').datagrid('selectRow',index);
	if (value) {
		rowData=value;
		OpenFrameLoad("win","page/teachinfo/courseware/courseware_view.jsp",450,520,'教案查看',init);
	}
//	$('#file').val(rowData.flsrcpath);
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
				$.post(basePath+'courseware/deleteData?id='
						+ row.flid+'&flsrcpath='+row.flsrcpath, function(result) {
					if (result.code==0) {
						$.messager.show({ // show error message
							title : '提示',
							msg : '删除课件失败,请进行解绑再次尝试删除！!'
						});
					} else {
						$.messager.show({ // show error message
							title : '提示',
							msg : '删除成功！'
						});
						closeWin(); 
						$('#dg').datagrid('reload'); // reload the user data
						$('#dg').datagrid('unselectAll');
						$("#dg").datagrid('uncheckAll');
					}
				}, 'json');
			}
		});
	}
}


function downloadFileZip() {
	var rows = $('#dg').datagrid('getSelections');
	var ids = [];
    if (rows.length > 0) {
//        $.messager.confirm('确认', '您是否要同时下载多个文件？',
//        function (r) {
//            if (r) {
            	var flids = "";
            	for(var i=0;i<rows.length;i++){
            		if(i==rows.length-1){
            			flids = flids+rows[i].flid;
            		}else{
            			flids = flids+rows[i].flid+",";
            		}
            	}
            	window.location.href = basePath+"courseware/downloadFile?flids="+flids;
//            }
//        });
        
    } 
//    else {
//        $.messager.show({ title: '提示', msg: '请勾选要下载的文件！' });
//    }
}


 
//刷新
function reload(){
	window.location.reload();
}
//宽度
function fixWidth(percent)  
{  
    return document.body.clientWidth * percent ; //这里你可以自己做调整  
} 