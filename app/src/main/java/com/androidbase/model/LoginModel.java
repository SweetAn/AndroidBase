package com.androidbase.model;

import com.androidbase.commons.MAsyncHttpResponseHandler;
import com.androidbase.data.http.HttpHelper;
import com.androidbase.model.imodel.ILoginModel;

/**
 * Created by qianjin on 2015/9/25.
 */
public class LoginModel implements ILoginModel {
    @Override
    public void login(String userName, String pas, MAsyncHttpResponseHandler responseHandler) {
        HttpHelper.login(userName, pas, responseHandler);
    }
}
