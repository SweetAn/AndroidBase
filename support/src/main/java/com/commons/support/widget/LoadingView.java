package com.commons.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.commons.support.R;


/**
 * Created by qianjin on 2015/7/6.
 */
public class LoadingView extends FrameLayout{

    TextView tvLoadingTip;
    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ll_loading, this);
        tvLoadingTip = (TextView) view.findViewById(R.id.tv_loading_tip);
    }

    public void setLoadingTip(String tip){
        tvLoadingTip.setText(tip);
    }

}
