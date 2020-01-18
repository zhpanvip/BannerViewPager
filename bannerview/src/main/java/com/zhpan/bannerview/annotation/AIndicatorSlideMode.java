package com.zhpan.bannerview.annotation;


import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zhpan.bannerview.constants.IndicatorSlideMode.NORMAL;
import static com.zhpan.bannerview.constants.IndicatorSlideMode.SMOOTH;
import static com.zhpan.bannerview.constants.IndicatorSlideMode.WORM;

/**
 * <pre>
 *   Created by zhangpan on 2019-10-18.
 *   Description:
 * </pre>
 */
@IntDef({NORMAL, SMOOTH,WORM})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER,ElementType.FIELD})
public @interface AIndicatorSlideMode {
}
