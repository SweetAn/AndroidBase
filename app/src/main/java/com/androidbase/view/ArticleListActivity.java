package com.androidbase.view;

import android.view.View;
import android.widget.AdapterView;

import com.androidbase.data.http.HttpHelper;
import com.androidbase.data.http.HttpResultHandler;
import com.androidbase.entity.Result;
import com.androidbase.util.CountUtil;
import com.androidbase.view.base.BaseListActivity;

/**
 * Created by qianjin on 2015/9/29.
 */
public class ArticleListActivity extends BaseListActivity{

    @Override
    public void getList() {
        //presenter = new ArticlePresenter(context);
        HttpHelper.getArticleList(page, new HttpResultHandler() {
            @Override
            public void onSuccess(Result result) {
                getDataSuccess(result);
            }
        });
    }

    @Override
    public String getCountActivityName() {
        return CountUtil.PAGE_TEST;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_article);
//
//        setTitle("资讯");
//
//        page = new Page();
//        presenter = new ArticlePresenter(this);
//        listView = (PtrListView) findViewById(R.id.lv_list);
//        adapter = new ArticleAdapter(context);
//        listView.setAdapter(adapter);
//        presenter.getArticles(page);
//
//        listView.setRefresh(new PtrListView.OnRefresh() {
//            @Override
//            public void onRefresh() {
//                page.initPage();
//                presenter.getArticles(page);
//            }
//        });
//
//        listView.setLoadMore(new PtrListView.OnLoadMore() {
//            @Override
//            public void onLoadMore() {
//                if (page.hasMore()) {
//                    presenter.getArticles(page);
//                } else {
//                    requestEnd();
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public void getDataSuccess(Result result) {
//        Page<Article> resultPage = result.getPage(Article.class);
//        page.initPage(resultPage);
//        if (page.isRefresh()) {
//            LogUtil.log("refresh,need refresh is :" + result.isNeedRefresh());
//            if(result.isNeedRefresh()) {
//                adapter.refresh(page.getDataList());
//            }
//        } else {
//            adapter.loadMore(page.getDataList());
//        }
//    }
//
//    @Override
//    public void requestEnd() {
//        com.commons.support.log.LogUtil.log("request end called!");
//        listView.loadDataComplete();
//    }
}
