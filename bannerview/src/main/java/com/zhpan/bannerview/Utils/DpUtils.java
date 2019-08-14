package com.zhpan.bannerview.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class DpUtils {
    public static int dp2px(Context context, float dpValue) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        float screenDensity = metric.density;
        return (int) (dpValue * screenDensity + 0.5f);
    }
}
