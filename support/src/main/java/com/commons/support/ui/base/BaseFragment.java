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

import com.commons.support.R;
import com.commons.support.entity.Result;
import com.commons.support.log.LogUtil;
import com.commons.support.ui.UIHelper;
import com.commons.support.util.BaseJava;
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
        loadingDialog = DialogUtil.createLoadingDialog(context, getString(R.string.loading));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int contentResValue = UIHelper.getContentViewRes(this);
        if (contentResValue > 0) {
            view = inflater.inflate(contentResValue, container, false);
        } else if (getViewRes() > 0) {
            view = inflater.inflate(getViewRes(), container, false);
        } else {
            throw new IllegalArgumentException("请至少用一种方法设置视图id，复写getViewRes或在类名上方加注解ContentView！");
        }

        init();

        initView(view);
        request();

        return view;
    }

    @Override
    public int getViewRes() {
        return 0;
    }

    protected void init() {
        LogUtil.log("Fragment class name is : " + this.getClass().getName());
    }

    protected abstract void initView(View view);

    //统计时用到的别名，默认为类的名字
    protected String getFragmentTitle() {
        return this.getClass().getSimpleName();
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

    //ui help methods-----------------------------------------------------------------------------------
    public <T extends View> T $(@IdRes int id) {
        return UIHelper.$(view, id, this);
    }

    public <T extends View> T $(View view, @IdRes int id) {
        return UIHelper.$(view, id, this);
    }

    public <T extends View> T $T(@IdRes int id) {
        return UIHelper.$T(view, id);
    }

    public void showToast(String msg) {
        UIHelper.showCenterToast(context, msg);
    }

    public View inflate(@LayoutRes int layout) {
        return LayoutInflater.from(getActivity()).inflate(layout, null);
    }

    //end of ui help methods----------------------------------------------------------------------------


    //java base help methods----------------------------------------------------------------------------
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
    //end of java base help methods-------------------------------------------------------------------

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


    public void startActivity(Class mClass) {
        startActivity(new Intent(context, mClass));
    }

    //data help methods
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
