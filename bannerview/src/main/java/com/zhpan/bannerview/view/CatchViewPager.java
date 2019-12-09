package com.zhpan.bannerview.view;

import android.annotation.SuppressLint;
import android.content.Context;

import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

import com.zhpan.bannerview.provider.BannerScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import static com.zhpan.bannerview.manager.BannerOptions.DEFAULT_SCROLL_DURATION;

/**
 * Author zhangpan
 * Time:2018/11/14 15:24
 * Description:处理嵌套PhotoView缩放引起的crash.
 */
public class CatchViewPager extends ViewPager {
    private ArrayList<Integer> mArrayList = new ArrayList<>();
    private SparseIntArray mSparseIntArray = new SparseIntArray();
    private boolean mOverlapStyle = false;
    private BannerScroller mBannerScroller;
    private boolean disableTouchScroll;
    private boolean firstLayout = true;

    public CatchViewPager(Context context) {
        this(context, null);
    }

    public CatchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        hookScroller();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            if (disableTouchScroll) {
                return false;
            }
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (mOverlapStyle) {
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

    public void setOverlapStyle(boolean overlapStyle) {
        mOverlapStyle = overlapStyle;
    }

    public void setScrollDuration(int scrollDuration) {
        mBannerScroller.setDuration(scrollDuration);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (disableTouchScroll) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    public void disableTouchScroll(boolean disableTouchScroll) {
        this.disableTouchScroll = disableTouchScroll;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        hookFirstLayout();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        firstLayout = false;
    }

    private void hookScroller() {
        try {
            mBannerScroller = new BannerScroller(getContext());
            mBannerScroller.setDuration(DEFAULT_SCROLL_DURATION);
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mField.set(this, mBannerScroller);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void hookFirstLayout() {
        try {
            Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(this, firstLayout);
            setCurrentItem(getCurrentItem());
        } catch (IllegalAccessException  e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void setFirstLayout(boolean firstLayout) {
        this.firstLayout = firstLayout;
    }
}
