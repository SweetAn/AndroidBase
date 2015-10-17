package com.androidbase.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.androidbase.entity.Result;
import com.commons.support.db.cache.Cache;
import com.commons.support.db.cache.CacheDB;
import com.commons.support.entity.JSONUtil;
import com.commons.support.log.LogUtil;

/**
 * Created by qianjin on 2015/10/1.
 */
public class CacheUtil {

    public static final String ARTICLE_LIST = "article_list";


    private static boolean isDataEqualsCache(String cacheKey, String value) {
        String cacheValue = CacheDB.getCacheValue(cacheKey);
        if (cacheValue.equals(value)) {
            return true;
        }
        return false;
    }

    /**
     * 比较缓存和Http得到的数据，不比较数据中的sincetime
     * @param cacheKey
     * @param result
     * @return
     */
    public static boolean isDataEqualsCache(String cacheKey, Result result) {
        String cacheValue = CacheDB.getCacheValue(cacheKey);
        Result cacheResult = JSONUtil.parseObject(cacheValue, Result.class);
        JSONObject cacheObj = JSON.parseObject(cacheResult.getData());
        JSONObject resultObj = JSON.parseObject(result.getData());
        cacheObj.remove("sincetime");
        resultObj.remove("sincetime");
        return JSONUtil.toJSONString(cacheObj).equals(JSONUtil.toJSONString(resultObj));
    }

    public static void saveResultCache(Result result, String cacheKey, long timeout) {
        if (!TextUtils.isEmpty(cacheKey)) {
            LogUtil.log("cacheKey is not empty,save cache!");
            Cache cache = new Cache();
            cache.setKey(cacheKey);
            cache.setTimeout(timeout);
            cache.setCurrentTime(System.currentTimeMillis());
            cache.setValue(JSONUtil.toJSONString(result));
            CacheDB.save(cache);
        } else {
            LogUtil.log("cacheKey is empty, do not save cache!");
        }
    }

}
