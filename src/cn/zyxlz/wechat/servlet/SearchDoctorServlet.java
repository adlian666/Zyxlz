package cn.zyxlz.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zyxlz.wechat.service.Service;
import cn.zyxlz.wechat.service.impl.ServiceImpl;

/**
 * Servlet implementation class SearchDoctorServlet
 */
public class SearchDoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Service service = new ServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchDoctorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("text");
		String text1= text.replace("\"", "");
		String str = service.searchDoctor(text1);

		System.out.println(text1);
		System.out.println(str);
		response.setCharacterEncoding("UTF_8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");

		PrintWriter writer = response.getWriter();
		writer.write(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("text");
		String str = service.searchDoctor(text);
		
		response.setCharacterEncoding("UTF_8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");

		PrintWriter writer = response.getWriter();
		writer.write(str);
	}

}
