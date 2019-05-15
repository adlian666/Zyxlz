package cn.zyxlz.wechat.utils;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;

import cn.zyxlz.wechat.dao.Dao;
import cn.zyxlz.wechat.dao.impl.DaoImpl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Time extends TimerTask {
	Dao dao = new DaoImpl();

	@Override
	public void run() {
		try {
			// 后台定时运行
			OkHttpClient okhttp = new OkHttpClient();
			Request request = new Request.Builder().get().url(
					"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxea1a33993b78bd88&secret=c8b417ed45b986c18ad3ab4291701b58")

					.build();
			Call call = okhttp.newCall(request);
			call.enqueue(new Callback() {

				public void onFailure(Call arg0, IOException arg1) {
					// TODO Auto-generated method stub

				}

				public void onResponse(Call arg0, Response arg1) throws IOException {
					// TODO Auto-generated method stub
					 String res = arg1.body().string();
					System.out.println(res);
					  final com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(res);
					 String  token = jsonObject.getString("access_token");
					 System.out.println(token);
					dao.token(token);
				}

			});
			System.out.println(new Date());// 定时打印当前时间
			

		} catch (Exception e) {

			// TODO: handle exception

			e.printStackTrace();

		}

	}

}
