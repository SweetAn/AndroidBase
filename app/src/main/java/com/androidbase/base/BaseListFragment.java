package com.androidbase.base;

import android.view.View;

import com.androidbase.http.HttpHelper;
import com.androidbase.util.CountUtil;

/**
 * Created by qianjin on 2016/1/27.
 */
public abstract class BaseListFragment extends com.commons.support.ui.base.BaseListFragment {

    public HttpHelper httpHelper;

    public String getCacheKey(String key) {
        return getClass().getSimpleName() + "_" + key;
    }

    @Override
    public void init() {
        super.init();
        httpHelper = HttpHelper.getInstance(context);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void onPause() {
        super.onPause();
        CountUtil.onPageStart(context, this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        CountUtil.onPageEnd(context, this.getClass().getSimpleName());
    }

}
