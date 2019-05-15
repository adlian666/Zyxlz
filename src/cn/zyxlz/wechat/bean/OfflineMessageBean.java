package cn.zyxlz.wechat.bean;

public class OfflineMessageBean {
	private String fromPuInnerCode ;
	private String fromUserName;
	private String toPuInnerCode ;
	private String toUserName ;
	private String content  ;
	private String createTime;
	private String chatId ;
	private String sign ;
	public String getFromPuInnerCode() {
		return fromPuInnerCode;
	}
	public void setFromPuInnerCode(String fromPuInnerCode) {
		this.fromPuInnerCode = fromPuInnerCode;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getToPuInnerCode() {
		return toPuInnerCode;
	}
	public void setToPuInnerCode(String toPuInnerCode) {
		this.toPuInnerCode = toPuInnerCode;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
