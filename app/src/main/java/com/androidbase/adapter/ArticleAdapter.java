package com.androidbase.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.androidbase.R;
import com.androidbase.adapter.base.BaseAdapter;
import com.androidbase.adapter.base.ViewHolder;
import com.androidbase.entity.Article;
import com.nostra13.universalimageloader.core.ImageLoader;


public class ArticleAdapter extends BaseAdapter {

    public ArticleAdapter(Context context) {
        super(context);
    }

    @Override
    protected void initData(ViewHolder viewHolder, Object obj, int position) {

        final Article bean = (Article) list.get(position);
        final Holder holder = (Holder) viewHolder;

        holder.tvTitle.setText(bean.getTitle());

        if (!TextUtils.isEmpty(bean.getImageUrl())) {
            ImageLoader.getInstance().displayImage(bean.getImageUrl(), holder.ivIcon);
            holder.ivIcon.setVisibility(View.VISIBLE);
        } else {
            holder.ivIcon.setVisibility(View.GONE);
        }

        String date = JSON.toJSONStringWithDateFormat(bean.getAddDt(), "yyyy-MM-dd");
        holder.tvDate.setText(date);

        if (bean.isNice()) {
            holder.vNiceTag.setVisibility(View.VISIBLE);
        } else {
            holder.vNiceTag.setVisibility(View.GONE);
        }

    }

    @Override
    protected int getViewRes() {
        return R.layout.article_list_item;
    }

    @Override
    protected ViewHolder initHolder() {

        Holder holder = new Holder();
        holder.tvTitle = $(R.id.tv_title);
        holder.tvDate = $(R.id.tv_date);
        holder.ivIcon = $(R.id.iv_image);
        holder.vNiceTag = $(R.id.v_nice_tag);
        holder.llTags = $(R.id.ll_tags);

        return holder;
    }

    class Holder extends ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivIcon;
        public LinearLayout llTags;
        public View vNiceTag;
    }

}
