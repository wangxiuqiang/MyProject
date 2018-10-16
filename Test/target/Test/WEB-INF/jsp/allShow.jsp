<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>查询</title>
    <script >
    </script>
</head>
<body>

<label style="color: red;">${failure}</label>
<form method="post" action="${pageContext.request.contextPath}/selectInfo">
    <table>
        <tr>
            <input type="text" name="name" value="" placeholder="请输入查询内容">
        </tr>
        <tr>
            <input type="submit" value="查询" />
        </tr>
    </table>
</form>
<form method="post" action="${pageContext.request.contextPath}/selectAll">
    <table>
        <tr>
            <input type="submit" value="查询全部" />
        </tr>
    </table>
</form>
<a href="${pageContext.request.contextPath}/add"> 添加</a>
<table style="border: dashed 1px blue">
    <tr>
        <td>名称:</td>
    </tr>
    <tr>
        <td>介绍:</td>
    </tr>
    <tr>
        <td>图片:</td>
    </tr>
    <c:forEach var="info" items="${lists}" >
        <tr>
            <td>${info.name}</td>


            <td>${info.introduce}</td>


            <td>${info.fileurl}</td>
            <td><a href="${pageContext.request.contextPath}/selectInfoById/${info.id}"> 查询 </a></td>
            <td><a href="${pageContext.request.contextPath}/update/${info.id}">修改</a></td>
            <td><a href="${pageContext.request.contextPath}/delInfo/${info.id}">删除</a></td>

        </tr>

    </c:forEach>
</table>
</body>
</html>
