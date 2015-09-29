package com.androidbase.presenter;

import android.support.annotation.Nullable;

import com.androidbase.data.http.MAsyncHttpResponseHandler;
import com.androidbase.entity.Page;
import com.androidbase.entity.Result;
import com.androidbase.model.ArticleModel;
import com.androidbase.view.iview.IArticleView;

/**
 * Created by qianjin on 2015/9/25.
 */
public class ArticlePresenter {

    private ArticleModel articleModel;
    private IArticleView articleView;

    public ArticlePresenter(IArticleView articleView){
        this.articleView = articleView;
        articleModel = new ArticleModel();
    }

    public void getArticles(final Page page){
        articleModel.getArticles(page, new MAsyncHttpResponseHandler() {
            @Override
            public void MRequestEnd() {
                super.MRequestEnd();
                articleView.requestEnd();
            }
            @Override
            public void onMSuccess(Result result) {
                if (result.isResult()) {
                    articleView.getArticlesScuccess(result);
                } else {
                    articleView.getArticlesFail(result.getMsg());
                }
            }
            @Override
            public void onMFailure(int statusCode, @Nullable Result result, @Nullable Throwable throwable) {
                if (throwable != null) throwable.printStackTrace();
                articleView.getArticlesFail("网络异常！");
            }
        });
    }

}
