<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-7-3
  Time: 上午11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>欢迎注册</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public.css" type="text/css" media="screen" />
    <link href="${pageContext.request.contextPath}/css/css.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
</head>

<body>
<div class="topbar">
    <div class="topbar_content">
        <div class="topbar_content_left">
            欢迎光临Estore图书商城
        </div>
        <div class="topbar_content_right">
            <ul>
                <li>
                    <a href="#" style="color:rgb(241, 187, 10)">亲，请登录</a>
                </li>
                <li>
                    <a href="#" style="color: rgb(241, 187, 10)">免费注册</a>
                </li>
                <li>
                    <a href="#">首页</a>
                </li>
                <li>|</li>
                <li>
                    <a href="#">购物车</a>
                </li>
                <li>|</li>
                <li>
                    <a href="#">我的订单</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="estore_content" style="min-height: 800px;">
    <div class="header">
        <div class="header_png"></div>
        <div class="estore" align="center">
            <h1 style="font-size:35px;text-align:center;padding-top:80px;color: #87520E;">Estore
                <br/>图书商城
            </h1>

        </div>
    </div>
    <form action="${pageContext.request.contextPath}/insertUser" method="post" onsubmit="return checkForm();" id="registform">
        <input type="hidden" name="method" value="regist">
        <!-- 我们当前是一个注册操作 -->
        <table>
            <tr>
                <td>用户名:</td>
                <td>
                    <input type="text" name="username" id="username">
                    <span id="username_span"></span>
                </td>
            </tr>


            <tr>
                <td>密码:</td>
                <td>
                    <input type="password" name="password" id="password">
                    <span id="password_span"></span>
                </td>
            </tr>

            <tr>
                <td>确认密码:&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>
                    <input type="password" name="repassword" id="repassword">
                    <span id="repassword_span"></span>
                </td>
            </tr>

            <tr>
                <td>昵称:</td>
                <td>
                    <input type="text" name="nickname" id="nickname">
                    <span id="nickname_span"></span>
                </td>
            </tr>

            <tr>
                <td>邮箱:</td>
                <td>
                    <input type="text" name="email" id="email">
                    <span id="email_span"></span>
                </td>
            </tr>

            <tr>
                <td>验证码:</td>
                <td>
                    <input type="text" name="checkcode" id="checkcode">
                    <img src='checkImage' id="im" onclick="change();">
                    <span id="checkcode_span">
                            </span>
                    <br/>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <input type="submit" value="注册" style="color:white;background:#87520E;font-size:20px;cursor:pointer">&nbsp;&nbsp;

                    <a href="javascript:void(0)" onclick="change();">
                        <font style="font-size:15px;color:red;">&nbsp;看不清，换一张</font>
                    </a>
                </td>

            </tr>
            <tr>
                ${msg}
            </tr>

        </table>
    </form>
</div>


<div id="color">
    <h1>&nbsp;</h1>
    <h2>&nbsp;</h2>
    <h3>&nbsp;</h3>
    <h4>&nbsp;</h4>
    <h5>&nbsp;</h5>
    <span>&nbsp;</span>
    <h6>&nbsp;</h6>
    <blockquote>&nbsp;</blockquote>
    <font>&nbsp;</font>
    <div>&nbsp; </div>
</div>
</div>

<div class="footer1">
    <p align="center">
        总部地址:北京市海淀区小南庄怡秀园甲一号亿德大厦10层 电话：010-61943240
    </p>
    <p align="center"> Copyright © 2005-2020 北京翡翠教育科技有限公司，All Rights Reserved 京ICP备12036804号-23</p>
</div>
<script type="text/javascript">
    function change() {
        $("#im").src = "/checkImage?date=" + new Date();
    }


$("#username").focusout(function (){
    $.ajax({
        url: "http://localhost:8080/checkName",
        type: "post",
        data: {"name": $("#username").val()},
        dataType: "json",
        success: function (data) {
            if(data.msg == "false") {
               $("#username_span").html("用户名已存在");
            }else {
                $("#username_span").html("用户名可以使用");
            }
        }
    });
});


</script>
</body>


</html>