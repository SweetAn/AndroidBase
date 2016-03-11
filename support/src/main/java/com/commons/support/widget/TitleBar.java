package com.commons.support.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.commons.support.R;
import com.commons.support.util.LogUtil;


/**
 * Created by qianjin on 2015/4/24.
 */
public class TitleBar extends FrameLayout {

    private ImageView leftButton;
    private ImageView rightButton;
    private TextView tvTitle;
    private TextView tvRightButton;
    ImageView redDot;

    public TitleBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View titleView = LayoutInflater.from(context).inflate(R.layout.title_bar, this);
        leftButton = (ImageView) titleView.findViewById(R.id.btn_left);
        rightButton = (ImageView) titleView.findViewById(R.id.btn_right);
        redDot = (ImageView) titleView.findViewById(R.id.iv_red_dot);
        tvRightButton = (TextView) titleView.findViewById(R.id.btn_text_right);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        tvTitle = (TextView) titleView.findViewById(R.id.tv_title);
    }


    public void setLeftButton(int imageRes,OnClickListener onClickListener) {
        if (imageRes != 0) {
            leftButton.setImageResource(imageRes);
        }
        leftButton.setOnClickListener(onClickListener);
    }

    public void setLeftButton(OnClickListener onClickListener) {
        leftButton.setOnClickListener(onClickListener);
    }

    public void setRightButton(int imageRes,OnClickListener onClickListener) {
        if (imageRes != 0) {
            rightButton.setImageResource(imageRes);
        }
        rightButton.setOnClickListener(onClickListener);
        rightButton.setVisibility(VISIBLE);
    }

    public void setRightButton(String btnText,OnClickListener onClickListener) {
        tvRightButton.setText(btnText);
        tvRightButton.setOnClickListener(onClickListener);
        tvRightButton.setVisibility(VISIBLE);
    }

    public void setTitle(String title){
        this.tvTitle.setText(title);
    }

    public void showLeftButton(boolean isShow){
        if (isShow) {
            leftButton.setVisibility(VISIBLE);
        } else {
            leftButton.setVisibility(GONE);
        }
    }

    public void showRedDot(boolean isShow){
        LogUtil.log("Call showRedDot");
        if (isShow) {
            LogUtil.log("Set redDot Visible");
            redDot.setVisibility(VISIBLE);
        } else {
            LogUtil.log("Set redDot Gone");
            redDot.setVisibility(GONE);
        }
    }

    public void showRightButton(boolean isShow){
        if (isShow) {
            rightButton.setVisibility(VISIBLE);
        } else {
            rightButton.setVisibility(GONE);
        }
    }

    public void showTvRightButton(boolean isShow){
        if (isShow) {
            tvRightButton.setVisibility(VISIBLE);
        } else {
            tvRightButton.setVisibility(GONE);
        }
    }

}
