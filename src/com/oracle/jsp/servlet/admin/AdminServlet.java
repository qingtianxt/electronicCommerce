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
 * admin����
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
	 * �˳���¼
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
			req.getSession().invalidate();//ɾ��session��ʵ���˳�
			resp.sendRedirect(req.getContextPath() + "/admin/login.jsp");
		}
	}
	
	/**
	 * �鿴����Ա
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("utf-8");
		//��listҳ������û��status�ģ�����ͨ���޸ĺ�ɾ��ҳ������Ҫ������һ��statusֵ�������ж��Ƿ��޸ĺ�ɾ���ɹ�
		String status = req.getParameter("status");
		AdminDao adminDao = new AdminDao();
		
		int currentPage = StringUtil.StringToInt(req.getParameter("currentPage"));
		
		int countSize = adminDao.getCount();//��ȡ��Ҫ��ʾ����������
		
		PagingBean pagingBean = new PagingBean(currentPage, countSize, Constants.PAGE_SIZE_1);
		
		//��ȡ��ǰҳ��Ҫ������
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
	 * �޸Ĺ���Ա����ת�����ҳ�棬ͬʱ����һ��updateBean session����
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
	// //��������
	// //��ȡ��ǰ��¼�û���id���ж��ǲ��ǳ�������Ա������ǳ�������Ա����ô�����޸��κ����û��������룬�������ֻ���޸��Լ�����Ϣ
	// //�������Ҫ�����Ա�Ƚ��Ե�¼���������ܽ������֣��ڽ���ʵ����Ӫ��ʱ����˵������Ϊ�˱��ڵ��ԣ����������ַ���
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
	//�޸ĵ����̣���������ת���÷������ж��ǲ��ǳ�������Ա���ǳ�������Ա�����ز鿴�б�ҳ�棬����ʾû��Ȩ�ޣ�������ǳ�������Ա����Ҫ�����id����Ϣ�洢����������ת��
	//���ҳ�棬�������ҳ�棬�����е���Ϣ��ʾһ�£�Ȼ����Ϣ�޸ĺ���ת����ʾ��servlet��Ȼ��������ʾ
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
	 * ��¼
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
			// ��½�ɹ�
			req.getSession().setAttribute(Constants.SESSION_ADMIN_BEAN, adminBean);//���û��˻���Ϣ�洢��session��
			// req.getRequestDispatcher("main.jsp").forward(req, resp);
			resp.sendRedirect(req.getContextPath() + "/admin/main.jsp");//��ת����ҳ��
		} else {
			resp.sendRedirect(req.getContextPath() + "/admin/login.jsp?status=1");//��ת�ص�¼���棬����ʾ��¼ʧ��
		}
	}

	/**
	 * ��Ӻ��޸Ĺ���Ա
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("utf-8");
		String updateId = req.getParameter("updateId");// �����ж�����ӻ����޸ģ�updateID����ǿ�˵������ӣ��������޸�

		AdminDao adminDao = new AdminDao();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		AdminBean adminBean = new AdminBean();

		adminBean.setUsername(username);
		
		String salt = StringUtil.getRandomString(10);
		
		
		String md5Pwd = MD5.GetMD5Code(MD5.GetMD5Code(password) + salt);
		//MD5����
		adminBean.setSalt(salt);
		adminBean.setPassword(md5Pwd);
		SimpleDateFormat creatDate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		adminBean.setCreateDate(creatDate1.format(new Date()));
		//��������ӻ����޸Ķ���adminbean�������ֵ��ֻ���ڵ��õ�ʱ���������ӵ�ʱ����Ҫ��adminbean���е���Ϣ�洢�����ݿ⣬�޸ĵĻ��޸ĳ��������������Ϣ
		
		//updateId ��ʱΪ��ӣ���Ϊ��Ϊ�޸�
		 
		if (!updateId.equals("")) {
			int id = StringUtil.StringToInt(updateId);
			adminBean.setId(id);
			boolean flag = true;
			if (!(username.equals(adminDao.getById1(id).getUsername()))) {//�ж���û���û�����û���޸ģ�����޸ĵĻ����ж�����޸ĵ��û����Ƿ���ڣ�
				//���ڵĻ��Ͱ��޸�ʧ�ܴ�����������ڿ����޸ģ���Ϊidֵ��һ���ġ�
				flag = adminDao.checkReg(username);
			}
			
			if (flag) {
				
				adminDao.update(adminBean);
				// �޸ĳɹ���д�����ݿ�
				resp.sendRedirect("adminServlet?method=list&status=2");
			} else {
				
				// �޸�ʧ�ܣ�����
				resp.sendRedirect("addUser.jsp?status=2");
			}
		} else {
			boolean flag = adminDao.checkReg(username);
			if (flag) {
				// ע��ɹ�
				adminDao.save(adminBean);
				resp.sendRedirect("addUser.jsp?status=1");
			} else {
				// ע��ʧ�ܣ�����
				resp.sendRedirect("addUser.jsp?status=2");
			}
		}
	}

	/**
	 * ɾ������Ա
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
			// ��ͨ�û�������ɾ��
			AdminDao adminDao = new AdminDao();
			adminDao.delete(id);
			resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=3");
		} else if (id == 1) {
			// idΪ�����û�,����ɾ��
			resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=1");
		} else {
			// id���Ϸ�
			resp.sendRedirect(req.getContextPath() + "/admin/adminServlet?method=list&status=5");
		}
	}
}
