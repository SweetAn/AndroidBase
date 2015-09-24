package com.androidbase.commons;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.androidbase.entity.Result;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

/**
 * 只能用于自家服务端返回的数据处理
 * Created by Wang on 2015/9/22.
 */
public abstract class MAsyncHttpResponseHandler extends TextHttpResponseHandler {

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        try {
            Result baseResult = JSON.parseObject(responseString, Result.class);
            if (baseResult != null) {
                if(baseResult.isResult()) {
                    MBoth();
                    onMSuccess(statusCode, headers, responseString, baseResult);
                } else {
                    MBoth();
                    onMFailure(statusCode, headers, responseString, baseResult, null);
                }
            } else {
                MBoth();
                onMFailure(statusCode, headers, responseString, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MBoth();
            onMFailure(statusCode, headers, responseString, null, null);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        MBoth();
        onMFailure(statusCode, headers, responseString, null, throwable);
//        if(statusCode==502) {
//            this.sendRetryMessage(4);
//        }
    }

//    @Override
//    public void onRetry(int retryNo) {
//        LogUtil.log("0923..onRetry.retryNo=" + retryNo);
//        super.onRetry(retryNo);
//    }

    public abstract void onMSuccess(int statusCode, Header[] headers, String responseString, Result result);

    public abstract void onMFailure(int statusCode, Header[] headers, String responseString, @Nullable Result result, @Nullable Throwable throwable);

    /**
     * 成功失败都会执行的操作，如关闭加载动画，都重写此方法
     */
    public void MBoth(){}
}
