<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="/page/public/header.jsp"%>
<body>
<div id="p" class="easyui-panel" style="padding:0px;background-color: #EFF7FD;"  fit="true">

	<div class="easyui-layout short-menu"  fit="true">
		

       <div data-options="region:'west'"  class="scroll-west" title="常用菜单快捷方式" style="height: 440px; ">
		
		<table id="sqd" >
		</table>
		
		</div>
        <div data-options="region:'center'" title="待办列表"  class="backlog" style="width: 520px;height: 440px">
            <%-- <table id="col" class="easyui-datagrid" fit="true" 
				data-options="onDblClickRow:go,singleSelect:true,url:'${basePath}bench/getChange',collapsible:true" 
				pagination="true"
				rownumbers="true" fitColumns="true" singleSelect="true">
				<thead>
					<tr>
						<th field="flid" hidden="true"> </th>
						<th field="flchangenumber" sortable="true" align="left" width="18">变更编号</th>
						<th field="flchangetitle" sortable="true" align="left" width="55">变更标题</th>
						<th sortable="true" field="flname" align="left" width="20">创建人</th>
						<th sortable="true" field="type" align="left" width="20"
						formatter="type">工单类型</th>
					</tr>
				</thead>
			</table> --%>
        </div>
    </div>
</div>
<div id="add" class="easyui-window"
		data-options="modal:false,inline:true,closed:true,minimizable: false,collapsible:true"
		style="width: 350px; height: 400px; padding: 0px 0px" closed="true">
			<table id="col1" class="easyui-datagrid" fit="true" 
				toolbar="#tool"
					data-options="singleSelect:true,url:'${basePath}bench/getMenu',collapsible:true" 
					pagination="false" pageSize="1000" singleSelect="false",selectOnCheck="true" checkOnSelect="true"
					rownumbers="true" fitColumns="true" >
					<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th field="flicon" hidden="true"> </th>
							<th field="flurl" hidden="true"> </th>
							<th field="flname" sortable="true" align="center" width="18">菜单名</th>
							
						</tr>
					</thead>
				</table>
	<div id="tool" style="padding-bottom: 0px;padding-top: 0px;">
			<a class="easyui-linkbutton" href="javascript:void(0)"
				 iconCls="icon-add" id="bt_add" onclick="addshortcut()">添加</a> 
	</div>
</div>
<div id="remove"class="easyui-window"
		data-options="modal:false,inline:true,closed:true,minimizable: false,collapsible:false"
		style="width: 350px; height: 400px; padding: 0px 0px" closed="true">
		<table id="col2" class="easyui-datagrid" fit="true" 
			toolbar="#t"
			data-options="singleSelect:true,url:'${basePath}bench/getShortcut',collapsible:true" 
			pagination="false" pageSize="1000"
			rownumbers="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="flid" hidden="true"> </th>
					<th field="flicon" hidden="true"> </th>
					<th field="flurl" hidden="true"> </th>
					<th field="flname" sortable="true" align="center" width="18">菜单名</th>
					
				</tr>
			</thead>
		</table>
	<div id="t" style="padding-top: 0px;padding-bottom: 0px;">
			<a class="easyui-linkbutton" href="javascript:void(0)"
				 iconCls="icon-remove" onclick="removeshortcut()">删除</a> 
	</div>
</div>
</body>
<head>
<title>工作台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
.add div{
	text-align: center;
}

h2{
	text-align: center;
}

.backlog{
	display: none;
}

.short-menu .layout-panel-center{
	 display: none !important;
}

.short-menu .panel-title{
	text-align: center;
	font-size: 20px;
}

#sqd{
	position:relative;
	left:0;
	top:0;
	padding-left:25px;
	padding-top:20px;
	margin:0 auto;
	text-align: left;
}

#sqd .table td{
	display: inline-block;
	height:192px !important;
	padding-right: 25px ;
	position: relative;
	padding-top:0 !important;
	padding-bottom:0 !important;
}

.td{
	padding-top: 30px !important;
	text-align: center;
}

#sqd table .td:HOVER {
	background-color: #E8F0F6;
}

.short-menu .panel-body{
	background-color: #EEF7FD;
}

.short-menu .panel-header{
	background-color: #EEF7FD !important;
	display:none;
}

.delete:hover{
	background: url('${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/icons/modelmanage/bench/delete-hover.png') no-repeat center center;
}

.delete{
	width:30px;
	height:30px;
	position: absolute;
	top:0;
	right: 32px;
	background: url('${basePath}jslib/jquery-easyui-1.3.2/themes/metro_AH/icons/modelmanage/bench/delete.png') no-repeat center center;
}
.flname{
	text-align: center;
	display: block;
	height:40px;
	margin-top:20px;
	font-size:13px;

}
.add{
	border:1px #A6ACB1 dotted;
	padding-right:0 !important;
	width:145px;
	height:158px;
}
.scroll-west{
	position:relative;

}
.short-menu .layout-panel-west,.short-menu .layout-panel-west .layout-body{
	width:100% !important;
}
img{
	cursor: pointer;
}

</style>

<script type="text/javascript">
function getshortcut(){
	$.post(basePath+'bench/getShortcut',{}, function(result) {
		if (result) {	
			var tb = document.getElementById('sqd');
			var rowNum = tb.rows.length;
			if (rowNum > 0) {
				for (var j = 0; j < rowNum; j++) {
					tb.deleteRow(j);
					rowNum = rowNum-1;
					j = j - 1;
				}
			}
			newRow = document.all.sqd.insertRow();	
			newcell = newRow.insertCell();
			var i=0;
			var htmlStr = "<table class='table' style=\"text-align:center;border:0px;padding-top:0px;\"><tr>";
			var widthNow = $(window).width();
			var rowSum = 1;
			var everyObjW=150;
			var rowTemp = parseInt((widthNow-100)/everyObjW);
			if(rowTemp>1){
				if(rowTemp*everyObjW+250>widthNow){
					--rowTemp;
				}
				rowSum = rowTemp;
			}
			
			for(i=0;i<result.total;i++){
				var row = result.rows[i];
				if(row.flbr %rowSum == 0){
					htmlStr +="</tr><tr class=' tr' id=' tr"+row.flbr/rowSum+"' style=\"padding-top:0px;height: 105px;\">";
				}
				htmlStr += "<td><div class='td' " +
                        "style='cursor:hand;padding:0px;padding-top:0px;width: 145px;height: 160px;'><div onclick=\"jump('" + row.flMenuId + "', '" + row.flurl + "')\"><img src='"+basePath+"jslib/jquery-easyui-1.3.2/themes/metro_AH/icons/modelmanage/bench/" + row.flicon + "_white.png' width=90 height=90></div><div id='delete' onclick=\"remove1('" + row.flid + "', '" + row.flurl + "')\" ></div>";
				
					htmlStr += "<div class='flname'>"+row.flname + "<div>";
				    htmlStr += "</div></td>";
			}
			if((i)%rowSum==0){
				htmlStr +="</tr><tr style=\"padding-top:0px;height: 105px;\">";
			}
			htmlStr += "<td  " +
            "style='cursor:hand;'><div class='td add' onclick='add()'><div><img src='"+basePath+"jslib/jquery-easyui-1.3.2/themes/metro_AH/icons/modelmanage/bench/add.png' alt='增加'></div>";
        	htmlStr += "</div></td>";
			htmlStr += "</tr></table>";
			newcell.innerHTML = htmlStr;
		}
	},'json');
}

function type(value, row, index){
	if(value=="1"){
		return "模型变更";
	}else{
		return "接口变更";
	}
}

function add(){
	$('#add').dialog('open').dialog('setTitle', '添加快捷方式');
}

function remove1(menuId, url){
	if (menuId) {
		$.messager.confirm('提示', '确定删除此快捷菜单么?', function(r) {
			if (r) {
				$.post(basePath+'bench/remove?flid='
						+ menuId, function(result) {
					if (result) {
						$.messager.show({ // show error message
							title : '提示',
							msg : '删除快捷菜单成功！'
						});
						$('#col1').datagrid('reload'); // reload the user data
						getshortcut();
					} else {
						$.messager.show({ // show error message
							title : '错误提示',
							msg : '删除快捷菜单失败！'
						});
						
					}
				}, 'json');
			}
		});
	}else{
		$.messager.show({ 
			title : '提示',
			msg : '请选择要删除的快捷菜单!'
		});
	}
		 
}

//
function addshortcut(){
	$('#bt_add').attr("disabled", false);
	var row = $('#col1').datagrid('getChecked');
	var flname= new Array(); 
	var flicon= new Array() ;
	var flurl= new Array();
	var flMenuId= new Array();
	if (row) {
		for (var i = 0; i < row.length; i++) {
			flname.push(row[i].flname);
			flicon.push(row[i].flicon);
			flurl.push(row[i].flurl);
			flMenuId.push(row[i].flid);
		}
	}
	
	$.post(basePath+'bench/addPatchShortcut?flname='+encodeURI(encodeURI(flname.toString())) +'&flicon='+flicon.toString()+'&flurl='+flurl.toString()+"&flMenuId="+flMenuId.toString(), function(result) {
		if (result) {
			$.messager.show({ // show error message
				title : '提示',
				msg : '添加快捷菜单成功！'
			});
			
			$('#col1').datagrid('reload'); // reload the user data
			$('#col2').datagrid('reload');
			getshortcut();
		} else {
			$('#bt_add').attr("disabled", true);
			$.messager.show({ // show error message
				title : '错误提示',
				msg : '添加快捷菜单失败！'
			});
		}
	}, 'json');
}

function removeshortcut(){
	var row = $('#col2').datagrid('getSelected');
	if (row) {
		$.messager.confirm('提示', '确定删除此快捷菜单么?', function(r) {
			if (r) {
				$.post(basePath+'bench/remove?flid='
						+ row.flid, function(result) {
					if (result) {
						$.messager.show({ // show error message
							title : '错误提示',
							msg : '删除快捷菜单失败！'
						});
					} else {
						$.messager.show({ // show success message
							title : '提示',
							msg : '删除快捷菜单成功！'
						});
						$('#col1').datagrid('reload'); // reload the user data
						$('#col2').datagrid('reload');
						getshortcut();
					}
				}, 'json');
			}
		});
	}else{
		$.messager.show({ 
			title : '提示',
			msg : '请选择预删除的菜单！'
		});
	}
		 
}

function go(row,value){
	if (value.type=="2") {
		window.location.href = basePath+"interfaceChange/showWorOrder?flid="+value.flid;
	} else {
		window.location.href = basePath+"changeQuery/showWorOrder?flid="+value.flid;
	}
}

function jump(menuId, url) {
    $.ajax({
        type: 'GET',
        url: basePath+'bench/getActiveMenu',
        data: { flMenuId: menuId },
        dataType: 'json'
    }).done( function(result) {
    	$.ajax({
            url :basePath+ "main/child",
            data : {
            	childid : menuId
            },
            dataType: "json",
            success : function(rs) {
            	window.parent.location = basePath+"admin/getToBackPage?activeMenu=" + result + "&selectedSubmenu=" + rs.msg;
            }
        });
        
    });
}

/**
 * 代办事项提示框
 */
/* function backlogInfo(){
	var times=0;
	var len=0;
	$.post(basePath+"bench/queryBacklogTimes", {}, function(result) {
		if (result == "0") {
			times = result;
			$.post(basePath+'changeQuery/getData',{}, function(result) {
				if (result) {	
				     len = result.total;
				  
				     if (len > 0 && times == 0) {
				    	 $.messager.show({ // 显示代办对话框
								title : '待办事项提醒',
								msg : '您有'+len+"项待办事项"
							});
				 
				    		$.post(basePath+"bench/resetBacklogTimes", {
				    			"backlogTimes" : times
				    		});
				     }
				}
			},'json');
		}
		
	}, 'json');
} */


$(document).ready(function(){
	getshortcut();
	/* backlogInfo(); */
	/* 隐藏侧边栏 */
	with(window.top){
		(function hideLeft() {
			// alert('xx');
			$('.easyui-layout').layout('remove','west');
			setTimeout(function(){
				$('.layout-panel-center').css({
					'left':0
				});
				$('.layout-panel-center').find('iframe').css({
					width:$('.layout-panel-center').width()+'px'
				});
			},0);
		})();
	}
 	$("#sqd").on("mouseover", ".td", function() {
	$(this).children("#delete").addClass("delete");
    }).on("mouseout", ".td", function() {
	
	 $(this).children("#delete").removeClass("delete");
    }); 
    reMenus();
});
function reMenus(){
	var topDocument = $(window.top.document);
    var offsetW = topDocument.width()-188;
    topDocument.find('#cc').closest('.layout-panel-center').css('left','188px');
    topDocument.find('#cc').find('iframe').width(offsetW);
    topDocument.find('#btn_er').click(function(){
    	topDocument.find('#cc').find('iframe').width("100%");
    });
}
</script>
</head></html>

