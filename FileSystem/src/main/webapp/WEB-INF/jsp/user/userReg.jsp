<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-7-9
  Time: 下午8:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>用户激活</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
</head>
<body>
<form action="${pageContext.request.contextPath}/user/reg" method="post" onsubmit="return result">
    <span>输入密码:</span>
    <input type="password" name="upwdFirst" id="upwdFirst" value=""><br>
    <span>确认密码:</span>
    <input type="password" name="upwd" id="upwd" value="">
    <span id="upwd_span"></span><br>
    <input type="hidden" name="code" value="${code}">
    <input type="submit" value="确认">
</form>

<script type="text/javascript">
    var result = false;
    $("#upwd").focusout(function () {
        alert("123");
        if($("#upwd").val() == $("#upwdFirst").val()) {
            result =true;
        }else {
            $("#upwd_span").html("两次输入的密码不一致");
        }
    });
</script>
</body>
</html>
