package cn.zyxlz.wechat.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
	
	JSONObject js;
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

	public String uploadCase(PeopleCaseBean peoplecase) {
		return dao.uploadCase(peoplecase);
	}

	public String searchDoctor(String str) {
		// TODO Auto-generated method stub
		return dao.searchDoctor(str);
	}

	// 登陆
	public String login(String str) {
		String  res;
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
				String openid;
				String  res = arg1.body().string();
				System.out.println("res2:" + res);
				com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(res);
			
				openid = jsonObject.getString("openid");
				
				Map map = new HashMap();
				map.put("openid",openid);
				js = new JSONObject(map);
			
				
			}

		});
		return JSON.toJSONString(js);
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
//发送模板消息
	public String sendMessage(String openId, String template_id, String page, String form_id, String token) {
		// TODO Auto-generated method stub
		return dao.sendMessage(openId,template_id,page,form_id,token);
	}
//加关注医生
	public String focusDoctor(String peopleCode, String doctorCode) {
		// TODO Auto-generated method stub
		return dao.focusDoctor(peopleCode,doctorCode);
	}

	public String isFocused(String peopleCode, String doctorCode) {
		// TODO Auto-generated method stub
		return dao.isFocused(peopleCode,doctorCode);
	}

	

	public String postDoctorinfoServlet(Object[] doctorinfo, String manCode,String hospital) {
		// TODO Auto-generated method stub
		return dao.postDoctorInfo(doctorinfo,manCode,hospital);
	}

	public String postPeopleInfo(Object[] peopleinfo, String peopleCode) {
		// TODO Auto-generated method stub
		return dao.postPeopleInfo(peopleinfo,peopleCode);
	}

	public String queryMyDoctor(String peopleCode) {
		// TODO Auto-generated method stub
		return dao.queryMyDoctor(peopleCode);
	}

	public String queryPeopleInfo(String peopleCode) {
		// TODO Auto-generated method stub
		return dao.queryPeopleInfo(peopleCode);
	}

	public String queryPeopleCase(String peopleCode) {
		// TODO Auto-generated method stub
		return dao.queryPeopleCase(peopleCode);
	}

	public String collectArticle(String gUIDPeople, String gUIDArticle) {
		// TODO Auto-generated method stub
		return dao.collectArticle(gUIDPeople,gUIDArticle);
	}

	public String queryPeopleCollection(String gUIDPeople) {
		// TODO Auto-generated method stub
		return dao.queryPeopleCollection(gUIDPeople);
	}

}