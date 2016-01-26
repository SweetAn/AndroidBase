package com.commons.support.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.commons.support.R;
import com.commons.support.entity.Result;
import com.commons.support.ui.UIHelper;
import com.commons.support.util.DialogUtil;
import com.commons.support.util.EventUtil;
import com.commons.support.widget.TitleBar;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class BaseFragmentActivity extends FragmentActivity implements IBaseView {

    public boolean isLoading = false;
    public Dialog loadingDialog;
    public Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UIHelper.getContentViewRes(this) > 0) {
            setContentView(UIHelper.getContentViewRes(this));
        } else if (getViewRes() > 0) {
            setContentView(getViewRes());
        } else {
            throw new IllegalArgumentException("请至少用一种方法设置视图id，复写getViewRes或在类名上方加注解ContentView！");
        }
        this.context = this;
        loadingDialog = DialogUtil.createLoadingDialog(context, "加载中..");

        init();

        initView();
        request();
    }

    @Override
    public int getViewRes() {
        return 0;
    }

    public <T extends View> T $(@IdRes int id) {
        return UIHelper.$(this, id, this);
    }

    public <T extends View> T $T(@IdRes int id) {
        return UIHelper.$T(this, id);
    }

    public boolean objectNotNull(Object object) {
        if (object == null) {
            return false;
        }
        return true;
    }

    public boolean objectIsNull(Object object) {
        return !objectNotNull(object);
    }

    public boolean strNotEmpty(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return true;
    }

    public boolean listNotEmpty(List list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        return true;
    }

    public <T extends Serializable> T getSerializableExtra(String key) {
        return (T) getIntent().getSerializableExtra(key);
    }

    public View inflate(@LayoutRes int layout) {
        return LayoutInflater.from(this).inflate(layout, null);
    }

    public void start(Context context, Class<?> cls, @Nullable Map<String, Serializable> extras) {
        Intent intent = new Intent(context, cls);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (extras != null && extras.size() > 0)
            for (String key : extras.keySet()) {
                intent.putExtra(key, extras.get(key));
            }
        context.startActivity(intent);
    }

    public void start(Context context, Class<?> cls, String key, Serializable value) {
        Intent intent = new Intent(context, cls);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(key, value);
        context.startActivity(intent);
    }


    public void showToast(String msg) {
        UIHelper.showToast(context, msg);
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


    protected abstract void initView();

    public void init() {
    }

    public void onEvent(Object obj) {
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
