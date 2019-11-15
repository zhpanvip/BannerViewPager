package com.zhpan.bannerview.utils;

import android.content.res.Resources;
import android.util.Log;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class BannerUtils {

    private static final boolean DEBUG = true;

    public float density;

    public BannerUtils() {
        this.density = Resources.getSystem().getDisplayMetrics().density;
    }

    public static int dp2px(float dpValue) {
        return (int) (0.5F + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float px2dp(float pxValue) {
        return pxValue / Resources.getSystem().getDisplayMetrics().density;
    }

    public int dip2px(float dpValue) {
        return (int) (0.5F + dpValue * this.density);
    }

    public float px2dip(int pxValue) {
        return (float) pxValue / this.density;
    }


    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e("BannerView", msg);
        }
    }
}
