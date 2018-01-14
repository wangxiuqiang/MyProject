<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-10
  Time: 下午7:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>删除信息</title>
    <script type="text/javascript" src="/js/deleteAllAndNoSelect.js">

    </script>
</head>
<body>
<%--这个div用来显示增删改查的链接--%>
<div>
    <table>
        <tr>
            <td><a href="${pageContext.request.contextPath}/admin/select">查询</a></td>
            <td><a href="${pageContext.request.contextPath}/admin/insert">添加</a></td>
            <td><a href="${pageContext.request.contextPath}/admin/update">修改</a></td>
            <td><a href="${pageContext.request.contextPath}/admin/deleteIndex">删除</a></td>
        </tr>
    </table>
</div>
 <div>
     <form  name="headForm" action="${pageContext.request.contextPath}/admin/delete/0" method="post">
         <input type="radio" name="whoSelect" value="teacher" <c:if test="${flag == 1}">checked="true"</c:if>  />教师
         <input type="radio" name="whoSelect" value="student" <c:if test="${flag == 2}">checked="true"</c:if>   />学生
         <input type="submit" value="确认"/>
     </form>
 </div>
 <c:if test="${flag == 1}" >
     <form action="${pageContext.request.contextPath}/admin/deleteAllSuccess/1" method="post">
     <table>
         <tr>
             <td>编号</td>
             <td>姓名</td>
             <td>密码</td>
         </tr>
         <c:forEach items="${listTeacher}" var="Teacher">

             <tr>
                 <td>
                     <input type="checkbox" name="deleteSome" value="${Teacher.tid}" />
                 </td>
                 <td>
                         ${Teacher.tname}
                 </td>
                 <td>
                         ${Teacher.tid}
                 </td>
                 <td>
                         ${Teacher.tpassword}
                 </td>
                 <td><a href="${pageContext.request.contextPath}/admin/deleteOneSuccess/1/${Teacher.tid}">删除</a></td>
             </tr>
         </c:forEach>
         <tr>
             <td>
                 <input type="button" value="全选" onclick="allSelect()" />
                 <input type="button" value="取消全选" onclick="noSelect()" />
             </td>
         </tr>
         <tr>
             <td>
                 <input type="submit" value="部分删除">
             </td>
         </tr>
     </table>
     </form>
 </c:if>
 <c:if test="${flag == 2}" >

     <div>
         <form action="${pageContext.request.contextPath}/admin/deleteAllSuccess/2" method="post">
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
                         <input type="checkbox" name="deleteSome" value="${Student.sid}" />
                     </td>
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
                     <td><a href="${pageContext.request.contextPath}/admin/deleteOneSuccess/2/${Student.sid}">删除</a></td>
                 </tr>

         </c:forEach>
             <tr>
                 <td>
                     <input type="button" value="全选" onclick="allSelect()" />
                     <input type="button" value="取消全选" onclick="noSelect()" />
                 </td>
             </tr>
             <tr>
                 <td>
                     <input type="submit" value="部分删除"/>
                 </td>
             </tr>
         </table>
     </form>
     </div>
 </c:if>
</body>
</html>
