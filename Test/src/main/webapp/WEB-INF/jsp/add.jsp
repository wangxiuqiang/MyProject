<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-10-2
  Time: 下午6:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提交</title>
    <script >

    </script>
</head>
<body>

<form action="${pageContext.request.contextPath}/insertInfo" enctype="multipart/form-data" method="post">
<table>
    <label style="color: red;">${failure}</label>

    <label style="color: red;">${success}</label>

    <tr>
        <td>
            名字:
        </td>
        <td><input name="name" value="" type="text"/></td>
    </tr>
    <tr>
        <td>
            简介:
        </td>
        <td>
            <textarea name="introduce"  cols="30" rows="10"></textarea>
        </td>

    </tr>
    <tr>
        <td >
            文件:
        </td>
        <td>
            <input type="file"  name="file" />
        </td>
    </tr>

    <tr>
        <td>
            <input type="submit" value="提交" />
        </td>

    </tr>

</table>
</form>
</body>
</html>
