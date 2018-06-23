<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>

<link href="${pageContext.request.contextPath}/css/sys.css"
	type="text/css" rel="stylesheet" />
 <script type="text/javascript">
       window.onload=function() {
    	   //页面初始完成后立刻加载
    	   //发起请求,加载数据获得json
    	   //ajax异步请求
    	   //解析json
    	   //展示数据
    	   var xmlhttp;
    	   if (window.XMLHttpRequest)
    	   {// code for IE7+, Firefox, Chrome, Opera, Safari
    	   xmlhttp=new XMLHttpRequest();
    	   }
    	   else
    	   {// code for IE6, IE5
    	   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    	   }
    	   //打开链接 状态为1
    	   xmlhttp.open("GET","http://localhost:8080/CRM/dep/selectAllDepJson");
    	   //设置监听
    	   xmlhttp.onreadystatechange= function(data){
    		   //如果状态是4 且http状态是200
    		   if(xmlhttp.readState == 4 && xmlhttp.status == 200){
    			   var jsontext= xmlhttp.responseText;
    			   //转换为下拉框
    			   //转化json
    			   var json = JSON.parse(jsontext);
    			   //遍历数组
    			  /*  for(var js : json) {
    				   
    			   } */
    			 var select = document.getElementById("depid");
    			  // var option = $("<option value=""> </option>");
    			   for(var i = 0 ;i<json.length;i++){
    				   var currentJson = json[i];
    				 
    				   var p = document.createElement("p");
    				   p.innerHtml(json[i]);
    				   select.append(p);
    				//   $("#depid").append(option.value().html());
    				 //   var option = document.createElement("option");
    				 //   option.value=josn[i].depId;
    				 //   option.value=josn[i].depName;
    				    
    				    
    			   }
    		   }
    	   }
    	   xmlhttp.send();  //发送内容
    	   
       }
    </script> 
</head>

<body>

<div id="depid"> </div>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td class="topg"></td>
		</tr>
	</table>

	<table border="0" cellspacing="0" cellpadding="0" class="wukuang"
		width="100%">
		<tr>
			<td width="1%"><img
				src="${pageContext.request.contextPath}/images/tleft.gif" /></td>
			<td width="39%" align="left">[部门管理]</td>

			<td width="57%" align="right">
				<%--添加部门 --%> <a
				href="${pageContext.request.contextPath}/dep/addDep/null/null"> <img
					src="${pageContext.request.contextPath}/images/button/tianjia.gif" />
			</a>

			</td>
			<td width="3%" align="right"><img
				src="${pageContext.request.contextPath}/images/tright.gif" /></td>

		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0"
		style="margin-top: 5px;">
		<tr>
			<td><img
				src="${pageContext.request.contextPath}/images/result.gif" /></td>
		</tr>
	</table>

	<table width="100%" border="1">

		<tr class="henglan" style="font-weight: bold;">
			<td width="6%" align="center">部门名称</td>
			<td width="7%" align="center">编辑</td>
			<td width="7%" align="center">删除</td>
		</tr>
		<c:forEach varStatus="vs" var="dep" items="${listDep}">
			<tr class="${vs.index%2==0?'tabtd1':'tabtd2'}">
				<td align="center">${dep.depName}</td>
				<td width="7%" align="center"><a
					href="${pageContext.request.contextPath}/dep/addDep/${dep.depId}/${dep.depName}"><img
						src="${pageContext.request.contextPath}/images/button/modify.gif"
						class="img" /></a></td>

				<td width="7%"  align="center"><a href="${pageContext.request.contextPath}/dep/deleteThis/${dep.depId}">delete</a></td>
		</c:forEach>
		<%-- <tr class="tabtd1">
			<td align="center">java</td>
			<td width="7%" align="center"><a
				href="${pageContext.request.contextPath}/pages/department/addOrEditDepartment.jsp"><img
					src="${pageContext.request.contextPath}/images/button/modify.gif"
					class="img" /></a></td>
		</tr>

		<tr class="tabtd2">
			<td align="center">咨询部</td>
			<td width="7%" align="center"><a
				href="${pageContext.request.contextPath}/pages/department/addOrEditDepartment.jsp"><img
					src="${pageContext.request.contextPath}/images/button/modify.gif"
					class="img" /></a></td>
		</tr> --%>

	</table>



	<table border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td align="right"><span>第1/3页</span> <span> <a href="#">[首页]</a>&nbsp;&nbsp;
					<a href="#">[上一页]</a>&nbsp;&nbsp; <a href="#">[下一页]</a>&nbsp;&nbsp;
					<a href="#">[尾页]</a>
			</span></td>
		</tr>
	</table>
</body>
</html>
