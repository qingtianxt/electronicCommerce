<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的京东</title>

	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery-1.12.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/ui-base.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/common.css">
	
</head>
<body>
	<div id="container">
		<input id="serverTime" type="hidden" value="1499182465">
		<div class="w">
			<div id="content">
				<div id="main">
					<div class="lyt-c-0">
					<c:if test="${user!=null}">
						<div id="user-info" class="user-info">
							<div class="info-lcol">
								<div class="u-pic">
									<img  src="${user.pic }">
									<a id="accountImg" href="">
									<div class="mask"></div>
									</a>
								</div>
								<div class="info-m">
									<div class="u-name">
										<a href="" target="_blank">${user.nickname}</a>
									</div>
									
									<div class="info-line">
										<span class="top-icon"></span> 
										<span class="bottom-icon"></span>
										<span class="left-icon"></span>
									</div>
									<div class="clr mb10"></div>
									<div class="u-medal">
										<a href="" type="button" data-toggle="modal" data-target="#myModal">修改头像</a>
									</div>

								</div>
							</div>
							<div class="info-rcol">
								<div class="acco-info">
									<ul>
										<li class="fore3">
											<div class="acco-item">
												<div>
													<label>真实姓名：${user.truename}</label>
												</div>
												<div>
													<label>手机号：<a><em>${user.username}</em></a></label>
												</div>
												<div>
													<label>昵称：${user.nickname}</label>
												</div>
												
												<div>
													<label>性别：${user.sex}</label>
												</div>
												<div>
													<label>密码：</label> 
												</div>
											</div>
										</li>
										
										<li class="fore1 ">
											<div class="acco-item ">
												
											</div>
										</li>
										<li class="fore2 ">
											<div class="acco-item ">
												<div id="jinku-info " class="jinku-info ">
													<div id="xjk0 " class="mb ">
														<a type="button" data-toggle="modal" data-target="#myModal" class="alink" href="">修改信息</a>
													</div>
													
													<!-- Modal -->
													<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
													  <div class="modal-dialog" role="document">
													    <div class="modal-content">
													      <div class="modal-header">
													        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
													        <h4 class="modal-title" id="myModalLabel">修改个人信息</h4>
													      </div>
													      <div class="modal-body">
													        <form class="form-horizontal">
															  <div class="form-group">
															    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
															    <div class="col-sm-10">
															      <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
															    </div>
															  </div>
															  <div class="form-group">
															    <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
															    <div class="col-sm-10">
															      <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
															    </div>
															  </div>
															  <div class="form-group">
															    <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
															    <div class="col-sm-10">
															      <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
															    </div>
															  </div>
															  <div class="form-group">
															    <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
															    <div class="col-sm-10">
															      <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
															    </div>
															  </div>
							
															</form>
													      </div>
													      <div class="modal-footer">
													        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
													        <button type="button" class="btn btn-primary">保存</button>
													      </div>
													    </div>
													  </div>
													</div>										
												</div>
											</div>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${user==null}">
						您还未登录！
					
					</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>