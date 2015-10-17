package com.androidbase.view.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.androidbase.R;
import com.commons.support.util.DialogUtil;
import com.commons.support.widget.TitleBar;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends Activity implements IBaseView{

    public boolean isLoading = false;
    public Dialog loadingDialog;
    public Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewRes());
        this.context = this;
        loadingDialog = DialogUtil.createLoadingDialog(context, "加载中..");
        initView();
        getData();
    }

    protected void showToastCenter(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public void startActivity(Class mClass) {
        startActivity(new Intent(context, mClass));
    }

    public void sendEvent(Object obj) {
        EventBus.getDefault().post(obj);
    }

    public void setTitle(String title) {
        getTitleBar().setTitle(title);
    }

    public TitleBar getTitleBar() {
        TitleBar titleBar = (TitleBar) findViewById(R.id.v_title);
        return titleBar;
    }



    public abstract void initView();
    public abstract void getData();
    public abstract int getViewRes();

    public void init(){
        initView();
        getData();
    }



}
