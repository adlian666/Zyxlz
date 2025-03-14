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
 * Servlet implementation class QueryOrganizationDoctorServlet
 */
public class QueryOrganizationDoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Service service = new ServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryOrganizationDoctorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String HospitalOrganizationCode = request.getParameter("HospitalOrganizationCode");
		String str = service.queryOrganizationDoctor(HospitalOrganizationCode);
		response.setCharacterEncoding("UTF_8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
