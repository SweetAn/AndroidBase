package com.commons.support.http;

import android.text.TextUtils;

import com.commons.support.db.config.Config;
import com.commons.support.db.config.ConfigUtil;
import com.commons.support.log.LogUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by qianjin on 2015/9/28.
 */
public class httpDns {
    public static final String HTTP_DNS_HOST = "http://119.29.29.29/d?dn=";
    public static void initHost(final String host) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_DNS_HOST + host, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (!TextUtils.isEmpty(responseString)) {
                    LogUtil.log(responseString);
                    ConfigUtil.save(new Config(host,responseString));
                }
            }
        });
    }
    public static String getHostIp(String host){
        String hostIp = ConfigUtil.getConfigValue(host);
        if (TextUtils.isEmpty(hostIp)) {
            hostIp = host;
        }
        return hostIp;
    }
}
