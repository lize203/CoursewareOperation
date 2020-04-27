/**
 * 初始化加载
 */
$(function() {
	datagrid_init_pager("dg",'角色管理','role/getData',editData,true,pagerFilter);
});

/**
 * 新增角色
 */
function newData() {
	OpenFrameLoad("win","page/system/role/Role_Add.jsp",450,240,'新建角色',clear);
}


/**
 * 双击view
 * @param index
 * @param value
 * @returns
 */
function editData(index,value) {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		rowData=row;
		OpenFrameLoad("win","page/system/role/Role_View.jsp",500,240,'角色详细',init);
	}
}