package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Paint;
import android.graphics.RectF;

import com.zhpan.bannerview.manager.IndicatorOptions;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description:
 * </pre>
 */
public abstract class BaseDrawer implements IDrawer {

    private MeasureResult mMeasureResult;
    IndicatorOptions mIndicatorOptions;
    float maxWidth;
    float minWidth;
    Paint mPaint;
    RectF mRectF;

    BaseDrawer(IndicatorOptions indicatorOptions) {
        this.mIndicatorOptions = indicatorOptions;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMeasureResult = new MeasureResult();
        mRectF = new RectF();
    }

    @Override
    public MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        maxWidth = Math.max(mIndicatorOptions.getNormalIndicatorWidth(), mIndicatorOptions.getCheckedIndicatorWidth());
        minWidth = Math.min(mIndicatorOptions.getNormalIndicatorWidth(), mIndicatorOptions.getCheckedIndicatorWidth());
        mMeasureResult.setMeasureResult(measureWidth(), measureHeight());
        return mMeasureResult;
    }

    protected int measureHeight() {
        return (int) mIndicatorOptions.getSliderHeight();
    }

    private int measureWidth() {
        int pageSize = mIndicatorOptions.getPageSize();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        return (int) ((pageSize - 1) * indicatorGap + maxWidth + (pageSize - 1) * minWidth);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }

    public class MeasureResult {

        int measureWidth;

        int measureHeight;

        void setMeasureResult(int measureWidth, int measureHeight) {
            this.measureWidth = measureWidth;
            this.measureHeight = measureHeight;
        }

        public int getMeasureWidth() {
            return measureWidth;
        }

        public int getMeasureHeight() {
            return measureHeight;
        }

    }

    protected boolean isWidthEquals() {
        return mIndicatorOptions.getNormalIndicatorWidth() == mIndicatorOptions.getCheckedIndicatorWidth();
    }
}
