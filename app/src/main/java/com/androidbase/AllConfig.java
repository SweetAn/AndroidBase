package com.androidbase;

import android.content.Context;

import com.androidbase.commons.CustomCrashHandler;
import com.commons.support.db.DaoUtil;
import com.commons.support.img.ImageLoader;

/**
 * Created by qianjin on 2015/9/23.
 */
public class AllConfig {

    //所有初始化操作
    public static void init(Context context) {
        //init db
        DaoUtil.init(context);

        //init imageLoader
        ImageLoader.init(context);

        //init UncaughtException
        CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
        mCustomCrashHandler.setCustomCrashHanler(context);

    }
}
