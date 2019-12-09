package com.zhpan.bannerview.indicator.drawer;

import com.zhpan.bannerview.manager.IndicatorOptions;

import static com.zhpan.bannerview.constants.IndicatorStyle.DASH;
import static com.zhpan.bannerview.constants.IndicatorStyle.ROUND_RECT;

/**
 * <pre>
 *   Created by zhpan on 2019/11/24.
 *   Description: Indicator Drawer Factory.
 * </pre>
 */
class DrawerFactory {
    static IDrawer createDrawer(IndicatorOptions indicatorOptions) {
        IDrawer drawer;
        int indicatorStyle = indicatorOptions.getIndicatorStyle();
        if (indicatorStyle == DASH) {
            drawer = new DashDrawer(indicatorOptions);
        } else if (indicatorStyle == ROUND_RECT) {
            drawer = new RoundRectDrawer(indicatorOptions);
        } else {
            drawer = new CircleDrawer(indicatorOptions);
        }
        return drawer;
    }
}
