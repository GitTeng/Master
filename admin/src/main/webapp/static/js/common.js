$(function () {
    $("#form").validate();
    table("list");
    refresh_button_status();
    $("#table").change(function () {
        refresh_button_status();
    });
});


function searchData() {
    console.log($("#searchForm").serialize());
    table("list", $("#searchForm").serialize());
    refresh_button_status();
}

function table(url, searchForm) {

    $('#table').bootstrapTable('destroy');

    $('#table').bootstrapTable({

        //请求方法
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        //是否显示行间隔色
        striped: true,
        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        //   cache: false,
        // toolbar: '#toolbar',
        //是否显示分页（*）
        pagination: true,
        //是否启用排序
        sortable: false,
        //排序方式
        sortOrder: "asc",
        //初始化加载第一页，默认第一页
        //我设置了这一项，但是貌似没起作用，而且我这默认是0,- -
        page: 1,
        //每页的记录行数（*）
        pageSize: 10,
        //可供选择的每页的行数（*）
        pageList: [10, 15, 20, 50],
        //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
        url: url,
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParamsType: "undefined",
        queryParams: queryParams,//请求服务器时所传的参数

        ////查询参数,每次调用是会带上这个参数，可自定义
        /* queryParams: {"delFlag": "0"},*/


        //分页方式：client客户端分页，server服务端分页（*）
        sidePagination: "server",
        //是否显示搜索
        search: false,
        //Enable the strict search.
        strictSearch: true,
        //Indicate which field is an identity field.
        idField: "id",
        /*  onLoadSuccess:function(data){

         $("#table").bootstrapTable("load",data)
         }
         },           */
        pagination: true
    });

    function queryParams(params) {
        return $.param({pageNumber: params.pageNumber, pageSize: params.pageSize, "search.delFlag_eq": "0"}) + "&" + searchForm;
    }
}

function refresh_button_status() {
    var id = getIdSelections();
    if (id.length == 0) {
        $("#delete").attr("disabled", "disabled");
        $("#update").attr("disabled", "disabled");
    } else if (id.length > 1) {
        $("#delete").removeAttr("disabled");
        $("#update").attr("disabled", "disabled");
    } else if (id.length == 1) {
        $("#delete").removeAttr("disabled");
        $("#update").removeAttr("disabled");
    }
}

function reload() {
    location.reload();
}

function add() {
    $("#form").validate().resetForm();
    $('#form')[0].reset();
    $("[name = id]").val(null);
    $("#submit").attr("disabled", false);
}

function update() {
    $("#form").validate().resetForm();
    $('#form')[0].reset();
    $("#submit").attr("disabled", false);
    // $('#table').on('click-row.bs.table', function (e, row, element) {
    $.map($("#table").bootstrapTable('getSelections'), function (row) {
        $("#form").loadJson(row);
    });
}

function del() {
    layer.confirm('确定要删除么?', {icon: 3, title: '提示'}, function (index) {
        var id = getIdSelections();
        post("del", {ids: id}, reload);
    });
}

function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

/**
 * 检查字段值是否重复
 * @param name 要检查的字段名
 * @param value 字段值
 * @param showId 重复信息显示位置id
 */
function checkValue(name, value) {
    if (value.length > 0 && ($("#" + name + "-error").text() == null || "" == $("#" + name + "-error").text())) {
        $.ajax({
            'url': "checkValue",
            'data': {"name": name, "value": value},
            'type': "post",
            'dataType': "json",
            'success': checkSuccess,
            'error': function (data) {
                toastr.error(data.info);
            }
        });
    }
}

function checkSuccess(result) {
    if ("0" != result.error) {
        toastr.error(result.info);
        $("#submit").attr("disabled", true);
    } else {
        $("#submit").attr("disabled", false);
    }
}

Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function dateFormat(date) {
    if(date != null){
        return new Date(date).format("yyyy-MM-dd hh:mm:ss");
    }
}
