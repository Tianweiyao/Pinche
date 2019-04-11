package com.hodehtml.demo.utils;

import com.core.framework.constants.Constants;
import com.core.framework.utils.CommonUtils;
import com.core.framework.utils.StringUtils;
import com.core.framework.utils.TraceInfoUtils;
import com.core.framework.utils.ValidatorUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class WebUtils {
	private static Logger logger = Logger.getLogger(WebUtils.class);

	private static final int BUFFERSIZE = 8196;
	private static final String CHARSETNAME = Constants.ENCODING_UTF8;
	
	public static WebUtils getInstance(){
		return new WebUtils();
	}
	
	public String getLocalHost() {

		try {
			return InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
		}

		return "";
	}
	
	//获得IP地址
	public String getIP(HttpServletRequest request) {

		String ip = "";
		ip = request.getHeader("x-forwarded-for");
		if(ValidatorUtils.isNull(ip)){
			ip = request.getHeader("x-forwared-for");
		}
		if (ValidatorUtils.isNull(ip) || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if (ValidatorUtils.isNull(ip) || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (ValidatorUtils.isNull(ip) || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
				}
			}
		} else {
			String[] ips = ip.split(",");
			if(ips.length > 1) {
			String tempIP = "";
				for(int i=0; i<ips.length; i++) {
					tempIP = StringUtils.nullToStrTrim(ips[i]);
					if(ValidatorUtils.isNull(tempIP) && !Constants.UNKNOWN.equalsIgnoreCase(tempIP)) {
						ip = tempIP;
						break;
					}
				}
			}
		}
		if("0:0:0:0:0:0:0:1".equals(ip)){
			ip = "127.0.0.1";
		}
		return ip;
	}
	
	public String getHeader(HttpServletRequest request, String name) {
		return StringUtils.nullToStrTrim(request.getHeader(name));
	}
	public String getHeaderDecode(HttpServletRequest request, String name) {
		return StringUtils.decode(getHeader(request, name));
	}

	
	//关闭流信息
	public void closeBufferedInputStream(BufferedInputStream bufferedInputStream) {

		if(bufferedInputStream != null) {
			try {
				bufferedInputStream.close();
			} catch (IOException e) {
				logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			}
			bufferedInputStream = null;
		}
	}
	
	public void closeInputStream(InputStream inputStream) {

		if(inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			}
			inputStream = null;
		}
	}
	
	public String generateContent(InputStream inputStream) {

		if (inputStream == null) {
			return null;
		}

		BufferedInputStream bufferedInputStream = null;

		StringBuffer content = new StringBuffer();

		try {
			byte[] buffer = new byte[BUFFERSIZE];
			int count = 0;

			bufferedInputStream = new BufferedInputStream(inputStream, BUFFERSIZE);

			while ((count = bufferedInputStream.read(buffer)) != -1) { // >0
				content.append(new String(buffer, 0, count, CHARSETNAME));
			}

			buffer = null;

			return content.toString();
		} catch (Exception e) {
			logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			return null;
		} finally {
			closeBufferedInputStream(bufferedInputStream);
			closeInputStream(inputStream);
		}
	}
	
	public void responseContent(HttpServletResponse response, String rescontent) {

		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.setContentType("application/octet-stream");
		response.setContentLength(CommonUtils.getRealLength(rescontent));

		OutputStream outputstream = null;
		BufferedOutputStream bufferedOutputStream = null;

		try {
			outputstream = response.getOutputStream();
			bufferedOutputStream = new BufferedOutputStream(outputstream, BUFFERSIZE);

			bufferedOutputStream.write(rescontent.getBytes(Constants.ENCODING_UTF8));

			bufferedOutputStream.flush();
			
			response.flushBuffer();
		} catch (IOException e) {
			logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
		} finally {
			closeBufferedOutputStream(bufferedOutputStream);
			closeOutputStream(outputstream);
		}
	}
	public void closeBufferedOutputStream(BufferedOutputStream bufferedOutputStream) {

		if(bufferedOutputStream != null) {
			try {
				bufferedOutputStream.close();
			} catch (IOException e) {
				logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			}
			bufferedOutputStream = null;
		}
	}
	public void closeOutputStream(OutputStream outputStream) {

		if(outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			}
			outputStream = null;
		}
	}
	public void closeFileInputStream(FileInputStream fileInputStream) {

		if(fileInputStream != null) {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			}
			fileInputStream = null;
		}
	}
	
	public void flush(BufferedOutputStream bufferedOutputStream) {

		try {
			bufferedOutputStream.flush();
		} catch (IOException e) {
			logger.error(TraceInfoUtils.getTraceInfo() + e.getMessage());
		}
	}
	
	public void flush(HttpServletResponse response) {

		try {
			response.flushBuffer();
		} catch (IOException e) {
			logger.error(TraceInfoUtils.getTraceInfo() + e.getMessage());
		}
	}
	
	public String response(String resfilename) {

		FileInputStream fileInputStream  = null;
		BufferedInputStream bufferedInputStream = null;

		StringBuffer rescontent = new StringBuffer();

		try {
			fileInputStream = new FileInputStream(resfilename);
			bufferedInputStream = new BufferedInputStream(fileInputStream, BUFFERSIZE);

			int count = 0;
			byte[] buffer = new byte[BUFFERSIZE];

			while ((count = bufferedInputStream.read(buffer)) != -1) {
				rescontent.append(new String(buffer, 0, count, Constants.ENCODING_UTF8));
			}

			buffer = null;

			return rescontent.toString();
		} catch (IOException e) {
			logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			return "";
		} finally {
			closeBufferedInputStream(bufferedInputStream);
			closeFileInputStream(fileInputStream);
		}
	}
	
	public String getDefaultRescontent(String format) {

		if(!format.equalsIgnoreCase(Constants.JSON)) {
			format = Constants.XML;
		}

		return 100002 + format.toLowerCase();
	}
}
