package com.androidbase.presenter;

import com.androidbase.model.LoginModel;
import com.androidbase.model.imodel.ILoginModel;
import com.androidbase.view.iview.ILoginView;
import com.commons.support.http.HttpResultHandler;

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
        loginModel.login(userName, pas, new HttpResultHandler() {
            @Override
            public void onSuccess(com.commons.support.entity.Result result) {
                if (result.isResult()) {
                    loginView.loginSuccess();
                } else {
                    loginView.loginFail(result.getMsg());
                }
            }
        });
    }

}
