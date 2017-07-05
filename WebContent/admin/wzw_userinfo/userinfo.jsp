<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!--导入css-->
<script
	src="${pageContext.request.contextPath}/static/js/jquery-1.12.1.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
<script
	src="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<!-- validate验证 -->
<script src="${pageContext.request.contextPath}/static/js/myValidate.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/static/js/jquery.validate.js"
	type="text/javascript"></script>
<style>
body {
	margin: 0px;
}

.touxiang {
	width: 40%;
	height: 20%;
	float: left;
	margin: 2%;
	padding: 10px 20px 30px;
}

.main {
	background-color: #F5F5F5;
	width: 50%;
	height: 20%;
	float: left;
	margin: 2%;
	padding: 10px 20px 30px;
}
</style>
</head>

<body>
	<div class="container">

		<div class="main">
			<table height="15%" width="50%">
				<tr>
					<td>
				<tr>
					<td>真实姓名：${userBean.truename }&nbsp;&nbsp;&nbsp; <c:if
							test="${userBean.sex==0 }">男</c:if> <c:if test="${userBean.sex==1 }">女</c:if>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>

							<button class="btn btn-default btn-sm" data-toggle="modal"
								data-target="#myModal">修改信息</button> <!-- Modal -->
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="myModalLabel">修改信息</h4>
										</div>
										<div class="modal-body">

											<form role="form" id="checkForm"
												action="${pageContext.request.contextPath}/admin/wzw_userinfo/wzw_userServlet?method=update&id=${userBean.id}"
												method="post">
												<div class="form-group">
													<label for="exampleInputEmail1">新手机号</label> <input
														type="text" class="form-control" name="account"
														id="account" placeholder="新手机号" value="${userBean.username }">
												</div>
												<div class="form-group">
													<label for="exampleInputPassword1">昵称</label> <input
														type="text" class="form-control" name="nickname"
														id="nickname" placeholder="昵称" value="${userBean.nickname }">
												</div>
												<div class="form-group">
													<label for="exampleInputPassword1">原密码</label> <input
														type="password" class="form-control" name="password"
														id="inputPassword" placeholder="原密码"
														value="${userBean.password }">
												</div>
												<div class="form-group">
													<label for="exampleInputPassword1">新密码</label> <input
														type="password" class="form-control" name="password2"
														id="password2" placeholder="新密码">
												</div>
										</div>
										<div class="modal-footer">
											<button type="reset" class="btn btn-default"
												data-dismiss="modal">关闭</button>
											<button type="submit" class="btn btn-primary">提交更改</button>
										</div>
										</form>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>
							<!-- /.modal -->
					</span></td>
				</tr>
				<tr>
					<td>手机号：${userBean.username }</td>
				</tr>
				<tr>
					<td>昵称：${userBean.nickname }</td>
				</tr>
				<tr>
					<td>当前密码：${userBean.password }</td>
				</tr>
			</table>
		</div>
		<div class="touxiang">
			<div>
				<img src="other.jpg" class="img-circle" height="30%" width="30%">
			</div>
			<div>
				<form enctype="multipart/form-data" class="form-inline" role="form"
					action="${pageContext.request.contextPath}/admin/wzw_userinfo/wzw_userServlet?method=pic&id=${userBean.id}"
					method="post">
					<div class="form-group">
						<label class="sr-only" for="exampleInputEmail2">头像</label> <input
							type="file" class="form-control" name="pic" id="pic"
							placeholder="pic">
					</div>
					<input type="hidden" name="id" value="${userBean.id }">
					<button type="submit" class="btn btn-default">提交</button>
				</form>
			</div>

		</div>
	</div>
	${param.status==1?"<div class='alert alert-danger' role='alert'>修改成功</div>":"" }
	${param.status==2?"<div class='alert alert-danger' role='alert'>修改失败</div>":"" }
	${param.status==3?"<div class='alert alert-danger' role='alert'>更改头像成功</div>":"" }
	${param.status==4?"<div class='alert alert-danger' role='alert'>更改头像失败</div>":"" }
</body>
</html>