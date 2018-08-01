<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/jsp/taglib.jsp" %>
    <link href="${ctx}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <%--tab相关--%>
    <link href="${ctx}/static/css/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>管理系统模板</title>
    <link rel="shortcut icon" href="${ctx}/static/favicon.ico">
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li>
                    <span><img alt="image" style="  width: 225px;height:60px;position: relative;z-index: 1;" class="img" src="${ctx}/static/img/logo.png"/></span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="block m-t-xs">当前用户：<strong class="font-bold"><security:authentication property="name"/></strong></span>
                    </a>
                </li>
                <li>
                    <c:forEach var="p" items="${menus}">
                <li>
                    <a href="#">
                        <span class="nav-label" style="position:relative;left:55px;font-size : 1.2em;">${p.text}</span>
                    </a>
                    <c:if test="${p.child!=null}">
                        <ul class="nav nav-second-level">
                            <c:forEach var="c" items="${p.child}">
                                <li>
                                    <a class="J_menuItem" style="position:relative;left:33px" href="${ctx}${ c.url}">${ c.text}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </li>
                </c:forEach>
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0;height: 60px">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary" href="#"><i class="fa fa-bars"></i> </a>
                </div>
                <div class="pull-right" style="height: inherit;padding: 15px">
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#dialog_operat_user" id="update">修改密码
                    </button>
                </div>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="index_v1.html">初始页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span></button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="${ctx}/logout" href="#" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i>安全退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe" width="100%" height="100%" src="${ctx}/static/welcome.html" frameborder="0" data-id="index_v1.html" seamless></iframe>
        </div>
        <div class="footer">
            <div class="text-center">&copy; 2014-2018 <a href="http://www.huan.tv" target="_blank">warriorr</a>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="dialog_operat_user" tabindex="-1"
     role="dialog" aria-labelledby="title_assign"
     aria-hidden="true">
    <div class="modal-dialog" style="position: absolute;z-index: 9999;top: 200px; left: 650px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="title_assign_new_desktop">修改密码</h4>
            </div>
            <div class="modal-body">
                <div class="modal-body">
                    <form id="form_operat_user">
                        <div class=" active sample-pane alert">
                            <fieldset>
                                <label>原密码</label>
                                <input type="text" id="old_password" name="old_password" class="required form-control">
                            </fieldset>
                            <div class="has-error">
                                <label id="response_old_password" class="help-block"></label>
                            </div>
                            <fieldset>
                                <label>新密码</label>
                                <input type="password" id="new_password" name="new_password" class="required form-control">
                            </fieldset>
                            <div class="has-error">
                                <label id="response_new_password" class="help-block"></label>
                            </div>
                            <fieldset>
                                <label>再次输入新密码</label>
                                <input type="password" name="second_password" id="second_password" class="required form-control">
                            </fieldset>
                            <div class="has-error">
                                <label id="response_second_password" class="help-block"></label>
                            </div>
                        </div>
                    </form>
                    <div class="modal-footer">
                        <button class="btn btn-primary" type="reset" data-dismiss="modal">取消</button>
                        <button id="button_operat_user" class="btn btn-primary">确认</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${ctx}/static/js/jquery.js?v=2.1.4"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${ctx}/static/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${ctx}/static/plugins/layer/layer.min.js"></script>
<!-- 自定义js -->
<script src="${ctx}/static/js/main/hplus.js?v=4.1.0"></script>
<script type="text/javascript" src="${ctx}/static/js/main/contabs.js"></script>
<script type="text/javascript" src="${ctx}/static/js/ajax.js"></script>
<script src="${ctx}/static/plugins/toastr/toastr.min.js"></script>
<link href="${ctx}/static/plugins/toastr/toastr.min.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/static/js/config.js"></script>
<script>
    $("#button_operat_user").click(function () {
        if ($("#new_password").val() != $("#second_password").val()) {
            $("#response_second_password").html("两次密码输入不符");
            return;
        }
        if ($("#old_password").val() == '') {
            $("#response_old_password").html("请输入初始密码")
            return;
        } else if ($("#new_password").val() == '') {
            $("#response_new_password").html("请输入更新密码")
            return;
        } else if ($("#second_password").val() == '') {
            $("#response_second_password").html("请再次输入更新密码")
            return;
        }
        var url = '<c:url value='/user/updatePW'/>';
        post(url, $("#form_operat_user").serialize(), function (data) {
            $('#dialog_operat_user').modal('hide');
            $('#form_operat_user')[0].reset();
            alert("修改成功，请重新登陆");
            self.location.href = "${ctx}/logout"
        });
    });
</script>
</body>
</html>
