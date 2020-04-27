/**
 * 点击修改按钮
 */
function editerData() {
	// 点击编辑按钮让所有按钮和文本处于可编辑状态
	$("#fm").find("a").attr("disabled", false);
	$("#fm").find("select").attr("disabled", false);
	$("#fm").find("input").attr("disabled", false);
	$("#edit_hide").show();
	$("#edit_show").hide();
	$("#pb_choise").attr("style", "visibility:visible");
	if (rowData.type == 1) {
		$('#win').dialog('setTitle', '菜单编辑');
	} else {
		$('#win').dialog('setTitle', '按钮编辑');
	}
}


/**
 * 编辑状态点击保存按钮
 */
function ersavaData() {
	$('#fm').form('submit', {
		url : basePath+'menufun/savaMenuFun',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var json=JSON.parse(result);
			if (json.success) {
				$.messager.show({
					title : '提示',
					msg : "保存成功"
				});
				closeWin();
				$('#dg').treegrid('reload');
			} else {
				$.messager.show({
					title : '提示',
					msg : '更新失败'
				});
			}
		}
	});
}


/**
 * 选择上级业务
 */
function chooseParent() {
	var row = $('#dg-search').treegrid('getSelected');
		/* 判断窗口为新建还是编辑 */
	if (row) {
		if(row.name=="无"){
			$('#parentText').val("");
		}else{
			$('#parentText').val(row.name);
		}
		$('#pid').val(row.id);
	} else {
		$('#parentText').val("");
		$('#pid').val("");
	}
	$('#dlg-search').window('close');
}

/**
 * 获取上级业务列表
 */
function openchoose() {
	var id=$("#id").val();
	$('#dlg-search').window('open');
	$('#dg-search').treegrid({url:basePath+"menufun/getParentMenu?flid="+id});
}