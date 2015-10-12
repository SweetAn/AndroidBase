package com.androidbase.view.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.commons.support.util.Utility;


public abstract class BaseFragment extends Fragment implements IBaseView{

    public boolean isLoading = false;
    public Dialog loadingDialog;
    public Activity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.context = getActivity();
        loadingDialog = Utility.createLoadingDialog(context, "加载中..");
    }

    public void startActivity(Class mClass){
        startActivity(new Intent(context, mClass));
    }

    protected void showToastCenter(String msg) {

        if (TextUtils.isEmpty(msg)) {
            return;
        }

        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showToast(int drawable, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(drawable);
        toastView.addView(imageView, 0);
        toast.show();
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void login(){
        startActivity(NewLoginActivity.class);
    }

    public abstract void initView(View view);
    public abstract void getData();

    public void init(View view){
        initView(view);
        getData();
    }


}
