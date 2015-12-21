package com.androidbase.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.commons.support.db.config.ConfigUtil;
import com.commons.support.util.DirUtil;
import com.commons.support.util.EncryptionUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by qianjin on 2015/12/21.
 */
public class UuidUtil {

    public static final String UUID = "uuid";


    /**
     * 读UUID
     */
    public static String getUUID(Context context) {
        String uuid = ConfigUtil.getConfigValue(UUID);
        if (TextUtils.isEmpty(uuid)) {
            uuid = getUUIDFromFile(context);
        }
        return uuid;
    }

    /**
     * 生成默认UUID算法
     * @param context
     * @param uuid
     * @return
     */
    public static String saveNewUUID(Context context,String uuid) {
        LogUtil.log("save uuid from user!");
        saveUUIDInFile(uuid);
        ConfigUtil.save(UUID, uuid);
        return uuid;
    }

    private static void saveUUIDInFile(String uuid) {
        File targetFile = new File(DirUtil.getStoragePath(DirUtil.UUID_FILE_PATH));
        if (targetFile.exists()) {
            targetFile.delete();
        }
        OutputStreamWriter osw = null;
        try {
            LogUtil.log("save uuid into file!");
            targetFile.createNewFile();
            osw = new OutputStreamWriter(new FileOutputStream(targetFile), "utf-8");
            osw.write(uuid);
            osw.close();
        } catch (Exception e) {
            try {
                if (null != osw) {
                    osw.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static String getUUIDFromFile(Context context) {
        String uuid = "";
        File uuidFile = new File(DirUtil.getStoragePath(DirUtil.UUID_FILE_PATH));
        if (uuidFile.exists()) {
            LogUtil.log("get uuid from old file!");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(uuidFile));
                uuid = reader.readLine();
                reader.close();
                if (TextUtils.isEmpty(uuid)) {
                    uuid = buildNewUUID(context);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            uuid = buildNewUUID(context);
        }
        return uuid;
    }

    private static String buildNewUUID(Context context) {
        LogUtil.log("build a new uuid default!");
        String uuid = buildUUID(context);
        saveUUIDInFile(uuid);
        ConfigUtil.save(UUID, uuid);
        return uuid;
    }

    /**
     * 提供一个默认生成uuid的算法
     * @param context
     * @return
     */
    private static String buildUUID(Context context) {
        String pid = "86" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10
                + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
                + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
                + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
                + Build.USER.length() % 10 + System.currentTimeMillis();

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String lid = getDeviceId(context) + pid + androidId + getLocalMac(context);
        String uniqueID = EncryptionUtil.MD5(lid).toUpperCase();
        return uniqueID;
    }

    private static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    private static String getLocalMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


}
