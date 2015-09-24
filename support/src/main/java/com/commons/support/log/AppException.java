package com.commons.support.log;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.commons.support.R;

import org.apache.http.HttpException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;

/**
 * 应用程序异常类：用于捕获异常和提示错误信息
 */
public class AppException extends Exception implements UncaughtExceptionHandler{

	private static final long serialVersionUID = -198334873606487035L;

	private final static boolean Debug = true;//是否保存错误日志 BuildConfig.LOG_DEBUG
	
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
	
	/** 系统默认的UncaughtException处理Handler*/
	private UncaughtExceptionHandler mDefaultHandler;
	
	private AppException(){
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}
	
	private AppException(byte type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;
		if (Debug) {
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
	 * @param ctx
	 */
	public void makeToast(Context ctx){
		if(!Debug) return;
		switch(this.getType()){ // Debug弹Toast
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
	
	public String getErrorMsg(Context ctx){
		String err ="";
		switch(this.getType()){
		case TYPE_HTTP_CODE:
			err = ctx.getString(R.string.abc_action_bar_home_description, this.getCode());
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
	
	/**
	 * 保存异常日志
	 * @param excp
	 */
	public void saveErrorLog(Exception excp) {
		String errorlog = "errorlog.txt";
		String savePath = "";
		String logFilePath = "";
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			//判断是否挂载了SD卡
			String storageState = Environment.getExternalStorageState();		
			if(storageState.equals(Environment.MEDIA_MOUNTED)){
				savePath = Environment.getExternalStorageDirectory() + "/log/";
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdirs();
				}
				logFilePath = savePath + errorlog;
			}
			//没有挂载SD卡，无法写文件
			if(logFilePath == ""){
				return;
			}
			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			fw = new FileWriter(logFile,true);
			pw = new PrintWriter(fw);
			pw.println("--------------------"+(DateFormat.getDateTimeInstance().format(new Date()))+"---------------------");
			excp.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}finally{ 
			if(pw != null){ pw.close(); } 
			if(fw != null){ try { fw.close(); } catch (IOException e) { }}
		}
		excp.printStackTrace();
	}
	
	public static AppException http(int code) {
		return new AppException(TYPE_HTTP_CODE, code, null);
	}
	
	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, 0 ,e);
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

	/**
	 * 获取APP异常崩溃处理对象
	 * @return
	 */
	public static AppException getAppExceptionHandler(){
		return new AppException();
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if(!handleException(ex) && mDefaultHandler != null) {
			LogUtil.log("APPException.uncaughtException....");
			// 这里关闭应用
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}
	/**
	 * 自定义异常处理收集错误信息-发送错误报告
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if(ex == null) {
			return false;
		}
		
		final Context context = null;//AppManager.getAppManager().currentActivity();
		
		if(context == null) return false;
		
		final String crashReport = getCrashReport(context, ex);
		if(TextUtils.isEmpty(crashReport)) return false;
		//显示异常信息-发送错误报告
		sendAppCrashReport(context, crashReport);
		return true;
	}
	/**
	 * 获取APP崩溃异常报告
	 * @param ex
	 * @return
	 */
	private String getCrashReport(Context context, Throwable ex) {
		try {
			PackageInfo pinfo = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0);
			StringBuffer exceptionStr = new StringBuffer();
			exceptionStr.append("Version: "+pinfo.versionName+"("+pinfo.versionCode+")\n");
			exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.MODEL + ")\n");
			exceptionStr.append("Exception: "+ex.getMessage()+"\n");
			StackTraceElement[] elements = ex.getStackTrace();
			for (int i = 0; i < elements.length; i++) {
				exceptionStr.append(elements[i].toString()+"\n");
			}
			return exceptionStr.toString();
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 发送App异常崩溃报告
	 *
	 * @param context
	 * @param crashReport
	 */
	public static void sendAppCrashReport(final Context context, final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("Title");
		builder.setMessage("Message");
		builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 发送异常报告
				Intent i = new Intent(Intent.ACTION_SEND);
				// i.setType("text/plain"); //模拟器
				i.setType("message/rfc822"); // 真机
				i.putExtra(Intent.EXTRA_EMAIL, new String[]{"wjj@dianmi365.com", "qj@dianmi365.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, "点米社保通Android客户端 - 错误报告");
				i.putExtra(Intent.EXTRA_TEXT, crashReport);
				context.startActivity(Intent.createChooser(i, "发送错误报告"));
				// 退出
//				AppManager.getAppManager().AppExit(context);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 退出
//				AppManager.getAppManager().AppExit(context);
			}
		});
		builder.show();
	}
}
