package com.androidbase.http;

import android.content.Context;

import com.androidbase.commons.AppException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;


/**
 * Created by qianjin on 2015/9/23.
 */
public class HttpHelper {

    private static final String contentType = "application/json;charset=UTF-8";
    public static String BASE_URL_DEV = "http://api-test.365hr.com:8030/";
    public static String BASE_URL = "http://api.365hr.com/";
    private volatile static HttpHelper httpHelper;
    private static Context context;

    private final int RETRY_MAX_TIME_FOR_502 = 3;

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
        client.setLoggingEnabled(false);
        return client;
    }


    // ----------- 基础访问 START ----------//

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().get(url, params, responseHandler);
    }

    private void get(String url, AsyncHttpResponseHandler responseHandler) {
        get(url, responseHandler, 0);
//        getClient().get(url, responseHandler);
    }
    private void get(final String url, final AsyncHttpResponseHandler responseHandler, final int retryTime) {
        if(retryTime >= RETRY_MAX_TIME_FOR_502) {
            try {
                throw new AppException(url, 502);
            } catch (AppException e) {}
            try {
                new BufferedOutputStream(new FileOutputStream(""));
            } catch (IOException e) {
//                throw AppException.io(e);
            }
            return;
        }
        AsyncHttpResponseHandler rh = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                responseHandler.onSuccess(statusCode,headers,responseBody);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode==502) {
                    this.sendRetryMessage(0); // 在主线程里，不能线程休眠
                } else {
                    responseHandler.onFailure(statusCode,headers,responseBody,error);
                }
            }
            @Override
            public void onRetry(int retryNo) {
                get(url, responseHandler, (retryTime+1));
            }
        };

        getClient().get(url, rh);
    }

    private void post(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        entity.setContentType("application/json;charset=UTF-8");
        //getClient().post(context, url, entity, contentType, responseHandler);
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

    // ----------- 基础访问 END ----------//

    public void dns502Test(AsyncHttpResponseHandler handler){
        get("http://www.shitoo.com/", handler);
    }
}
