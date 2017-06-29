package com.oracle.jsp.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.oracle.jsp.bean.ProductTypeBean;
import com.oracle.jsp.dao.ProductTypeDao;
import com.oracle.jsp.util.StringUtil;

/**
 * Servlet implementation class ProductTypeServlet
 */
public class ProductTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException{
		req.setCharacterEncoding("utf-8");
		String method =req.getParameter("method");
		if("toAdd".equals(method)){
			toAdd(req,resp);
		}
		else if("add".equals(method)){
			add(req,resp);
		}else if("list".equals(method)){
			list(req,resp);
		}
		else if("update".equals(method)){
			update(req,resp);
		}
		else if("delete".equals(method)){
			delete(req,resp);
		}
		else if("getType".equals(method)){//ajax������
			getType(req,resp);
		}
	}
	
	/**
	 * ɾ������
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id =StringUtil.StringToInt(req.getParameter("id"));
		ProductTypeDao productTypeDao = new ProductTypeDao();
		productTypeDao.delete(id);
		resp.sendRedirect("productTypeServlet?method=list");
	}


	/**
	 * ��ʾ��߼�����
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int parentId = StringUtil.StringToInt(req.getParameter("id"));
		ProductTypeDao productTypeDao = new ProductTypeDao();
		ProductTypeBean productTypeBean =productTypeDao.getType(parentId);
		System.out.println(productTypeBean);
		req.setAttribute("productTypeBean", productTypeBean);
		req.getRequestDispatcher("list.jsp").forward(req, resp);
	}


	/**
	 * ��ȡ����
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void getType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//��ajax��ȡ��id�ǵ��������id
		int parentId = StringUtil.StringToInt(req.getParameter("id"));
		ProductTypeDao productTypeDao = new ProductTypeDao();
		//�����ݿ���Ѱ�Ҹ���id��parentId��Ҳ���ǵ���ķ����id�������ݣ��ŵ�������
		List<ProductTypeBean>typeList = productTypeDao.getTypeList(parentId);
		resp.setCharacterEncoding("utf-8");
		PrintWriter out =resp.getWriter();
		out.println(JSON.toJSONString(typeList));
		out.flush();
		out.close();
	}
	/**
	 * Я������������ת��������ӽ���
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductTypeDao productTypeDao  =new ProductTypeDao();
		List<ProductTypeBean> typeList = productTypeDao.getTypeBean(0);
		req.setAttribute("productTypeList", typeList);
		req.getRequestDispatcher("add.jsp").forward(req, resp);
	}
	
	/**
	 * ��ת���޸���Ʒ���ͽ���
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id =StringUtil.StringToInt(req.getParameter("id"));
		ProductTypeDao productTypeDao = new ProductTypeDao();
		//��Ϊ���޸�,������Ҫ��ȡ�����Ҫ�޸ĵ�id����Ϣ���������ҳ����Ĭ��ֵ
		ProductTypeBean productTypeBean = productTypeDao.getTypeById(id);
		
		req.setAttribute("productTypeBean", productTypeBean);
		
		//����ת���ҳ��һ������Ҫ��ȡ�������࣬���ڵ�һ��option�����ݴ�
		List<ProductTypeBean> typeList = productTypeDao.getTypeList(0);
		req.setAttribute("productTypeList", typeList);
		
		req.getRequestDispatcher("add.jsp").forward(req, resp);
		
	}
	
	/**
	 * �����Ʒ����
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String name =req.getParameter("name");
		int sort = StringUtil.StringToInt(req.getParameter("sort"));
		String intro =req.getParameter("desc");
		int id =StringUtil.StringToInt(req.getParameter("id"));
		String []parIds =req.getParameterValues("parentId");
		
		int parentId =0;
		//�ҵ����id�ĸ���
		for(String parId:parIds){
			parentId = Math.max(parentId,StringUtil.StringToInt(parId));
		}
		ProductTypeDao productTypeDao = new ProductTypeDao();
		boolean f; 
		if(id == 0){
			//����¼�¼
			ProductTypeBean productTypeBean = new ProductTypeBean(sort,parentId, name, intro);
			f= productTypeDao.add(productTypeBean);
		}else{
			//�޸ļ�¼
			ProductTypeBean productTypeBean = new ProductTypeBean(id,sort, parentId, name, intro);
			f= productTypeDao.update(productTypeBean);
		}
		if(f){
			//�ض���ᱣ�����status�����ܣ�
			resp.sendRedirect("productTypeServlet?method=toAdd&status=true");
		}else
		{
			resp.sendRedirect("productTypeServlet?method=toAdd&status=false");
		}
	}

}
