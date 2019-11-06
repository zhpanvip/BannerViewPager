package com.zhpan.bannerview.indicator;

import androidx.viewpager.widget.ViewPager;


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

    void setSlideMode(int slideStyle);

    void setIndicatorGap(int gap);

    void setIndicatorWidth(int normalIndicatorWidth, int checkedIndicatorWidth);

    void notifyDataChanged();
}
