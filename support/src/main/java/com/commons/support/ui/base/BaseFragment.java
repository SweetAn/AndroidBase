package com.commons.support.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commons.support.entity.Result;
import com.commons.support.log.LogUtil;
import com.commons.support.ui.UIHelper;
import com.commons.support.util.DialogUtil;
import com.commons.support.util.EventUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class BaseFragment extends Fragment implements IBaseView, View.OnClickListener {

    public boolean isLoading = false;
    public Dialog loadingDialog;
    public Activity context;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        loadingDialog = DialogUtil.createLoadingDialog(context, "加载中..");
    }

    public <T extends View> T $(@IdRes int id) {
        return UIHelper.$(view, id, this);
    }

    public <T extends View> T $(View view, @IdRes int id) {
        return UIHelper.$(view, id, this);
    }

    public <T extends View> T $T(@IdRes int id) {
        return UIHelper.$T(view, id);
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
        return (T) getActivity().getIntent().getSerializableExtra(key);
    }

    public View inflate(@LayoutRes int layout) {
        return LayoutInflater.from(getActivity()).inflate(layout, null);
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


    public int getContentViewRes() {
        try {
            Class<?> clazz = this.getClass();
            ContentView contentView = clazz.getAnnotation(ContentView.class);
            if (contentView != null) {
                return contentView.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getViewRes() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getViewRes() > 0) {
            view = inflater.inflate(getViewRes(), container, false);
        } else if (getContentViewRes() > 0) {
            view = inflater.inflate(getContentViewRes(), container, false);
        } else {
            throw new IllegalArgumentException("请至少用一种方法设置视图id，复写getViewRes或在类名上方加注解ContentView！");
        }

        init();

        initView(view);
        request();

        return view;
    }

    public void startActivity(Class mClass) {
        startActivity(new Intent(context, mClass));
    }

    public void showToast(String msg) {
        UIHelper.showCenterToast(context, msg);
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


    protected abstract void initView(View view);

    protected String getCountViewTitle() {
        return this.getClass().getSimpleName();
    }


    public void onEvent(Object obj) {
    }

    protected void init() {
        LogUtil.log("class name activity: " + this.getClass().getName());
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


}
