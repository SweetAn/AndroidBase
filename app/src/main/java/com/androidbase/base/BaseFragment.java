package com.androidbase.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidbase.entity.Result;
import com.androidbase.util.CountUtil;
import com.commons.support.util.DialogUtil;
import com.commons.support.util.EventUtil;

public abstract class BaseFragment extends Fragment implements IBaseView,View.OnClickListener {

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

    public <T extends View> T findView(@IdRes int id) {
        return (T) view.findViewById(id);
    }

    public <T extends View> T findViewWithClick(@IdRes int id) {
        T v = (T) view.findViewById(id);
        v.setOnClickListener(this);
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getViewRes(), container, false);

        init();

        initView(view);
        request();

        return view;
    }

    public void startActivity(Class mClass) {
        startActivity(new Intent(context, mClass));
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
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


    protected abstract void initView(View view);

    protected abstract String getCountViewTitle();

    /**
     * TODO 如果返回为 true 请重写 onEvent() 方法
     * @return
     */
    protected boolean isSupportEvent() {
        return false;
    }

    protected void onEvent(Object obj) {
    }

    protected void init(){
    }

    @Override
    public void onResume() {
        super.onResume();
        CountUtil.onPageStart(context, getCountViewTitle());
    }

    @Override
    public void onPause() {
        super.onPause();
        CountUtil.onPageEnd(context, getCountViewTitle());
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


}
