//基础函数
//使用三次贝塞尔曲线模拟椭圆
function BezierEllipse(context, x, y, a, b) { 
	var ox = 0.5 * a, 
	oy = 0.6 * b; 
	context.save(); 
	context.translate(x, y); 
	context.beginPath(); 
	//从椭圆纵轴下端开始逆时针方向绘制 
	context.moveTo(0, b); 
	context.bezierCurveTo(ox, b, a, oy, a, 0); 
	context.bezierCurveTo(a, -oy, ox, -b, 0, -b); 
	context.bezierCurveTo(-ox, -b, -a, -oy, -a, 0); 
	context.bezierCurveTo(-a, oy, -ox, b, 0, b); 
	context.closePath();  
	context.restore();
	return context;
};
//画title
function drawTitle(cxt,width,fontText,color){
	cxt.save();
	cxt.beginPath();
	cxt.font = "18px microsoft yahei";
	// var metrics = cxt.measureText(fontText);
	// var deviation = Math.floor(metrics.width/2-3); 
	// cxt.fillStyle = "#3e3e3e";
	// cxt.fillText(fontText,width/2-deviation,30);
	cxt.fillStyle = color?color:"#74b4e4";
	cxt.fillText(fontText,40,30);
	cxt.restore();
}
//画错误信息
function drawError(cxt,width,y,fontText){
	cxt.save();
	cxt.beginPath();
	cxt.font = "18px microsoft yahei";
	var metrics = cxt.measureText(fontText);
	var deviation = Math.floor(metrics.width/2-3); 
	cxt.fillStyle = "#3e3e3e";
	cxt.fillText(fontText,width/2-deviation,y);
	cxt.restore();
}
//画百分比圆柱
function drawPercentColumn(cxt,percentUsed,_statsuColor){
	// _statsuColor:{
	// 	useColor:'#7cb7e5',
	// 	useShapColor:'#5ca2d6',
	// 	useFontColor:'#fff',
	// 	unuseColor:'#e3f3ff',
	// 	unuseShapColor:'#dceffe',
	// 	unuseFontColor:'#2384cb',
	// 	gradShapColor:'#b3d8f5'
	// }
	var used = percentUsed.toFixed(2);
	var unused = parseFloat(1 - percentUsed);
	//画大圆柱
	cxt.beginPath();
	var grad = cxt.createLinearGradient(210,245,210,85);
	grad.addColorStop(0,_statsuColor.porColor);
	grad.addColorStop(used,_statsuColor.porColor);
	grad.addColorStop(used,_statsuColor.unporColor);
	grad.addColorStop(1,_statsuColor.unporColor);
	cxt.fillStyle = grad;
	cxt.fillRect(210,85,140,160);
	
	//顶部固定椭圆
	cxt = BezierEllipse(cxt,280,85,70,20);
	cxt.fillStyle = _statsuColor.unporShapColor;	
	cxt.fill();
	
	//底部固定椭圆
	cxt = BezierEllipse(cxt,280,245,70,20);
	cxt.fillStyle = _statsuColor.porShapColor;
	cxt.fill();
	
	//中间浮动椭圆
	cxt = BezierEllipse(cxt,280,85+160*unused,70,20);
	cxt.fillStyle = _statsuColor.gradShapColor;	
	cxt.fill();
	//画大圆柱 End
	
	//书写百分比
	//已使用
	var usedInt = Math.floor(used*100);
	cxt.save(); 
	cxt.font="24px microsoft yahei";
	cxt.fillStyle= _statsuColor.porFontColor;
	var fontText =	usedInt + '%';  
	var metrics = cxt.measureText(fontText);
	var deviation = Math.floor(metrics.width/2-3); 
	// console.log(metrics);
	var usedY = (245-160*used)+160*used/2+25;
	usedY = usedY>255?255:usedY;
	cxt.fillText(fontText,280-deviation,usedY);
	cxt.restore();
	
	//未使用
	cxt.save(); 
	cxt.font="24px microsoft yahei";
	cxt.fillStyle= _statsuColor.unporFontColor;
	var fontText = (100 - usedInt)+'%';  
	var metrics = cxt.measureText(fontText);
	var deviation = Math.floor(metrics.width/2-3); 
	// console.log(metrics);
	var unusedY = (85+160*unused)-160*unused/2+25;
	unusedY = unusedY<125?98:unusedY;
	cxt.fillText(fontText,280-deviation,unusedY);
	cxt.restore();
}
//画百分比圆柱标识bar
function drawPercentColumnBar(cxt,portName,unportName,_statsuColor){
	// _statsuColor:{
	// 	porColor:'#7cb7e5',
	// 	porShapColor:'#5ca2d6',
	// 	porFontColor:'#fff',
	// 	unporColor:'#e3f3ff',
	// 	unporShapColor:'#dceffe',
	// 	unporFontColor:'#2384cb',
	// 	gradShapColor:'#b3d8f5'
	// }
	cxt.save(); 
	cxt.beginPath();
	cxt.fillStyle = _statsuColor.unporColor;
	cxt.fillRect(40,75,30,10);
	cxt = BezierEllipse(cxt,55,85,15,5);
	cxt.fill();
	cxt.restore();
	cxt.save(); 
	cxt.beginPath();
	cxt.fillStyle = _statsuColor.unporShapColor;
	cxt = BezierEllipse(cxt,55,75,15,5);
	cxt.fill();
	cxt.restore();
	
	cxt.save(); 
	cxt.beginPath();
	cxt.fillStyle = _statsuColor.porColor;
	cxt.fillRect(40,110,30,10);
	cxt = BezierEllipse(cxt,55,120,15,5);
	cxt.fill();
	cxt.restore();
	cxt.save(); 
	cxt.beginPath();
	cxt.fillStyle = _statsuColor.porShapColor;
	cxt = BezierEllipse(cxt,55,110,15,5);
	cxt.fill();
	cxt.restore();
	
	cxt.save(); 
	cxt.beginPath();
	cxt.fillStyle = "#666";
	cxt.fontStyle = "14px microsoft yahei";
	cxt.fillText((unportName || ''),80,84,128);
	cxt.fillText((portName || ''),80,119,128);
	cxt.restore();
}
//画百分比圆
function drawPercentCircle(cxt,_data,colors){
	var sAngle = 0;
	var eAngle = 0;
	var data = [];
	//去空 value为0的
	for(var i=0;i<_data.length;i++){
		if(_data[i].portion != 0){
			data.push(_data[i]);
		}
	}
	for(var i=0;i<data.length;i++){
		eAngle = i==data.length-1?Math.PI*2:eAngle + data[i].portion*Math.PI*2;
		cxt.save();
		cxt.beginPath();
		cxt.moveTo(310,150); 
		cxt.arc(310,150,88,sAngle,eAngle);
		cxt.closePath();
		cxt.fillStyle = colors[i];
		cxt.lineWidth = 1;
		cxt.fill();
		cxt.restore();
		sAngle = eAngle;
	}
}
//百分圆bar
function drawPercentCircleBar(cxt,_data,colors){
	var data = _data;
	var x = 30;
	var y = 75;
	for(var i=0;i<data.length;i++){
		if(i>6){
			cxt.fillStyle = "#333";
			cxt.font = "14px microsoft yahei";
			cxt.fillText('......',x,y+7,138);	
			return false;
		}
		cxt.save();
		cxt.beginPath();
		cxt.arc(x,y,5,0,2*Math.PI);
		cxt.fillStyle = colors[i];
		cxt.fill();
		cxt.beginPath();
		cxt.fillStyle = "#333";
		cxt.font = "14px microsoft yahei";
		var textName = _data[i].name;
		if(navigator.userAgent.indexOf("MSIE 8.0") > 0){
			if(textName.length>8){
				textName = textName.substr(0,14) + '...';
			}
		}
		cxt.fillText(textName,x+20,y+7,138);
		cxt.restore();
		y = y+25;
	}
}
//随机生成十六进制颜色
function randomColor() {
	//16进制方式表示颜色0-F
	var arrHex = ["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"];
	var strHex = "#";
	var index;
	for(var i = 0; i < 6; i++) {
		//取得0-15之间的随机整数
		index = Math.round(Math.random() * 15);
		strHex += arrHex[index];
	}
	return strHex;
}
//判断数组字段,并返回总和
function checkArray(data,key){
    var has = true;
    var sum = 0;
    for(var i=0;i<data.length;i++){
    	if((data[i])[key] != undefined){
    		var aValue = parseFloat((data[i].value));
			data[i].value = aValue;
    		sum = sum + ((data[i])[key]);
    	}else{
    		has = false;
    	}
    }
    return has?sum:false;
};
//封装canvas函数
//柱形图
$.fn.PercentColumn = function(obj){
	var key = $(this).attr('id')?$(this).attr('id'):$(this).attr('class');
	if($('#'+key+"_wrap").length == 0){
		$(this).wrap("<div id=\""+key+"_wrap"+"\" style=\"position:relative;display:inline-block;\"></div>");
	}
	var wrap =$('#'+key+'_wrap');
	var canvas = $(this)[0];
	var context = canvas.getContext('2d');
	canvas.width = obj.width || 454;
	canvas.height = obj.height || 290;
	var title = obj.title || '';
	drawTitle(context,canvas.width,title,obj.titleColor);
	// console.log('圆柱图'+obj.portion);
	if(!obj.portion || obj.portion=='' || obj.portion==null || obj.portion==undefined){
		drawError(context,canvas.width,128,"没有该数据");
		return false;
	}
	
	var portion = obj.portion;
	var portName = obj.portName;
	var unportName = obj.unportName;
	var statusColor =   obj.statusColor?
					    obj.statusColor:
						{
							porColor:'#7cb7e5',
							porShapColor:'#5ca2d6',
							porFontColor:'#fff',
							unporColor:'#e3f3ff',
							unporShapColor:'#dceffe',
							unporFontColor:'#2384cb',
							gradShapColor:'#b3d8f5'
						};
	drawPercentColumnBar(context,portName,unportName,statusColor);
	drawPercentColumn(context,portion,statusColor);

	if(navigator.userAgent.indexOf("MSIE 8.0")==-1 || navigator.userAgent.indexOf("MSIE")==-1){
		//动态效果
		var mask = $(this).clone();
		var _id = $(this).attr('id') + '_clone';
		mask.attr('id',_id);
		mask.css({'position':'absolute','top':'0','left':'0','z-index':'2'});
		wrap.append(mask);
		var maskCxt = (mask[0]).getContext('2d');
		maskCxt.fillStyle = '#fff';
		maskCxt.fillRect(210,65,140,200);
		var maskHeight = 200;
		var timeHeight = 20;
		var timer = setInterval(function(){
			maskCxt.clearRect(0,0,mask[0].width,mask[0].height);
			maskCxt.beginPath();
			maskHeight -= timeHeight;
			maskCxt.closePath();
			maskCxt.fillRect(210,65,140,maskHeight);
			if(maskHeight == 0){
				clearInterval(timer);
				mask.remove();
			}
		},48);
	}else if(navigator.userAgent.indexOf("MSIE 8.0")>-1){
		//动态效果
		var mask =$(this);
		var maskCxt = context;
		maskCxt.fillStyle = '#fff';
		maskCxt.fillRect(206,58,148,200);
		var maskHeight = 200;
		var timeHeight = 20;
		var timer = setInterval(function(){
			drawPercentColumn(context,portion,statusColor);
			maskCxt.beginPath();
			maskHeight -= timeHeight;
			maskCxt.closePath();
			maskCxt.fillStyle = '#fff';
			maskCxt.fillRect(206,58,148,maskHeight);
			if(maskHeight == 0){
				clearInterval(timer);
				drawPercentColumn(context,portion,statusColor);
			}
		},48);
	}

	compateCanvas(this);
};
//圆饼图
$.fn.PercentCircle = function(obj){
	var key = $(this).attr('id')?$(this).attr('id'):$(this).attr('class');
	if($('#'+key+'_wrap').length == 0){
		$(this).wrap("<div id=\""+key+"_wrap"+"\" style=\"position:relative;display:inline-block;\"></div>");
	}
	var wrap = $('#'+key+'_wrap');
	var canvas = $(this)[0];
	var context = canvas.getContext('2d');
	canvas.width = obj.width || 454;
	canvas.height = obj.height || 290;
	var title = obj.title || '';
	drawTitle(context,canvas.width,title);
	// console.log('圆饼图');
	// console.log(obj.data);
	if(obj.data.length==0 || !obj.data){
		drawError(context,canvas.width,128,"没有数据");
		return false;
	}
	
	var colors = ['#61b2ee','#ceb7f4','#36c9db','#ff7c86','#947271','#00ac28','#ffc600','#dc66a7'];
	if(colors.length<obj.data.length){
		var cl = colors.length;
		var dl = obj.data.length;
		var num = dl -cl;
		for(var i=0;i<num;i++){
			colors[cl+i] = randomColor();	
		}
	}
	
	
	//如果obj传的是value,配置百分比
	var sum = checkArray(obj.data,'value'); 
	if(sum){
		for(var i=0;i<obj.data.length;i++){
			var aValue = parseFloat(obj.data[i].value);
			obj.data[i].portion = (aValue/sum).toFixed(2);
			// console.log(obj.data[i].portion);
		}
	}
	
	//画百分比圆
	//外圈
	context.save();
	context.beginPath();
	context.lineWidth = 1;
	context.strokeStyle = '#739dbe';
	context.arc(310,150,95,0,2*Math.PI);
	context.stroke();
	context.restore();
	//画百分比
	drawPercentCircle(context,obj.data,colors);
	//白色遮挡圆
	context.beginPath();
	context.fillStyle = '#fff';
	context.arc(310,150,35,0,2*Math.PI,true);
	context.fill();

	//bar
	drawPercentCircleBar(context,obj.data,colors);
	if(navigator.userAgent.indexOf("MSIE 8.0")==-1 || navigator.userAgent.indexOf("MSIE")==-1){

		//动态效果
		var mask = $(this).clone();
		var _id = $(this).attr('id') + '_clone';
		mask.attr('id',_id);
		mask.css({'position':'absolute','top':'0','left':'0','z-index':'2'});
		wrap.append(mask);
		var maskCxt = (mask[0]).getContext('2d');
		var maskS = 0;
		var maskE = 2*Math.PI;
		maskCxt.fillStyle = '#fff';
		maskCxt.arc(310,150,96,0,maskE);
		maskCxt.fill();
		var timeAngle = 0.2*Math.PI;
		var timer = setInterval(function(){
			maskCxt.clearRect(0,0,mask[0].width,mask[0].height);
			maskS += timeAngle;
			maskCxt.beginPath();
			maskCxt.moveTo(310,150);
			maskCxt.arc(310,150,96,maskS,maskE);
			maskCxt.closePath();
			maskCxt.fill();
			if(parseInt(maskS) == parseInt(maskE)){
				clearInterval('timer');
				mask.remove();
			}
		},48);

		//监听click事件
		$(this).bind('click',function(e){
			var x = e.clientX - this.getBoundingClientRect().left;
			var y = e.clientY - this.getBoundingClientRect().top;
			var sAngle = 0;
			var eAngle = 0;
			var cxt =  this.getContext('2d');
			var data = obj.data;
			for(var i=0;i<data.length;i++){
				eAngle = eAngle + data[i].portion*Math.PI*2;
				cxt.save();
				cxt.beginPath();
				cxt.moveTo(310,150); 
				cxt.arc(310,150,88,sAngle,eAngle);
				cxt.closePath();
				sAngle = eAngle;
				if(cxt.isPointInPath(x,y)){
					// alert('isInPath'+x+":"+y);
					obj.fnClick(data[i]);
					break;
				}
			}
		});

		//监听hover事件
		$(this).bind('mousemove',function(e){
			var x = e.clientX - this.getBoundingClientRect().left;
			var y = e.clientY - this.getBoundingClientRect().top;
			if((x>222 && x<398)&&(y>62 && y<238)){
				var sAngle = 0;
				var eAngle = 0;
				var cxt =  this.getContext('2d');
				var data = obj.data;
				for(var i=0;i<data.length;i++){
					eAngle = eAngle + data[i].portion*Math.PI*2;
					cxt.save();
					cxt.beginPath();
					cxt.moveTo(310,150); 
					cxt.arc(310,150,88,sAngle,eAngle);
					cxt.closePath();
					sAngle = eAngle;
					if(cxt.isPointInPath(x,y)){
						// alert('hover isInPath'+x+":"+y);
						var key = $(this).attr('id')?$(this).attr('id'):$(this).attr('class');
						var wrap = $(this).closest('#'+key+'_wrap');
						wrap.find('.hover_tip').remove();
						var tipText = data[i].name+" : "+data[i].value;
						if(obj.tooltip){
							if(obj.tooltip.formatter){
								tipText = obj.tooltip.formatter(data[i])?obj.tooltip.formatter(data[i]):tipText;
							}
						}
						var tipStr = "<div class=\"hover_tip\">"+tipText+"</div>";
					    wrap.append(tipStr);
					    var tip = wrap.find('.hover_tip');
					    tip.css({
					    	'width':'168px',
					    	// 'max-width':'188px',
					    	'padding':'0 6px',
					    	'height':'auto',
					    	'line-height':'28px',
					    	'color':'#fff',
					    	'backgroundColor':'rgba(0,0,0,0.6)',
					    	'display':'inline-block',
					    	'border-radius':'3px',
					    	'font-size':'14px',
				    		'word-wrap':'break-word',
				    		'word-break':'break-all',
					    	'text-align':'left',
					    	'position':'absolute',
					    	'top':(y+26)+'px'
					    });
					    if(x<310){
					    	tip.css({'left':(x+12)+'px'});
					    }else{
					    	tip.css({'right':($(this)[0].width-x)+'px'});
					    }
					    $(this).css('cursor','pointer');
					}
				}
			}else{
				var tips = $(this).parent().find('.hover_tip');
				if(tips.length>0){
					tips.remove();
					 $(this).css('cursor','default');
				}
			}
		});
	}else if(navigator.userAgent.indexOf("MSIE 8.0")>-1){
		var maskCxt = context;
		var mask =$(this);
		var maskS = 0;
		var maskE = 2*Math.PI;
		maskCxt.fillStyle = '#fff';
		maskCxt.arc(310,150,96,0,maskE);
		maskCxt.fill();
		var timeAngle = 0.2*Math.PI;
		var timeout = 10;
		var timer = setInterval(function(){
			// maskCxt.clearRect(0,0,mask[0].width,mask[0].height);
			drawPercentCircle(context,obj.data,colors);
			//白色遮挡圆
			context.beginPath();
			context.fillStyle = '#fff';
			context.arc(310,150,35,0,2*Math.PI,true);
			context.fill();
			maskS += timeAngle;
			maskCxt.beginPath();
			maskCxt.moveTo(310,150);
			maskCxt.arc(310,150,96,maskS,maskE);
			maskCxt.closePath();
			maskCxt.fill();
			timeout += 1;
			if(parseInt(maskS) - parseInt(maskE) > -1){
				clearInterval(timer);
				// console.log('clearInterval');
				//画百分比圆
				//外圈
				context.save();
				context.beginPath();
				context.lineWidth = 1;
				context.strokeStyle = '#739dbe';
				context.arc(310,150,95,0,2*Math.PI);
				context.stroke();
				context.restore();
				//画百分比
				drawPercentCircle(context,obj.data,colors);
				//白色遮挡圆
				context.beginPath();
				context.fillStyle = '#fff';
				context.arc(310,150,35,0,2*Math.PI,true);
				context.fill();
			}
		},48);
	}
	compateCanvas(this);
}
//IE canvas兼容处理
function compateCanvas(param){
	if(navigator.userAgent.indexOf("MSIE 8.0")>0 ||　navigator.userAgent.indexOf("MSIE ７.0")>0 || navigator.userAgent.indexOf("MSIE 6.0")>0){
		$(param).children('div').eq(1).remove();
	};
}

