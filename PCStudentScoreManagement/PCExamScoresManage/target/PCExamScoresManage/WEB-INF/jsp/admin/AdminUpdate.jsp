<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-1-14
  Time: 上午9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>信息更改</title>
</head>
<body>
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
<%--div中包含两个按钮,一个表示查询全部学生,url中有变量 2 来控制controller中执行查询学生的函数
    同理  另一查询全部老师,url中的变量为1
--%>
<div>
    <table>
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/admin/updateForType/1/2">
                    <input type="submit" value="查询全部教师"/>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/updateForType/2/2">
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
    <form method="post" action="${pageContext.request.contextPath}/admin/updateForType/2/3" >
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


<%--flag = 1 控制上面的查询全部教师的输出,主要控制第一个tr中的输出
   在selectAll的url中   part2
--%>
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
                <td><a href="${pageContext.request.contextPath}/admin/updateOne/${Teacher.tid}/1">更改</a></td>

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
                        <td><a href="${pageContext.request.contextPath}/admin/updateOne/${Student.sid}/2">更改</a></td>

                    </tr>

                </c:forEach>
            </c:if>
            <%-- 如果 不调用这个函数 他就是lisStudent 为空  part3--%>
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
                    <td><a href="${pageContext.request.contextPath}/admin/updateOne/${studentGrade.sid}/2">更改</a></td>
                </tr>
            </c:if>
        </table>
    </div>
</c:if>
</body>
</html>
