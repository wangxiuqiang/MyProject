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

<c:if test="${info != null }" >
<c:forEach items="${info}" var="info" >
    <table>
        <tr>
            <td>名称:</td>
            <td>${info.name}</td>
        </tr>
        <tr>
            <td>介绍:</td>
            <td>${info.introduce}</td>
        </tr>
        <tr>
            <td>图片:</td>
            <td><img src="<c:url value='${info.fileurl}'/>" /> </td>
        </tr>
    </table>
</c:forEach>
</c:if>
</body>
</html>
