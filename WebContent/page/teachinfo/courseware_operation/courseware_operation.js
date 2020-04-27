function exportFile() {
	// var nodes = $('#tt2').tree('getChecked');
	var nodes = getAllNode("tt2");
	if( nodes == null || nodes.length == 0){
		$.messager.alert('提示','没有已选择的实验');
		return ;
	}

	getFileList(nodes);
}

function getAllNode(id) {
	var nodes = [];
	var roots = $('#' + id).tree('getRoots');
	if (roots != null && roots.length > 0) {
		for (var i = 0; i < roots.length; i++) {
			var root = roots[i];
			nodes.push(root);
			pushChildren(id, root, nodes);
		}
	}
	return nodes;
}

function pushChildren(id, node, nodes) {
	var children = $('#' + id).tree('getChildren', node.target);
	if (children != null && children.length >0) {
		for (var i = 0; i<children.length; i++) {
			var c = children[i];
			nodes.push(c);
			// pushChildren(id, c, nodes);
		}
	}
}

function addFile() {
	var nodes = $('#tt').tree('getChecked');
	for(var i=0;i<nodes.length;i++){
		node = nodes[i];
		addNode(node);
	}
}

function getNodeByRoot(p) {
	var nodeArray = $('#tt2').tree('getRoots');
	for (var i = 0; i<nodeArray.length; i++) {
		var node = nodeArray[i];
		var res = getNode(p, node);
		if (res != null) {
			return res;
		}
	}
}

function getNode(p, node) {
	var path = getPath("tt2", node);
	path = path.substring(1);
	if (p == path) {
		return node;
	} else {
		var children = $('#tt2').tree('getChildren', node.target);
		if (children != null && children.length >0) {
			for (var i = 0; i<children.length; i++) {
				var getN = getNode(p, children[i]);
				if (getN != null) {
					return getN;
				}
			}
		}
		return null;
	}
}

function addNode(node) {
	var path = getPath("tt", node);
	path = path.substring(1);
	var pathArray = path.split("/");
	var p = pathArray[0];
	var n = {
		id:p,
		text:p,
		children:[]
	};
	var node = getNodeByRoot(p);
	if (node != null) {
		n = node;
	}
	setChildren(n, pathArray, 1);
	if (n.target == null) {
		$('#tt2').tree('append', {data:[n]});
	}
}

function setChildren(n, pathArray, i) {
	if (i < pathArray.length) {
		var p = pathArray[i];

		var childrenNode = null;
		if (n.target != null) {
			var path = getPath("tt2", n) + "/" + p;
			path = path.substring(1);
			childrenNode = getNodeByRoot(path);
		}
		if (childrenNode == null) {
			var chn = {
				id:p,
				text:p,
				children:[]
			};
			if (n.target != null) {
				$('#tt2').tree('append', {
					parent: n.target,
					data:[chn]
				});
				childrenNode = getNodeByRoot(path);
			} else {
				n.children = [chn];
				childrenNode = chn;
			}
		}

		setChildren(childrenNode, pathArray, i+1);
	}
}

function removeFile() {
	var nodes = $('#tt2').tree('getChecked');

	for(var i=0;i<nodes.length;i++){
		node = nodes[i];
		try {
			$('#tt2').tree('remove', node.target);
		}catch (e) {
		}
	}
	$('#remove').linkbutton("disable");
}

function getFileList(nodes) {
	var nod = [];
	for(var i=0;i<nodes.length;i++){
		var ff = $('#tt2').tree('getParent',nodes[i].target);
		if(ff != null){
			nod.push(getPath("tt2", nodes[i]));
		}
	}
	var prefix = $("input[name='prefix']:checked").val();

	if(nod.length > 0){
		// $.post(basePath+'courseware/exportCourseware', {files : nod.toString()}, function(result) {
		// 	if (result.code == 1) {
		// 		$.messager.alert('提示','导出成功');
		// 	} else {
		// 		$.messager.alert('提示','导出失败');
		// 	}
		// }, 'json');
		var url = basePath+'courseware/exportCourseware?files=' + nod.toString();
		url += "&prefix=" + prefix;
		// location.href = url;
		// $.messager.alert('提示','导出文件已添加到浏览器下载任务中');

		var a = document.createElement("a");
		a.href = url;
		a.download = "export.tar.gz";
		a.click();
	} else {
		$.messager.alert('提示','导出数据错误');
	}
}

function getPath(id, node) {
	var path = "/";
	var ff = $('#' + id).tree('getParent',node.target);
	if (ff != null) {
		path = getPath(id, ff) + "/" + node.text;
	} else {
		path += node.text;
	}
	return path;
}