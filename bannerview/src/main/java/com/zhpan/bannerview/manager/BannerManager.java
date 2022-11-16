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

package com.zhpan.bannerview.manager;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.zhpan.bannerview.transform.OverlapPageTransformer;
import com.zhpan.bannerview.transform.ScaleInTransformer;

/**
 * <pre>
 *   Created by zhpan on 2019/11/20.
 *   Description:
 * </pre>
 */
public class BannerManager {

  private BannerOptions mBannerOptions;

  private final AttributeController mAttributeController;

  private final CompositePageTransformer mCompositePageTransformer;

  private MarginPageTransformer mMarginPageTransformer;

  private ViewPager2.PageTransformer mDefaultPageTransformer;

  public BannerManager() {
    mBannerOptions = new BannerOptions();
    mAttributeController = new AttributeController(mBannerOptions);
    mCompositePageTransformer = new CompositePageTransformer();
  }

  public BannerOptions getBannerOptions() {
    if (mBannerOptions == null) {
      mBannerOptions = new BannerOptions();
    }
    return mBannerOptions;
  }

  public void initAttrs(Context context, AttributeSet attrs) {
    mAttributeController.init(context, attrs);
  }

  public CompositePageTransformer getCompositePageTransformer() {
    return mCompositePageTransformer;
  }

  public void addTransformer(@NonNull ViewPager2.PageTransformer transformer) {
    mCompositePageTransformer.addTransformer(transformer);
  }

  public void removeTransformer(@NonNull ViewPager2.PageTransformer transformer) {
    mCompositePageTransformer.removeTransformer(transformer);
  }

  public void removeMarginPageTransformer() {
    if (mMarginPageTransformer != null) {
      mCompositePageTransformer.removeTransformer(mMarginPageTransformer);
    }
  }

  public void removeDefaultPageTransformer() {
    if (mDefaultPageTransformer != null) {
      mCompositePageTransformer.removeTransformer(mDefaultPageTransformer);
    }
  }

  public void setPageMargin(int pageMargin) {
    mBannerOptions.setPageMargin(pageMargin);
  }

  public void createMarginTransformer() {
    removeMarginPageTransformer();
    mMarginPageTransformer = new MarginPageTransformer(mBannerOptions.getPageMargin());
    mCompositePageTransformer.addTransformer(mMarginPageTransformer);
  }

  public void setMultiPageStyle(boolean overlap, float scale) {
    removeDefaultPageTransformer();
    if (overlap && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      mDefaultPageTransformer = new OverlapPageTransformer(mBannerOptions
          .getOrientation(), scale, 0f, 1, 0);
    } else {
      mDefaultPageTransformer = new ScaleInTransformer(scale);
    }
    mCompositePageTransformer.addTransformer(mDefaultPageTransformer);
  }
}
