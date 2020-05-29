package com.zhpan.bannerview.provider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.reflect.Field;


/**
 * source from:https://github.com/youth5201314/banner/blob/master/banner/src/main/java/com/youth/banner/util/ScrollSpeedManger.java
 * thanks:https://github.com/zguop/banner/blob/master/pager2banner/src/main/java/com/to/aboomy/pager2banner/Banner.java
 */
public class ScrollDurationManger extends LinearLayoutManager {
    private int scrollDuration;

    public ScrollDurationManger(ViewPager2 viewPager2, int scrollDuration, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        super(viewPager2.getContext(), linearLayoutManager.getOrientation(), false);
        this.scrollDuration = scrollDuration;
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

    public static void reflectLayoutManager(ViewPager2 viewPager2, int scrollDuration) {
        try {
            RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
            recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            ScrollDurationManger speedManger = new ScrollDurationManger(viewPager2, scrollDuration, recyclerView, linearLayoutManager);
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
