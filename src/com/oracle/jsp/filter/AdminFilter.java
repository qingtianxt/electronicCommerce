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

import com.oracle.jsp.bean.AdminBean;
import com.oracle.jsp.util.Constants;

/**
 * Servlet Filter implementation class adminFilter
 */
public class AdminFilter implements Filter {

	private Set<String> urls = new HashSet<String>();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		// 获取访问地址
		// 该过滤是处理后台管理员登录的判断
		if (path.startsWith("/admin")) {
			// 有某些地址需要放过，比如登录，注册
			if (urls.contains(path)) {
				chain.doFilter(request, response);
			} else {
				AdminBean adminBean = (AdminBean) request.getSession().getAttribute(Constants.SESSION_ADMIN_BEAN);
				if (adminBean != null) {
					// 已登录，放过
					// 如果符合业务规则，则放过
					chain.doFilter(request, response);
				} else {
					// 没有登录，跳回登录页面
					response.sendRedirect(request.getContextPath() + "/admin/login.jsp");
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		urls.add("/admin/login.jsp");
		urls.add("/admin/adminServlet");
		urls.add("/admin/addUser.jsp");
	}
}
