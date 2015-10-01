package com.androidbase.presenter;

import com.androidbase.data.http.HttpResultHandler;
import com.androidbase.entity.Page;
import com.androidbase.entity.Result;
import com.androidbase.model.ArticleModel;
import com.androidbase.view.iview.IBaseView;

/**
 * Created by qianjin on 2015/9/25.
 */
public class ArticlePresenter {

    private ArticleModel articleModel;
    private IBaseView articleView;

    public ArticlePresenter(IBaseView articleView){
        this.articleView = articleView;
        articleModel = new ArticleModel();
    }

    public void getArticles(final Page page){
        articleModel.getArticles(page, new HttpResultHandler() {
            @Override
            public void onSuccess(Result result) {
                articleView.getDataSuccess(result);
            }
        });
    }

}
