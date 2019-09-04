package com.zhpan.bannerview.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author zhangpan
 * @Time:2018/11/14 15:24
 * @Description:
 */
public class CatchViewPager extends ViewPager {
    public CatchViewPager(Context context) {
        this(context,null);
    }

    public CatchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
