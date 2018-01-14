<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-14
  Time: 上午9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>修改信息</title>
</head>
<body>
<%--
    可以修改编号, 不能修改密码,密码只读为了确保密码的长度在5-8之间满足校验器,
    所以密码的value值赋为12345
--%>
<c:if test="${errors != null}">
    <c:forEach items="${errors}" var="error">
        ${error.defaultMessage}
    </c:forEach>
</c:if>
${updateSuccess}
<c:if test="${flag == 1}">
    <div>
        <form action="${pageContext.request.contextPath}/admin/updateSuccess/1" method="post">
            <table>
                <tr>
                    <td>教师编号:<input type="text" name="Tid" value="${teacher.tid}" placeholder="请输入编号"  ></td></tr>
                <tr>
                    <td>教师姓名:<input type="text" name="Tname" value="${teacher.tname}" placeholder="请输入姓名"></td></tr>
                <tr>
                    <td>密&nbsp;&nbsp;&nbsp;&nbsp;码:<input type="text" name="Tpassword" placeholder="密码不能更改" value="12345" readonly>默认值不能改</td></tr>
                <tr>
                    <td><input type="submit" value="提交"></td></tr>
            </table>
        </form>
    </div>
</c:if>
<c:if test="${flag == 2}">
    <div>
        <form action="${pageContext.request.contextPath}/admin/updateSuccess/2" method="post">
            <table>
                <tr>
                    <td>学生编号:<input type="text" name="Sid" value="${student.sid}" placeholder="请输入编号" ></td></tr>
                <tr>
                    <td>学生姓名:<input type="text" name="Sname" value="${student.sname}" placeholder="请输入姓名"></td></tr>
                <tr>
                    <td>密&nbsp;&nbsp;&nbsp;&nbsp;码:<input type="text" name="Spassword" value="12345" placeholder="密码不能更改" readonly>(默认值不能改)</td></tr>
                <tr>
                    <td>班级编号:<input type="text" name="SGid" placeholder="请输入班级编号" value="${student.SGid}"></td></tr>
                <tr>
                    <td>考试编号:<input type="text" name="SEid" placeholder="请输入考试编号" value="${student.SEid}"></td></tr>
                <tr>
                    <td><input type="submit" value="提交"></td></tr>
            </table>
        </form>
    </div>
</c:if>
<div>
    <a href="${pageContext.request.contextPath}/admin/update">返回</a>
</div>
</body>
</html>
