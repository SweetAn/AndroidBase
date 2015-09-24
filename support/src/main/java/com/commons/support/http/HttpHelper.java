package com.commons.support.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;


/**
 * Created by qianjin on 2015/9/23.
 */
public class HttpHelper {

    private static final String contentType = "application/json;charset=UTF-8";
    public static String BASE_URL_DEV = "http://api-test.365hr.com:8030/";
    public static String BASE_URL = "http://api.365hr.com/";
    private volatile static HttpHelper httpHelper;
    private static Context context;

    private HttpHelper (){}
    public static HttpHelper getInstance(Context context) {
        if (httpHelper == null) {
            synchronized (HttpHelper.class) {
                if (httpHelper == null) {
                    httpHelper = new HttpHelper();
                }
            }
        }
        return httpHelper;
    }

    public AsyncHttpClient getClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(15 * 1000);
        client.addHeader("os", "1");
        client.addHeader("os", "1");
        client.addHeader("os", "1");
        client.addHeader("os", "1");
        client.addHeader("os", "1");
        client.addHeader("os", "1");
        client.setLoggingEnabled(false);
        return client;
    }


    // ----------- 基础访问 START ----------//

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().get(url, params, responseHandler);
    }

    private void get(String url, AsyncHttpResponseHandler responseHandler) {
        getClient().get(url, responseHandler);
    }

    private void post(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        entity.setContentType("application/json;charset=UTF-8");
        getClient().post(context, url, entity, contentType, responseHandler);
    }

    private void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().post(context, url, params, responseHandler);
    }

    private void post(String url, AsyncHttpResponseHandler responseHandler) {
        getClient().post(url, responseHandler);
    }

    public void cancelAllRequests() {
        getClient().cancelAllRequests(true);
    }


}
