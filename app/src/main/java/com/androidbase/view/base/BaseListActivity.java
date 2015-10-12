package com.androidbase.view.base;

import android.widget.AdapterView;

import com.androidbase.R;
import com.androidbase.adapter.ArticleAdapter;
import com.androidbase.adapter.BaseAdapter;
import com.androidbase.entity.Article;
import com.androidbase.entity.Page;
import com.androidbase.entity.Result;
import com.androidbase.util.CountUtil;
import com.androidbase.widget.ptr.PtrListView;
import com.commons.support.log.LogUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by qianjin on 2015/10/12.
 */
public abstract class BaseListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    public Page page;
    public BaseAdapter adapter;
    public PtrListView listView;


    @Override
    public void initView() {

        listView = (PtrListView) findViewById(R.id.lv_list);

        listView.setRefresh(new PtrListView.OnRefresh() {
            @Override
            public void onRefresh() {
                page.initPage();
                getList();
            }
        });
        listView.setLoadMore(new PtrListView.OnLoadMore() {
            @Override
            public void onLoadMore() {
                if (page.hasMore()) {
                    getList();
                } else {
                    listView.loadDataComplete();
                }
            }
        });
        adapter = new ArticleAdapter(context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }


    @Override
    public void getData() {
        page = new Page<>();
        getList();
    }

    @Override
    public void getDataSuccess(Result result) {
        if (result.isResult()) {
            Page<Article> resultPage = result.getPage(Article.class);
            if (resultPage != null) {
                page.initPage(resultPage);
                if (page.isRefresh()) {
                    adapter.refresh(page.getList());
                } else {
                    adapter.loadMore(page.getList());
                }
            }
        } else {
            showToastCenter(result.getMsg());
        }
        if (result.isRequestEnd()) {
            requestEnd();
        }
    }

    @Override
    public void requestEnd() {
        isLoading = false;
        listView.loadDataComplete();
        loadingDialog.dismiss();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        CountUtil.onPageStart(context, getCountActivityName());
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.log("count name is :" + getCountActivityName());
        CountUtil.onPageEnd(context, getCountActivityName());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    public abstract void getList();

    public abstract String getCountActivityName();

    public int getViewRes(){
        return R.layout.activity_view_list;
    }

    public void onEvent(){}

}
