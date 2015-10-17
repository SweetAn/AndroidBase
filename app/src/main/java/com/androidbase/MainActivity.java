package com.androidbase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.androidbase.base.BaseNoInitDataActivity;
import com.androidbase.util.LogUtil;
import com.androidbase.view.ArticleListActivity;
import com.androidbase.view.LoginActivity;
import com.commons.support.db.config.Config;
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

        //测试
        //select * from seeds where seeds.title like ‘%测试%’ limit 0,10；


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class);
            }
        });
        findViewById(R.id.btn_article).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ArticleListActivity.class);
            }
        });


        ConfigUtil.save(new Config("test", "this is a value"));
        ConfigUtil.save(new Config("test1", "this is a value1"));
        ConfigUtil.save(new Config("test2", "this is a value2"));
        ConfigUtil.save(new Config("test3", "this is a value3"));
        ConfigUtil.save(new Config("test4", "this is a value4"));

        ConfigUtil.save(new Config("test5", "this is a value5", "test5c1", "test5c2"));
        ConfigUtil.save(new Config("test6", "this is a value6", "test6c1", "test6c2"));
        String value = ConfigUtil.getConfigValue("test");
        LogUtil.log("value is :" + value);


    }

    @Override
    protected void initView() {

    }

    @Override
    protected Activity getCountContext() {
        return null;
    }


    @Override
    public int getViewRes() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
