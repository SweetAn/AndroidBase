package com.androidbase.view.iview;

/**
 * Created by qianjin on 2015/9/25.
 */
public interface ILoginView {
    boolean loginWithPas(String phoneNo, String pas);
    boolean loginWithVerifyCode(String phoneNo, String verifyCode);
    boolean getVerifyCode(String phoneNo);
}
