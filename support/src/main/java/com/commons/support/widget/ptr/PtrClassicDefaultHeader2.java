package com.commons.support.widget.ptr;
/**
 * 定制版header，支持默认下拉、自定义img组下拉
 * Created by Wang on 2016/1/26.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.commons.support.R;
import com.commons.support.widget.ptr.indicator.PtrIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PtrClassicDefaultHeader2 extends FrameLayout implements PtrUIHandler {

    private final static String KEY_SharedPreferences = "cube_ptr_classic_last_update";
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private int mRotateAniTime = 150;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private TextView mTitleTextView;
    private View mRotateView;
    private View mProgressBar;
    private long mLastUpdateTime = -1;
    private TextView mLastUpdateTextView;
    private String mLastUpdateTimeKey;
    private boolean mShouldShowLastUpdate;

    private LastUpdateTimeUpdater mLastUpdateTimeUpdater = new LastUpdateTimeUpdater();

    private boolean isDefault = true;
    private boolean isLoadAniShow;
    private int [] imgs;
    private int [] cycleImgs;
    private ViewGroup header;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            if(!isLoadAniShow || cycleImgs ==null || cycleImgs.length<1) return;
            switch (msg.what) {
                case -1:
                    isLoadAniShow = false;
                    break;
                default: // msg.what直接是index
                    if (msg.what < cycleImgs.length) {
                        ((ImageView)mRotateView).setImageResource(cycleImgs[msg.what]);
                        this.sendEmptyMessageDelayed(msg.what+1, 200);
                    } else {
                        this.sendEmptyMessageDelayed(0, 200); // 回到第一张，循环
                    }
                    break;
            }
        }
    };

    public void setImgs(int[] imgs, int[] cycleImgs) {
        if (imgs!=null && imgs.length > 0) {
            isDefault = false;
            this.imgs = imgs;
            this.cycleImgs = cycleImgs;
            setCustomView();
        } else {
            isDefault = true;
            setDefaultView();
        }
    }

    public void setToDefault(){ // 设置为默认的下拉回弹样式
        isDefault = true;
        setDefaultView();
    }

    public PtrClassicDefaultHeader2(Context context) {
        super(context);
        initViews(null);
        setDefaultView();
    }

    public PtrClassicDefaultHeader2(Context context, int[] Imgs, int[] cycleImgs) {
        super(context);
        initViews(null);
        setImgs(Imgs, cycleImgs);
    }

    public PtrClassicDefaultHeader2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public PtrClassicDefaultHeader2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.PtrClassicHeader, 0, 0);
        if (arr != null) {
            mRotateAniTime = arr.getInt(R.styleable.PtrClassicHeader_ptr_rotate_ani_time, mRotateAniTime);
        }
        buildAnimation();
        if (attrs!=null) { // 这里逻辑不严谨，暂时如此没问题，以后xml里添加了属性再更改
            setDefaultView();
        }
    }

    private void setDefaultView(){
        if (header!=null) {
            header.removeAllViews();
        }
        header = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.cube_ptr_classic_default_header, this);

        mRotateView = header.findViewById(R.id.ptr_classic_header_rotate_view);

        mTitleTextView = (TextView) header.findViewById(R.id.ptr_classic_header_rotate_view_header_title);
        mLastUpdateTextView = (TextView) header.findViewById(R.id.ptr_classic_header_rotate_view_header_last_update);
        mProgressBar = header.findViewById(R.id.ptr_classic_header_rotate_view_progressbar);

        resetView();
    }

    private void setCustomView(){
        if (header!=null) {
            header.removeAllViews();
        }
        header = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.cube_ptr_classic_default_header2, this);

        mRotateView = header.findViewById(R.id.ptr_classic_header_rotate_view);

        mTitleTextView = (TextView) header.findViewById(R.id.ptr_classic_header_rotate_view_header_title);
        mLastUpdateTextView = (TextView) header.findViewById(R.id.ptr_classic_header_rotate_view_header_last_update);
        mProgressBar = header.findViewById(R.id.ptr_classic_header_rotate_view_progressbar);

        resetView();
    }

    public void setTitleText(String text){
        mTitleTextView.setText(text);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mLastUpdateTimeUpdater != null) {
            mLastUpdateTimeUpdater.stop();
        }
    }

    public void setRotateAniTime(int time) {
        if (time == mRotateAniTime || time == 0) {
            return;
        }
        mRotateAniTime = time;
        buildAnimation();
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mLastUpdateTimeKey = key;
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeKey(object.getClass().getName());
    }

    private void buildAnimation() {
        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(mRotateAniTime);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);
    }

    private void resetView() {
        hideRotateView();
        mProgressBar.setVisibility(INVISIBLE);
        if (!isDefault) {
            handler.sendEmptyMessage(-1);
        }
    }

    private void hideRotateView() {
        mRotateView.clearAnimation();
        if (isDefault) {
            mRotateView.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
        mLastUpdateTimeUpdater.start();

        mProgressBar.setVisibility(INVISIBLE);
        if (!isDefault) {
            handler.sendEmptyMessage(-1);
        }

        mRotateView.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        } else {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_down));
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mShouldShowLastUpdate = false;
        hideRotateView();

        if (isDefault) {
            mProgressBar.setVisibility(VISIBLE);
        } else {
            handler.sendEmptyMessage(0);
            isLoadAniShow = true;
        }

        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(R.string.cube_ptr_refreshing);

        tryUpdateLastUpdateTime();
        mLastUpdateTimeUpdater.stop();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

        hideRotateView();
        mProgressBar.setVisibility(INVISIBLE);
        if (!isDefault) {
            handler.sendEmptyMessage(-1);
        }

        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(getResources().getString(R.string.cube_ptr_refresh_complete));

        // update last update time
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(KEY_SharedPreferences, 0);
        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = new Date().getTime();
            sharedPreferences.edit().putLong(mLastUpdateTimeKey, mLastUpdateTime).commit();
        }
    }

    private void tryUpdateLastUpdateTime() {
        if (isDefault) {
            if (TextUtils.isEmpty(mLastUpdateTimeKey) || !mShouldShowLastUpdate) {
                mLastUpdateTextView.setVisibility(GONE);
            } else {
                String time = getLastUpdateTime();
                if (TextUtils.isEmpty(time)) {
                    mLastUpdateTextView.setVisibility(GONE);
                } else {
                    mLastUpdateTextView.setVisibility(VISIBLE);
                    mLastUpdateTextView.setText(time);
                }
            }
        }
    }

    private String getLastUpdateTime() {

        if (mLastUpdateTime == -1 && !TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = getContext().getSharedPreferences(KEY_SharedPreferences, 0).getLong(mLastUpdateTimeKey, -1);
        }
        if (mLastUpdateTime == -1) {
            return null;
        }
        long diffTime = new Date().getTime() - mLastUpdateTime;
        int seconds = (int) (diffTime / 1000);
        if (diffTime < 0) {
            return null;
        }
        if (seconds <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.cube_ptr_last_update));

        if (seconds < 60) {
            sb.append(seconds + getContext().getString(R.string.cube_ptr_seconds_ago));
        } else {
            int minutes = (seconds / 60);
            if (minutes > 60) {
                int hours = minutes / 60;
                if (hours > 24) {
                    Date date = new Date(mLastUpdateTime);
                    sb.append(sDataFormat.format(date));
                } else {
                    sb.append(hours + getContext().getString(R.string.cube_ptr_hours_ago));
                }

            } else {
                sb.append(minutes + getContext().getString(R.string.cube_ptr_minutes_ago));
            }
        }
        return sb.toString();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();

        if (isDefault) {
            final int lastPos = ptrIndicator.getLastPosY();

            if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
                if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                    crossRotateLineFromBottomUnderTouch(frame);
                    if (mRotateView != null) {
                        mRotateView.clearAnimation();
                        mRotateView.startAnimation(mReverseFlipAnimation);
                    }
                }
            } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
                if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                    crossRotateLineFromTopUnderTouch(frame);
                    if (mRotateView != null) {
                        mRotateView.clearAnimation();
                        mRotateView.startAnimation(mFlipAnimation);
                    }
                }
            }
        } else {
            if(currentPos==0) handler.sendEmptyMessage(-1);

            final int deviation = 35;
            if (currentPos < mOffsetToRefresh) {
                if (mRotateView != null && currentPos>deviation && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                    mRotateView.post(new Runnable() {
                        @Override
                        public void run() {
                            float size = imgs.length;
                            int position = (int) ((currentPos - deviation) / ((mOffsetToRefresh - deviation) / size));
                            ((ImageView) mRotateView).setImageResource(imgs[position]);
                            handler.sendEmptyMessage(-1);
                        }
                    });
                }
                if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                    crossRotateLineFromBottomUnderTouch(frame);
                }
            } else {
                if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                    crossRotateLineFromTopUnderTouch(frame);
                }
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mTitleTextView.setVisibility(VISIBLE);
            mTitleTextView.setText(R.string.cube_ptr_release_to_refresh);
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        } else {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_down));
        }
    }

    private class LastUpdateTimeUpdater implements Runnable {

        private boolean mRunning = false;

        private void start() {
            if (TextUtils.isEmpty(mLastUpdateTimeKey)) {
                return;
            }
            mRunning = true;
            run();
        }

        private void stop() {
            mRunning = false;
            removeCallbacks(this);
        }

        @Override
        public void run() {
            tryUpdateLastUpdateTime();
            if (mRunning) {
                postDelayed(this, 1000);
            }
        }
    }
}
