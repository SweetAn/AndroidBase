package com.androidbase.base;

import com.androidbase.commons.Constants;
import com.androidbase.http.HttpHelper;
import com.androidbase.util.CountUtil;
import com.commons.support.db.config.ConfigUtil;

public abstract class BaseFragment extends com.commons.support.ui.base.BaseFragment {

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
    public void onPause() {
        super.onPause();
        CountUtil.onPageEnd(context, getFragmentTitle());
    }

    @Override
    public void onResume() {
        super.onResume();
        CountUtil.onPageStart(context, getFragmentTitle());
    }

}
