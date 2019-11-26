package com.zhpan.bannerview.indicator.drawer;

import com.zhpan.bannerview.manager.IndicatorOptions;

import static com.zhpan.bannerview.constants.IndicatorStyle.DASH;

/**
 * <pre>
 *   Created by zhpan on 2019/11/24.
 *   Description: Indicator Drawer Factory.
 * </pre>
 */
class DrawerFactory {
    static IDrawer createDrawer(IndicatorOptions indicatorOptions) {
        IDrawer drawer;
        if (indicatorOptions.getIndicatorStyle() == DASH) {
            drawer = new DashDrawer(indicatorOptions);
        } else {
            drawer = new CircleDrawer(indicatorOptions);
        }
        return drawer;
    }
}
