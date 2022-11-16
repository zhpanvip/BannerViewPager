/*
Copyright 2017 zhpanvip The BannerViewPager Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.zhpan.bannerview.provider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.reflect.Field;

/**
 * 通过反射修改页面滑动的时间
 * Thanks:https://github.com/zguop/banner/blob/master/pager2banner/src/main/java/com/to/aboomy/pager2banner/Banner.java
 */
public class ReflectLayoutManager {

  public static void reflectLayoutManager(ViewPager2 viewPager2, int scrollDuration) {
    try {
      RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
      recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
      LinearLayoutManager linearLayoutManager =
          (LinearLayoutManager) recyclerView.getLayoutManager();
      if (linearLayoutManager == null) {
        return;
      }
      ScrollDurationManger scrollDurationManger =
          new ScrollDurationManger(viewPager2, scrollDuration, linearLayoutManager);
      recyclerView.setLayoutManager(scrollDurationManger);

      Field mRecyclerField = RecyclerView.LayoutManager.class.getDeclaredField("mRecyclerView");
      mRecyclerField.setAccessible(true);
      mRecyclerField.set(linearLayoutManager, recyclerView);
      Field layoutMangerField = ViewPager2.class.getDeclaredField("mLayoutManager");
      layoutMangerField.setAccessible(true);
      layoutMangerField.set(viewPager2, scrollDurationManger);

      Field pageTransformerAdapterField =
          ViewPager2.class.getDeclaredField("mPageTransformerAdapter");
      pageTransformerAdapterField.setAccessible(true);
      Object mPageTransformerAdapter = pageTransformerAdapterField.get(viewPager2);
      if (mPageTransformerAdapter != null) {
        Class<?> aClass = mPageTransformerAdapter.getClass();
        Field layoutManager = aClass.getDeclaredField("mLayoutManager");
        layoutManager.setAccessible(true);
        layoutManager.set(mPageTransformerAdapter, scrollDurationManger);
      }
      Field scrollEventAdapterField = ViewPager2.class.getDeclaredField("mScrollEventAdapter");
      scrollEventAdapterField.setAccessible(true);
      Object mScrollEventAdapter = scrollEventAdapterField.get(viewPager2);
      if (mScrollEventAdapter != null) {
        Class<?> aClass = mScrollEventAdapter.getClass();
        Field layoutManager = aClass.getDeclaredField("mLayoutManager");
        layoutManager.setAccessible(true);
        layoutManager.set(mScrollEventAdapter, scrollDurationManger);
      }
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
