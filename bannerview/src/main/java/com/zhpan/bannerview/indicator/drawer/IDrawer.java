package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;
import android.util.Pair;

import com.zhpan.bannerview.manager.IndicatorOptions;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description:
 * </pre>
 */
public interface IDrawer {

    Pair<Integer, Integer> measure(int widthMeasureSpec, int heightMeasureSpec);

    void draw(Canvas canvas);

    void setIndicatorOptions(IndicatorOptions indicatorOptions);

    IndicatorOptions getIndicatorOptions();
}
