package com.androidbase.util;

import android.app.Activity;
import android.content.Context;

import com.commons.support.BuildConfig;
import com.tendcloud.tenddata.TCAgent;

/**
 * Created by qianjin on 2015/5/29.
 */
public class CountUtil {



    public static void onPause(Activity context) {
        if (!BuildConfig.DEBUG) {
            TCAgent.onPause(context);
        }
    }

    public static void onResume(Activity context) {
        if (!BuildConfig.DEBUG) {
            TCAgent.onResume(context);
        }
    }

    public static void onPageStart(Context context, String pageName) {
        if (!BuildConfig.DEBUG) {
            TCAgent.onPageStart(context, pageName);
        }
    }

    public static void onPageEnd(Context context, String pageName) {
        if (!BuildConfig.DEBUG) {
            TCAgent.onPageEnd(context, pageName);
        }
    }


}
