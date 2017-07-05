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
		System.out.println("��̨���ó�ʼ����ʼwzw");
		String path;
		FileInputStream fis;
		try {
			// ��ȡ conf.properties �ļ�
			path = wzw_InitServlet.class.getResource("/").getPath();
			fis = new FileInputStream(path + "conf.properties");
			Properties properties = new Properties();
			properties.load(fis);
			fis.close();
			// ���� Constants ���ͼƬ�ϴ���ص�����
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
