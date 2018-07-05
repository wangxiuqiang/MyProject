<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-7-3
  Time: 下午5:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎登陆</title>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/public.css" type="text/css" media="screen" />
    <link href="css/css.css" rel="stylesheet">
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
    <form action="" method="post" onsubmit="return checkForm();" id="loginform">
        <input type="hidden" name="method" value="login">

        <table>
            <tr>
                <td>用户名:</td>
                <td>
                    <input type="text" name="username" id="username" class="a">
                    <span id="username_span"></span>
                </td>
            </tr>


            <tr>
                <td>密码:</td>
                <td>
                    <input type="password" name="password" id="password" class="a">
                    <span id="password_span"></span>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="checkbox" name="remember" value="on" style="width: 25px;line-height: 35px;"/>记住用户 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="checkbox" name="autologin" value="on" style="width: 25px"/>自动登录
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <input type="submit" value="登录" class="a" style="color:white;background:#87520E;font-size:20px;cursor:pointer">&nbsp;&nbsp;

                    <a href="javascript:void(0)">
                        <font style="font-size:15px;color:red;">&nbsp;忘记密码?</font>
                    </a>
                </td>




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

</body>


</html>