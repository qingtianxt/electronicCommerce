<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>删除订单页面</title>

<script
	src="${pageContext.request.contextPath}/static/js/jquery-1.12.1.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/js/bootstrap.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.css" />
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-md-4">
				<h1>订单编号</h1>
			</div>
			<div class="col-md-6">
				<form class="navbar-form navbar-left" role="search"
					action="${pageContext.request.contextPath}/admin/frontUser/userOrderServlet?method=del"
					method="post">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="订单编号"
							name="code">
					</div>
					<button type="submit" class="btn btn-primery">提交</button>
				</form>
			</div>
		</div>
			
	</div>
	${param.status==1?"<div class='alert alert-danger' role='alert'>删除成功</div>":"" }
	${param.status==2?"<div class='alert alert-danger' role='alert'>订单号不存在</div>":"" }
</body>
</html>