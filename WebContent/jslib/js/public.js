var rowData;//datagrid行记录数据对象
	
	/**
	 * 打开新窗口
	 */
	function pagerFilter(data) {
		if (typeof data.length == 'number' && typeof data.splice == 'function') {
			data = {
				total : data.length,
				rows : data
			};
		}
		var dg = $(this);
		var opts = dg.datagrid('options');
		var pager = dg.datagrid('getPager');
		pager.pagination({
			onSelectPage : function(pageNum, pageSize) {
				opts.pageNumber = pageNum;
				opts.pageSize = pageSize;
				pager.pagination('refresh', {
					pageNumber : pageNum,
					pageSize : pageSize
				});
				dg.datagrid('loadData', data);
				dg.datagrid({loadMsg : '正在加载数据...'});

				//刷新页码显示
				_pager = dg.datagrid('getPager');
				totalPage = Math.ceil(data.total/pageSize);
				totalNum = data.total;
				startNum = (pageNum-1)*pageSize+1;
				endNum = (pageNum*pageSize)%pageSize==0?pageNum*pageSize:(pageNum-1)*pageSize+totalNum%pageSize;
				_pager.find('.pagination-num').parent().next().children('span').text('共'+totalPage+'页');
				_pager.find('.pagination-info').text('显示'+startNum+'到'+endNum+',共'+totalNum+'记录');
			}
		});
		if (!data.originalRows) {
			data.originalRows = (data.rows);
		}
		var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
		var end = start + parseInt(opts.pageSize);
        if ($.isArray(data.originalRows)) {
		    data.rows = (data.originalRows.slice(start, end));
        }
		return data;
	}
	
	
	/**
	 * 打开新窗口
	 */
	function OpenFrame(id,url,width,height,title) {
		if($('#'+id).length == 0){
			$('body').append("<div id=\""+id+"\"></div>");
		}
	    $('#'+id).window({                    
	    	href:basePath+url,  
	    	title:title,  
	        width: width,
	        height: height,
	        top: ($(window).height() - height) * 0.5 + $(document).scrollTop(),
	        left: ($(window).width() - width) * 0.5,
	       // closed:true,
	        minimizable: false,
	        maximizable: false,
	        collapsible:false,
	        resizable: false,
	        modal: true
	    });
	}
	
	/**
	 * 打开新窗口,并初始化窗口样式
	 */
	function OpenFrameLoad(id,url,width,height,title,load) {
	    $('#'+id).window({                    
	    	href:basePath+url,  
	    	title:title,  
	        width: width,
	        height: height,
	        top: ($(window).height() - height) * 0.5,
	        left: ($(window).width() - width) * 0.5,
	       // closed:true,
	        minimizable: false,
	        maximizable: false,
	        collapsible:false,
	        resizable: false,
	        modal: true,
	        onLoad:load
	    });
	}
	/**
	 * 窗口关闭
	 */
	function closeFrameLoad(id) {
		$('#'+id).dialog('close');
		$('#'+id).html('');
	}
	/**
	 * 窗口关闭
	 */
	function closeWin() {
		$('#win').dialog('close');
		$('#win').html('');
	}
	
	/**
	 * 父iframe窗口关闭
	 */
	function closeParentWin(){
		window.parent.closeWin();
	}
	
	/**
	 * 窗口关闭
	 */
	function closeById(id) {
		$('#'+id).dialog('close');
		$('#'+id).html('');
	}
	
	/**
	 * 一般初始化
	 */
	function init(){
		$('#fm').form('clear');
		$('#fm').form('load', rowData);
	}
	
	/**
	 * 清理缓存
	 */
	function clear(){
		$('#fm').form('clear');
	}
	
	/**
	 * datagrid数据初始化——后端代码分页
	 */
	function datagrid_init(id,title,url,editData,isSingleSelect){
		$('#'+id).datagrid({
	    	title : title,//标题
	        fit : true,// 自动大小
	        pageSize : 10,//默认选择的分页是每页5行数据
	       	pageList : [ 10, 20, 50, 100 ],//可以选择的分页集合
	        nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
	        striped : true,//设置为true将交替显示行背景。
	        collapsible : false,//显示可折叠按钮
	        toolbar:"#toolbar",//在添加 增添、删除、修改操作的按钮要用到这个
	        url:basePath+url,//url调用Action方法
	        onDblClickRow:editData,
	        singleSelect:isSingleSelect,//为true时只能选择单行
	        selectOnCheck: true,
	        checkOnSelect: true,
	        fitColumns:true,//允许表格自动缩放，以适应父容器
	        remoteSort : false,
	        pagination : true,//分页
	        rownumbers : true//行数
	    });
	}
	
	/**
	 * datagrid数据查询——带前端分页
	 */
	function datagrid_init_pager(id,title,url,editData,isSingleSelect){
		$('#'+id).datagrid({
			title : title,//标题
			fit : true,// 自动大小
			pageSize : 10,//默认选择的分页是每页5行数据
			pageList : [ 10, 20, 50, 100 ],//可以选择的分页集合
			nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
			striped : true,//设置为true将交替显示行背景。
			collapsible : false,//显示可折叠按钮
			toolbar:"#toolbar",//在添加 增添、删除、修改操作的按钮要用到这个
			url:basePath+url,//url调用Action方法
			onDblClickRow:editData,
			singleSelect:isSingleSelect,//为true时只能选择单行
			selectOnCheck: true,
			checkOnSelect: true,
			fitColumns:true,//允许表格自动缩放，以适应父容器
			remoteSort : false,
			pagination : true,//分页
			rownumbers : true,//行数
			loadFilter : pagerFilter
		});
	}
	
	/**
	 * datagrid数据查询——带前端分页_自定义按钮
	 */
	function datagrid_init_pager_toolbar(id,title,url,editData,isSingleSelect,toolbar){
		$('#'+id).datagrid({
			title : title,//标题
			fit : true,// 自动大小
			pageSize : 10,//默认选择的分页是每页5行数据
			pageList : [ 10, 20, 50, 100 ],//可以选择的分页集合
			nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
			striped : true,//设置为true将交替显示行背景。
			collapsible : false,//显示可折叠按钮
			toolbar:toolbar,//在添加 增添、删除、修改操作的按钮要用到这个
			url:basePath+url,//url调用Action方法
			onDblClickRow:editData,
			singleSelect:isSingleSelect,//为true时只能选择单行
			selectOnCheck: true,
			checkOnSelect: true,
			fitColumns:true,//允许表格自动缩放，以适应父容器
			remoteSort : false,
			pagination : true,//分页
			rownumbers : true,//行数
			loadFilter : pagerFilter
		});
	}
	
	
	/**
	 * datagrid数据查询——全属性自定义
	 */
	function datagrid_init_all(id,title,url,editData,isSingleSelect,toolbar,collapsible,pageSize,pageList){
		var plist=[ 10, 20, 50, 100 ];
		var pSize=10;
		if(pageSize!=null){
			pSize=pageSize;
		}
		if(pageList!=null){
			plist=pageList;
		}
		$('#'+id).datagrid({
			title : title,//标题
			fit : true,// 自动大小
			pageSize : pSize,//默认选择的分页是每页5行数据
			pageList : plist,//可以选择的分页集合
			nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
			striped : true,//设置为true将交替显示行背景。
			collapsible : collapsible,//显示可折叠按钮
			toolbar:toolbar,//在添加 增添、删除、修改操作的按钮要用到这个
			url:basePath+url,//url调用Action方法
			onDblClickRow:editData,
			singleSelect:isSingleSelect,//为true时只能选择单行
			selectOnCheck: true,
			checkOnSelect: true,
			fitColumns:true,//允许表格自动缩放，以适应父容器
			remoteSort : false,
			pagination : true,//分页
			rownumbers : true,//行数
			loadFilter : pagerFilter
		});
	}
	
	
	/**
	 * datagrid数据初始化——带前端分页
	 */
	function datagrid_query_pager(id,url,isSingleSelect){
		$("#"+id).datagrid('loadData', {
			total : 0,
			rows : []
		});
		$("#"+id).datagrid({
			modal : true,
			url : basePath+url,
			singleSelect : isSingleSelect,
			collapsible : false,
			pageNumber:1,
			pagination : true,
			loadFilter : pagerFilter
		});
	}
	
	/**
	 * datagrid数据初始化——后前端分页
	 */
	function datagrid_query(id,url,isSingleSelect){
		$("#"+id).datagrid('loadData', {
			total : 0,
			rows : []
		});
		$("#"+id).datagrid({
			modal : true,
			url : basePath+url,
			singleSelect : isSingleSelect,
			collapsible : false,
			pageNumber:1,
			pagination : true
		});
	}
	
	/**
	 * treegrid数据初始化
	 */
	function treegrid_init(id,title,url,id_field,tree_field){
		$('#'+id).treegrid({
			title : title,
			collapsible : false,// 是否可折叠的
			fit : true,// 自动大小
			fitColumns : true,
			url : basePath+url,
			idField : id_field,
			treeField : tree_field,
			onDblClickRow : editData,
			toolbar : '#toolbar'
		});
	}
	
	/**
	 * 迷你窗口treegrid数据初始化
	 */
	function treegrid_init_min(id,url,id_field,tree_field){
		$('#'+id).treegrid({
			collapsible : false,// 是否可折叠的
			fit : true,// 自动大小
			fitColumns : true,
			url : basePath+url,
			idField : id_field,
			treeField : tree_field
		});
	}
	/**
	 * 打开一个窗口独立一个iframe
	 * @param flid
	 * 
	 */
	function OpenFrameNew(id,url,width,height,title) {
		if($('#' + id).length == 0){
			$(document.body).append("<div id='"+id+"'></div>"); 
		}
		
		var _win = $('#' + id).window({
			title : title,
			width : width,
			height : height,
			top: ($(window).height() - height) * 0.5 + $(document).scrollTop(),
			content : "<iframe id=\"iframe_"+id+"\" frameborder=\"0\" scrolling=\"no\""
			+ " name=\"iframe_"+id+"\" width=\"100%\" height=\"100%\" "
			+ "src=\"" + basePath + url
			+ "\">" + "</iframe>",
			minimizable : false,
			maximizable : false,
			resizable : false,
			collapsible : false,
			modal: true,
			shadow: false
		});
		return _win;
	}
	
	/**
	 * 将窗口的标题改变
	 */
	function setDialogTtile(id,title){
		$("#"+id).dialog('setTitle', title);
	}
	
	
	function reloadParent(id){
		$('#'+id).datagrid('reload');
	}
	
	function showSuccess(){
		$.messager.show({
			title : '提示',
			msg : '保存成功！'
		});
	}
	
	function showFail(){
		$.messager.show({
			title : '提示',
			msg : '保存失败！'
		});
	}