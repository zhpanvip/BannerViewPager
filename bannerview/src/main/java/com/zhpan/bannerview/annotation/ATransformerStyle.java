package com.zhpan.bannerview.annotation;


import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zhpan.bannerview.constants.TransformerStyle.ACCORDION;
import static com.zhpan.bannerview.constants.TransformerStyle.DEPTH;
import static com.zhpan.bannerview.constants.TransformerStyle.NONE;
import static com.zhpan.bannerview.constants.TransformerStyle.ROTATE;
import static com.zhpan.bannerview.constants.TransformerStyle.SCALE_IN;
import static com.zhpan.bannerview.constants.TransformerStyle.STACK;

/**
 * <pre>
 *   Created by zhangpan on 2019-10-18.
 *   Description:
 * </pre>
 */
@IntDef({NONE, DEPTH, STACK, ACCORDION, ROTATE,SCALE_IN})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface ATransformerStyle {
}
