package com.androidbase;

import android.app.Application;
import android.content.Context;


public class MApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        this.context = this;

        AllConfig.init(this);
    }

    public static Context getAppContext(){
        return context;
    }

}