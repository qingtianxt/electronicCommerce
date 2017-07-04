package wzw_user;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.oracle.jsp.dao.UserDao;
import com.oracle.jsp.util.DateUtil;
import com.oracle.jsp.util.MD5;

/**
 * Servlet implementation class wzw_userServlet
 */
public class wzw_userServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String method = req.getParameter("method");
		 if(method.equals("login")){
			login(req,resp);
		}
		else if(method.equals("update")){
			update(req,resp);
		}else if(method.equals("pic")){
			pic(req,resp);
		}
	
	}
	private void pic(HttpServletRequest req, HttpServletResponse resp) {
		 DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		 ServletFileUpload upload= new ServletFileUpload(diskFileItemFactory);
		 userBean userbean =(userBean)req.getSession().getAttribute("user");
		 boolean status = true;
		 String path="";
		 try{
			 List<FileItem> fileItems = upload.parseRequest(req);
			 String pic="";
			 for (FileItem item : fileItems) {
				item.getString("utf-8");
				path=processUploadFile(item, pic);
			}
			 String id="";
			 wzw_userDao userdao = new wzw_userDao();
				 status =userdao.updatepic(path,id);
			 if(status == true){
				 req.getRequestDispatcher("userinfo.jsp?status=4").forward(req, resp);
			 }else{
				 req.getRequestDispatcher("userinfo.jsp?status=5").forward(req, resp);
			 }
		 }
		 catch (Exception e) {
				e.printStackTrace();
			}
		 
		}
	/**
	 * 处理上传的头像
	 * @param item
	 * @param pic
	 */
	private String  processUploadFile(FileItem item, String pic) {
			String path = "";
			String filename = item.getName();
			int index= filename.lastIndexOf(".");
			filename = filename.substring(index+1,filename.length());
			
			String picPath = Constants.PIC_SHOW_PATH+DateUtil.getDateStr()+DateUtil.getTimeStr();
			long filesize = item.getSize();
			if("".equals(filename)&&filesize==0){
				path="";
			}
			File file = new File(Constants.PIC_UPLOAD_PATH+DateUtil.getDateStr()); 
			
			file.mkdirs();
			
			File uploadFile = new  File(Constants.PIC_UPLOAD_PATH+DateUtil.getDateStr()+"/"+DateUtil.getTimeStr()+"."+filename);
			try{
				item.write(uploadFile);
				path=picPath;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return path;
		}
/**
 * 修改个人信息
 * @param req
 * @param resp
 * @throws IOException 
 * @throws ServletException 
 */
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		userBean userbean =(userBean)req.getSession().getAttribute("user");
		String phone = req.getParameter("account");
		String nickname = req.getParameter("nickname");
		String password = req.getParameter("password");
		password = MD5.GetMD5Code(MD5.GetMD5Code(password)+userbean.getSalt());
		String id = req.getParameter("id");
		userbean.setNickname(nickname);
		userbean.setPassword(password);
		userbean.setUsername(phone);
		
		wzw_userDao userDao = new wzw_userDao();
		boolean flag = userDao.update(id,phone,nickname,password);
		if(flag==true){
			req.getSession().setAttribute("user", userbean);
			req.getRequestDispatcher("userinfo.jsp?status=1").forward(req, resp);
		}else{
			req.getRequestDispatcher("userinfo.jsp?status=2").forward(req, resp);
		}
	}
	/**
	 * 前台用户登录
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
		private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String username = req.getParameter("account");
			String password= req.getParameter("password");
			password = MD5.GetMD5Code(password);
			wzw_userDao userDao = new wzw_userDao();
			userBean userbean=userDao.login(username,password);
			req.getSession().setAttribute("user", userbean);
			if(userbean!=null){
				req.getRequestDispatcher("userinfo.jsp").forward(req, resp);
//				System.out.println("登陆成功");
			}
			else{
				req.getRequestDispatcher("login.jsp?status=1").forward(req, resp);
			}
		}
	
}
