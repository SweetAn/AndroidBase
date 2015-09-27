package com.androidbase.data.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;


/**
 * Created by qianjin on 2015/9/23.
 */
public class BaseHttpHelper {

    private static final String contentType = "application/json;charset=UTF-8";
    public static String BASE_URL_DEV = "http://api-test.365hr.com:8030/";
    public static String BASE_URL = "http://api.365hr.com/";
    private volatile static BaseHttpHelper httpHelper;
    private static Context context;

    public BaseHttpHelper() {
    }

    public static BaseHttpHelper initInstance(Context context) {
        if (httpHelper == null) {
            synchronized (BaseHttpHelper.class) {
                if (httpHelper == null) {
                    httpHelper = new BaseHttpHelper();
                }
            }
        }
        return httpHelper;
    }

    public static BaseHttpHelper getInstance() {
        return httpHelper;
    }


    public static AsyncHttpClient getClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(15 * 1000);
        client.addHeader("os", "1");
        client.setLoggingEnabled(false);
        return client;
    }


    // ----------- 基础访问 START ----------//

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().get(url, params, responseHandler);
    }

    public void get(String url, AsyncHttpResponseHandler responseHandler) {
        getClient().get(url, responseHandler);
    }

    public static void post(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        entity.setContentType("application/json;charset=UTF-8");
        getClient().post(context, url, entity, contentType, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().post(context, url, params, responseHandler);
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        getClient().post(url, responseHandler);
    }

    public static void cancelAllRequests() {
        getClient().cancelAllRequests(true);
    }


    //TODO 这个方法还要考虑怎么做方便点
    public static RequestParams getParams(Class<?>... values) {
        RequestParams params = new RequestParams();
        params.put("","");
        return params;
    }


}
