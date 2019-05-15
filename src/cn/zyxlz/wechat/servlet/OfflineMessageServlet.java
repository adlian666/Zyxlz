package cn.zyxlz.wechat.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.zyxlz.wechat.service.Service;
import cn.zyxlz.wechat.service.impl.ServiceImpl;

/**
 * Servlet implementation class OfflineMessageServlet
 */
public class OfflineMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Service service = new ServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OfflineMessageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		JSONObject jsonObject = JSONObject.parseObject(sb.toString());
		String fromPuInnerCode = jsonObject.getString("fromPuInnerCode");
		String fromUserName = jsonObject.getString("fromUserName");

		String toPuInnerCode = jsonObject.getString("toPuInnerCode");
		String toUserName = jsonObject.getString("toUserName");
		String content = jsonObject.getString("content");
		String createTime = jsonObject.getString("createTime");
		String chatId = jsonObject.getString("chatId");
		String sign = jsonObject.getString("sign");
		Object[] params = { fromPuInnerCode, fromUserName, toPuInnerCode, toUserName, content, createTime, chatId,
				sign };
		String str = service.offlineMessage(params);
		System.out.println("离线消息：" + params);
		response.setCharacterEncoding("UTF_8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//内容是个json格式数据， 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		JSONObject jsonObject = JSONObject.parseObject(sb.toString());
		String fromPuInnerCode = jsonObject.getString("fromPuInnerCode");
		String fromUserName = jsonObject.getString("fromUserName");
		String toPuInnerCode = jsonObject.getString("toPuInnerCode");
		String toUserName = jsonObject.getString("toUserName");
		String content = jsonObject.getString("content");
		String createTime = jsonObject.getString("createTime");
		String chatId = jsonObject.getString("chatId");
		String sign = jsonObject.getString("sign");
		Object[] params = { fromPuInnerCode, fromUserName, toPuInnerCode, toUserName, content, createTime, chatId,
				sign };
		String str = service.offlineMessage(params);
		System.out.println("离线消息：" + fromPuInnerCode);
		response.setCharacterEncoding("UTF_8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(str);
	}

}
