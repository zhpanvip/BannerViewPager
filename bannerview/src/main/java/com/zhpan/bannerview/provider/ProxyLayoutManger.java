package com.zhpan.bannerview.provider;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.reflect.Field;


/**
 * source form:https://github.com/youth5201314/banner/blob/master/banner/src/main/java/com/youth/banner/util/ProxyLayoutManger.java
 * thanks:https://github.com/zguop/banner/blob/master/pager2banner/src/main/java/com/to/aboomy/pager2banner/Banner.java
 */
public class ProxyLayoutManger extends LinearLayoutManager {

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ViewPager2 mViewPager2;
    private long scrollDuration;

    public static void setScrollProxy(ViewPager2 viewPager2, long scrollDuration) {
        RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
        recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null) {
            ProxyLayoutManger manger = new ProxyLayoutManger(viewPager2, scrollDuration, recyclerView, (LinearLayoutManager) layoutManager);
            recyclerView.setLayoutManager(manger);
        }
    }

    private ProxyLayoutManger(ViewPager2 viewPager2, long scrollDuration, RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        super(viewPager2.getContext(), layoutManager.getOrientation(), false);
        this.mViewPager2 = viewPager2;
        this.scrollDuration = scrollDuration;
        this.recyclerView = recyclerView;
        this.linearLayoutManager = layoutManager;
        reflectLayoutManager();
    }

    private void reflectLayoutManager() {
        try {
            //虽然设置了ProxyLayoutManger，但是还是调用的linearLayoutManager中实现的方法
            //将mRecyclerView重新赋值，可以避免使用mRecyclerView时为null
            Field mRecyclerView = RecyclerView.LayoutManager.class.getDeclaredField("mRecyclerView");
            mRecyclerView.setAccessible(true);
            mRecyclerView.set(linearLayoutManager, recyclerView);

            Field LayoutMangerField = ViewPager2.class.getDeclaredField("mLayoutManager");
            LayoutMangerField.setAccessible(true);
            LayoutMangerField.set(mViewPager2, this);

            Field pageTransformerAdapterField = ViewPager2.class.getDeclaredField("mPageTransformerAdapter");
            pageTransformerAdapterField.setAccessible(true);
            Object mPageTransformerAdapter = pageTransformerAdapterField.get(mViewPager2);
            if (mPageTransformerAdapter != null) {
                Class<?> aClass = mPageTransformerAdapter.getClass();
                Field layoutManager = aClass.getDeclaredField("mLayoutManager");
                layoutManager.setAccessible(true);
                layoutManager.set(mPageTransformerAdapter, this);
            }
            Field scrollEventAdapterField = ViewPager2.class.getDeclaredField("mScrollEventAdapter");
            scrollEventAdapterField.setAccessible(true);
            Object mScrollEventAdapter = scrollEventAdapterField.get(mViewPager2);
            if (mScrollEventAdapter != null) {
                Class<?> aClass = mScrollEventAdapter.getClass();
                Field layoutManager = aClass.getDeclaredField("mLayoutManager");
                layoutManager.setAccessible(true);
                layoutManager.set(mScrollEventAdapter, this);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean performAccessibilityAction(@NonNull RecyclerView.Recycler recycler,
                                              @NonNull RecyclerView.State state, int action, @Nullable Bundle args) {
        return linearLayoutManager.performAccessibilityAction(recycler, state, action, args);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(@NonNull RecyclerView.Recycler recycler,
                                                  @NonNull RecyclerView.State state, @NonNull AccessibilityNodeInfoCompat info) {
        linearLayoutManager.onInitializeAccessibilityNodeInfo(recycler, state, info);
    }

    @Override
    public boolean requestChildRectangleOnScreen(@NonNull RecyclerView parent,
                                                 @NonNull View child, @NonNull Rect rect, boolean immediate,
                                                 boolean focusedChildVisible) {
        return linearLayoutManager.requestChildRectangleOnScreen(parent, child, rect, immediate);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            protected int calculateTimeForDeceleration(int dx) {
                return (int) (scrollDuration * (1 - .3356));
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override
    protected void calculateExtraLayoutSpace(@NonNull RecyclerView.State state,
                                             @NonNull int[] extraLayoutSpace) {
        int pageLimit = mViewPager2.getOffscreenPageLimit();
        if (pageLimit == ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT) {
            super.calculateExtraLayoutSpace(state, extraLayoutSpace);
            return;
        }
        final int offscreenSpace = getPageSize() * pageLimit;
        extraLayoutSpace[0] = offscreenSpace;
        extraLayoutSpace[1] = offscreenSpace;
    }

    private int getPageSize() {
        final RecyclerView rv = (RecyclerView) mViewPager2.getChildAt(0);
        return getOrientation() == RecyclerView.HORIZONTAL
                ? rv.getWidth() - rv.getPaddingLeft() - rv.getPaddingRight()
                : rv.getHeight() - rv.getPaddingTop() - rv.getPaddingBottom();
    }
}
