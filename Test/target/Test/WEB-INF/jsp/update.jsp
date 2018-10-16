<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-10-2
  Time: 下午6:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改</title>
    <script >

    </script>
</head>
<body>

<form action="${pageContext.request.contextPath}/updateInfo" enctype="multipart/form-data" method="post">
    <table style=" border: dashed 1px black">
        <label style="color: red;">${failure}</label>

        <label style="color: red;">${success}</label>

        <tr>
            <td >
                名字:
            </td>
            <td><input name="name" value="${Info.name}" type="text"/></td>
        </tr>
        <tr>
            <td>
                简介:
            </td>
            <td>
                <textarea name="introduce"  cols="30" rows="10">${Info.introduce}</textarea>
            </td>

        </tr>
        <tr>
            <td >
                文件:
            </td>
            <td>
                <input type="file"  name="file" value="${Info.fileurl}"/>
            </td>
        </tr>
        <tr>
            <td> <<input type="hidden" name="id" value="${Info.id}"></td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="提交" />
            </td>

        </tr>

    </table>