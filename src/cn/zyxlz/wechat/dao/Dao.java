package cn.zyxlz.wechat.dao;

import cn.zyxlz.wechat.bean.PeopleCaseBean;

public interface Dao {

String findAll();
String getArticles(String str);
String postUserInfo(Object[] params);
String queryHospitalList();
String queryOrganizationDoctor(String str);
void uploadCase(PeopleCaseBean peoplecase);
String searchDoctor(String str);
void token(String str);
String doctorLogin(Object[] params );
String peopleLogin(Object[] params);
String getToken();
String offlineMessage(Object[] params);
}
