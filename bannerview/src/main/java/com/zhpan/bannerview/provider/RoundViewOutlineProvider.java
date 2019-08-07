

package com.zhpan.bannerview.provider;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * <pre>
 *   Created by zhangpan on 2018/12/26.
 *   Description:圆角效果
 * </pre>
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RoundViewOutlineProvider extends ViewOutlineProvider {

    private float mRadius;//圆角弧度

    public RoundViewOutlineProvider(float radius) {
        this.mRadius = radius;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        Rect selfRect = new Rect(0, 0, view.getWidth(), view.getHeight());// 绘制区域
        outline.setRoundRect(selfRect, mRadius);
    }
}
