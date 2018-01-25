<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-25
  Time: 下午3:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3> 修改密码</h3>
${success}
<div>
    <form action="${pageContext.request.contextPath}/student/updatePassword1/${Sid}" method="post">
    <table>
        <tr>
            <td>
                原来的密码:
            </td>
            <td>
                <input type="password" name="firstPassword" />
            </td>
        </tr>
        <tr>
            <td>
                修改后的密码:
            </td>
            <td>
                <input type="password" name="afterPassword" />
            </td>
        </tr>
        <tr>
            <td>
                确认密码:
            </td>
            <td>
                <input type="password" name="againPassword" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="accept">
            </td>
        </tr>
    </table>
    </form>
</div>
</body>
</html>
