package com.zhpan.bannerview.indicator;

/**
 * <pre>
 *   Created by zhangpan on 2019-09-04.
 *   Description:
 * </pre>
 */
public interface IIndicator {

    void onPageSelected(int position);

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageScrollStateChanged(int state);
}
