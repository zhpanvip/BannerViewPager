package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.zhpan.bannerview.constants.IndicatorSlideMode;

/**
 * Created by zhpan on 2017/12/6.
 */
public class DashIndicatorView extends BaseIndicatorView {
    private float sliderHeight;
    private float maxWidth;
    private float minWidth;

    public DashIndicatorView(Context context) {
        this(context, null);
    }

    public DashIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setColor(getNormalColor());
        sliderHeight = getNormalIndicatorWidth() / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        maxWidth = Math.max(getNormalIndicatorWidth(), getCheckedIndicatorWidth());
        minWidth = Math.min(getNormalIndicatorWidth(), getCheckedIndicatorWidth());
        setMeasuredDimension((int) ((getPageSize() - 1) * getIndicatorGap() + maxWidth + (getPageSize() - 1) * minWidth),
                (int) (sliderHeight));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPageSize() > 1) {
            for (int i = 0; i < getPageSize(); i++) {
                if (getSlideMode() == IndicatorSlideMode.SMOOTH) {
                    smoothSlide(canvas, i);
                } else {
                    normalSlide(canvas, i);
                }
            }
        }
    }


    private void normalSlide(Canvas canvas, int i) {
        if (getNormalIndicatorWidth() == getCheckedIndicatorWidth()) {
            mPaint.setColor(getNormalColor());
            float left = i * (getNormalIndicatorWidth()) + i * +getIndicatorGap();
            canvas.drawRect(left, 0, left + getNormalIndicatorWidth(), sliderHeight, mPaint);
            drawSliderStyle(canvas);
        } else {  //  仿支付宝首页轮播图的Indicator
            if (i < getCurrentPosition()) {
                mPaint.setColor(getNormalColor());
                float left = i * minWidth + i * getIndicatorGap();
                canvas.drawRect(left, 0, left + minWidth, sliderHeight, mPaint);
            } else if (i == getCurrentPosition()) {
                mPaint.setColor(getCheckedColor());
                float left = i * minWidth + i * getIndicatorGap();
                canvas.drawRect(left, 0, left + minWidth + (maxWidth - minWidth), sliderHeight, mPaint);
            } else {
                mPaint.setColor(getNormalColor());
                float left = i * minWidth + i * getIndicatorGap() + (maxWidth - minWidth);
                canvas.drawRect(left, 0, left + minWidth, sliderHeight, mPaint);
            }
        }
    }

    private void smoothSlide(Canvas canvas, int i) {
        mPaint.setColor(getNormalColor());
        float left = i * (maxWidth) + i * +getIndicatorGap() + (maxWidth - minWidth);
        canvas.drawRect(left, 0, left + minWidth, sliderHeight, mPaint);
        drawSliderStyle(canvas);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
    }

    private void drawSliderStyle(Canvas canvas) {
        mPaint.setColor(getCheckedColor());
        float left = getCurrentPosition() * (maxWidth) + getCurrentPosition() * +getIndicatorGap() + (maxWidth + getIndicatorGap()) * getSlideProgress();
        canvas.drawRect(left, 0, left + maxWidth, sliderHeight, mPaint);
    }

    public DashIndicatorView setSliderHeight(int sliderHeight) {
        this.sliderHeight = sliderHeight;
        return this;
    }
}
