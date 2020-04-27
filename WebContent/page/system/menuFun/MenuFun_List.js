/**
 * 加载菜单/按钮树
 */
$(function() {
	treegrid_init("dg",'菜单按钮管理','menufun/getData','id','name');
});

/**
 * 状态样式
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function status_init(value, row, index) {
	if (value == 0)
		return "<font style='color:grey'>停用</font>";
	else
		return '启用';
}

/**
 * url样式
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function url_init(value, row, index) {
	if (row.type == 2)
		return row.eventfunction;
	else
		return value;
}

/**
 * url样式
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function type_init(value, row, index) {
	if (value == 1) {
		return "<font style='color:green'>菜单</font>";
	} else if (value == 2) {
		return "<font style='color:blue'>按钮</font>";
	}
}

/**
 * 新增顶级菜单
 */
function addTopMenu() {
	OpenFrame("win","page/system/menuFun/MenuFun_AddTopMenu.jsp",480,350,'新增顶级菜单');
}


/**
 * 双击view
 */
function editData(index, value) {
	var row = $('#dg').treegrid('getSelected');
	if (row) {
		rowData=row;
		OpenFrameLoad("win","page/system/menuFun/MenuFun_View.jsp",480,420,'编辑详情',init_edit);
	} else {
		$.messager.show({
			title : '提示',
			msg : "请先选择需要编辑的节点"
		});
	}
}


/**
 * 编辑初始化样式
 */
function init_edit(){
	init();
	// 编辑前将所有文本和部分按钮禁用
	$("#fm").find("input").attr("disabled", true);
	$("#fm").find("select").attr("disabled", true);
	if (rowData.type == 1) {
		$('#win').dialog('setTitle', '菜单详情');
		$("#fm").find("#_type").val("菜单");
		$("#fm").find("#eventfunction").val(" ");
		$("#fm").find("#fun_show").hide();
		$("#fm").find("#menu_show").show();
	} else {
		$('#win').dialog('setTitle', '按钮详情');
		$("#fm").find("#url").val(" ");
		$("#fm").find("#_type").val("按钮");
		$("#fm").find("#menu_show").hide();
		$("#fm").find("#fun_show").show();
	}
	if(rowData.pid){
		var parentRow = $('#dg').treegrid('getParent', rowData.id);
		if (parentRow) {
			$("#fm").find('#parentText').val(parentRow.name);
		} else {
			parentRow = $('#dg').treegrid('find', rowData.pid);
			if (parentRow) {
				$("#fm").find('#parentText').val(parentRow.name);
			}
		}
	}
}



/**
 * 追加子菜单/按钮
 */
function addFunMenu(){
	var row = $("#dg").treegrid('getSelected');
	if(row){
		if(row.type==2){
			$.messager.alert('提示', '按钮不能追加！');
			return;
		}
		url = basePath+"menufun/hasFunOrMenu";
		$.post(url, {
			"id" : row.id
		}, function(result) {
			if (result.code==3) {
				$.messager.alert('提示', '该顶级菜单只能有一个子菜单！');
				return;
			}else{
				code=result.code;
				rowData=row;
				OpenFrameLoad("win","page/system/menuFun/MenuFun_AddFunMenu.jsp",480,400,'追加子菜单/按钮',init_add);
			}
		},'json');
	}else{
		$.messager.alert('提示', '请选中一个节点！');
		return;
	}
}



/**
 * 初始化样式
 */
function init_add(){
	if (code==2) {//按钮
		$("#fmAdd").find("#parentText").val(rowData.name);
		$("#fmAdd").find("#type").val("2");
		$("#fmAdd").find("#_type").val("2");
		$("#fmAdd").find("#url").val(" ");
		$("#fmAdd").find("#_type").attr("disabled", true);
		$("#fmAdd").find("#menu_show").hide();
		$("#fmAdd").find("#fun_show").show();
	} else if (code==1) {//菜单
		$("#fmAdd").find("#type").val("1");
		$("#fmAdd").find("#eventfunction").val(" ");
		$("#fmAdd").find("#parentText").val(rowData.name);
		$("#fmAdd").find("#_type").attr("disabled", true);		
	}else{
		$("#fmAdd").find("#type").val("1");
		$("#fmAdd").find("#eventfunction").val(" ");
		$("#fmAdd").find("#parentText").val(rowData.name);
	}
	$("#fmAdd").find("#pid").val(rowData.id);
}

/**
 * 删除菜单/按钮
 */
function deleteOne() {
	var row = $('#dg').treegrid('getSelected');
	if (row) {
		$.messager.confirm('提示', '确定删除此记录?', function(r) {
			if (r) {
				$.post(
					basePath+'menufun/deleteOne', 
					{
						id:row.id,
						type:row.type
					},
					function(result) {
						if(result.code==2){
							$.messager.show({
								title : '提示',
								msg : "请先删除该菜单下的所有子菜单/按钮！"
							});
						}else if (result.code==1) {
							$.messager.show({
								title : '提示',
								msg : "删除成功！"
							});
							$('#dg').treegrid('reload');
						} else {
							$.messager.show({
								title : '提示',
								msg : "删除失败！"
							});
						}
					}, 'json');
			}
		});
	}else{
		$.messager.alert('提示', '请选中一个节点！');
		return;
	}
}