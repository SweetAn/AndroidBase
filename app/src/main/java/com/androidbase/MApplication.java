package com.androidbase;

import android.app.Application;
import android.content.Context;


public class MApplication extends Application {

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        this.context = this;

        AllConfig.init(this);

    }

}