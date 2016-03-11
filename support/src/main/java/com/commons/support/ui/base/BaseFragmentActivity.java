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
import com.commons.support.log.LogUtil;
import com.commons.support.ui.UIHelper;
import com.commons.support.util.BaseJava;
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
        int contentValueRes = UIHelper.getContentViewRes(this);
        if (contentValueRes > 0) {
            setContentView(contentValueRes);
        } else if (getViewRes() > 0) {
            setContentView(getViewRes());
        } else {
            LogUtil.e(this.getClass().getSimpleName(), "当前activity未设置layout");
        }

        this.context = this;

        loadingDialog = DialogUtil.createLoadingDialog(context, getString(R.string.loading));

        //初始化操作
        init();

        initView();
        request();
    }

    @Override
    public int getViewRes() {
        return 0;
    }


    public void init() {
        LogUtil.log("Activity class name is : " + context.getClass().getName());
    }

    protected abstract void initView();

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

    /**
     * EventBus写onEvent时避免写错，直接Override
     */
    public void onEvent(Object obj) {
    }

    /**
     * *****************************************以上是Activity自带方法*********************************************
     */


    /**
     * ************UI帮助类*****************
     */
    public <T extends View> T $(@IdRes int id) {
        return UIHelper.$(this, id, this);
    }

    public <T extends View> T $T(@IdRes int id) {
        return UIHelper.$T(this, id);
    }

    public void showToast(String msg) {
        UIHelper.showToast(context, msg);
    }

    public void setTitle(String title) {
        getTitleBar().setTitle(title);
    }

    public TitleBar getTitleBar() {
        return $(R.id.v_title);
    }

    public View inflate(@LayoutRes int layout) {
        return LayoutInflater.from(this).inflate(layout, null);
    }

    public void startActivity(Class mClass) {
        startActivity(new Intent(context, mClass));
    }

    public void startActivity(Context context, Class<?> cls, @Nullable Map<String, Serializable> extras) {
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

    public void startActivity(Context context, Class<?> cls, String key, Serializable value) {
        Intent intent = new Intent(context, cls);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(key, value);
        context.startActivity(intent);
    }


    /**
     * ********************Java帮助类*****************************
     */
    public boolean objectNotNull(Object object) {
        return BaseJava.objectNotNull(object);
    }

    public boolean objectIsNull(Object object) {
        return BaseJava.objectIsNull(object);
    }

    public boolean strNotEmpty(String str) {
        return BaseJava.strNotEmpty(str);
    }

    public boolean strIsEmpty(String str) {
        return BaseJava.strIsEmpty(str);
    }

    public boolean listNotEmpty(List list) {
        return BaseJava.listNotEmpty(list);
    }

    public boolean listIsEmpty(List list) {
        return BaseJava.listIsEmpty(list);
    }
    /**
     * *********************数据处理帮助类***********************
     */
    public <T extends Serializable> T getSerializableExtra(String key) {
        return (T) getIntent().getSerializableExtra(key);
    }

    public int getIntExtra(String key) {
        return getIntent().getIntExtra(key, 0);
    }

    public String getStringExtra(String key) {
        return getIntent().getStringExtra(key);
    }

    public boolean getBooleanExtra(String key) {
        return getIntent().getBooleanExtra(key, false);
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

}
