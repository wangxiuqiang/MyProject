

//ajax请求回来数据,然后进行打印渲染
function ajaxUtils(url ,formDate , back , bool) {
    if(bool == undefined || bool == '' || bool == null) {
        bool = true;
    }
    $.ajax( {
//            url: "http://39.106.191.144:8180/admin/selectAllSubject/" + page ,
        url: url,
        type: "POST",
        data: formDate,
        dataType: 'json',
        xhrFields: {withCredentials: true},
        async: bool,
        processData:false,
        contentType:false,
        success : function (data) {
          back(data);
        }
    });
}
// //ajax请求回来数据,进行跳转
// function ajaxUtilsUID(url ,formDate ,next, back) {
//     $.ajax( {
// //            url: "http://39.106.191.144:8180/admin/selectAllSubject/" + page ,
//         url: url,
//         type: "POST",
//         data: formDate,
//         dataType: 'json',
//         processData:false,
//         contentType:false,
//         success : function (data) {
//             back(data , next);
//         }
//     });
// }
function findCode(data) {
    var code=[0,-1,1,2,-2,3,-3,4,-4,5,-5,-6,-7];
    var status = ["没有权限","用户名活密码错误","登录成功","查找成功","查询失败","添加或更新成功","添加活更新失败","删除成功","删除失败", "退出成功" , "输入为空值","未知的会话"
    ,"验证码不正确"];
    // alert( data.statuscode );
    // alert(data);
    // alert( data.uname );
    if ( data.statuscode != undefined ) {
        for( var i = 0; i <  code.length ; i++ ) {
            if(  code[i] == data.statuscode) {
                alert( status[i] ) ;
                if( status[i] == "登录成功" ) {
                    if( data.ifAdmin == 1 ) {
                        $(location).attr('href','adminIndex.html?uname='+ data.uname + "&uaccount=" +data.uaccount + "&uid=" + data.uid  + "&uemail=" + data.uemail);
                    }else {
                        $(location).attr('href','userIndex.html?uname='+ data.uname + "&uaccount=" +data.uaccount + "&uid=" + data.uid  + "&uemail=" + data.uemail);

                    }
                }
                if(status[i] == "退出成功") {
                    $(location).attr('href','login.html');
                }
                break;
            }

        }
    } else {
        return 1;
    }
}


