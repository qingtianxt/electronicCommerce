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
	 * 显示商品详情
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void info(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = StringUtil.StringToInt(req.getParameter("id"));//获取该商品的id
		ProductDao productDao = new ProductDao();
		ProductBean productBean = productDao.getProduct(id);//获取id所代表的商品的详细信息
		String option = productBean.getProductProperties();//通过刚才获得的详细信息里获取属性选项
		String[] options = option.split(",");//属性选项存储时存储的是选项的id，id之间用，隔开，现在获取时将这个字符串，在还原为一个字符串数组
		
		ProductOptionDao productOptionDao = new ProductOptionDao();
		//存取属性选项的内容（一个商品可以有多个属性选项）
		List<ProductOptionBean> productOptionBeans = new ArrayList<>();
		for (String item : options) {
			int optionId = StringUtil.StringToInt(item);
			ProductOptionBean productOptionBean = productOptionDao.getOptionById(optionId);
			productOptionBeans.add(productOptionBean);
		}
		productBean.setProductOptionBeans(productOptionBeans);
		req.setAttribute("productBean", productBean);
		// 获取商品评论
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
	 * 在导航栏搜索框搜索关键字， 获取商品名称包含该关键字的所有商品。
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
	 * 显示某分类的所有商品
	 * 
	 * @param req
	 * @param resp
	 */
	private void sort(HttpServletRequest req, HttpServletResponse resp) {
		ProductDao productDao = new ProductDao();
		int type_id = StringUtil.StringToInt(req.getParameter("id"));
		String status = req.getParameter("status");
		req.setAttribute("status", status);
		//获取这个分类还有他的所有子类的所有商品
		List<ProductBean> productBeans = productDao.getListById(type_id);
		req.setAttribute("productBeans", productBeans);
		try {
			req.getRequestDispatcher("list.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
