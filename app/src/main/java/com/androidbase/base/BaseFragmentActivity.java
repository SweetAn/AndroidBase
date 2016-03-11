package com.androidbase.base;

import com.androidbase.commons.Constants;
import com.androidbase.http.HttpHelper;
import com.androidbase.util.CountUtil;
import com.commons.support.db.config.ConfigUtil;

public abstract class BaseFragmentActivity extends com.commons.support.ui.base.BaseFragmentActivity {
    public HttpHelper httpHelper;

    public String getUserKey() {
        return ConfigUtil.getConfigValue(Constants.KEY);
    }

    public boolean isLogin() {
        return ConfigUtil.getBooleanConfigValue(Constants.LOGIN);
    }

    @Override
    public void init() {
        super.init();
        httpHelper = HttpHelper.getInstance(context);
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
