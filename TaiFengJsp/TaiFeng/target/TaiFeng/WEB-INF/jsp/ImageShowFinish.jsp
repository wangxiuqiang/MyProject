<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-5-6
  Time: 下午3:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>结训照片</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ImageShow.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ImageShowFinish.css">
</head>
<body>
<div class="background"></div>
<div class="logo">
    <img src="" alt="跆风跆拳道标志">
</div>
<div class="nav">
    <ul>
        <li><a href="#">首页</a></li>
        <li><a href="#">新闻中心</a></li>
        <li ><a href="#">品势教学</a>
            <div class="videoTeaching">
                <ul>
                    <li><a href="#">低腰带品势</a></li>
                    <hr/>
                    <li><a href="#">高级品势</a></li>
                    <hr/>
                    <li><a href="#">跆拳道舞</a></li>
                </ul>
            </div>
        </li>
        <li><a href="#">精彩图集</a>
            <div class="picture_son">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/ImageShowTraining">训练图集</a></li>
                    <hr/>
                    <li><a href="${pageContext.request.contextPath}/ImageShowRace">比赛图集</a></li>
                    <hr/>
                    <li><a href="${pageContext.request.contextPath}/ImageShowFinish">结训图集</a></li>
                    <hr/>
                    <li><a href="${pageContext.request.contextPath}/">精美壁纸</a></li>
                </ul>
            </div>
        </li>
        <li><a href="#">联系我们</a></li>
    </ul>
    <form action="#">
        <input type="text" placeholder="请输入关键字搜索">
        <span onclick=searchJs() style="cursor: pointer;"></span>
    </form>

</div>
<!--中间一个最大的盒子，放上其他的盒子-->
<div class="middle">
    <div class="nav_Vertical">
        <div class="training">
            <!--训练连接-->
            <span></span>
            <span><a href="${pageContext.request.contextPath}/ImageShowTraining">训练图集</a></span>
        </div>
        <div class="race">
            <!--比赛连接-->
            <span></span>
            <span><a href="${pageContext.request.contextPath}/ImageShowRace">比赛图片</a></span>
        </div>
        <div class="finish">
            <!--结训-->
            <span></span>
            <span><a href="${pageContext.request.contextPath}/ImageShowFinish">结训图片</a></span>
        </div>
        <div class="picture">
            <!--精美壁纸-->
            <span></span>
            <span><a href="${pageContext.request.contextPath}/">精美壁纸</a></span>
        </div>
    </div>
    <div class="allShowPart">
        <!--上面的所有分类的部分的展示-->

        <div class="finish_show">
            <span><a href="${pageContext.request.contextPath}/ImageShowFinish">结训图集</a></span>
            <%--<% int num = 1; %>--%>
            <%--<% for(int row = 0; row < 8 ; row ++){%>--%>
            <%--<div style="padding-top: 5px;">--%>
               <%--<% for(; num < 26 ; num++ ){--%>
                       <%--String s;--%>
                       <%--if(num < 10) { s = "0" + num;}--%>
                       <%--else {s = num + "";}--%>

                       <%--%>--%>
                   <c:forEach items="${name}" var="ImageFinishSmall" >
                   <div style="float: left;padding-left: 30px;padding-top: 20px;">
                       <a href="#"> <img src="${pageContext.request.contextPath}/images/2014-2015-2016/finish/smallShow/${ImageFinishSmall}" alt=""></a>
                   </div>
                   </c:forEach>
                <%--<%if( num % 3 == 0) { num ++ ;break;} %>--%>
                <%--<%}%>--%>
                <%--</div>--%>
            <%--<%}%>--%>
        </div>
    </div>
</div>
<div class="foot">
    <img src="${pageContext.request.contextPath}/images/foot/foot.jpg" alt="版权声明">
</div>

<script type="text/javascript">
    function searchJs() {
        alert("我只是一个图标，点我没用");
    }
</script>
</body>

</html>