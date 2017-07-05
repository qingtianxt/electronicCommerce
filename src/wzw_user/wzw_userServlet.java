package wzw_user;

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
		if (method.equals("login")) {
			login(req, resp);
		} else if (method.equals("update")) {
			update(req, resp);
		} else if (method.equals("pic")) {
			pic(req, resp);
		}

	}

	private void pic(HttpServletRequest req, HttpServletResponse resp) {
//		System.out.println("进来了");
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);

		wzw_userDao userDao = new wzw_userDao();

		userBean userbean = new userBean();
		String id = "";
		String path = "";
		boolean status = true;
		try {
			List<FileItem> fileItems = upload.parseRequest(req);
			for (FileItem item : fileItems) {
				item.getString("utf-8");
				if (item.isFormField()) {
					// 处理表单内容
					id = processFormField(item);
//					System.out.println(id);
					userbean = userDao.getUserbyId(id);
//					System.out.println(userbean.toString());
				} else {
					// 处理上传文件（图片）
					path = processUploadFile(item);
				}
			}

			status = userDao.updatepic(path, userbean);
			
			if (status == true) {
				userbean.setPic(path);
				req.getSession().setAttribute("userBean", userbean);
				req.getRequestDispatcher("userinfo.jsp?status=3").forward(req, resp);
			} else {
				req.getRequestDispatcher("userinfo.jsp?status=4").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 处理数据（这里只有一个id）
	 * 
	 * @param item
	 * @param userbean
	 */
	private String processFormField(FileItem item) {
		String id1 = "";
		try {
			String value = new String(item.getString("utf-8"));
			String name = item.getFieldName();
			if (name.equals("id")) {
				id1 = value;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return id1;
	}

	/**
	 * 处理上传的头像
	 * 
	 * @param item
	 * @param pic
	 */
	private String processUploadFile(FileItem item) {
		String path = "";
		String filename = item.getName();
		int index = filename.lastIndexOf(".");
		filename = filename.substring(index + 1, filename.length());
//		System.out.println("格式"+filename);
		String picPath = Constants.PIC_SHOW_PATH + DateUtil.getDateStr()+"/" + DateUtil.getTimeStr()+"."+filename;
		
		long filesize = item.getSize();
		if ("".equals(filename) && filesize == 0) {
			path = "";
		}
		File file = new File(Constants.PIC_UPLOAD_PATH + DateUtil.getDateStr());

		file.mkdirs();

		File uploadFile = new File(
				Constants.PIC_UPLOAD_PATH + DateUtil.getDateStr() + "/" + DateUtil.getTimeStr() + "." + filename);
		try {
			item.write(uploadFile);
			path = picPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 修改个人信息
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String phone = req.getParameter("account");
		String nickname = req.getParameter("nickname");
		String password = req.getParameter("password");
		String id = req.getParameter("id");
		wzw_userDao userDao = new wzw_userDao();

		userBean userbean = userDao.getUserbyId(id);
		System.out.println(userbean.toString());
		password = MD5.GetMD5Code(MD5.GetMD5Code(password) + userbean.getSalt());
		userbean.setNickname(nickname);
		userbean.setPassword(password);
		userbean.setUsername(phone);

		boolean flag = userDao.update(userbean);
		if (flag == true) {
			req.getSession().setAttribute("userBean", userbean);
			System.out.println(userbean.toString());
			req.getRequestDispatcher("userinfo.jsp?status=1").forward(req, resp);
		} else {
			req.getRequestDispatcher("userinfo.jsp?status=2").forward(req, resp);
		}
	}

	/**
	 * 前台用户登录
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("account");
		String password = req.getParameter("password");
		password = MD5.GetMD5Code(password);
		wzw_userDao userDao = new wzw_userDao();
		userBean userbean = userDao.login(username, password);
		req.getSession().setAttribute("userBean", userbean);
		if (userbean != null) {
			req.getRequestDispatcher("userinfo.jsp").forward(req, resp);
			// System.out.println("登陆成功");
		} else {
			req.getRequestDispatcher("login.jsp?status=1").forward(req, resp);
		}
	}

}
