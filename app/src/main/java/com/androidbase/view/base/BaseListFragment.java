package com.androidbase.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dianmi365.hr365.BaseFragment;
import com.dianmi365.hr365.R;
import com.dianmi365.hr365.adapter.ArticleAdapter;
import com.dianmi365.hr365.adapter.BaseAdapter;
import com.dianmi365.hr365.entity.Article;
import com.dianmi365.hr365.entity.Page;
import com.dianmi365.hr365.entity.Result;
import com.dianmi365.hr365.util.CountUtil;
import com.dianmi365.hr365.util.LogUtil;
import com.dianmi365.widget.ptr.PtrListView;

import de.greenrobot.event.EventBus;

/**
 * Created by qianjin on 2015/10/12.
 */
public abstract class BaseListFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    public Page page;
    public BaseAdapter adapter;
    public PtrListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getViewRes(), container, false);
        init(view);
        return view;
    }


    @Override
    public void initView(View view) {

        listView = (PtrListView) view.findViewById(R.id.lv_list);

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
        CountUtil.onPageStart(context, getCountPageName());
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.log("count name is :" + getCountPageName());
        CountUtil.onPageEnd(context, getCountPageName());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    public abstract void getList();

    public abstract String getCountPageName();

    public int getViewRes(){
        return R.layout.fragment_view_list;
    }

    public void onEvent(){}

}
