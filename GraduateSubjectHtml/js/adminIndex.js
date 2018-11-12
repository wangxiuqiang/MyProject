//
function selectSubject() {
    var url = "http://127.0.0.1:8099/admin/selectAllSubject/1";
    var formDate = new FormData();
    formDate.append( "uaccount" , $("#uaccount").val() );
    formDate.append( "upwd", $("#upwd").val() );
    $("#father").empty();
//        alert( page);
    $("#father").append("   <div class=\"layui-form\">\n" +
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
        "                        <th>操作</th>\n" +
        "                    </tr>\n" +
        "                    </thead>\n" +
        "                    <tbody id=\"tbody\">\n" +
        "                    <tr>\n" +
        "                        <td>贤心</td>\n" +
        "                        <td>汉族</td>\n" +
        "                        <td>1989-10-14</td>\n" +
        "\n" +"<td> <a href=''>修改</a> |  <a href=''>删除</a> </td>" +
        "                    </tr>\n" +
        "                    </tbody>\n" +
        "                </table>\n" +
        "            </div>");

    // $("#father").append("123");

    //请求url 返回数据
    ajaxUtils(url , formDate, ajaxback , null);
    function ajaxback(data) {
        var i = 2;
        $("#tbody").append( "<tr>\n" + "<td>" +  i  + "</td>\n" +
        "                        <td>"+ data.statuscode + "</td>\n" +
        "                        <td>"+ data.statuscode + "</td>\n" +
        "\n" + "<td> <a href='javascript:void(0)' onclick='UpdateSubject("+
            data.statuscode + "," + data.statuscode +"," + data.statuscode
            +")' style='color: blue;'>修改</a> |  <a href='javascript:void(0)' onclick='delSubject("+ data.statuscode +")'" +
            " style='color: red;'>删除</a></td>"+
        "                    </tr>");
    }
    $("#father").append("<div id=\"demo1\" style=\"left:50%; margin-left: -200px; position: absolute; \"></div>");
    //用来获取当前页数的
    var page;
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
            , count: 100 //数据总数
            , jump: function (obj) {
                //获取到当前的页数
                page = obj.curr;
            }
        });
    });

}
//跳转到添加题目信息的页面
function addSubject() {
    var url = "http://39.106.191.144/addSubject";
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
    ajaxUtils( "http://39.106.191.144/addSubject" ,formDate , findCode);
}
//弹窗提示,然后删除,
function delSubject(id) {
    var result = confirm( "确认删除" );
    // alert( result );
    var formData = new FormData();
    formData.append( "sid" , id );
    if( result === true ) {
        ajaxUtils( "http://39.106.191.144/delSubject" ,formData , findCode )
    }
}

//更新一个题目,更新的时候跳转到add的页面,但是里面有值
function UpdateSubject(sname, sintroduce,sid) {
    var url = "http://39.106.191.144/updateSubject";
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
    ajaxUtils( "http://39.106.191.144/updateSubject" , formData , findCode);
}

//下面是对用户的操作

//添加一个用户
function addUser() {

    var url = "http://39.106.191.144/addUesr";
    //清除页面元素 .empty 是只清除子元素,remove是清除自身和子元素,,demo1是一个分页的东西,,暂时先让他全部页面显示
    $("#father").empty();
    $("#demo1").remove();
    //添加文本框
    $("#father").append(" <h2>添加选题内容:</h2>" +
        "<div class=\"layui-form-item\" style=\"margin-top:29px; margin-left: 28px;\">\n" +
        "        <label class=\"layui-form-label\">姓名:</label>\n" +
        "        <div class=\"layui-input-inline\">\n" +
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
        "      <select name=\"ucollege\">\n" +
        "        <option value=\"\">请选择专业</option>\n" +
        "        <option value=\"浙江\" selected=\"\">浙江省</option>\n" +
        "        <option value=\"你的工号\">江西省</option>\n" +
        "        <option value=\"你最喜欢的老师\">福建省</option>\n" +
        "      </select>\n" +
        "    </div>\n" +
        "    <div class=\"layui-input-inline\">\n" +
        "      <select name=\"ugrade\" >\n" +
        "        <option value=\"\">请选择班级</option>\n" +
        "        <option value=\"杭州\">杭州</option>\n" +
        "        <option value=\"宁波\" disabled=\"\">宁波</option>\n" +
        "        <option value=\"温州\">温州</option>\n" +
        "        <option value=\"温州\">台州</option>\n" +
        "        <option value=\"温州\">绍兴</option>\n" +
        "      </select>\n" +
        "    </div>\n" +
        "</div>"  +
        " <div class=\"layui-form-item\">\n" +
        "    <label class=\"layui-form-label\">性别:</label>\n" +
        "    <div class=\"layui-input-block\">\n" +
        "      <input type=\"radio\" name=\"usex\" value=\"男\" title=\"男\" checked=\"\">\n" +
        "      <input type=\"radio\" name=\"usex\" value=\"女\" title=\"女\">\n" +
        "    </div>\n" +
        "  </div>" +
        "</form>");
    //添加按钮
    $("#father").append("<div style=\"margin-left: 120px;\">\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\" onclick=\"addUserFinish()\">添  加</button>\n" +
        "        <button type=\"button\" class=\"layui-btn layui-btn-primary layui-btn-radius\">重  置</button>\n" +
        "    </div>");

    layui.use('form', function(){
        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
       form.render();
        form.render('select' ,'test'); //刷新select选择框渲染
    });
}
//提交表单
function addUserFinish() {
    var formData = new FormData();
    formData.append( "uaccount" , $("#uaccount").val() );
    formData.append( "uaccount" , $("#uname").val() );
    formData.append( "uaccount" , $("#uage").val() );
    formData.append( "uaccount" , $("#ucollege").val() );
    formData.append( "uaccount" , $("#ugrade").val() );
    formData.append( "uaccount" , $("#usex").val() );

    var url = "";
    ajaxUtils( url , formData , findCode);
}

//查询学生
function selectUser() {
    var url = "http://39.106.191.144:8180/";
    var formDate = new FormData();
    formDate.append( "uaccount" , $("#uaccount").val() );
    formDate.append( "upwd", $("#upwd").val() );
    $("#father").empty();
//        alert( page);
    $("#father").append("   <div class=\"layui-form\">\n" +
        "                <table class=\"layui-table\">\n" +
        "                    <colgroup>\n" +
        "                        <col width=\"50\">\n" +
        "                        <col width=\"120\">\n" +
        "                        <col width=\"120\">\n" +
        "                        <col width='120'>\n" +
        "                        <col width=\"120\">\n" +
        "                        <col width=\"120\">\n" +
        "                        <col width=\"120\">\n" +
        "                        <col width='80'>\<n></n>" +
        "                    </colgroup>\n" +
        "                    <thead>\n" +
        "                    <tr>\n" +
        "                        <th>序号</th>\n" +
        "                        <th>选题题目</th>\n" +
        "                        <!--<th></th>-->\n" +
        "                        <th>选题介绍</th>\n" +
        "                        <th>操作</th>\n" +
        "                        <th>序号</th>\n" +
        "                        <th>选题题目</th>\n" +
        "                        <!--<th></th>-->\n" +
        "                        <th>选题介绍</th>\n" +
        "                        <th>操作</th>\n" +
        "                    </tr>\n" +
        "                    </thead>\n" +
        "                    <tbody id=\"tbody\">\n" +
        "                    <tr>\n" +
        "                        <td>贤心</td>\n" +
        "                        <td>汉族</td>\n" +
        "                        <td>1989-10-14</td>\n" +
        "\n" +"<td> <a href=''>修改</a> |  <a href=''>删除</a> </td>" +

        "                        <td>贤心</td>\n" +
        "                        <td>汉族</td>\n" +
        "                        <td>1989-10-14</td>\n" +
        "\n" +"<td> <a href=''>修改</a> |  <a href=''>删除</a> </td>" +
        "                    </tr>\n" +
        "                    </tbody>\n" +
        "                </table>\n" +
        "            </div>");

    // $("#father").append("123");

    //请求url 返回数据
    ajaxUtils(url , formDate, userfinish );
    function userfinish(data) {
        var i = 2;
        $("#tbody").append( "<tr>\n" + "<td>" +  i  + "</td>\n" +
            "                        <td>"+ data.statuscode + "</td>\n" +
            "                        <td>"+ data.statuscode + "</td>\n" +
            "\n" + "<td> <a href='javascript:void(0)' onclick='UpdateUser("+
            data.statuscode + "," + data.statuscode +"," + data.statuscode
            +")' style='color: blue;'>修改</a> |  <a href='javascript:void(0)' onclick='delUser("+ data.statuscode +")'" +
            " style='color: red;'>删除</a></td>"+
            "                    </tr>");
    }
    $("#father").append("<div id=\"demo1\" style=\"left:50%; margin-left: -200px; position: absolute; \"></div>");
    //用来获取当前页数的
    var page;
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
            , count: 100 //数据总数
            , jump: function (obj) {
                //获取到当前的页数
                page = obj.curr;
            }
        });
    });


}

function UpdateUser() {
    
}
function delUser() {
    
}

//以下代码用来退出
function logout() {
    var url = "http://39.106.191.144/logout";

    ajaxUtils(url , "" , findCode);
}