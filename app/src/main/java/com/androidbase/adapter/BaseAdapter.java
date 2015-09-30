package com.androidbase.adapter;

import java.util.List;

/**
 * Created by qianjin on 2015/9/29.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter{
    public abstract void refresh(List<T> list);
    public abstract void loadMore(List<T> list);
}
