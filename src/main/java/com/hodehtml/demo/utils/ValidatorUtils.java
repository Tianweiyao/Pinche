package com.hodehtml.demo.utils;

import com.hodehtml.demo.vo.EduException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {
	private static final Log log = LogFactory.getLog(ValidatorUtils.class);

	public static boolean isEmpty(String str) {

		return ((str == null) || (str.trim().length() == 0));
	}
	
	public static boolean isNotEmpty(String str) {
		if(str==null){
			return false;
		}

		return ((str != null) && (str.trim().length() > 0)&&!"null".equals(str.trim()));
	}
	
	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") Map result){
		if(result!=null&&!result.isEmpty()&&result.size()>0){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(@SuppressWarnings("rawtypes") Map result){		
		return !isNotEmpty(result);
	}
	
	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") List result){
		if(result!=null&&!result.isEmpty()&&result.size()>0){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(@SuppressWarnings("rawtypes") List result){		
		return !isNotEmpty(result);
	}
	
	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") Set result){
		if(result!=null&&!result.isEmpty()&&result.size()>0){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(@SuppressWarnings("rawtypes") Set result){		
		return !isNotEmpty(result);
	}

	public static boolean isNotNull(String str) {
		if (str == null) {
			return false;
		}
		return ((str != null) && (str.trim().length() > 0) && !"null"
				.equals(str.trim()));
	}
	
	public static boolean isNotNull(Object obj){
		if(obj==null){
			return false;
		}
		return isNotNull(obj+"");
	}
	
	public static boolean isNull(Object obj){
		return !isNotNull(obj);
	}

	public static boolean isNull(String str) {
		return !isNotNull(str);
	}
	
	public static boolean isNotNull(Integer str) {
		if (str == null) {
			return false;
		}
		return true;
	}

	public static boolean isNull(Integer str) {
		return !isNotNull(str);
	}
	
	public static boolean isNotNull(Long str) {
		if (str == null) {
			return false;
		}
		return true;
	}

	public static boolean isNull(Long str) {
		return !isNotNull(str);
	}
	
	public static boolean isNotNull(Date str) {
		if (str == null) {
			return false;
		}
		return true;
	}

	public static boolean isNull(Date str) {
		return !isNotNull(str);
	}
	

	public static boolean isBlank(String str) {

		int length = 0;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}
	
	public static boolean isHalfAngle(String str) {

		str = StringUtils.nullToStrTrim(str);
		return str.length() == CommonUtils.getWordLength(str);
	}
	
	public static boolean isLong(String v){
		if(!isDigital(v)){
			return false;
		}
		if(Long.valueOf(v)<=0){
			return false;
		}
		return true;
	}

	public static boolean isTimeStamp(String timeStamp) {
		   if(!ValidatorUtils.isLong(timeStamp)){
			   // 空 小于0
			   return false;
		   }
		   // 1973-03-03 17:46:40.000000改时间前9位
		   if(timeStamp.length()<10){
			   return false;
		   }
		   return true;
	}
	
	public static boolean checkString(String str, String regex) {

		return str.matches(regex);
	}
	
	public static boolean isMd5(String md5) {

		if (md5.length() != 32) {
			return false;
		}

		return checkString(md5, "[0-9A-Fa-f]+");
	}
	public static boolean validTime(String time, String pattern) {
		
		if (time.length() != 14) {
			return false;
		}

		if (!checkString(time, "[2][0-9]+")) {
			return false;
		}

    	DateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);

		Date date = null;
		try {
			date = (Date) formatter.parse(time);
		} catch (ParseException e) {
			log.error(CommonUtils.getTraceInfo() +" time:"+time+" pattern:"+pattern+"\t"+ StringUtils.nullToStrTrim(e.getMessage()));
			return false;
		}
		return time.equals(formatter.format(date));
	}
	
	public static boolean isInfoCode(String code) {

		code = StringUtils.nullToStrTrim(code);
    	if(code.length() != 6) {
    		return false;
    	}

    	String regex = "^([1-9])\\d{5}$";

    	return code.matches(regex);
	}
	
	public static void isLoginMobile(String code) throws EduException{
		code = StringUtils.nullToStrTrim(code);
		if(isNull(code)){
			throw new EduException(200100);
		}
    	if(code.length() != 11) {
    		throw new EduException(200101);
    	}
    	String regex = "^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}|17[0-9]{9}$|18[0-9]{9}$";

    	boolean flg =  code.matches(regex);
    	if(!flg){
    		throw new EduException(200101);
    	}
	}
	
	public static boolean isMobile(String code){
		code = StringUtils.nullToStrTrim(code);
		String regex = "^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}|17[0-9]{9}$|18[0-9]{9}$";
		return code.matches(regex);
	}
	public static boolean isEmail(String code){
		code = StringUtils.nullToStrTrim(code);
    	String regex = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
    	return code.matches(regex);
	}
	
	public static boolean isURL(String value){
    	String regex = "^(http|https)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\'\\\\\\+&amp;%\\$#\\=~_\\-]+))*$";
    	return value.matches(regex);	
	}
	
	public static boolean isDigital(String v){
		v = StringUtils.nullToStrTrim(v);
		if(v.length()==0){
			return false;
		}
    	String regex = "^[0-9]*$";
    	return v.matches(regex);
	}
	
	public static boolean isDouble(String v){
		v = StringUtils.nullToStrTrim(v);
		if(v.length()==0){
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");    
	    return pattern.matcher(v).matches();
	}
	
	public static boolean isInteger(String v){
		if(!isDigital(v)){
			return false;
		}
		if(Integer.valueOf(v)<=0){
			return false;
		}
		return true;
	}
	
	public static boolean isSms(String code){
		code = StringUtils.nullToStrTrim(code);
    	String regex = "^\\d{6}$";
    	return code.matches(regex);
	}

	/**
	 * 版本校验
	 * @throws EduException 
	 */
	public static String checkVersion(String requestVersion,String configVersion) throws EduException{
		//判定是否2、3位
		String[] tp =requestVersion.split("\\.");
		if(tp.length!=2&&tp.length!=3){
			throw new EduException(110074);
		}
		//判定最前面或最后面是否是空的
		if("".equals(tp[0])||"".equals(tp[tp.length-1])){
			throw new EduException(110074);
		}
		//如果是两位数字，则最后补零
		if(requestVersion.length()==3){
			requestVersion = requestVersion+".0";
		}
		//判定版本号是否小于当前版本号
		tp = configVersion.split(";");
		boolean flg = false;
		for (String v : tp) {
			if(v.length()==3){
				v = v+".0";
			}
			if(v.equals(requestVersion)){
				flg = true;
				break;
			}	
		}
		if(!flg){
			throw new EduException(110073);
		}
		requestVersion = requestVersion.replaceAll("\\.", "");
		if(!requestVersion.matches("^\\d{3}$")){
			throw new EduException(110074);
		}
		return requestVersion;
	}
	
	public static boolean isMoney(String value){
		value = StringUtils.nullToStrTrim(value);
    	String regex = "^([1-9][\\d]{0,7}|0)(\\.[\\d]{1,2})?$";
    	return value.matches(regex);
	}
	
	public static boolean isBank(String value){
		value = StringUtils.nullToStrTrim(value);
    	String regex = "^([1-9]{1})(\\d{14}|\\d{15}|\\d{17}|\\d{18})$";
    	return value.matches(regex);
	}
	
	public static boolean isNickName(String value){
		value = StringUtils.nullToStrTrim(value);
		int len = 0;
		try {
			len = value.getBytes("utf-8").length;
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		if(len<4||len>18){
			return false;
		}
    	String regex = "^[0-9a-zA-Z\u4e00-\u9fa5]*$";
    	return value.matches(regex);
	}
	
	/**
	 * 判断传入字符串是否包含汉字
	 * @param str 传入字符串
	 * @return true 包含 false 不包含
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	    Matcher m = p.matcher(str);
	    if (m.find()) {
	       return true;
	    }
	    return false;
	}
	
	public static boolean isVersion(String code){
		code = StringUtils.nullToStrTrim(code);
    	String regex = "^\\d{3}$";
    	return code.matches(regex);
	}
	
	public static boolean isRealName(String name){
		name = StringUtils.nullToStrTrim(name);
		name = name.replaceAll(" ", "");
    	String regex = "^[\u4e00-\u9fa5]{2,20}$";
    	return name.matches(regex);
	}
	
	public static boolean isPassword(String password){
		password = StringUtils.nullToStrTrim(password);
		if(password.length()<8||password.length()>16){
			return false;
		}
		return true;
	}
	

	public static void isWeakPassword(String key) throws EduException{
		List<String> list = new ArrayList<String>();
		list.add("00000000");
		list.add("11111111");
		list.add("12345678");
		list.add("87654321");
		list.add("66666666");
		list.add("88888888");
		list.add("abcdefgh");
		list.add("abcdabcd");
		list.add("abcd1234");
		list.add("a1b2c3d4");
		list.add("aaaa1111");
		list.add("1234qwer");
		list.add("qwertyui");
		list.add("qwerasdf");
		list.add("administrator");
		list.add("password");
		list.add("p@ssword");
		list.add("iloveyou");
		list.add("52013140");
		list.add("12341234");
		list.add("12344321");
		if(list.contains(key.toLowerCase())){
			throw new EduException(400008);
		}
	}
	public static boolean isArea(String code){	
		code = StringUtils.nullToStrTrim(code);
		code = code.replaceAll(" ", "");
    	String regex = "^[1-6]\\d{5}$";
    	return code.matches(regex);
	}
	
	public static void main(String[] args) {
		System.out.println(isBank("622908333008934068"));
		/*System.out.println(isPhone("010-12345678"));
		System.out.println(isPhone("0103-1234567"));
		System.out.println(isPhone("0102-12345678"));
		System.out.println(isPhone("0101234567"));
		System.out.println(isPhone("01012345678"));
		System.out.println(isPhone("01031234567"));
		System.out.println(isPhone("010212345678"));
		System.out.println(isPhone("0102123456789"));*/
	}

	public static boolean isPhone(String companyPhone) {	
		companyPhone = StringUtils.nullToStrTrim(companyPhone);
    	String regex = "^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}$";
    	return companyPhone.matches(regex);
	}
}
