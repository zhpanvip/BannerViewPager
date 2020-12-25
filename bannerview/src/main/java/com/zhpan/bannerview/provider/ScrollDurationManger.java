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
 * @author zhangpan
 * @date 2020/12/21
 */
public class ScrollDurationManger extends LinearLayoutManager {
    private final LinearLayoutManager mParent;
    private final int scrollDuration;

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
}
