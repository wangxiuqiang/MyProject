<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-1
  Time: 下午2:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page isELIgnored="false"%>
<html>
<head>
    <title>请登录</title>
</head>
<body>
   <form method="post" action="${pageContext.request.contextPath}/success">
       <table >
           <tr>
               <td>账号:</td>
               <td> <input  type="text" name="id" placeholder="请输入账号"/></td>
           </tr>
           <tr>
               <td>密码:</td>
               <td> <input name="password" type="password" placeholder="请输入密码"/></td>
           </tr>
       <tr>
           <td><input name="selectWhoIn" type="radio" value="admin"/></td>
           <td><input name="selectWhoIn" type="radio" value="teacher"/></td>
           <td><input name="selectWhoIn" type="radio" value="3"/></td>

       </tr>
           <tr>
               <td><input type="submit" value="确认" /></td>
               <td><input type="reset" value="重置" /></td>
           </tr>
       </table>
   </form>
</body>
</html>
