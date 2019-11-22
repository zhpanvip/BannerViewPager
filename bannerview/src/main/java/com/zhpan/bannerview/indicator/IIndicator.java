package com.zhpan.bannerview.indicator;

import androidx.viewpager.widget.ViewPager;

import com.zhpan.bannerview.annotation.AIndicatorSlideMode;
import com.zhpan.bannerview.manager.IndicatorOptions;


/**
 * <pre>
 *   Created by zhangpan on 2019-09-02.
 *   Description:
 * </pre>
 */
public interface IIndicator extends ViewPager.OnPageChangeListener {

    void setPageSize(int pageSize);

    void setIndicatorOptions(IndicatorOptions options);
}
