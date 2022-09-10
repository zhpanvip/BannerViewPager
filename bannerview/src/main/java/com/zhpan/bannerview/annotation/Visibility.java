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

package com.zhpan.bannerview.annotation;

import android.view.View;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *   Created by zhangpan on 2019-11-12.
 * </pre>
 */
@IntDef({ View.VISIBLE, View.INVISIBLE, View.GONE })
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface Visibility {

}
