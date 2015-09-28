package com.androidbase.data.http;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.androidbase.entity.Result;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * 只能用于自家服务端返回的数据处理
 * Created by Wang on 2015/9/22.
 */
public abstract class MAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        try {
            Result baseResult = JSON.parseObject(responseBody, Result.class);
            if (baseResult != null) {
                if(baseResult.isResult()) {
                    MRequestEnd();
                    onMSuccess(baseResult);
                    onMSuccess(statusCode, headers, responseBody, baseResult);
                } else {
                    MRequestEnd();
                    onMFailure(statusCode, baseResult, null);
                    onMFailure(statusCode, headers, responseBody, baseResult, null);
                }
            } else { // 几乎不存在
                MRequestEnd();
                onMFailure(statusCode, null, null);
                onMFailure(statusCode, headers, responseBody, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MRequestEnd();
            onMFailure(statusCode, null, e);
            onMFailure(statusCode, headers, responseBody, null, e);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        MRequestEnd();
        onMFailure(statusCode, null, throwable);
        onMFailure(statusCode, headers, responseBody, null, throwable);
    }

    public abstract void onMSuccess(Result result);
    public abstract void onMFailure(int statusCode, @Nullable Result result, @Nullable Throwable throwable);

    public void onMSuccess(int statusCode, Header[] headers, byte[] responseBody, Result result){}
    public void onMFailure(int statusCode, Header[] headers, byte[] responseBody, @Nullable Result result, @Nullable Throwable throwable){}
    /**
     * 成功失败都会执行的操作，如关闭加载动画，都重写此方法
     */
    public void MRequestEnd(){}
}
