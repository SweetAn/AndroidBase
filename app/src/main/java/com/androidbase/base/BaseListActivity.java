package com.androidbase.base;

import com.androidbase.commons.Constants;
import com.androidbase.http.HttpHelper;
import com.androidbase.util.CountUtil;
import com.commons.support.db.config.ConfigUtil;

/**
 * Created by qianjin on 2016/1/27.
 */
public abstract class BaseListActivity extends com.commons.support.ui.base.BaseListActivity {

    public HttpHelper httpHelper;


    public boolean isLogin() {
        return ConfigUtil.getBooleanConfigValue(Constants.LOGIN);
    }

    public String getCacheKey(String key) {
        return getClass().getSimpleName() + "_" + key;
    }


    @Override
    public void init() {
        super.init();
        httpHelper = HttpHelper.getInstance(context);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CountUtil.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CountUtil.onResume(this);
    }

}
