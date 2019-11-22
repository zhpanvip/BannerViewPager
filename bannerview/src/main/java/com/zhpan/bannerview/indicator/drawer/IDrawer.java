package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;

import com.zhpan.bannerview.manager.IndicatorOptions;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description:
 * </pre>
 */
public interface IDrawer {

    BaseDrawer.MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec);

    void onDraw(Canvas canvas);
}
