/**
 * 保存用户
 * @returns
 */
function testAdd() {
	var reg = /^(1[3458]\d{9})$/;
	$('#fmAdd').form('submit', {
		url : basePath+'test/testAdd',
		onSubmit : function() {
			if($(this).form('validate')){
				if (!reg.test($("#fltelephoneadd").val())) {
					$.messager.alert('提示','请输入正确的手机号码！');
			        return false;
			    }else{
			    	return true;
			    }
			}else{
				return false;
			}
		},
		success : function(result) {
			result = $.parseJSON(result);
			if (result.success) {
				$.messager.show({
					title : '提示',
					msg : '保存成功！'
				});
				$('#fmAdd').form('clear');
			} else{
				$.messager.show({
					title : '提示',
					msg : '保存失败！'
				});
			} 
		}
	});
}
