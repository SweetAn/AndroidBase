package com.commons.support.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.commons.support.log.LogUtil;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.List;

public class Utility {


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getSource(Context context) {
        String source = "";
        try {
            ApplicationInfo ai =
                    context.getPackageManager().getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            source = bundle.getString("CHANNEL");
            //LogUtil.log("source is :" + source);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Utility", "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e("Utility", "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
        return source;
    }

    public static boolean containsChinese(String s) {
        if (null == s || "".equals(s.trim())) return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i))) return true;
        }
        return false;
    }

    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    public static boolean isAllChinese(String s) {
        if (null == s || "".equals(s.trim())) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!isChinese(s.charAt(i))) return false;
        }
        return true;
    }

    public static boolean isActivityRunning(Class activityClass, Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo task : tasks) {
            LogUtil.log("task.baseActivity.getClassName() = " + task.baseActivity.getClassName());
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }
        return false;
    }


    public static void startAppWithPackageName(String packageName, Context context) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String pn = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(pn, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }


    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    public static int px2Dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得Banner广告的高
     *
     * @param context
     * @return
     */
    public static int getBannerAdHeight(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        // System.out.println("屏幕宽度："+d.getWidth()+" 广告位高度："+(int) (d.getWidth() / 2.5));
        //1024 * 410
        int height = (int) (d.getWidth() * 410.0 / 1024);
        //return (int) (d.getHeight() / 4);
        //return dip2px(context, 150);
        return height;

    }


    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static int getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }


    /**
     * 安装APP
     */
    public static boolean installPkgByFile(Context context, String strFilePath, String strFileName) {
        File file = new File(strFilePath + File.separator + strFileName);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    public static boolean installPkgByFile(Context context, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    public static boolean installPkgByFile(Context context, File file) {
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 检查系统中是否已安装某APP
     *
     * @param context
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            //e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 从assets 文件夹中获取文件并读取数据
     */
    public static String getFromAssets(Context context, String fileName) {
        String result = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int lenght = in.available();
            byte[] buffer = new byte[lenght];
            in.read(buffer);
            result = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void saveObj(Object o, String fileName, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadObj(String fileName, Context context) {
        Object object = null;
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            object = ois.readObject();
            ois.close();
        } catch (Exception e) {
            Log.e("Utility", e.getMessage(), e);
        }
        return object;
    }

    public static Object loadObjWithException(String fileName, Context context) throws Exception {
        Object object = null;
        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        object = ois.readObject();
        ois.close();
        return object;
    }


    /**
     * 序列化数据
     *
     * @param o
     * @param file
     */
    public static void serializationOfObject(Object o, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            Log.e("Utility", e.getMessage(), e);
        }
    }

    /**
     * 反序列化数据
     *
     * @param file
     * @return
     */
    public static Object deserializationOfObject(File file) {
        if (!file.exists()) return null;
        Object object = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            object = ois.readObject();
            ois.close();
        } catch (Exception e) {
            Log.e("Utility", e.getMessage(), e);
        }
        return object;
    }

    /**
     * 播放音效
     */
    public static MediaPlayer ring(Context c, String name) throws Exception {
        AssetFileDescriptor aFD = c.getAssets().openFd(name);
        FileDescriptor fileDescriptor = aFD.getFileDescriptor();
        MediaPlayer player = new MediaPlayer();
        player.setDataSource(fileDescriptor, aFD.getStartOffset(), aFD.getLength());
        aFD.close();
        player.setAudioStreamType(AudioManager.STREAM_ALARM);
        player.setLooping(false);
        player.prepare();
        player.start();
        return player;
    }

    public static void hideKb(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && context.getCurrentFocus() != null) {
            if (context.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void showKeybord(Context context) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }


    private int getLastDayOfMonth(int year, int month) {
        month--;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        // 某年某月的最后一天
        return cal.getActualMaximum(Calendar.DATE);
    }


}
