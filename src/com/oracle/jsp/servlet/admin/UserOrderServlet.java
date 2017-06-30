package com.oracle.jsp.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.jsp.bean.UserOrderBean;
import com.oracle.jsp.dao.OrderDao;

/**
 * Servlet implementation class UserOrderServlet
 */
public class UserOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String method = req.getParameter("method");
		if(method.equals("list")){
			list(req,resp);//根据用户名返回一个人所有的订单信息
		}else if(method.equals("delete")){
			delete(req,resp);//删除订单信息
		}
	}
	/**
	 * 删除订单信息
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String orderId = req.getParameter("orderId");
		String username = req.getParameter("username");
		OrderDao orderDao = new OrderDao();
		boolean flag=orderDao.deletebyId(orderId);
		
		if(flag==true){
//			req.getRequestDispatcher("userOrder.jsp?status=1").forward(req, resp);
			resp.sendRedirect(req.getContextPath()+"/admin/frontUser/userOrderServlet?method=list&status=1&search="+username); 
		}
		else{
//			req.getRequestDispatcher("userOrder.jsp?status=0").forward(req, resp);
			resp.sendRedirect(req.getContextPath()+"/admin/frontUser/userOrderServlet?method=list&status=0&search="+username); 
		}
	}
/**
 * 根据用户名返回一个人所有的订单信息
 * @param req
 * @param resp
 * @throws IOException 
 * @throws ServletException 
 */
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("search");
		String status = req.getParameter("status");
//		System.out.println(username);
		OrderDao orderDao = new OrderDao();
		List<UserOrderBean>list=orderDao.search(username);
//		System.out.println(list.get(1).toString());
		req.setAttribute("orderBeans", list);
		req.getRequestDispatcher("userOrder.jsp?status="+status).forward(req, resp);
	}
	
	
	
}
