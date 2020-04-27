


/**
 * 保存课件
 * @returns
 */
function savaAdd() {
	if(!checkExplainTextarea())
	{
		return false;
	}
	
	var uploadFile = $('#file').val();
	if (uploadFile == "") {
		if(navigator.userAgent.toLowerCase().indexOf("chrome") == -1){
			alert('请选择文件 ！');
		}else{
			$.messager.alert('提示', '请选择文件 ！');
		}
		return false;
	}
//	fileType = uploadFile.substr(uploadFile.lastIndexOf(".")).toLowerCase();
// 	if (fileType != ".gz") {// && fileType != ".rar" && fileType != ".gz"
// 		$.messager.alert('提示', '请选择后缀名为tar.gz文件 ！');//,rar,tar.gz
// 		return false;
// 	}
	var type = ".tar.gz";
	var type2 = ".zip";
		var lastfileType = uploadFile.lastIndexOf(type);
		var lastfileType2 = uploadFile.lastIndexOf(type2);
//		fileType = uploadFile.substr(uploadFile.lastIndexOf(".tar.gz")).toLowerCase();
//		fileType != ".gz" && 
	 	if (lastfileType == -1 && lastfileType2 == -1) {
	 		if(navigator.userAgent.toLowerCase().indexOf("chrome") == -1){
				alert('请选择后缀名为'+type+'或'+type2+'的文件 ！(注意大小写)');
			}else{
				$.messager.alert('提示', '请选择后缀名为'+type+'或'+type2+'的文件 ！(注意大小写)');
			}
	 		return false;
	 	}
		
 	var index0 = lastfileType>0 ? lastfileType : lastfileType2;
 	var startIndex = uploadFile.lastIndexOf("\\")+1;
 	var fileName = uploadFile.substr(startIndex,index0).toLowerCase();;
 	$("#flname").val(fileName);
	
//	 var formData = new FormData($( "#uploadPic" )[0]);  
     var ajaxUrl = basePath+'courseware/uploadCourseware';
     $('#uploadPic').form('submit', {
		    url:ajaxUrl,
		    onSubmit : function() {
		    	showProgress();
				return true;
			},
		    success:function(result){
	        	 hideProgress();
	        	 result = $.parseJSON(result);
	 			if (result.code == 0) {
	 				$.messager.show({
	 					title : '提示',
	 					msg : '教案上传失败(清检查文件是否为空或异常)！'
	 				});
	 			} else if (result.code == 1) {
	 				$.messager.show({
	 					title : '提示',
	 					msg : '教案上传成功！'
	 				});
	 				closeWin();
	 				$('#dg').datagrid('reload'); // reload the user data
	 				hideProgress();
	 			} else {
	 				$.messager.show({
	 					title : '提示',
	 					msg : '教案已存在，请重新选择 !'
	 				});				
	 			};
	 			hideProgress();
	         },
	         error: function(data) {
	        	 hideProgress();
	             alert("error:"+data.responseText);
	          }
		});
     
//     showProgress();
//     $.ajax({
//         type: "POST",
//         //dataType: "text",
//         url: ajaxUrl,
//         data: formData,
//         async: true,  
//         cache: false,  
//         contentType: false,  
//         processData: false,
//         success: function (result) {
//        	 hideProgress();
//        	 result = $.parseJSON(result);
// 			if (result.code == 0) {
// 				$.messager.show({
// 					title : '提示',
// 					msg : '课件上传失败(清检查文件是否为空或异常)！'
// 				});
// 			} else if (result.code == 1) {
// 				$.messager.show({
// 					title : '提示',
// 					msg : '课件上传成功！'
// 				});
// 				closeWin();
// 				$('#dg').datagrid('reload'); // reload the user data
// 				hideProgress();
// 			} else {
// 				$.messager.show({
// 					title : '提示',
// 					msg : '课件已存在，请重新选择 !'
// 				});				
// 			};
// 			hideProgress();
//         },
//         error: function(data) {
//        	 hideProgress();
//             alert("error:"+data.responseText);
//          }
//     });
}
