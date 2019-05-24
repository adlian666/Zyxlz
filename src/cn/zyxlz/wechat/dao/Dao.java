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
String sendMessage(String openId, String template_id, String page, String form_id, String token);
String focusDoctor(String peopleCode, String doctorCode);
String isFocused(String peopleCode, String doctorCode);
String postDoctorInfo(Object[] doctorinfo, String manCode, String hospital);
String postPeopleInfo(Object[] peopleinfo, String peopleCode);


}
