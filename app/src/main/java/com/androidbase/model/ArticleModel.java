package com.androidbase.model;

import com.androidbase.entity.Page;
import com.androidbase.http.HttpResultHandler;
import com.androidbase.util.LogUtil;

/**
 * Created by qianjin on 2015/9/29.
 */
public class ArticleModel {

    public void getArticles(Page page,HttpResultHandler handler){
//        if(page.isNeedCache()) {
//            handler.initCache(getCacheKey("getArticles"));
//        }
//        HttpHelper.getInstance().getArticleList(page, handler);
    }

    public String getCacheKey(String key) {
        String cacheKey = getClass().getName() + "-" + key;
        LogUtil.log("cache key is :" + key);
        return cacheKey;
    }



}
