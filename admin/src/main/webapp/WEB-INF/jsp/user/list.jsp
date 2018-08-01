<%@ page contentType="text/html;charset=UTF-8" %>
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
                    <span class="glyphicon glyphicon-plus"></span> 添加
                </button>
                <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#modal" id="update" onclick="update()">
                    <span class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" id="delete" class="btn btn-danger" onclick="del()">
                    <span class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>
            <div class="pull-right">
                <form class="bs-example bs-example-form" role="form" id="searchForm">
                    <div class="form-inline">
                        <input name="search.loginName_like" class="form-control" placeholder="请输入账号">
                        <button class="btn btn-info" type="button" onclick="searchData()">
                            <span class="glyphicon glyphicon-search"></span> 查询
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <table id="table" class='table table-bordered table-hover table-condensed' data-show-columns="false">
            <thead>
            <th data-checkbox="true"></th>
            <th data-field="id" data-visible="false"></th>
            <th data-field="loginName">账号</th>
            <th data-field="realName">姓名</th>
            <th data-field="age">年龄</th>
            <th data-field="phone">电话</th>
            <th data-field="address">地址</th>
            <th data-field="loginDate" data-formatter="dateFormat">最后登录时间</th>
            <th data-field="loginIp">最后登ip</th>
            <th data-field="createDate">创建时间</th>
            <th data-field="updateDate">更新时间</th>
            </thead>
        </table>
    </div>
</div>
<%--模态框内容 添加用户--%>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="title_assign" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">操作用户</h4>
            </div>
            <form id="form" action="<c:url value="/user/save"/>" class="form-horizontal">
                <input type="hidden" name="id">
                <div class="modal-body">
                    <div class="form-inline form-group">
                        <label for="loginName" class="col-sm-4 control-label">账号</label>
                        <input id="loginName" name="loginName" class="required form-control" minlength="6">
                    </div>
                    <div class="form-inline form-group">
                        <label for="realName" class="col-sm-4 control-label">姓名</label>
                        <input name="realName" id="realName" class="required form-control">
                    </div>
                    <div class="form-inline form-group">
                        <label for="phone" class="col-sm-4 control-label">电话</label>
                        <input id="phone" name="phone" class="required isMobile form-control">
                    </div>
                    <div class="form-inline form-group">
                        <label for="email" class="col-sm-4 control-label">邮箱</label>
                        <input id="email" name="email" class="form-control">
                    </div>
                    <div class="form-inline form-group">
                        <label for="address" class="col-sm-4 control-label">地址</label>
                        <input id="address" name="address" class="form-control">
                    </div>
                    <div class="form-inline form-group">
                        <label for="role" class="col-sm-4 control-label">角色</label>
                        <select id="role" name="roleList[0].id" class="required form-control"></select>
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
    let roleList = null;
    $.validator.setDefaults({
        submitHandler: function () {
            submitAjax($("#form"), reload);
        }
    });
    $("#add").click(function () {
        $('#loginName').removeAttr("readonly");
        $('#loginName').attr("onblur", "checkValue('loginName',this.value)");
        getAllRole();
    });

    function update() {
        $("#form").validate().resetForm();
        getAllRole();
        $('#loginName').attr("readonly", "readonly");
        $('#loginName').removeAttr("onblur");
        $("#submit").attr("disabled", false);
        $.map($("#table").bootstrapTable('getSelections'), function (row) {
            $("#form").loadJson(row);
            setValueSelected("role", row.roleList[0].id);
        });
    }

    function getAllRole() {
        if (roleList == null) {
            let url = '<c:url value='/role/getAll'/>';
            post(url, null, function (data) {
                $("#role option").remove();
                $.each(data, function (i, e) {
                    $("#role").append("<option value='" + e.id + "'>" + e.name + "</option>");
                });
            });
        }
    }
</script>
</body>
</html>

