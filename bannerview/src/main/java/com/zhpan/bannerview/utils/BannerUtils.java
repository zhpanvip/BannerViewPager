/*
Copyright 2017 zhpanvip The BannerViewPager Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.zhpan.bannerview.utils;

import android.content.res.Resources;
import android.util.Log;

import com.zhpan.bannerview.BaseBannerAdapter;

import static com.zhpan.bannerview.BaseBannerAdapter.MAX_VALUE;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class BannerUtils {

    private static boolean debugMode = false;

    private static final String TAG = "BVP";

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
            log(TAG, msg);
        }
    }

    /**
     * 在循环模式下{@link com.zhpan.bannerview.BannerViewPager}会初始化一个item为
     * {@link BaseBannerAdapter#MAX_VALUE}的ViewPager2,并将当前position设置为ViewPager2
     * 的中间位置，因此，此时的position需要通过该方法进行转换为真实的position。
     *
     * @param position  当前position
     * @param pageSize  轮播图页面数
     * @return 真实的position
     */
    public static int getRealPosition(int position, int pageSize) {
        if (pageSize == 0) {
            return 0;
        }
        return (position + pageSize) % pageSize;
    }

    /**
     * @param pageSize 轮播图页面数
     * @return 轮播图初始位置
     */
    public static int getOriginalPosition(int pageSize) {
        return MAX_VALUE / 2 - ((MAX_VALUE / 2) % pageSize);
    }
}
