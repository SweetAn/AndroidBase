package com.androidbase;

import android.content.Context;

import com.androidbase.commons.CustomCrashHandler;
import com.androidbase.data.http.BaseHttpHelper;
import com.androidbase.data.http.HttpHelper;
import com.commons.support.db.DaoUtil;
import com.commons.support.http.httpDns;
import com.commons.support.img.ImageLoader;

/**
 * Created by qianjin on 2015/9/23.
 */
public class AllConfig {
    public static void init(Context context) {
        //init db
        DaoUtil.init(context);

        //init imageLoader
        ImageLoader.init(context);

        //init httpHelper
        BaseHttpHelper.initInstance(context);

        //init httpDns
        //HttpDns.initHost("");
        httpDns.initHost(HttpHelper.RELEASE_HOST);
        httpDns.initHost(HttpHelper.DEV_HOST);

        //init UncaughtException
        CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
        mCustomCrashHandler.setCustomCrashHanler(context);

        //init push
        //MiPushMessageReceiver.initMiPush(context);

        //init others
//        //初始化talkingData
//        TCAgent.init(context);
//        TCAgent.setReportUncaughtExceptions(true);
//        //LogUtil.log("Utility.getSource(context) = " + Utility.getSource(context));
//        TalkingDataAppCpa.init(context, Constants.TD_APP_AD_ID, Utility.getSource(context));
//        if (BuildConfig.DEBUG) {
//            TCAgent.LOG_ON = true;
//        } else {
//            TCAgent.LOG_ON = false;
//        }
    }
}
