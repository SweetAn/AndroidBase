package com.commons.support.http;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.commons.support.BuildConfig;
import com.commons.support.util.BaseJava;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by qianjin on 2016/1/26.
 */
public abstract class BaseHttpHelper extends BaseJava {

    public static int TIME_OUT = 15;
    public static boolean SHOW_LOG = false;
    private Map<String, String> headers;
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private Context context;

    public static String URL_DEV = "";
    public static String URL_RELEASE = "";
    public static String BASE_URL = "";

    public void setClient(int timeout, Map<String, String> headers) {
        TIME_OUT = timeout;
        this.headers = headers;
    }

    public static void setShowLog(boolean showLog) {
        SHOW_LOG = showLog;
    }

    private AsyncHttpClient getClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIME_OUT);
        if (objectNotNull(headers)) {
            for (String key : headers.keySet()) {
                client.addHeader("key", headers.get(key));
            }
        }
        client.setLoggingEnabled(SHOW_LOG);
        return client;
    }


    public BaseHttpHelper (Context context) {
        this.context = context;
        URL_DEV = getDevUrl();
        URL_RELEASE = getReleaseUrl();
        headers = getHeaders();
        setBaseUrl();
    }


    public abstract String getDevUrl();
    public abstract String getReleaseUrl();
    public abstract Map<String,String> getHeaders();

    private static void setBaseUrl(){
        if (BuildConfig.DEBUG) {
            BASE_URL = URL_DEV;
        } else {
            BASE_URL = URL_RELEASE;
        }
    }

    // ----------- 基础访问 START ----------//

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().get(url, params, responseHandler);
    }

    public void get(String url, AsyncHttpResponseHandler responseHandler) {
        getClient().get(url, responseHandler);
    }

    public void post(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        entity.setContentType(CONTENT_TYPE);
        getClient().post(context, url, entity, CONTENT_TYPE, responseHandler);
    }

    public void post(String url, String json, AsyncHttpResponseHandler responseHandler) {
        StringEntity entity;
        try {
            entity = new StringEntity(json);
            entity.setContentType(CONTENT_TYPE);
            post(url, entity, responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void post(String url, Map<String, String> params, AsyncHttpResponseHandler responseHandler) {
        StringEntity entity;
        try {
            entity = new StringEntity(getPostJson(params));
            entity.setContentType(CONTENT_TYPE);
            post(url, entity, responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getClient().post(url, params, responseHandler);
    }

    public void post(String url, AsyncHttpResponseHandler responseHandler) {
        getClient().post(url, responseHandler);
    }

    public void cancelAllRequests() {
        getClient().cancelAllRequests(true);
    }

    public String getPostJson(Map<String, String> params) {
        JSONObject json = new JSONObject();
        for (String key : params.keySet()) {
            json.put(key,params.get(key));
        }
        return json.toJSONString();
    }

}
