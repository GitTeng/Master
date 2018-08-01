<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="target-densitydpi=device-dpi,width=device-width,initial-scale=1,minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"/>
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="static/css/login.css"/>
    <link rel="shortcut icon" href="${ctx}/static/favicon.ico">
    <script type="text/javascript">
        function loadTopWindow() {
            if (window.top != null && window.top.document.URL != document.URL) {
                window.top.location = document.URL; //这样就可以让登陆窗口显示在整个窗口了
            }
        }
    </script>
</head>

<body onload="loadTopWindow()">
<div class="wrapper">
    <img class="logo" src="static/img/login_logo.png" alt="logo"/>
    <form class="login_form" method="post" action="auth">
        <div class="ipt_block">
            <div class="ipt">
                <label>用户名&nbsp;/</label><input name="username" type="text" placeholder="输入用户名">
            </div>
            <span class="warn">
                <c:if test="${error!=null}">
                    <spring:message code="${error}"/>
                </c:if>
            </span>
        </div>
        <div class="ipt_block">
            <div class="ipt">
                <label>密码&nbsp;/</label><input name="passwd" TYPE="password" type="text" placeholder="输入您的密码">
            </div>
        </div>
        <div class="ipt_block">
            <div class="ipt code">
                <label>验证码&nbsp;/</label><input type="text" placeholder="输入验证码">
            </div>
            <img class="code_img" src="static/img/code.jpg" alt="code"/>
        </div>
        <div class="ipt_block submit">
            <button class="btn" type="submit">登 录</button>
        </div>
    </form>
</div>
</body>
</html>
