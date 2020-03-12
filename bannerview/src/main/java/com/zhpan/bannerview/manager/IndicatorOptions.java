package com.zhpan.bannerview.manager;

import android.graphics.Color;

import com.zhpan.bannerview.annotation.AIndicatorSlideMode;
import com.zhpan.bannerview.annotation.AIndicatorStyle;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhpan on 2019/11/20.
 *   Description:Indicator的配置参数
 * </pre>
 */
public class IndicatorOptions {

    public IndicatorOptions() {
        normalSliderWidth = BannerUtils.dp2px(8);
        checkedSliderWidth = normalSliderWidth;
        sliderGap = normalSliderWidth;
        normalColor = Color.parseColor("#8C18171C");
        checkedColor = Color.parseColor("#8C6C6D72");
        slideMode = IndicatorSlideMode.NORMAL;
    }

    private @AIndicatorStyle
    int mIndicatorStyle;

    /**
     * Indicator滑动模式，目前仅支持两种
     *
     * @see IndicatorSlideMode#NORMAL
     * @see IndicatorSlideMode#SMOOTH
     */
    private @AIndicatorSlideMode
    int slideMode;

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
    private float sliderGap;

    private float sliderHeight;

    private float normalSliderWidth;

    private float checkedSliderWidth;

    /**
     * 指示器当前位置
     */
    private int currentPosition;

    /**
     * 从一个点滑动到另一个点的进度
     */
    private float slideProgress;

    public int getPageSize() {
        return pageSize;
    }

    public int getNormalSliderColor() {
        return normalColor;
    }

    public int getCheckedSliderColor() {
        return checkedColor;
    }

    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }

    public float getSliderGap() {
        return sliderGap;
    }

    public void setSliderGap(float sliderGap) {
        this.sliderGap = sliderGap;
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

    public int getSlideMode() {
        return slideMode;
    }

    public void setSlideMode(int slideMode) {
        this.slideMode = slideMode;
    }

    public float getNormalSliderWidth() {
        return normalSliderWidth;
    }

    public float getCheckedSliderWidth() {
        return checkedSliderWidth;
    }


    public float getSliderHeight() {
        return sliderHeight > 0 ? sliderHeight : normalSliderWidth / 2;
    }

    public void setSliderHeight(float sliderHeight) {
        this.sliderHeight = sliderHeight;
    }

    public int getIndicatorStyle() {
        return mIndicatorStyle;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setIndicatorStyle(int indicatorStyle) {
        mIndicatorStyle = indicatorStyle;
    }

    public void setSliderWidth(float normalIndicatorWidth, float checkedIndicatorWidth) {
        this.normalSliderWidth = normalIndicatorWidth;
        this.checkedSliderWidth = checkedIndicatorWidth;
    }

    public void setSliderWidth(float sliderWidth) {
        setSliderWidth(sliderWidth, sliderWidth);
    }

    public void setSliderColor(int normalColor, int checkedColor) {
        this.normalColor = normalColor;
        this.checkedColor = checkedColor;
    }
}
