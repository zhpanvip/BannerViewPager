package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;

import com.zhpan.bannerview.manager.IndicatorOptions;

import static com.zhpan.bannerview.constants.IndicatorStyle.CIRCLE;
import static com.zhpan.bannerview.constants.IndicatorStyle.DASH;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description: Indicator Drawer Proxy.
 * </pre>
 */
public class DrawerProxy implements IDrawer {

    private IDrawer mIDrawer;

    public DrawerProxy(IndicatorOptions indicatorOptions) {
        init(indicatorOptions);
    }

    private void init(IndicatorOptions indicatorOptions) {
        mIDrawer = DrawerFactory.createDrawer(indicatorOptions);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }

    @Override
    public BaseDrawer.MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        return mIDrawer.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        mIDrawer.onDraw(canvas);
    }

    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        init(indicatorOptions);
    }
}
