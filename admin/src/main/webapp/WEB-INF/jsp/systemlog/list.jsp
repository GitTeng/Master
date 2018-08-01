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
            </div>
            <div class="pull-right">
                <form class="bs-example bs-example-form" role="form" id="searchForm">
                    <div class="form-inline">
                        <input name="search_user.realName_like" class="form-control" placeholder="请输入姓名">
                        <select id="type" name="search_type_eq" class="form-control">
                            <option value="1">操作日志</option>
                            <option value="2">错误日志</option>
                        </select>
                        <button class="btn btn-info" type="button" onclick="searchData()">查询</button>
                    </div>
                </form>
            </div>
        </div>
        <table id="table" class='table table-bordered table-hover table-condensed' data-show-columns="false">
            <thead>
            <th data-field="type" data-formatter="getType">类型</th>
            <th data-field="user" data-formatter="getRealName">姓名</th>
            <th data-field="remoteAddr">ip</th>
            <th data-field="requestUri">对象</th>
            <th data-field="module">描述</th>
            <th data-field="createDate">创建时间</th>
            </thead>
        </table>
    </div>
</div>
<script>
    function getRealName(user) {
        if (user == null) {
            return "";
        } else {
            return user.realName;
        }
    }

    function getType(type) {
        if (type == "1") {
            return "操作日志";
        }
        if (type == "2") {
            return "错误日志";
        }
    }

</script>
</body>
</html>