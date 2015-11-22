package com.androidbase.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidbase.R;
import com.androidbase.base.BaseNoInitDataActivity;
import com.androidbase.presenter.LoginPresenter;
import com.androidbase.view.iview.ILoginView;
import com.commons.support.util.DialogUtil;

public class LoginActivity extends BaseNoInitDataActivity implements ILoginView {


    private boolean pasType = false;
    private boolean isShowPas = false;
    ImageView btnIsShowPas;
    EditText etPas;
    EditText etPhone;
    EditText etVerifyCode;


    TextView tvVerifyCode;
    TextView btnNoGetVerCode;
    private int cnt = 60;
    private int activateCnt = 0;

    LoginPresenter loginPresenter;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            cnt--;
            if (cnt == 0) {
                cnt = 60;
                tvVerifyCode.setEnabled(true);
                etPhone.setEnabled(true);
                tvVerifyCode.setTextColor(getResources().getColor(R.color.main_color));
                tvVerifyCode.setText("重新获取");
                btnNoGetVerCode.setVisibility(View.VISIBLE);
            } else {
                tvVerifyCode.setText("重新获取 " + cnt + "秒");
                handler.sendEmptyMessageDelayed(0, 1000);
            }
            return true;
        }
    });


    @Override
    protected void initView() {
        loginPresenter = new LoginPresenter(this);

        setTitle("登录/注册");

        loadingDialog = DialogUtil.createLoadingDialog(context, "登录中..");

        final View llVerifyType = findViewById(R.id.ll_verify_type);
        final View llPasType = findViewById(R.id.ll_pas_type);
        llPasType.setVisibility(View.GONE);
        final TextView tv = (TextView) findViewById(R.id.btn_change_login_type);
        btnNoGetVerCode = (TextView) findViewById(R.id.btn_no_get_ver_code);
        btnNoGetVerCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvVerifyCode = (TextView) findViewById(R.id.btn_get_verify_code);
        tvVerifyCode.setVisibility(View.VISIBLE);
        tvVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsObtain();
            }
        });


        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pasType) {
                    llVerifyType.setVisibility(View.VISIBLE);
                    //btnNoGetVerCode.setVisibility(View.VISIBLE);
                    llPasType.setVisibility(View.GONE);
                    tv.setText(getString(R.string.use_pas_login));
                    tvVerifyCode.setVisibility(View.VISIBLE);
                    pasType = false;
                } else {
                    llVerifyType.setVisibility(View.GONE);
                    llPasType.setVisibility(View.VISIBLE);
                    //btnNoGetVerCode.setVisibility(View.GONE);
                    tvVerifyCode.setVisibility(View.GONE);
                    tv.setText(getString(R.string.use_verify_login));
                    pasType = true;
                }
            }
        });

        btnNoGetVerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = DialogUtil.createAlertConfirmDialog(context, "您可以尝试语音验证码，点击确定我们将给您电话告知验证码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        smsObtainByVoice();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        TextView btnRegister = (TextView) findViewById(R.id.btn_register);
        //btnRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(RegisterActivity.class);
            }
        });


        etPas = (EditText) findViewById(R.id.et_pas);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etVerifyCode = (EditText) findViewById(R.id.et_verify);

        btnIsShowPas = (ImageView) findViewById(R.id.btn_is_show_pas);
        btnIsShowPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowPas) {
                    isShowPas = false;
                    btnIsShowPas.setImageResource(R.drawable.ic_eye_close);
                    etPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    btnIsShowPas.setImageResource(R.drawable.ic_eye_open);
                    isShowPas = true;
                    etPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                etPas.setSelection(etPas.getText().toString().length());
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llVerifyType.getVisibility() == View.VISIBLE) {
                    verifyCodeLogin();
                } else {
                    phoneNumberLogin();
                }

            }
        });

    }

    private void smsObtainByVoice() {

        String phoneNumber = etPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("请输入电话号码");
            return;
        }
        etVerifyCode.requestFocus();
        tvVerifyCode.setEnabled(false);

    }


    private void smsObtain() {

        String phoneNumber = etPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("请输入电话号码");
            return;
        }

        etVerifyCode.requestFocus();
        tvVerifyCode.setEnabled(false);
        final Dialog smsLoading = DialogUtil.createLoadingDialog(context, "获取中..");
        smsLoading.show();

    }


    private void phoneNumberLogin() {

        String phone = etPhone.getText().toString();
        String pas = etPas.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(pas)) {
            showToast("请输入密码");
            return;
        }

        if (isLoading) {
            return;
        }

        loginPresenter.login(phone, pas);

    }


    private void verifyCodeLogin() {

        String phone = etPhone.getText().toString();
        String code = etVerifyCode.getText().toString();

        String phoneNumber = etPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("请输入电话号码");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            showToast("请输入验证码");
            return;
        }

        if (isLoading) {
            return;
        }

    }


    @Override
    public void loginSuccess() {
        //TODO 登录成功后，处理UI逻辑
        requestEnd();
        showToast("登录成功！");
    }

    @Override
    public void loginFail(String msg) {
        requestEnd();
        showToast(msg);
    }

    @Override
    public int getViewRes() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                break;
        }
    }
}
