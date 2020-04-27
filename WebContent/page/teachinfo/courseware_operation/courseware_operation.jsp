<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.zl.courseware.system.model.system.SysUser" %>
<%@ taglib uri="../../../WEB-INF/priveliege.tld" prefix="privilege"%>

<html>
<head>

	<%@include file="../../public/header.jsp"%>
	<script type="text/javascript" src="${basePath}page/teachinfo/courseware_operation/courseware_operation.js"></script>
	<%
		SysUser user=(SysUser)request.getSession().getAttribute("loginUser");
	%>
    <style>
        .mastButton {
            height:100px;
            width:100px;
            margin-left:100px;
            margin-top:20px;
            background:#3e1;
        }
        .addButton {
            height:30px;
            background:red;
        }
        .aaaa div{
            font-size:17px;
        }
        /*树节点高宽*/
        .tree-node {
            border-top: 1px #f8f8f8 solid;
            border-bottom: 1px #f8f8f8 solid;
            height: 20px;
            white-space: nowrap;
            cursor: pointer;
            background: #f8f8f8 right 0 repeat-y;
        }

        .tree-hit {
            cursor: pointer;
        }
        .tree ul li .tree-node ,.tree ul li .tree-file {
            height:20px;
        }

        .tree-expanded, .tree-collapsed, .tree-folder, .tree-file, .tree-checkbox, .tree-indent {
            display: inline-block;
            width: 16px;
            height: 20px;
            line-height: 40px;
            vertical-align: top;
            overflow: hidden;
            /*margin-right: 10px;*/
        }

        .tree-expanded {
            background: url('../images/accordion_arrows.png') no-repeat -16px 0px;
            margin-top: 3px;
            /*float:right;*/
        }

        .tree-expanded-hover {
            /*background: url('../images/tree_icons.png') no-repeat -50px 0px;*/
        }

        .tree-collapsed {
            background: url('../images/accordion_arrows.png') no-repeat 0px 0px;
            margin-top: 3px;
            /*  float:right;*/
            height:32px;
        }

        /*树节点文字*/
        .tree-title {
            font-size: 14px;
            display: inline-block;
            text-decoration: none;
            vertical-align: middle;
            white-space: nowrap;
            padding: 0 10px;
            height: 15px;
            line-height: 15px;
            font-family: "Microsoft YaHei" !important;
            color: #3333D3
        }

    </style>
</head>
<body>
<div class="panel datagrid">
    <div class="panel-header" style="width: 1704px;">
        <div class="panel-title">教案分配</div><div class="panel-tool"></div>
        <div style="margin-left:687px;">
            选择前缀：
            <input type="radio" name="prefix" value="0" checked="checked">无&nbsp;</input>
            <input type="radio" name="prefix" value="1">一，二，三...&nbsp;</input>
            <input type="radio" name="prefix" value="2">实验一，实验二，实验三...&nbsp;</input>
        </div>
        <a class="easyui-linkbutton" style="margin-left:1050px;" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
           onclick="exportFile()">导出</a>
    </div>
    <table>
        <tr>
            <td>
                <div style="width: 520px;height: 800px;overflow: auto;background-color: rgb(248, 248, 248);border: 1px solid #42a1d7;">
                    <ul id="tt" class="easyui-tree" style="width: 520px;"></ul>
                </div>
            </td>
            <td width="100px">
                <a id = "add" class="easyui-linkbutton" style="float: left;margin-left: 20px;"  href="javascript:void(0)"
                   onclick="addFile()"> > > </a><br/><br/><br/><br/>
                <a id = "remove" class="easyui-linkbutton" style="float: left;margin-left: 20px;"  href="javascript:void(0)"
                   onclick="removeFile()"> < < </a>
            </td>
            <td>
                <div style="width: 520px;height: 800px;overflow: auto;background-color: rgb(248, 248, 248);border: 1px solid #42a1d7;">
                    <ul id="tt2" class="easyui-tree" style="width: 520px;"></ul>
                </div>
            </td>
        </tr>
    </table>

</div>
    <script>
        $(function(){
            $('#tt').tree({
                url: basePath+'courseware/getCoursewareData',
                animate:true,
                checkbox:true,
                image:null,
                onCheck: function (node) {
                    var isChecked = isCheck("tt");
                    var f = "disable";
                    if (isChecked) {
                        f = "enable";
                    }
                    $('#add').linkbutton(f);
                }
            });
            $('#tt2').tree({
                animate:true,
                dnd:true,
                checkbox:true,
                image:null,
                onCheck: function (node) {
                    var isChecked = isCheck("tt2");
                    var f = "disable";
                    if (isChecked) {
                        f = "enable";
                    }
                    $('#remove').linkbutton(f);
                }
            });

            function isCheck(id) {
                var nodes = $('#'+id).tree('getChecked');
                if( nodes != null && nodes.length > 0){
                    return true;
                }
                return false;
            }
            $('#add').linkbutton("disable");
            $('#remove').linkbutton("disable");
        });
    </script>
</body>
</html>
