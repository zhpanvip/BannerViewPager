package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.manager.IndicatorOptions;

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
        mMeasureResult.setMeasureResult(getMeasureWidth(), (int) mIndicatorOptions.getSliderHeight());
        return mMeasureResult;
    }

    private int getMeasureWidth() {
        int pageSize = mIndicatorOptions.getPageSize();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        return (int) ((pageSize - 1) * indicatorGap + maxWidth + (pageSize - 1) * minWidth);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawIndicator(canvas);
    }

    private void drawIndicator(Canvas canvas) {
        int pageSize = mIndicatorOptions.getPageSize();
        if (pageSize > 1) {
            for (int i = 0; i < pageSize; i++) {
                switch (mIndicatorOptions.getSlideMode()) {
                    case IndicatorSlideMode.NORMAL:
                        normalSlide(canvas, i);
                        break;
                    case IndicatorSlideMode.SMOOTH:
                    case IndicatorSlideMode.WORM:
                        if (mIndicatorOptions.getNormalIndicatorWidth() == mIndicatorOptions.getCheckedIndicatorWidth()) {
                            smoothSlide(canvas, i);
                        } else {
                            normalSlide(canvas, i);
                        }
                        break;
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
        RectF rectF;
        if (normalIndicatorWidth == mIndicatorOptions.getCheckedIndicatorWidth()) {
            mPaint.setColor(normalColor);
            float left = i * (normalIndicatorWidth) + i * +indicatorGap;
            rectF = new RectF(left, 0, left + normalIndicatorWidth, sliderHeight);
            canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
            drawSliderStyle(canvas);
        } else {
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
    }

    private void smoothSlide(Canvas canvas, int i) {
        mPaint.setColor(mIndicatorOptions.getNormalColor());
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        float left = i * (maxWidth) + i * +mIndicatorOptions.getIndicatorGap() + (maxWidth - minWidth);
        RectF rectF = new RectF(left, 0, left + minWidth, sliderHeight);
        canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
        drawSliderStyle(canvas);
    }

    private void drawSliderStyle(Canvas canvas) {
        mPaint.setColor(mIndicatorOptions.getCheckedColor());
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        float left = currentPosition * (maxWidth) + currentPosition * +indicatorGap + (maxWidth + indicatorGap) * mIndicatorOptions.getSlideProgress();
        RectF rectF = new RectF(left, 0, left + maxWidth, sliderHeight);
        canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
//        canvas.drawRect(left, 0, left + maxWidth, mIndicatorOptions.getSliderHeight(), mPaint);
    }


}
