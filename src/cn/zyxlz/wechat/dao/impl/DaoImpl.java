package cn.zyxlz.wechat.dao.impl;

import java.sql.SQLException;
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
import cn.zyxlz.wechat.bean.OfflineMessageBean;
import cn.zyxlz.wechat.bean.PeopleBean;
import cn.zyxlz.wechat.bean.PeopleCaseBean;
import cn.zyxlz.wechat.bean.TokenBean;
import cn.zyxlz.wechat.dao.Dao;
import cn.zyxlz.wechat.utils.C3P0Utils;

public class DaoImpl implements Dao {
	JSONObject js;

	// 获取所有医生列表
	public String findAll() {
		List<DoctorBean> doctor;
		Map map = new HashMap();
		String jsonString2 = null;
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "select  medicinedoctor.*,hospitalorganization.HospitalOrganizationName,hospitallist.HospitalName\r\n"
					+ "FROM medicinedoctor left join hospitalorganization on medicinedoctor.HospitalOrganizationCode = hospitalorganization.HospitalOrganizationCode\r\n"
					+ "left join hospitallist on hospitalorganization.HospitalCode = hospitallist.HospitalCode;\r\n";

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
	public void uploadCase(PeopleCaseBean peoplecase) {

		Map map = new HashMap();
		try {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "INSERT INTO peoplecaselist VALUES (?,?,?,?,?,?,?,?,?)";
			Object[] obj = { peoplecase.getGUIDCase(), peoplecase.getGUIDPeople(), peoplecase.getPeopleCode(),
					peoplecase.getCaseTime(), peoplecase.getCaseContent(), peoplecase.getCaseAttachment(),
					peoplecase.getAcceptDoctorCode(), peoplecase.getAcceptTime(), peoplecase.getReleaseTime() };
			qr.update(sql, obj);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public String getToken() {
		List<TokenBean> token;
		Map map = new HashMap();
		try {

			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

			String sql = "SELECT * FROM wechatservice";

			token = qr.query(sql,new BeanListHandler<TokenBean>(TokenBean.class));
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
//接收离线消息
	public String offlineMessage(Object[] params) {
		List<OfflineMessageBean> OfflineMessage;
		Map map = new HashMap();
		String jsonString2 = null;

		try {
			// params[0] = openid;
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

}
