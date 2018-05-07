<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-5-6
  Time: 下午3:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>跆风跆拳道首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <script src="${pageContext.request.contextPath}/js/index.js" type="text/javascript"></script>
    <script type="text/javascript">
        function searchJs() {
            alert("我只是一个图标，点我没用");
        }
        //用来获取url中问号后面的数值的方式
        // function getImageName() {
        //     var url = location.search;
        //     if(url.indexOf("?") != -1) {
        //         var str = url.substr(1); //获取了？后面的值
        //          var strs = str.
        //         alert(str);
        //     }
        // }
    </script>
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
        <a id="search" onclick=searchJs() href=""><span></span></a>
    </form>

</div>
<div id="turnPicture">
    <div id="turn" style="left: -800px;">
        <!-- 轮播图的制作-->
        <img src="${pageContext.request.contextPath}/images/pictureTurn/turn3.jpg" alt="">
        <img src="${pageContext.request.contextPath}/images/pictureTurn/turn1.jpg" alt="">
        <img src="${pageContext.request.contextPath}/images/pictureTurn/turn2.jpg" alt="">
        <img src="${pageContext.request.contextPath}/images/pictureTurn/turn3.jpg" alt="">
        <img src="${pageContext.request.contextPath}/images/pictureTurn/turn1.jpg" alt="">
    </div>
    <div id="arrow-l"></div>
    <div id="arrow-r"></div>
    <div id="buttons">
        <ul>
            <!-- index 自定义的变量，用来控制是哪个li-->
            <li index="1" class="alive" ></li>
            <li index="2" class="" ></li>
            <li index="3" class="" ></li>
        </ul>
    </div>
    <div class="buttons_add_left"></div>
    <div class="buttons_add_right"></div>

</div>
<div class="club">
    <div class="introduce">
        <span></span>
        <span><a href="#">社团简介</a></span>
    </div>
    <div class="history">
        <span></span>
        <span><a href="#">社团发展史</a></span>
    </div>
    <div class="knowledge">
        <span></span>
        <span><a href="#">跆拳道知识</a></span>
    </div>

    <div class="news">
        <span>社团公告&nbsp;&nbsp;></span>
        <span>.......................................................</span>
        <div class="newsIn">
            <ul>
                <li>1</li>
                <li>2</li>
                <li>3</li>
                <li>4</li>
            </ul>
        </div>
    </div>
</div>
<div class="Image">
    <span><a href="#">精彩图片</a></span>
    <div class="ImageDiv">
        <ul>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-01(IMG_4782).jpg" alt=""></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-02(IMG_4762).jpg" alt=""></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-03(IMG_4776).jpg" alt=""></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-04(IMG_4772).jpg" alt=""></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-05(IMG_4845).jpg" alt=""></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-01(IMG_4782).jpg" alt=""></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-02(IMG_4762).jpg" alt=""></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-03(IMG_4776).jpg" alt=""></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-04(IMG_4772).jpg" alt=""></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/images/smallShow/14-05(IMG_4845).jpg" alt=""></a></li>
        </ul>
    </div>
</div>

<div class="Video">
    <span><a href="#">有趣视频</a></span>
    <div class="VideoDiv1">
        <img src="${pageContext.request.contextPath}/video/VideoImages/index/1.png" alt="">
    </div>
    <div class="VideoDiv2">
        <img src="${pageContext.request.contextPath}/video/VideoImages/index/2.png" alt="">
    </div>
    <div class="VideoDiv3">
        <img src="${pageContext.request.contextPath}/video/VideoImages/index/3.png" alt="">
    </div>
    <div class="VideoDiv4">
        <img src="${pageContext.request.contextPath}/video/VideoImages/index/4.png" alt="">
    </div>
</div>
<div class="foot">
    <img src="${pageContext.request.contextPath}/images/foot/foot.jpg" alt="版权声明">
</div>
</body>
</html>
