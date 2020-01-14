package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;

import com.zhpan.bannerview.manager.IndicatorOptions;
import com.zhpan.bannerview.utils.BannerUtils;

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
            drawableNormalCircle(canvas);
            drawSelectedCircle(canvas);
        }
    }

    private void drawableNormalCircle(Canvas canvas) {
        float normalIndicatorWidth = mIndicatorOptions.getNormalIndicatorWidth();
        mPaint.setColor(mIndicatorOptions.getNormalColor());
        for (int i = 0; i < mIndicatorOptions.getPageSize(); i++) {
            float coordinateX = BannerUtils.getCoordinateX(mIndicatorOptions, maxWidth, i);
            float coordinateY = BannerUtils.getCoordinateY(maxWidth);
            drawCircle(canvas, coordinateX, coordinateY, normalIndicatorWidth / 2);
        }
    }

    private void drawSelectedCircle(Canvas canvas) {
        mPaint.setColor(mIndicatorOptions.getCheckedColor());
        float normalIndicatorWidth = mIndicatorOptions.getNormalIndicatorWidth();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        float coordinateX = maxWidth / 2 + (normalIndicatorWidth + indicatorGap) * mIndicatorOptions.getCurrentPosition()
                + (normalIndicatorWidth + indicatorGap) * mIndicatorOptions.getSlideProgress();
        float coordinateY = maxWidth / 2f;
        float radius = mIndicatorOptions.getCheckedIndicatorWidth() / 2;
        drawCircle(canvas, coordinateX, coordinateY, radius);
    }

    private void drawCircle(Canvas canvas, float coordinateX, float coordinateY, float radius) {
        canvas.drawCircle(coordinateX, coordinateY, radius, mPaint);
    }
}
