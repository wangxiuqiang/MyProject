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
<%--这个div用来显示单个查询使的输入框和按钮,可以查询学生和老师--%>
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
<%--div中包含两个按钮,一个表示查询全部学生,url中有变量 2 来控制controller中执行查询学生的函数
    同理  另一查询全部老师,url中的变量为1
--%>
<div>
<table>
    <tr>
        <td>
            <form action="${pageContext.request.contextPath}/admin/selectAll/1">
                <input type="submit" value="查询全部教师"/>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/admin/selectAll/2">
                <input type="submit" value="查询全部学生"/>
            </form>
        </td>
    </tr>
</table>
</div>
<%--div中包含下拉菜单和按钮 , 通过选择班级来查询一个班级中的学生  url中的2 用来传入controller,
从controller传回来,可以控制页面的输出
--%>
<div>
    <form method="post" action="${pageContext.request.contextPath}/admin/selectStudentForGrade/2" >
     <table>
        <tr>
            <td>选择学生所在班级查询
                <select name="Gid">
                    <option value="1501">软件1501</option>
                    <option value="1502">软件1502</option>
                    <option value="1503">软件1503</option>
                    <option value="1504">软件1504</option>
                    <option value="1505">软件1505</option>
                </select>
            </td>
            <td>
                <input type="submit" value="查询">
            </td>
        </tr>
     </table>
    </form>
</div>
<%--show = 1 的时候显示的是输入框和按钮组成的可以查老师和学生的,主要控制第一个tr中的输出 --%>
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
<%--flag = 1 控制上面的查询全部教师的输出,主要控制第一个tr中的输出--%>
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
<%--flag = 2 通过cont传过来控制查询学生的输出 ,主要是第一个tr中的格式--%>
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
        <%--这个if,因为有俩个学生的查询,一个是全部的学生,另一个是班级的学生查询,
         如果全部的学生的查询返回的list数组为空,就是按班级查询,显示下面listStudent=null
         中的内容,否则相反
        --%>
    <c:if test="${listStudent != null}">
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
    </c:if>
    <c:if test="${listStudent == null}">
        <tr>
            <td>
                    ${studentGrade.sname}
            </td>
            <td>
                    ${studentGrade.sid}
            </td>
            <td>
                    ${studentGrade.SEid}
            </td>
            <td>
                    ${studentGrade.SGid}
            </td>
            <td>
                    ${studentGrade.spassword}
            </td>
        </tr>
    </c:if>
</table>
    </div>
</c:if>
</body>
</html>
