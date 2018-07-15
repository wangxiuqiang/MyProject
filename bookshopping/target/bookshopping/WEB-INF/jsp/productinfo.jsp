<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!-- 将jstl标签库引用到当前页面 -->
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Estore图书商城</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/public.css">
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
                        <a href="${pageContext.request.contextPath}/showLogin" style="color:rgb(241, 187, 10)">亲，请登录</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/showReg" style="color: rgb(241, 187, 10)">免费注册</a>
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

    <div class="estore_content" style="min-height:900px">
        <div class="header">
            <div class="header_png"></div>
            <div class="estore" align="center">
                <h1 align="center" style="font-size: 28px;color: #87520E;margin: 40px 0 10px 0;">Estore图书商城</h1>
                <img src="images/TB1yeWeIFXXXXX5XFXXuAZJYXXX-210-210.png_50x50.jpg" style="border-radius:50%">
                <br/>
                <font size="2">Hi!你好</font>
                <br/>
                <br/>
              <a href="${pageContext.request.contextPath}/showReg" style="color: rgb(241, 187, 10)"><button>注册</button></a>
                 <a href="${pageContext.request.contextPath}/showLogin" style="color:rgb(241, 187, 10)"><button>登录</button></a>
            </div>
        </div>
        <aside class="leftaside" style="min-height:600px">
            <div class="category">
                <h1>图书类别</h1>
                <div>
                    <ul>
                        <li>
                            <img src="images/plane.png" align="center">
                            <a href="${pageContext.request.contextPath }/showproductkinds?type=novel">小说</a>
                        </li>
                        <li>
                            <img src="images/plane.png" align="center">
                            <a href="${pageContext.request.contextPath }/showproductkinds?type=cartoon">童书</a>
                        </li>
                        <li>
                            <img src="images/plane.png" align="center">
                            <a href="${pageContext.request.contextPath }/showproductkinds?type=study">学习考试</a>
                        </li>
                        <li>
                            <img src="images/plane.png" align="center">
                            <a href="${pageContext.request.contextPath }/showproductkinds?type=literature">文学</a>
                        </li>
                        <li>
                            <img src="images/plane.png" align="center">
                            <a href="${pageContext.request.contextPath }/showproductkinds?type=music">音乐</a>
                        </li>
                        <li>
                            <img src="images/plane.png" align="center">
                            <a href="${pageContext.request.contextPath }/showproductkinds?type=art">艺术</a>
                        </li>
                        <li>
                            <img src="images/plane.png" align="center">
                            <a href="${pageContext.request.contextPath }/showproductkinds?type=science">科技</a>
                        </li>
                    </ul>
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
       
        <div class="main" style = "height:600px">
        
    
            <div class="proMore" style="margin:20px 50px">
			
			   <img src="${product.imgurl }" style="float:left;margin-right:20px;width="216px" height="200px">
				<table style="width:60%;" >

					<tr>
					   <!--<td rowspan="5"> <img src="http://localhost:8080/bookEstore//upload/1/caab30e4-7f1d-4a0d-a23d-1246840bc1a8_s.jpg">
			           <img src="images/subpart.png">
			           </td>-->
						<td><h2>商品名称:${product.name }</h2><br>
						
						</td>
				</tr>

					<tr class="lineH">
						<td>商品价格:${product.price }</td>
				</tr>
					<tr class="lineH">
						<td>商品类别${product.category }</td>
				</tr>
					<tr class="lineH">
						<td>商品数量:${product.pnum}</td>
				</tr>
					<tr style="font-size:18px;">
						<td >商品描述:&nbsp;&nbsp;<span style="height:10px;width:200px;font-size:16px;">${product.description }</span></td>
				</tr>

					<tr>
						<td colspan="2"><button onclick="addProductToCart('482b5255-741d-4466-8596-26b68db91dbb')" style="background:#87520E;color:white;line-height:30px;font-size:18px;border-radius:5px;border:#87520E;padding:5px 40px;cursor:pointer;">加入购物车</button></td>
				</tr>
			</table>
			
		
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