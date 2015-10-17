package com.androidbase.view.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dianmi365.hr365.ui.LoginActivity;
import com.dianmi365.hr365.util.Utility;
import com.dianmi365.widget.TitleBar;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

public class BaseFragmentActivity extends FragmentActivity {

    public boolean isLoading = false;
    public Dialog loadingDialog;
    public View footer;
    public Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        footer = LayoutInflater.from(this).inflate(R.layout.footer_view, null);
        footer.setVisibility(View.GONE);
        loadingDialog = Utility.createLoadingDialog(context, "加载中..");
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

    public JSONObject getJsonObject(byte[] bytes) throws Exception {
        return new JSONObject(new String(bytes));
    }

    public void loadStart(boolean showFooter) {
        if (isLoading) {
            return;
        }
        if (showFooter) {
            footer.setVisibility(View.VISIBLE);
        } else {
            if (loadingDialog != null) {
                // loadingDialog.show();
            }
        }
        isLoading = true;
    }

    public void loadEnd() {
        isLoading = false;
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        footer.setVisibility(View.GONE);
    }


    public void sendEvent(Object obj) {
        EventBus.getDefault().post(obj);
    }

    public void startActivity(Class mClass){
        startActivity(new Intent(context,mClass));
    }

    public void login() {
        startActivity(LoginActivity.class);
    }

    public void setTitle(String title) {
        TitleBar titleBar = (TitleBar) findViewById(R.id.v_title);
        titleBar.setTitle(title);
    }

    public TitleBar getTitleBar() {
        TitleBar titleBar = (TitleBar) findViewById(R.id.v_title);
        return titleBar;
    }

}
