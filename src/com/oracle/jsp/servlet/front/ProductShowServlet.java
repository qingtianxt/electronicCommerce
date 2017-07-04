package com.oracle.jsp.servlet.front;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.jsp.bean.ProductBean;
import com.oracle.jsp.bean.ProductOptionBean;
import com.oracle.jsp.dao.ProductDao;
import com.oracle.jsp.dao.ProductOptionDao;
import com.oracle.jsp.util.StringUtil;

/**
 * Servlet implementation class ProductShowServlet
 */
public class ProductShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if ("sort".equals(method)) {
			sort(req, resp);
		} else if ("search".equals(method)) {
			search(req, resp);
		} else if ("info".equals(method)) {
			info(req, resp);
		}
	}

	/**
	 * ��ʾ��Ʒ����
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void info(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = StringUtil.StringToInt(req.getParameter("id"));//��ȡ����Ʒ��id
		ProductDao productDao = new ProductDao();
		ProductBean productBean = productDao.getProduct(id);//��ȡid���������Ʒ����ϸ��Ϣ
		String option = productBean.getProductProperties();//ͨ���ղŻ�õ���ϸ��Ϣ���ȡ����ѡ��
		String[] options = option.split(",");//����ѡ��洢ʱ�洢����ѡ���id��id֮���ã����������ڻ�ȡʱ������ַ������ڻ�ԭΪһ���ַ�������
		
		ProductOptionDao productOptionDao = new ProductOptionDao();
		//��ȡ����ѡ������ݣ�һ����Ʒ�����ж������ѡ�
		List<ProductOptionBean> productOptionBeans = new ArrayList<>();
		for (String item : options) {
			int optionId = StringUtil.StringToInt(item);
			ProductOptionBean productOptionBean = productOptionDao.getOptionById(optionId);
			productOptionBeans.add(productOptionBean);
		}
		productBean.setProductOptionBeans(productOptionBeans);
		req.setAttribute("productBean", productBean);
		// ��ȡ��Ʒ����
		/*
		 * ProductCommentDao productCommentDao = new ProductCommentDao();
		 * List<CommentBean> commentBeans =
		 * productCommentDao.getComsByProduct(id);
		 * req.setAttribute("commentBeans", commentBeans);
		 */
		try {
			req.getRequestDispatcher("info.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ڵ����������������ؼ��֣� ��ȡ��Ʒ���ư����ùؼ��ֵ�������Ʒ��
	 * 
	 * @param req
	 * @param resp
	 */
	private void search(HttpServletRequest req, HttpServletResponse resp) {
		String chars = req.getParameter("key");
		ProductDao productDao = new ProductDao();
		List<ProductBean> productBeans = new ArrayList<>();
		productBeans = productDao.getLists(chars);
		req.setAttribute("productBeans", productBeans);
		if (productBeans.size() > 0) {
			try {
				req.getRequestDispatcher("list.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				req.getRequestDispatcher("list.jsp?status=1").forward(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ʾĳ�����������Ʒ
	 * 
	 * @param req
	 * @param resp
	 */
	private void sort(HttpServletRequest req, HttpServletResponse resp) {
		ProductDao productDao = new ProductDao();
		int type_id = StringUtil.StringToInt(req.getParameter("id"));
		String status = req.getParameter("status");
		req.setAttribute("status", status);
		//��ȡ������໹���������������������Ʒ
		List<ProductBean> productBeans = productDao.getListById(type_id);
		req.setAttribute("productBeans", productBeans);
		try {
			req.getRequestDispatcher("list.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
