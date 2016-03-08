package com.commons.support.widget.ad;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.commons.support.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class AdImageAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Ad> list;
    private LayoutInflater inflater;
    private ImageView adImageView;
    private int height;

    public AdImageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
        height = getBannerAdHeight(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public void setAds(List<Ad> ads) {
        this.list.clear();
        this.list.addAll(ads);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list.size() <= 1) {
            return list.size();
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        if (list.size() <= 0) {
            return inflater.inflate(R.layout.ad_image_view, view, false);
        }
        position = position % list.size();
        final Ad ad = list.get(position);

        View imageLayout = inflater.inflate(R.layout.ad_image_view, view, false);
        adImageView = (ImageView) imageLayout.findViewById(R.id.row_image);
        adImageView.getLayoutParams().height = height;
        String url = ad.getImg();
        ImageLoader.getInstance().displayImage(url, adImageView);

        view.addView(imageLayout, 0);
        adImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }

    public int getBannerAdHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        int height = (int) (d.getWidth() * 410.0 / 1024);
        return height;
    }

}
