<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>我的订单</title>
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
                <button>注册</button>
                <button>登录</button>
            </div>
        </div>

        <div align="center" id="cart">
            <table class="maintable" border="1" style="text-align: center">
                <tbody>
                    <tr style="background:#87520E;color:white;line-height:40px;font-size:18px;border:#87520E solid 1px;text-align: center">
                        <td>订单编号</td>
                        <td>订单信息</td>
                        <td>订单状态</td>
                       
                        <td>订单操作</td>
                    </tr>

					<c:if test="${empty list }">
						<tr><td colspan="4">没有订单信息</td></tr>
					</c:if>
					<c:if test="${!empty list }">
						<c:forEach items="${list }" var="order">
		                    <tr>
		                        <td width="26%">${order.id }</td>
		                        <td width="50%">
		                            <table style="border-collapse: collapse;border:#f8e49b;width:100%;text-align:center;" border="1">
		                                <tbody>
		                                    <tr style="background:#87520E;color:white;line-height:35x;border:white solid 1px;">
		                                        <td  width="45%">商品名称</td>
		                                        <td  width="20%">商品单价</td>
		                                        <td  width="20%">购买数量</td>
		                                        <td >总价</td>
		                                    </tr>
		
										<c:forEach items="${order.orderItems }" var="item" varStatus="count">
		                                    <tr>
		                                        <td>${item.product.name }</td>
		                                        <td>${item.product.price }</td>
		                                        <td>${item.buynum }</td>
		                                        <c:if test="${count.count==1 }">
		                                       	 <td rowspan="10">${order.money }</td>
		                                        
		                                        </c:if>
		                                    </tr>
										
										</c:forEach>
		
		                                    
		
		
		                                </tbody>
		                            </table>
		                        </td>
		                        <td>
		                            <a href="/bb/pay.jsp?id=a5f10151-6841-4e13-a4c3-570af3d8968f&amp;money=279.0">${order.paystate }</a>
		                        </td>
		                       
		
		                        <td>
		                            <a href="/bb/order?method=del&amp;id=a5f10151-6841-4e13-a4c3-570af3d8968f">取消订单</a>
		                        </td>
		
		
		                    </tr>
						</c:forEach>
					</c:if>
                   
                </tbody>
            </table>


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