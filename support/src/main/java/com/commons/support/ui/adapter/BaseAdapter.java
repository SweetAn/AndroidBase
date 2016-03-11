package com.commons.support.ui.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianjin on 2015/10/12.
 */
public abstract class BaseAdapter extends android.widget.BaseAdapter {

    protected List list;
    protected Context context;
    protected View view;

    public BaseAdapter(Context context) {
        list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public View getView(final int position, View view, ViewGroup group) {
        final BaseViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(getViewRes(), group, false);
            this.view = view;
            holder = initHolder();
            view.setTag(holder);
        } else {
            holder = (BaseViewHolder) view.getTag();
        }
        if (list.size() > 0) {
            initData(holder, list.get(position), position);
        } else {
            initData(holder, null, position);
        }
        return view;
    }

    public <T extends View> T $(@IdRes int id) {
        return (T) view.findViewById(id);
    }

    protected abstract void initData(BaseViewHolder baseViewHolder, Object obj, final int position);

    protected abstract
    @LayoutRes
    int getViewRes();

    protected abstract BaseViewHolder initHolder();

    public void refresh(int position, Object obj) {
        this.list.set(position, obj);
        notifyDataSetChanged();
    }

    public void refresh(List list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void loadMore(List list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}


