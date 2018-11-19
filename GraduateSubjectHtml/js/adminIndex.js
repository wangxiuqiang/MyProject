
function selectAllSubject(page) {
    $("#father").empty();
    selectSubject(page,'' ,false);
}

function selectSubjectByCondition(page ,sname) {

    $("#father").empty();
    if (sname == undefined) {
        sname='';
    }
    //添加文本框
    $("#father").append(" <h2>输入条件进行检索:</h2>" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">题目:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input id=\"sname\" value='' type=\"text\" name=\"sname\" placeholder=\"请输入题目名称:\" autocomplete=\"off\" class=\"layui-input\">\n" +
        "        </div>\n" +
        "    </div>");

    //添加按钮
    $("#father").append("<div style=\"margin-left: 120px;\">\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\" onclick=\"selectSubjectByCondition(1, $('#sname').val())\">检  索</button>\n" +
        "    </div>");
    selectSubject(page,sname ,true);
}

//查找选题
function selectSubject( page1 , sname ,bool) {
    var formData = new FormData();
    formData.append("sname",sname);
    var  url = "http://127.0.0.1:8099/admin/selectSubjectByName/" + page1;
//数据的总数量
    var total1 = 0 ;
    //保存先前的页码,便于下面引用
    var pagePre = page1;
    // alert(pagePre == page1 );
    $("#formDiv").remove();
    $("#demo1").remove();


//        alert( page);
    $("#father").append("   <div id='formDiv' class=\"layui-form\">\n" +
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
    ajaxUtils(url , formData, ajaxback , false );
    function ajaxback(data) {
        // alert(data.total);
       var result =  findCode(data);
       if ( result == 1 ) {
           total1 = data.pages * 10;
           // alert(total1);
           // alert(total);
           for (var i = 0; i < data.list.length ; i++ ) {
               $("#tbody").append( "<tr>\n" + "<td>" +  (i+1)  + "</td>\n" +
                   "                        <td>"+ data.list[i].sname + "</td>\n" +
                   "                        <td>"+ data.list[i].sintroduce + "</td>\n" +
                   "                        <td><a href='javascript:void(0)' onclick='selectUser(1,\"\" ,\"\",0,0,data.list[i].sid)' >"+ data.list[i].count + "</a></td>\n" +
                   "\n" + '<td> <a href="javascript:void(0)" onclick="UpdateSubject('+  data.list[i].sid + '\,' +'\'' +data.list[i].sname + '\''+
                   '\,' +'\'' + data.list[i].sintroduce +'\''
                   + ')" style="color: blue;">修改</a> |  <a href="javascript:void(0)" onclick="delSubject('+ data.list[i].sid +')"' +
                   ' style="color: red;">删除</a></td>'+
                   '                   </tr>');
           }
       }
        $("#father").append("<div id=\"demo1\" style=\"left:50%; margin-left: -200px; position: absolute; \"></div>");


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
                ,limit: 10 //每页展示条数
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

                            selectSubject( page1 , sname ,false);
                        }
                    }


                    // selectSubject(page,'',false); //导致循环调用了
                    // alert(page);
                }
            });
        });

    }


}


//跳转到添加题目信息的页面
function addSubject() {

    //清除页面元素 .empty 是只清除子元素,remove是清除自身和子元素,,demo1是一个分页的东西,,暂时先让他全部页面显示
    $("#father").empty();
    $("#demo1").remove();
    //添加文本框
    $("#father").append(" <h2>添加选题内容:</h2>" +
        "<div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">选题看题目:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input id=\"sname\" type=\"text\" name=\"sname\" lay-verify=\"required\" placeholder=\"请输入选题题目\" autocomplete=\"off\"\n" +
        "                   class=\"layui-input\">\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">选题介绍:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input id=\"sintroduce\" type=\"text\" name=\"sintroduce\" placeholder=\"请输入选题的介绍\" autocomplete=\"off\" class=\"layui-input\">\n" +
        "        </div>\n" +
        "    </div>");

    //添加按钮
    $("#father").append("<div style=\"margin-left: 120px;\">\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\" onclick=\"addSubjectFinish()\">添  加</button>\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\">重  置</button>\n" +
        "    </div>");

}
//提交要添加的题目的信息
function addSubjectFinish() {
    var formDate = new FormData();
    formDate.append("sname", $("#sname").val());
    formDate.append("sintroduce",$("#sintroduce").val());
    alert( $("#sname").val());
    ajaxUtils( "http://127.0.0.1:8099/admin/addSubject" ,formDate , findCode);
}
//弹窗提示,然后删除,
function delSubject(id) {
    var result = confirm( "确认删除" );
    // alert( result );
    var formData = new FormData();
    formData.append( "sid" , id );
    if( result === true ) {
        ajaxUtils( "http://127.0.0.1:8099/admin/delSubject" ,formData , findCode )
    }
    selectSubject(1,'',true);
}

//更新一个题目,更新的时候跳转到add的页面,但是里面有值
function UpdateSubject(sid,sname, sintroduce) {

    //清除页面元素 .empty 是只清除子元素,remove是清除自身和子元素,,demo1是一个分页的东西,,暂时先让他全部页面显示
    $("#father").empty();
    $("#demo1").remove();
    //添加文本框
    $("#father").append(" <h2>添加选题内容:</h2>" +
        "<div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">选题看题目:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input id=\"sname\" type=\"text\" name=\"sname\" lay-verify=\"required\" placeholder=\"请输入选题题目\" autocomplete=\"off\"\n" +
        "                   class=\"layui-input\" value='"+sname +"' />\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">选题介绍:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input id=\"sintroduce\" type=\"text\" name=\"sintroduce\" placeholder=\"请输入选题的介绍\" autocomplete=\"off\" class=\"layui-input\" " +
        "  value='" + sintroduce + "' />\n" +
        // "            <input id=\"sid\" type=\"hidden\" name=\"sintroduce\" placeholder=\"请输入选题的介绍\" autocomplete=\"off\" class=\"layui-input\" " +
        // "  value='" + sid + "' />\n" +
        "        </div>\n" +
        "    </div>");

    //添加按钮
    $("#father").append("<div style=\"margin-left: 120px;\">\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\" onclick=\"updateSubjectFinish("+sid + " )\">更  新</button>\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\">重  置</button>\n" +
        "    </div>");

}
//发送请求 更新
function updateSubjectFinish( id )  {
    // alert( id );
    var formData = new FormData();
    formData.append( "sid" , id );
    formData.append("sname", $("#sname").val());
    formData.append( "sintroduce" , $("#sintroduce").val());
    ajaxUtils( "http://127.0.0.1:8099/admin/updateSubject" , formData , findCode);
}

//下面是对用户的操作

//添加一个用户
function addUser() {


    //清除页面元素 .empty 是只清除子元素,remove是清除自身和子元素,,demo1是一个分页的东西,,暂时先让他全部页面显示
    $("#father").empty();
    $("#demo1").remove();
    //添加文本框
    $("#father").append(" <h2>添加选题内容:</h2>" +
        "<div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">姓名:</label>\n" +
        "        <div class=\"layui-input-inline\" >\n" +
        "            <input id=\"uname\" type=\"text\" name=\"uname\" lay-verify=\"required\" placeholder=\"请输入姓名\" autocomplete=\"off\"\n" +
        "                   class=\"layui-input\">\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">年龄:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input id=\"uage\" type=\"text\" name=\"uage\" placeholder=\"请输入年龄\" autocomplete=\"off\" class=\"layui-input\">\n" +
        "        </div>\n" +
        "    </div>" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">学号:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input id=\"uaccount\" type=\"text\" name=\"uaccount\" placeholder=\"请输入学号\" autocomplete=\"off\" class=\"layui-input\">\n" +
        "        </div>\n" +
        "    </div>" );


    $("#father").append("<form class=\"layui-form\" lay-filter='test' > " +
        "<div class=\"layui-form-item\" >\n" +
        "    <label class=\"layui-form-label\">学院班级:</label>\n" +
        "    <div class=\"layui-input-inline\" >\n" +
        "      <select name=\"ucollege\" id='ucollege'  lay-filter='college'>\n" +
        "        <option value=\"\">请选择专业</option>\n" +
        "      </select>\n" +
        "    </div>\n" +
        "    <div class=\"layui-input-inline\">\n" +
        "      <select name=\"ugrade\" id='ugrade'>\n" +
        "        <option value=\"\">请选择班级</option>\n" +
        "      </select>\n" +
        "    </div>\n" +
        "</div>"  +
        " <div class=\"layui-form-item\">\n" +
        "    <label class=\"layui-form-label\">性别:</label>\n" +
        "    <div class=\"layui-input-block\">\n" +
        "      <input type=\"radio\" name=\"usex\" value=\"男\" title=\"男\">\n" +
        "      <input type=\"radio\" name=\"usex\" value=\"女\" title=\"女\">\n" +
        "    </div>\n" +
        "  </div>" +
        "</form>");
    //下拉框添加内容
    optionDataGet();

    //添加按钮
    $("#father").append("<div style=\"margin-left: 120px;\">\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\" onclick=\"addUserFinish()\">添  加</button>\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\">重  置</button>\n" +
        "    </div>");

    layui.use('form', function(){
        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
        form.on('select(college)',function (data) {
            gradeDataByCid(data.value);
        });
       form.render();
        form.render('select' ,'test'); //刷新select选择框渲染
    });
}
//提交表单
function addUserFinish() {
    var formData = new FormData();
    formData.append( "uaccount" , $("#uaccount").val() );
    formData.append( "uname" , $("#uname").val() );
    formData.append( "uage" , $("#uage").val() );
    formData.append( "ucollege" , $("#ucollege").val() );
    formData.append( "ugrade" , $("#ugrade").val() );
    formData.append( "usex" , $("input[name='usex']:checked").val());
    alert($("#uaccount").val() + '-----' + $("#uname").val()  + '----' + $("#uage").val() + '-----' +$("#ucollege").val() +'---------' +  $("#ugrade").val()
    +'-------' +$("input[name='usex']:checked").val())
    var url = "http://127.0.0.1:8099/admin/addUser";
    ajaxUtils( url , formData , findCode);
}

//查询全部学生
function selectAllUser(page) {
    $("#father").empty();
    selectUser(page,"","",0,0,0);
}
//根据条件查询学生
function selectUserByCondition( page ) {
    $("#father").empty();
    $("#father").append(" <h2>根据以下内容搜索:</h2>" +
        "<div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">姓名:</label>\n" +
        "        <div class=\"layui-input-inline\" >\n" +
        "            <input  id='uname' type=\"text\" name=\"uname\" lay-verify=\"required\" placeholder=\"请输入姓名\" autocomplete=\"off\"\n" +
        "                   class=\"layui-input\" value=''>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">学号:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input id=\"uaccount\" type=\"text\" name=\"uaccount\" placeholder=\"请输入学号\" autocomplete=\"off\" class=\"layui-input\" " +
        "value=\"\">\n" +
        "        </div>\n" +
        "    </div>" );
    $("#father").append("<form class=\"layui-form\" lay-filter='test' > " +
        "<div class=\"layui-form-item\" >\n" +
        "    <label class=\"layui-form-label\">学院班级:</label>\n" +
        "    <div class=\"layui-input-inline\" >\n" +
        "      <select name=\"ucollege\" id='ucollege' lay-filter=\"college\">\n" +
        "        <option value=\"\" >请选择专业</option>\n" +
        "      </select>\n" +
        "    </div>\n" +
        "    <div class=\"layui-input-inline\">\n" +
        "      <select id=\"ugrade\" name=\"ugrade\" >\n" +
        "        <option value=\"\">请选择班级:</option>\n" +
        "      </select>\n" +
        "    </div>\n" +
        "</div>"  +
        "</form>");
    //函数在页面加载完调用 , 添加的是专业的下拉框 选择完专业的然后把班级的找出来
    optionDataGet();
    // $("#ucollege").change( function () {
    //     alert($("#ucollege option:selected").val());
    //     gradeDataByCid( $("#ucollege option:selected").val() );
    // });




    // var uaccount =  ;
    // var uname = $("#uname").val();
    // var ucollege = $("#ucollege option:selected").val();
    // var ugrade = $("#ucollege option:selected").val();
    //添加按钮
    $("#father").append("<div style=\"margin-left: 120px;\">\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\" onclick=\"selectUser(1,$('#uaccount').val(), $('#uname').val(),$('#ucollege option:selected').val(),$('#ucollege option:selected').val() , 0)\">搜  索</button>\n" +
        "    </div>");
    layui.use('form', function(){
        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
        form.on('select(college)',function (data) {
            // alert(data.val());
            if(data.value != 0) {
                gradeDataByCid(data.value);
            }

        });
        form.render();
        form.render('select' ,'test'); //刷新select选择框渲染
    });


    // selectUser(page ,formData);

}
//查询学生
function selectUser(page ,uaccount,uname,ucollege,ugrade,sid ) {

    var url = "http://127.0.0.1:8099/admin/selectUser/" + page ;
    var formData = new FormData();
    if(uaccount != "") {
        formData.append("uaccount" , uaccount);
    }
    if(uname != "" ) {
        formData.append("uname" , uname);
    }
    if( ucollege !=  0) {
        formData.append("ucollege" ,ucollege );
    }
    if(ugrade !=  0) {
        formData.append("ugrade" , ugrade );
    }
    if( sid != 0 || sid != undefined) {
        formData.append("sid" , sid );
    }


//        alert( page);


    // $("#father").append("123");

    //请求url 返回数据,并添加数据
    ajaxUtils(url , formData , userfinish , false);
    function userfinish(data) {

        var total = data.total;
        $("#formDiv").remove();
        $("#demo1").remove();
        //添加数据
        $("#father").append("   <div id='formDiv' class=\"layui-form\">\n" +
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
            "                    <tr >\n" +
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
        if( result == 1) {
            for (var i = 0; i < data.list.length; i++) {
                $("#tbody").append('<tr id ="'+  i +'" onclick="selectProgressForAdmin(' + data.list[i].user.pid + ')"></tr>');
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

                $("#" + i + "").append('<td> <a href="javascript:void(0)" onclick="UpdateUser(' +
                    data.list[i].user.uaccount + '\,' + '\''+ data.list[i].user.uname + '\'' +  '\,' + data.list[i].college.cid + '\,' +
                    data.list[i].grade.gid + '\,' + data.list[i].user.uage + '\,' + '\''+ data.list[i].user.usex +'\''  + '\,'+
                    data.list[i].subject.sid + '\,' + data.list[i].user.pid + '\,' + data.list[i].user.uemail + '\,' +
                    data.list[i].user.uid
                    + ')" style="color: blue;">修改</a> |  <a href="javascript:void(0)" onclick="delUser(' + data.list[i].user.uid + ')"' +
                    ' style="color: red;">删除</a></td>');

            }
        }



        $("#father").append("<div id=\"demo1\" style=\"left:50%; margin-left: -200px; position: absolute; \"></div>");
        // //用来获取当前页数的
        // var page;
        //JavaScript代码区域
        layui.use('element', function () {
            var element = layui.element;

        });
        //分页
        layui.use(['laypage', 'layer'], function () {
            var laypage = layui.laypage
                , layer = layui.layer;

            //总页数大于页码总数
            laypage.render({
                elem: 'demo1'
                , count: total //数据总数
                ,limit:10 //每页展示条数
                ,curr: page
                , groups: 5// 默认的连续出现的页码数
                // ,hash: true,
                ,layout: ['prev', 'page', 'next','skip']
                // ,curr: location.hash.replace('#!fenye=', '') //获取起始页
                , jump: function (obj ,first) {
                    //获取到当前的页数
                    // alert(obj.curr)
                    page = obj.curr;
                    // alert(page1);
                    // alert(pagePre);
                    if( !first ) {
                        // alert("我进来了");
                        if ( page !== pagePre ) {
                            selectUser(page,uaccount,uname,ucollege,ugrade,sid);
                        }
                    }


                    // selectSubject(page,'',false); //导致循环调用了
                    // alert(page);
                }
            });
        });
    }
}

function UpdateUser(uaccount,uname,cid,gid,uage,usex,sid,pid,uemail) {
    //清除页面元素 .empty 是只清除子元素,remove是清除自身和子元素,,demo1是一个分页的东西,,暂时先让他全部页面显示
    $("#father").empty();
    $("#demo1").remove();
    // alert( usex);
    //添加文本框
    $("#father").append(" <h2>添加学生内容:</h2>" +
        "<div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">姓名:</label>\n" +
        "        <div class=\"layui-input-inline\" >\n" +
        "            <input  id='uname' type=\"text\" name=\"uname\" lay-verify=\"required\" placeholder=\"请输入姓名\" autocomplete=\"off\"\n" +
        "                   class=\"layui-input\" value='"+ uname +"'>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">年龄:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input  id=\"uage\" type=\"text\" name=\"uage\" placeholder=\"请输入年龄\" autocomplete=\"off\" class=\"layui-input\" " +
        "value=\" "+ uage + "\">\n" +
        "        </div>\n" +
        "    </div>" +
        "    <div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">学号:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
        "            <input id=\"uaccount\" type=\"text\" name=\"uaccount\" placeholder=\"请输入学号\" autocomplete=\"off\" class=\"layui-input\" " +
        "value=\" "+ uaccount + "\">\n" +
        "        </div>\n" +
        "    </div>" );


    $("#father").append("<form class=\"layui-form\" lay-filter='test' > " +
        "<div class=\"layui-form-item\" >\n" +
        "    <label class=\"layui-form-label\">学院班级:</label>\n" +
        "    <div class=\"layui-input-inline\" >\n" +
        "      <select name=\"ucollege\" id='ucollege' lay-filter='college' >\n" +
        "        <option value=\"\" >请选择专业</option>\n" +
        "      </select>\n" +
        "    </div>\n" +
        "    <div class=\"layui-input-inline\">\n" +
        "      <select id=\"ugrade\" name=\"ugrade\" >\n" +
        "        <option value=\"\">请选择班级</option>\n" +
        "      </select>\n" +
        "    </div>\n" +
        "</div>"  +
        " <div class=\"layui-form-item\">\n" +
        "    <label class=\"layui-form-label\">性别:</label>\n" +
        "    <div class=\"layui-input-block\">\n" +
        "      <input type=\"radio\" name=\"usex\" value=\"男\" title=\"男\">\n" +
        "      <input type=\"radio\" name=\"usex\" value=\"女\" title=\"女\">\n" +
        "    </div>\n" +
        "  </div>" +
        "</form>");
    //下拉框添加信息
    optionDataGet(false);
    //添加按钮
    $("#father").append("<div style=\"margin-left: 120px;\">\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\" onclick=\"updateUserFinish()\">添  加</button>\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\">重  置</button>\n" +
        "    </div>");


    //控制选中的默认的专业和班级
    selectOption(cid,gid);
    function selectOption(cid,gid) {
        //获取到指定id下的 option
        var colleges = $("#ucollege").find("option");

        for( var i = 0 ; i < colleges.length ; i++ ) {
            //使用text调用的是Option的文本值. $() 表明是jquery获取的数据, val表示是value的值
            // alert ( $(colleges[i]).val() );

            if( $(colleges[i]).val() == cid) {
                $("#ucollege").get(0).options[i].selected = true;
                // $(colleges[i]).attr("selected","selected");
                // alert ( $("#ucollege option:selected").val());
            }
        }

        var grades = $("#ugrade").find("option");

        for( var i = 0 ; i < colleges.length ; i++ ) {
            //使用text调用的是Option的文本值. $() 表明是jquery获取的数据, val表示是value的值
            // alert ( $(colleges[i]).val() );
            if( $(grades[i]).val() == gid) {
                $("#ugrade").get(0).options[i].selected = true;
                // $(colleges[i]).attr("selected","selected");
            }
        }

            // $("input[name='usex']").each(function () {
            //     if( $(this).val() == "男" ) {
            //         $(this).checked = true;
            //     }
            // });
        $.each($("input[name='usex']") ,function (index, data) {

            if( data.value == usex ) {
                // alert(usex);
                $(data).prop('checked',true);

            }
        });

        layui.use('form', function(){
            var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
            form.on('select(college)' , function (data) {
                gradeDataByCid(data.value);
            });
            form.render();
            form.render('select' ,'test'); //刷新select选择框渲染
        });

        layui.use(['jquery', 'layer'], function(){
            var $ = layui.$ //重点处
                ,layer = layui.layer;
        });
        // if( )
    }
}

function updateUserFinish() {
    var formData = new FormData();
    formData.append("uaccount", $("#uname").val());
    formData.append("uaccount", $("#uage").val());
    formData.append("uaccount", $("#ucollege").val());
    formData.append("uaccount", $("#ugrade").val());
    formData.append("uaccount", $("input[name='usex']:checked").val());
    var url = "http://127.0.0.1:8099/admin/updateUser";
    ajaxUtils(url , formData, findCode);
    selectUser(1);
}

function delUser(uid) {
    var formData = new FormData();
    formData.append( "uid", uid);
    var url = "http://127.0.0.1:8099/admin/delUser";
    ajaxUtils( url ,  formData , findCode );
    selectUser(1);
}

//以下代码用来退出
function logout() {
    var url = "http://127.0.0.1:8099/logout";
    ajaxUtils(url , "" , findCode);
}

//用来添加专业下拉框的数据
function optionDataGet(bool) {
    var url = "http://127.0.0.1:8099/admin/selectCollege";
    ajaxUtils( url , null , appendOption,bool);
}
function appendOption(data) {
    for( var i =0; i < data.length; i++) {
        // console.log(data[i].cid);
        $("#ucollege").append("<option value='" + data[i].cid +"'>" + data[i].cname  + "</option>" );
    }

    //在那个函数中添加option就要在哪引入这个
    layui.use('form', function(){
        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
        form.render();
        form.render('select' ,'test'); //刷新select选择框渲染
    });

}

//用来添加班级下拉框的数据
function gradeDataByCid(cid){
    var url = "http://127.0.0.1:8099/admin/selectGradeByCid";
    // alert(url);
    var fromData = new FormData();
    fromData.append("cid",cid);
    ajaxUtils( url , fromData, appendOptionGrade);

}

function appendOptionGrade(data) {
    for( var i =0; i < data.length; i++) {
        // console.log(data[i].cid);
        $("#ugrade").append("<option value='" + data[i].gid +"'>" + data[i].gname  + "</option>" );
    }

    //在那个函数中添加option就要在哪引入这个
    layui.use('form', function(){
        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
        form.render();
        form.render('select' ,'test'); //刷新select选择框渲染
    });
}

function beforeSendEmail() {
    $("#father").empty();
    $("#father").append( '<form class="layui-form" lay-filter="example">'
        + ' <div class="layui-form-item layui-form-text">\n' +
        '    <label class="layui-form-label">请输入内容:</label>\n' +
        '    <div class="layui-input-block">\n' +
        '      <textarea placeholder="请输入内容" class="layui-textarea" name="desc" id="email"></textarea>\n' +
        '    </div>\n' +
        '  </div>\n' +
        ' \n' +
        '  <div class="layui-form-item">\n' +
        '    <div class="layui-input-block">\n' +
        '      <button class="layui-btn layui-btn-normal" onclick="sendEmail()">发送</button>' +
        '    </div>\n' +
        '  </div>\n' +
        '</form>');


    //在那个函数中添加option就要在哪引入这个
    layui.use('form', function(){
        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
        form.render();
        // form.render('select' ,'test'); //刷新select选择框渲染
    });
}


function sendEmail() {
    var formData = new FormData();
    formData.append("content",$("#email").val());
    formData.append("uaccount",'');

    var url = "http://127.0.0.1:8099/admin/sendEmail";
    ajaxUtils(url,formData,findCode);
}

function selectProgressForAdmin(pid) {
    if( pid == 0 ) {
        alert("该生尚未提交任何材料");
        return;
    }
    var formData = new FormData();
    formData.append("pid" , pid);
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