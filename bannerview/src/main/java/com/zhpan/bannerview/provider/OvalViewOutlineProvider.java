
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
 *   Description:圆形效果
 * </pre>
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class OvalViewOutlineProvider extends ViewOutlineProvider {


    public OvalViewOutlineProvider() {}

    @Override
    public void getOutline(final View view, final Outline outline) {
        Rect ovalRect = convertToCircleRect(new Rect(0, 0, view.getWidth(), view.getHeight()));
        outline.setOval(ovalRect);
    }

    /**
     * 以矩形的中心点为圆心,较短的边为直径画圆
     *
     * 注意, 由于整除省略了小数, (x/2)*2不一定等于x
     * 
     * Currently, only Outlines that can be represented as a rectangle, circle, or round rect
     * support clipping.
     * 
     * @param rect
     * @return
     */
    private Rect convertToCircleRect(Rect rect) {
        int left, top, right, bottom;
        if(rect.width() > rect.height()) {
            int dH = rect.height() / 2;
            left = rect.centerX() - dH;
            top = 0;
            right = rect.centerX() + dH;
            bottom = dH * 2;
        } else {
            int dW = rect.width() / 2;
            left = 0;
            top = rect.centerY() - dW;
            right = dW * 2;
            bottom = rect.centerY() + dW;
        }
        rect.set(left, top, right, bottom);
        return rect;
    }

}
