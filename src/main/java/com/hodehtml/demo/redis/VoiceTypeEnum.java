package com.hodehtml.demo.redis;


public enum VoiceTypeEnum {
	
	//还款当天提醒
	REPAY_BEFORE_0_DAY(0,"12:00:00","当日到期","尊敬的用户[#username#]您好，您在蓝莓众信的[#loan_capital#]元借款今天到期，请保持银行卡余额充足或手动还款，还可使用展期服务，如已还清请忽略，感谢您的接听，祝你生活愉快，再见！"),
	OVER_DUE_1_DAY(1,"09:00:00","逾期1天","尊敬的用户[#username#]您好，您在[#platform#]的[#loan_capital#]元借款已逾期[1]天，为避免个人信用受损，请于今天12点前处理，如已还清请忽略，感谢您的接听，祝你生活愉快，再见！"),
	OVER_DUE_2_DAY(2,"09:00:00","逾期2天","尊敬的用户[#username#]您好，您在[#platform#]的[#loan_capital#]元借款已逾期[2]天，为避免个人信用受损，请于今天12点前处理，如已还清请忽略，感谢您的接听，祝你生活愉快，再见！"),
	OVER_DUE_3_DAY(3,"09:00:00","逾期3天","尊敬的用户[#username#]您好，您在[#platform#]的[#loan_capital#]元借款已逾期[3]天，为避免个人信用受损，请于今天12点前处理，如已还清请忽略，感谢您的接听，祝你生活愉快，再见！"),
	FRIEND_OVER_DUE_3_DAY(4,"09:00:00","逾期3天好友提醒","[#username#]您好，手机尾号[#mobile_end#]在[#platform#]借款[#loan_capital#]元，已发生逾期，请及时还款并保持良好的信用。感谢您的接听，如有打扰请谅解，祝您生活愉快，再见！");	
	public int MSG_KEY = 0;
	public String sendTime = "";
	public String MSG_TITLE = "" ;
	public String CONTENT = "" ;
	
	VoiceTypeEnum(int key, String sendTime, String msgTitle, String msgContent){
		MSG_KEY = key ;
		this.sendTime = sendTime;
		MSG_TITLE = msgTitle ;
		CONTENT = msgContent;
	}
}
