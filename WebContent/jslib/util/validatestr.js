
/**
 * 校验 flremark
 * @author helix chen
 * @returns {Boolean}
 */
function checkRemarkTextarea(){
	var flremark = $("#flremark").val();
	if(flremark.length > 500)
	{
		$.messager.show({
			title : '提示',
			msg : '字符长度限制500 !'
		});	
		return false;
	}
	return true;
}

/**
 * 校验 flexplain
 * @author helix chen
 * @returns {Boolean}
 */
function checkExplainTextarea(){
	var flremark = $("#flexplain").val();
	if(flremark.length > 500)
	{
		$.messager.show({
			title : '提示',
			msg : '字符长度限制500 !'
		});	
		return false;
	}
	return true;
}


/**
 * 校验 通用
 * @author helix chen
 * @returns {Boolean}
 */
function checkTextarea(textareaid){
	var flremark = $("#"+textareaid).val();
	if(flremark.length > 500)
	{
		$.messager.show({
			title : '提示',
			msg : '字符长度限制500 !'
		});	
		return false;
	}
	return true;
}