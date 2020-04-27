<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<style type="text/css">
.pageul {
	list-style: none;
	float: right;
}

#pagelist {
	width: 100%;
	margin: 0px auto;
	padding: 0px 0px;
	height: 20px;
}

#pagelist ul li {
	float: left;
	border: 1px solid #5d9cdf;
	height: 20px;
	line-height: 20px;
	margin: 0px 2px;
}

#pagelist ul li a,.pageinfo {
	display: block;
	padding: 0px 6px;
	background: #e6f2fe;
}

.pageinfo {
	color: #005279;
}

.current {
	background: #3E9EFF;
	display: block;
	padding: 0px 6px;
	font-weight: bold;
	color: white;
}
</style>

</head>

<body>
	<div id="pagelist">
		<ul class="pageul">
			<li class="pageinfo">第${request.pageView.currentPage}页
			</li>
			<li class="pageinfo">总数${request.pageView.totalRecord}条
			</li>

			<li class="pageinfo">总页数${request.pageView.totalPage}页
			</li>

			<li><select id="select_rows" onchange="setRows()"
				style="height: 20px;">
					<c:forEach begin="10" end="50" step="5" var="i">
						<option
							<c:if test="${i==pageView.maxResult }">selected="selected"</c:if>
							value="${i}">${i}</option>
					</c:forEach>
			</select></li>
			
			<c:if test="#request.pageView.currentPage > 1">
				<li class="pageinfo"><a href="javascript:topage(1)">首页</a></li>
				<li class="pageinfo"><a
					href="javascript:topage('<s:property value="#request.pageView.currentPage - 1"/>')">上一页</a></li>
			</c:if>
			<c:if test="#request.pageView.currentPage <= 1">
				<li class="pageinfo">首页</li>
				<li class="pageinfo">上一页</li>
			</c:if>

			<c:if test="#request.pageView.pageIndex.startIndex > 1">
				<li class="pageinfo">...</li>
			</c:if>
			<c:if test="#request.pageView!=null">
				<c:forEach begin="#request.pageView.pageIndex.startIndex"
					end="#request.pageView.pageIndex.endIndex" var="wp">
					<c:if test="#request.pageView.currentPage== #wp">
						<li class="current">${wp}</li>
					</c:if>
					<c:if test="#request.pageView.currentPage!= #wp">
						<li><a href="javascript:topage('${wp}')">${wp}</a></li>
					</c:if>
				</c:forEach>
			</c:if>
			<c:if
				test="#request.pageView.pageIndex.endIndex < #request.pageView.totalPage">
				<li class="pageinfo">...</li>
			</c:if>
			<c:if
				test="#request.pageView.currentPage < #request.pageView.totalPage">
				<li><a
					href="javascript:topage('<s:property value="#request.pageView.currentPage + 1"/>')">下一页</a></li>
				<li><a
					href="javascript:topage('<s:property value="#request.pageView.totalPage"/>')">末页</a></li>
			</c:if>
			<c:if
				test="#request.pageView.currentPage >= #request.pageView.totalPage">
				<li class="pageinfo">下一页</li>
				<li class="pageinfo">末页</li>
			</c:if>
		</ul>
	</div>
</body>
</html>


<script type="text/javascript">
function topage(page)
{
	var rows = $("#select_rows");
	//var form = document.pageForm;
	//var form = document.getElementById("pageForm");
	var url = window.location.href;
	//var len=urlinfo.length;//获取url的长度
	//var offset=urlinfo.lastIndexOf("/");//设置参数字符串开始的位置
	//var newsidinfo=urlinfo.substr(offset,len);
	if(url.indexOf("?")==-1){
		url = url + "?page="+page;
	}else{
		if(url.indexOf("?page")==-1&&url.indexOf("&page")==-1){
			url = url + "&page="+page;
		}else{
			var len = url.length;
			var offset=url.indexOf("page=");	
			var url2 = url.substr(0,offset);
			url = url2+"page="+page;
		}		
	}	
	if(url.indexOf("?rows")==-1&&url.indexOf("&rows")==-1){
		url = url + "&rows="+rows.val();
	}else{
		var len = url.length;
		var offset=url.lastIndexOf("rows=");	
		var url2 = url.substr(0,offset);
		url = url2+"rows="+rows.val();
	}
	window.location.href = url;
}

function setRows(){
	var rows = $("#select_rows");
	$.post("global_ajax!setCustomRows",{ "rows": rows.val()},
  		function(data){
  			var url = window.location.href;	
  			if(url.indexOf("?")==-1){
				url = url + "?page="+1;
			}else{
				if(url.indexOf("?page")==-1&&url.indexOf("&page")==-1){
					url = url + "&page="+1;
				}else{
					var len = url.length;
					var offset=url.indexOf("page=");	
					var url2 = url.substr(0,offset);
					url = url2+"page="+1;
				}		
			}	
			if(url.indexOf("?rows")==-1&&url.indexOf("&rows")==-1){
				url = url + "&rows="+rows.val();
			}else{
				var len = url.length;
				var offset=url.lastIndexOf("rows=");	
				var url2 = url.substr(0,offset);
				url = url2+"rows="+rows.val();
			}
			window.location.href = url;		
  		},
  	"HTML"
 	);
}
</script>
