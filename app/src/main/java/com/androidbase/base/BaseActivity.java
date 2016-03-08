package com.androidbase.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.androidbase.R;
import com.androidbase.entity.Result;
import com.androidbase.http.HttpHelper;
import com.androidbase.util.CountUtil;
import com.commons.support.util.DialogUtil;
import com.commons.support.util.EventUtil;
import com.commons.support.widget.TitleBar;

public abstract class BaseActivity extends Activity implements IBaseView, View.OnClickListener {

    public boolean isLoading = false;
    public Dialog loadingDialog;
    public Activity context;
    public HttpHelper httpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getViewRes() > 0)
            setContentView(getViewRes());
        this.context = this;
        httpHelper = HttpHelper.getInstance(context);
        loadingDialog = DialogUtil.createLoadingDialog(context, getString(R.string.loading));

        //初始化操作
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

    public <T extends View> T $T(@IdRes int id) {
        T view = (T) super.findViewById(id);
        return view;
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
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

    public void init() {
    }

    protected abstract void initView();

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

    @Override
    protected void onResume() {
        super.onResume();
        CountUtil.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CountUtil.onPause(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventUtil.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

    public void onEvent(Object obj) {
    }
    public void onEventMainThread(Object obj) {
    }


}
