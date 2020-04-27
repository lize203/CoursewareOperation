/**
 * 更新课件
 * @returns
 */
function savaData() {
	
	if(!checkExplainTextarea())
	{
		return false;
	}
	
 	  var uploadFile = $('#file').val();
 	  	if (uploadFile != undefined && uploadFile !="") {
 	  		var type = ".tar.gz";
 			var lastfileType = uploadFile.lastIndexOf(type);
 		 	if (lastfileType == -1) {
 		 		if(navigator.userAgent.toLowerCase().indexOf("chrome") == -1){
 					alert('请选择后缀名为'+type+'文件 ！(注意大小写)');
 				}else{
 					$.messager.alert('提示', '请选择后缀名为'+type+'文件 ！(注意大小写)');
 				}
 		 		return false;
 		 	}
 	 		
 	 	 	var index0 = uploadFile.lastIndexOf(type);
 	 	 	var startIndex = uploadFile.lastIndexOf("\\")+1;
 	 	 	var fileName = uploadFile.substring(startIndex,index0).toLowerCase();;
 	 	 	$("#flname").val(fileName);
 	 		$.messager.confirm('提示', '文件将会替换服务器现有的课件,确认替换？',function(r){if (r) {updateCourseware()}});
 	 	}else{
 	 		updateCourseware();
 	 	}
}

function updateCourseware()
{
//	var formobject = $( "#fm" )[0];
//	 var formData = new FormData(formobject);  
     var ajaxUrl = basePath+'courseware/uploadCoursewareUpdate';
     $('#fm').form('submit', {
		    url:ajaxUrl,
		    onSubmit : function() {
		    	showProgress();
				return true;
			},
		    success:function(result){
	        	 result = $.parseJSON(result);
	  			if (result.code == 0) {
	  				$.messager.show({
	  					title : '提示',
	  					msg : '课件修改失败(清检查文件是否为空或异常)！'
	  				});
	  			} else if (result.code == 1) {
	  				$.messager.show({
	  					title : '提示',
	  					msg : '课件修改成功！'
	  				});
	  				closeWin();
	  				$('#dg').datagrid('reload'); // reload the user data
	  				hideProgress();
	  			} else {
	  				$.messager.show({
	  					title : '提示',
	  					msg : '课件已存在，请重新选择 !'
	  				});				
	  			}
	  			hideProgress();
	          },
	         error: function(data) {
	        	 hideProgress();
	        	 alert("error:"+data.responseText);

	          }
		});
     
//     
//     showProgress();
//     $.ajax({
//         type: "POST",
//         url: ajaxUrl,
//         data: formData,
//         async: true,  
//         cache: false,  
//         contentType: false,  
//         processData: false,
//         success: function (result) {
//        	 result = $.parseJSON(result);
// 			if (result.code == 0) {
// 				$.messager.show({
// 					title : '提示',
// 					msg : '课件修改失败(清检查文件是否为空或异常)！'
// 				});
// 			} else if (result.code == 1) {
// 				$.messager.show({
// 					title : '提示',
// 					msg : '课件修改成功！'
// 				});
// 				closeWin();
// 				$('#dg').datagrid('reload'); // reload the user data
// 				hideProgress();
// 			} else {
// 				$.messager.show({
// 					title : '提示',
// 					msg : '课件已存在，请重新选择 !'
// 				});				
// 			}
// 			hideProgress();
//         },
//         error: function(data) {
//        	 hideProgress();
//        	 alert("error:"+data.responseText);
//
//          }
//     });
}

/**
 * 删除课件
 */
function destroyData() {
	var row = $('#dg').datagrid('getSelected');
	var userID = $("#userID").val();
	var userAccount = $("#userAccount").val();
	if (row) {
		if(row.fluserid != userID && userAccount != "admin"){
			alert("您没有权限删除\""+row.flname+"\"课件");
			return;
		}
		$.messager.confirm('提示', '确定删除此记录?', function(r) {
			if (r) {
				$.post(basePath+'courseware/deleteData?id='
						+ row.flid, function(result) {
					if (result.code==0) {
						$.messager.show({ // show error message
							title : '提示',
							msg : '删除课件失败,请进行解绑再次尝试删除！(绑定如：课程,实验模板)'
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
function editRow(){
	var row = $('#dg').datagrid('getSelected');
	var userID = $("#userID").val();
	var userAccount = $("#userAccount").val();
	if(row.fluserid != userID && userAccount != "admin"){
		alert("您没有权限删除\""+row.flname+"\"课件");
		return;
	}
	//点击编辑按钮时将所有按钮和文本设置为可编辑状态
	$('#win').dialog('setTitle', '课件编辑');
	$("#win").find("input").attr("disabled",false)
	$("#win").find("textarea").attr("disabled",false);
	var inputs = $("#win").find("input");
	for ( var i = 0; i < inputs.length; i++) {
		var readonly = inputs[i].readOnly;
		if(readonly == true){
//			inputs[i].disabled = false;
			inputs[i].style.color='#999999';
//			style="background-color:#999;"
		}
	}
	
	$("#edit_show").hide();
	$("#edit_hide").show();
	$("#role_choise").attr("style", "visibility:visible");
}