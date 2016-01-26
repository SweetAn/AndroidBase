package com.androidbase.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AbsListView;

import com.androidbase.R;
import com.androidbase.entity.Result;
import com.androidbase.util.CountUtil;
import com.androidbase.util.ToastUtil;
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
        loadingDialog = DialogUtil.createLoadingDialog(context, getString(R.string.loading));

        init();

        initView();
        request();
    }

    public <T extends View> T $(@IdRes int id) {
        T view = (T) super.findViewById(id);
        if (!(view instanceof AbsListView)) {
            view.setOnClickListener(this);
        }
        return view;
    }

    public void showToast(String msg) {
        ToastUtil.showToast(context, msg);
    }

    protected boolean resultSuccess(Result result, boolean... callRequestEnd) {
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

    protected void init() {
    }

    protected abstract void initView();

    public void onEvent(Object obj) {
    }

    @Override
    public void onResume() {
        super.onResume();
        CountUtil.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        CountUtil.onPause(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventUtil.register(this);
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
