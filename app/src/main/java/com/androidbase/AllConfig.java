package com.androidbase;

import android.content.Context;

import com.commons.support.db.config.ConfigUtil;
import com.commons.support.img.ImageLoader;

/**
 * Created by qianjin on 2015/9/23.
 */
public class AllConfig {
    public static void init(Context context) {
        //init config db
        ConfigUtil.init(context);

        //init imageLoader
        ImageLoader.init(context);


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
