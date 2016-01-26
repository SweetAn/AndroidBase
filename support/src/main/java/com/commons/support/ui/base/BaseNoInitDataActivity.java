package com.commons.support.ui.base;


import com.commons.support.entity.Result;

/**
 * Created by qianjin on 2015/10/12.
 */
public abstract class BaseNoInitDataActivity extends BaseActivity{
    @Override
    public void request() {
    }
    @Override
    public void requestSuccess(Result result, Class... entity) {
    }
    @Override
    public void requestEnd() {
    }
}
