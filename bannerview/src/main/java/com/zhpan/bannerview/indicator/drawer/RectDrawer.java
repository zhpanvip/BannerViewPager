package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;

import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.manager.IndicatorOptions;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhpan on 2020/1/17.
 *   Description:
 * </pre>
 */
public class RectDrawer extends BaseDrawer {

    RectDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int pageSize = mIndicatorOptions.getPageSize();
        if (pageSize > 1) {
            if (isWidthEquals() && mIndicatorOptions.getSlideMode() != IndicatorSlideMode.NORMAL) {
                drawUncheckedSlider(canvas, pageSize);
                drawCheckedSlider(canvas);
            } else {    // 单独处理normalWidth与checkedWidth不一致的情况
                for (int i = 0; i < pageSize; i++) {
                    drawInequalitySlider(canvas, i);
                }
            }
        }
    }

    private void drawUncheckedSlider(Canvas canvas, int pageSize) {
        for (int i = 0; i < pageSize; i++) {
            mPaint.setColor(mIndicatorOptions.getNormalSliderColor());
            float sliderHeight = mIndicatorOptions.getSliderHeight();
            float left = i * (maxWidth) + i * +mIndicatorOptions.getSliderGap() + (maxWidth - minWidth);
            mRectF.set(left, 0, left + minWidth, sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        }
    }

    private void drawInequalitySlider(Canvas canvas, int i) {
        int normalColor = mIndicatorOptions.getNormalSliderColor();
        float indicatorGap = mIndicatorOptions.getSliderGap();
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        if (i < currentPosition) {
            mPaint.setColor(normalColor);
            float left = i * minWidth + i * indicatorGap;
            mRectF.set(left, 0, left + minWidth, sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        } else if (i == currentPosition) {
            mPaint.setColor(mIndicatorOptions.getCheckedSliderColor());
            float left = i * minWidth + i * indicatorGap;
            mRectF.set(left, 0, left + minWidth + (maxWidth - minWidth), sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        } else {
            mPaint.setColor(normalColor);
            float left = i * minWidth + i * indicatorGap + (maxWidth - minWidth);
            mRectF.set(left, 0, left + minWidth, sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        }
    }

    private void drawCheckedSlider(Canvas canvas) {
        mPaint.setColor(mIndicatorOptions.getCheckedSliderColor());
        switch (mIndicatorOptions.getSlideMode()) {
            case IndicatorSlideMode.SMOOTH:
                drawSmoothSlider(canvas);
                break;
            case IndicatorSlideMode.WORM:
                drawWormSlider(canvas);
                break;
        }
    }

    private void drawWormSlider(Canvas canvas) {
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        float slideProgress = mIndicatorOptions.getSlideProgress();
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float distance = mIndicatorOptions.getSliderGap() + mIndicatorOptions.getNormalSliderWidth();
        float startCoordinateX = BannerUtils.getCoordinateX(mIndicatorOptions, maxWidth, currentPosition);
        float left = startCoordinateX + Math.max(distance * (slideProgress - 0.5f) * 2.0f, 0) - mIndicatorOptions.getNormalSliderWidth() / 2;
        float right = startCoordinateX + Math.min((distance * slideProgress * 2), distance) + mIndicatorOptions.getNormalSliderWidth() / 2;
        mRectF.set(left, 0, right, sliderHeight);
        drawRoundRect(canvas, sliderHeight, sliderHeight);
    }

    private void drawSmoothSlider(Canvas canvas) {
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float indicatorGap = mIndicatorOptions.getSliderGap();
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        float left = currentPosition * (maxWidth) + currentPosition * +indicatorGap + (maxWidth + indicatorGap) * mIndicatorOptions.getSlideProgress();
        mRectF.set(left, 0, left + maxWidth, sliderHeight);
        drawRoundRect(canvas, sliderHeight, sliderHeight);
    }

    protected void drawRoundRect(Canvas canvas, float rx, float ry) {
        drawDash(canvas);
    }

    protected void drawDash(Canvas canvas) {
    }
}
