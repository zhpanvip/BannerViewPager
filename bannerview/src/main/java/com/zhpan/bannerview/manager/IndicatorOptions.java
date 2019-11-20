package com.zhpan.bannerview.manager;

import android.graphics.Color;

import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhpan on 2019/11/20.
 *   Description:
 * </pre>
 */
public class IndicatorOptions {

    public IndicatorOptions() {
        normalIndicatorWidth = BannerUtils.dp2px(8);
        checkedIndicatorWidth = normalIndicatorWidth;
        indicatorGap = normalIndicatorWidth;
        normalColor = Color.parseColor("#8C18171C");
        checkedColor = Color.parseColor("#8C6C6D72");
        slideMode = IndicatorSlideMode.NORMAL;
    }

    /**
     * 页面size
     */
    private int pageSize;
    /**
     * 未选中时Indicator颜色
     */
    private int normalColor;
    /**
     * 选中时Indicator颜色
     */
    private int checkedColor;
    /**
     * Indicator间距
     */
    private float indicatorGap;
    /**
     * 从一个点滑动到另一个点的进度
     */
    private float slideProgress;
    /**
     * 指示器当前位置
     */
    private int currentPosition;
    /**
     * 指示器上一个位置
     */
    private int prePosition;
    /**
     * 是否是向右滑动，true向右，false向左
     */
    private boolean slideToRight;

    private float sliderHeight;

    /**
     * Indicator滑动模式，目前仅支持两种
     *
     * @see IndicatorSlideMode#NORMAL
     * @see IndicatorSlideMode#SMOOTH
     */
    private int slideMode;

    private float normalIndicatorWidth;

    private float checkedIndicatorWidth;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public int getCheckedColor() {
        return checkedColor;
    }

    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }

    public float getIndicatorGap() {
        return indicatorGap;
    }

    public void setIndicatorGap(float indicatorGap) {
        this.indicatorGap = indicatorGap;
    }

    public float getSlideProgress() {
        return slideProgress;
    }

    public void setSlideProgress(float slideProgress) {
        this.slideProgress = slideProgress;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getPrePosition() {
        return prePosition;
    }

    public void setPrePosition(int prePosition) {
        this.prePosition = prePosition;
    }

    public boolean isSlideToRight() {
        return slideToRight;
    }

    public void setSlideToRight(boolean slideToRight) {
        this.slideToRight = slideToRight;
    }

    public int getSlideMode() {
        return slideMode;
    }

    public void setSlideMode(int slideMode) {
        this.slideMode = slideMode;
    }

    public float getNormalIndicatorWidth() {
        return normalIndicatorWidth;
    }

    public void setNormalIndicatorWidth(float normalIndicatorWidth) {
        this.normalIndicatorWidth = normalIndicatorWidth;
    }

    public float getCheckedIndicatorWidth() {
        return checkedIndicatorWidth;
    }

    public void setCheckedIndicatorWidth(float checkedIndicatorWidth) {
        this.checkedIndicatorWidth = checkedIndicatorWidth;
    }

    public float getSliderHeight() {
        return sliderHeight;
    }

    public void setSliderHeight(float sliderHeight) {
        this.sliderHeight = sliderHeight;
    }
}
