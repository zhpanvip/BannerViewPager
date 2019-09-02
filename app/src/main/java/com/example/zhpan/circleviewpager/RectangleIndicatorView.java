package com.example.zhpan.circleviewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zhpan.bannerview.enums.IndicatorSlideMode;
import com.zhpan.bannerview.indicator.IIndicator;
import com.zhpan.bannerview.utils.DpUtils;

/**
 * Created by zhpan on 2017/12/6.
 */
public class RectangleIndicatorView extends View implements IIndicator {
    private int normalColor;
    private int checkedColor;
    private Paint mPaint;
    private int mPageSize;
    private float sliderWidth;
    private float sliderHeight;
    private int currentPosition;
    private float mMargin;
    private IndicatorSlideMode mSlideStyle = IndicatorSlideMode.SMOOTH;
    private float slideProgress;

    public RectangleIndicatorView(Context context) {
        this(context, null);
    }

    public RectangleIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectangleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        normalColor = Color.parseColor("#8C18171C");
        checkedColor = Color.parseColor("#8C6C6D72");
        mPaint = new Paint();
        mPaint.setColor(normalColor);
        mPaint.setAntiAlias(true);
        sliderWidth = DpUtils.dp2px(context, 5);
        sliderHeight = sliderWidth / 2;
        mMargin = sliderWidth;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) ((mPageSize - 1) * mMargin + sliderWidth * mPageSize),
                (int) (sliderHeight));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(normalColor);
        for (int i = 0; i < mPageSize; i++) {
            mPaint.setColor(normalColor);
            canvas.drawRect(i * (sliderWidth) + i * +mMargin, 0, i * (sliderWidth) + i * +mMargin + sliderWidth, sliderHeight, mPaint);
        }
        drawSliderStyle(canvas);
    }

    @Override
    public void onPageSelected(int position) {
        if (mSlideStyle == IndicatorSlideMode.NORMAL) {
            this.currentPosition = position;
            invalidate();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mSlideStyle == IndicatorSlideMode.SMOOTH) {
            slideProgress = positionOffset;
            currentPosition = position;
            invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void drawSliderStyle(Canvas canvas) {
        switch (mSlideStyle) {
            case NORMAL:
                slideProgress = 0;
            case SMOOTH:
                slideProgress = (currentPosition == mPageSize - 1) ? 0 : slideProgress;
                break;
        }
        mPaint.setColor(checkedColor);
        canvas.drawRect(currentPosition * (sliderWidth) + currentPosition * +mMargin + (sliderWidth + mMargin) * slideProgress, 0, currentPosition * (sliderWidth) + currentPosition * +mMargin + (sliderWidth + mMargin) * slideProgress + sliderWidth, sliderHeight, mPaint);
    }


    public RectangleIndicatorView setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public RectangleIndicatorView setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
        return this;
    }

    public RectangleIndicatorView setPageSize(int pageSize) {
        this.mPageSize = pageSize;
        requestLayout();
        return this;
    }

    public RectangleIndicatorView setIndicatorMargin(float margin) {
        if (margin > 0) {
            this.mMargin = DpUtils.dp2px(getContext(), margin);
        }
        return this;
    }

    public RectangleIndicatorView setSliderWidth(float sliderWidth) {
        this.sliderWidth = DpUtils.dp2px(getContext(), sliderWidth);
        return this;
    }

    public RectangleIndicatorView setSliderHeight(float sliderHeight) {
        this.sliderHeight = DpUtils.dp2px(getContext(), sliderHeight);
        return this;
    }

    public RectangleIndicatorView setSlideStyle(IndicatorSlideMode slideStyle) {
        mSlideStyle = slideStyle;
        return this;
    }
}
