package cn.zyxlz.wechat.bean;

import java.util.Date;

public class PeopleCaseBean {
private String GUIDCase;
private String GUIDPeople;
private String PeopleCode;
private Date CaseTime;
private String CaseContent;
private String CaseAttachment;
private String AcceptDoctorCode;
private Date AcceptTime;
private Date ReleaseTime;
public String getGUIDCase() {
	return GUIDCase;
}
public void setGUIDCase(String gUIDCase) {
	GUIDCase = gUIDCase;
}
public String getGUIDPeople() {
	return GUIDPeople;
}
public void setGUIDPeople(String gUIDPeople) {
	GUIDPeople = gUIDPeople;
}
public String getPeopleCode() {
	return PeopleCode;
}
public void setPeopleCode(String peopleCode) {
	PeopleCode = peopleCode;
}
public Date getCaseTime() {
	return CaseTime;
}
public void setCaseTime(Date caseTime) {
	CaseTime = caseTime;
}
public String getCaseContent() {
	return CaseContent;
}
public void setCaseContent(String caseContent) {
	CaseContent = caseContent;
}
public String getCaseAttachment() {
	return CaseAttachment;
}
public void setCaseAttachment(String caseAttachment) {
	CaseAttachment = caseAttachment;
}
public String getAcceptDoctorCode() {
	return AcceptDoctorCode;
}
public void setAcceptDoctorCode(String acceptDoctorCode) {
	AcceptDoctorCode = acceptDoctorCode;
}
public Date getAcceptTime() {
	return AcceptTime;
}
public void setAcceptTime(Date acceptTime) {
	AcceptTime = acceptTime;
}
public Date getReleaseTime() {
	return ReleaseTime;
}
public void setReleaseTime(Date releaseTime) {
	ReleaseTime = releaseTime;
}



}
