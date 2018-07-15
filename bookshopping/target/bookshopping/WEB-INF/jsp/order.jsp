<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>购物车</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css">
</head>

<body>
    <div class="topbar">
        <div class="topbar_content">
            <div class="topbar_content_left">
                欢迎光临Estore图书商城
            </div>
            <div class="topbar_content_right">
               <ul>
                    <c:if test="${empty user}">
                    <li>
                          <a href="${pageContext.request.contextPath}/showLogin" style="color:rgb(241, 187, 10)">亲，请登录</a>
                    </li>
                     <li>
                        <a href="${pageContext.request.contextPath}/showReg" style="color: rgb(241, 187, 10)">免费注册</a>
                    </li>
                    </c:if>
                     <c:if test="${!empty user}">
                    <li>
                          <a href="#" style="color:rgb(241, 187, 10)">${user.nickname}</a>
                    </li>
                     <li>
                          <a href="${pageContext.request.contextPath}/logout" style="color:rgb(241, 187, 10)">退出登录</a>
                    </li>
                    </c:if> 
                   
                    <li>
                        <a href="${pageContext.request.contextPath}/showIndex">首页</a>
                    </li>
                    <li>|</li>
                    <li>
                        <a href="${pageContext.request.contextPath}/showCart">购物车</a>
                    </li>
                    <li>|</li>
                    <li>
                         <a href="${pageContext.request.contextPath}/showCreateOrder">我的订单</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="cart_content">
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

        <div align="center" id="cart">
            
			<form action="${pageContext.request.contextPath }/createOrder" method="post">
	
		<table id="ordertable" width="400px" border="1">
			<tbody><tr style="background:#87520E;color:white;line-height:40px;">
				<td>&nbsp;&nbsp;收货地址:
				<input name="receiverinfo" style="width:300px;height:30px;" type="text">
				</td>
			</tr>

			<tr>
				<td>
					<table class="innertable" width="100%" border="1">
						<tbody><tr style="border-bottom:solid 1px;">
							<td>商品名称</td>
							<td>单价</td>
							<td>数量</td>
						</tr>
							<c:set var = "money" value="0"></c:set>
							<c:forEach items="${cart }" var="pro">
							
								<tr>
									<td>${pro.key.name }</td>
									<td>${pro.key.price }</td>
									<td>${pro.value }</td>
								</tr>
								<c:set var = "money" value="${money + pro.key.price * pro.value }"></c:set>
							</c:forEach>
						
							
						
							
						
					</tbody></table></td>
			</tr>
			<tr>
				<td>订单总价:${money }元 <input value="${money }" name="money" type="hidden">
				     <input value="生成订单" style="background:#87520E;border:none;cursor:pointer;color:white; border-radius:5px;line-height:30px;padding:5px 30px;font-size:19px;float:right;" type="submit">
				</td>
			</tr>
		</tbody></table>
	</form>
			
			

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