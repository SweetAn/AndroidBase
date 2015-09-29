package com.androidbase.data.http;

import com.androidbase.BuildConfig;
import com.androidbase.entity.Page;
import com.commons.support.db.config.ConfigUtil;
import com.commons.support.log.LogUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by qianjin on 2015/9/25.
 */
public class HttpHelper extends BaseHttpHelper {

    public static String initUrl(String path) {
        if (BuildConfig.DEBUG) {
            return "http://" + ConfigUtil.getConfigValue(DEV_HOST) + ":8030/" + path;
            //return "http://" + DEV_HOST + ":8030/" + path;
        }
        return "http://" + ConfigUtil.getConfigValue(RELEASE_HOST) + "/" + path;
        //return "http://" + RELEASE_HOST + "/" + path;
    }

    public static void login(String userName, String pas, final AsyncHttpResponseHandler responseHandler) {
        LogUtil.log("Call login in HttpHelper");
        Page page = new Page();
        getFaqList(page,responseHandler);

//        RequestParams params = new RequestParams();
//        params.put("userName", userName);
//        params.put("pas", pas);
//        String url = initUrl("login");
//        LogUtil.log("url is :" + url);
//        post(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                responseHandler.onSuccess(statusCode, headers, responseBody);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                responseHandler.onFailure(statusCode, headers, responseBody, error);
//            }
//        });
    }


    public static void getFaqList(Page page,AsyncHttpResponseHandler handler){
        //http://api-test.365hr.com:8030/faqlist/257?city_id=257&p=1&st=
        get(initUrl("faqlist/257"),page.getParams(),handler);
    }

    public static void getArticleList(Page page,AsyncHttpResponseHandler handler){
        //http://api-test.365hr.com:8030/articlelist/257?p=1&st=
        get(initUrl("articlelist/257"),page.getParams(),handler);
    }



}
