package com.androidbase.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.androidbase.R;
import com.androidbase.entity.Result;
import com.androidbase.util.CountUtil;
import com.commons.support.util.DialogUtil;
import com.commons.support.util.EventUtil;
import com.commons.support.widget.TitleBar;

public abstract class BaseFragmentActivity extends FragmentActivity implements IBaseView {

    public boolean isLoading = false;
    public Dialog loadingDialog;
    public Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewRes());
        this.context = this;
        loadingDialog = DialogUtil.createLoadingDialog(context, "加载中..");

        init();

        initView();
        request();
    }

    public <T extends View> T findView(@IdRes int id){
        return (T)super.findViewById(id);
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    protected boolean resultSuccess(Result result,boolean ... callRequestEnd){
        if (!result.isResult()) {
            showToast(result.getMsg());
        }
        if (callRequestEnd != null) {
            if (result.isRequestEnd()) {
                requestEnd();
            }
        }
        return result.isResult();
    }

    public void startActivity(Class mClass) {
        startActivity(new Intent(context, mClass));
    }

    public void setTitle(String title) {
        getTitleBar().setTitle(title);
    }

    public TitleBar getTitleBar() {
        TitleBar titleBar = (TitleBar) findViewById(R.id.v_title);
        return titleBar;
    }


    protected abstract void initView();

    protected abstract Activity getCountContext();

    public void init() {
    }

    protected boolean isSupportEvent() {
        return false;
    }

    protected void onEvent(Object obj) {
    }

    @Override
    public void onResume() {
        super.onResume();
        CountUtil.onResume(getCountContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        CountUtil.onPause(getCountContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isSupportEvent()) {
            EventUtil.register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }


    @Override
    public void request() {
    }
    /**
     * 这些对于FragmentActivity不常用到，作为备选重写方法
     */
    @Override
    public void requestSuccess(Result result, Class... entity) {
    }
    @Override
    public void requestEnd() {
    }
}
