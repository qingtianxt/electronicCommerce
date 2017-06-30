package com.oracle.jsp.servlet.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.oracle.jsp.bean.ProductBean;
import com.oracle.jsp.bean.ProductOptionBean;
import com.oracle.jsp.dao.ProductDao;
import com.oracle.jsp.dao.ProductOptionDao;
import com.oracle.jsp.util.Constants;
import com.oracle.jsp.util.DateUtil;
import com.oracle.jsp.util.StringUtil;

@SuppressWarnings("serial")
public class ProductServlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		req.setCharacterEncoding("utf-8");
		if ("add".equals(method)) {
			add(req, resp);
		}
		else if("list".equals(method)){
			list(req,resp);
			}
		else if("listDetails".equals(method)){
			listDetails(req,resp);
			}
		else if("update".equals(method)){
			update(req,resp);
			}
		else if("delete".equals(method)){
			delete(req,resp);
		}
	}
	/**
	* ɾ����Ʒ
	* @param req
	* @param resp
	*/
	private void delete(HttpServletRequest req, HttpServletResponse resp)
	{
	int id = StringUtil.StringToInt(req.getParameter("id"));
	ProductDao productDao = new ProductDao();
	productDao.delete(id);
	try {
	resp.sendRedirect("productServlet?method=list");
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	
	
	
	/**
	* �޸���Ʒ
	* @param req
	* @param resp
	*/
	private void update(HttpServletRequest req, HttpServletResponse resp)
	{
	int id = StringUtil.StringToInt(req.getParameter("id"));
	ProductDao productDao = new ProductDao();
	ProductBean productBean = productDao.getProduct(id);
	req.setAttribute("productBean", productBean);try {
	req.getRequestDispatcher("add.jsp").forward(req, resp);
	} catch (ServletException | IOException e) {
	e.printStackTrace();
	}
	}
	/**
	* ��ʾ��Ʒ����
	* @param req
	* @param resp
	*/
	private void listDetails(HttpServletRequest req, HttpServletResponse resp) {
	int id = StringUtil.StringToInt(req.getParameter("id"));
	ProductDao productDao = new ProductDao();
	ProductBean productBean = productDao.getProduct(id);
	String option = productBean.getProductProperties();
	String[] options = option.split(",");
	ProductOptionDao productOptionDao = new ProductOptionDao();
	List<ProductOptionBean>productOptionBeans = new ArrayList<>();
	for(String item : options){
	int optionId = StringUtil.StringToInt(item);
	ProductOptionBean productOptionBean =
	productOptionDao.getOptionById(optionId);
	//productOptionBean.getProductPropertyBean();
	productOptionBeans.add(productOptionBean);
	}
	productBean.setProductOptionBeans(productOptionBeans);
	req.setAttribute("productBean", productBean);
	try {
		req.getRequestDispatcher("details.jsp").forward(req, resp);
	} catch (ServletException | IOException e) {
		e.printStackTrace();
	}
	}
	
	
	/**
	* ��ʾ��Ʒ
	* @param req
	* @param resp
	 * @throws IOException 
	 * @throws ServletException 
	*/
	private void list(HttpServletRequest req, HttpServletResponse resp)  {
	ProductDao productDao = new ProductDao();
	List<ProductBean>productBeans = productDao.getList();
	req.setAttribute("productBeans", productBeans);
	
	try {
		req.getRequestDispatcher("list.jsp").forward(req, resp);
	} catch (ServletException | IOException e) {
		e.printStackTrace();
	}
	
	}
	/**
	 * �����Ʒ
	 * 
	 * @param req
	 * @param resp
	 * @throws UnsupportedEncodingException
	 */
	private void add(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
		req.setCharacterEncoding("utf-8");
		boolean status = true;
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();//����fileitem ����Ĺ���
		ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);//�������ϴ��ļ����ļ����ݣ��������е�ÿһ�������װ��fileitem����
		try {
			List<FileItem> fileItems = upload.parseRequest(req);//����upload.parseRequest��������request���󣬵õ�һ�������������ݵ�list����
			ProductBean productBean = new ProductBean();
			for (FileItem item : fileItems) {
				item.getString("utf-8");
//				System.out.println(item.toString());
				if (item.isFormField()) {
					// ���������
					processFormField(item, productBean);//���fileitem���������ͨ�����������
				} else {
					// �����ϴ��ļ���ͼƬ��
//					System.out.println("tupian");
					processUploadFile(item, productBean);
				}
			}
			ProductDao productDao = new ProductDao();
			if (productBean.getId() == 0) {
				status = productDao.add(productBean);
				if(status==true)
				{
					req.getRequestDispatcher("add.jsp?status=1").forward(req, resp);
				}
				else{
					req.getRequestDispatcher("add.jsp?status=2").forward(req, resp);
				}
				
			} else {
				status=productDao.update(productBean);
				if(status == true){
					req.getRequestDispatcher("add.jsp?status=3").forward(req, resp);
				}
				else{
					req.getRequestDispatcher("add.jsp?status=4").forward(req, resp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/**
* �����ϴ����ļ�
* @param item
* @param pw
* @param productBean
*/
	private void processUploadFile(FileItem item,
			ProductBean productBean) {
			String filename = item.getName();
			//����ļ��ϴ��ֶε��ļ���
			int index = filename.lastIndexOf(".");//�ҵ�.���±�
			filename = filename.substring(index+1, filename.length());//��á�֮��ĺ�׺����Ҳ�����ļ��ĸ�ʽ
			String picPath = Constants.PIC_SHOW_PATH
			+DateUtil.getDateStr()+"/"+DateUtil.getTimeStr()+"."+ filename;
			//չʾ��·��
			long fileSize = item.getSize();//�����ϴ��ļ��Ĵ�С
			if("".equals(filename)&&fileSize == 0){
				return;
			}
			//�½��ļ��У�����Ϊ�ļ�������ʱ��Ϊ�ļ���
			File file = new File(Constants.PIC_UPLOAD_PATH+DateUtil.getDateStr());
			System.out.println(Constants.PIC_UPLOAD_PATH+DateUtil.getDateStr());
			
			file.mkdirs();
			File uploadFile = new File(Constants.PIC_UPLOAD_PATH
			+DateUtil.getDateStr()+"/"+DateUtil.getTimeStr()+"."+ filename);
			try {
				item.write(uploadFile);//���ļ���д��ָ�����ļ����棬������Ǵ�����ͼƬ��
				
				productBean.setPic(picPath);
			} catch (Exception e) {
			e.printStackTrace();
			}
			}
			/**
	/**
	 * ���������
	 * 
	 * @param item
	 * @param pw
	 * @param productBean
	 * @throws UnsupportedEncodingException
	 */
	private void processFormField(FileItem item, ProductBean productBean)
			throws UnsupportedEncodingException {
		String name = item.getFieldName();
		//��ñ���ǩ�е�name���Ե�ֵ
		String value = new String(item.getString("utf-8"));
		switch (name) {
		case "id":
			int id = StringUtil.StringToInt(value);
			productBean.setId(id);
			;
			break;
		case "name":
			productBean.setName(value);
			break;
		case "productTypeId":
			int productTypeId = Math.max(productBean.getProductTypeId(), StringUtil.StringToInt(value));
			productBean.setProductTypeId(productTypeId);
			break;
		case "originalPrice":
			float originalPrice = StringUtil.strToFlo(value);
			productBean.setOriginalPrice(originalPrice);
			break;
		case "price":
			float price = StringUtil.strToFlo(value);
			productBean.setPrice(price);
			break;
		case "intro":
			productBean.setIntro(value);
			break;
		case "number":
			int number = StringUtil.StringToInt(value);
			productBean.setNumber(number);
			break;
		case "option":
			String options = productBean.getProductProperties();
			if (options == null) {
				productBean.setProductProperties(value);
			} else {
				productBean.setProductProperties(options + "," + value);
			}
			break;
		default:
			break;
		}
	}
}