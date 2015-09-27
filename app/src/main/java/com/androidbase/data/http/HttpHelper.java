package com.androidbase.data.http;

import com.androidbase.commons.MAsyncHttpResponseHandler;
import com.commons.support.log.LogUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by qianjin on 2015/9/25.
 */
public class HttpHelper extends BaseHttpHelper{
    public static void login(String userName, String pas, final MAsyncHttpResponseHandler responseHandler){
        LogUtil.log("Call login in HttpHelper");
        RequestParams params = new RequestParams();
        params.put("userName",userName);
        params.put("pas", pas);
        post(BASE_URL + "login", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                responseHandler.onSuccess(statusCode,headers,responseBody);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                responseHandler.onFailure(statusCode,headers,responseBody,error);
            }
        });
    }

}
