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
			search(req,resp);//�����û��������û�����ϸ��Ϣ
		}
		else if(method.equals("update")){
			update(req,resp);//�޸��û���״̬���Ƿ񶳽ᣩ
		}
	}
	/**
	 * �޸��û���״̬
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//����idȥ�޸�����û�����Ϣ�޸�״̬�������޸Ľ�����͸�blockUser.jsp
		int id = StringUtil.StringToInt(req.getParameter("id"));
		//ͨ����ȡ����statusֵ�����ǽⶳ���Ƕ���
		int status = StringUtil.StringToInt(req.getParameter("status"));
//		System.out.println(status);
		int flag1= StringUtil.StringToInt(req.getParameter("flag"));//flagֵΪ0�û�����Ӷ����˺�ҳ�洫��������Ҫ���ض����˺�ҳ�棬������Ҫ��ת��list
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
			//����ǴӲ�ѯҳ����ת�����ģ���ô�������ض���list
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
			//��ȡ�����û�����ϸ��Ϣ
			UserBean userBean = userDao.search(username);
			
			req.setAttribute("userBean", userBean);
			req.getRequestDispatcher("blockUser.jsp").forward(req, resp);
		}
/**
 * �û���Ϣ��ѯ
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
