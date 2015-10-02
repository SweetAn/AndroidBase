package com.androidbase.view;

import android.os.Bundle;

import com.androidbase.BaseActivity;
import com.androidbase.R;
import com.androidbase.adapter.ArticleAdapter;
import com.androidbase.entity.Article;
import com.androidbase.entity.Page;
import com.androidbase.entity.Result;
import com.androidbase.presenter.ArticlePresenter;
import com.androidbase.util.LogUtil;
import com.androidbase.view.iview.IBaseView;
import com.androidbase.widget.ptr.PtrListView;

/**
 * Created by qianjin on 2015/9/29.
 */
public class ArticleListActivity extends BaseActivity implements IBaseView{


    ArticlePresenter presenter;
    Page<Article> page;
    ArticleAdapter adapter;
    PtrListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        setTitle("资讯");

        page = new Page();
        presenter = new ArticlePresenter(this);
        listView = (PtrListView) findViewById(R.id.lv_list);
        adapter = new ArticleAdapter(context);
        listView.setAdapter(adapter);
        presenter.getArticles(page);

        listView.setRefresh(new PtrListView.OnRefresh() {
            @Override
            public void onRefresh() {
                page.initPage();
                presenter.getArticles(page);
            }
        });

        listView.setLoadMore(new PtrListView.OnLoadMore() {
            @Override
            public void onLoadMore() {
                if (page.hasMore()) {
                    presenter.getArticles(page);
                } else {
                    requestEnd();
                }
            }
        });

    }

    @Override
    public void getDataSuccess(Result result) {
        Page<Article> resultPage = result.getPage(Article.class);
        page.initPage(resultPage);
        if (page.isRefresh()) {
            LogUtil.log("refresh,need refresh is :" + result.isNeedRefresh());
            if(result.isNeedRefresh()) {
                adapter.refresh(page.getDataList());
            }
        } else {
            adapter.loadMore(page.getDataList());
        }
    }

    @Override
    public void requestEnd() {
        listView.loadDataComplete();
    }
}
