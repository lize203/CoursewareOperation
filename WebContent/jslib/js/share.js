// JavaScript Document

$(document).ready(function(e) {
	/* 显示隐藏 */
	$("#main_left_hide_td").toggle(

	function() {
		$("#main_left_td").hide();
		$("#main_left_hide_td").removeClass("main_left_hide_td");
		$("#main_left_hide_td").addClass("main_left_hide_td2");
	}, function() {
		$("#main_left_td").show();
		$("#main_left_hide_td").removeClass("main_left_hide_td2");
		$("#main_left_hide_td").addClass("main_left_hide_td");
	});

	/* 鼠标划过样式 */
	$("#main_center_title_td2 button").hover(function() {
		// $("#orderedlist li:last").hover(function() {
			$(this).addClass("btn_hover");
		}, function() {
			$(this).removeClass("btn_hover");
		});
});

	

function goUrl(url) {
	location.href = url;
}

function goAddOrUpdate(url) {
	$("#form1").attr("action", url).submit();
}
function mycheck(op, url) {
	var falg = 0;
	$("input[name=^'" + op + "']:checkbox").each(function() {
		if ($(this).attr("checked")) {
			falg = 1;
		}
	})
	if (falg > 0) {
		if (confirm('删除后其它相关联的数据会丢失，确认要删除吗？')) {
			$("#form").attr("action", url).submit();
		}

	} else {
		alert("请选择一个");
		return false;
	}
}
/**
 * artDialog默认初始化
 */

/**
 * 打开弹出窗口
 */
function openArtDialog(url,id,title,width,height){
	art.dialog.open(url, {
            id : id,
            title : title,
            lock : true,
            drag : true,
            esc : false,
            background: '#ddd', // 背景色
            opacity: 0.7, // 透明度
            width : width,
			height : height
        }, false);
}

function closeDialog(){
	art.dialog.close();
}
/**
 * 适应IE将/替换为/
 * @param val
 * @return
 */
function dateConvert(val)
{
  return val.replace('-','/').replace('-','/');
}

/** 
 * 时间对象的格式化; 
 */  
Date.prototype.format = function(format) //author: meizz  
{  
  var o = {  
    "M+" : this.getMonth()+1, //month  
    "d+" : this.getDate(),    //day  
    "h+" : this.getHours(),   //hour  
    "m+" : this.getMinutes(), //minute  
    "s+" : this.getSeconds(), //second  
    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter  
    "S" : this.getMilliseconds() //millisecond  
  }  
  if(/(y+)/.test(format)){
	  format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
  }for(var k in o)if(new RegExp("("+ k +")").test(format))  
    format = format.replace(RegExp.$1,  
      RegExp.$1.length==1 ? o[k] :  
        ("00"+ o[k]).substr((""+ o[k]).length));  
  return format;  
}
/**
 * 时间格式化
 * @param value
 * @return
 */
function newDate(value){
	var date = new Date(dateConvert(value));	
    return date.format("yyyy-MM-dd hh:mm:ss"); 
}
/**
 * 时间格式化
 * @param value
 * @return
 */
function newTime(value){
	var date = new Date(dateConvert(value));	
    return date.format("hh:mm:ss"); 
}


/**
 * 分页数组
 */
var pageGroup = [10,15,20,30,50,80,100,150,200,300,400,500];


/**
 * 项目路径
 */
var basic ={
    getContextPath: function () {  //获取项目路径
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0, index + 1);
        return result;
    }
}


