package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Paint;

import com.zhpan.bannerview.manager.IndicatorOptions;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description:
 * </pre>
 */
public abstract class BaseDrawer implements IDrawer {

    IndicatorOptions mIndicatorOptions;

    Paint mPaint;

    MeasureResult mMeasureResult;

    BaseDrawer(IndicatorOptions indicatorOptions) {
        this.mIndicatorOptions = indicatorOptions;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMeasureResult = new MeasureResult();
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
}
