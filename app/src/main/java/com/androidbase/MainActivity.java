package com.androidbase;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidbase.commons.AppException;
import com.androidbase.view.ArticleListActivity;
import com.androidbase.view.LoginActivity;
import com.commons.support.db.config.Config;
import com.commons.support.db.config.ConfigUtil;

public class MainActivity extends BaseActivity {

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
        String value = ConfigUtil.getConfigValue("test");
        printLog("value is :" + value);

//        HttpHelper.getInstance(context).dns502Test(new MAsyncHttpResponseHandler() {
//            @Override
//            public void onMSuccess(int statusCode, Header[] headers, String responseString, Result result) {
//                LogUtil.log("0925..onMSuccess");
//            }
//            @Override
//            public void onMFailure(int statusCode, Header[] headers, String responseString, @Nullable Result result, @Nullable Throwable throwable) {
//                LogUtil.log("0925..onMFailure.statusCode=" + statusCode + ", throwable=" + throwable.toString());
//            }
//        });
//
//        try {
//            testNull();
//        } catch (AppException e) {}

    }

    private void testNull() throws AppException {
        TextView tv = null;
        tv.setText("");
        if(tv==null) throw new AppException("TextView is null!");
    }

}
