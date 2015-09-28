package com.androidbase.commons;

import android.content.Context;

import com.androidbase.BuildConfig;
import com.androidbase.util.LogUtil;

import org.apache.http.HttpException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 应用程序异常类：用于捕获异常和提示错误信息
 */
public class AppException extends Exception{

	/** 定义异常类型 */
	public final static byte TYPE_NETWORK 	= 0x01;
	public final static byte TYPE_SOCKET	= 0x02;
	public final static byte TYPE_HTTP_CODE	= 0x03;
	public final static byte TYPE_HTTP_ERROR= 0x04;
	public final static byte TYPE_XML	 	= 0x05;
	public final static byte TYPE_IO	 	= 0x06;
	public final static byte TYPE_RUN	 	= 0x07;
	public final static byte TYPE_JSON      = 0x08;

	private byte type;
	private int code;
	
	private AppException(){}

	public AppException(String detailMessage) {
		super(detailMessage);
		if (BuildConfig.LOG_DEBUG) {
			saveErrorLog(this);
			printStackTrace();
		}
	}

	public AppException(String url, int code) {
		this("request === " + url + " === appear " + code + " error!");
	}

	private AppException(byte type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;
		if (BuildConfig.LOG_DEBUG) {
			if (excp != null) {
				this.saveErrorLog(excp);
				excp.printStackTrace();
			}
		}
	}
	public int getCode() {
		return this.code;
	}
	public int getType() {
		return this.type;
	}
	
	/**
	 * 提示友好的错误信息提示
	 * @param context
	 */
	public void makeToast(Context context, int type, int code){
		if(!BuildConfig.LOG_DEBUG) return;
		switch(type){ // Debug弹Toast
		case TYPE_HTTP_CODE:
			break;
		case TYPE_HTTP_ERROR:
			break;
		case TYPE_SOCKET:
			break;
		case TYPE_NETWORK:
			break;
		case TYPE_XML:
			break;
		case TYPE_IO:
			break;
		case TYPE_RUN:
			break;
		case TYPE_JSON:
			break;
		}
	}

	public String getErrorMsg(Context context, int type, int code){
		String err ="";
		switch(type){
		case TYPE_HTTP_CODE:
//			err = context.getString(R.string.abc_action_bar_home_description, code);
			break;
		case TYPE_HTTP_ERROR:
			err = "";
			break;
		case TYPE_SOCKET:
			break;
		case TYPE_NETWORK:
			break;
		case TYPE_XML:
			break;
		case TYPE_IO:
			break;
		case TYPE_RUN:
			break;
		case TYPE_JSON:
			break;
		}
		return err;
	}

	public void saveErrorLog() {
		LogUtil.saveErrorLog(this);
	}

	public void saveErrorLog(Exception excp) {
		LogUtil.saveErrorLog(excp);
	}

	public static AppException http(int code) {
		return new AppException(TYPE_HTTP_CODE, code, null);
	}
	
	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, 0 ,e);
	}

	public static AppException http(int code, Exception e) {
		return new AppException(TYPE_HTTP_ERROR, code ,e);
	}

	public static AppException socket(Exception e) {
		return new AppException(TYPE_SOCKET, 0 ,e);
	}
	
	public static AppException io(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new AppException(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof IOException){
			return new AppException(TYPE_IO, 0 ,e);
		}
		return run(e);
	}
	
	public static AppException xml(Exception e) {
		return new AppException(TYPE_XML, 0, e);
	}
	
	public static AppException network(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new AppException(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof HttpException){
			return http(e);
		}
		else if(e instanceof SocketException){
			return socket(e);
		}
		return http(e);
	}
	
	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, 0, e);
	}
	
	public static AppException json(Exception e){
		return new AppException(TYPE_JSON, 0, e);
	}


}
