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

package com.zhpan.bannerview.constants;

/**
 * <pre>
 *   Created by zhangpan on 2019-10-18.
 *   Description:
 * </pre>
 */
public interface PageStyle {

  int NORMAL = 0;
  /**
   * @deprecated please use {@link com.zhpan.bannerview.BannerViewPager#setRevealWidth(int)} instead.
   */
  @Deprecated
  int MULTI_PAGE = 1 << 1;
  /**
   * Requires Api Version >= 21
   */
  int MULTI_PAGE_OVERLAP = 1 << 2;

  int MULTI_PAGE_SCALE = 1 << 3;
}
