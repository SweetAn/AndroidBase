package com.commons.support.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.commons.support.ui.base.ContentView;


/**
 * Created by Wang on 2015/12/30.
 */
public class UIHelper {

    public static int getContentViewRes(Object o) {
        try {
            Class<?> clazz = o.getClass();
            ContentView contentView = clazz.getAnnotation(ContentView.class);
            if (contentView != null) {
                return contentView.layoutResValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static <T extends View> T $(Activity activity, @IdRes int id, View.OnClickListener onClickListener) { // activity用
        T view = $T(activity, id);
        if (onClickListener != null && !(view instanceof AbsListView))
            view.setOnClickListener(onClickListener);
        return view;
    }

    public static <T extends View> T $T(Activity activity, @IdRes int id) { // activity用
        T view = (T) activity.findViewById(id);
        return view;
    }

    public static <T extends View> T $(View view, @IdRes int id, View.OnClickListener onClickListener) { // fragment、LayoutInflater用
        T v = $T(view, id);
        if (onClickListener != null && !(v instanceof AbsListView))
            v.setOnClickListener(onClickListener);
        return v;
    }

    public static <T extends View> T $T(View view, @IdRes int id) {
        T v = (T) view.findViewById(id);
        return v;
    }

    public static void showToast(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showCenterToast(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     *防止view重复快速点击，触发多次事件
     * */
    public static void delayedClick(final View v,long time){
        v.setClickable(false);
        v.postDelayed(new Runnable() {//防止多次点击触发多次，导致动画不流畅
            @Override
            public void run() {
                v.setClickable(true);
            }
        }, time);
    }

}
