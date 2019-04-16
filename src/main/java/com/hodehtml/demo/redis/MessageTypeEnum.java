package com.hodehtml.demo.redis;


import com.hodehtml.demo.utils.ValidatorUtils;
import com.hodehtml.demo.vo.EduException;

public enum MessageTypeEnum {
	
	//验证码
	REGISTER(0,1,"","注册","您的｛现代吧｝验证码是#code#。如非本人操作，请忽略本短信。"),
	IFROGOT(1,1,"","修改密码","本次密码找回，您的｛现代吧｝验证码是#code#，请勿告知他/她人。"),
	AUTH(2,1,"","额度申请","您的｛现代吧｝额度申请的验证码是#code#，请保存好您的验证码。"),
	//提醒
	RECOMMENDED_REG_SUCCESS(3,0,"oCf9gc9g9Q4CWfozxu8dkQEhbOAPX6PpNWItfxFqvds"
			,"邀请新用户注册成功","恭喜你邀请新用户#login_mobile#，注册成功"),
	RECOMMENDED_LOAN_SUCCESS(4,1,""
			,"粉丝借款成功通知","您的粉丝#loan_mobile#，在现代吧借款成功，借款金额#loan_money#元"),
	RECOMMENDED_REPAY_ON_TIME(5,0,""
			,"代理奖励通知","你的粉丝用户#loan_mobile#按时还款，本次完成借款，奖励#prize_money#元，请登录现代吧查收。"),
	RECOMMENDED_REPAY_OVER_DUE(6,0,""
			,"代理逾期通知","你的粉丝用户#loan_mobile#已逾期，请登录现代吧查看，并督促用户还款，本次逾期将扣除你的奖金#pubish_money#元。"),

	AUTH_COMMIT(7,0,"tV6kAj6pB5vN_tThKgztcwUFnEqDV8kVSR0_VgV2ueE"
			,"资料提交审核通知","您好，您的｛现代吧｝资料已提交成功，审核部门初审通过后，将有人工与您联系，请保持手机畅通。"),
	AUTH_CHECK_SUCCESS(8,0,"dxjH-w7D0U_jsMlajPZC4ySOa5hzWXqeQmy0G3_n_eM"
			,"审核成功通知","您的｛现代吧｝资料审核通过，当前额度为#credit_money#元，您可以立即提现使用了！"),
	AUTH_CHECK_FAILURE(9,0,"dxjH-w7D0U_jsMlajPZC4ySOa5hzWXqeQmy0G3_n_eM"
			,"审核失败通知","您的｛现代吧｝资料审核未通过，请登录现代吧APP/微信公众号查看原因"),
	AGENT_WITHDRAW_GAP_REMIND(10,1,""
			,"提现奖励金提醒","活动至今你的｛现代吧｝账户已获得累计可提现奖励金#prize_money#元，钱包金额#withdraw_min#元可提现。再加油哦！奖励详情可在“钱包”页面点击查看。"),	
	LOAN_SUCCESS(11,0,""
			,"借款成功提醒","您｛现代吧｝成功借款#loan_money#元，请注意查收银行信息。"),
	
	REPAY_REMIND_BEFORE_3_DAY(12,0,"mAwF1wDtU_7bN4-wTn8PvhjL0f6FXxlttEsez-mop_U"
			,"还款提醒","您的｛现代吧｝借款#repay_money#元于#repay_date#到期，请保证绑定银行卡内余额充足或手动还款，还可使用展期服务。祝您生活愉快。"),
	REPAY_REMIND_BEFORE_1_DAY(13,1,"mAwF1wDtU_7bN4-wTn8PvhjL0f6FXxlttEsez-mop_U"
			,"还款提醒","您的｛现代吧｝借款#repay_money#元于#repay_date#到期，请保证绑定银行卡内余额充足或手动还款，还可使用展期服务。祝您生活愉快。"),
	REPAY_REMIND_BEFORE_0_DAY(13,1,"mAwF1wDtU_7bN4-wTn8PvhjL0f6FXxlttEsez-mop_U"
			,"还款提醒","您的｛现代吧｝借款#repay_money#元于#repay_date#到期，请保证绑定银行卡内余额充足或手动还款，还可使用展期服务。祝您生活愉快。"),
	
	OVER_DUE_1_DAY(14,1,"HEAgS15JOpqO8gII6hasOgs01dCgN9gMXvbHHw-9H0I"
			,"逾期还款提醒","您好，您的｛现代吧｝借款已逾期1天，为避免个人征信及芝麻信用受损，请于今天12点前处理逾期欠款。若已还款无需理会，如有疑问请致电#contact_phone#。"),			
	OVER_DUE_3_DAY(15,1,"HEAgS15JOpqO8gII6hasOgs01dCgN9gMXvbHHw-9H0I"
			,"逾期3天还款提醒","您的｛现代吧｝借款于#repay_date#应还#repay_money#元，已逾期3天，产生额外逾期费用#extra_money#元，请尽快处理欠款，避免信用不良。因您电话不接、短信不回，我司按照流程将告知您紧急联系人。"),
	OVER_DUE_5_DAY(16,1,"HEAgS15JOpqO8gII6hasOgs01dCgN9gMXvbHHw-9H0I"
			,"逾期5天还款提醒","您的｛现代吧｝借款于#repay_date#应还#repay_money#元，已逾期5天，产生额外逾期费用#extra_money#元，请尽快处理欠款，若您继续拒绝还款，我司按照流程将联系您的通讯录名单。"),
	OVER_DUE_7_DAY(17,1,"HEAgS15JOpqO8gII6hasOgs01dCgN9gMXvbHHw-9H0I"
			,"逾期7天还款提醒","您的｛现代吧｝借款于#repay_date#应还#repay_money#元，已逾期7天，产生额外逾期费用#extra_money#元，请尽快处理欠款，若您拒绝还款，我司将提交至芝麻信用，逾期信息在负面信用中无法消除。"),
	OVER_DUE_10_DAY(18,1,"HEAgS15JOpqO8gII6hasOgs01dCgN9gMXvbHHw-9H0I"
			,"逾期10天还款提醒","您的｛现代吧｝借款于#repay_date#应还#repay_money#元，已逾期10天，产生额外逾期费用#extra_money#元，我司已提交芝麻信用，并委托专业机构进行催收，同时保留诉松权利。如有疑问请致电#manage_phone#。"),
	OVER_DUE_15_DAY(19,1,"HEAgS15JOpqO8gII6hasOgs01dCgN9gMXvbHHw-9H0I"
			,"严重逾期通知","您好，#loan_mobile#，由于多次跟您沟通无果，拒绝还款，屡次爽约拖欠。公司决定采取对应措施，提交锦天城律师事务所内给予家庭/公司发出起诉函。详询#contact_phone#。"),
	REPAY_SUCCESS(20,0,"aWTQ8llqLiYusL0Fmoo9P8Em5NkDCcikNQgBPQ5h9j8"
			,"还款成功通知","您的｛现代吧｝本期借款已还清，感谢您的使用。"),
	REPAY_FAILURE(21,0,"aWTQ8llqLiYusL0Fmoo9P8Em5NkDCcikNQgBPQ5h9j8"
			,"还款失败通知","您的｛现代吧｝本期借款账单自动扣款失败，系统将稍后重新发起扣款，或您可手动还款，以免产生逾期。谢谢！");
	
	
	public int MSG_KEY = 0;//消息关键字
	public long SMS_SEND_FLG=0;//短信是否需要发送0不发送 1发送
	public String WEIXIN_TEMPLATE_ID = ""; //微信模板ID
	public String INNER_MSG_TITLE = "" ;  //站内信标题
	public String INNER_MSG_CONTENT = "" ; //站内信内容
	
	MessageTypeEnum(int key, int smsSendFlg, String weixinID, String msgTitle, String msgContent){
		MSG_KEY = key ;
		SMS_SEND_FLG = smsSendFlg;
		WEIXIN_TEMPLATE_ID = weixinID;
		INNER_MSG_TITLE = msgTitle ;
		INNER_MSG_CONTENT = msgContent;
	}
	
	public static void check(String type) throws EduException {
		if(!ValidatorUtils.isDigital(type)){
			throw new EduException(200103);
		}
		int t = Integer.valueOf(type);
		if(t<REGISTER.MSG_KEY||t>AUTH.MSG_KEY){
			throw new EduException(200103);
		}
	}
	
	public static MessageTypeEnum getEnums(String type)throws EduException{
		check(type);
		int t = Integer.valueOf(type);
		if(t==REGISTER.MSG_KEY){
			return REGISTER;
		}else if(t==IFROGOT.MSG_KEY){
			return IFROGOT;
		}else if(t==AUTH.MSG_KEY){
			return AUTH;
		}
		return REGISTER;
	}	
}
