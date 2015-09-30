package com.androidbase.data.http;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.androidbase.entity.Result;
import com.androidbase.util.LogUtil;
import com.commons.support.db.cache.Cache;
import com.commons.support.db.cache.CacheUtil;
import com.commons.support.entity.JSONUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


/**
 * 只能用于自家服务端返回的数据处理
 * Created by Wang on 2015/9/22.
 */
public abstract class MAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

    private String cacheKey;
    private String cacheValue;
    private long timeout;

    public void initCache(String cacheKey) {
        timeout = 0;
        this.cacheKey = cacheKey;
    }

    public void initCache(String cacheKey, long timeout) {
        this.timeout = timeout;
        this.cacheKey = cacheKey;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        MRequestEnd();
        try {
            Result baseResult = JSON.parseObject(responseBody, Result.class);
            if (baseResult != null) {
                if (baseResult.isResult()) {

                    saveCache(baseResult);

                    if (TextUtils.isEmpty(cacheValue)) {

                        baseResult.setNeedRefresh(true);
                        onMSuccess(baseResult);
                        onMSuccess(statusCode, headers, responseBody, baseResult);

                    } else {
//                        if (!cacheValue.equals(new String(responseBody))) {

                        if (!isDataEqualsCache(baseResult)) {

                            LogUtil.log("get data and cache is not equals, need refresh!");
                            LogUtil.log("cache  str:" + cacheValue);
                            LogUtil.log("result str:" + JSONUtil.toJSONString(baseResult));

                            baseResult.setNeedRefresh(true);
                            onMSuccess(baseResult);
                            onMSuccess(statusCode, headers, responseBody, baseResult);
                        } else {

                            LogUtil.log("get data and cache is equals, not need refresh!");
                            baseResult.setNeedRefresh(false);
                            onMSuccess(baseResult);
                            onMSuccess(statusCode, headers, responseBody, baseResult);
                        }
                    }
                } else {
                    onMFailure(statusCode, baseResult, null);
                    onMFailure(statusCode, headers, responseBody, baseResult, null);
                }
            } else { // 几乎不存在
                onMFailure(statusCode, null, null);
                onMFailure(statusCode, headers, responseBody, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(cacheKey)) {
            cacheValue = CacheUtil.getCacheValue(cacheKey);
            if (!TextUtils.isEmpty(cacheValue)) {
                Result result = JSONUtil.parseObject(cacheValue, Result.class);
                result.setNeedRefresh(true);
                onMSuccess(result);
            }
        }
    }

    private boolean isDataEqualsCache(Result dataResult) {
        Result cacheResult = JSONUtil.parseObject(cacheValue, Result.class);
        JSONObject cacheObj = JSON.parseObject(cacheResult.getData());
        JSONObject resultObj = JSON.parseObject(dataResult.getData());
        cacheObj.remove("sincetime");
        resultObj.remove("sincetime");
        return JSONUtil.toJSONString(cacheObj).equals(JSONUtil.toJSONString(resultObj));
    }

    private void saveCache(Result result) {
        if (!TextUtils.isEmpty(cacheKey)) {
            LogUtil.log("cacheKey is not empty,save cache!");
            Cache cache = new Cache();
            cache.setKey(cacheKey);
            cache.setTimeout(timeout);
            cache.setCurrentTime(System.currentTimeMillis());
            cache.setValue(JSONUtil.toJSONString(result));
            CacheUtil.save(cache);
        } else {
            LogUtil.log("cacheKey is empty, do not save cache!");
        }
    }


    public abstract void onMSuccess(Result result);

    public abstract void onMFailure(int statusCode, @Nullable Result result, @Nullable Throwable throwable);

    public void onMSuccess(int statusCode, Header[] headers, byte[] responseBody, Result result) {
    }
    public void onMFailure(int statusCode, Header[] headers, byte[] responseBody, @Nullable Result result, @Nullable Throwable throwable) {
    }
    /**
     * 成功失败都会执行的操作，如关闭加载动画，都重写此方法
     */
    public void MRequestEnd() {
    }
}
