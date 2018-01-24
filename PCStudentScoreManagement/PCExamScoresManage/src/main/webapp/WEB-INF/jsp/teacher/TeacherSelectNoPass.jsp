<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-19
  Time: 上午9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>补考名单查询</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/teacher/noPassSelectResult/${Tid}" method="post">
    <table>
        <tr>
            <td>
              <select name="Cid">
                <c:forEach items="${course}" var="course">
                  <option value="${course.cid}">${course.cname}</option>
                </c:forEach>
              </select>
            </td>
            <td>
                <input type="submit" value="确认" />
            </td>
        </tr>
    </table>
</form>
<c:if test="${studentNoPass != null}">
<table>
    <tr>
        <td>学号</td>
        <td>姓名</td>
        <td>班级</td>
        <td>课程</td>
        <td>成绩</td>
    </tr>
    <c:forEach items="${studentNoPass}" var="student">
        <tr>
            <td>${student.sid}</td>
            <td>${student.sname}</td>
            <td>${student.SGid}</td>
            <td>${student.course.cname}</td>
            <td>${student.mark.mscore}</td>
        </tr>
    </c:forEach>
    <c:forEach items="${studentNoJoin}" var="noJoin">
        <tr>
            <td>${noJoin.sid}</td>
            <td>${noJoin.sname}</td>
            <td>${noJoin.SGid}</td>
            <td>${noJoin.course.cname}</td>
            <td>缺考</td>
        </tr>
    </c:forEach>
</table>
</c:if>
</body>
</html>
