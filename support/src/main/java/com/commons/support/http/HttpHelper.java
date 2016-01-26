package com.commons.support.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by qianjin on 2016/1/26.
 */
public class HttpHelper extends BaseHttpHelper{

    public HttpHelper(Context context) {
        super(context);
    }

    public static HttpHelper getInstance(Context context) {
        return new HttpHelper(context);
    }

    @Override
    public String getDevUrl() {
        return "";
    }
    @Override
    public String getReleaseUrl() {
        return "";
    }

    @Override
    public Map<String, String> getHeaders() {
        return null;
    }
}
