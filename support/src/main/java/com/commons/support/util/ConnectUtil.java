package com.commons.support.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络状态
 */
public class ConnectUtil {

    public static final int NETWORK_TYPE_EHRPD = 14; // Level 11
    public static final int NETWORK_TYPE_EVDO_B = 12; // Level 9
    public static final int NETWORK_TYPE_HSPAP = 15; // Level 13
    public static final int NETWORK_TYPE_IDEN = 11; // Level 8
    public static final int NETWORK_TYPE_LTE = 13; // Level 11

    public static final String CONN_MODE_WIFI = "WiFi";
    public static final String CONN_MODE_2G = "2G";
    public static final String CONN_MODE_3G = "3G";
    public static final String CONN_MODE_4G = "4G";

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnectedFast(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && ConnectUtil.isConnectionFast(info.getType(),
                info.getSubtype()));
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            // System.out.println("CONNECTED VIA WIFI");
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                // NOT AVAILABLE YET IN API LEVEL 7
                case ConnectUtil.NETWORK_TYPE_EHRPD:
                    return true; // ~ 1-2 Mbps
                case ConnectUtil.NETWORK_TYPE_EVDO_B:
                    return true; // ~ 5 Mbps
                case ConnectUtil.NETWORK_TYPE_HSPAP:
                    return true; // ~ 10-20 Mbps
                case ConnectUtil.NETWORK_TYPE_IDEN:
                    return false; // ~25 kbps
                case ConnectUtil.NETWORK_TYPE_LTE:
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return false;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Check if the connection mode
     *
     * @return
     */
    public static String getConnectionMode(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            int type = info.getType();
            int subType = info.getSubtype();

            if (type == ConnectivityManager.TYPE_WIFI) {
                return CONN_MODE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                switch (subType) {
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        return CONN_MODE_2G; // ~ 50-100 kbps
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        return CONN_MODE_2G; // ~ 14-64 kbps
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return CONN_MODE_2G; // ~ 50-100 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        return CONN_MODE_3G; // ~ 400-1000 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        return CONN_MODE_3G; // ~ 600-1400 kbps
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        return CONN_MODE_2G; // ~ 100 kbps
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        return CONN_MODE_3G; // ~ 2-14 Mbps
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        return CONN_MODE_3G; // ~ 700-1700 kbps
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        return CONN_MODE_3G; // ~ 1-23 Mbps
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        return CONN_MODE_3G; // ~ 400-7000 kbps
                    // NOT AVAILABLE YET IN API LEVEL 7
                    case ConnectUtil.NETWORK_TYPE_EHRPD:
                        return CONN_MODE_3G; // ~ 1-2 Mbps
                    case ConnectUtil.NETWORK_TYPE_EVDO_B:
                        return CONN_MODE_3G; // ~ 5 Mbps
                    case ConnectUtil.NETWORK_TYPE_HSPAP:
                        return CONN_MODE_3G; // ~ 10-20 Mbps
                    case ConnectUtil.NETWORK_TYPE_IDEN:
                        return CONN_MODE_2G; // ~25 kbps
                    case ConnectUtil.NETWORK_TYPE_LTE:
                        return CONN_MODE_4G; // ~ 10+ Mbps
                    // Unknown
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        return CONN_MODE_2G;
                    default:
                        return CONN_MODE_2G;
                }
            } else {
                return CONN_MODE_2G;
            }
        } else {
            return CONN_MODE_2G;
        }
    }

    public static int getConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                return 2;
            } else {
                return 1;
            }
        }
        return 0;
    }

}
