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
 * Servlet implementation class PeopleLoginServlet
 */
public class PeopleLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Service service = new ServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PeopleLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickName = request.getParameter("nickName");
		String gender = request.getParameter("gender");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		String country  = request.getParameter("country");
		String avatarUrl = request.getParameter("avatarUrl");
		String code = request.getParameter("code");
		String mancode = request.getParameter("caInnercode");
		String openId = request.getParameter("openId");
		String FormId = request.getParameter("FormId");
		Object[] params = {openId,mancode,nickName,country,avatarUrl};
		//调用添加方法
		String str = service.peopleLogin(params);
		response.setCharacterEncoding("UTF_8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickName = request.getParameter("nickName");
		String gender = request.getParameter("gender");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		String country  = request.getParameter("country");
		String avatarUrl = request.getParameter("avatarUrl");
		String code = request.getParameter("code");
		String mancode = request.getParameter("caInnercode");
		String openId = request.getParameter("openId");
		String FormId = request.getParameter("FormId");
		Object[] params = {openId,mancode,nickName,country,avatarUrl};
		//调用添加方法
		String str = service.peopleLogin(params);
		response.setCharacterEncoding("UTF_8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(str);
	}

}
