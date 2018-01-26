<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-26
  Time: 上午9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询成绩</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/student/selectScoreSuccess/${Sid}" method="post">
    <table>
        <tr>
            <td>
                ${cid}
                    <select name="Cid">
                        <c:if test="${cid != null}">
                            <c:forEach items="${courseList}" var="courseName" >
                                <c:if test="${cid.equals(courseName.cid)}">
                                    <option value="${cid}">
                                            ${courseName.cname}
                                    </option>
                                </c:if>
                            </c:forEach>

                            <c:forEach items="${courseList}" var="courseName" >
                                <c:if test="${!cid.equals(courseName.cid)}">
                                    <option value="${courseName.cid}">
                                            ${courseName.cname}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:forEach items="${courseList}" var="courseName" >
                            <c:if test="${cid == null}">
                                <option value="${courseName.cid}">
                                        ${courseName.cname}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>

            </td>
            <td>
                <input type="submit" value="go" />
            </td>
        </tr>
    </table>
</form>
    <c:if test="${score != null}">
        <table >
            <tr>
                <td>课程编号</td>
                <td>课程名称</td>
                <td>成绩</td>
            </tr>
            <tr>
                <c:forEach var="course" items="${courseList}">
                    <c:if test="${cid.equals(course.cid)}">
                        <td>${course.cid}</td>
                        <td>${course.cname}</td>
                    </c:if>
                </c:forEach>

                    <td>${score}</td>
            </tr>
        </table>
    </c:if>
</body>
</html>
