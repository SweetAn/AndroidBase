package com.androidbase.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.androidbase.BuildConfig;
import com.androidbase.MApplication;
import com.androidbase.commons.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by User on 2015/9/28.
 */
public class LogUtil {

    public static void log(String msg) {
        if (BuildConfig.LOG_DEBUG)
            System.out.println("=== " + msg + " ===");
    }
    public static void error(String msg) {
        if (BuildConfig.LOG_DEBUG)
            System.err.println("=== " + msg + " ===");
    }

    public static void i (String TAG, String log){
        if(BuildConfig.LOG_DEBUG)
            Log.i(TAG, log);
    }

    public static void d (String TAG, String log){
        if(BuildConfig.LOG_DEBUG)
            Log.d(TAG, log);
    }

    public static void d (String TAG, String log, Throwable tr){
        if(BuildConfig.LOG_DEBUG)
            Log.d(TAG, log, tr);
    }

    public static void e (String TAG, String log){
        if(BuildConfig.LOG_DEBUG)
            Log.e(TAG, log);
    }

    public static void v (String TAG, String log){
        if(BuildConfig.LOG_DEBUG)
            Log.v(TAG, log);
    }

    public static void w (String TAG, String log){
        if(BuildConfig.LOG_DEBUG)
            Log.w(TAG, log);
    }

    /**
     * 保存异常日志
     * @param excp
     */
    public static void saveErrorLog(Exception excp) {
        String errorlog = "errorlog.txt";
        String savePath = "";
        String logFilePath = "";
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            //判断是否挂载了SD卡
            String storageState = Environment.getExternalStorageState();
            if(storageState.equals(Environment.MEDIA_MOUNTED)){
                savePath = Environment.getExternalStorageDirectory() + "/"+ Constants.BASE_DIR_NAME+"/log/";
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
            pw.println("\n--------------------" + (DateFormat.getDateTimeInstance().format(new Date())) + "---------------------");
            excp.printStackTrace(pw);
            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(pw != null){ pw.close(); }
            if(fw != null){ try { fw.close(); } catch (IOException e) { }}
        }
//		excp.printStackTrace();
    }

    public static void saveErrorLog(String errorStr) {
        String errorlog = "errorlog.txt";
        String savePath = "";
        String logFilePath = "";
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            //判断是否挂载了SD卡
            String storageState = Environment.getExternalStorageState();
            if(storageState.equals(Environment.MEDIA_MOUNTED)){
                savePath = Environment.getExternalStorageDirectory() + "/"+ Constants.BASE_DIR_NAME+"/log/";
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
            pw.println("\n--------------------" + (DateFormat.getDateTimeInstance().format(new Date())) + "---------------------");
            pw.println(errorStr);
            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(pw != null){ pw.close(); }
            if(fw != null){ try { fw.close(); } catch (IOException e) { }}
        }
    }

    /**
     * 自定义异常处理收集错误信息-发送错误报告
     * @param ex
     * @return true:处理了该异常信息;否则返回false
     */
    public static boolean handleException(Throwable ex) {
        if(ex == null) {
            return false;
        }

        final Context context = MApplication.getAppContext();//AppManager.getAppManager().currentActivity();

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
    public static String getCrashReport(Context context, Throwable ex) {
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
