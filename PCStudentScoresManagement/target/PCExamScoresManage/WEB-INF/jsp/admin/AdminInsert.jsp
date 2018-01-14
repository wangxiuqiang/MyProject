<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-12
  Time: 下午2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>录入数据</title>
</head>
<body>
${result}

<c:if test="${errors != null}">
    <c:forEach items="${errors}" var="error">
        ${error.defaultMessage}
    </c:forEach>
</c:if>

     <div>
         <form action="${pageContext.request.contextPath}/admin/insertInput" method="post">
             <table>
                 <tr>
                     <td>
                         <input type="radio" name="insertWho" value="teacher" <c:if test="${who == 1}">checked="true"</c:if> />教师
                     </td>
                     <td>
                         <input type="radio" name="insertWho" value="student" <c:if test="${who == 2}">checked="true"</c:if> />学生
                     </td>
                     <td>
                         <input type="submit" value="确认"/>
                     </td>
                 </tr>

             </table>
         </form>
     </div>
    <c:if test="${who == 1}">
        <div>
            <form action="${pageContext.request.contextPath}/admin/insertSuccessOrFaliure/1" method="post">
                <table>
                    <tr><td>教师编号:<input type="text" name="Tid" placeholder="请输入编号"></td></tr>
                    <tr><td>教师姓名:<input type="text" name="Tname" placeholder="请输入姓名"></td></tr>
                    <tr><td>密&nbsp;&nbsp;&nbsp;&nbsp;码:<input type="text" name="Tpassword" placeholder="请输入密码"></td></tr>
                    <tr><td><input type="submit" value="提交"></td></tr>
                </table>
            </form>
        </div>
    </c:if>
    <c:if test="${who == 2}">
        <div>
            <form action="${pageContext.request.contextPath}/admin/insertSuccessOrFaliure/2" method="post">
                <table>
                    <tr><td>学生编号:<input type="text" name="Sid" placeholder="请输入编号"></td></tr>
                    <tr><td>学生姓名:<input type="text" name="Sname" placeholder="请输入姓名"></td></tr>
                    <tr><td>密&nbsp;&nbsp;&nbsp;&nbsp;码:<input type="text" name="Spassword" placeholder="请输入密码"></td></tr>
                    <tr><td>班级编号:<input type="text" name="SGid" placeholder="请输入班级编号" value=""></td></tr>
                    <tr><td>考试编号:<input type="text" name="SEid" placeholder="请输入考试编号" value=""></td></tr>
                    <tr><td><input type="submit" value="提交"></td></tr>
                </table>
            </form>
        </div>
    </c:if>
</body>
</html>
