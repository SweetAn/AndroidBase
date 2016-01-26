package com.commons.support.ui.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.AdapterView;

import com.commons.support.R;
import com.commons.support.entity.Page;
import com.commons.support.entity.Result;
import com.commons.support.http.HttpResultHandler;
import com.commons.support.log.LogUtil;
import com.commons.support.ui.adapter.BaseAdapter;
import com.commons.support.widget.ptr.PtrListView;


/**
 * Created by qianjin on 2015/10/12.
 */
public abstract class BaseListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    public Page page;
    public BaseAdapter adapter;
    public PtrListView listView;


    @Override
    public void initView(View view) {

        listView = $(R.id.lv_list);

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

        adapter = getAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }


    @Override
    public void request() {
        page = new Page<>();
        getList();
    }

    protected HttpResultHandler getDefaultListHandler(final Class... entity){
        return new HttpResultHandler() {
            @Override
            public void onSuccess(Result result) {
                requestSuccess(result, entity);
            }
        };
    }

    @Override
    public void requestSuccess(Result result, Class... entity) {
        if (result.isResult()) {
            Page resultPage = result.getPage(entity[0]);
            LogUtil.log("TotalPage.requestSuccess.CurrentPage=" + resultPage.getCurrentPage());
            if (resultPage != null) {
                page.initPage(resultPage);
                if (page.isRefresh()) {
                    adapter.refresh(page.getList());
                } else {
                    adapter.loadMore(page.getList());
                }
            }
        } else {
            showToast(result.getMsg());
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

    protected abstract void getList();

    protected abstract BaseAdapter getAdapter();

    /**
     * 如果布局有变化，请重写此方法
     * @return
     */
    @Override
    public @LayoutRes int getViewRes() {
        return R.layout.fragment_view_list;
    }

    @Override
    public void onClick(View v) {
    }
}
