<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-11-9
  Time: 下午2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>毕业选题系统登录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="../../../layui/css/layui.css" media="all">
    <script src="../../../layui/layui.js" charset="utf-8"></script>
    <script src="https://cdn.bootcss.com/fetch/2.0.3/fetch.js"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>
<div style="margin-left: 27%;width: 800px;margin-top: 50px; height: 90px;">
    <h1 style="font-size: 70px;">毕业生毕业设计选题系统</h1>
</div>
<div style="background-color: ghostwhite; border: 1px solid skyblue; position: absolute; height: 300px; width: 500px; top: 50%; margin-top: -150px;
    left: 50%; margin-left: -250px;">
    <h2 style="margin-top: 2px;">请登录:</h2>
    <%--<form action="${pageContext.request.contextPath}/userLogin" , method="post">--%>
        <div class="layui-form-item" style="margin-top:29px; margin-left: 28px;">
            <label class="layui-form-label">学号:</label>
            <div class="layui-input-inline">
                <input type="text" name="uaccount" lay-verify="required" placeholder="请输入学号" autocomplete="off" value=""
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" style="margin-top:29px; margin-left: 28px;">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-inline">
                <input type="password" name="upwd" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">请务必填写用户名</div>
        </div>
        <%--<div class="layui-form-item" style="margin-top:29px; margin-left: 28px;">--%>
            <%--<label class="layui-form-label">单选框</label>--%>
            <%--<div class="layui-input-block">--%>
                <%--<input type="radio" name="sex" value="男" title="男" checked="">管理员--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--<input type="radio" name="sex" value="女" title="女">学生--%>
                <%--&lt;%&ndash;<input type="radio" name="sex" value="禁" title="禁用" disabled="">&ndash;%&gt;--%>
            <%--</div>--%>
        <%--</div>--%>
        <div style="margin-left: 120px;">
            <button type="button" class="layui-btn layui-btn-primary layui-btn-radius" onclick="login()">提  交</button>
            <button type="reset" class="layui-btn layui-btn-primary layui-btn-radius">重  置</button>
        </div>

    <%--</form>--%>

</div>
<script type="application/javascript" >
    var code = {
        "1" : "登录成功"
    };
    var u = document.getElementsByName("uaccount");
    var p = document.getElementsByName("upwd");
    var formDate = new FormData();
    formDate.append("uaccount" , u);
    formDate.append("upwd",p);
    var state = ["账号和密码错误" , "登录成功"];
    <%--window.location = function login() {--%>
        <%--for (var i = 0; i < code.length ; i++ ) {--%>
            <%--if( code[i] === ${statuscode} ) {--%>
                <%--alert( state[i] );--%>
            <%--}--%>
        <%--}--%>
    <%--}--%>
    function login() {
        fetch('/userLogin'), {
            method: 'POST',
            body: formDate,
            headers: new Headers({
                'Content-Type': 'application/x-www-form-urlencoded' ,// 指定提交方式为表单提交
                'Accept': 'application/json' // 通过头指定，获取的数据类型是JSON
            }),
        }.then(function(response) {
                return response.json();
            })
            .then(function(myJson) {
                console.log(myJson);
            });
    }
</script>
</body>
</html>
