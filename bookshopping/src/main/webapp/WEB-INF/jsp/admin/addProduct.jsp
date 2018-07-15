<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>商品发布</title>
<meta name="keywords" content="">
<meta name="description" content="">
<link rel="shortcut icon" href="favicon.ico">
<link href="css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="css/animate.min.css" rel="stylesheet">
<link href="css/style.min.css?v=4.0.0" rel="stylesheet">
<script src="js/jquery.min.js?v=2.1.4"></script>

<script src="js/content.min.js?v=1.0.0"></script>
<script src="js/plugins/iCheck/icheck.min.js"></script>
<script src="js/plugins/validate/jquery.validate.min.js"></script>
<script src="js/plugins/validate/messages_zh.min.js"></script>
<script src="js/plugins/date/WdatePicker.js"></script>
<script src="js/plugins/layer/layer.js"></script>
<script type="text/javascript" src="plugins/ckeditor/ckeditor.js"></script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							商品管理 <small>商品发布</small>
						</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<form method="post" class="form-horizontal" id="addGoodsForm"
							action="${pageContext.request.contextPath }/saveProduct"
							enctype="multipart/form-data" onsubmit="return checkSelect();">
							<!-- 商品名称输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品名称</label>
								<div class="col-sm-4">
									<input id="goodsName" name="name" type="text" value=""
										class="form-control"><span id="goodsName_span"></span> <br />
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<!-- 商品类型下拉框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品类型</label>
								<div class="col-sm-4">
									<select class="form-control m-b" name="category"
										id="selCategory">
										<option value="type">--请选择类别--</option>
										<option value="novel">小说</option>
										<option value="cartoon">童书</option>
										<option value="study">学习考试</option>
										<option value="literature">文学</option>
										<option value="music">音乐</option>
										<option value="art">艺术</option>
										<option value="science">科技</option>
									</select>
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<!-- 商品价格输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品价格</label>
								<div class="col-sm-4">
									<input name="price" type="text" value="" class="form-control" required="required">
								</div>
							</div>
							<div class="hr-line-dashed"></div>


							<!-- 商品数量输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品数量</label>
								<div class="col-sm-4">
									<input name="pnum" type="text" value="" class="form-control" required="required">
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<!-- 商品数量输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">出版时间</label>
								<div class="col-sm-4">
									<input name="cbtime" type="date" value="" class="form-control">
								</div>
							</div>
							<div class="hr-line-dashed"></div>

							<!-- 商品数量输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品图片</label>
								<div class="col-sm-4">
									<input name="imgpic" type="file" value="" class="form-control"
										onchange="checking(this)" required="required">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							
							<!-- 商品详情输入框 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">商品详情</label>
								<div class="col-sm-4">
									<textarea name="description" id="goodsDesc"
										class="form-control"></textarea>
								</div>
							</div>
							
							<div class="hr-line-dashed"></div>
							
							

							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">保存</button>
									<button class="btn btn-white" type="reset">重置</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
// 	function checkgoods() {
		
// 		var f = false
// 	     var name = document.getElementById("goodsName").value;
// 		//去除字符两端空格
// 		name = name.trim();
// 		//判断是否为空
// 		if(name == ""){
// 			name.getElementById("goodsName_span").innerHTML = "商品名不能为空";
// 			f = false;
// 		}

	//	else if(name.length < 6 || name.length > 16){
			
// 			document.getElementById("username_span").innerHTML = "商品名的长度为6到16位";
// 		}
// 		else{
// 			document.getElementById("goodsName_span").innerHTML = "";
// 			//当用户名存在时  校验用户名是否存在
// 			//使用ajax异步校验用户名
// 			$.ajax({
// 				url:"/bookshopping/checkgoods",
// 				type:"POST",
// 				data:{"goodsName":name},
// 				dataType:"json",
// 				async:false,
// 				success:function(data){
					
// 					if(data.msg == "false"){
// 						document.getElementById("username_span").innerHTML = "商品已存在";
// 						f = false;
// 					}else{
// 						document.getElementById("username_span").innerHTML = "";
// 						f = true;
// 				}
// 				}
// 			});
// 		}
// 		return f;
		
// 	}
	
	
	
		function checkSelect() {
			var show = document.getElementById("selCategory").value;
			//alert("请选择商品类型");
			if (show == "type") {
				return false;
			}
			return true;

		}

		function checking(obj) {
			//获取到上传文件的路径
			var filename = obj.value;
			//只能上传.jpg,.jpeg,.png,.gif
			var index = filename.lastIndexOf(".");
			var name = filename.substring(filename.lastIndexOf("."));

			if (name != ".jpg" && name != ".png" && name != ".gif"
					&& name != ".jpeg") {
				alert("只能上传.jpg,.jpeg,.png,.gif");
				obj.value = "";
			}

		}
	</script>



</body>

</html>