package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;
import android.util.Pair;

import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.manager.IndicatorOptions;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description:Dash Indicator Drawer.
 * </pre>
 */
public class DashDrawer extends BaseDrawer {

    private float maxWidth;

    private float minWidth;

    public DashDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
    }

    @Override
    public Pair<Integer, Integer> measure(int widthMeasureSpec, int heightMeasureSpec) {
        maxWidth = Math.max(mIndicatorOptions.getNormalIndicatorWidth(), mIndicatorOptions.getCheckedIndicatorWidth());
        minWidth = Math.min(mIndicatorOptions.getNormalIndicatorWidth(), mIndicatorOptions.getCheckedIndicatorWidth());
        return new Pair<>(getMeasureWidth(), (int) mIndicatorOptions.getSliderHeight());
    }

    private int getMeasureWidth() {
        int pageSize = mIndicatorOptions.getPageSize();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        return (int) ((pageSize - 1) * indicatorGap + maxWidth + (pageSize - 1) * minWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        drawIndicator(canvas);
    }

    @Override
    public IndicatorOptions getIndicatorOptions() {
        return mIndicatorOptions;
    }

    private void drawIndicator(Canvas canvas) {
        int pageSize = mIndicatorOptions.getPageSize();
        if (pageSize > 1) {
            for (int i = 0; i < pageSize; i++) {
                if (mIndicatorOptions.getSlideMode() == IndicatorSlideMode.SMOOTH) {
                    smoothSlide(canvas, i);
                } else {
                    normalSlide(canvas, i);
                }
            }
        }
    }

    private void normalSlide(Canvas canvas, int i) {
        float normalIndicatorWidth = mIndicatorOptions.getNormalIndicatorWidth();
        int normalColor = mIndicatorOptions.getNormalColor();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        if (normalIndicatorWidth == mIndicatorOptions.getCheckedIndicatorWidth()) {
            mPaint.setColor(normalColor);
            float left = i * (normalIndicatorWidth) + i * +indicatorGap;
            canvas.drawRect(left, 0, left + normalIndicatorWidth, sliderHeight, mPaint);
            drawSliderStyle(canvas);
        } else {  //  仿支付宝首页轮播图的Indicator
            if (i < currentPosition) {
                mPaint.setColor(normalColor);
                float left = i * minWidth + i * indicatorGap;
                canvas.drawRect(left, 0, left + minWidth, sliderHeight, mPaint);
            } else if (i == currentPosition) {
                mPaint.setColor(mIndicatorOptions.getCheckedColor());
                float left = i * minWidth + i * indicatorGap;
                canvas.drawRect(left, 0, left + minWidth + (maxWidth - minWidth), sliderHeight, mPaint);
            } else {
                mPaint.setColor(normalColor);
                float left = i * minWidth + i * indicatorGap + (maxWidth - minWidth);
                canvas.drawRect(left, 0, left + minWidth, sliderHeight, mPaint);
            }
        }
    }

    private void smoothSlide(Canvas canvas, int i) {
        mPaint.setColor(mIndicatorOptions.getNormalColor());
        float left = i * (maxWidth) + i * +mIndicatorOptions.getIndicatorGap() + (maxWidth - minWidth);
        canvas.drawRect(left, 0, left + minWidth, mIndicatorOptions.getSliderHeight(), mPaint);
        drawSliderStyle(canvas);
    }

    private void drawSliderStyle(Canvas canvas) {
        mPaint.setColor(mIndicatorOptions.getCheckedColor());
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        float left = currentPosition * (maxWidth) + currentPosition * +indicatorGap + (maxWidth + indicatorGap) * mIndicatorOptions.getSlideProgress();
        canvas.drawRect(left, 0, left + maxWidth, mIndicatorOptions.getSliderHeight(), mPaint);
    }
}
