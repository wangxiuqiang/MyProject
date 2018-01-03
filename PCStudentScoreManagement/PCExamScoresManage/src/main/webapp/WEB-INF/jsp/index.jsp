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
   <form:form commandName="admin" method="post" action="${pageContext.request.contextPath}/success">
       <form:input path="aname" />
       <form:password path="apassword" />
       <input type="submit" value="确认" />
   </form:form>
</body>
</html>
