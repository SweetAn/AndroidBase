package com.androidbase.view.iview;

import com.androidbase.entity.Result;

/**
 * Created by android on 2015/10/1.
 */
public interface IBaseView {
    abstract void getDataSuccess(Result result);
    abstract void requestEnd();
}
