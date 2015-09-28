package com.androidbase.presenter;

import android.support.annotation.Nullable;

import com.androidbase.commons.MAsyncHttpResponseHandler;
import com.androidbase.entity.Result;
import com.androidbase.model.LoginModel;
import com.androidbase.model.imodel.ILoginModel;
import com.androidbase.view.iview.ILoginView;

import org.apache.http.Header;

/**
 * Created by qianjin on 2015/9/25.
 */
public class LoginPresenter {

    private ILoginModel loginModel;
    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView){
        this.loginView = loginView;
        loginModel = new LoginModel();
    }



    public void login(String userName,String pas){
        loginModel.login(userName, pas, new MAsyncHttpResponseHandler() {
            @Override
            public void onMSuccess(int statusCode, Header[] headers, String responseString, Result result) {
                if (result.isResult()) {
                    loginView.loginSuccess();
                } else {
                    loginView.loginFail(result.getMsg());
                }
            }
            @Override
            public void onMFailure(int statusCode, Header[] headers, String responseString, @Nullable Result result, @Nullable Throwable throwable) {
                throwable.printStackTrace();
                loginView.loginFail("登录失败！");
            }
        });
    }

}
