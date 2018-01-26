<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-25
  Time: 下午2:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>欢迎登录</title>
</head>
<body>
      <table>
          <tr>
              <td><a href="${pageContext.request.contextPath}/student/beforeUpdatePassword/${loginPo.id}">密码修改</a></td>
              <td><a href="${pageContext.request.contextPath}/student/selectScore/${loginPo.id}">成绩查询</a></td>
              <td><a href="">考试报名</a></td>
          </tr>
      </table>

</body>
</html>
