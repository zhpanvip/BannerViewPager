package com.zhpan.bannerview.indicator;



import android.support.v4.view.ViewPager;

import com.zhpan.bannerview.manager.IndicatorOptions;


/**
 * <pre>
 *   Created by zhangpan on 2019-09-02.
 *   Description:
 * </pre>
 */
public interface IIndicator extends ViewPager.OnPageChangeListener {

    void notifyDataChanged();

    void setIndicatorOptions(IndicatorOptions options);
}
