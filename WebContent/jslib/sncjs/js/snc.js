(function(jQuery){
	var $ = jQuery;
	var _snc = {
		parse:function(_obj){
			var obj = _obj || $('body'); 
			parseDrop(obj);
			parseCheckbox(obj);
		},
		
		/**重新查询的时候，刷新页码
		 * @param id : 元素ID 
		 */
		refreshPageNumber:function(id) {
			     //获取dataGrid的列表对象属性
			    var $datagrid = $("#" + id).datagrid("options");
			    if ($datagrid != undefined) {
			        $datagrid.pageNumber = 1;
			    }
			             
			    //获取dataGrid的分页对象
			    var $getPager = $("#" + id).datagrid('getPager');
			    var $pagination = $($getPager).pagination("options");
			    if ($pagination != undefined) {
			        $pagination.pageNumber = 1; 
			    }
		},
		/**
		 * 带参数setTimeout
		 * @param fun : 方法
		 * @param delay : 延迟
		 * @see 用法 setTimeout(start,1000,a[i]);
		 */
		setTimeout:function(fun, delay) {
		    if(typeof fun == 'function'){
		      var argu = Array.prototype.slice.call(arguments,2);
		       var f = (function(){
		       fun.apply(null, argu);
		       }
		    );
		     return window.setTimeout(f, delay);
		   }
		   return window.setTimeout(fun,delay);
		},
		/**
		 * 字节码转换为最小单位 保留2位小数点
		 * @param bytes : int
		 */
		bytesToSize:function (bytes) {
			if (bytes === "") return '';
			if (bytes === "0.000") return '0 B';
		    if (bytes === 0) return '0 B';
		    var k = 1024,
		        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
		        i = Math.floor(Math.log(bytes) / Math.log(k));
		   return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i];
		},
		mbsToSize:function(mbs){
			var sign = '';
			if (mbs === "") return '';
		    if (Number(mbs) === 0) return '0 MB';
		    if(mbs === undefined) return '0 MB'; 
		    if(Number(mbs)<0){
		    	mbs = Math.abs(mbs);
		    	sign = "-";
		    }
		    var k = 1024;
	        var sizes = ['MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
	        var i = Math.floor(Math.log(mbs) / Math.log(k));
	        var mbsNum = '';
	        if(i>=0){
	        	mbsNum = (mbs / Math.pow(k, i)).toFixed(2);
	        	if(mbsNum.indexOf('.00')>-1){
	        		mbsNum = mbsNum.substr(0,mbsNum.indexOf('.00'));
	        	}
	        	return sign + mbsNum + ' ' + sizes[i];
	        }else{
	        	mbsNum = Number(mbs).toFixed(2);
	        	if(mbsNum.indexOf('.00')>-1){
	        		mbsNum = mbsNum.substr(0,mbsNum.indexOf('.00'));
	        	}
	        	return sign + mbsNum + ' ' + 'MB';
	        }
		    
		},
		/*
			_str:输入字符串 2015.56 GB
			_type:'num'或'unit'
		*/
		getVolume:function(_str,_type){
			_type = _type?_type:'num';
			var arr = _str.split(' ');
			if(_type == 'num'){
				return arr[0];
			}else{
				return arr[1];
			}
		},
		/**
		 * 转义 去除SQL 语句的双引号引起的HTML 解释乱码
		 * @param str : String
		 */
		html_encode:function(str)   
		{   
			  var s = "";   
			  if (str.length == 0) return "";   
			  s = str.replace(/&/g, "&gt;");   
			  s = s.replace(/</g, "&lt;");   
			  s = s.replace(/>/g, "&gt;");   
			  s = s.replace(/ /g, "&nbsp;");   
			  s = s.replace(/\'/g, "&#39;");   
			  s = s.replace(/\"/g, "&quot;");   
			  //s = s.replace(/\n/g, "<br>");  
			  s = s.replace(/\n/g, ""); 
			  return s;   
		},  
		/**
		 *去除转义符
		 * @param str : String
		 */
		html_decode:function(str)   
		{   
			  var s = "";   
			  if (str.length == 0) return "";   
			  s = str.replace(/&gt;/g, "&");   
			  s = s.replace(/&lt;/g, "<");   
			  s = s.replace(/&gt;/g, ">");   
			  s = s.replace(/&nbsp;/g, " ");   
			  s = s.replace(/&#39;/g, "\'");   
			  s = s.replace(/&quot;/g, "\"");   
			  s = s.replace(/<br>/g, "\n");   
			  return s;   
		},
		/*
		 * 封装echart pie
		 * @param fun : object
	     * @用法chartPie({
 					dom:''				图形初始化DOM对象
					type:'1'			1:正常显示圆环	2:显示数值圆环	3:显示数值半圆环
					pieText:[			labe字体样式
						{
							text:'总分',
							textStyle:{
								//参考echart textStyle
							}
						},
						{
							text:'60',
							textStyle:{
								//参考echart textStyle
							}
						}
					]						
					pieColors:[			圆环颜色:默认正常为#39aeda,异常为#fd5287
						'#39aeda',
						'#fd5287'
					]			
					title:{				图形的title对象 包含text属性
						text:'标题1'
					}
					legendShow:true     是否显示legend
					radius:[75,55]		圆环内外半径,默认为[75,55]
					seriesData:[			图形的数据显示
						{
							name:'',
							value:''
						}
					]

	 			);
		 */
		 chartPie:function(_obj){
		 	var type = parseInt(_obj.type);
		 	/*
		 		hover toolti样式控制
		 	*/
		 	var tooltip = {     
                trigger: 'item',
                backgroundColor: 'black',
		        transitionDuration:0,
		        showDelay:2000
            };
            /*
            	是否有数据
		 	*/
		 	var hasData = _obj.seriesData&&_obj.seriesData.length>0?true:false;
            /*
            	type为2时,圆心label字号，默认为18
            */
            /*
            	_obj.pieColors报警
            */
            if(type==1 && hasData){
        		var score = parseInt(_obj.seriesData[0].value);
        		if(score<60){
        			_obj.pieColors[0] = '#d8186d';
        		}else if(score<90 && score>60){
        			_obj.pieColors[0] = '#e15f01';
        		}else if(score>90){
        			_obj.pieColors[0] = '#00b721';
        		}
        		if(_obj.pieText&&_obj.pieText[0]&&_obj.pieText[0].textStyle){
        			_obj.pieText[0].textStyle.color = _obj.pieColors[0];
        		}
            }
            var labTextSize = _obj.labTextSize?_obj.labTextSize:18;
		 	var usualColor = _obj.pieColors&&_obj.pieColors[0]?_obj.pieColors[0]:'#39aeda';
		 	var unusualColor = _obj.pieColors&&_obj.pieColors[1]?_obj.pieColors[1]:'#fd5287';
		 	var usualLableFormatter = function(params){
		 		var formatterStr = '';
		 		switch(type){
		 			case 1:
		 				formatterStr = _obj.pieText&&_obj.pieText[0]&&_obj.pieText[0].text?_obj.pieText[0].text:'';
		 				break;
	 				case 2:
	 					var data = params.series.data;
		            	var len = (data[1].value.toString().length+1)*2;
		            	var space = ''; 
		            	for(var i=0;i<len;i++){
		            		space = space + ' ';
		            	}
		                formatterStr = space+ '/ ' + (data[0].value+data[1].value);
		 				break;
	 				case 3:
	 					return '';
		                break;
		 			default:
		 				return '';
		 				break;
		 		}
		 		return formatterStr;
		 	}
		 	var unusualLableFormatter = function(params){
		 		var formatterStr = '';
		 		switch(type){
		 			case 1:
		 				formatterStr = _obj.pieText&&_obj.pieText[1]&&_obj.pieText[1].text?_obj.pieText[1].text:'';
		 				break;
	 				case 2:
	 					var data = params.series.data;
	 					var sum = data[0].value+data[1].value;
		            	var len = (sum.toString().length+1.5)*2;
		            	var space = ''; 
		            	for(var i=0;i<len;i++){
		            		space = space + ' ';
		            	}
		                formatterStr = (data[1].value)+space;
		 				break;
	 				case 3:
	 					return '';
		                break;
		 			default:
		 				return '';
		 				break;
		 		}
		 		return formatterStr;
		 	}
		 	/*正常*/
			var usualLable = {
			    normal : {
			    	label : {
			            show : true,
			            position : 'center',
			            formatter :usualLableFormatter,
			            textStyle:_obj.pieText&&_obj.pieText[0]&&_obj.pieText[0].textStyle?_obj.pieText[0].textStyle:{
						            	color: '#666',
						            	fontSize:labTextSize,
						            	fontWeight:'bolder'
					            }
			        },
			        labelLine : {
			            show : false
			        },
			        color: usualColor
			    }
			};
			/*异常*/
			var unusualLable = {
			    normal : {
			        color: unusualColor,
			        label : {
			            show : true,
			            position : 'center',
			            formatter :unusualLableFormatter,
			            textStyle:_obj.pieText&&_obj.pieText[1]&&_obj.pieText[1].textStyle?_obj.pieText[1].textStyle:{
					            	color: unusualColor,
					            	fontSize:labTextSize,
					            	fontWeight:'bolder'
					            }
			        },
			        labelLine : {
			            show : false
			        }
			    },
			    emphasis: {
			        color: 'rgba(0,0,0,0)'
			    }
			};
			/*不显示*/
			var hideStyle = {
			    normal : {
			        color: 'rgba(0,0,0,0)',
			        label: {show:false},
			        labelLine: {show:false}
			    },
			    emphasis : {
			        color: 'rgba(0,0,0,0)'
			    }
			};
			var radius = _obj.radius?_obj.radius:[75, 55];
			var centerMinY = _obj.legendShow?parseInt(radius[0])+20:parseInt(radius[0]);
			var centerPositionY = parseInt((_obj.dom.height()-radius[0])*0.5)<centerMinY?centerMinY:parseInt((_obj.dom.height()-radius[0])*0.5);
			var centerPosition = type==3?[radius[0],centerPositionY]:['50%','50%'];	//圆心位置
			titleTextSize = _obj.title.textStyle?parseInt(_obj.title.textStyle.fontSize):14;
			var titleY = type==3?parseInt(centerPositionY-titleTextSize):parseInt(_obj.dom.height()*0.5+radius[0]+titleTextSize*0.5);
			var titleX = type==3?parseInt(radius[0]-_obj.title.text.length*titleTextSize*0.5):'center';
			/*
				title数据处理
			*/
			if(_obj.pieText&&_obj.pieText[0]&&!isNaN(_obj.pieText[0].text)){
				_obj.pieText[0].text = Number(_obj.pieText[0].text); 
				_obj.pieText[0].text = _obj.pieText[0].text.isInt()?_obj.pieText[0].text:_obj.pieText[0].text.toFixed(2);
			}
			option = {
			    legend: {
			        x : 0,
			        y : 0,
			        data:[
			            '正常','异常'
			        ],
			        selectedMode:false,
			        show:_obj.legendShow?_obj.legendShow:false
			    },
			    title : {
			        text: _obj.title.text?_obj.title.text:'',
			        x: titleX,
			        y:titleY,
			        textStyle: _obj.title.textStyle?
			        		   _obj.title.textStyle:
			        		   {
					        		color:'#a6a5ab',
					        		fontSize:'16'
				        	   },
			        show:_obj.title.text?true:false
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter:function(tip){
			        	// console.log(tip);
			        	var tipStr = '';
			        	if(type == 1){
			        		if(tip.name.indexOf("异常")>-1){
			        			tipStr = tip.value;
			        		}else {
			        			tipStr = tip.name+':'+tip.value+'分';	
			        		}
			        	}else{
			        		tipStr = _obj.title.text+tip.name+':'+tip.value+'('+tip.percent+'%)';
			        	}
			        	return tipStr;
					}
			    },
			    series : [
			        {
			            type : 'pie',
			            center : centerPosition,
			            radius : radius,
			            x: '0%', // for funnel
			            // itemStyle : labelFromatter,
			            data : [
			            	// {
			            	// 	name:'正常',
			            	// 	value:_obj.usual,
			            	// 	itemStyle : usualLable,
			            	// 	tooltip : tooltip
			            	// },
			             //    {
			             //    	name:'异常',
			             //    	value:_obj.unusual, 
			             //    	itemStyle : unusualLable,
			             //    	tooltip : tooltip
			             //    }
			            ]
			        }
			    ],
			    noDataLoadingOption:{
			    	text:'暂无数据',
			    	// textStyle:{
			    	// 	align:'center',
			    	// 	baseline:'middle'
			    	// },
			    	// x:'center',
			    	// y:'center',
			    	effect:'bubble'
			    }
			}
			var seriesObj = option.series[0];
			var sum = 0;//type为3时使用
			for(var i=0;i<_obj.seriesData.length;i++){
				sum = sum + _obj.seriesData[i].value;
				var seriesItem = {
					name:_obj.seriesData[i].name,
					value:_obj.seriesData[i].value.isInt()?_obj.seriesData[i].value:_obj.seriesData[i].value.toFixed(2),
					tooltip : tooltip,
					itemStyle:i==0?usualLable:unusualLable
				};
				seriesObj.data.push(seriesItem);
			}
			if(type == 3 && hasData){
				seriesObj.startAngle = 0;
				seriesObj.clockWise = false;
				seriesObj.data.push({
					name:'space',
					value:sum,
					itemStyle:hideStyle,
					tooltip:{
						show:false
					}
				});
			}
			if(hasData){
				var piesChart = echarts.init(_obj.dom[0],"macarons");
				piesChart.setOption(option,true);
			}else{
				_obj.dom.css({
					'text-align':'center',
					'line-height':_obj.dom.height()+'px'
				});
				_obj.dom.text("暂无数据");
			}
			//echart初始化会将对象内部的DOM去除,所以后插入
			if(type == 3 && hasData){
				_obj.dom.css('position','relative');
				var valStr =    "<div style=\"position:absolute;font-size:"+titleTextSize+"px;top:"+titleY+"px;left:"+parseInt(radius[0]*2+12)+"px;\">"+
								"	<span style=\"color:"+unusualColor+"\">"+seriesObj.data[1].value+"</span>"+
								"	<span style=\"color:"+"#666;"+"\">"+'/'+seriesObj.data[2].value+"</span>"+
							 	"</div>";
			 	_obj.dom.append(valStr);
			}
			_obj.dom.children('div').css('overflow','visible');
		 	var styleStr = '<style>.echarts-tooltip{ z-index: 99; }</style>';
		 	_obj.dom.append(styleStr);
		},
		/*
			echarts dataZoom样式优化
			参数:{},参考echart dataZoom配置项
			返回:{},优化样式后的dataZoom配置项
		*/
		dataZoomInit:function(_obj){
			_obj.backgroundColor = 'rgba(255,255,255,1)';
	        _obj.dataBackgroundColor = 'rgba(216,233,247,1)';
	        _obj.fillerColor = 'rgba(216,233,247,0.5)';
	        _obj.handleColor = 'rgba(91,165,222,1)';
	        _obj.height = 16;
	        return _obj;
		},
		dataZoomInitLock:function(_option,num,lock){
			var dataNum = option.xAxis[0].data.length;
			if(dataNum<=num){
				option.dataZoom.start = 0;
			}else{
				option.dataZoom.start = 100 - parseInt(num*100/dataNum);
				if(lock) option.dataZoom.zoomLock = true;
			}
		},
		noDataLoadingOption:function(){
			return {
				text: '暂无数据',
				effect:'bubble',
				effectOption : {
					effect: {
						n: 0
					}
				},
				textStyle: {
					fontSize: 26,
					fontWeight: 'bold'
				}
			};
		},
		arraySum:function(arr,key){
			key = key?key:'value';
			var sum = 0;
			for(var i=0;i<arr.length;i++){
				var num = arr[i][key]?arr[i][key]:arr[i];
				num = Number(num);
				sum += num;
			}
			return sum;
		},
		getCurrentTime:function(){
			// 2006-09-10
			var cur = new Date();
			return cur.getFullYear()+'-'+(cur.getMonth()+1)+'-'+cur.getDate()+' '+cur.getHours()+':00:00'; 
		}
	};
	
//	去除左右空格
	function trim(str){ //删除左右两端的空格
　　   	return str.replace(/(^\s*)|(\s*$)/g, "");
　　  }
	$.fn.trim = function(str){
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	//下拉框
	//{'label':'展示的名字','value':'对应的值'}
	//label= 下拉框默认显示label 
	//val=  默认值 
	//items 下拉框选项
	
	//下拉框初始化
	//下拉框类型type	0或undefined：默认下拉框	1：可选可输入
	function parseDrop(_obj){
		var obj = _obj || $('body');
		var drops = obj.find('.snc_drop');
		if(drops.length == 0){
			return false;
		}
		dropBind(obj);
		drops.find('.snc_drop_point').remove();
		drops.find('.snc_submit').remove();
		drops.find('.icon_triangle_area').remove();
		drops.find('.snc_drop_space').remove();

		var str_0 = "<div class=\"snc_drop_point\"></div>";
		var str_1 = "<input class=\"snc_drop_point\" style=\"top:0 !important;background:none !important;font-size:14px;text-indent:8px;color:#aaa;height:34px;line-height:34px;padding-bottom:0;\" />";
		var str_space = "<div class=\"snc_drop_space\" style=\"width:100%;height:4px;position:absolute;left:0;bottom:-4px;\"></div>";
		var triangle = "<div class=\"icon_triangle_area\"><i class=\"icon_triangle_b\"></i></div>";
		drops.each(function(idx,elem){
			$(elem).find(".drop_list").siblings().remove();
			var defaultVal = $(elem).attr('val');
			var defaultLab = $(elem).attr('label');
			var name = $(elem).attr('name');
			var nameStr = name?"name="+name:'';
			var submit = "<input class=\"snc_submit\" style=\"width:0;height:0;display:none;\" "+nameStr+">";
			var type = $(elem).attr('type');
			if(type==0 || type==undefined){
				$(elem).append(str_0);
			}else if(type==1){
				$(elem).append(str_1);
				$(elem).children('.snc_drop_point').css({'top':0,'padding-bottom':0,'font-size':'14px'});
			}
			$(elem).append(triangle);
			$(elem).append(submit);
			$(elem).append(str_space);
			$(elem).find('.snc_submit').val(defaultVal);
			
			if(defaultLab!=undefined){
				if(type==0 || type==undefined){
					$(elem).children('.snc_drop_point').text(defaultLab);	
				}else if(type==1){
					var typeVal = defaultLab; 
					if(typeVal.indexOf('请')>-1 || typeVal.indexOf('选择')>-1 || typeVal.indexOf('输入')>-1){
						typeVal = '';
					};
					$(elem).children('.snc_drop_point').val(typeVal);
					$(elem).children('.snc_drop_point').attr('placeholder','请选择或输入');
				}
			}
			
			$(elem).bind('mouseleave',function(){
				snc_dropMouseLeave(this);
			});
		});	
	}
	//打开下拉框
	function openDrop(param){
		var drop = $(param).closest('.snc_drop');
		var hasList = drop.children('.drop_list').length;
		if(hasList > 0){
			drop.children('.drop_list').show();
			//兼容IE6、7 z-index问题
			drop.css('z-index',99);
		}
	}
	$('.snc_drop>.snc_drop_point').live('click',function(){
		openDrop(this);
	});
	$('.snc_drop>.icon_triangle_area').live('click',function(){
		openDrop(this);
	})
	//type:1	可输入下拉框
	$('.snc_drop>.snc_drop_point').live('keyup',function(e){
		var drop = $(this).parent('.snc_drop');
		var dropList = drop.find('.drop_list');
		var type = drop.attr('type');
		if(type == 1){
			var val = $(this).val();
			drop.attr('val',val);
			drop.find('.snc_submit').val(val);
			if(val == ''){
				dropList.find('li').removeClass('hidden');
			}else{
				dropList.find('li').each(function(idx,elem){
					var liLab = $(elem).text();
					if(liLab.indexOf(val)==0){
						$(elem).removeClass('hidden');
					}else{
						$(elem).addClass('hidden');
					}
				});
			}
		}
		// console.log(e.keyCode);
		var curLi = dropList.find('.drop_hover:not(.hidden)');
		var Lis = dropList.find('li:not(.hidden)');
		var idxItem = curLi.length>0?Lis.index(curLi):-1;
		var code = e.keyCode;
		if(code == 40){
			idxItem += 1;
			Lis.removeClass('drop_hover');
			Lis.eq(idxItem).addClass('drop_hover');
			dropList.scrollTop(parseInt(idxItem/5)*170);
		}else if(code == 38){
			idxItem -= 1;
			Lis.removeClass('drop_hover');
			Lis.eq(idxItem).addClass('drop_hover');
			dropList.scrollTop(parseInt(idxItem/5)*170);
		}else if(code == 13){
			if(idxItem == -1){
				openDrop(this);
			}else{
				Lis.eq(idxItem).click();
			};		
		};
	});
	
	//.snc_drop mouseleave效果
	function snc_dropMouseLeave(_drop){
		var drop = $(_drop);
		drop.find('.drop_list').hide();
		drop.css('z-index',1);
	}
	
	
	//点击选择
	function dropSelect(param,drop_fn){
		var obj = $(param);
		var lab = trim(obj.text());
		var val = obj.attr('val') || lab;
		var drop = obj.closest('.snc_drop');
		var type = drop.attr('type');
		drop.attr('label',lab);
		drop.attr('val',val);
		drop.find('.snc_submit').val(val);
		drop.find(".drop_list").hide();
		drop.css('z-index',1);
		if(type==0 || type==undefined){
			drop.find('.snc_drop_point').text(lab);
		}else if(type==1){
			drop.find('.snc_drop_point').val(lab);
		}
		if(drop_fn != '' && drop_fn != undefined){
			drop_fn();
		}
	}
	function dropBind(_obj){
		_obj.find('.snc_drop>.drop_list>li').bind('click',function(){
			dropSelect(this);
		})
		_obj.find('.snc_drop>.drop_list>li').bind('mouseenter',function(){
			$(this).addClass('drop_hover');
		})
		_obj.find('.snc_drop>.drop_list>li').bind('mouseleave',function(){
			$(this).removeClass('drop_hover');
		});	
	}
	
//	dropInsert参数data的格式	[{'label':'data_1','val':'1'},{'label':'data_2','val':'2'},{'label':'data_3','val':'2'}]
//	attrs{label:'',val:''} 
//  obj匹配参数
//	data,drop_fn,attrs,obj,isSort
	$.fn.dropInsert = function(data,drop_fn,attrs,obj,isSort){
		var drop = $(this);
		var booleanSort = isSort!=undefined?isSort:true;
		if(data != undefined && data != ''){
			if(booleanSort){
				data = data.sort(function(pre,next){
					if(obj){
						try{
							return pre[obj.label].charCodeAt(0) - next[obj.label].charCodeAt(0);
						}catch(e){
							//TODO handle the exception
							console.log(e);
							console.log(pre);
							console.log(next);
						}
					}else{
						return pre.label.charCodeAt(0) - next.label.charCodeAt(0);	
					}	
				});	
			}//sort End
			var items = data;
			var str = '';
			var label= '';
			var val = '';
			clearDropList(this);
			drop.append("<ul class=\"drop_list\"></ul>");
			var droplist = drop.find('.drop_list');
			for(var i=0;i<data.length;i++){
				if(obj){
					var _option = items[i];
					label = _option[obj.label];
					val = _option[obj.val];
				}else{
					label = items[i].label;
					val = items[i].val;	
				}
				str = "<li val="+val+"><a href=\"javascript:;\">"+label+"</a></li>";		
				droplist.append(str);
			}
			drop.bind('mouseleave',function(){
				snc_dropMouseLeave(this);
			});
			droplist.children('li').bind('click',function(){
				dropSelect(this,drop_fn);
			}).bind('mouseenter',function(){
				$(this).addClass('drop_hover');
			}).bind('mouseleave',function(){
				$(this).removeClass('drop_hover');
			});	
		}
		//处理默认值
		if(attrs){
			var _attrs = attrs != undefined && attrs != ''?attrs:{'label':'请选择',val:0};
			drop.attr('label',_attrs.label);
			var type= drop.attr('type');
			if(type == 0 || type == undefined){
				drop.children('.snc_drop_point').text(_attrs.label);	
			}else if(type == 1){
				drop.children('.snc_drop_point').val('');
				drop.children('.snc_drop_point').attr('placeholder',_attrs.label);
			}
			drop.attr('val',_attrs.val);
			drop.find('.snc_submit').val(_attrs.val);	
		}
	}
	
	//重置下拉框的值
	//	obj = {
	//		label:'',
	//		val:''
	//	}
	$.fn.dropReset = function(obj){
		var label = obj.label;
		var val = obj.val;
		var drop = $(this);
		var type = $(this).attr('type');
		
		drop.attr('label',label);
		drop.attr('val',val);
		drop.find('.snc_submit').val(val);
		if(type == 0 ||type == undefined){
			drop.find('.snc_drop_point').text(label);
		}else if(type == 1){
			drop.find('.snc_drop_point').val(label);
		}
		
	}
	
	//删除下拉框
	$.fn.dropRemove = function(_str,_val){
		var lab = _str || '';
		var val = _val || 0;
		var type = $(this).attr('type');
		clearDropList(this);
		$(this).find('.drop_list>li').unbind('click');
		$(this).attr('label',lab);
		$(this).find('.snc_submit').val(val);
		$(this).attr('val',val);
		if(type == 0 || type==undefined){
			$(this).children('.snc_drop_point').text(lab);	
		}else if(type == 1){
			$(this).children('.snc_drop_point').val(lab);
			$(this).children('.snc_drop_point').attr('placeholder',lab);
		}
	}
	
	function clearDropList(param){
		var hasList = $(param).find('.drop_list').length;
		if(hasList > 0){
			$(param).find('.drop_list').remove();	
		}
	}
	
//	单选框
//	单选框初始化
	function parseCheckbox(_obj){
		var obj = _obj || $('body');
		var checkboxs = obj.find('.snc_checkbox');
		if(checkboxs.length == 0){
			return false;
		}
		var label = '';
		var val = '';
		var ischecked = '';
		var name = '';
		checkboxs.each(function(idx,elem){
			var chk = $(elem);
			chk.bind('click',function(){
				chkClick(this);
			});
			if(chk.children('.snc_submit').length == 0){
				label = chk.attr('label');
				val = chk.attr('val');
				name = chk.attr('name');
				ischecked = chk.attr('ischecked');
				var submit = "<input class=\"snc_submit\" style=\"width:0;height:0;display:none;\">";
				chk.append(submit);
				if(ischecked == 'true'){
					chk.addClass('snc_checkbox_checked');
					chk.find('.snc_submit').val(val);
					chk.find('.snc_submit').attr('name',name);
				}
				if(label != '' && label != undefined){
					var str = "<label class=\"snc_checkbox_label fl\">"+label+"</label>" 
					chk.after(str);
				}	
			}
		});
		
	}
	
//	动态生成chk
//	obj = {
//		name:'',
//		label:'',
//		val:'',
//		ischecked:''
//	}
	$.fn.chkInsert = function(obj){
		var chk = "<div class=\"snc_checkbox fl\" name=\""+obj.name+"\" label=\""+obj.label+"\" val=\""+obj.val+"\" ischecked=\""+obj.ischecked+"\"></div>";
		$(this).append(chk);
		parseCheckbox($(this));
	}
	
	
	//点击单选框
	function chkClick(param){
		var chk = $(param);
		var status = chk.attr('ischecked');
		var name = chk.attr('name');
		var val = chk.attr('val');
		if(status == 'true'){
			chk.removeClass('snc_checkbox_checked');
			chk.attr('ischecked','');
			chk.find('.snc_submit').val('');
			chk.find('.snc_submit').removeAttr('name');
		}
		else{
			chk.addClass('snc_checkbox_checked');
			chk.attr('ischecked','true');
			chk.find('.snc_submit').val(val);
			chk.find('.snc_submit').attr('name',name);
		}
	}
	
	//JSON字符串显示
	$.fn.formatJson = function(json, options) {
		var reg = null,
			formatted = '',
			pad = 0,
			PADDING = '    ';
	 
		options = options || {};
		options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
		options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
		if (typeof json !== 'string') {
			json = JSON.stringify(json);
		} else {
			json = JSON.parse(json);
			json = JSON.stringify(json);
		}
	 
		reg = /([\{\}])/g;
		json = json.replace(reg, '\r\n$1\r\n');
	 
		reg = /([\[\]])/g;
		json = json.replace(reg, '\r\n$1\r\n');
	 
		reg = /(\,)/g;
		json = json.replace(reg, '$1\r\n');

		reg = /(\r\n\r\n)/g;
		json = json.replace(reg, '\r\n');
	 
		reg = /\r\n\,/g;
		json = json.replace(reg, ',');
	 
		if (!options.newlineAfterColonIfBeforeBraceOrBracket) {			
			reg = /\:\r\n\{/g;
			json = json.replace(reg, ':{');
			reg = /\:\r\n\[/g;
			json = json.replace(reg, ':[');
		}
		if (options.spaceAfterColon) {			
			reg = /\:/g;
			json = json.replace(reg, ': ');
		}
	 
		$.each(json.split('\r\n'), function(index, node) {
			var i = 0,
				indent = 0,
				padding = '';
	 
			if (node.match(/\{$/) || node.match(/\[$/)) {
				indent = 1;
			} else if (node.match(/\}/) || node.match(/\]/)) {
				if (pad !== 0) {
					pad -= 1;
				}
			} else {
				indent = 0;
			}
	 
			for (i = 0; i < pad; i++) {
				padding += PADDING;
			}
	 
			formatted += padding + node + '\r\n';
			pad += indent;
		});
	 
		return formatted;
	};
	
	//检查必填属性
	$.fn.checkRequired = function(objs){
		var result = true;	//假设默认为true 全部通过检测
		objs.each(function(idx,elem){
			var required = $(elem).attr('isrequired');	//若dom元素的isrequired属性为ture时,则为必填
			if(required == 'true'){
				var val = $(elem).val();
				if(val == ''){
					result = false;
					$(elem).css('border','1px solid #ff2846');
				}else{
					$(elem).css('border','1px solid #aaa');
				}
			}
		});
		return result;
	}
	
	//生成uuid
	$.fn.getuuid = function(len,radix){
		var _len = len;
		var _radix = radix;
		var uuidStr = $.fn.uuid(_len,_radix);
		for(var i=0;i<4;i++){
			uuidStr = uuidStr.replace('-','');
		}
		return uuidStr;
	}
	
	var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	$.fn.uuid = function (len, radix) {
		var chars = CHARS, uuid = [], i;
    	radix = radix || chars.length;
    	if (len) {
	      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
	    } else {
	      // rfc4122, version 4 form
	      var r;
	 
	      // rfc4122 requires these characters
	      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	      uuid[14] = '4';
	 
	      // Fill in random data.  At i==19 set the high bits of clock sequence as
	      // per rfc4122, sec. 4.1.5
	      for (i = 0; i < 36; i++) {
	        if (!uuid[i]) {
	          r = 0 | Math.random()*16;
	          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	        }
	      }
    	}
	    return uuid.join('');
	};
	  
	  Math.uuidFast = function() {
	    var chars = CHARS, uuid = new Array(36), rnd=0, r;
	    for (var i = 0; i < 36; i++) {
	      if (i==8 || i==13 ||  i==18 || i==23) {
	        uuid[i] = '-';
	      } else if (i==14) {
	        uuid[i] = '4';
	      } else {
	        if (rnd <= 0x02) rnd = 0x2000000 + (Math.random()*0x1000000)|0;
	        r = rnd & 0xf;
	        rnd = rnd >> 4;
	        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	      }
	    }
	    return uuid.join('');
	  };
	 
	  Math.uuidCompact = function() {
	    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	      var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	      return v.toString(16);
	    });
	  };
	
	//弹窗
	//	obj = {
	//		title:'',
	//      text:'',
	//		sure:null,
	//		cancel:null,
	//      sureText:'',
	//      cancelText:'',
	//		hidden:'',
	//		type:''
	//	}
	$.fn.popup = function(obj){
		var popStrt = "";
		var maskStr = "<div class=\"mask\" style=\"position:fixed;width:100%;height:100%;top:0;left:0;z-index: 2001;\"></div>";
		obj.type = obj.type?obj.type:1;
		obj.sureText = obj.sureText?obj.sureText:'确认';
		obj.cancelText = obj.cancelText?obj.cancelText:'取消';
		obj.sure = typeof obj.sure == 'function'?obj.sure:'';
		obj.cancel = typeof obj.cancel == 'function'?obj.cancel:'';
		obj.hidden = obj.hidden || typeof obj.cancel == 'function'?true:false; 
		if(obj.type == 1){
			popStrt = "<div style=\"position:fixed;top:108px;left:50%;margin-left:-152px;z-index:2002;width:304px;min-height:200px;background:#fff;font-family:Microsoft YaHei;font-size:14px;\">"+
							"<p style=\"height:42px;line-height:42px;text-indent:20px;font-size:16px;border-bottom:1px solid #f1f1f1;\">"+
								obj.title+
								"<b id=\"popClose\" class=\"popup_close\" style=\"float:right;margin-right:10px;margin-top:13px;\"></b>"+
							"</p>"+
							"<div style=\"padding:18px 20px 32px 20px\">"+
								"<div id=\"popText\" style=\"height:auto;min-height:72px;line-height:24px;font-size:14px;margin-bottom:20px;text-align:center;\">测试测试文本</div>"+
								"<div style=\"text-align:center;\">"+
									"<span>"+
										"<a id=\"popCancel\" style=\"line-height:28px;margin:0 10px;width:80px;height:28px;display:inline-block;color:#fff;text-align:center;background:#1a96de;border-radius:3px;\" href=\"javascript:;\">取消</a>"+
										"<a id=\"popSure\" style=\"line-height:28px;margin:0 10px;width:80px;height:28px;display:inline-block;color:#fff;text-align:center;background:#1a96de;border-radius:3px;\" href=\"javascript:;\">确定</a>"+
									"</span>"+
								"</div>"+
							"</div>"+
						"</div>";
		}
		var _id = $.fn.uuid(8,16);
		var popDomStr = "<div id=\"popup"+_id+"\" style=\"position:absolute;top:0;left:0;\">" + maskStr + popStrt + "</div>";
		$('body').append(popDomStr);
		var popDom = $('#popup'+_id);
		popDom.find('#popSure').bind('click',function(){
			return function(){
				if (obj.sure != ''){
					obj.sure();
				};
				$.fn.popClose(_id);
			}();
		});
		popDom.find('#popCancel').bind('click',function(){
			return function(){
				if (obj.cancel != ''){
					obj.cancel();
				};
				$.fn.popClose(_id);
			}();
		});
		popDom.find('#popClose').bind('click',function(){
			return $.fn.popClose(_id);
		})
		$('#popText').html(obj.text);

		if (!obj.hidden){
			$('#popCancel').hide();
		};
	}
	$.fn.popClose = function(_uuid){
		$('body').find('#popup'+_uuid).remove();
	}

	$.fn.brBotton =	function (obj){
		var dom = obj.dom;
		var domHeihgt = obj.height?obj.height:'74px';
		dom.css('height',domHeihgt);
		dom.find('br').remove();
		dom.find('select').css({'height':'28px','border-color':'#bbb','border-radius':'3px','color':'#666'});
		dom.find('.easyui-combobox').css({'width':'158px'});
		dom.children().children('input').css({'width':'128px','height':'26px','border':'1px solid #bbb','border-radius':'3px'});
		var end = obj.end;
		if(end && end !=[]){
			var btns = dom.find('.easyui-linkbutton');
			var idx = btns.index(end);
			btns.each(function(_idx,elem){
				if(_idx == idx){
					$(elem).after('<div style="clear:both;margin-top:4px;"><div>');
				}else if(_idx > idx){
					var parWrap = $(elem).parent();
					$(elem).css({'margin-right':'8px'});
					parWrap.append($(elem));
				}
			});
			dom.append('<div class=\"toolwrap\" style=\"float:left;\"><div>');
			var toolwrap = dom.find('.toolwrap');
			var clds = dom.find('.oldwrap').children();
			clds.each(function(idx, elem) {
				$(elem).css('margin-right','8px');
				toolwrap.append($(elem));
			});
			dom.find('.oldwrap').remove();
		}
	}

	$.fn.toolbarInit = function(obj){
		var toolbar = obj?obj:$(this);
		
	}
	
	$.fn.stringDate = function(_date){
		var obj = {
			toDate:'',
			toTime:''
		};
		obj.toDate = _date.getFullYear()+'-'+(_date.getMonth()+1)+'-'+_date.getDate();
		obj.toTime = _date.getHours()+':'+_date.getMinutes()+':'+_date.getSeconds();
		return obj;
	}
	$.fn.rowHead = function(value, row, index){
		return 	"<div style=\"padding:0 5px\" title=\""+value+"\">"+
    				"<input style=\"font-size:12px;color:#007dc4;font-weight:600;width:100%;display:inline-block;border:0;background:none;\" type=\"text\" value=\""+value+"\" readonly=\"true\">"+
    		   	"<div>";
	}
	$.fn.rowLong = function(value, row, index){
		return 	"<div style=\"padding:0 5px;\" title=\""+value+"\">"+
    				"<input style=\"font-size:12px;color:#666 ;font-weight:normal;width:100%;display:inline-block;border:0;background:none;\" type=\"text\" value=\""+value+"\" readonly=\"true\">"+
    		   	"<div>";
	}
	$.fn.rowText = function(value, row, index){
		return 	"<div style=\"padding:0 5px;height:auto;word-break: break-all; word-wrap: break-word; white-space: normal;\" title=\""+value+"\">"+
    				value+
    		   	"<div>";
	}

	//按钮编辑权限控制
	// _config = {
	// 	edit:'',
	//	editCancel:'',
	// 	save:''
	// }
	$.fn.editBtnCtrl = function(_config){
		var editBtn = $(_config.edit);
		var saveBtn = $(_config.save);
		var hasEdit = editBtn.attr('disabled'); 
		if(hasEdit == undefined){
			editBtn.click();
		}else{
			saveBtn.hide();
		}
		editBtn.hide();
		if(_config.editCancel){
			var editCancelBtn = $(_config.editCancel);
			var _fn = editCancelBtn.attr('onclick');
			var fn = _fn + ";" + "$('" +_config.edit+ "').click()"; 
			editCancelBtn.attr('onclick',fn)
		}
	}

	Number.prototype.isInt = function(){
		var value = this.valueOf();
    	var re = /^[1-9]+[0-9]*]*$/;  
     	if (!re.test(value) && value != 0){  
        	return false;  
     	}else{
			return true;
		}  
	}
	String.prototype.isInt = function(){
		var value = this.valueOf();
    	var re = /^[1-9]+[0-9]*]*$/;  
     	if (!re.test(value) && value != 0){  
        	return false;  
     	}else{
			return true;
		}  
	}

	//定义replaceAll
	String.prototype.replaceAll = function(str1,str2){
	  var str = this; 
	  var result = str.replace(eval("/"+str1+"/gi"),str2);
	  return result;
	}  

//	执行初始化
	$.extend({snc:_snc});
	$.snc.parse();
	
	
})(jQuery);
	
