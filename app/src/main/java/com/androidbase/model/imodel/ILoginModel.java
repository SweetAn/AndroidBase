package com.androidbase.model.imodel;

import com.androidbase.data.http.MAsyncHttpResponseHandler;

/**
 * Created by qianjin on 2015/9/25.
 */
public interface ILoginModel {
    void login(String userName, String pas, MAsyncHttpResponseHandler responseHandler);
}
