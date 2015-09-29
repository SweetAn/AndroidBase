package com.androidbase;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.androidbase.adapter.BaseAdapter;
import com.androidbase.entity.Page;
import com.androidbase.entity.Result;
import com.commons.support.log.LogUtil;
import com.commons.support.util.DialogUtil;
import com.commons.support.widget.TitleBar;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

public class BaseActivity extends Activity {

    public boolean isLoading = false;
    public Dialog loadingDialog;
    public View footer;
    public Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        loadingDialog = DialogUtil.createLoadingDialog(context, "加载中..");
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

    public void requestStart() {
        if (isLoading) {
            return;
        }
        loadingDialog.show();
        isLoading = true;
    }

    public void requestEnd() {
        isLoading = false;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public void printLog(String msg) {
        LogUtil.log(msg);
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

    public void showListView(Page page,Result result,BaseAdapter adapter) {
        if (page.isRefresh()) {
            com.androidbase.util.LogUtil.log("refresh,need refresh is :" + result.isNeedRefresh());
            if(result.isNeedRefresh()) {
                adapter.refresh(page.getDataList());
            }
        } else {
            adapter.loadMore(page.getDataList());
        }
    }


}
