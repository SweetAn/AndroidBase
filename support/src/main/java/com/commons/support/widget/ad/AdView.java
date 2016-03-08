package com.commons.support.widget.ad;

import android.content.Context;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.commons.support.R;
import com.commons.support.util.BaseJava;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by qianjin on 2015/4/24.
 */
public class AdView extends FrameLayout {

    Context context;
    ViewPager viewPager;
    AdImageAdapter adapter;
    CircleIndicator circleIndicator;
    private int dataSize;
    PageBannerHandler pageBannerHandler;
    TextView tvTitle;
    int height = 190;


    public AdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View adView = LayoutInflater.from(context).inflate(R.layout.ad_view, this);
        tvTitle = (TextView) adView.findViewById(R.id.tv_ad_title);
        viewPager = (ViewPager) adView.findViewById(R.id.ad_pager);
        viewPager.getLayoutParams().height = height;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (mOnPageChange != null) {
                    mOnPageChange.onChange(position);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        adapter = new AdImageAdapter(context);
        viewPager.setAdapter(adapter);

        circleIndicator = (CircleIndicator) adView.findViewById(R.id.indicator);
        circleIndicator.setSelectNum(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (dataSize > 0)
                    position = position % dataSize;
                circleIndicator.setSelectNum(position + positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                pageBannerHandler.sendMessage(Message.obtain(pageBannerHandler, PageBannerHandler.MSG_PAGE_CHANGED, position, 0));
                if (dataSize > 0)
                    position = position % dataSize;
                circleIndicator.setSelectNum(position);
                tvTitle.setText(adapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        pageBannerHandler.sendEmptyMessage(PageBannerHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        pageBannerHandler.sendEmptyMessageDelayed(PageBannerHandler.MSG_UPDATE_IMAGE, PageBannerHandler.MSG_DELAY);
                        break;
                }
            }
        });

    }

    public void setBannerAdHeight(int height){
        this.height = height;
        viewPager.getLayoutParams().height = height;
    }

    public void setAds(List<Ad> list) {

        if (BaseJava.listIsEmpty(list)) {
            return;
        }

        adapter.setAds(list);
        dataSize = list.size();

        if (list.size() == 1) {
            tvTitle.setText(list.get(0).getTitle());
            circleIndicator.setVisibility(GONE);
        }
        if (pageBannerHandler == null && list.size() > 1) {
            circleIndicator.setVisibility(VISIBLE);
            circleIndicator.setPointNum(list.size());
            tvTitle.setText(list.get(0).getTitle());
            pageBannerHandler = new PageBannerHandler(viewPager, 0, new WeakReference<>(context));
            pageBannerHandler.sendEmptyMessageDelayed(PageBannerHandler.MSG_UPDATE_IMAGE, PageBannerHandler.MSG_DELAY);
        }
    }


    private OnPageChangeListener mOnPageChange;

    public void setOnPageChangeListener(OnPageChangeListener l) {
        mOnPageChange = l;
    }

    public interface OnPageChangeListener {
        void onChange(int position);
    }

}
