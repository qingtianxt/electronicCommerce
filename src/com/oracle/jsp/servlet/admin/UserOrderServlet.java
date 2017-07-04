package com.oracle.jsp.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.jsp.bean.OrderBean;
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
//			list(req,resp);//�����û�������һ�������еĶ�����Ϣ
		}else if(method.equals("delete")){
			delete(req,resp);//ɾ��������Ϣ
		}else if(method.equals("del")){
			delbycode(req,resp);
		}
	}
	/**
	 * ���ݶ������ɾ������
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void delbycode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		OrderDao orderDao = new OrderDao();
		boolean flag = orderDao.delbyCode(code);
		if(flag==true){
			req.getRequestDispatcher("deleteOrder.jsp?status=1").forward(req, resp);
		}else{
			req.getRequestDispatcher("deleteOrder.jsp?status=2").forward(req, resp);
		}
	}
	/**
	 * ɾ��������Ϣ
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String orderId = req.getParameter("orderId");
		String username = req.getParameter("username");
		OrderDao orderDao = new OrderDao();
		boolean flag=orderDao.deleteById(orderId);
		
		if(flag==true){
//			req.getRequestDispatcher("userOrder.jsp?status=1").forward(req, resp);
			resp.sendRedirect(req.getContextPath()+"/admin/frontUser/userOrderServlet?method=list&status=1&search="+username); 
		}
		else{
//			req.getRequestDispatcher("userOrder.jsp?status=0").forward(req, resp);
			resp.sendRedirect(req.getContextPath()+"/admin/frontUser/userOrderServlet?method=list&status=0&search="+username); 
		}
	}

	
}
