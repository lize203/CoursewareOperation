var tree;
/**
 * 保存权限
 */
function saveRoleAuthority() {
	var check = tree.getAllCheckedBranches();
	var row = $('#dg').datagrid('getSelected');
	var roleid = row.flid;
	$.post(basePath+'role/saveRoleAuthority', {
		"check" : check,
		"roleid" : roleid
	}, function(result) {
		
		result = $.parseJSON(result);
		if (result.code == 0) {
			$.messager.show({
				title : '提示',
				msg : '保存失败！'
			});
		}else if (result.code == 3) {
			$.messager.confirm('提示', '你修改了你自身的用户权限，系统建议重新加载所有菜单！确定重新加载？', function(r) {
				if (r) {
					window.parent.location = basePath+"admin/reload";
				}
			});
		} else {
			
			$.messager.show({
				title : '提示',
				msg : '保存成功！'
			});
			$('#dlg-roles').dialog('close'); 
		}
		
	});
}

/**
 * 点击分配权限
 */
function editRoles() {
	var row = $('#dg').datagrid('getSelected');
	if (row == null) {
		return;
	}
	$("#rolename").text(row.flname);
	$.ajax({
        url : basePath+'role/queryRolesMenu',
        data : {
        	roleid : row.flid
        },
        dataType: "json",
        success : function(rs) {
        	$('#divTree').html("");
        	tree = new dhtmlXTreeObject("divTree", "100%", "100%", 0);
        	tree.setSkin('dhx_skyblue');
        	tree.setImagePath(basePath+"jslib/dhtmltree/imgs/dhxtree_skyblue/");
        	tree.enableCheckBoxes(1);
        	tree.enableTreeImages(0);
        	tree.enableSmartXMLParsing(true);
        	tree.loadXMLString(rs.msg);
        	tree.setOnLoadingEnd(changeStateCheckboxes);
        	$('#dlg-roles').dialog('open').dialog('setTitle', '分配权限');
        }
    });
}

/**
 * 当点击任一节点时，激活该方法
 */
function changeStateCheckboxes() {
	tree.enableThreeStateCheckboxes(true);
}

/**
 * 收缩节点
 */
function closeAllNodes() {
	tree.closeAllItems();
}

/**
 * 展开节点
 */
function openAllNodes() {
	tree.openAllItems(0);
}

/**
 * 编辑保存
 * @returns
 */
function savaData() {
	var floperationrole =$('#floperationrole').val();
	if(!floperationrole){
		$.messager.alert('提示','请选择业务角色类型 !');
		return;
	}
	
	$('#fm').form('submit', {
		url : basePath+"role/updateData",
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			result = $.parseJSON(result);
			if (result.code==0) {
				$.messager.show({
					title : '提示',
					msg : "保存失败，请稍后重试。"
				});
			} else if (result.code==1){
				$.messager.show({
					title : '提示',
					msg : "保存成功。"
				});
				closeWin();
				$('#dg').datagrid('reload');
			}else if(result.code == 4){
				$.messager.alert('提示',"该业务角色类型的已存在，请重新操作。（学生、老师、实验室管理员的类型，只能存在一个角色名）");
			}else{
				$.messager.show({
					title : '提示',
					msg : "角色名已经存在，请重新输入！"
				});
			}
		}
	});
}

/**
 * 删除
 */
function destroyData() {
	var flid=$('#flid').val();
	var flcomment=$('#flcomment').val();
	$.messager.confirm('提示', '确定删除此记录?', function(r) {
		if (r) {
			if(flcomment=="admin"){
				$.messager.show({ // show error message
					title : '提示',
					msg : '系统管理员不能删除！'
				});
			}else{
				$.post(basePath+'role/deleteData?id='+ flid, function(result) {
					if (result.code==0) {
						$.messager.show({ // show error message
							title : '提示',
							msg : '删除失败！'
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
		}
	});
}


/**
 * 点击编辑按钮
 */
function editRole(){
	//点击编辑按钮后让所有按钮盒所有文本处于可编辑状态以便操作
	$('#win').dialog('setTitle', '角色编辑');
	$("#win").find("input").attr("disabled",false);
	$("#win").find("select").attr("disabled",false);
	$("#edit_hide").show();
	$("#edit_show").hide();
}