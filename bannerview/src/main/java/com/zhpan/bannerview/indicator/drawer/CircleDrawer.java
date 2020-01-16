package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.zhpan.bannerview.manager.IndicatorOptions;
import com.zhpan.bannerview.utils.BannerUtils;

import static com.zhpan.bannerview.constants.IndicatorSlideMode.NORMAL;
import static com.zhpan.bannerview.constants.IndicatorSlideMode.SMOOTH;
import static com.zhpan.bannerview.constants.IndicatorSlideMode.WORM;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description: Circle Indicator drawer.
 * </pre>
 */
public class CircleDrawer extends BaseDrawer {

    private float maxWidth;

    private float minWidth;

    CircleDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
    }

    private RectF rectF = new RectF();

    @Override
    public MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        maxWidth = Math.max(mIndicatorOptions.getNormalIndicatorWidth(), mIndicatorOptions.getCheckedIndicatorWidth());
        minWidth = Math.min(mIndicatorOptions.getNormalIndicatorWidth(), mIndicatorOptions.getCheckedIndicatorWidth());
        mMeasureResult.setMeasureResult(measureWidth(), (int) maxWidth);
        return mMeasureResult;
    }

    private int measureWidth() {
        int pageSize = mIndicatorOptions.getPageSize();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        return (int) ((pageSize - 1) * indicatorGap + maxWidth + (pageSize - 1) * minWidth);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mIndicatorOptions.getPageSize() > 1) {
            drawNormal(canvas);
            drawSlider(canvas);
        }
    }

    private void drawNormal(Canvas canvas) {
        float normalIndicatorWidth = mIndicatorOptions.getNormalIndicatorWidth();
        mPaint.setColor(mIndicatorOptions.getNormalColor());
        for (int i = 0; i < mIndicatorOptions.getPageSize(); i++) {
            float coordinateX = BannerUtils.getCoordinateX(mIndicatorOptions, maxWidth, i);
            float coordinateY = BannerUtils.getCoordinateY(maxWidth);
            drawCircle(canvas, coordinateX, coordinateY, normalIndicatorWidth / 2);
        }
    }

    private void drawSlider(Canvas canvas) {
        mPaint.setColor(mIndicatorOptions.getCheckedColor());
        switch (mIndicatorOptions.getSlideMode()) {
            case NORMAL:
            case SMOOTH:
                drawCircleSlider(canvas);
                break;
            case WORM:
                drawWormSlider(canvas, mIndicatorOptions.getNormalIndicatorWidth());
                break;
        }
    }

    private void drawCircleSlider(Canvas canvas) {
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float startCoordinateX = BannerUtils.getCoordinateX(mIndicatorOptions, maxWidth, currentPosition);
        float endCoordinateX = BannerUtils.getCoordinateX(mIndicatorOptions, maxWidth, (currentPosition + 1) % mIndicatorOptions.getPageSize());
        float coordinateX = startCoordinateX + (endCoordinateX - startCoordinateX) * mIndicatorOptions.getSlideProgress();
        float coordinateY = BannerUtils.getCoordinateY(maxWidth);
        float radius = mIndicatorOptions.getCheckedIndicatorWidth() / 2;
        drawCircle(canvas, coordinateX, coordinateY, radius);
    }

    private void drawWormSlider(Canvas canvas, float sliderHeight) {
        float slideProgress = mIndicatorOptions.getSlideProgress();
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float distance = mIndicatorOptions.getIndicatorGap() + mIndicatorOptions.getNormalIndicatorWidth();
        float startCoordinateX = BannerUtils.getCoordinateX(mIndicatorOptions, maxWidth, currentPosition);
        float left = startCoordinateX + Math.max(distance * (slideProgress - 0.5f) * 2.0f, 0) - mIndicatorOptions.getNormalIndicatorWidth() / 2;
        float right = startCoordinateX + Math.min((distance * slideProgress * 2), distance) + mIndicatorOptions.getNormalIndicatorWidth() / 2;
        rectF.set(left, 0, right, sliderHeight);
        canvas.drawRoundRect(rectF, sliderHeight, sliderHeight, mPaint);
    }

    private void drawCircle(Canvas canvas, float coordinateX, float coordinateY, float radius) {
        canvas.drawCircle(coordinateX, coordinateY, radius, mPaint);
    }
}
