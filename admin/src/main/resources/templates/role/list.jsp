<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../include.jspf" %>
    <link rel="stylesheet" href="${ctx}/static/plugins/jqueryZtree/zTreeStyle/metro.css">
    <script type="text/javascript" src="${ctx}/static/plugins/jqueryZtree/jquery.ztree.all-3.5.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="fixed-table-header head_padding">
            <div class="pull-left">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal" id="add" onclick="add()">
                    <span class="glyphicon glyphicon-plus"></span> 添加</button>
                <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#modal" id="update" onclick="update()">
                    <span class="glyphicon glyphicon-pencil"></span> 修改</button>
                <button type="button" id="delete" class="btn btn-danger" onclick="del()">
                    <span class="glyphicon glyphicon-minus"></span> 删除</button>
            </div>
        </div>
        <div>
            <table id="table" class='table table-bordered table-hover table-condensed' data-show-columns="false">
                <thead>
                <th data-checkbox="true"></th>
                <th data-field="id" data-visible="false"></th>
                <th data-field="name">角色名</th>
                <th data-field="createDate">创建时间</th>
                <th data-field="updateDate">更新时间</th>
                </thead>
            </table>
        </div>
    </div>
    <div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="title_assign" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">角色权限</h4>
                </div>
                <form id="form" class="form-horizontal" action="<c:url value='/role/save'/>">
                    <input type="hidden" name="id">
                    <div class="modal-body">
                        <div class="form-inline form-group">
                            <label for="name" class="col-sm-4 control-label">角色名称</label>
                            <input id="name" name="name" class="required form-control">
                        </div>
                        <div style="height: 20px"></div>
                        <div class="clearfix">
                            <div id="parent_update_tree" style="width:160px; overflow:auto;float:left;position:relative;left:120px">
                                <h5>分配权限</h5>
                                <ul id='ztree' class='ztree'></ul>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-default" type="reset" data-dismiss="modal">取消</button>
                        <button id="submit" type="submit" class="btn btn-default">确认</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $("#update").click(function () {
        initResource(getIdSelections()[0])
    });
    $("#add").click(function () {
        initResource(0)
    });
    $.validator.setDefaults({
        submitHandler: function () {
            var zTree = $.fn.zTree.getZTreeObj("ztree");//换成实际的图层的id  
            var changedNodes = zTree.getCheckedNodes(true); //获取改变的全部结点
            var treeNode = [];
            for (var i = 0; i < changedNodes.length; i++) {
                treeNode.push(changedNodes[i]);
            }
            for (var i = 0; i < treeNode.length; i++) {
                $("#form").append(
                    "<input class='clear' type='hidden' name='resourceList[" + i + "].id' value=" + treeNode[i].id + " >"
                )
            }
            submitAjax($("#form"), reload);
        }
    });

    function initResource(id) {
        let url = '<c:url value='/role/getResourceList'/>';
        post(url, {id: id}, function (result) {
            $.fn.zTree.init($("#ztree"), setting, result);
        });
    }
</script>
</body>
</html>
