package com.androidbase.data.http;
import android.text.TextUtils;

import com.androidbase.entity.Result;
import com.androidbase.util.CacheUtil;
import com.commons.support.db.cache.CacheDB;
import com.commons.support.entity.JSONUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
/**
 * 新的联网处理回调
 * Created by qianjin on 2015/10/1.
 */
public abstract class HttpResultHandler extends AsyncHttpResponseHandler {

    private String cacheKey;
    private String cacheValue;
    private long timeout;

    public void initCache(String cacheKey) {
        initCache(cacheKey, 0);
    }

    public void initCache(String cacheKey, long timeout) {
        this.timeout = timeout;
        this.cacheKey = cacheKey;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Result baseResult = JSONUtil.parseObject(responseBody, Result.class);
        if (baseResult != null) {
            if (baseResult.isResult()) {

                CacheUtil.saveResultCache(baseResult, cacheKey, timeout);

                if (TextUtils.isEmpty(cacheValue) || !CacheUtil.isDataEqualsCache(cacheKey, baseResult)) {
                    baseResult.setNeedRefresh(true);
                } else {
                    baseResult.setNeedRefresh(false);
                }
            }
        } else {
            baseResult = new Result();
            baseResult.setErrorMsg("没有解析出结果！");
            baseResult.setResult(false);
        }

        onSuccess(baseResult);

        requestEnd();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        throwable.printStackTrace();

        Result baseResult = new Result();
        baseResult.setErrorMsg("请求服务器失败！");
        baseResult.setResult(false);
        onSuccess(baseResult);

        requestEnd();
    }

    @Override
    public void onStart() {
        super.onStart();
        getCache();
    }

    public void getCache() {
        if (!TextUtils.isEmpty(cacheKey)) {
            cacheValue = CacheDB.getCacheValue(cacheKey);
            if (!TextUtils.isEmpty(cacheValue)) {
                Result result = JSONUtil.parseObject(cacheValue, Result.class);
                result.setNeedRefresh(true);
                onSuccess(result);
            }
        }
    }

    public abstract void onSuccess(Result result);

    public void requestEnd() {
    }
}
