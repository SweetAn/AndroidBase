package com.androidbase.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by qianjin on 2015/10/29.
 */
public class ToastUtil {
    public static void showToast(Context context,String msg){
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
