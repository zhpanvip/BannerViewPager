package com.zhpan.bannerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.zhpan.bannerview.Utils.DpUtils;

/**
 * Created by zhpan on 2017/12/6.
 */
public class IndicatorView extends View implements ViewPager.OnPageChangeListener {

    private int normalColor;
    private int checkedColor;
    private Paint mPaint;
    private int mPageSize;
    private float mRadius;
    private int height;
    private int currentPosition;
    private float mMargin;
    private static final String tag = "IndicatorView";

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        normalColor = Color.parseColor("#000000");
        checkedColor = Color.parseColor("#ffffff");
        mPaint = new Paint();
        mPaint.setColor(normalColor);
        mPaint.setAntiAlias(true);
        mRadius = DpUtils.dp2px(context, 4);
        mMargin = mRadius * 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) ((mPageSize - 1) * mMargin + 2 * mRadius * mPageSize),
                (int) (2 * mRadius));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mPageSize; i++) {
            mPaint.setColor(normalColor);
            canvas.drawCircle(mRadius + (2 * mRadius + mMargin) * i, height / 2f, mRadius, mPaint);
        }
        mPaint.setColor(checkedColor);
        canvas.drawCircle(mRadius + (2 * mRadius + mMargin) * currentPosition + (2 * mRadius + mMargin) * slideProgress, height / 2f, mRadius, mPaint);
    }

    @Override
    public void onPageSelected(int position) {

    }

    private float slideProgress;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        slideProgress = positionOffset;
        currentPosition = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public IndicatorView setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public IndicatorView setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
        return this;
    }

    public IndicatorView setPageSize(int pageSize) {
        this.mPageSize = pageSize;
        requestLayout();
        return this;
    }

    public IndicatorView setIndicatorRadius(float radiusDp) {
        this.mRadius = radiusDp;
        return this;
    }

    public IndicatorView setIndicatorMargin(float margin) {
        if (margin > 0) {
            this.mMargin = margin;
        }
        return this;
    }
}
