package com.androidbase.view;

import android.view.View;

import com.androidbase.R;
import com.androidbase.base.BaseNoInitDataActivity;
import com.commons.support.widget.MWebView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianjin on 2015/10/8.
 */
public class BrowserActivity extends BaseNoInitDataActivity {

    @Override
    protected void initView() {
        MWebView webView = $T(R.id.webview);
        Map<String, String> header = new HashMap<>();
        header.put("Host", "m-dev.365hr.com");
        webView.loadUrl("http://123.59.40.166:8030/page/paymentsocialsecuritytips", header);
    }

    @Override
    public int getViewRes() {
        return R.layout.activity_browser;
    }

    @Override
    public void onClick(View v) {
    }
}
