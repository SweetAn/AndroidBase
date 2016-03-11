package com.commons.support.ui.base;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.commons.support.entity.Result;

/**
 * Created by qianjin on 2015/10/1.
 */
public interface IBaseView extends View.OnClickListener{

    @LayoutRes int getViewRes();

    void request();
    void requestSuccess(Result result, Class entity);
    void requestEnd();

}
