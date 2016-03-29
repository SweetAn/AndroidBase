package com.commons.support.ui.adapter;

/**
 * Created by qianjin on 2015/10/13.
 */
public class BaseViewHolder {
    public <T extends BaseViewHolder> T getHolder(){
        return (T)this;
    }
}
