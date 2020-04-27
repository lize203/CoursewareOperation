/**
 * 追加子菜单/按钮点击保存
 */
function saveFunMenu(){
	$('#type').val($('#_type').val());
	$('#fmAdd').form('submit', {
		url : basePath+'menufun/addChild',
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
					msg : '追加失败'
				});
			}
		}
	});
}

/**
 * 保存顶级菜单
 */
function ersavaAdd() {
	$('#fmAdd').form('submit', {
		url : basePath+'menufun/addTopMenu',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var json=JSON.parse(result);
			if (json.success){
				$.messager.show({
					title : '提示',
					msg : "保存成功"
				});
				closeWin();
				$('#dg').treegrid('reload');
				
			} else {
				$.messager.show({
					title : '提示',
					msg : '添加失败'
				});
			}
		}
	});
}


/**
 * 追加子菜单/按钮的类型
 */
function getType(){
	var t=$("#fmAdd").find("#_type").val();
	if (t==2) {//按钮
		$("#fmAdd").find("#url").val(" ");
		$("#fmAdd").find("#menu_show").hide();
		$("#fmAdd").find("#fun_show").show();
	} else{//菜单
		$("#fmAdd").find("#eventfunction").val(" ");
		$("#fmAdd").find("#menu_show").hide();
		$("#fmAdd").find("#fun_show").show();
	}
	$("#fmAdd").find("#type").val(t);
}
