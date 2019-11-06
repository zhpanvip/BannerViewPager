package com.zhpan.bannerview.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zhpan.bannerview.constants.IndicatorSlideMode.NORMAL;
import static com.zhpan.bannerview.constants.IndicatorSlideMode.SMOOTH;

/**
 * <pre>
 *   Created by zhangpan on 2019-10-18.
 *   Description:
 * </pre>
 */
@IntDef({NORMAL, SMOOTH})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface AIndicatorSlideMode {
}
