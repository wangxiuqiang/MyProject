<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-5
  Time: 下午8:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <table>
        <tr>
            <td>${teacher.tid} &nbsp;${student.sid}</td>
            <td>${teacher.tname}&nbsp;${student.sname}</td>
        </tr>
    </table>
</body>
</html>
