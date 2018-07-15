<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">


	<title>商品管理</title>
	<meta name="keywords" content="">
	<meta name="description" content="">
	<link rel="shortcut icon" href="favicon.ico">
	<link href="css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
	<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
	<link href="css/plugins/iCheck/custom.css" rel="stylesheet">
	<link href="css/animate.min.css" rel="stylesheet">
	<link href="css/style.min.css?v=4.0.0" rel="stylesheet">

</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<div class="ibox-tools">
							<a class="collapse-link">
								<i class="fa fa-chevron-up"></i>
							</a>
							<a class="dropdown-toggle" data-toggle="dropdown" href="table_basic.html#">
								<i class="fa fa-wrench"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						
						<div class="table-responsive">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>序号</th>
										<th>商品名</th>
										<th>价格</th>
										<th>类别</th>
										<th>数量</th>
										<th>商品描述</th>
										<th>图片</th>
										<th>出版日期</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								   <c:forEach items="${productsList }" var="pro" varStatus="count">

									<tr>
									<td>${count.count }</td>
									<td>${pro.name }</td>
									<td>${pro.price }</td>
									<td>${pro.category }</td>
									<td>${pro.pnum }</td>
									<td>${pro.description}</td>
									<td><img src = "${pro.imgurl}" height = "30"></td>
									<td>${pro.cbtime }</td>
									<td>${pro.state }</td>
										<td>
											<a href="${pageContext.request.contextPath}/showeditProduct?id=${pro.id}">
												<i class="fa fa-edit text-navy"></i>
											</a>
											<!-- ${pageContext.request.contextPath}/delProduct?id=${pro.id} -->
											<a href="#" onclick="delProduct('${pro.id}');">
												<i class="fa fa-trash-o text-navy"></i>
											</a>
										</td>
									</tr>
                               </c:forEach>
								</tbody>
							</table>
						</div>
						<a href="${pageContext.request.contextPath}/showAddProduct">
							<button type="button" class="btn btn-w-m btn-success">
								新增
							</button>
						</a>
					</div>
				</div>
			</div>

		</div>
	</div>
	<script src="js/jquery.min.js?v=2.1.4"></script>
	<script src="js/bootstrap.min.js?v=3.3.5"></script>
	<script src="js/plugins/peity/jquery.peity.min.js"></script>
	<script src="js/content.min.js?v=1.0.0"></script>
	<script src="js/plugins/iCheck/icheck.min.js"></script>
	<script src="js/demo/peity-demo.min.js"></script>
	<script type="text/javascript">
	 function delProduct(id) {
		var f =  confirm("确认删除吗");
		if(f){
			
			window.location.href  = "${pageContext.request.contextPath}/delProduct?id=" + id;
			
		}
			
		
	}
	
	</script>
	
	
</body>

</html>