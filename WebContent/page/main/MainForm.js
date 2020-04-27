var keyword;
var columnEntities = [];
var globel_3_4_opne = "none";
var click_below_divice = "";
var timeOutId;
var LEFT_MENU_DEFAULT_WIDTH = 55; //左侧栏默认宽度
var LEFT_MENU_EXTEND_WIDTH = 188; //左侧栏扩展距离
var ANIMATE_DURATION = 300; //缓动时间

function loadLeftTree() {
	//1.拿到父Id
	var parentids = $('#parentid').val();
	var isN = parentids.split("_")[1]; //top下是有多级子菜单还是只有唯一的一个子菜单如业务架构
	var parentid = parentids.split("_")[0]; //top菜单的id

	//获取所有子菜单
	$.post('main/menu', {
			parent: parentid
		},
		function(result) {
			//在左右top菜单对应的div中加载子菜单树
			$('#' + parentids).tree({
				animate: false,
				data: result.obj,
				onClick: function(node) {
					//子菜单绑定点击事件
					//增加判断是扩展还是收缩
					if ($("#layoutWest").width() <= LEFT_MENU_DEFAULT_WIDTH) return;
					clickNode(node, isN, parentids);
				}
			});
		}, 'json').done(function() {
		//加载完子菜单树后，选中一个子菜单
		var selectedSubmenu = $('#submenuId').val();
		//如果是点击选中指定子菜单
		if (selectedSubmenu) {
			benchNoteOpen(parentids, isN, selectedSubmenu);
		} else {
			childNoteOpen(parentids, isN);
		}
		// _resumeLeftMenu(); //收缩
		// var navH = $('#layoutWest').find('.easyui-tree').height();
		$('#btn_er_wrap').css('top',+'px').show();

	});
}

function showSearch() {
	$('#dlg-search').dialog('open').dialog('setTitle', '查找数据业务表');
}

function searchData() {
	$("#tab").datagrid("load");
	$("#tab").datagrid("loadData", []);
	$("#col").datagrid("loadData", []);
	columnEntities = [];
	keyword = $("#keyword").searchbox('getValue');
	if (keyword == null || keyword == "") {
		$.messager.alert('Error', '关键字为空！');
		return;
	}

	url = basePath + 'modelManage/dataModelMaintain!getSearch.action?pageNumber=1';
	$("#search-fm").form('submit', {
		url: url,
		onSubmit: function() {
			return $(this).form('validate');
		},
		success: function(result) {
			if (result == "没有找到数据!") {
				$.messager.alert('提示', result);
				return;
			}
			$("#tab").datagrid({
				data: eval('(' + result + ')').tabdata,
				modal: true,
				pageNumber: 1,
				pageSize: 10,
				pagination: true,
				onClickRow: getColnumData,
				loadFilter: pagerFilter
			});
			var pg = $("#tab").datagrid("getPager");
			if (pg) {
				$(pg).pagination({
					onSelectPage: function(pageNumber, pageSize) {
						$("#tab").datagrid("reload");
						getPageData(pageNumber, pageSize);
					}
				});
			}

			columnEntities = eval('(' + result + ')').coldata;
			$("#col").datagrid({
				data: eval('(' + result + ')').coldata,
				modal: true,
				pageNumber: 1,
				loadFilter: pagerFilter
			});

			$('#tab').datagrid('selectRow', 0);
			var checked = $('#tab').datagrid('getSelected');
			getColnumData(null, checked);
		}
	});
}

function getColnumData(value, row) {
	var evens = _.filter(columnEntities.rows, function(val) {
		return val.coltableid == row.tabflid;
	});
	$("#col").datagrid({
		data: evens,
		modal: true,
		pageNumber: 1,
		loadFilter: pagerFilter
	});
}

function formatPrice(val, row) {
	var type = $("input[name='type']:checked").val();
	if (type == 0) {
		if (row.tabname != null && row.tabname != "") {
			if (val.toUpperCase().indexOf(keyword.toUpperCase()) >= 0) {
				return val.toUpperCase().replace(
					keyword.toUpperCase(),
					'<span style="color:red;">' + keyword.toUpperCase() + '</span>');
			} else {
				return val;
			}
		} else {
			return val;
		}
	} else if (type == 1) {
		if (row.colname != null && row.colname != "") {
			if (val.toUpperCase().indexOf(keyword.toUpperCase()) >= 0) {
				return val.toUpperCase().replace(
					keyword.toUpperCase(),
					'<span style="color:red;">' + keyword.toUpperCase() + '</span>');
			} else {
				return val;
			}
		} else {
			return val;
		}
	} else {
		return val;
	}
}

function isConfirm() {
	if (window.confirm("您确定要跳转至业务架构吗？")) {
		return true;
	} else {
		return false;
	}
}

var savechar = "1";
var saveTextUrl = "";
var eqText = "";
// 控制存储节点图标变化。

/**
 * 触发选中子菜单按钮事件
 */
function clickNode(node, isN, pid) {
	//如果top菜单只有唯一的一个子菜单如业务架构或者工作台隐藏左侧菜单树
	if (isN == "1") {
		$('#layoutWest').parent().width(0);
		$('#cc').parent().removeAttr('style').attr('style', "top: 60px;width: 100%;height:100%;");
		$('#cc').width('100%');
	}

	//如果top菜单下无子菜单就不处理
	if (!node || node.attributes == null) {
		return;
	}

	var panels = document.getElementById("panels");
	var values = node.id.split("_");
	//如果有子菜单控制子菜单的展开和缩进
	if (values[1] == "yes") {
		if (node.state == 'open') {
			$('#' + pid).tree('collapse',
				node.target);
		} else {
			$('#' + pid).tree('expand',
				node.target);
		}
		return;
	}
	$.ajax({
		type: 'post',
		url: "main/getMenuId",
		data: {
			nowtime: Date.parse(new Date()),
			menuid: values[0]
		}
	}).done(
		function(result) {
			//设备管理下的--〉设备维护菜单下，需要传入一个id参数，为菜单的flid，修改设备管理树后，
			//原来生成树的方法不再使用，所以，在这里传入参数
			if (node.attributes.url.indexOf("#") < 0) {
				panels.src = node.attributes.url + "&id=" + values[0];
			}
		});


	// 控制树的图片变化
	var parentids = $('#parentid').val();
	var nodeList = $('#' + parentids).tree('getChildren');
	$(nodeList).each(
		function(k, v) {
			var nd = $('#' + parentids).tree('find', v.id);
			var ndname = nd.iconCls;
			if (v.id != node.id) {
				if (nd.iconCls && nd.iconCls.lastIndexOf("_white") != -1) {
					ndname = nd.iconCls.substring(0, nd.iconCls.lastIndexOf("_white"));
					nd.iconCls = ndname;
					$('#' + parentids).tree('update', nd);
				}
			} else {
				
				if (nd.iconCls && nd.iconCls.lastIndexOf("_white") != -1) {
					ndname = nd.iconCls.substring(0, nd.iconCls.lastIndexOf("_white"));
					nd.iconCls = ndname;
				}
				
				$('#' + parentids).find('li div').removeClass('tree-node-selected-c'); //清除所有样式
				$('#' + parentids).find('li div').removeClass('tree-node-selected');
				$('#' + parentids).find('li div').removeClass('tree-node-selected-true'); //真实的选择，用来记录真正选择页面
				if ($("#layoutWest").width() <= LEFT_MENU_DEFAULT_WIDTH){ //收的
					
					var length = $(nd.target).find(".tree-indent").length;
					//判断是有孩子
					if(length>1){
						var  treeRoot = $('#' + parentids).tree("getParent",nd.target);
						$(treeRoot.target).addClass('tree-node-selected-c');
						
					}else{
						$(nd.target).addClass('tree-node-selected-c');
					}
					
				}else{ //扩展
					$(nd.target).addClass('tree-node-selected-c');
					
				}
				$(nd.target).addClass('tree-node-selected-true'); //真实的选择，用来记录真正选择页面
				$('#' + parentids).tree('update', nd);
				
			}
		});
}

/**
 * 迭代所有子节点选中bench选中的子菜单
 */
function benchNoteOpen(parentids, isN, selectedSubmenu) {
	var nodeList = $('#' + parentids).tree('getChildren');
	$(nodeList).each(
		function(k, v) {
			var node1 = $('#' + parentids).tree('find', v.id);
			if (v.id == selectedSubmenu) {
				clickNode(node1, isN, parentids);
				return;
			}
			var has = false;
			var length = $('#' + parentids).tree('getChildren', node1.target).length;
			if (length > 0) {
				var childlist = $('#' + parentids).tree('getChildren', node1.target);
				$(childlist).each(
					function(k, v) {
						if (v.id == selectedSubmenu) {
							has = true;
						}
					});
				if (has) {
					if (length == 1) {
						clickNode(childlist[0], isN, parentids);
					} else {
						benchNote(parentids, isN, selectedSubmenu, node1);
					}
					return;
				} else {
					$('#' + parentids).tree('collapse', node1.target);
				}
			} else {

			}
		});
}

function benchNote(parentids, isN, selectedSubmenu, node1) {
	var nodeList = $('#' + parentids).tree('getChildren', node1.target);
	$(nodeList).each(
		function(k, v) {
			var has = false;
			var node1 = $('#' + parentids).tree('find', v.id);
			var length = $('#' + parentids).tree('getChildren', node1.target).length;
			if (length > 0) {
				var childlist = $('#' + parentids).tree('getChildren', node1.target);
				$(childlist).each(
					function(k, v) {
						if (v.id == selectedSubmenu) {
							has = true;
						}
					});
				if (has) {
					if (length == 1) {
						clickNode(childlist[0], isN, parentids);
					} else {
						benchNote(parentids, isN, selectedSubmenu, node1);
					}
					return;
				} else {
					$('#' + parentids).tree('collapse', node1.target);
				}
			}

		});
}


/**
 * 迭代所有第一个子节点展开
 */
function childNoteOpen(parentids, isN) {
	var nodeList = $('#' + parentids).tree('getChildren');
	var index = 0;
	$(nodeList).each(
		function(k, v) {
			var node1 = $('#' + parentids).tree('find', v.id);
			var length = $('#' + parentids).tree('getChildren', node1.target).length;
			if (length > 0) {
				if (k == 0) {
					openNode(v.id, parentids, isN);
					index = index + length;
				} else {
					if (k > index) {
						$('#' + parentids).tree('collapse', node1.target);
					}
				}
			} else {
				if (k == 0) {
					clickNode(node1, isN, parentids);
				}
			}
			/*var name = node1.iconCls;
				if (node1.iconCls && node1.iconCls.lastIndexOf("_white") != -1) {
					name = node1.iconCls.substring(0, node1.iconCls
							.lastIndexOf("_white"));
				}
				node1.iconCls = name;
                $('#' + parentids).tree('update', node1);*/

		});
}

function openNode(cid, parentids, isN) {
	var node = $('#' + parentids).tree('find', cid);
	var nodeList = $('#' + parentids).tree('getChildren', node.target);
	var index = 0;
	$(nodeList).each(
		function(k, v) {
			var cn = $('#' + parentids).tree('find', v.id);
			var length = $('#' + parentids).tree('getChildren', cn.target).length;
			if (length > 0) {
				if (k == 0) {
					openNode(v.id, parentids, isN);
					index = index + length;
				} else {
					if (k > index) {
						$('#' + parentids).tree('collapse', cn.target);
					}
				}
			} else {
				if (k == 0) {
					clickNode(cn, isN, parentids);
				}
			}
		});
}


function exit() {
	if (window.confirm("您确定要退出吗?")) {
		document.location.href = 'Login!Exit.action';
	}
};

function getPageData(pageNumber, pageSize) {
	url = "modelManage/dataModelMaintain!getSearch.action?pageNumber=" + pageNumber + "&pageSize=" + pageSize;
	$("#search-fm").form('submit', {
		url: url,
		onSubmit: function() {
			return $(this).form('validate');
		},
		success: function(result) {
			if (result == "没有找到数据!") {
				$.messager.alert('提示', result);
				return;
			}
			$("#tab").datagrid({
				data: eval('(' + result + ')').tabdata,
				modal: true,
				pageNumber: pageNumber,
				pageSize: pageSize,
				pagination: true,
				onClickRow: getColnumData,
				loadFilter: pagerFilter
			});
			var pg = $("#tab").datagrid("getPager");
			if (pg) {
				$(pg).pagination({
					onSelectPage: function(pageNumber, pageSize) {
						$("#tab").datagrid("reload");
						getPageData(pageNumber, pageSize);
					}
				});
			}

			columnEntities = eval('(' + result + ')').coldata;
			$("#col").datagrid({
				data: eval('(' + result + ')').coldata,
				modal: true,
				pageNumber: 1,
				loadFilter: pagerFilter
			});

			$('#tab').datagrid('selectRow', 0);
			var checked = $('#tab').datagrid('getSelected');
			getColnumData(null, checked);
		}
	});
}



$(window).load(function() {
	loadLeftTree();
});

function editpass() {
	$("#editpassword").show();
	$("#editpassword").dialog('open').dialog('setTitle', '密码修改');
	$("#oldPassWord").val("");
	$("#newPassWord").val("");
	$("#again").val("");
}

function savaPassWord() {
	if ($("#oldPassWord").val().length < 1) {
		$.messager.alert('提示', '旧密码不能为空！');
		return false;
	}

	var reg = /^[a-zA-Z\d_]{6,16}$/;
	if (!reg.test($("#newPassWord").val())) {
		$.messager.alert('提示', '新密码长度必须为6到16位并且只能由英文字母、数字和下划线组成，字母区分大小写！');
		return false;
	}
	if ($("#newPassWord").val() != $("#again").val()) {
		$.messager.alert('提示', '两次新密码输入不相同，请重新输入！');
		return false;
	}

	$.post("user/queryOldPassWord", {
		"oldpassword": $("#oldPassWord").val()
	}, function(result) {
		if (result.code != 1) {
			$.messager.alert('提示', '旧密码输入不正确，请重新输入！');
			return false;
		} else {
			$.post("user/editPassWord", {
				"newpassword": $("#newPassWord").val()
			}, function(r) {
				if (result.code != 1) {
					$.messager.alert('提示', '密码修改失败，请稍后重试！');
					return false;
				} else {
					$.messager.alert('提示', '密码修改成功！');
					$('#editpassword').dialog('close');
				}
			});
		}
	}, 'json');

}

function displayUserinfo() {
	$(".user-info").hover(function() {

		$(".dropdown-userinfo").show();

	}, function() {
		$(".dropdown-userinfo").hover(function() {
			$(".dropdown-userinfo").show();
		}, function() {
			$(".dropdown-userinfo").hide();
		});
		$(".dropdown-userinfo").hide();
	});
}

function displayBacklog() {
	$(".backlogli").hover(function() {
		$(".dropdown-backlog").show();
	}, function() {
		$(".dropdown-backlog").hover(function() {
			$(".dropdown-backlog").show();
		}, function() {
			$(".dropdown-backlog").hide();
		});
		$(".dropdown-backlog").hide();
	});
}

/*function dispalytBacklength() {
	var len = 0;
	$.post(basePath + 'changeQuery/getData', {}, function(result) {
		if (result) {
			len = result.total;
			$(".drop-backlognum").html(len.toString());
			if (len > 0) {
				$(".backlognum").html(len.toString());
			} else {
				$(".backlognum").addClass("empty");
			}
		}
	}, 'json');
}*/

var resizeTimeOut;
$(document).ready(function() {

	//页面每加载一次测判断子菜单的top菜单是否只有一个子菜单如业务架构，是的话就隐藏左侧子菜单栏
	/* var regex = new RegExp("(BussinessModel/BussinessModel.jsp)|(benchGD/bench.jsp)");*/
	var parentids = $('#parentid').val();
	var isN = parentids.split("_")[1];

	$(window).resize(function() {
		clearTimeout(resizeTimeOut);
		resizeTimeOut = setTimeout(function() {
			/*var src = $("#panels")[0].src;
            if(regex.test(src)) {*/
			if (isN == "1") {
				$('#layoutWest').parent().width(0);
				$('#cc').parent().removeAttr('style').attr('style',
					"top: 60px;width: 100%;height:100%;");
				$('#cc').width('100%');
			}
		}, 250);
	});

//	dispalytBacklength();
	displayBacklog();
	displayUserinfo();
	$(".dropdown-userinfo").hide();
	$(".dropdown-backlog").hide();

	//注册事件
	_addEventListener();
});



//注册事件
function _addEventListener() {

	$("#btn_er").mousedown(
		function(event) {
			if ($("#layoutWest").width() > LEFT_MENU_DEFAULT_WIDTH) { // 判断侧栏宽度  , 扩展
				_resumeLeftMenu();
				$("#layoutWest").animate({
					"width": LEFT_MENU_DEFAULT_WIDTH
				}, {
					duration: ANIMATE_DURATION,
					step: function(now, tween) {
						$('#ccc').layout('panel', 'west').panel('resize', {
							width: now
						});
						$('#ccc').layout('resize');
					},
					complete: function() {
						var newUrl = basePath + 'style/images/extend.png';
						$("#btn_er").attr('src', newUrl);
					}
				});
			} else { //收缩

				_extendLeftMenu();
				$("#layoutWest").animate({
					"width": LEFT_MENU_EXTEND_WIDTH
				}, {
					duration: ANIMATE_DURATION,
					step: function(now, tween) {
						$('#ccc').layout('panel', 'west').panel('resize', {
							width: now
						});
						$('#ccc').layout('resize');
					},
					complete: function() {
						var newUrl = basePath + 'style/images/resume.png';
						$("#btn_er").attr('src', newUrl);
					}
				});
			}
		})

	//
	$("#btn_er").mouseover(
		function(event) {
			var url = $(this).attr("src");
			if (url.indexOf("_o") > 0) return;
			var newUrl = url.substr(0, url.lastIndexOf(".")) + "_o.png";
			$(this).attr('src', newUrl);
			event.stopPropagation();
		})
	//
	$("#btn_er").mouseout(
		function(event) {
			var url = $(this).attr("src");
			if (url.indexOf("_o") < 0) return;
			var newUrl = url.substr(0, url.lastIndexOf("_")) + ".png";
			$(this).attr('src', newUrl);
			event.stopPropagation();
		})
}

//扩展左边菜单
function _extendLeftMenu() {
	var parentids = $('#parentid').val();
	$("#layoutWest li").each(function(index, element) {
		$(this).removeClass("resume");
		$(this).unbind();
		$(this).find("div").css('cursor', "pointer");
	})
	//把样式复制到真实的菜单上
	 $(".tree-node-selected-c").removeClass("tree-node-selected-c");
	 $(".tree-node-selected-true").addClass("tree-node-selected-c");
	 $('#' + parentids).tree("expandTo", $(".tree-node-selected-true")[0]);
}

//收缩左边菜单
function _resumeLeftMenu() {
	var parentids = $('#parentid').val();
	var  treeRoot = $('#' + parentids).tree("getParent", $(".tree-node-selected-true")[0]);
	if(treeRoot){
		$(treeRoot.target).addClass('tree-node-selected-c');
	}
	
	//折叠所有Tree 
	$('#' + parentids).tree('collapseAll');
	$("#layoutWest li").each(function(index, element) {
		
		$(this).addClass("resume");
		//mouseover
		$(this).mouseover(function(event) {
			clearTimeout(timeOutId);
			var $obj = $(this).find("span:eq(1)");
			var url = $obj.css("background-image");
			if (url.indexOf("_white") > 0) return;
			
			var start =url.indexOf("http://");
			var end = url.lastIndexOf(".");
			var newUrl = url.substr(start,end-start) + "_white.png";
			$obj.css("background-image", "url(" + newUrl+")");

			//漂浮窗位置
			$("#drawer_window").css("top", $obj.offset().top);
			$("#drawer_window").css("left", LEFT_MENU_DEFAULT_WIDTH);

			//漂浮窗内容
			$("#drawer_window").empty();
			$(this).find("div").css('cursor', "default");
			var $cloneObj = $(this).find(".tree-title").clone(true); //复制对象

			if ($cloneObj.length > 1) { //如果有孩子,属于多层,没点击事件

				var $than = $(this);
				$cloneObj.each(function(index, element) {

					var $cloneObjAttr = $than.find("div:eq(" + index + ")").attr("node-id");
					if ($cloneObjAttr.indexOf("_no") > 0) { // 子项
						//复制图标
						$(element).prepend($than.find(".tree-icon:eq(" + index + ")").clone(true)); //插入内容
						//
						$(element).click(function(event) {
							_clickNodeByNodeId(this);
						});
					} else { //主项
						$(element).css('cursor', "default");
					}
					$(element).attr("node-id", $cloneObjAttr); //复制属性
				})
			} else {

				var $cloneObjAttr = $(this).find("div").attr("node-id");
				$cloneObj.attr("node-id", $cloneObjAttr); //复制属性
				//点击事件
				$cloneObj.click(function(event) {
					_clickNodeByNodeId(this);
				});

			}
			$("#drawer_window").append($cloneObj); //插入内容
			$("#drawer_window").unbind();

			var $that = $(this);
			$("#drawer_window").mouseover( //弹出窗鼠标经过
				function(event) {
					clearTimeout(timeOutId);
					$obj.css("background-image", "url(" + newUrl+")");
					$that.find("div").addClass("tree-node-hover");
					//
					$("#drawer_window").mouseleave( //鼠标经过
						function(event) {
							$that.trigger("mouseout");
							$that.find("div").removeClass("tree-node-hover");
							$(this).unbind();
							$(this).empty();
							event.stopPropagation();
						}
					);
					event.stopPropagation();
				}
			);
		});

		//mouseout
		$(this).mouseleave(function(event) {

			var $obj = $(this).find("span:eq(1)");
			var url = $obj.css("background-image");
			if (url.indexOf("_white") < 0) return;
			var start =url.indexOf("http://");
			var end = url.lastIndexOf("_");
			var newUrl = url.substr(start, end-start) + ".png";
			$obj.css("background-image", "url(" + newUrl+")" );
			//内容
			timeOutId = setTimeout(function() {
				$("#drawer_window").empty();
				$("#drawer_window").unbind();
			}, 20);

		})
	})
}


//点击
function _clickNodeByNodeId(element) {

	var parentids = $('#parentid').val();
	var isN = parentids.split("_")[1]; //top下是有多级子菜单还是只有唯一的一个子菜单如业务架构

	var nodeList = $('#' + parentids).tree('getChildren');
	$(nodeList).each(
		function(k, v) {
			var node = $('#' + parentids).tree('find', v.id);
			if (node.id === $(element).attr("node-id")) {
				clickNode(node, isN, parentids);
				return;
			}
		}
	);
}


/*
 * 跳转到待办事项
 */
function backlogjump() {
	$("#panels")[0].src = basePath + "page/bench/benchbacklog.jsp";
}

//页面导航跳转
function gotoOther(nodename, paramstr) {
	var parentids = $('#parentid').val();
	var isN = parentids.split("_")[1];
	var nodeList = $('#' + parentids).tree('getChildren');
	$(nodeList).each(
		function(k, v) {
			var node1 = $('#' + parentids).tree('find', v.id);
			if (v.text == nodename) {
				
				//				clickNode(node1,isN,parentids);
				makeclick(node1, isN, parentids, paramstr);
				return;
			}
		});
	//	benchNoteOpen(parentids,isN,"21a8b9018a19402bacd40fa2dac0a9b3_no");
}
//
function makeclick(node, isN, pid, paramstr) {
	var panels = document.getElementById("panels");
	var values = node.id.split("_");
	//如果有子菜单控制子菜单的展开和缩进
	if (values[1] == "yes") {
		if (node.state == 'open') {
			$('#' + pid).tree('collapse',
				node.target);
		} else {
			$('#' + pid).tree('expand',
				node.target);
		}
		return;
	}
	$.ajax({
		type: 'post',
		url: "main/getMenuId",
		data: {
			nowtime: Date.parse(new Date()),
			menuid: values[0]
		}
	}).done(
		function(result) {
			//设备管理下的--〉设备维护菜单下，需要传入一个id参数，为菜单的flid，修改设备管理树后，
			//原来生成树的方法不再使用，所以，在这里传入参数
			if (node.attributes.url.indexOf("#") < 0) {
				panels.src = node.attributes.url + paramstr;
			}
		});


	// 控制树的图片变化
	var parentids = $('#parentid').val();
	var nodeList = $('#' + parentids).tree('getChildren');
	$(nodeList).each(
		function(k, v) {
			var nd = $('#' + parentids).tree('find', v.id);
			var ndname = nd.iconCls;
			if (v.id != node.id) {
				if (nd.iconCls && nd.iconCls.lastIndexOf("_white") != -1) {
					ndname = nd.iconCls.substring(0, nd.iconCls.lastIndexOf("_white"));
					nd.iconCls = ndname;
					$('#' + parentids).tree('update', nd);
				}
			} else {
				if (nd.iconCls && nd.iconCls.lastIndexOf("_white") != -1) {
					ndname = nd.iconCls.substring(0, nd.iconCls.lastIndexOf("_white"));
					nd.iconCls = ndname;
				}
				
			$('#' + parentids).find('li div').removeClass('tree-node-selected');
				$(nd.target).addClass('tree-node-selected');
				$('#' + parentids).tree('update', nd);
			}
		});
}

function goToPage(url) {
	var panels = document.getElementById("panels");
	panels.src = url;
}

function hideLeft() {
	$('#layoutWest').parent().width(0);
	$('#cc').parent().removeAttr('style').attr('style', "top: 60px;width: 100%;height:100%;");
	$('#cc').width('100%');
	// $('.easyui-layout').layout('collapse', 'center');
}

function showLeft() {
	$('.easyui-layout').layout('expand', 'west');
}