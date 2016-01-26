package com.androidbase.model.imodel;


import com.commons.support.http.HttpResultHandler;

/**
 * Created by qianjin on 2015/9/25.
 */
public interface ILoginModel {
    void login(String userName, String pas, HttpResultHandler responseHandler);
}
