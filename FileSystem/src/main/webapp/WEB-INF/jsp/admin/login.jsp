<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-7-11
  Time: 下午2:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请登录</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/admin/login" method="post">
    <input type="text" placeholder="请输入密码" id="aname" name="aname"><br>
    <input type="password" placeholder="请输入密码" id="apwd" name="apwd"><br>
    <input type="submit" value="登录"><br>
    <input type="reset" value="清空">
</form>

<script type="text/javascript">

</script>
</body>
</html>
