<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-5
  Time: 下午8:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>查询信息</title>
</head>
<body>
${loginPo.id}
${loginPo.password}
    <form action="${pageContext.request.contextPath}/admin/select" method="post" >
     <table>
         <tr>
             <td><input placeholder="请输入要查询的信息" type="text" name="id">  </td>
             <td><input type="submit" value="查询" /></td>
         </tr>

     </table>
    </form>
<form action="${pageContext.request.contextPath}/admin/selectAll/1">
    <input type="submit" value="查询全部教师"/>
</form>
<form action="${pageContext.request.contextPath}/admin/selectAll/2">
    <input type="submit" value="查询全部学生"/>
</form>
    <table>
        <tr>
            <td>${teacher.tid} &nbsp;${student.sid}</td>
            <td>${teacher.tname}&nbsp;${student.sname}</td>
        </tr>
    </table>
<c:forEach items="${listTeacher}" var="Teacher">
    <table>
        <tr>
            <td>
                ${Teacher.tname}
            </td>
            <td>
                    ${Teacher.tid}
            </td>
            <td>
                    ${Teacher.tpassword}
            </td>
        </tr>
    </table>
</c:forEach>

<c:forEach items="${listStudent}" var="Student">
    <table>
        <tr>
            <td>
                    ${Student.sname}
            </td>
            <td>
                    ${Student.sid}
            </td>
            <td>
                    ${Student.SEid}
            </td>
            <td>
                    ${Student.SGid}
            </td>
            <td>
                    ${Student.spassword}
            </td>
        </tr>
    </table>
</c:forEach>
</body>
</html>
