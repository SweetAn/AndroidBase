package com.androidbase.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.androidbase.R;
import com.androidbase.base.BaseNoInitDataActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianjin on 2015/10/8.
 */
public class BrowserActivity extends BaseNoInitDataActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        WebView webView = (WebView) findViewById(R.id.webview);
        Map<String,String> header = new HashMap<>();
        // client.addHeader("Host", "api-test.365hr.com:8030");
        header.put("Host","m-dev.365hr.com");
        //123.59.40.166
        webView.loadUrl("http://123.59.40.166:8030/page/paymentsocialsecuritytips",header);
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
