<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-10-11
  Time: 下午4:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>show</title>
</head>
<body>
<table>
    <tr>
        <td>名称:</td>
        <td>${file.name}</td>
    </tr>
    <tr>
        <td>介绍:</td>
        <td>${file.introduce}</td>
    </tr>
    <tr>
        <td>图片:</td>
        <td><img src="<c:url value='${file.fileurl}'/>" /> </td>
    </tr>
</table>
</body>
</html>
