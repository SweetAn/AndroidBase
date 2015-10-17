package com.androidbase.view;

import android.os.Bundle;
import android.webkit.WebView;

import com.androidbase.BaseActivity;
import com.androidbase.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianjin on 2015/10/8.
 */
public class BrowserActivity extends BaseActivity{

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
}
