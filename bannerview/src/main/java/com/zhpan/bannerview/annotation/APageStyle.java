package com.zhpan.bannerview.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zhpan.bannerview.constants.PageStyle.MULTI_PAGE;
import static com.zhpan.bannerview.constants.PageStyle.MULTI_PAGE_OVERLAP;
import static com.zhpan.bannerview.constants.PageStyle.MULTI_PAGE_SCALE;
import static com.zhpan.bannerview.constants.PageStyle.NORMAL;

/**
 * <pre>
 *   Created by zhangpan on 2019-11-06.
 *   Description:
 * </pre>
 */
@IntDef({NORMAL, MULTI_PAGE, MULTI_PAGE_OVERLAP,MULTI_PAGE_SCALE})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface APageStyle {
}
