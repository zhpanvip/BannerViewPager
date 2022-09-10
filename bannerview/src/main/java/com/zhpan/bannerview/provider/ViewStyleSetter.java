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

import android.os.Build;
import androidx.annotation.RequiresApi;
import android.view.View;

/**
 * <pre>
 *   Created by zhangpan on 2018/12/26.
 *   Description:为View设置圆角，支持View及ViewGroup，适用Android 5.1及以上系统。
 * </pre>
 */
public class ViewStyleSetter {

  /**
   * 为View设置圆角效果
   *
   * @param radius 圆角半径
   */
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public static void applyRoundCorner(View target, float radius) {
    if (target == null) {
      return;
    }
    target.setClipToOutline(true);// 用outline裁剪内容区域
    target.setOutlineProvider(new RoundViewOutlineProvider(radius));
  }
}
