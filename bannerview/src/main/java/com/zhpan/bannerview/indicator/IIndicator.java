package com.zhpan.bannerview.indicator;

import com.zhpan.bannerview.enums.IndicatorSlideMode;

/**
 * <pre>
 *   Created by zhangpan on 2019-09-02.
 *   Description:
 * </pre>
 */
public interface IIndicator {
    void onPageSelected(int position);

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageScrollStateChanged(int state);

    void setPageSize(int pageSize);

    void setNormalColor(int normalColor);

    void setCheckedColor(int checkedColor);

    void setIndicatorGap(float gap);

    void setSlideMode(IndicatorSlideMode slideStyle);

    void setIndicatorWidth(float normalIndicatorWidth,float checkedIndicatorWidth);
}
