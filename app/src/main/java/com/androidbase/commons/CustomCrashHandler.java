package com.androidbase.commons;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.androidbase.MApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * UncaughtExceptionHandler：线程未捕获异常控制器是用来处理未捕获异常的。
 * 如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框
 * 实现该接口并注册为程序中的默认未捕获异常处理
 * 这样当未捕获异常发生时，就可以做些异常处理操作
 * 例如：收集异常信息，发送错误报告 等。
 * <p>
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 * Created by Wang on 2015/9/25.
 */
public class CustomCrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "Activity";
    private Context mContext;
    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();
    private static CustomCrashHandler mInstance = new CustomCrashHandler();


    private CustomCrashHandler(){}
    /**
     * 单例模式，保证只有一个CustomCrashHandler实例存在
     * @return
     */
    public static CustomCrashHandler getInstance(){
        return mInstance;
    }

    /**
     * 异常发生时，系统回调的函数，我们在这里处理一些操作
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //将一些信息保存到SDcard中
        savaInfoToSD(mContext, ex);

        //提示用户程序即将退出
        showToast(mContext, "很抱歉，程序遭遇异常，被迫退出！");
        try {
            thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        ActivityManager am = (ActivityManager) MApplication.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(MApplication.getAppContext().getPackageName());

        //完美退出程序方法
//        ExitAppUtils.getInstance().exit();

    }


    /**
     * 为我们的应用程序设置自定义Crash处理
     */
    public void setCustomCrashHanler(Context context){
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 显示提示信息，需要在线程中显示Toast
     * @param context
     * @param msg
     */
    private void showToast(final Context context, final String msg){
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }


    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     * @param context
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context){
        HashMap<String, String> map = new HashMap<String, String>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);

        map.put("MODEL", "" + Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", "" +  Build.PRODUCT);

        return map;
    }


    /**
     * 获取系统未捕捉的错误信息
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();

        Log.e(TAG, mStringWriter.toString());
        return mStringWriter.toString();
    }

    /**
     * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
     * @param context
     * @param ex
     * @return
     */
    private void savaInfoToSD(Context context, Throwable ex){
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        for (Map.Entry<String, String> entry : obtainSimpleInfo(context).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        sb.append(obtainExceptionInfo(ex));

        String errorlog = "uncatchErrorlog.txt";
        String savePath = "";
        String logFilePath = "";
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            //判断是否挂载了SD卡
            String storageState = Environment.getExternalStorageState();
            if(storageState.equals(Environment.MEDIA_MOUNTED)){
                savePath = Environment.getExternalStorageDirectory() + "/"+Constants.BASE_DIR_NAME+"/log/";
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
            pw.println(sb.toString());
            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(pw != null){ pw.close(); }
            if(fw != null){ try { fw.close(); } catch (IOException e) { }}
        }


//        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            File dir = new File(SDCARD_ROOT + File.separator + Constants.BASE_DIR_NAME + File.separator + "log/");
//            if(! dir.exists()){
//                dir.mkdirs();
//            }
//
//            try{
//                fileName = dir.toString() + File.separator + "uncatchErrorlog.txt";
//                FileOutputStream fos = new FileOutputStream(fileName);
//                fos.write(sb.toString().getBytes());
//                fos.flush();
//                fos.close();
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//
//        }
//
//        return fileName;

    }


    /**
     * 将毫秒数转换成yyyy-MM-dd-HH-mm-ss的格式
     * @param milliseconds
     * @return
     */
    private String paserTime(long milliseconds) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String times = format.format(new Date(milliseconds));

        return times;
    }
}
