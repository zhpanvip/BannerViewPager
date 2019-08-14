package com.zhpan.idea.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * dp px转换
 */
public class DensityUtils {
    private static int screenW;
    private static int screenH;
    private static float screenDensity;

    public static int getScreenW(Context context){
        if (screenW == 0){
            initScreen(context);
        }
        return screenW;
    }

    public static int getScreenH(Context context){
        if (screenH == 0){
            initScreen(context);
        }
        return screenH;
    }

    public static float getScreenDensity(Context context){
        if (screenDensity == 0){
            initScreen(context);
        }
        return screenDensity;
    }

    private static void initScreen(Context context){
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        screenW = metric.widthPixels;
        screenH = metric.heightPixels;
        screenDensity = metric.density;
    }


    public static int dp2px(Context context, float dpValue) {
        return (int) (dpValue * getScreenDensity(context) + 0.5f);
    }


    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / getScreenDensity(context) + 0.5f);
    }
}
