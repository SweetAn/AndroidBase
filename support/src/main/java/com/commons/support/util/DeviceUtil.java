package com.commons.support.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

/**
 * 设备信息
 */
public class DeviceUtil {

    private String manufacturer;
    private String model;
    private String releaseVersion;
    private String sdkVersion;

    private int displayWidth;
    private int displayHeight;

    private String deviceId;
    private String networkOperatorName;
    private String networkOperator;
    private String networkCountryISO;
    private String simOperator;
    private String simCountryISO;
    private String simSerialNumber;
    private String subscriberId;
    // private String phoneNumber;
    private String androidId;
    private int dpi;

    private String localMacAddress;

    public DeviceUtil(Context context) {
        getSystemInfo(context);
    }


    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        return d.getWidth();
    }

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        return d.getHeight();
    }


    private void getSystemInfo(Context context) {
        this.manufacturer = Build.MANUFACTURER;
        this.model = Build.MODEL;
        this.releaseVersion = Build.VERSION.RELEASE;
        this.sdkVersion = Build.VERSION.SDK;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        this.displayWidth = d.getWidth();
        this.displayHeight = d.getHeight();
        this.dpi = context.getResources().getDisplayMetrics().densityDpi;

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        this.deviceId = tm.getDeviceId();
        this.networkOperatorName = tm.getNetworkOperatorName();
        this.networkOperator = tm.getNetworkOperator();
        this.networkCountryISO = tm.getNetworkCountryIso();
        this.simOperator = tm.getSimOperator();
        this.simCountryISO = tm.getSimCountryIso();
        this.simSerialNumber = tm.getSimSerialNumber();
        this.subscriberId = tm.getSubscriberId();
        // this.phoneNumber = tm.getLine1Number();

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        this.localMacAddress = info.getMacAddress();

        this.androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    /**
     * @return 厂商
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @return 产品型号
     */
    public String getModel() {
        return model;
    }

    /**
     * @return 系统版本
     */
    public String getReleaseVersion() {
        return releaseVersion;
    }

    /**
     * @return SDK版本
     */
    public String getSdkVersion() {
        return sdkVersion;
    }

    /**
     * @return 屏幕宽
     */
    public int getDisplayWidth() {
        return displayWidth;
    }

    /**
     * @return 屏幕高
     */
    public int getDisplayHeight() {
        return displayHeight;
    }

    /**
     * @return IMEI
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @return 网络服务提供名称
     */
    public String getNetworkOperatorName() {
        return networkOperatorName;
    }

    /**
     * @return 网络服务提供
     */
    public String getNetworkOperator() {
        return networkOperator;
    }

    /**
     * @return 网络服务提供商国家编码
     */
    public String getNetworkCountryISO() {
        return networkCountryISO;
    }

    /**
     * @return SIM服务提供商 中国移动：46000&46002 中国联通：46001 中国电信：46003
     */
    public String getSimOperator() {
        if (TextUtils.isEmpty(simOperator)) {
            return "46000";
        }
        return simOperator;
    }

    /**
     * @return SIM国家编码
     */
    public String getSimCountryISO() {
        return simCountryISO;
    }

    /**
     * @return SIM 序列号
     */
    public String getSimSerialNumber() {
        if (TextUtils.isEmpty(simSerialNumber)) {
            return "000000";
        }
        return simSerialNumber;
    }

    /**
     * @return IMSI
     */
    public String getSubscriberId() {
        if (TextUtils.isEmpty(subscriberId)) {
            return "9";
        }
        return "9" + subscriberId;
    }

    /**
     * 获取wifi mac地址
     * @return wifi mac地址(xx:xx:xx:xx:xx:xx)
     */
    public String getLocalMacAddress() {
        return localMacAddress;
    }



    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    @Override
    public String toString() {
        return "DeviceId: " + getDeviceId() + " || DisplayHeight: " + getDisplayHeight()
                + " || DisplayWidth: " + getDisplayWidth() + " || Manufacturer: " + getManufacturer()
                + " || Model: " + getModel() + " || NetworkCountryISO: " + getNetworkCountryISO()
                + " || NetworkOperator: "
                + getNetworkOperator()
                + " || NetworkOperatorName: "
                + getNetworkOperatorName()
                // +" || PhoneNumber: "+getPhoneNumber()
                + " || ReleaseVersion: " + getReleaseVersion() + " || SdkVersion: " + getSdkVersion()
                + " || SimCountryISO: " + getSimCountryISO() + " || SimOperator: " + getSimOperator()
                + " || SimSerialNumber: " + getSimSerialNumber() + " || SubscriberId: " + getSubscriberId()
                + " || LocalMacAddress: " + getLocalMacAddress();
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }


}
