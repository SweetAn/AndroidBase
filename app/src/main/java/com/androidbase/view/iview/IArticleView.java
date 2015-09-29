package com.androidbase.view.iview;

import com.androidbase.entity.Result;

/**
 * Created by qianjin on 2015/9/29.
 */
public interface IArticleView {
    void getArticlesScuccess(Result result);
    void getArticlesFail(String msg);
    void requestEnd();
}
