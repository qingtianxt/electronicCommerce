package com.oracle.jsp.servlet.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.jsp.bean.UserBean;
import com.oracle.jsp.dao.UserDao;
import com.oracle.jsp.util.StringUtil;

/**
 * Servlet implementation class userServlet
 */
public class userServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if(method.equals("list")){
			list(req,resp);
		}
		else if(method.equals("search")){
			search(req,resp);//根据用户名查找用户的详细信息
		}
		else if(method.equals("update")){
			update(req,resp);//修改用户的状态（是否冻结）
		}
	}
	/**
	 * 修改用户的状态
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//根据id去修改这个用户的信息修改状态，并将修改结果发送给blockUser.jsp
		int id = StringUtil.StringToInt(req.getParameter("id"));
		//通过获取到的status值决定是解冻还是冻结
		int status = StringUtil.StringToInt(req.getParameter("status"));
//		System.out.println(status);
		int flag1= StringUtil.StringToInt(req.getParameter("flag"));//flag值为0得话代表从冻结账号页面传过来，需要传回冻结账号页面，否则需要跳转到list
		System.out.println(flag1);
		UserDao userDao = new UserDao();
		boolean flag = userDao.updateStatus(status,id);
		
		if(flag1==0)
		{
			if(flag==true){
				req.getRequestDispatcher("blockUser.jsp?status=1").forward(req, resp);
			}
			else{
				req.getRequestDispatcher("blockUser.jsp?status=0").forward(req, resp);
			}
		}
		else{
			//如果是从查询页面跳转过来的，那么他会先重定向到list
			if(flag==true){
				resp.sendRedirect("frontUser/userServlet?method=list&status=1");
//				req.getRequestDispatcher("listUsers.jsp?status=1").forward(req, resp);
			}
			else{
//				req.getRequestDispatcher("listUsers.jsp?status=0").forward(req, resp);
				resp.sendRedirect("frontUser/userServlet?method=list&status=0");

			}
		}
	}
	private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String username = req.getParameter("search");
			UserDao userDao = new UserDao();
			//获取到该用户的详细信息
			UserBean userBean = userDao.search(username);
			
			req.setAttribute("userBean", userBean);
			req.getRequestDispatcher("blockUser.jsp").forward(req, resp);
		}
/**
 * 用户信息查询
 * @param req
 * @param resp
 * @throws IOException 
 * @throws ServletException 
 */
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String status = req.getParameter("status");
		 UserDao userDao = new UserDao();
		 List<UserBean> userBeans = userDao.list();
		req.setAttribute("userBeans", userBeans);
		System.out.println(status);
		if(status!=null){
			req.getRequestDispatcher("listUsers.jsp?status="+status).forward(req, resp);
		}else{
			req.getRequestDispatcher("listUsers.jsp").forward(req, resp);
		}
	}
	
}
