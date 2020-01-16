package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.manager.IndicatorOptions;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhpan on 2019/11/26.
 *   Description:
 * </pre>
 */
public class RoundRectDrawer extends BaseDrawer {

    private float maxWidth;

    private float minWidth;

    RoundRectDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
    }

    @Override
    public MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        maxWidth = Math.max(mIndicatorOptions.getNormalIndicatorWidth(), mIndicatorOptions.getCheckedIndicatorWidth());
        minWidth = Math.min(mIndicatorOptions.getNormalIndicatorWidth(), mIndicatorOptions.getCheckedIndicatorWidth());
        mMeasureResult.setMeasureResult(measureWidth(), (int) mIndicatorOptions.getSliderHeight());
        return mMeasureResult;
    }

    private int measureWidth() {
        int pageSize = mIndicatorOptions.getPageSize();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        return (int) ((pageSize - 1) * indicatorGap + maxWidth + (pageSize - 1) * minWidth);
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
            mPaint.setColor(mIndicatorOptions.getNormalColor());
            float sliderHeight = mIndicatorOptions.getSliderHeight();
            float left = i * (maxWidth) + i * +mIndicatorOptions.getIndicatorGap() + (maxWidth - minWidth);
            RectF rectF = new RectF(left, 0, left + minWidth, sliderHeight);
            canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
        }
    }

    private void drawInequalitySlider(Canvas canvas, int i) {
        int normalColor = mIndicatorOptions.getNormalColor();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        RectF rectF;
        if (i < currentPosition) {
            mPaint.setColor(normalColor);
            float left = i * minWidth + i * indicatorGap;
            rectF = new RectF(left, 0, left + minWidth, sliderHeight);
            canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
        } else if (i == currentPosition) {
            mPaint.setColor(mIndicatorOptions.getCheckedColor());
            float left = i * minWidth + i * indicatorGap;
            rectF = new RectF(left, 0, left + minWidth + (maxWidth - minWidth), sliderHeight);
            canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
        } else {
            mPaint.setColor(normalColor);
            float left = i * minWidth + i * indicatorGap + (maxWidth - minWidth);
            rectF = new RectF(left, 0, left + minWidth, sliderHeight);
            canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
        }
    }

    private void drawCheckedSlider(Canvas canvas) {
        mPaint.setColor(mIndicatorOptions.getCheckedColor());
        switch (mIndicatorOptions.getSlideMode()) {
            case IndicatorSlideMode.SMOOTH:
                drawSmoothSlider(canvas);
                break;
            case IndicatorSlideMode.WORM:
                drawWormSlider(canvas);
                break;
        }
    }

    private RectF rectF = new RectF();

    private void drawWormSlider(Canvas canvas) {
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        float slideProgress = mIndicatorOptions.getSlideProgress();
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float distance = mIndicatorOptions.getIndicatorGap() + mIndicatorOptions.getNormalIndicatorWidth();
        float startCoordinateX = BannerUtils.getCoordinateX(mIndicatorOptions, maxWidth, currentPosition);
        float left = startCoordinateX + Math.max(distance * (slideProgress - 0.5f) * 2.0f, 0) - mIndicatorOptions.getNormalIndicatorWidth() / 2;
        float right = startCoordinateX + Math.min((distance * slideProgress * 2), distance) + mIndicatorOptions.getNormalIndicatorWidth() / 2;
        rectF.set(left, 0, right, sliderHeight);
        canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
    }

    private void drawSmoothSlider(Canvas canvas) {
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        float left = currentPosition * (maxWidth) + currentPosition * +indicatorGap + (maxWidth + indicatorGap) * mIndicatorOptions.getSlideProgress();
        RectF rectF = new RectF(left, 0, left + maxWidth, sliderHeight);
        canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
    }
}
