package com.zhpan.bannerview.view;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Author zhangpan
 * Time:2018/11/14 15:24
 * Description:处理嵌套PhotoView缩放引起的crash.
 */
public class CatchViewPager extends ViewPager {
    private ArrayList<Integer> mArrayList = new ArrayList<>();
    private SparseIntArray mSparseIntArray = new SparseIntArray();
    private boolean mMultiPageOverlay = false;

    public CatchViewPager(Context context) {
        this(context, null);
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

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (mMultiPageOverlay) {
            if (i == 0 || mSparseIntArray.size() != childCount) {
                mArrayList.clear();
                mSparseIntArray.clear();
                int viewCenterX = getViewCenterX(this);
                for (int index = 0; index < childCount; ++index) {
                    int indexAbs = Math.abs(viewCenterX - getViewCenterX(getChildAt(index)));
                    mArrayList.add(++indexAbs);
                    mSparseIntArray.append(indexAbs, index);
                }
                Collections.sort(mArrayList);
            }
            return mSparseIntArray.get(mArrayList.get(childCount - 1 - i));
        } else {
            return super.getChildDrawingOrder(childCount, i);
        }
    }

    private int getViewCenterX(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);
        return array[0] + view.getWidth() / 2;
    }

    public void setMultiPageOverlay(boolean multiPageOverlay) {
        mMultiPageOverlay = multiPageOverlay;
    }
}
