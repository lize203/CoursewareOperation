/**
 * 添加保存
 * @returns
 */
function savaAdd() {
	var floperationrole =$('#floperationrole').val();
	if(!floperationrole){
		$.messager.alert('提示','请选择业务角色类型 !');
		return;
	}
	
	$('#fm').form('submit', {
		url : basePath+"role/saveData",
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			result = $.parseJSON(result);
			if (result.code == 0) {
				$.messager.show({
					title : '提示',
					msg : "保存失败，请稍后重试。"
				});
			} else if(result.code == 1){
				$.messager.show({
					title : '提示',
					msg : "保存成功。"
				});
				closeWin();
				$('#dg').datagrid('reload'); // reload the user data
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