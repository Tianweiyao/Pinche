package com.hodehtml.demo.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;


public class SmsUtil {
	protected static final Log log = LogFactory.getLog(SmsUtil.class);

	public static String url = "http://api.bqsms.com/smsUTF8.aspx";//"http://115.29.242.32:8888/sms.aspx";
	//public static String userid = PropertiesConfig.getProperties("sms","wodong_userid");
	public static String account = "13958873583";
	public static String password = "13958873583";
	public static String prefix = "【"+"现代吧"+"】";

	
	public static void main(String[] args) throws JDOMException, IOException {
	 
	
		
		String a="浙江省,杭州市,下城区";
	
		String string = a.split(",")[1];
		System.out.println(string);
		
		
	System.out.println(send("15381115869", "你的验证码是1234",""));
		//send("15381115869", "尊敬的用户[]您好，您在蓝莓钱包的[]元借款今天到期，请保持银行卡余额充足或手动还款，还可使用展期服务，如已还清请忽略，感谢您的接听，祝你生活愉快，再见！","abc");
	
		
	}
	
	/*
	 * 单条短信发送,智能匹配短信模板
	 * @param apikey 成功注册后登录云片官网,进入后台可查看
	 * @param text 需要使用已审核通过的模板或者默认模板
	 * @return json格式字符串
	 */
	public static boolean send(String mobile,String content,String title) {
//		log.info("电话》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》"+mobile+".。。。。。"+content+title);
			//title不为空，则为语音短信
		if(ValidatorUtils.isNull(title)){
			content = prefix+content;
			//url = url.replace("sendvms", "sms");
		}
		log.info("短信发送mobile:"+mobile+",  content:"+content+",  title:"+title+" url:"+url+" 执行开始...");
		String result = sendSms(url, null, account, password, mobile, content, null,
				null, null, null, null, null, null, "POST", "UTF-8", "UTF-8",title);
		log.info("短信发送mobile:"+mobile+",  title:"+title+"执行结果:"+result);
		if(ValidatorUtils.isNull(result)){
			return false;
		}
		
		try {
			StringReader stringReader = new StringReader(result);
			Document doc = (new SAXBuilder()).build(stringReader);
			Element rootXML = doc.getRootElement();
			String returnstatus = rootXML.getChildText("returnstatus");
//			String balance = rootXML.getChildText("remainpoint");
			if("success".equals(returnstatus.toLowerCase())){
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("短信返回内容解析失败",e);
		}	
		return false;
	}
	
	/**
	 * <p>
	 * <date>2012-03-01</date><br/>
	 * <span>发送信息方法--暂时私有化，这里仅仅是提供用户接口而已。其实用不了那么复杂</span><br/>
	 * <span>发送信息最终的组合形如：http://http://客户端地址:8888/sms.aspx?action=send</span>
	 * </p>
	 * 
	 * @param url
	 *            ：必填--发送连接地址URL--比如>http://客户端地址:8888/sms.aspx
	 * 
	 * @param userid
	 *            ：必填--用户ID，为数字
	 * @param account
	 *            ：必填--用户帐号
	 * @param password
	 *            ：必填--用户密码
	 * @param mobile
	 *            ：必填--发送的手机号码，多个可以用逗号隔比如>13512345678,13612345678
	 * @param content
	 *            ：必填--实际发送内容，
	 * @param action
	 *            ：选填--访问的事件，默认为send
	 * @param sendTime
	 *            ：选填--定时发送时间，不填则为立即发送，时间格式如>2011-11-11 11:11:11
	 * @param checkContent
	 *            ：选填--检查是否包含非法关键字，1--表示需要检查，0--表示不检查
	 * @param taskName
	 *            ：选填--任务名称，本次任务描述，100字内
	 * @param countNumber
	 *            ：选填--提交号码总数
	 * @param mobileNumber
	 *            ：选填--手机号码总数
	 * @param telephoneNumber
	 *            ：选填--小灵通（和）或座机总数
	 * @param sendType
	 *            ：选填--发送方式，默认为POST
	 * @param codingType
	 *            ：选填--发送内容编码方式，默认为UTF-8
	 * @param backEncodType
	 *            ：选填--返回内容编码方式，默认为UTF-8
	 * @return 返回发送之后收到的信息
	 */
	private static String sendSms(String url, String userid, String account,
			String password, String mobile, String content, String action,
			String sendTime, String checkContent, String taskName,
			String countNumber, String mobileNumber, String telephoneNumber,
			String sendType, String codingType, String backEncodType,String title) {
	

		if(content!=null){
			content=content.replace(".00", "");
		}
		

		try {
			content = URLEncoder.encode(content, "UTF-8");
			if (codingType == null || codingType.equals("")) {
				codingType = "UTF-8";
			}
			if (backEncodType == null || backEncodType.equals("")) {
				backEncodType = "UTF-8";
			}
			StringBuffer send = new StringBuffer();
			send.append("&type=").append("send");
			send.append("&username=").append(account);
			send.append("&password=").append(MD5.md5(password));
			send.append("&gwid=").append("a01f686b");
			send.append("&mobile=").append(mobile);
			send.append("&message=").append(content);
			send.append("&dstime=");
			if (sendType != null && (sendType.toLowerCase()).equals("get")) {
				return SmsClientAccessTool.getInstance().doAccessHTTPGet(
						url + "?" + send.toString(), backEncodType);
			} else {
				return SmsClientAccessTool.getInstance().doAccessHTTPPost(url,
						send.toString(), backEncodType);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("短信发送失败",e);
		}
		return null;
	}

	
	
}
