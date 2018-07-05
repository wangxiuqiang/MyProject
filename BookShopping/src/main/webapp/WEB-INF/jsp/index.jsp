<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-7-3
  Time: 上午10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Estore图书商城</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public.css">
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

<div class="estore_content">
    <div class="header">
        <div class="header_png"></div>
        <div class="estore" align="center">
            <h1 align="center" style="font-size: 28px;color: #87520E;margin: 40px 0 10px 0;">Estore图书商城</h1>
            <img src="images/TB1yeWeIFXXXXX5XFXXuAZJYXXX-210-210.png_50x50.jpg" style="border-radius:50%">
            <br/>
            <font size="2">Hi!你好</font>
            <br/>
            <br/>
            <button>注册</button>
            <button>登录</button>
        </div>
    </div>
    <aside class="leftaside">
        <div class="category">
            <h1>图书类别</h1>
            <div>
                <ul>
                    <li>
                        <img src="images/plane.png" align="center">
                        <a href="#">小说</a>
                    </li>
                    <li>
                        <img src="images/plane.png" align="center">
                        <a href="#">童书</a>
                    </li>
                    <li>
                        <img src="images/plane.png" align="center">
                        <a href="#">学习考试</a>
                    </li>
                    <li>
                        <img src="images/plane.png" align="center">
                        <a href="#">文学</a>
                    </li>
                    <li>
                        <img src="images/plane.png" align="center">
                        <a href="#">音乐</a>
                    </li>
                    <li>
                        <img src="images/plane.png" align="center">
                        <a href="#">艺术</a>
                    </li>
                    <li>
                        <img src="images/plane.png" align="center">
                        <a href="#">科技</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="author">
            <h1>人气作家</h1>
            <div class="content">
                <figure>
                    <img src="images/auth.png" width="77" height="83">
                    <figcaption>时寒冰</figcaption>
                    <p>字暖之，70年代生人。工商管理博士。著名经济趋势研究专家</p>
                </figure>
                <a href="#" style="background:#87520E;color:white; padding:6px;text-decoration:none;">查看</a>
            </div>
            <div class="content">
                <figure>
                    <img src="images/auth.png" width="77" height="83">
                    <figcaption>时寒冰</figcaption>
                    <p>字暖之，70年代生人。工商管理博士。著名经济趋势研究专家</p>
                </figure>
                <a href="#" style="background:#87520E;color:white; padding:6px;text-decoration:none;">查看</a>
            </div>
            <div class="content">
                <figure>
                    <img src="images/auth.png" width="77" height="83">
                    <figcaption>时寒冰</figcaption>
                    <p>字暖之，70年代生人。工商管理博士。著名经济趋势研究专家</p>
                </figure>
                <a href="#" style="background:#87520E;color:white; padding:6px;text-decoration:none;">查看</a>
            </div>
            <div class="content">
                <figure>
                    <img src="images/auth.png" width="77" height="83">
                    <figcaption>时寒冰</figcaption>
                    <p>字暖之，70年代生人。工商管理博士。著名经济趋势研究专家</p>
                </figure>
                <a href="#" style="background:#87520E;color:white; padding:6px;text-decoration:none;">查看</a>
            </div>
            <div class="content">
                <figure>
                    <img src="images/auth.png" width="77" height="83">
                    <figcaption>时寒冰</figcaption>
                    <p>字暖之，70年代生人。工商管理博士。著名经济趋势研究专家</p>
                </figure>
                <a href="#" style="background:#87520E;color:white; padding:6px;text-decoration:none;">查看</a>
            </div>
        </div>

        <div class="newbook">
            <h1>最新图书</h1>
            <div>
                <ul>
                    <li>
                        <img src="images/star.png" align="center">
                        <a href="#">java编程思想</a>
                    </li>
                    <li>
                        <img src="images/star.png" align="center">
                        <a href="#">java编程思想</a>
                    </li>
                    <li>
                        <img src="images/star.png" align="center">
                        <a href="#">java编程思想</a>
                    </li>
                    <li>
                        <img src="images/star.png" align="center">
                        <a href="#">java编程思想</a>
                    </li>
                    <li>
                        <img src="images/star.png" align="center">
                        <a href="#">java编程思想</a>
                    </li>
                </ul>
            </div>
        </div>
    </aside>

    <div class="main">
        <div class="menubox">
            <div class="flash">
                <embed id="changePlayer" src="images/adImage.swf" wmode="opaque" flashvars="pics=images/screen1.png|images/screen2.png|images/screen3.png|images/screen4.png|images/screen5.png|images/screen6.png&amp;links=#|#|#|#|#|#&amp;texts=&amp;pic_width=512&amp;pic_height=340&amp;show_text=0&amp;txtcolor=000000&amp;bgcolor=FFFFFF&amp;button_pos=4&amp;stop_time=4000"
                       menu="false" bgcolor="#ffffff" quality="high" allowscriptaccess="sameDomain" type="application/x-shockwave-flash"
                       pluginspage="http://www.macromedia.com/go/getflashplayer" width="512" height="340">
            </div>
            <div class="menu">
                <ul>
                    <li style="background: #87520E;font-weight: 900;font-size: 18px;color: #fff">导航菜单</li>
                    <li class="aa">主页</li>
                    <li class="aa">我的购物车</li>
                    <li class="aa">我的订单</li>
                    <li class="aa">下载榜单</li>
                    <li class="aa">关于我们</li>
                    <li class="aa">联系方式</li>
                </ul>
                <input type="text" style="width: 150px;height: 48px;border: 2px #87520E solid ;" />
                <button style="width: 70px;height: 50px;;margin: 0;background: #87520E;border: 2px solid #87520E;font-size: 16px;font-weight: 800;color: #fff">search</button>
            </div>



        </div>


        <div class="tuijian">
            <div class="tuijian_top">
                <h1 style="float: left;">倾情推荐</h1>
                <p style="float: right;">更多商品>></p>
            </div>
            <div class="tuijian_product">
                <div style="float:left; margin: 10px 30px;">
                    <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                    <a href="#">
                        <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                    </a>
                    <br>
                    <span>价格: ￥190</span>
                    <br>
                    <a href="#">查看详细</a>
                    <br>
                    <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                </div>
                <div style="float:left; margin: 10px 30px;">
                    <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                    <a href="#">
                        <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                    </a>
                    <br>
                    <span>价格: ￥190</span>
                    <br>
                    <a href="#">查看详细</a>
                    <br>
                    <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                </div>
                <div style="float:left; margin: 10px 30px;">
                    <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                    <a href="#">
                        <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                    </a>
                    <br>
                    <span>价格: ￥190</span>
                    <br>
                    <a href="#">查看详细</a>
                    <br>
                    <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                </div>
                <div style="float:left; margin: 10px 30px;">
                    <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                    <a href="#">
                        <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                    </a>
                    <br>
                    <span>价格: ￥190</span>
                    <br>
                    <a href="#">查看详细</a>
                    <br>
                    <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                </div>
                <div style="float:left; margin: 10px 30px;">
                    <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                    <a href="#">
                        <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                    </a>
                    <br>
                    <span>价格: ￥190</span>
                    <br>
                    <a href="#">查看详细</a>
                    <br>
                    <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                </div>
                <div style="float:left; margin: 10px 30px;">
                    <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                    <a href="#">
                        <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                    </a>
                    <br>
                    <span>价格: ￥190</span>
                    <br>
                    <a href="#">查看详细</a>
                    <br>
                    <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                </div>
            </div>
            <div style="float: none; margin-top: 20px;">
                <button type="button" id="up" style="float: left;background:#87520E;line-height:30px;color:white;border:0;padding:3px 7px;font-size:15px;cursor:pointer;">上一页</button>
                <button type="button" id="down" style="float: right;background:#87520E;line-height:30px;color:white;border:0;padding:3px 7px;font-size:15px;cursor:pointer;">下一页</button>
            </div>

            <div class="new">
                <div class="new_top">
                    <h1 style="float: left;">新书预售</h1>
                    <p style="float: right;">更多商品>></p>
                </div>
                <div class="new_product">
                    <div style="float:left; margin: 10px 30px;">
                        <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                        <a href="#">
                            <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                        </a>
                        <br>
                        <span>价格: ￥190</span>
                        <br>
                        <a href="#">查看详细</a>
                        <br>
                        <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                    </div>
                    <div style="float:left; margin: 10px 30px;">
                        <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                        <a href="#">
                            <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                        </a>
                        <br>
                        <span>价格: ￥190</span>
                        <br>
                        <a href="#">查看详细</a>
                        <br>
                        <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                    </div>
                    <div style="float:left; margin: 10px 30px;">
                        <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                        <a href="#">
                            <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                        </a>
                        <br>
                        <span>价格: ￥190</span>
                        <br>
                        <a href="#">查看详细</a>
                        <br>
                        <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                    </div>
                    <div style="float:left; margin: 10px 30px;">
                        <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                        <a href="#">
                            <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                        </a>
                        <br>
                        <span>价格: ￥190</span>
                        <br>
                        <a href="#">查看详细</a>
                        <br>
                        <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                    </div>
                    <div style="float:left; margin: 10px 30px;">
                        <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                        <a href="#">
                            <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                        </a>
                        <br>
                        <span>价格: ￥190</span>
                        <br>
                        <a href="#">查看详细</a>
                        <br>
                        <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                    </div>
                    <div style="float:left; margin: 10px 30px;">
                        <h3 style="overflow:hidden;width:110px;text-overflow:ellipsis;white-space: nowrap;">Tomcat原理与Java Web系统开发</h3>
                        <a href="#">
                            <img src="images/book6.png" alt="an image" class="image" width="108px" height="100px">
                        </a>
                        <br>
                        <span>价格: ￥190</span>
                        <br>
                        <a href="#">查看详细</a>
                        <br>
                        <button style="background:#87520E;color:white;line-height:15px;font-size:13px;border-radius:5px;border:#87520E;cursor:pointer;">加入购物车</button>
                    </div>
                </div>
                <div style="float: none; margin-top: 20px;">
                    <button type="button" id="up" style="float: left;background:#87520E;line-height:30px;color:white;border:0;padding:3px 7px;font-size:15px;cursor:pointer;">上一页</button>
                    <button type="button" id="down" style="float: right;background:#87520E;line-height:30px;color:white;border:0;padding:3px 7px;font-size:15px;cursor:pointer;">下一页</button>
                </div>

            </div>
        </div>
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