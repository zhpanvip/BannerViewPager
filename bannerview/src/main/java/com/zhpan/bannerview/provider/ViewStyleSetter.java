

package com.zhpan.bannerview.provider;

import android.os.Build;
import androidx.annotation.RequiresApi;
import android.view.View;

/**
 * <pre>
 *   Created by zhangpan on 2018/12/26.
 *   Description:为View设置圆角/圆形效果，支持View及ViewGroup，适用Android 5.1及以上系统。
 * </pre>
 */

public class ViewStyleSetter {

    private ViewStyleSetter() {}

    /**
     * 为View设置圆角效果
     *
     * @param radius 圆角半径
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void applyRoundCorner(View target, float radius) {
        if(target == null) {
            return;
        }
        target.setClipToOutline(true);// 用outline裁剪内容区域
        target.setOutlineProvider(new RoundViewOutlineProvider(radius));
    }
}
