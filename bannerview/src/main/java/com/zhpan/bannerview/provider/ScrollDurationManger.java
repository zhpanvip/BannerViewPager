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

import com.zhpan.bannerview.utils.BannerUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * source from:https://github.com/youth5201314/banner/blob/master/banner/src/main/java/com/youth/banner/util/ScrollSpeedManger.java
 * thanks:https://github.com/zguop/banner/blob/master/pager2banner/src/main/java/com/to/aboomy/pager2banner/Banner.java
 */
public class ScrollDurationManger extends LinearLayoutManager {
    private LinearLayoutManager mParent;
    private int scrollDuration;

    public ScrollDurationManger(ViewPager2 viewPager2, int scrollDuration, LinearLayoutManager linearLayoutManager) {
        super(viewPager2.getContext(), linearLayoutManager.getOrientation(), false);
        this.scrollDuration = scrollDuration;
        mParent = linearLayoutManager;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            protected int calculateTimeForDeceleration(int dx) {
                return scrollDuration;
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override
    public boolean performAccessibilityAction(@NonNull RecyclerView.Recycler recycler,
                                              @NonNull RecyclerView.State state, int action, @Nullable Bundle args) {
        return mParent.performAccessibilityAction(recycler, state, action, args);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(@NonNull RecyclerView.Recycler recycler,
                                                  @NonNull RecyclerView.State state, @NonNull AccessibilityNodeInfoCompat info) {
        mParent.onInitializeAccessibilityNodeInfo(recycler, state, info);
    }

    @Override
    protected void calculateExtraLayoutSpace(@NonNull RecyclerView.State state,
                                             @NonNull int[] extraLayoutSpace) {
        try {
            Method method = mParent.getClass().getDeclaredMethod("calculateExtraLayoutSpace", state.getClass(), extraLayoutSpace.getClass());
            method.setAccessible(true);
            method.invoke(mParent, state, extraLayoutSpace);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            BannerUtils.log(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            BannerUtils.log(e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            BannerUtils.log(e.getMessage());
        }
    }

    @Override
    public boolean requestChildRectangleOnScreen(@NonNull RecyclerView parent,
                                                 @NonNull View child, @NonNull Rect rect, boolean immediate,
                                                 boolean focusedChildVisible) {
        return false; // users should use setCurrentItem instead
    }

    public static void reflectLayoutManager(ViewPager2 viewPager2, int scrollDuration) {
        try {
            RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
            recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (linearLayoutManager == null) {
                return;
            }
            ScrollDurationManger speedManger = new ScrollDurationManger(viewPager2, scrollDuration, linearLayoutManager);
            recyclerView.setLayoutManager(speedManger);

            Field mRecyclerView = RecyclerView.LayoutManager.class.getDeclaredField("mRecyclerView");
            mRecyclerView.setAccessible(true);
            mRecyclerView.set(linearLayoutManager, recyclerView);

            Field layoutMangerField = ViewPager2.class.getDeclaredField("mLayoutManager");
            layoutMangerField.setAccessible(true);
            layoutMangerField.set(viewPager2, speedManger);

            Field pageTransformerAdapterField = ViewPager2.class.getDeclaredField("mPageTransformerAdapter");
            pageTransformerAdapterField.setAccessible(true);
            Object mPageTransformerAdapter = pageTransformerAdapterField.get(viewPager2);
            if (mPageTransformerAdapter != null) {
                Class<?> aClass = mPageTransformerAdapter.getClass();
                Field layoutManager = aClass.getDeclaredField("mLayoutManager");
                layoutManager.setAccessible(true);
                layoutManager.set(mPageTransformerAdapter, speedManger);
            }
            Field scrollEventAdapterField = ViewPager2.class.getDeclaredField("mScrollEventAdapter");
            scrollEventAdapterField.setAccessible(true);
            Object mScrollEventAdapter = scrollEventAdapterField.get(viewPager2);
            if (mScrollEventAdapter != null) {
                Class<?> aClass = mScrollEventAdapter.getClass();
                Field layoutManager = aClass.getDeclaredField("mLayoutManager");
                layoutManager.setAccessible(true);
                layoutManager.set(mScrollEventAdapter, speedManger);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
