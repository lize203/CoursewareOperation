/**
 * 获取角色列表——添加页面和编辑页面供用
 */
function openChangeRoles(isCreate) {
	var id = '';
	if (isCreate == false) {
		id = $('#flid').val();
	}
	var getTimestamp=new Date().getTime();
	$('#searchlist').datagrid({
		url : basePath+'user/getRoleData?id=' + id+"&newtime="+getTimestamp,
		onLoadSuccess : function(data) {
			if (isCreate)
				return;
			if (data) {
				$.each(data.rows, function(index, item) {
					if (item.checked) {
						$('#searchlist').datagrid('checkRow', index);
					}
				});
			}
		}
	});
	$('#searchlist').datagrid('reload');
	$('#dlg-search').dialog('open').dialog('setTitle', '用户角色列表');
}


/**
 * 选择角色——添加页面和编辑页面供用
 * @param index
 * @param value
 */
function checkRoles(isCreate) {
	var row = $("#searchlist").datagrid('getChecked');
	var rolename = "";
	var roleflid = "";
	for ( var i = 0; i < row.length; i++) {
		rolename += rolename==""?row[i].flname:( ","+row[i].flname );
		roleflid += roleflid==""?row[i].flid: (","+row[i].flid);
	}
	$("#checkRole").val(rolename);
	$("#rolesid").val(roleflid);
	/*if (isCreate) {
		$("#checkRoleAdd").val(rolename);
		$("#rolesidAdd").val(roleflid);
	} else {
		$("#checkRole").val(rolename);
		$("#rolesid").val(roleflid);
	}*/
	$('#dlg-search').dialog('close');
}


/**
 * 保存用户
 * @returns
 */
function savaAdd() {
	$('#fmAdd').form('submit', {
		url : basePath+'user/saveData',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			result = $.parseJSON(result);
			if (result.code == 0) {
				$.messager.show({
					title : '提示',
					msg : '保存失败！'
				});
			} else if (result.code == 1) {
				$.messager.show({
					title : '提示',
					msg : '保存成功！'
				});
				closeWin();
				$('#dg').datagrid('reload'); // reload the user data
			} else {
				$.messager.show({
					title : '提示',
					msg : '该帐号已存在，请重新输入 !'
				});				
			}
		}
	});
}

/**
 * 关闭角色选择列表
 */
function closeRoles(){
	$('#dlg-search').dialog('close');
}