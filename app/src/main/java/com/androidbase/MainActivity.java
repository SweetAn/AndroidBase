package com.androidbase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.androidbase.base.BaseNoInitDataActivity;
import com.androidbase.util.LogUtil;
import com.androidbase.view.ArticleListActivity;
import com.androidbase.view.LoginActivity;
import com.commons.support.db.config.ConfigUtil;

public class MainActivity extends BaseNoInitDataActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView iv = new ImageView(this);
//        ImageLoader.loadImage("", iv, new ImageLoadListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//            }
//        });


    }

    @Override
    public void init() {

        super.init();

        ConfigUtil.save("test", "this is a value");
        ConfigUtil.save("test1", "this is a value1");
        ConfigUtil.save("test2", "this is a value2");
        ConfigUtil.save("test3", "this is a value3");
        ConfigUtil.save("test4", "this is a value4");

        String value = ConfigUtil.getConfigValue("test");
        LogUtil.log("value is :" + value);

    }

    @Override
    protected void initView() {
        findViewWithClick(R.id.btn_login);
        findViewWithClick(R.id.btn_article);
    }

    @Override
    protected Activity getCountContext() {
        return this;
    }

    @Override
    public int getViewRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                startActivity(LoginActivity.class);

                break;
            case R.id.btn_article:

                startActivity(ArticleListActivity.class);

                break;
        }
    }
}
