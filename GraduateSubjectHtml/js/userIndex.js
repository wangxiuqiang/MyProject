

function updateUpwdBefore() {
    $("#middle").empty();
    $("#middle").append( "<div style='position: absolute; left: 26%'>" +
        " <h2>添加邮箱信息:</h2>" +
        "<div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">旧密码:</label>\n" +
        "        <div class=\"layui-input-inline\" >\n" +
        "            <input  id='oldPwd' type=\"password\" name=\"oldPwd\" lay-verify=\"required\" placeholder=\"请输入密码\" autocomplete=\"off\"\n" +
        "                   class=\"layui-input\" value=''>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "<div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">新密码:</label>\n" +
        "        <div class=\"layui-input-inline\" >\n" +
        "            <input  id='newPwd' type=\"password\" name=\"newPwd\" lay-verify=\"required\" placeholder=\"请输入密码\" autocomplete=\"off\"\n" +
        "                   class=\"layui-input\" >\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">确认密码:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input  id=\"checkPwd\" type=\"password\" name=\"checkPwd\" placeholder=\"请输入确认密码\" autocomplete=\"off\" class=\"layui-input\" " +
        ">\n" +
        "<label><a href='javascript:void(0);' onclick='emailToUpdateUpwd()' >忘记密码?</a> </label>"+
        "        </div>\n"  +
        "    </div>"
        +'<button class="layui-btn layui-btn-normal" onclick="updataUpwdFinsih()" style="position:absolute; left: 46%;">提交</button>'
        +"</div>");

}

/**
 * 使用邮箱修改密码
 */
function emailToUpdateUpwd() {
    var formData = new FormData();
    formData.append("uaccount", $("#uaccount").val() );
    formData.append("content", "您的密码系统已经帮你初始化为123456,请注意保管" );
    //没有添加服务端
    var url = "http://127.0.0.1:8099/user/upwdFirst";
    ajaxUtils(url,formData,findCode , true );
}
function updataUpwdFinsih() {
    var formData = new FormData();
    if( $("#newPwd").val() == '' || $("#checkPwd") == '') {
        alert("输入不能为空");
        return;
    }

    if( $("#newPwd").val() != $("#checkPwd").val()) {
        alert("两次密码输入不一致");
        return;
    }

    formData.append("upwd", $("#newPwd").val());
    formData.append("uaccount", $("#uaccount").val() );
    var url ="http://127.0.0.1:8099/user/updatePwd";
    ajaxUtils(url,formData,findCode , true );
    alert("请重新登录.");
    ajaxUtils("http://127.0.0.1:8099/logout",null,findCode , true );
    $(location).attr('href','login.html');
}
function emailHtml() {
    $("#middle").empty();

    $("#middle").append( "<div style='position: absolute; left: 30%'>" +
        " <h2>添加邮箱信息:</h2>" +
        "<div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">邮箱地址:</label>\n" +
        "        <div class=\"layui-input-inline\" >\n" +
        "            <input  id='uemail' type=\"text\" name=\"uemail\" lay-verify=\"required\" placeholder=\"请输入邮箱\" autocomplete=\"off\"\n" +
        "                   class=\"layui-input\" value=''>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">邮箱验证码:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input  id=\"check\" type=\"text\" name=\"check\" placeholder=\"请输入验证码\" autocomplete=\"off\" class=\"layui-input\" " +
        "value=\"\">\n" +
        "<a href='javascript:void(0);' onclick='sendEmail()' > 获取验证码</a>"+
        "        </div>\n" +
        "    </div>"+
       '<button class="layui-btn layui-btn-normal" onclick="updateEmailFinish()" style="position:absolute; left: 46%;">提交</button>'
        +"</div>");

}
/**
 发送邮件进行确认,确认完之后实现绑定
 **/
function sendEmail() {
    var formData = new FormData();
    formData.append('content','验证码为:8635' );
    formData.append('uemail', $("#uemail").val());
    var url ="http://127.0.0.1:8099/user/sendEmailToCheck";
    ajaxUtils(url , formData , findCode);

}

function updateEmailFinish() {
    var formData = new FormData();
    formData.append("uemail",$("#uemail").val());
    formData.append("uaccount" , $("#uaccount").val() );
    var url = "http://127.0.0.1:8099/user/updateEmail";
    ajaxUtils(url,formData,findCode );
}

/**
 * 查找全部选题
 */


function selectAllSubjectForUser(page) {

    selectSubjectForUser(page,'' ,true);
}
//查找选题
function selectSubjectForUser( page1  ,bool) {
    $("#middle").empty();
    var  url = "http://127.0.0.1:8099/admin/selectAllSubject/" + page1;
//数据的总数量
    var total1 = 0 ;
    //保存先前的页码,便于下面引用
    var pagePre = page1;
    // alert(pagePre == page1 );
    $("#formDiv").remove();
    $("#demo1").remove();


//        alert( page);
    $("#middle").append("   <div id='formDiv' class=\"layui-form\">\n" +
        "                <table class=\"layui-table\">\n" +
        "                    <colgroup>\n" +
        "                        <col width=\"150\">\n" +
        "                        <col width=\"150\">\n" +
        "                        <col width=\"200\">\n" +
        "                        <col>\n" +
        "                    </colgroup>\n" +
        "                    <thead>\n" +
        "                    <tr>\n" +
        "                        <th>序号</th>\n" +
        "                        <th>选题题目</th>\n" +
        "                        <!--<th></th>-->\n" +
        "                        <th>选题介绍</th>\n" +
        "                        <th>被选数量</th>" +
        "                        <th>操作</th>\n" +
        "                    </tr>\n" +
        "                    </thead>\n" +
        "                    <tbody id=\"tbody\">\n" +
        // "                    <tr>\n" +
        // "                        <td>贤心</td>\n" +
        // "                        <td>汉族</td>\n" +
        // "                        <td>1989-10-14</td>\n" +
        // "                        <td>1989-10-14</td>\n" +
        // "\n" +"<td> <a href=''>修改</a> |  <a href=''>删除</a> </td>" +
        // "                    </tr>\n" +
        "                    </tbody>\n" +
        "                </table>\n" +
        "            </div>");

    // $("#father").append("123");



    //请求url 返回数据
    ajaxUtils(url , null, ajaxback , bool );
    function ajaxback(data) {
        // alert(data.total);
        var result =  findCode(data);
        if ( result == 1 ) {
            total1 = data.pages * 10;
            // alert(total);
            for (var i = 0; i < data.list.length ; i++ ) {
                $("#tbody").append( "<tr>\n" + "<td>" +  (i+1)  + "</td>\n" +
                    "                        <td>"+ data.list[i].sname + "</td>\n" +
                    "                        <td>"+ data.list[i].sintroduce + "</td>\n" +
                    "                        <td><a href='javascript:void(0)' onclick='selectUser(1,\"\" ,\"\",0,0,data.list[i].sid)' >"+ data.list[i].count + "</a></td>\n" +
                    "\n" + "<td> <a href='javascript:void(0)' onclick='addSubjectToSelf(" + data.list[i].sid
                    +")' style='color: blue;'>添加</a></td>"+
                    "                    </tr>");
            }
        }
        $("#middle").append("<div id=\"demo1\" style=\"left:50%; margin-left: -200px; position: absolute; \"></div>");


        //JavaScript代码区域
        layui.use('element', function(){
            var element = layui.element;

        });
        //分页
        layui.use(['laypage', 'layer' ], function() {
            var laypage = layui.laypage
                , layer = layui.layer;
            //总页数大于页码总数
            laypage.render({
                elem: 'demo1'
                , count: total1 //数据总数
                ,limit:10 //每页展示条数
                ,curr: page1
                , groups: 5// 默认的连续出现的页码数
                // ,hash: true,
                ,layout: ['prev', 'page', 'next','skip']
                // ,curr: location.hash.replace('#!fenye=', '') //获取起始页
                , jump: function (obj ,first) {
                    //获取到当前的页数
                    // alert(obj.curr)
                    page1 = obj.curr;
                    // alert(page1);
                    // alert(pagePre);
                    if( !first ) {
                        // alert("我进来了");
                        if ( page1 !== pagePre ) {

                            selectSubjectForUser( page1 ,true);
                        }
                    }


                    // selectSubject(page,'',false); //导致循环调用了
                    // alert(page);
                }
            });
        });

    }


}

/**
 * 给自己添加选题,
 * @param sid
 */
function addSubjectToSelf(sid) {
    var url = "http://127.0.0.1:8099/user/addSubjectForSelf";
    var formData = new FormData();
    // alert(sid);
    // alert( $("#uid").val());
    formData.append("uid", $("#uid").val());
    formData.append("sid", sid);
    ajaxUtils( url , formData , findCode);
}

/**
 * 删除自己的选题
 */
function delSubjectToSelf(uid) {
    var url = "http://127.0.0.1:8099/user/delSubjectForSelf";
    var formData = new FormData();
    // formData.append("uid", $("#uid").val());
    formData.append("uid", uid);
    ajaxUtils( url , formData , findCode);
}

/**
 * 查看自己的信息和选课信息
 **/

function selectSelf() {
    $("#middle").empty();
    var url = "http://127.0.0.1:8099/admin/selectUser/1" ;
    var formData = new FormData();
    formData.append("uaccount", $("#uaccount").val());

//        alert( page);


    // $("#father").append("123");

    //请求url 返回数据,并添加数据
    ajaxUtils(url, formData, userfinish);

    function userfinish(data) {

        var total = data.total;
        $("#formDiv").remove();
        $("#demo1").remove();
        //添加数据
        $("#middle").append("   <div id='formDiv' class=\"layui-form\">\n" +
            "                <table class=\"layui-table\">\n" +
            "                    <colgroup>\n" +
            "                        <col width=\"50\">\n" +
            "                        <col width=\"120\">\n" +
            "                        <col width=\"120\">\n" +
            "                        <col width='120'>\n" +
            "                        <col width=\"120\">\n" +
            "                        <col width=\"120\">\n" +
            "                        <col width=\"120\">\n" +
            "                        <col width=\"120\">\n" +
            "                        <col width=\"120\">\n" +
            "                        <col width=\"120\">\n" +
            "                        <col width='80'>\<n></n>" +
            "                    </colgroup>\n" +
            "                    <thead>\n" +
            "                    <tr>\n" +
            "                        <th>序号</th>\n" +
            "                        <th>学号</th>\n" +
            "                        <!--<th></th>-->\n" +
            "                        <th>姓名</th>\n" +
            "                        <th>专业</th>\n" +
            "                        <th>班级</th>\n" +
            "                        <th>年龄</th>\n" +
            "                        <!--<th></th>-->\n" +
            "                        <th>性别</th>\n" +
            "                        <th>选题</th>\n" +
            "                        <th>进度</th>\n" +
            "                        <th>邮箱</th>\n" +
            "                        <th>操作</th>\n" +
            "                    </tr>\n" +
            "                    </thead>\n" +
            "                    <tbody id=\"tbody\">\n" +
            // "                    <tr>\n" +
            // "                        <td>贤心</td>\n" +
            // "                        <td>汉族</td>\n" +
            // "                        <td>1989-10-14</td>\n" +
            // "\n" +"<td> <a href=''>修改</a> |  <a href=''>删除</a> </td>" +
            //
            // "                        <td>贤心</td>\n" +
            // "                        <td>汉族</td>\n" +
            // "                        <td>1989-10-14</td>\n" +
            // "\n" +"<td> <a href=''>修改</a> |  <a href=''>删除</a> </td>" +
            // "                        <td>汉族</td>\n" +
            // "                        <td>1989-10-14</td>\n" +
            // "\n" +"<td> <a href=''>修改</a> |  <a href=''>删除</a> </td>" +
            // "                    </tr>\n" +
            "                    </tbody>\n" +
            "                </table>\n" +
            "            </div>");

        var result = findCode(data);
        if (result == 1) {
            if( data.list.length == 0 ) {
                alert("还没有进行选题,请尽快选题!");
            } else {
                for (var i = 0; i < data.list.length; i++) {
                    $("#tbody").append('<tr id ="' + i + '"></tr>');
                    $("#" + i + "").append("<td>" + (i + 1) + "</td>\n" +
                        "                        <td>" + data.list[i].user.uaccount + "</td>\n" +
                        "                        <td>" + data.list[i].user.uname + "</td>\n" +
                        "                        <td>" + data.list[i].college.cname + "</td>\n" +
                        "                        <td>" + data.list[i].grade.gname + "</td>\n" +
                        "                        <td>" + data.list[i].user.uage + "</td>\n" +
                        "                        <td>" + data.list[i].user.usex + "</td>\n" +
                        "                        <td>" + data.list[i].subject.sname + "</td>\n" +
                        "                        <td>" + data.list[i].user.pid + "</td>\n" +
                        "                        <td>" + data.list[i].user.uemail + "</td>\n");
                    // "                        <td>" + data.list[i].uname + "</td>\n" +

                    $("#" + i + "").append('<td> <a href="javascript:void(0)" onclick="delSubjectToSelf(' + data.list[i].user.uid + ')"' +
                        ' style="color: red;">删除</a></td>');

                }
            }

        }


    }
}
function uploadFile() {
    $("#middle").empty();
    var text = ["需求分析" ,"概要设计","详细设计","报告总结","项目成果"];
    $("#middle").append("   <div id='formDiv' class=\"layui-form\">\n" +
        "                <table class=\"layui-table\">\n" +
        "                    <colgroup>\n" +
        "                        <col width=\"20\">\n" +
        "                        <col width=\"90\">\n" +
        "                        <col width=''>\n" +
        "                        <col width='90'>\n" +
        "                    </colgroup>\n" +
        "                    <thead>\n" +
        "                    <tr>\n" +
        "                        <th>序号</th>\n" +
        "                        <th>进度</th>\n" +
        "                        <!--<th></th>-->\n" +
        "                        <th>文件上传</th>\n" +
        "                        <th>状态</th>\n" +
        "                    </tr>\n" +
        "                    </thead>\n" +
        "                    <tbody id=\"tbody\">\n" +

        "                    </tbody>\n" +
        "                </table>\n" +
        "            </div>");
    for( var i = 0; i < 5 ;i++) {
        $("#tbody").append('<tr><td>' +  (i+1) +'</td> <td>'+ text[i] +'  </td> <td id="buttonUpload' + i +'"></td><td id="status' + (i+1) +'"></td> </tr>')
        $("#buttonUpload"+ i +"").append('<div class="layui-upload">\n' +
            '  <input type="file" class="layui-btn layui-btn-normal" id="file' + (i+1) + '" multiple="multiple" >选择文件</input>\n' +
            '  <button type="button" class="layui-btn" id=fileUpload'+ (i+1) +' onclick="uploadFileFinish('+ (i+1) +')" >开始上传</button>\n' +
            // '  <button type="button" class="layui-btn"  onclick="selectProgress()" >123</button>\n' +
            '</div>');
}
    selectProgress();

}


function uploadFileFinish(i) {

    // if( i == 1 ) {
        var formData = new FormData();
        var files = $("#file"+i+"").prop('files');
        formData.append("file" , files[0]);
        formData.append("uaccount", $("#uaccount").val());
        formData.append("uname", $("#userName").val());
        formData.append("i", i );
        formData.append("uid" , $("#uid").val());
        formData.append("pid" , $("#pid").val());

        var url = "http://127.0.0.1:8099/user/uploadFile";
        ajaxUtils(url,formData,findCode ,true);
    // } else {
    //     var formData = new FormData();
    //     var files = $("#file"+i+"").prop('files');
    //     formData.append("file" , files[0]);
    //     formData.append("uid" , $("#uid").val());
    // }


}


function selectProgress() {
    var formData = new FormData();
    formData.append("pid" , $("#pid").val());
    var url = "http://127.0.0.1:8099/user/selectProgress";
    ajaxUtils(url , formData , back , true);
    function back(data) {
        var result = findCode( data );
        if (result == 1) {
            if( data.pone != undefined ) {
                $("#status1").text("已完成");
                $("#status1").css('color','green');
            }else  {
                $("#status1").text("未完成");
                $("#status1").css('color','red');
            }
            if( data.ptwo != undefined ) {
                $("#status2").text("已完成");
                $("#status2").css('color','green');

            }else  {
                $("#status2").text("未完成");
                $("#status2").css('color','red');
            }
            if( data.pthree != undefined ) {
                $("#status3").text("已完成");
                $("#status3").css('color','green');
            }else  {
                $("#status3").text("未完成");
                $("#status3").css('color','red');

            }
            if( data.pfour != undefined ) {
                $("#status4").text("已完成");
                $("#status4").css('color','green');

            }else  {
                $("#status4").text("未完成");
                $("#status4").css('color','red');

            }
            if( data.pfive != undefined ) {
                $("#status5").text("已完成");
                $("#status5").css('color','green');

            }else  {
                $("#status5").text("未完成");
                $("#status5").css('color','red');

            }
        }
    }

}
