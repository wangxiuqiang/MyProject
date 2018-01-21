<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-17
  Time: 上午9:26
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>录入成绩</title>


</head>
<body>
<h4>请选择班级和课程:</h4>
<hr>
    <div>
        <form method="post" action="${pageContext.request.contextPath}/teacher/selectStudent/${Tid}">
            <table>
                <tr>
                    <td>
                        <select name="Cid">
                            <c:if test="${cid != null}">
                                <c:forEach items="${course}" var="courseName" >
                                    <c:if test="${cid.equals(courseName.cid)}">
                                         <option value="${cid}">
                                            ${courseName.cname}
                                          </option>
                                    </c:if>
                                </c:forEach>

                                <c:forEach items="${course}" var="courseName" >
                                    <c:if test="${!cid.equals(courseName.cid)}">
                                        <option value="${courseName.cid}">
                                                ${courseName.cname}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:forEach items="${course}" var="courseName" >
                            <c:if test="${cid == null}">
                                    <option value="${courseName.cid}">
                                            ${courseName.cname}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select name="Gid">
                            <%--如果不为空,就先输出选择了的,然后输出其余的--%>
                            <c:if test="${gid != null}">
                                <c:forEach items="${grade}" var="Grade" >
                                    <c:if test="${gid.equals(Grade.gid)}">
                                        <option value="${gid}">
                                            ${Grade.gname}
                                        </option>
                                    </c:if>
                                </c:forEach>
                                <c:forEach items="${grade}" var="Grade" >
                                 <c:if test="${!Grade.gid.equals(gid)}">
                                    <option value="${Grade.gid}">
                                            ${Grade.gname}
                                    </option>
                                 </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${gid == null}">
                                <c:forEach items="${grade}" var="Grade" >
                                        <option value="${Grade.gid}">
                                                ${Grade.gname}
                                        </option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </td>
                    <td>
                        <input type="submit" value="确定">
                    </td>
                </tr>
            </table>
        </form>
    </div>

<div>
    <table>
        <c:if test="${studentList != null}">
        <tr align="center">
            <td width="20px">状态</td>
            <td width="50px">学号</td>
            <td width="50px">姓名</td>
            <td width="50px">课程</td>
            <td width="50px">班级</td>
            <td width="50px">成绩</td>
            <td>是否修改</td>
        </tr>
        </c:if>
        <c:forEach items="${studentList}" var="student">
            <form  method="post" action="${pageContext.request.contextPath}/teacher/writeSuccessSome/${Tid}/${cid}/${gid}">
            <tr>
                <td>
                    <input type="checkbox" value="${student.sid}" name="array"/>
                </td>
                <td>${student.sid}</td>
                <td>${student.sname}</td>
                <td>${student.course.cname}</td>
                <td>${student.grade.gname}</td>
                <td><input type="text" name="score" value="${student.mark.mscore}"/></td>
                <td>

                </td>
                
             </tr>
                <tr>
                    <td>
                        <input type="submit" value="部分修改" />
                    </td>
                </tr>
            </form>
        </c:forEach>
    </table>
</div>
</body>
</html>
