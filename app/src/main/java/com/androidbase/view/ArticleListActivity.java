package com.androidbase.view;

import android.view.View;
import android.widget.AdapterView;

import com.androidbase.adapter.ArticleAdapter;
import com.androidbase.base.BaseListActivity;
import com.androidbase.entity.Article;
import com.commons.support.ui.adapter.BaseAdapter;

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
    protected void getList() {
        httpHelper.getArticleList(page, getDefaultListHandler(Article.class));
    }

    @Override
    protected BaseAdapter getAdapter() {
        return new ArticleAdapter(context);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
