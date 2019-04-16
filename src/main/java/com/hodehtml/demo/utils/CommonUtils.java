package com.hodehtml.demo.utils;

import com.hodehtml.demo.vo.EduException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;

public class CommonUtils {
	private static final Log log = LogFactory.getLog(CommonUtils.class);

	private static final String CARD_ID = null;
	private static final String BANK_ACCOUNT = null;
	private static final String USER_TOKEN = null;
	private static final String LOGIN_PWD = null;
	
	private static Random rand = null;

	private static SecureRandom secureRand = null;

	private static String localhost = "";

	static {
		secureRand = new SecureRandom();
		rand = new Random(secureRand.nextLong());
	}

	public static String getTraceInfo() {
		StringBuffer stringBuffer = new StringBuffer();
		StackTraceElement stackTraceElement = (new Throwable()).getStackTrace()[1];
		stringBuffer.append("(");
		stringBuffer.append("className:").append(
				stackTraceElement.getClassName());
		stringBuffer.append(";fieldName:").append(
				stackTraceElement.getFileName());
		stringBuffer.append(";methodName:").append(
				stackTraceElement.getMethodName());
		stringBuffer.append(";lineNumber:").append(
				stackTraceElement.getLineNumber());
		stringBuffer.append(")");
		return stringBuffer.toString();
	}

	public static String getTraceInfo(Throwable e) {
		StringBuffer stringBuffer = new StringBuffer();
		StackTraceElement stackTraceElement = e.getStackTrace()[0];
		stringBuffer.append("(");
		stringBuffer.append("className:").append(
				stackTraceElement.getClassName());
		stringBuffer.append(";fieldName:").append(
				stackTraceElement.getFileName());
		stringBuffer.append(";methodName:").append(
				stackTraceElement.getMethodName());
		stringBuffer.append(";lineNumber:").append(
				stackTraceElement.getLineNumber());
		stringBuffer.append(")");
		return stringBuffer.toString();
	}

	// 获得随机数
	public static int getRandom(int accuracy) {
		return (int) (Math.random() * accuracy);
	}

	public static String getUUID() {

		return UUID.randomUUID().toString();
	}

	public static String getRandom() {

		String str = MD5.md5(getUUID() + System.currentTimeMillis()
				+ getRandom(999999999) + rand.nextLong() + localhost);
		str = str.toLowerCase();
		return str;
	}

	public static String getRandom(String string) {

		String str = MD5.md5(getUUID() + System.currentTimeMillis()
				+ getRandom(999999999) + rand.nextLong() + localhost + string);
		str = str.toLowerCase();

		return str;
	}

	public static String encodeBase64String(String data) {
		return Base64.encode(data.getBytes()).trim();
	}

	public static String hiddenMobile(String mobile) {
		if (ValidatorUtils.isNull(mobile) || mobile.length() < 11) {
			return mobile;
		}
		return mobile.substring(0, 3) + "******" + mobile.substring(9, 11);
	}
	
	public static String hiddenBankAccount(String account) {
		return account.substring(0, 6) + "******" + account.substring(account.length()-4);
	}

	public static String hiddenNickName(String nickName) {
		if (ValidatorUtils.isNull(nickName)) {
			return nickName;
		}
		if (nickName.length() < 7) {
			return nickName.substring(0, 1) + "****";
		}
		return nickName.substring(0, 1) + "****"
				+ nickName.substring(nickName.length() - 1, nickName.length());
	}

	/**
	 * 用于生成红包编号字符串
	 */
	public static final String REWARD_NUMBER_RANDOM = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";

	/**
	 * 获取红包编号
	 * 
	 * @return
	 */
	public static String getRewardNo() {
		Random random = new Random();
		StringBuffer result = new StringBuffer("QTYD");
		for (int i = 0; i < 10; i++) {
			result.append(REWARD_NUMBER_RANDOM.charAt(random.nextInt(34)));
		}
		return result.toString();
	}

	/**
	 * 登录密码处理
	 * 
	 * @param password
	 * @return
	 */
	public static String loginPwd(String password) {
		return MD5.md5("@)!%" + password + ")!!$");
	}

	/**
	 * 支付密码处理
	 * 
	 * @param password
	 * @return
	 */
	public static String payPwd(String password) {
		return MD5.md5("@)$!" + password + "^&*()");
	}

	/**
	 * 短信验证码
	 * 
	 * @return
	 */
	public static String smsCode() {
		StringBuffer rand = new StringBuffer("");
		while (rand.toString().length() < 6) {
			rand.append((new Random()).nextInt(10));
		}
		return rand.toString();
	}

	/**
	 * 获得16位的记录编号
	 * 
	 * @return
	 */
	public static String getRecordNo() {
		String date = (new Date()).getTime() + "";
		date = date.substring(date.length() - 3);
		return TimeUtils.getSysTimeLong().substring(2) + date
				+ (new Random()).nextInt(10);
	}

	public static String getDesKey() {
		String md5 = MD5.md5((new Date()).getTime() + "");
		return md5.substring(8, 16).toUpperCase();
	}


	public static String birthday(String cardId) {
		if (cardId.length() == 18) {
			return cardId.substring(6, 10) + "-" + cardId.substring(10, 12)
					+ "-" + cardId.substring(12, 14);
		}
		return "";
	}

	public static String encodeCardId(String cardId) {
		return DesUtils.encrypt(cardId, CARD_ID);
	}

	public static String decodeCardId(String cardId) throws EduException {
		try{
			return DesUtils.decrypt(cardId, CARD_ID);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("身份证信息"+cardId+"("+CARD_ID+")解密失败",e);
			throw new EduException(110075);
		}
	}
	
	public static String encodeBankAccount(String bankAccount) {
		return DesUtils.encrypt(bankAccount, BANK_ACCOUNT);
	}

	public static String decodeBankAccount(String bankAccount) throws EduException {
		try{
			return DesUtils.decrypt(bankAccount, BANK_ACCOUNT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("银行卡信息"+bankAccount+"("+BANK_ACCOUNT+")解密失败",e);
			throw new EduException(110075);
		}
	}

	public static String encodeUserToken(String redisKey) {
		return DesUtils.encrypt(redisKey, USER_TOKEN);
	}

	public static String decodeUserToken(String redisKey) throws EduException {
		try {
			return DesUtils.decrypt(redisKey, USER_TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("用户令牌"+redisKey+"("+USER_TOKEN+")解密失败",e);
			throw new EduException(110075);
		}
	}

	public static String encodeLoginPwd(String redisKey) {
		return MD5.md5(redisKey+LOGIN_PWD);
	}

	public static Map<String, String> pageInfo(int currentPage, int pageSize,
			int totalRows) {
		int totalPage = 1;
		if (totalRows > 0) {
			totalPage = (totalRows % pageSize == 0) ? (totalRows / pageSize)
					: ((totalRows / pageSize) + 1);
		}
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		Map<String, String> result = new LinkedHashMap<String, String>();
		result.put("total_count", totalRows + "");
		result.put("total_page", totalPage + "");
		result.put("page_size", pageSize + "");
		result.put("current_page", currentPage + "");
		return result;
	}

	/**
	 * 给集合中间嵌入一层
	 * 
	 * @param dbResult
	 *            集合
	 * @param key
	 *            嵌入名称
	 * @return
	 */
	public static List<Map<String, Object>> convertDataMapToApiMap(
			List<Map<String, Object>> dbResult, String key) {
		if (dbResult == null) {
			return null;
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> obj = null;
		for (Map<String, Object> map : dbResult) {
			obj = new HashMap<String, Object>();
			obj.put(key, map);
			result.add(obj);
		}
		return result;
	}

	/**
	 * 给集合移除嵌入的一层
	 * 
	 * @param dbResult
	 *            集合
	 * @param key
	 *            嵌入名称
	 * @return
	 */
	public static List<Map<String, Object>> removeNesting(
			List<Map<String, Object>> dbResult, String key) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(ValidatorUtils.isEmpty(dbResult)){
			return list ;
		}
		for (Map<String, Object> map : dbResult) {
			@SuppressWarnings("unchecked")
			Map<String,Object> result = (Map<String, Object>) map.get(key);
			list.add(result);
		}
		return list;
	}

	public static int howOld(String cardId) {
		if (cardId == null || "".equals(cardId)) {
			return 0;
		}
		int len = cardId.length();
		if (len == 18) {
			cardId = cardId.substring(6, 14);
		} else if (len == 15) {
			cardId = "19" + cardId.substring(6, 12);
		}
		String oldYear = cardId.substring(0, 4);
		String md = cardId.substring(4, 8);
		String now = TimeUtils.getSysdateInt();
		String nowYear = now.substring(0, 4);
		String nmd = now.substring(4, 8);
		int age = Integer.parseInt(nowYear) - Integer.parseInt(oldYear);
		if (md.compareTo(nmd) > 0) {
			age = age - 1;
		}
		return age;
	}

	public static String round(String value, int digits) {
		return round(Double.valueOf(value), digits);
	}

	public static String round(double value, int digits) {
		return String.format("%." + digits + "f", value);
		/*
		 * BigDecimal bg = new BigDecimal(value); double result =
		 * bg.setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();
		 * java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		 * nf.setGroupingUsed(false); nf.setMinimumFractionDigits(digits);
		 * return nf.format(result);
		 */
	}

	public static String roundDown(double value, int digits) {
		BigDecimal bg = new BigDecimal(value);
		double result = bg.setScale(digits, BigDecimal.ROUND_DOWN)
				.doubleValue();
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumFractionDigits(digits);
		return nf.format(result);
	}

	public static double add(double v1, double v2) {
		String tp = round(v1, 2);
		String tp2 = round(v2, 2);
		double result = Double.valueOf(tp) + Double.valueOf(tp2);
		return Double.valueOf(CommonUtils.round(result, 2));
	}

	public static double add(String v1, String v2) {
		double result = Double.valueOf(v1) + Double.valueOf(v2);
		return Double.valueOf(CommonUtils.round(result, 2));
	}

	public static double sub(double v1, double v2) {
		String tp = round(v1, 2);
		String tp2 = round(v2, 2);
		double result = Double.valueOf(tp) - Double.valueOf(tp2);
		return Double.valueOf(CommonUtils.round(result, 2));
	}

	public static double sub(String v1, String v2) {
		double result = Double.valueOf(v1) - Double.valueOf(v2);
		return Double.valueOf(CommonUtils.round(result, 2));
	}

	public static int getWordLength(String str) {
		str = StringUtils.nullToStrTrim(str);
		return str.replaceAll("[^\\x00-\\xff]", "**").length();
	}

	public static int getRealLength(String str) {

		return getRealLength(str, Constants.CHARSETNAME_DEFAULT);
	}

	/**
	 * 获得标的期数
	 * 
	 * @param borrowName
	 *            标全名
	 * @param bracketsFlg
	 *            新的标题是否需要携带左右括弧
	 */
	public static String getBorrowTitle(String borrowName, boolean bracketsFlg) {
		if (borrowName.indexOf("【") > 0) {
			int end = borrowName.length();
			if (borrowName.indexOf("】") > 0) {
				end = borrowName.indexOf("】");
			}
			borrowName = borrowName.substring(borrowName.indexOf("【") + 1, end);
		}
		if (bracketsFlg) {
			borrowName = "【" + borrowName + "】";
		}
		return borrowName;
	}

	public static int getRealLength(String str, String charsetName) {

		str = StringUtils.nullToStrTrim(str);

		if (ValidatorUtils.isNull(str)) {
			return 0;
		}

		try {
			return str.getBytes(charsetName).length;
		} catch (UnsupportedEncodingException e) {
			log.error(getTraceInfo() + " str:" + str + "  charsetName:"
					+ charsetName + "\t"
					+ StringUtils.nullToStrTrim(e.getMessage()));
			return 0;
		}
	}

	public static void main(String[] args) throws EduException {
		/*
		 * String accessid = getRandom("123121321");
		 * System.out.println(accessid); String accesskey =
		 * encodeBase64String(getRandom(accessid));
		 * System.out.println(accesskey);
		 */
//		System.out.println(smsCode());
	//	System.out.println(decodeCardId("74484227b0cc975284043bc5d4164bd501f86094f30a925e"));
		//System.out.println(decodeCardId("b1bf79278319f5f2a1a2f729ff57df80"));
		System.out.println(encodeBankAccount("6228480329513985872"));
	}

}
