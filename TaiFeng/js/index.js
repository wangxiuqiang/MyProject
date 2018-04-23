window.onload = function () {

    // 取得li 标签元素
      var buttons = document.getElementById("buttons").getElementsByTagName("li");

        //取得存放image的div
      var turn = document.getElementById("turn");

      //      取得最外层的div
    var turnPicture = document.getElementById("turnPicture");

    // 获取右边的按钮
    var right = document.getElementById("arrow-r");

    //获取左边的按钮
    var left = document.getElementById("arrow-l");

    // 定义一个变量用来 ，确定图片是不是移动完毕
    var isFinish = false;

    // 让图片转换的函数  num 表示移动的位移 ，当单个点击的时候
    function  turnGO(num) {
        isFinish = true ; //开始移动，设置为true，
        // 运动后的div turn的left值
        var newLeft = parseInt(turn.style.left) + num;
        var time = 300 ;// 移动的时间总数 ，移动一张图片用的时间
        var interval = 10 ; //移动的时间间隔 递进的移动方式
        var speed = num /(time/interval) ; //移动的速度，，通过上面定义的两个参数决定
        //移动的函数，，赋值给go
        var go = function () {
            //第一个条件的意思是往右移 ，右移的话，，left的值就得变小，所以新left小于
            //现在的left  ，传过来的参数为负的，所以speed < 0 ,反之往左移
            if((speed < 0 && parseInt(turn.style.left) > newLeft) ||
                (speed > 0 && parseInt(turn.style.left )< newLeft) ) {
                //让这个left值改变
                turn.style.left = parseInt(turn.style.left) + speed + "px";
                //一共改变10次
                setTimeout(go,interval);
            }else {
                if(newLeft > -800) {
                    turn.style.left = -3200 + "px";
                }
                // 如果图片到达第一张的备用图片，就返回去 第一张那个的left
                if(newLeft < -3200) {
                    turn.style.left = -800 + "px";
                }
                isFinish = false ; //移动结束，，设置为false
            }
        }
        // 执行go方法
        go();
    }

    function showButtons() {
        for(var i = 0; i< buttons.length ; i++) {
            if(buttons[i].className == "alive") {
                buttons[i].className = "";
                break;
            }
        }
        buttons[index - 1].className = "alive"
    }

   // 点击右边或左边按钮的时候 ， 进行的动作
    var index = 1;
    right.onclick = function () {
        if(isFinish) {return;} //如果是true  ，表示没有移动完，下面代码不执行

        //index用来控制小圆点的变色
        if(index == 3){
            index = 1;
        }else {
            index += 1;
        }
        turnGO(-800);
        showButtons();
    }
    left.onclick = function () {
        if(isFinish) {return;} //如果是true  ，表示没有移动完，下面代码不执行
        if(index == 1){
            index = 3;
        }else {
            index -= 1;
        }
        turnGO(800);
        showButtons();
    }
    var timer; //定时器，，用来在鼠标移动上去的时候，，停止自己变化
    function play() {
        timer = setInterval(function () {
             right.onclick();
        } , 3000);
    }
    //停止自动的函数
    function stop() {
        clearInterval(timer);
    }

    //设置一个点击小圆点进行切换
    for(var i = 0; i < buttons.length; i++){
        buttons[i].onclick = function () {
            if(isFinish) {return;}
            if(this.className == "alive"){return;}
            // this 表示onclick 的这个小圆点 ，getAttribute获取一个自定义的index
            var myIndex = parseInt(this.getAttribute('index')); // 要去的位置
             //确定 要到的地方和现在的距离
            var num = (-800) * (myIndex - index ) ;
            turnGO(num);
            index = myIndex; //将变换后的位置赋值给下面的函数用到的变量index
            showButtons();
        }
    }
    turnPicture.onmouseout = play; //当鼠标移动开最外面的父盒子就开始自动
    turnPicture.onmouseover = stop; //当鼠标移动到 父盒子上就停止自动
    play();
}