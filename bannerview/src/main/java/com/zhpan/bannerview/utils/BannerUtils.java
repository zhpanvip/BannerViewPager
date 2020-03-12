package com.zhpan.bannerview.utils;

import android.content.res.Resources;
import android.util.Log;

import com.zhpan.bannerview.manager.IndicatorOptions;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class BannerUtils {

    private static boolean debugMode = false;

    public static void setDebugMode(boolean isDebug) {
        debugMode = isDebug;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static int dp2px(float dpValue) {
        return (int) (0.5F + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void log(String tag, String msg) {
        if (isDebugMode()) {
            Log.e(tag, msg);
        }
    }

    public static void log(String msg) {
        if (isDebugMode()) {
            Log.e("BannerView", msg);
        }
    }

    public static int getRealPosition(boolean isCanLoop, int position, int pageSize) {
        return isCanLoop ? (position - 1 + pageSize) % pageSize : (position + pageSize) % pageSize;
    }

    public static float getCoordinateX(IndicatorOptions indicatorOptions, float maxDiameter, int index) {
        float normalIndicatorWidth = indicatorOptions.getNormalSliderWidth();
        return maxDiameter / 2 + (normalIndicatorWidth + indicatorOptions.getSliderGap()) * index;
    }

    public static float getCoordinateY(float maxDiameter) {
        return maxDiameter / 2;
    }
}
