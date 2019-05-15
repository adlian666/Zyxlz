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
 * Servlet implementation class QueryArticleListServlet
 */
public class QueryArticleListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Service service = new ServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryArticleListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String s = request.getParameter("ArticleTypeCode");
		String str = service.getArticles(s);
		/*
		 * Object users = null; String jsonString2 = JSON.toJSONString(users);
		 * System.out.println("jsonString2:" + jsonString2);
		 */
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
