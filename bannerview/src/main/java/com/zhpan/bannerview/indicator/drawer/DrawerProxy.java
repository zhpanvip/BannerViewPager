package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;

import com.zhpan.bannerview.manager.IndicatorOptions;

import static com.zhpan.bannerview.constants.IndicatorStyle.CIRCLE;
import static com.zhpan.bannerview.constants.IndicatorStyle.DASH;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description: Indicator Drawer Controller.
 * </pre>
 */
public class DrawerProxy implements IDrawer{
    
    private IDrawer mIDrawer;

    public DrawerProxy(IndicatorOptions indicatorOptions) {
        init(indicatorOptions);
    }

    private void init(IndicatorOptions indicatorOptions) {
        switch (indicatorOptions.getIndicatorStyle()) {
            case CIRCLE:
                mIDrawer = new CircleDrawer(indicatorOptions);
                break;
            case DASH:
                mIDrawer = new DashDrawer(indicatorOptions);
                break;
        }
    }

    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        init(indicatorOptions);
    }

    @Override
    public BaseDrawer.MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        return mIDrawer.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        mIDrawer.onDraw(canvas);
    }
}
