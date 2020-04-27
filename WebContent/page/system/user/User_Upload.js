/**
 * 点击导入
 * @returns {Boolean}
 */
function commitFile() {
	var uploadXls = $('#uploadXls').val();
	if (uploadXls == "") {
		$.messager.alert('提示', '请选择文件 ！');
		return false;
	}
	fileType = uploadXls.substr(uploadXls.lastIndexOf(".")).toLowerCase();
	if (fileType != ".xls") {
		$.messager.alert('提示', '请选择后缀名为xls文件 ！');
		return false;
	}
	//$('#subForm').attr('action', $('#subForm').attr('action')+'?upload='+uploadXls);
	$('#fm').submit();
	closeWin();
	$('#loading_Detail').dialog('open').dialog('setTitle', '正在导入文档');
}


/**
 * 下载模板
 */
function downDemo() {
	window.location.href = basePath+"user/downDemo";
}