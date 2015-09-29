package com.androidbase.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.androidbase.R;
import com.androidbase.entity.Article;
import com.androidbase.entity.Tag;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class ArticleAdapter extends BaseAdapter {

    private ArrayList<Article> list;
    private Context context;

    public ArticleAdapter(Context context) {
        this.context = context;
        list = new ArrayList<Article>();
    }

    public void refresh(List<Article> articles) {
        this.list.clear();
        this.list.addAll(articles);
        notifyDataSetChanged();
    }

    public void loadMore(List<Article> articles) {
        this.list.addAll(articles);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Article getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup group) {
        final Holder holder;
        if (view == null) {
            holder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.article_list_item, null);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.tvDate = (TextView) view.findViewById(R.id.tv_date);
            holder.ivIcon = (ImageView) view.findViewById(R.id.iv_image);
            holder.vNiceTag = view.findViewById(R.id.v_nice_tag);
            holder.llTags = (LinearLayout) view.findViewById(R.id.ll_tags);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        final Article bean = list.get(position);
        holder.tvTitle.setText(bean.getTitle());

        if (!TextUtils.isEmpty(bean.getImageUrl())) {

            ImageLoader.getInstance().displayImage(bean.getImageUrl(), holder.ivIcon);

            holder.ivIcon.setVisibility(View.VISIBLE);
        } else {
            holder.ivIcon.setVisibility(View.GONE);
        }

        if (bean.getTags() != null) {
            holder.llTags.removeAllViews();
            for (int i = 0; i < bean.getTags().size(); i++) {
                Tag tag = bean.getTags().get(i);
                //LogUtil.log("==add Tag==" + bean.getTags().size());
                TextView tv = new TextView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.rightMargin = 15;
                tv.setLayoutParams(layoutParams);
                tv.setTextSize(10);
                tv.setPadding(2, 2, 2, 2);
                tv.setText(tag.getName());
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(context.getResources().getColor(R.color.main_color));
                tv.setBackgroundResource(R.drawable.ic_tag_bg);
                holder.llTags.addView(tv);
            }
        }

        String date = JSON.toJSONStringWithDateFormat(bean.getAddDt(), "yyyy-MM-dd");
        holder.tvDate.setText(date.substring(1,date.length()-1));

        if(bean.isNice()){
            holder.vNiceTag.setVisibility(View.VISIBLE);
        } else {
            holder.vNiceTag.setVisibility(View.GONE);
        }

        return view;
    }

    class Holder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivIcon;
        public LinearLayout llTags;
        public View vNiceTag;
    }

}
