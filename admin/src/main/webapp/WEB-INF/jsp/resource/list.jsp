<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../include.jspf" %>
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
                <th id="fuck" data-checkbox="true"></th>
                <th data-field="id">id</th>
                <th data-field="name">name</th>
                <th data-field="url">url</th>
                <th data-field="parentId">parent</th>
                <th data-field="createDate">创建时间</th>
                <th data-field="updateDate">更新时间</th>
                </thead>
            </table>
        </div>
    </div>
</div>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="title_assign" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">资源编辑</h4>
            </div>
            <form id="form" action="<c:url value="/resource/save"/>" class="form-horizontal">
                <input type="hidden" name="id">
                <div class="modal-body">
                    <div class="form-inline form-group">
                        <label for="name" class="col-sm-4 control-label">栏目名称</label>
                        <input id="name" name="name" class="required form-control">
                    </div>
                    <div class="form-inline form-group">
                        <label for="url" class="col-sm-4 control-label">URL</label>
                        <input id="url" name="url" class="form-control">
                    </div>
                    <div class="form-inline form-group">
                        <label for="url" class="col-sm-4 control-label">父栏目</label>
                        <select id="parentId" name="parentId" class="required form-control"></select>
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
<script>
    $.validator.setDefaults({
        submitHandler: function () {
            submitAjax($("#form"), reload);
        }
    });
    $("#add").click(function () {
        getResourceList()
    });
    $("#update").click(function () {
        getResourceList()
    });

    //拿出所有权限
    function getResourceList() {
        var url = '<c:url value='/resource/getAll'/>';
        post(url, null, function (data) {
            $("#parentId option").remove();
            $("#parentId").append("<option value='0'>根节点</option>");
            $.each(data, function (i, e) {
                $("#parentId").append("<option value='" + e.id + "'>" + e.name + "</option>");
            });
        });
    }
</script>
</body>
</html>
