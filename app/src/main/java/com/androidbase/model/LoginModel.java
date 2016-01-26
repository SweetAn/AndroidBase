package com.androidbase.model;

import com.androidbase.model.imodel.ILoginModel;
import com.androidbase.util.LogUtil;
import com.commons.support.http.HttpResultHandler;

/**
 * Created by qianjin on 2015/9/25.
 */
public class LoginModel implements ILoginModel {
    @Override
    public void login(String userName, String pas, final HttpResultHandler responseHandler) {

//        final String key = getCacheKey(Method.class);
//        final String resultStr = ConfigUtil.getConfigValue(key);
//        if (!TextUtils.isEmpty(resultStr)) {
//            Result result = JSONUtil.parseObject(resultStr, Result.class);
//            responseHandler.onMSuccess(result);
//        }
//
//        HttpHelper.login(userName, pas, new MAsyncHttpResponseHandler() {
//            @Override
//            public void onMSuccess(Result result) {
//                String temp = JSONUtil.toJSONString(result);
//                if (!temp.equals(resultStr)) {
//                    responseHandler.onMSuccess(result);
//                    //cache
//                    Config config = new Config(key, JSONUtil.toJSONString(result));
//                    ConfigUtil.save(config);
//                }
//            }
//            @Override
//            public void onMFailure(int statusCode, @Nullable Result result, @Nullable Throwable throwable) {
//                responseHandler.onMFailure(statusCode, result, throwable);
//            }
//        });
//        responseHandler.initCache(getCacheKey("login"));
//        HttpHelper.login(userName, pas, responseHandler);

    }

    public String getCacheKey(String key) {
        String cacheKey = getClass().getName() + "-" + key;
        LogUtil.log("cache key is :" + key);
        return cacheKey;
    }


}



