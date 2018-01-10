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
<div>
    <table>
        <tr>
            <td>查询</td>
            <td>添加</td>
            <td>修改</td>
            <td><a href="${pageContext.request.contextPath}/admin/deleteIndex">删除</a></td>
        </tr>
    </table>
</div>
<div>
    <form action="${pageContext.request.contextPath}/admin/select" method="post" >
     <table>
         <tr>
             <td><input placeholder="请输入要查询的信息" type="text" name="id">  </td>
             <td><input type="submit" value="查询" /></td>
         </tr>

     </table>
    </form>
</div>
<div>
<form action="${pageContext.request.contextPath}/admin/selectAll/1">
    <input type="submit" value="查询全部教师"/>
</form>
<form action="${pageContext.request.contextPath}/admin/selectAll/2">
    <input type="submit" value="查询全部学生"/>
</form>
</div>
<c:if test="${show == 1}">
<div>
    <table>
        <tr>
            <td>编号</td>
            <td>姓名</td></tr>
        <tr>
            <td>${teacher.tid} &nbsp;${student.sid}</td>
            <td>${teacher.tname}&nbsp;${student.sname}</td>
        </tr>
    </table>
</div>
</c:if>
<c:if test="${flag == 1}" >
<table>
    <tr>
        <td>编号</td>
        <td>姓名</td>
        <td>密码</td>
    </tr>
<c:forEach items="${listTeacher}" var="Teacher">

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
</c:forEach>
</table>
</c:if>
<c:if test="${flag == 2}" >
    <div>
<table>
    <tr>
        <td>姓名</td>
        <td>编号</td>
        <td>考试编号</td>
        <td>班级编号</td>
        <td>密码</td>
    </tr>
<c:forEach items="${listStudent}" var="Student">

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

</c:forEach>
</table>
    </div>
</c:if>
</body>
</html>
