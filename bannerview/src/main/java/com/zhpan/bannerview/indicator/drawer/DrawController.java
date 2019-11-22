package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;
import android.util.Pair;

import com.zhpan.bannerview.manager.IndicatorOptions;

import static com.zhpan.bannerview.constants.IndicatorStyle.CIRCLE;
import static com.zhpan.bannerview.constants.IndicatorStyle.DASH;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description:
 * </pre>
 */
public class DrawController {

    private IndicatorOptions mIndicatorOptions;

    private IDrawer mIDrawer;

    public DrawController(IndicatorOptions indicatorOptions) {
        this.mIndicatorOptions = indicatorOptions;
        init();
    }

    private void init() {
        switch (mIndicatorOptions.getIndicatorStyle()) {
            case CIRCLE:
                mIDrawer = new CircleDrawer(mIndicatorOptions);
                break;
            case DASH:
                mIDrawer = new DashDrawer(mIndicatorOptions);
                break;
        }
    }

    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        mIndicatorOptions = indicatorOptions;
        mIDrawer.setIndicatorOptions(indicatorOptions);
        init();
    }

    public Pair<Integer, Integer> measure(int widthMeasureSpec, int heightMeasureSpec) {
        return mIDrawer.measure(widthMeasureSpec, heightMeasureSpec);
    }

    public void draw(Canvas canvas) {
        mIDrawer.draw(canvas);
    }
}
