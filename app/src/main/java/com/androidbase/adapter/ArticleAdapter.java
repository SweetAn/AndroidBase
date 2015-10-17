package com.androidbase.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.androidbase.R;
import com.androidbase.adapter.base.BaseAdapter;
import com.androidbase.adapter.base.ViewHolder;
import com.androidbase.entity.Article;
import com.androidbase.entity.Tag;
import com.nostra13.universalimageloader.core.ImageLoader;


public class ArticleAdapter extends BaseAdapter {

    private Context context;

    public ArticleAdapter(Context context) {
        this.context = context;
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
        holder.tvDate.setText(date.substring(1, date.length() - 1));

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
        holder.tvTitle = findView(R.id.tv_title);
        holder.tvDate = findView(R.id.tv_date);
        holder.ivIcon = findView(R.id.iv_image);
        holder.vNiceTag = findView(R.id.v_nice_tag);
        holder.llTags = findView(R.id.ll_tags);

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
