package com.commons.support.http;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
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
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    public Context context;
    AsyncHttpClient client;

    public static String URL_DEV = "";
    public static String URL_RELEASE = "";
    public static String BASE_URL = "";

    public static void setShowLog(boolean showLog) {
        SHOW_LOG = showLog;
    }

    private AsyncHttpClient getClient() {
        if (objectIsNull(client)) {
            client = new AsyncHttpClient();
            client.setTimeout(TIME_OUT);
            client.setLoggingEnabled(SHOW_LOG);
        }
        clientSetHeader(client);
        return client;
    }

    public void clientSetHeader(AsyncHttpClient client) {
        Map<String,String> headers = getHeaders();
        if (objectNotNull(headers)) {
            for (String key : headers.keySet()) {
                String value = headers.get(key);
                if (strNotEmpty(value)) {
                    client.addHeader(key, headers.get(key));
                }
            }
        } else {
            client.removeAllHeaders();
        }


//        if (retryGetHeader >= 1) {
//            return;
//        }
//        if (objectNotNull(headers)) {
//            for (String key : headers.keySet()) {
//                String value = headers.get(key);
//                if (strNotEmpty(value)) {
//                    client.addHeader(key, headers.get(key));
//                } else {
//                    client.removeAllHeaders();
//                    retryGetHeader = 1;
//                    headers = getHeaders();
//                    clientSetHeader(client);
//                    break;
//                }
//            }
//            retryGetHeader = 0;
//        }
    }


    public BaseHttpHelper(Context context) {
        this.context = context;
        URL_DEV = getDevUrl();
        URL_RELEASE = getReleaseUrl();
        if (isDev()) {
            BASE_URL = URL_DEV;
        } else {
            BASE_URL = URL_RELEASE;
        }
    }


    public abstract String getDevUrl();

    public abstract String getReleaseUrl();

    public abstract boolean isDev();

    public abstract Map<String, String> getHeaders();


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
            json.put(key, params.get(key));
        }
        return json.toJSONString();
    }

}
