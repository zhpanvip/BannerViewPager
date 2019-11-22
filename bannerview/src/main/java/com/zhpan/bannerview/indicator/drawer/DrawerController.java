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
public class DrawerController {
    
    private IDrawer mIDrawer;

    public DrawerController(IndicatorOptions indicatorOptions) {
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

    public BaseDrawer.MeasureResult measure(int widthMeasureSpec, int heightMeasureSpec) {
        return mIDrawer.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void draw(Canvas canvas) {
        mIDrawer.onDraw(canvas);
    }
}
