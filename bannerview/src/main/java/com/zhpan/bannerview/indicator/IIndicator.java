package com.zhpan.bannerview.indicator;

import androidx.viewpager.widget.ViewPager;

import com.zhpan.bannerview.enums.IndicatorSlideMode;

/**
 * <pre>
 *   Created by zhangpan on 2019-09-02.
 *   Description:
 * </pre>
 */
public interface IIndicator extends ViewPager.OnPageChangeListener {
    void setPageSize(int pageSize);

    void setNormalColor(int normalColor);

    void setCheckedColor(int checkedColor);

    void setSlideMode(IndicatorSlideMode slideStyle);

    void setIndicatorGap(int gap);

    void setIndicatorWidth(int normalIndicatorWidth, int checkedIndicatorWidth);

    void notifyDataChanged();
}
