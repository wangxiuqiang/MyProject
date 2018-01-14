<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-14
  Time: 下午3:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>欢迎登录考试管理系统</title>
</head>
<body>
${teacher}
<a href="${pageContext.request.contextPath}/teacher/updatePassword/${teacher.tid}">修改密码</a>
<a href="">录入成绩</a>
<c:if test="${flag.equals('update')}">
    <div>
        <form action="${pageContext.request.contextPath}/teacher/updatePasswordSuccess/${teacherEx.tid}">
            <table>
                <tr>
                    <td>教师编号</td>
                    <td>
                        <input type="text" name="Tid" value="${teacherEx.tid}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>新密码</td>
                    <td>
                        <input type="password" name="newPassword" placeholder="请输入新密码"/>
                    </td>
                </tr>
                <tr>
                    <td>确认密码</td>
                    <td>
                        <input type="password" name="Tpassword" placeholder="确认密码"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="修改" />
                    </td>
                    <td>
                        <input type="reset" value="重置" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</c:if>
</body>
</html>
