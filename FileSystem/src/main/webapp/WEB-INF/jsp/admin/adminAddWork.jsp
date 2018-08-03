<%--
  Created by IntelliJ IDEA.
  User: wxq
  Date: 18-7-7
  Time: 下午2:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>添加工作人员</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/listJSON.js"></script>
    <script type="text/javascript">
//        window.onload = loadWorkPlace("Ucompany");
       $(function (){
           $.ajax({
               url: "${pageContext.request.contextPath}/admin/selectWorkPlace",
               dataType: "json",
               success: function (data){
                   for(var i =0; i< data.length;i++) {
                       var option = "<option value='"+data[i].wname+"'> "+ data[i].wname +"</option>";
                       $("#ucompany").append(option)
                   }
           }
           })
       });

    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/admin/addUser" method="post"  onsubmit="return checkEmail()">
    <span>姓名:</span>
    <input type="text" name="uname" id="uname"/>
    <br>
    <span>邮箱:</span>
    <input type="text" name="uemail" id="uemail" />
    <span id="uemail_span"></span>
    <br>
    <span>用户单位(后改为SELECT标签):</span>
    <select name="ucompany" id="ucompany" >
        <option value="">请选择用户单位</option>
    </select>
    <input type="submit" value="注册" />
</form>
<script type="text/javascript" >
    function checkEmailIfExist() {
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/selectEmail?uemail=" + $("#uemail").val(),
            async: false,
            dataType: "json",
            success: function (data){
                /**
                 * data 为 1 则说明这个emial是存在的,
                 */
                if(data == "1") {
                    $("#uemail_span").html("该邮箱已存在");
                }else {
                    $("#uemail_span").html("邮箱可以使用");
                }
            }
        });
    }

    $("#uemail").focusout(function () {

        var email = $("#uemail").val();
      //  alert(email);
        // var reg = new RegExp("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$");
        var reg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
        if(reg.test(email)) {
            checkEmailIfExist();
        }else{
            $("#uemail_span").html("邮箱格式不对").css("color","red");

        }
    });

    function checkEmail() {
        var test = $('#uemail_span').html();
        if( test == "邮箱可以使用") {

            return true;
        }
        return false;
    }



</script>
</body>
</html>
