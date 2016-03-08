package com.androidbase.http;

import android.content.Context;

import com.androidbase.BuildConfig;
import com.androidbase.commons.Constants;
import com.androidbase.entity.Page;
import com.commons.support.db.config.ConfigUtil;
import com.commons.support.http.BaseHttpHelper;
import com.commons.support.util.Utility;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianjin on 2016/1/26.
 */
public class HttpHelper extends BaseHttpHelper {

    private static HttpHelper helper;

    Map<String, String> headers;

    public HttpHelper(Context context) {
        super(context);
        //TODO 做一些其它配置
    }

    public static HttpHelper getInstance(Context context) {
        if (helper == null) {
            helper = new HttpHelper(context);
        }
        return helper;
    }

    @Override
    public String getDevUrl() {
        return "http://uatapi-dev.cbmweibao.eebochina.com:8030/1.0/";
    }

    @Override
    public String getReleaseUrl() {
        return "http://ubmapi.cbmweibao.eebochina.com/1.0/";
    }

    @Override
    public boolean isDev() {
        return !BuildConfig.API_ENV;
    }


    @Override
    public Map<String, String> getHeaders() {
        if (objectIsNull(headers)) {
            headers = new HashMap<>();
            headers.put("Keys", getUserKey());
            headers.put("mfg", Utility.getSource(context));
            headers.put("ver", Utility.getAppVersionName(context) + "," + Utility.getAppVersionCode(context));
            headers.put("os", "1");
        } else {
            String keys = headers.get("Keys");
            String userKey = getUserKey();
            if (!keys.equals(userKey)) {
                headers.put("Keys", userKey);
            }
        }
        return headers;
    }

    /**
     * 参数编码
     *
     * @return
     */
    public static String encode(String s) {
        if (s == null) {
            return "";
        }
        try {
            return URLEncoder.encode(s, "UTF-8").replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~").replace("#", "%23");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static String url(String url){
        return BASE_URL + url;
    }

    public String getUserKey() {
        return ConfigUtil.getConfigValue(Constants.KEY);
    }

    //---------------------------分隔线----------------------------//



    // ------------ 七牛相关start-----------------//

    /**
     * 上传图片至七牛
     *
     * @param key
     * @param imgfile
     * @param token
     * @return
     */
    public void uploadPicture(String key, String token, File imgfile, AsyncHttpResponseHandler handler) {
        try {
            RequestParams params = new RequestParams();
            params.put("key", key);
            params.put("token", token);
            params.put("file", imgfile);
            post("http://up.qiniu.com/", params, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取七牛上传token
     *
     * @return
     * @throws Exception
     */
    public void getUploadToken(String picName, AsyncHttpResponseHandler handler) {
        try {
            JSONObject json = new JSONObject();
            json.put("pic_name", picName);
            json.put("retry_count", "1");
            json.put("content_type", "user_photo");
            StringEntity entity = new StringEntity(json.toString());
            post(BASE_URL + "upload/get_token", entity, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUploadTokens(String picNames, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pic_names", picNames);
        params.put("retry_count", "1");
        params.put("ticket_type", "threadpost");
        get(BASE_URL + "upload/token/morepicture", params, handler);
    }

    // ------------ 七牛相关end-----------------//

    public void getFaqList(Page page,AsyncHttpResponseHandler handler){
        get(url("faqlist/257"), page.getParams(), handler);
    }

    public void getArticleList(Page page,AsyncHttpResponseHandler handler){
        get(url("articlelist/257"),page.getParams(),handler);
    }

    public void downloadApk(String url, AsyncHttpResponseHandler handler) {
        get(url, handler);
    }

}
