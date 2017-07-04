package com.oracle.jsp.servlet.front;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.oracle.jsp.bean.ProductBean;
import com.oracle.jsp.bean.UserBean;
import com.oracle.jsp.dao.ProductDao;
import com.oracle.jsp.dao.UserDao;
import com.oracle.jsp.util.Constants;
import com.oracle.jsp.util.DateUtil;
import com.oracle.jsp.util.MD5;
import com.oracle.jsp.util.StringUtil;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		String method = req.getParameter("method");
		if(method.equals("reg")){
			add(req,resp);//ע��
		}
		else if(method.equals("login")){
			login(req,resp);
		}
	}
/**
 * ǰ̨�û���¼
 * @param req
 * @param resp
 * @throws IOException 
 * @throws ServletException 
 */
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserBean userBean =new UserBean();
		String username = req.getParameter("account");
		String password= req.getParameter("password");
		password = MD5.GetMD5Code(password);
		UserDao userDao = new UserDao();
		UserBean userbean=userDao.login(username,password);
		req.getSession().setAttribute("userBean", userbean);
		if(userbean!=null){
			req.getRequestDispatcher("../productShow/list.jsp").forward(req, resp);
//			System.out.println("��½�ɹ�");
		}
		else{
			req.getRequestDispatcher("login.jsp?status=1").forward(req, resp);
		}
	}
/**
 * ǰ̨�û�ע��
 * @param req
 * @param resp
 */
	private void add(HttpServletRequest req, HttpServletResponse resp) {
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
		
		boolean status = true;
		
		try {
			List<FileItem> fileItems = upload.parseRequest(req);
			UserBean userBean = new UserBean();
			for (FileItem item : fileItems) {
				item.getString("utf-8");
//				System.out.println(item.toString());
				if (item.isFormField()) {
					// ���������
					processFormField(item,userBean);
					//�����û�״̬Ϊ��Ծ
					userBean.setStatus(1);
				} else {
//					 �����ϴ��ļ���ͼƬ��
					System.out.println("tupian");
					processUploadFile(item, userBean);
				}
			}
			
			
			UserDao userDao = new UserDao();
			boolean flag=userDao.checkUsername(userBean.getUsername());
			
				
				
				if(flag==true)
				{
					status = userDao.add(userBean);
					if(status==true){
						req.getRequestDispatcher("add.jsp?status=1").forward(req, resp);
					}
					else{
						req.getRequestDispatcher("add.jsp?status=3").forward(req, resp);
					}
				}
				else{
					req.getRequestDispatcher("add.jsp?status=2").forward(req, resp);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �����ϴ����ļ�����ͷ��
	 * @param item
	 * @param userBean
	 */
	private void processUploadFile(FileItem item, UserBean userBean) {
		//��filename��ȡͼƬ�ø�ʽ
		String filename = item.getName();
		int index = filename.lastIndexOf(".");
		filename = filename.substring(index+1,filename.length());
		
		String picPath = Constants.PIC_SHOW_PATH+DateUtil.getDateStr()+"/"+DateUtil.getTimeStr()+"."+filename;
		long filesize =item.getSize();
		if("".equals(filename)&&filesize==0){
			return ;
		}
		File file = new File(Constants.PIC_UPLOAD_PATH+DateUtil.getDateStr());
//		System.out.println(Constants.PIC_UPLOAD_PATH+DateUtil.getDateStr());
		
		file.mkdirs();
		File uploadFile = new File(Constants.PIC_UPLOAD_PATH+DateUtil.getDateStr()+"/"+DateUtil.getTimeStr()+"."+filename);
		try {
			item.write(uploadFile);
			userBean.setPic(picPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �������Ϣ
	 * @param item
	 * @param userBean
	 * @throws UnsupportedEncodingException 
	 */
	private void processFormField(FileItem item, UserBean userBean) throws UnsupportedEncodingException {
		String name = item.getFieldName();
		String value = new String(item.getString("utf-8"));
		switch (name) {
		case "account":
			userBean.setUsername(value);
			break;
		case "password":
			String password = value;
			
			String salt = StringUtil.getRandomString(10);
			
			userBean.setPassword(MD5.GetMD5Code(MD5.GetMD5Code(password)+salt));
			userBean.setSalt(salt);
		case "nickname":
			userBean.setNickname(value);
		case "truename":
			userBean.setTruename(value);
		case "sex":
			String ssex = value;
			int sex =0;
			//���ݿ�����Ů��int���ʹ洢��1��ʾŮ  ��0��ʾ��
			if(ssex.equals("��")){
				sex=0;
			}
			else{
				sex=1;
			}
			userBean.setSex(sex);
			
		default:
			break;
		}
	}
}
