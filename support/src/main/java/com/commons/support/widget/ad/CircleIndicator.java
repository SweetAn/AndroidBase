package com.commons.support.widget.ad;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.commons.support.R;


public class CircleIndicator extends View {
    private int pointNum = 0;
    private Paint mPaint;
    private int selectColor = Color.WHITE;
    private int notSelectColor = Color.GRAY;
    private int pointPadding = 0;
    private int viewWidth, viewHeight;
    private float selectNum = -1f;

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode()) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator, defStyleAttr, 0);
        int lenght = typedArray.getIndexCount();
        for (int i = 0; i < lenght; i++) {
            int attr = typedArray.getIndex(i);

            if (attr == R.styleable.CircleIndicator_notselectColor) {
                notSelectColor = typedArray.getColor(attr, Color.GRAY);
            } else if(attr == R.styleable.CircleIndicator_selectColor) {
                selectColor = typedArray.getColor(attr, Color.WHITE);
            } else if(attr == R.styleable.CircleIndicator_pointNum) {
                pointNum = typedArray.getInt(attr, 0);
            } else if(attr == R.styleable.CircleIndicator_pointPadding) {
                pointPadding = (int) typedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
            }

        }
        typedArray.recycle();
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    public void setSelectColor(int color) {
        this.selectColor = color;
        invalidate();
    }

    public void setNotSelectColor(int color) {
        this.notSelectColor = color;
        invalidate();
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicator(Context context) {
        this(context, null);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int width  = measureDimension(100, widthMeasureSpec);
//        int height = measureDimension(10, heightMeasureSpec);
//        setMeasuredDimension(width, height);

        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }

    protected int measureDimension(int defaultSize, int measureSpec ) {
        int result = defaultSize;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //1. layout给出了确定的值，比如：100dp
        //2. layout使用的是match_parent，但父控件的size已经可以确定了，比如设置的是具体的值或者match_parent
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize; //建议：result直接使用确定值
        }
        //1. layout使用的是wrap_content
        //2. layout使用的是match_parent,但父控件使用的是确定的值或者wrap_content
        else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize); //建议：result不能大于specSize
        }
        //UNSPECIFIED,没有任何限制，所以可以设置任何大小
        //多半出现在自定义的父控件的情况下，期望由自控件自行决定大小
        else {
            result = defaultSize;
        }

        return result;
    }



    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int num) {
        pointNum = num;
        invalidate();
    }

    public void setSelectNum(float currentNum) {
        if (currentNum > pointNum - 1) {
            selectNum = pointNum - 1;
        } else if (currentNum < 0) {
            selectNum = 0;
        } else {
            selectNum = currentNum;
        }
        invalidate();
    }

    public float getSelectNum() {
        return selectNum;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int begin = (viewWidth - viewHeight * pointNum - pointPadding * (pointNum - 1)) / 2 + viewHeight / 2;
        if(mPaint==null) return;
        mPaint.setColor(notSelectColor);
        for (int i = 0; i < pointNum; i++) {
            canvas.drawCircle(begin + (pointPadding + viewHeight) * (i), viewHeight / 2, viewHeight / 2, mPaint);
        }
        mPaint.setColor(selectColor);
        if (pointNum > 0 && selectNum >= 0 && selectNum <= pointNum - 1) {
            int i = (int) selectNum;
            canvas.drawCircle(begin + (pointPadding + viewHeight) * (i) + (selectNum - i) * (viewHeight + pointPadding), viewHeight / 2, viewHeight / 2, mPaint);
        }
    }
}
