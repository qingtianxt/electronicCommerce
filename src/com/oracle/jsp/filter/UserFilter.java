package com.oracle.jsp.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oracle.jsp.bean.UserBean;
import com.oracle.jsp.util.Constants;

public class UserFilter implements Filter {
	private Set<String> urls = new HashSet<String>();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();// 获取访问地址
		// 该过滤是处理前台登录的判断
		// 路径为front 前台所有的路径
		if (path.startsWith("/front")) {
			// 有某些地址需要放过，比如登录，注册
			if (urls.contains(path)) {
				chain.doFilter(request, response);
			} else {
				UserBean userBean = (UserBean) request.getSession().getAttribute(Constants.SESSION_USER_BEAN);
				if (userBean != null) {
					// 已登录，放过
					// 如果符合业务规则，则放过
					chain.doFilter(request, response);
				} else {
					// 没有登录，跳回登录页面
					response.sendRedirect(request.getContextPath() + "/front/user/login.jsp");
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		urls.add("/front/user/login.jsp");
		urls.add("/front/user/userServlet");
		urls.add("/front/user/add.jsp");
	}
}