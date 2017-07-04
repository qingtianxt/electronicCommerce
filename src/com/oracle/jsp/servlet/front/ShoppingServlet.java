package com.oracle.jsp.servlet.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oracle.jsp.bean.AddressBean;
import com.oracle.jsp.bean.OrderBean;
import com.oracle.jsp.bean.OrderProductBean;
import com.oracle.jsp.bean.ProductTypeBean;
import com.oracle.jsp.bean.UserBean;
import com.oracle.jsp.dao.AddressDao;
import com.oracle.jsp.dao.OrderDao;
import com.oracle.jsp.dao.OrderProductDao;
import com.oracle.jsp.dao.ProductDao;
import com.oracle.jsp.dao.ProductTypeDao;
import com.oracle.jsp.util.DateUtil;
import com.oracle.jsp.util.StringUtil;

public class ShoppingServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getParameter("method");
		if ("toCart".equals(method)) {
			toCart(req, resp);
		} else if ("addItem".equals(method)) {
			addItem(req, resp);// �����Ʒ�����ﳵ
		} else if ("del".equals(method)) {
			del(req, resp);//�ӹ��ﳵ��ɾ����Ʒ
		} else if ("makeOrder".equals(method)) {
			makeOrder(req, resp);//��ת����������
		} else if ("addOrder".equals(method)) {
			addOrder(req, resp);//�ύ����
		}
	}

	/**
	 * �ύ����
	 * 
	 * @param req
	 * @param resp
	 */
	private void addOrder(HttpServletRequest req, HttpServletResponse resp) {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
		String code = DateUtil.getDateStr() + DateUtil.getTimeStr() + userBean.getId();// ������ŵĴ�����ʽ�������ռ�����+�û�id��
		OrderBean orderBean = new OrderBean(code, userBean);// ���溬�ж�����ţ����û���Ϣ
		OrderDao orderDao = new OrderDao();
		
		orderDao.addOrder(orderBean);//�ֽ������Ĳ�����Ϣ���뵽���ݿ�
		
		orderBean = orderDao.getOrderByCode(code);//orderbean�к��ж����е�������Ϣ
		// �洢����
		ProductDao productDao = new ProductDao();
		OrderProductDao orderProductDao = new OrderProductDao();
		
		
		OrderProductBean productOrderBean;
		float sum = 0;
		float oSum = 0;
		
		String[] items = req.getParameterValues("sub");
		for (String item : items) {
			String[] arr = item.split("_");
			if (arr.length == 2) {//����������Ҳ����Ʒidʱ
				
				int productId = StringUtil.StringToInt(arr[0]);
				int number = StringUtil.StringToInt(arr[1]);
				
				productOrderBean = new OrderProductBean(orderBean, productDao.getProduct(productId), number);
				//����Ʒ�������ݱ�����Ӷ�����Ϣ
				 orderProductDao.addOrderProduct(productOrderBean);
				 
				sum += productDao.getProduct(productId).getPrice();
				oSum += productDao.getProduct(productId).getOriginalPrice();
				// ɾ�� cookie
				Cookie cookie = new Cookie("items_" + userBean.getId() + "_" + productId, null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				resp.addCookie(cookie);
			}
		}
		orderBean.setPrice(sum);
		orderBean.setOriginal_price(oSum);
		// ���ʽ
		int payway = StringUtil.StringToInt(req.getParameter("payway"));
		orderBean.setPayment_type(payway);
		;
		// ��ַ
		int addressId = StringUtil.StringToInt(req.getParameter("addr"));
		orderBean.setAddress_id(addressId);
		boolean flag=orderDao.upOrder(orderBean);
		try {
			if(flag==true){
				req.getRequestDispatcher("../productShow/productShowServlet?method=sort&id=0&status=2").forward(req, resp);
			}
			else{
				req.getRequestDispatcher("../productShow/productShowServlet?method=sort&id=0&status=3").forward(req, resp);
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ȷ�϶���
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void makeOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
		float sum = 0;
		float oSum = 0;
		ProductDao productDao = new ProductDao();
		List<OrderProductBean> productOrderBeans = new ArrayList<>();
		OrderProductBean productOrderBean;
		String[] items = req.getParameterValues("sub");
		if(items==null){
			req.getRequestDispatcher("cart.jsp?status=1").forward(req, resp);
		}else{
			for (String item : items) {
				String[] arr = item.split("_");
				if (arr.length == 2) {
					int productId = StringUtil.StringToInt(arr[0]);
					int number = StringUtil.StringToInt(arr[1]);
					productOrderBean = new OrderProductBean(productDao.getProduct(productId), number);
					productOrderBeans.add(productOrderBean);
					sum += productDao.getProduct(productId).getPrice() * number;
					oSum += productDao.getProduct(productId).getOriginalPrice() * number;
				}
			}
			req.setAttribute("productOrderBeans", productOrderBeans);
			req.setAttribute("sum", sum);
			req.setAttribute("oSum", oSum);
			// ת���ַ
			AddressDao addressDao = new AddressDao();
			List<AddressBean> addressBeans = addressDao.getAddressList(userBean.getId());
			req.setAttribute("addressBeans", addressBeans);
			try {
				req.getRequestDispatcher("order.jsp").forward(req, resp);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ɾ�����ﳵ��Ʒ cookie
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void del(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
		int productId = StringUtil.StringToInt(req.getParameter("productId"));
		Cookie cookie = new Cookie("items_" + userBean.getId() + "_" + productId, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		resp.addCookie(cookie);
		resp.sendRedirect("shoppingServlet?method=toCart");
	}

	/**
	 * �����Ʒ�����ﳵ cookie
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void addItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		// û�е�¼��ʱ��Ҳ���Լ��뵽���ﳵ
		if (userBean == null) {
			userBean = new UserBean();
		}
		//��ȡ��Ʒid
		int productId = StringUtil.StringToInt(req.getParameter("productId"));
		String number = req.getParameter("num");
		Cookie cookie = new Cookie("items_" + userBean.getId() + "_" + productId, number);
		// �洢��Ʒ����������Ϊcookie������ʱ���ȡ�û���id����Ʒ��id
		cookie.setPath("/");
		resp.addCookie(cookie);
		PrintWriter out = resp.getWriter();
		out.print("data");
		out.flush();
		out.close();
	}

	/**
	 * ת�����ﳵ
	 * 
	 * @param req
	 * @param resp
	 */
	private void toCart(HttpServletRequest req, HttpServletResponse resp) {
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		if (userBean == null) {
			userBean = new UserBean();
		}
//		ProductTypeDao productTypeDao = new ProductTypeDao();
//		List<ProductTypeBean> typeBeans = productTypeDao.getTypeBeans(0);
//		req.setAttribute("typeBeans", typeBeans);
		// �洢����
		ProductDao productDao = new ProductDao();
		List<OrderProductBean> productOrderBeans = new ArrayList<>();
		OrderProductBean productOrderBean;
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {//����cookie������Ʒ������Ϣ�洢��productOrderBeans��
				if (cookie.getName().startsWith("items_" + userBean.getId())) {
					String[] arr = cookie.getName().split("_");
					if (arr.length == 3) {
						int productId = StringUtil.StringToInt(arr[2]);
						int number = StringUtil.StringToInt(cookie.getValue());
						productOrderBean = new OrderProductBean(productDao.getProduct(productId), number);
						productOrderBeans.add(productOrderBean);
					}
				}
			}
		}
		req.setAttribute("productOrderBeans", productOrderBeans);
		try {
			req.getRequestDispatcher("cart.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}