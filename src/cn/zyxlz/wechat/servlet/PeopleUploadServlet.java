package cn.zyxlz.wechat.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import cn.zyxlz.wechat.bean.PeopleCaseBean;
import cn.zyxlz.wechat.service.Service;
import cn.zyxlz.wechat.service.impl.ServiceImpl;

/**
 * Servlet implementation class PeopleUploadServlet
 */
public class PeopleUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PeopleCaseBean peoplecase = new PeopleCaseBean();
	String imgurl = "";
	private Service service = new ServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PeopleUploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			// 1.创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2.创建文件上传的核心类
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置上传文件的编码
			upload.setHeaderEncoding("UTF-8");
			// 3.解析request-----获得文件集合
			List<FileItem> parseRequest = upload.parseRequest(request);
			// 4.遍历文件项集合
			if (parseRequest != null) {
				String imgurl = "";
				for (FileItem item : parseRequest) {
					boolean formfield = item.isFormField();
					if (formfield) {
						// 普通表单项
						String fieldname = item.getFieldName();
						String fieldvalue = item.getString("UTF-8");// 对普通表单项内容定义
						map.put(fieldname, fieldvalue);

					} else {
						// 文件上传项
						// 获得上传文件的名称
						String filename = item.getName();
						String names[] = filename.split("\\.");
						int i = names.length;
						filename = System.currentTimeMillis() + "." + names[i - 1];
					
						// 获得上传内容
						InputStream in = item.getInputStream();    
						// 将in中的数据拷贝到服务器上
						String path = this.getServletContext().getRealPath("upload/people_case");
						
						OutputStream out = new FileOutputStream(path + "/" + filename);
						IOUtils.copy(in, out);
						
						in.close();
						out.close();
						// 删除临时文件。
						item.delete();
						imgurl =   "https://wechat.xust.edu.cn/Zyxlz/" + "upload/people_case/" + filename ;
						System.out.println(filename);

						map.put("CaseAttachment", "upload/people_case/" + filename);
					}
				}
				System.out.println(imgurl);
				// BeanUtils.populate(peoplecase, map);

				String GUIDCase = java.util.UUID.randomUUID().toString().replaceAll("-", "");
				peoplecase.setCaseContent(map.get("CaseContent").toString());
				peoplecase.setGUIDCase(GUIDCase);
				peoplecase.setPeopleCode(map.get("PeopleCode").toString());
				//peoplecase.setGUIDPeople(map.get("PeopleCode").toString());
				peoplecase.setCaseContent(map.get("CaseContent").toString());
				peoplecase.setCaseAttachment(imgurl);
				peoplecase.setCaseTime(new Date());
				peoplecase.setReleaseTime(new Date());
				String s = service.uploadCase(peoplecase);
				response.setCharacterEncoding("UTF_8");
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(s);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
