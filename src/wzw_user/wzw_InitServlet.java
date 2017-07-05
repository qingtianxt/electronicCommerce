package wzw_user;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class InitServlet
 */
public class wzw_InitServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("后台配置初始化开始wzw");
		String path;
		FileInputStream fis;
		try {
			// 读取 conf.properties 文件
			path = wzw_InitServlet.class.getResource("/").getPath();
			fis = new FileInputStream(path + "conf.properties");
			Properties properties = new Properties();
			properties.load(fis);
			fis.close();
			// 覆盖 Constants 里和图片上传相关的属性
			String picUploadPath = properties.getProperty("pic_upload_path");
			if (picUploadPath != null) {
				Constants.PIC_UPLOAD_PATH = picUploadPath;
			}
			String picShowPath = properties.getProperty("pic_show_path");
			if (picShowPath != null) {
				Constants.PIC_SHOW_PATH = picShowPath;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
