function loadWorkPlace(id) {
    //页面初始化完成后立刻加载
    //发起请求,加载数据获得 json
    //ajax 异步请求
    //解析 json
    //展示数据
    var xhr;
    if (window.XMLHttpRequest) {
        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xhr = new XMLHttpRequest();
    } else {
        // IE6, IE5 浏览器执行代码
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    //打开连接
    xhr.open("GET","http://localhost:8080/admin/selectWorkPlace");
    //设置监听
    xhr.onreadystatechange=function(){
        //如果状态是4并且 http 状态是200

        if(xhr.readyState==4&&xhr.status==200){
            var jsontext=xhr.responseText;
            //alert(jsontext);
            //转换,转成我们的下拉框
            //转换 json
            var json=JSON.parse(jsontext);//部门的 json 集合
            alert(json);
            var select = document.getElementById(id);
            for(var i =0;i<json.length;i++){
                var currentJson=json[i];//获取每一条
                //生成 option 对象,然后把内容设置上去
                var option=document.createElement("option");
                option.value=currentJson.wname;
                option.innerHTML=currentJson.wname;

                /*
                if("a9fba304d4154aa7adaa2eb22026fe4f"==currentJson.depId){
                //添加了一条部门数据,如果当前添加的部门的信息和你当前修改的职务的部门的 id 一样,代表它应该是选中的
                    option.setAttribute("selected","selected");
                }
                */
                //添加到我们的 select 中
                select.append(option);
            }

        }
    }

    xhr.send();
    //发送数据
}