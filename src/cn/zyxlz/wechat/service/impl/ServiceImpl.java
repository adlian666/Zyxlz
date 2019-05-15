package cn.zyxlz.wechat.service.impl;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;

import cn.zyxlz.wechat.bean.PeopleCaseBean;
import cn.zyxlz.wechat.dao.Dao;
import cn.zyxlz.wechat.dao.impl.DaoImpl;
import cn.zyxlz.wechat.service.Service;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceImpl implements Service {
	Dao dao = new DaoImpl();
	String openid;

	public String queryAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	public String getArticles(String str) {
		// TODO Auto-generated method stub
		return dao.getArticles(str);
	}

	public String postUserInfo(Object[] params) {
		// TODO Auto-generated method stub
		return dao.postUserInfo(params);
	}

	public String queryHospitalList() {
		// TODO Auto-generated method stub
		return dao.queryHospitalList();
	}

	public String queryOrganizationDoctor(String str) {
		// TODO Auto-generated method stub
		return dao.queryOrganizationDoctor(str);
	}

	public void uploadCase(PeopleCaseBean peoplecase) {
		// TODO Auto-generated method stub
		dao.uploadCase(peoplecase);
	}

	public String searchDoctor(String str) {
		// TODO Auto-generated method stub
		return dao.searchDoctor(str);
	}

	// 登陆
	public String login(String str) {

		OkHttpClient okhttp = new OkHttpClient();
		Request request = new Request.Builder().get()
				.url("https://api.weixin.qq.com/sns/jscode2session?" + "appid=" + "wxea1a33993b78bd88" + "&secret="
						+ "c8b417ed45b986c18ad3ab4291701b58" + "&js_code=" + str + "&grant_type=authorization_code")

				.build();
		Call call = okhttp.newCall(request);
		call.enqueue(new Callback() {

			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				System.out.println("arg0:" + arg0);
				System.out.println("arg1:" + arg1);
			}

			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub

				String res = arg1.body().string();
				System.out.println("res2:" + res);
				com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(res);

				openid = jsonObject.getString("openid");
				System.out.println("openid3:" + openid);

			}

		});
		return openid;
	}



	// 医生登陆
	public String doctorLogin(Object[] params, String code) {

		return dao.doctorLogin(params);
	}

	public String peopleLogin(Object[] params) {
		// TODO Auto-generated method stub
		return dao.peopleLogin(params);
	}

	public String getToken() {
		// TODO Auto-generated method stub
		return dao.getToken();
	}

	public String offlineMessage(Object[] params) {
		// TODO Auto-generated method stub
		return dao.offlineMessage(params);
	}

}