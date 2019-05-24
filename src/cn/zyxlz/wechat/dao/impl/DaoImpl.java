package cn.zyxlz.wechat.dao.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.zyxlz.wechat.bean.ArticleBean;
import cn.zyxlz.wechat.bean.ArticleDoctorBean;
import cn.zyxlz.wechat.bean.DoctorBean;
import cn.zyxlz.wechat.bean.HospitalBean;
import cn.zyxlz.wechat.bean.IsfocusedBean;
import cn.zyxlz.wechat.bean.OfflineMessageBean;
import cn.zyxlz.wechat.bean.PeopleBean;
import cn.zyxlz.wechat.bean.PeopleCaseBean;
import cn.zyxlz.wechat.bean.PeopleCollectionBean;
import cn.zyxlz.wechat.bean.SendTemplateMessage;
import cn.zyxlz.wechat.bean.TemplateData;
import cn.zyxlz.wechat.bean.TokenBean;
import cn.zyxlz.wechat.dao.Dao;
import cn.zyxlz.wechat.utils.C3P0Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DaoImpl implements Dao {
	JSONObject js;

	// 获取所有医生列表
	public String findAll() {
		List<DoctorBean> doctor;
		Map map = new HashMap();
		String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			/*String sql = "select  medicinedoctor.*,hospitalorganization.HospitalOrganizationName,hospitallist.HospitalName\r\n"
					+ "FROM medicinedoctor left join hospitalorganization on medicinedoctor.HospitalOrganizationCode = hospitalorganization.HospitalOrganizationCode\r\n"
					+ "left join hospitallist on hospitalorganization.HospitalCode = hospitallist.HospitalCode;\r\n";*/
			String sql = "select  medicinedoctor.*,worktime.*,hospitalorganization.HospitalOrganizationName,hospitallist.HospitalName\r\n"
					+ "FROM medicinedoctor left join hospitalorganization on medicinedoctor.HospitalOrganizationCode = hospitalorganization.HospitalOrganizationCode\r\n"
					+ "left join hospitallist on hospitalorganization.HospitalCode = hospitallist.HospitalCode\r\n"
			+ "left join worktime on medicinedoctor.GUIDMan = worktime.GUIDMan";
			doctor = qr.query(sql, new BeanListHandler<DoctorBean>(DoctorBean.class));
			if (doctor.size() != 0) {
				map.put("Result", "success");
				map.put("Doctors", doctor);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);

	}

	// 获取文章列表
	public String getArticles(String str) {
		List<ArticleDoctorBean> article;
		Map map = new HashMap();
		String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "SELECT article.*,medicinedoctor.ManName,articlestatus.*"
					+ " FROM article left join medicinedoctor on article.GUIDMan = medicinedoctor.GUIDMan"
					+ " left join articlestatus on article.GUIDArticleStatus = articlestatus.GUIDArticleStatus"
					+ " where article.ArticleTypeCode = '" + str + "'";
			article = qr.query(sql, new BeanListHandler<ArticleDoctorBean>(ArticleDoctorBean.class));
			if (article.size() != 0) {
				map.put("Result", "success");
				map.put("Articles", article);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);

	}

	public String postUserInfo(Object[] params) {
		try {
			List<PeopleBean> people;
			Map map = new HashMap();
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			// 添加患者
			String sql = "insert into people(GUIDPeople,PeopleCode,WechatnickName,gender,city,province,country,avatarUrl) values(?,?,?,?,?,?,?,?)";
			qr.update(sql, params);
			System.out.print(params[0]);

			// 返回患者信息
			Object s = params[0];
			String sql1 = "select * from people where GUIDPeople = '" + s + "'";

			people = qr.query(sql1, new BeanListHandler<PeopleBean>(PeopleBean.class));
			if (people.size() != 0) {
				map.put("Result", "success");
				map.put("People", people);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);
	}

	// 查询医院列表
	public String queryHospitalList() {
		List<HospitalBean> hospital;
		Map map = new HashMap();
		String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "select  hospitallist.*,hospitalorganization.* from hospitallist left join hospitalorganization on hospitallist.HospitalCode =  hospitalorganization.HospitalCode;";

			hospital = qr.query(sql, new BeanListHandler<HospitalBean>(HospitalBean.class));
			if (hospital.size() != 0) {
				map.put("Result", "success");
				map.put("Hospital", hospital);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);
	}

	// 根据医院科室找医生
	public String queryOrganizationDoctor(String str) {
		List<DoctorBean> doctor;
		Map map = new HashMap();
		String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "SELECT * from medicinedoctor WHERE HospitalOrganizationCode = '" + str + "'";

			doctor = qr.query(sql, new BeanListHandler<DoctorBean>(DoctorBean.class));
			if (doctor.size() != 0) {
				map.put("Result", "success");
				map.put("Doctor", doctor);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);
	}

	// 上传附件
	public String uploadCase(PeopleCaseBean peoplecase) {

		Map map = new HashMap();
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "INSERT INTO peoplecaselist (GUIDCase,PeopleCode,CaseTime,CaseContent,CaseAttachment,AcceptDoctorCode,"
					+ "AcceptTime,ReleaseTime)VALUES (?,?,?,?,?,?,?,?)";
			Object[] obj = { peoplecase.getGUIDCase(),  peoplecase.getPeopleCode(),
					peoplecase.getCaseTime(), peoplecase.getCaseContent(), peoplecase.getCaseAttachment(),
					peoplecase.getAcceptDoctorCode(), peoplecase.getAcceptTime(), peoplecase.getReleaseTime() };
			 int q = qr.update(sql, obj);
			 if (q > 0) {
					map.put("Result", "success");
					
				}

				else {
					map.put("Result", "fail");
				}

				js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);

	}

	// 搜索医生文章列表
	public String searchDoctor(String str) {
		List<DoctorBean> doctor;
		List<ArticleDoctorBean> article;
		Map map = new HashMap();
		// String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "select  medicinedoctor.*,hospitalorganization.HospitalOrganizationName,hospitallist.HospitalName\r\n"
					+ "FROM medicinedoctor left join hospitalorganization on medicinedoctor.HospitalOrganizationCode = hospitalorganization.HospitalOrganizationCode\r\n"
					+ "left join hospitallist on hospitalorganization.HospitalCode = hospitallist.HospitalCode \r\n"
					+ "WHERE  CONCAT (medicinedoctor.ManWeChat,medicinedoctor.ManName,medicinedoctor.ManDiagnose,hospitalorganization.HospitalOrganizationName,hospitallist.HospitalName)\r\n"
					+ "LIKE '" + "%" + str + "%" + "'";

			String sql1 = " SELECT article.*,medicinedoctor.ManName,medicinedoctor.ManDiagnose,articlestatus.*\r\n"
					+ "	FROM article left join medicinedoctor on article.GUIDMan = medicinedoctor.GUIDMan\r\n"
					+ "	left join articlestatus on article.GUIDArticleStatus = articlestatus.GUIDArticleStatus\r\n"
					+ "	WHERE  CONCAT (medicinedoctor.ManName,medicinedoctor.ManDiagnose,article.ArticleTitle,article.ArticleContent)\r\n"
					+ "	LIKE '" + "%" + str + "%" + "'";
			doctor = qr.query(sql, new BeanListHandler<DoctorBean>(DoctorBean.class));
			article = qr.query(sql1, new BeanListHandler<ArticleDoctorBean>(ArticleDoctorBean.class));
			if (article.size() != 0 || doctor.size() != 0) {
				map.put("Result", "success");
				map.put("Articles", article);
				map.put("Doctors", doctor);
			}

			else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);
	}

	public void token(String str) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "update wechatservice set token='" + str + "' WHERE GUIDToken = 1;";
			qr.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 医生登陆
	public String doctorLogin(Object[] params) {
		List<DoctorBean> doctor;
		Map map = new HashMap();
		String jsonString2 = null;

		try {
			// params[0] = openid;
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "insert ignore  into medicinedoctor(GUIDMan,ManCode,ManWeChat,ManNation,ManHeadimg)\r\n"
					+ "VALUES(?,?,?,?,?)";
			String sql1 = "SELECT * from medicinedoctor WHERE GUIDMan = '" + params[0] + "'";

			qr.update(sql, params);
			doctor = qr.query(sql1, new BeanListHandler<DoctorBean>(DoctorBean.class));
			if (doctor.size() != 0) {
				map.put("Result", "success");
				map.put("Doctors", doctor);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(js));
		return JSON.toJSONString(js);
	}

	// 我是患者登陆
	public String peopleLogin(Object[] params) {
		List<PeopleBean> people;
		Map map = new HashMap();
		String jsonString2 = null;

		try {

			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "insert ignore into people(GUIDPeople,PeopleCode,PeopleWeChat,country,avatarUrl)\r\n"
					+ "VALUES(?,?,?,?,?)";
			String sql1 = "SELECT * from people WHERE GUIDPeople = '" + params[0] + "'";

			qr.update(sql, params);
			people = qr.query(sql1, new BeanListHandler<PeopleBean>(PeopleBean.class));
			if (people.size() != 0) {
				map.put("Result", "success");
				map.put("People", people);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(js));
		return JSON.toJSONString(js);

	}

	// 获取token
	public String getToken() {
		List<TokenBean> token;
		Map map = new HashMap();
		try {

			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

			String sql = "SELECT * FROM wechatservice";

			token = qr.query(sql, new BeanListHandler<TokenBean>(TokenBean.class));
			if (token.size() != 0) {
				map.put("Result", "success");
				map.put("Token", token);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(js));
		return JSON.toJSONString(js);
	}

	// 接收离线消息
	public String offlineMessage(Object[] params) {
		List<OfflineMessageBean> OfflineMessage;
		Map map = new HashMap();
		String jsonString2 = null;

		try {

			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "insert into offlinemessage(fromPuInnerCode,fromUserName,toPuInnerCode,toUserName,content,createTime,chatId,sign)\r\n"
					+ "VALUES(?,?,?,?,?,?,?,?)";
			qr.update(sql, params);
			map.put("Result", "success");
			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(js));
		return JSON.toJSONString(js);
	}

	// 发送模板消息
	public String sendMessage(String openId, String template_id, String page, String form_id, String token) {
		SendTemplateMessage sendTemplateMessage = new SendTemplateMessage();
		sendTemplateMessage.setTouser(openId);
		sendTemplateMessage.setTemplate_id(template_id);
		sendTemplateMessage.setPage(page);
		sendTemplateMessage.setForm_id(form_id);
		sendTemplateMessage.setEmphasis_keyword("");
		Map<String, TemplateData> map = new HashMap<String, TemplateData>();
		Map<String, Object> map1 = new HashMap<String, Object>();

		TemplateData temp = new TemplateData();
		map.put("keyword1", new TemplateData("2018年9月30日16:33:44"));
		map.put("keyword2", new TemplateData("2018年9月30日16:33:44"));
		map.put("keyword3", new TemplateData("***总部"));
		map.put("keyword4", new TemplateData("*****学院"));
		// sendTemplateMessage.setData(map);
		map1.put("touser", openId);
		map1.put("template_id", template_id);
		map1.put("page", "pages/index/index");
		map1.put("form_id", form_id);
		map1.put("data", map);
		map1.put("emphasis_keyword", "");

		// sendTemplateMessage.setData(map);

		js = new JSONObject(map1);
		// String json = JSONObject.toJSONString(sendTemplateMessage);
		System.out.println("json数据" + js);
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
				String.valueOf(js));
		String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
		OkHttpClient okhttp = new OkHttpClient();
		Request request = new Request.Builder().url(SEND_TEMPLATE_MESSAGE + token).post(requestBody).build();
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
				String errcode = jsonObject.getString("errcode");

			}

		});
		return null;
	}

	// 加关注/取消关注
	public String focusDoctor(String peopleCode, String doctorCode) {
		String GUIDFocus = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		List<IsfocusedBean> Isfocused;
		Map map = new HashMap();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		String datetime = df.format(new Date());
		Object[] params = { GUIDFocus, peopleCode, doctorCode, datetime };
		try {
			QueryRunner qr =  new QueryRunner(C3P0Utils.getDataSource());
			String query1 = "SELECT * from peoplefocuslist WHERE PeopleCode = '" + peopleCode + "' and FocusDoctorCode ='" + doctorCode + "'";
			List<IsfocusedBean> q = qr.query(query1,new BeanListHandler<IsfocusedBean>(IsfocusedBean.class));
			if(q.size() == 0) {
				String sql = "insert into peoplefocuslist  (GUIDFocus,PeopleCode,FocusDoctorCode,FocusTime)VALUES(?,?,?,?)";
				int r = qr.update(sql, params);
				if (r > 0) {
					map.put("Result", "关注成功");
				} else {
					map.put("Result", "关注失败");
				}
			}else {
				String delete = "delete from peoplefocuslist WHERE PeopleCode = '" + peopleCode + "' and FocusDoctorCode ='" + doctorCode + "'";
				int d = qr.update(delete);
				if (d > 0) {
					map.put("Result", "取消关注成功");
				} else {
					map.put("Result", "取消关注失败");
				}
			}
			
			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(js));
		return JSON.toJSONString(js);
	}

	// 查询医生是否被关注
	public String isFocused(String peopleCode, String doctorCode) {
		List<IsfocusedBean> Isfocused;
		Map map = new HashMap();
		Object[] params = { peopleCode, doctorCode };
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "SELECT * from peoplefocuslist WHERE PeopleCode = '" + peopleCode + "' and FocusDoctorCode = '" + doctorCode + "'";
			Isfocused = qr.query(sql, new BeanListHandler<IsfocusedBean>(IsfocusedBean.class));
			if (Isfocused.size() > 0) {
				map.put("Result", "已关注");
			} else {
				map.put("Result", "未关注");
			}
			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(js));
		return JSON.toJSONString(js);
	}
//完善医生信息
	public String postDoctorInfo(Object[] doctorinfo, String manCode,String hospital) {
		Map map = new HashMap();
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "update medicinedoctor  set ReleaseTime=?,Hospital=?,HospitalDepartment=?,ManName=?,ManName=?,ProfessionalTitle=?,\r\n"
					+ "				ManMail=?,ManMobilePhone=?,ManIDCard=?,ManResume=?,ManDiagnose=?,ManAchievement=?,ManAward=?,ManNation=?,\r\n"
					+ "				WorkTime=?,OpenId=? WHERE ManCode = '" + manCode
					+ "'";
		  
			
			int d = qr.update(sql, doctorinfo);
			if(d>0) {
				map.put("Result", "success");
			} else {
				map.put("Result", "fail");
			}
			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(js));
		return JSON.toJSONString(js);
	}
//完善患者信息
	public String postPeopleInfo(Object[] peopleinfo, String peopleCode) {
		Map map = new HashMap();
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "update people SET PeopleName =?,gender=?,PeopleNation=?,phoneNumber=?,OpenId=?  "
					+ "WHERE PeopleCode = '" + peopleCode+ "'";
					

			int d = qr.update(sql,peopleinfo);
			if(d>0) {
				map.put("Result", "success");
			} else {
				map.put("Result", "fail");
			}
			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(js));
		return JSON.toJSONString(js);
	}
//查询我的医生
	public String queryMyDoctor(String peopleCode) {
		List<DoctorBean> doctor;
		Map map = new HashMap();
		String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
				String sql = "select  medicinedoctor.*,worktime.*,hospitalorganization.HospitalOrganizationName,hospitallist.HospitalName\r\n" + 
						"					FROM medicinedoctor left join hospitalorganization on medicinedoctor.HospitalOrganizationCode = hospitalorganization.HospitalOrganizationCode\r\n" + 
						"					left join hospitallist on hospitalorganization.HospitalCode = hospitallist.HospitalCode\r\n" + 
						"			left join worktime on medicinedoctor.GUIDMan = worktime.GUIDMan\r\n" + 
						"					where medicinedoctor.ManCode = (   SELECT FocusDoctorCode  FROM peoplefocuslist where PeopleCode = '" + peopleCode + "')";
			doctor = qr.query(sql, new BeanListHandler<DoctorBean>(DoctorBean.class));
			if (doctor.size() != 0) {
				map.put("Result", "success");
				map.put("Doctors", doctor);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);
	}
//查询患者个人信息
	public String queryPeopleInfo(String peopleCode) {
		List<PeopleBean> people;
		Map map = new HashMap();
		String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
				String sql = "select * from people where PeopleCode = "+peopleCode;
				people = qr.query(sql, new BeanListHandler<PeopleBean>(PeopleBean.class));
			if (people.size() != 0) {
				map.put("Result", "success");
				map.put("People", people);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);
	}

	//查询患者病历
	public String queryPeopleCase(String peopleCode) {
		List<PeopleCaseBean> peopleCase;
		Map map = new HashMap();
		String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
				String sql = "SELECT * FROM `peoplecaselist` WHERE PeopleCode = '"+peopleCode+"'";
				peopleCase = qr.query(sql, new BeanListHandler<PeopleCaseBean>(PeopleCaseBean.class));
			if (peopleCase.size() != 0) {
				map.put("Result", "success");
				map.put("PeopleCase", peopleCase);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);
	}
//收藏文章
	public String collectArticle(String gUIDPeople, String gUIDArticle) {
		List<PeopleCollectionBean> peopleCollection;
		Map map = new HashMap();
		String GUIDCollection = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		Object[] params = { GUIDCollection,gUIDPeople, gUIDArticle ,new Date()};
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "SELECT * from people_collection WHERE GUIDPeople = '" + gUIDPeople + "' and GUIDArticle = '" + gUIDArticle + "'";
			peopleCollection = qr.query(sql, new BeanListHandler<PeopleCollectionBean>(PeopleCollectionBean.class));
			if(peopleCollection.size() == 0) {
				String sql1 = "insert into people_collection  (GUIDCollection,GUIDPeople,GUIDArticle,collect_time)VALUES(?,?,?,?)";
				int r = qr.update(sql1, params);
				if (r > 0) {
					map.put("Result", "收藏成功");
				} else {
					map.put("Result", "收藏失败");
				}
			}else {
				
				String delete = "delete from people_collection WHERE GUIDPeople = '" + gUIDPeople + "' and GUIDArticle ='" + gUIDArticle + "'";
				int d = qr.update(delete);
				if (d > 0) {
					map.put("Result", "取消收藏成功");
				} else {
					map.put("Result", "取消收藏失败");
				}
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSON.toJSONString(js));
		return JSON.toJSONString(js);
	}
//查询该文章是否收藏
	public String queryPeopleCollection(String gUIDPeople) {
		List<ArticleDoctorBean> article;
		Map map = new HashMap();
		String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "SELECT article.*,medicinedoctor.ManName,articlestatus.*"
					+ " FROM article left join medicinedoctor on article.GUIDMan = medicinedoctor.GUIDMan"
					+ " left join articlestatus on article.GUIDArticleStatus = articlestatus.GUIDArticleStatus"
					+ " where article.GUIDArticle = (SELECT GUIDArticle FROM people_collection WHERE GUIDPeople = '"+gUIDPeople+"')";
			article = qr.query(sql, new BeanListHandler<ArticleDoctorBean>(ArticleDoctorBean.class));
			if (article.size() != 0) {
				map.put("Result", "success");
				map.put("Articles", article);

			} else {
				map.put("Result", "fail");
			}

			js = new JSONObject(map);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(js);
	}

}
