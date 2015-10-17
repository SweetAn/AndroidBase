package com.androidbase.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.androidbase.adapter.ArticleAdapter;
import com.androidbase.adapter.base.BaseAdapter;
import com.androidbase.base.BaseListActivity;
import com.androidbase.data.http.HttpHelper;
import com.androidbase.data.http.HttpResultHandler;
import com.androidbase.entity.Article;
import com.androidbase.entity.Result;
import com.androidbase.util.CacheUtil;

/**
 * Created by qianjin on 2015/9/29.
 */
public class ArticleListActivity extends BaseListActivity {

    @Override
    public void initView() {
        super.initView();
        setTitle("资讯");
    }

    @Override
    protected Activity getCountContext() {
        return this;
    }


    @Override
    protected void getList() {
        HttpHelper.getArticleList(page, new HttpResultHandler(CacheUtil.ARTICLE_LIST) {
            @Override
            public void onSuccess(Result result) {
                requestSuccess(result, Article.class);
            }
        });
    }

    @Override
    protected BaseAdapter getAdapter() {
        return new ArticleAdapter(context);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
