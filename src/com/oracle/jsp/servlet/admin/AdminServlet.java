package com.oracle.jsp.servlet.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.jsp.bean.AdminBean;
import com.oracle.jsp.bean.PagingBean;
import com.oracle.jsp.dao.AdminDao;
import com.oracle.jsp.util.Constants;
import com.oracle.jsp.util.MD5;
import com.oracle.jsp.util.StringUtil;

/**
 * admin管理
 * 
 * @author wjxing
 *
 */
public class AdminServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if ("login".equals(method)) {
			login(req, resp);
		} else if ("addUser".equals(method)) {
			addUser(req, resp);
		} else if ("toUpdate".equals(method)) {
			toUpdate(req, resp);
		} else if ("delete".equals(method)) {
			delete(req, resp);
		} else if ("list".equals(method)) {
			listUsers(req, resp);
		} else if ("end".equals(method)) {
			end(req, resp);
		}
	}

	/**
	 * 退出登录
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void end(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String status = req.getParameter("status");
		if (status != null && "1".equals(status)) {
			req.getSession().invalidate();//删除session，实现退出
			resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
		}
	}
	
	/**
	 * 查看管理员
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("utf-8");
		//在list页面中是没有status的，但是通过修改和删除页面是需要传过来一个status值，用来判断是否修改和删除成功
		String status = req.getParameter("status");
		AdminDao adminDao = new AdminDao();
		
		int currentPage = StringUtil.StringToInt(req.getParameter("currentPage"));
		
		int countSize = adminDao.getCount();//获取需要显示的总数量。
		
		PagingBean pagingBean = new PagingBean(currentPage, countSize, Constants.PAGE_SIZE_1);
		
		//获取当前页需要的数据
		List<AdminBean> adminBeans = adminDao.getListByPage(currentPage * Constants.PAGE_SIZE_1, Constants.PAGE_SIZE_1);
		
		
		
		pagingBean.setPrefixUrl(req.getContextPath() + "/admin/adminServlet?method=list");
		pagingBean.setAnd(true);
		
		req.setAttribute(Constants.SESSION_ADMIN_BEANS, adminBeans);
		req.setAttribute("pagingBean", pagingBean);
		
		if (status != null) {
			req.getRequestDispatcher("listUsers.jsp?status=" + status).forward(req, resp);
		} else {
			req.getRequestDispatcher("listUsers.jsp").forward(req, resp);
		}

	}

	/**
	 * 修改管理员（跳转到添加页面，同时设置一个updateBean session对象）
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	// private void toUpdate(HttpServletRequest req, HttpServletResponse resp)
	// throws
	// ServletException, IOException{
	// req.setCharacterEncoding("utf-8");
	// int id = StringUtil.StringToInt(req.getParameter("id"));
	// AdminBean
	// adminBean1=(AdminBean)req.getSession().getAttribute(Constants.SESSION_ADMIN_BEAN);
	// //个人做法
	// //获取当前登录用户的id，判断是不是超级管理员，如果是超级管理员，那么可以修改任何人用户名和密码，如果不是只能修改自己的信息
	// //这个功能要求管理员先进性登录操作，才能进行区分，在进行实际运营的时候再说，现在为了便于调试，不采用这种方法
	// if(adminBean1 !=null && adminBean1.getId()==1)
	// {
	// AdminDao adminDao = new AdminDao();
	//
	// AdminBean adminBean = adminDao.getById(id);
	// req.setAttribute(Constants.SESSION_UPDATE_BEAN, adminBean);
	// System.out.println(1);
	// req.getRequestDispatcher("addUser.jsp?updateId="+id).forward(req, resp);
	// }
	// else
	// {
	// if(adminBean1 !=null && id==adminBean1.getId()){
	// AdminDao adminDao = new AdminDao();
	//
	// AdminBean adminBean = adminDao.getById(id);
	// req.setAttribute(Constants.SESSION_UPDATE_BEAN, adminBean);
	// System.out.println(1);
	// req.getRequestDispatcher("addUser.jsp?updateId="+id).forward(req, resp);
	// }
	// else{
	// resp.sendRedirect(req.getContextPath()+"/admin/adminServlet?method=list&status=4");
	// }
	// }
	//
	//
	// }
	//修改的流程，就是先跳转到该方法来判断是不是超级管理员，是超级管理员，返回查看列表页面，并提示没有权限，如果不是超级管理员，需要将这个id的信息存储起来，并跳转到
	//添加页面，并在添加页面，将所有的信息显示一下，然后将信息修改后跳转到显示的servlet，然后重新显示
	private void toUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		int id = StringUtil.StringToInt(req.getParameter("id"));
		if (id > 1) {
			AdminDao adminDao = new AdminDao();
			AdminBean adminBean = adminDao.getById(id);
			req.setAttribute("updateBean", adminBean);
			req.getRequestDispatcher("addUser.jsp").forward(req, resp);
		} else if (id == 1) {
			resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=1");
		}
	}

	/**
	 * 登录
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String username = req.getParameter("username");
		String password = MD5.GetMD5Code(req.getParameter("password"));

		AdminDao adminDao = new AdminDao();
		AdminBean adminBean = adminDao.checkLogin(username, password);
		if (adminBean != null) {
			// 登陆成功
			req.getSession().setAttribute(Constants.SESSION_ADMIN_BEAN, adminBean);//将用户账户信息存储到session中
			// req.getRequestDispatcher("main.jsp").forward(req, resp);
			resp.sendRedirect(req.getContextPath() + "/admin/main.jsp");//跳转到主页面
		} else {
			resp.sendRedirect(req.getContextPath() + "/admin/login.jsp?status=1");//跳转回登录界面，并显示登录失败
		}
	}

	/**
	 * 添加和修改管理员
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("utf-8");
		String updateId = req.getParameter("updateId");// 用来判断是添加还是修改，updateID如果是空说明是添加，不空是修改

		AdminDao adminDao = new AdminDao();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		AdminBean adminBean = new AdminBean();

		adminBean.setUsername(username);
		
		String salt = StringUtil.getRandomString(10);
		
		
		String md5Pwd = MD5.GetMD5Code(MD5.GetMD5Code(password) + salt);
		//MD5加密
		adminBean.setSalt(salt);
		adminBean.setPassword(md5Pwd);
		SimpleDateFormat creatDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		adminBean.setCreateDate(creatDate1.format(new Date()));
		//无论是添加还是修改都把adminbean里添加上值，只是在调用的时候如果是添加的时候需要把adminbean所有的信息存储到数据库，修改的话修改除日期外的所有信息
		
		//updateId 空时为添加，不为空为修改
		 
		if (!updateId.equals("")) {
			int id = StringUtil.StringToInt(updateId);
			adminBean.setId(id);
			boolean flag = true;
			if (!(username.equals(adminDao.getById1(id).getUsername()))) {//判断有没有用户名有没有修改，如果修改的话，判断这个修改的用户名是否存在，
				//存在的话就按修改失败处理，如果不存在可以修改，因为id值是一定的。
				flag = adminDao.checkReg(username);
			}
			
			if (flag) {
				
				adminDao.update(adminBean);
				// 修改成功，写入数据库
				resp.sendRedirect("adminServlet?method=list&status=2");
			} else {
				
				// 修改失败，跳回
				resp.sendRedirect("addUser.jsp?status=2");
			}
		} else {
			boolean flag = adminDao.checkReg(username);
			if (flag) {
				// 注册成功
				adminDao.save(adminBean);
				resp.sendRedirect("addUser.jsp?status=1");
			} else {
				// 注册失败，跳回
				resp.sendRedirect("addUser.jsp?status=2");
			}
		}
	}

	/**
	 * 删除管理员
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		int id = StringUtil.StringToInt(req.getParameter("id"));
		if (id > 1) {
			// 普通用户，可以删除
			AdminDao adminDao = new AdminDao();
			adminDao.delete(id);
			resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=3");
		} else if (id == 1) {
			// id为超级用户,不能删除
			resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=1");
		} else {
			// id不合法
			resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=5");
		}
	}
}
