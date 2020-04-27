/**
 * 保存
 * @returns
 */
function savaData() {
	$('#fm').form('submit', {
		url : basePath+"user/updateData",
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			result = $.parseJSON(result);
			if (result.code == 0) {
				$.messager.show({
					title : '提示',
					msg : '编辑失败！'
				});
			} else if (result.code == 1) {
				$.messager.show({
					title : '提示',
					msg : '编辑成功！'
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
 * 删除用户
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
 * 点击编辑
 */
function editUser(){
	//点击编辑按钮时将所有按钮和文本设置为可编辑状态
	$('#win').dialog('setTitle', '用户编辑');
	$("#win").find("input").attr("disabled",false);
	$("#edit_show").hide();
	$("#edit_hide").show();
	$("#role_choise").attr("style", "visibility:visible");
}