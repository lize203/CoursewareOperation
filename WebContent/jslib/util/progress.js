/*!
 * 
 * 
 * Author:helix chen||（xiang）
 * 
 * Date: 2018-08-11
 * 
 * 
 * help@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@---start
 * (JSP---OR----HTML   import  head   jquery 版本无所谓)
<link rel="stylesheet" type="text/css" href="${basePath}style/css/progress.css">
<script type="text/javascript" src="${basePath}jslib/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${basePath}jslib/util/progress.js"></script>
 * 
 * 
 * 
 * 
 (@@@@@@@@@@@@html  jsp  body  引用)
	<div class="wrapper"  style="display:none">
<!-- 	 style="display:none" -->
	<div class="load-bar" style="margin-top:40px;">  
		<div class="load-bar-inner" data-loading="0"> <span id="counter"></span> </div> 
	</div>  
	<h1>上传中。 请等待... </h1>  
</div>
 * help@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@---end
 * 
 *  showProgress();
 *  hideProgress();
 */


function showProgress(){
	progress();
//	if(navigator.userAgent.toLowerCase().indexOf("chrome") == -1){
//		document.getElementsByClassName("wrapper")[0].style.display="block";
//	}else{
		$(".wrapper")[0].style.display="block";
//	}
};
function hideProgress(){
//	if(navigator.userAgent.toLowerCase().indexOf("chrome") == -1){
//		document.getElementsByClassName("wrapper")[0].style.display="none";
//	}else{
		$(".wrapper")[0].style.display="none";
//	}
};

function progress()
{
//	var fileobj = $("#file");
//	var fileobjsize = fileobj.size();
//	var filesize = fileobj.size.length;
//	var filesizelength = fileobj.size.length;
//	alert("fileobj:"+fileobj);
//	alert("fileobjsize:"+fileobjsize);
//	alert("filesize:"+filesize);
//	alert("filesizelength:"+filesizelength);
	  var interval = setInterval(increment,600);
	  var current = 0;

	  function increment(){
	    current++;
	    $('#counter').html(current+'%'); 
	    if(current >= 99) { current = 99; }
	  }

//	  $('.load-bar').mouseover(function(){
//	            clearInterval(interval);
//	  }).mouseout(function(){
//	      interval = setInterval(increment,100);
//	         });
}


