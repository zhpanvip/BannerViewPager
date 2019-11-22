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

    protected IndicatorOptions mIndicatorOptions;

    protected Paint mPaint;

    public BaseDrawer(IndicatorOptions indicatorOptions) {
        this.mIndicatorOptions = indicatorOptions;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        this.mIndicatorOptions = indicatorOptions;
    }
}
