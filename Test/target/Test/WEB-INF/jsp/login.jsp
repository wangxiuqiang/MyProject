<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-10-2
  Time: 下午6:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>请登录</title>

</head>
<body>
<form action="${pageContext.request.contextPath}/loginGo" method="post">
<table >
    <label style="color: red;">${failure}</label>
    <tr>
        <td>用户名:</td>
        <td>
            <input type="text" name="name" value="" placeholder="请输入用户名"/>
        </td>
    </tr>
    <tr>
        <td>密码:</td>
        <td>
            <input type="password" name="passwd" value="" placeholder="请输入密码"/>
        </td>
    </tr>
    <tr>
        <td>
            <input type="submit" value="登录" />
        </td>
        <td>
            <input type="reset" value="重置"/>
        </td>
    </tr>
</table>
</form>
</body>
</html>
