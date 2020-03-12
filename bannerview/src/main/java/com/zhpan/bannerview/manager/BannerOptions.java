package com.zhpan.bannerview.manager;

import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhpan on 2019/11/20.
 *   Description:BannerViewPager的配置参数
 * </pre>
 */
public class BannerOptions {

    public BannerOptions() {
        mIndicatorOptions = new IndicatorOptions();
        mPageMargin = BannerUtils.dp2px(20);
        mRevealWidth = BannerUtils.dp2px(20);
    }

    public static final int DEFAULT_SCROLL_DURATION = 500;

    private int offScreenPageLimit;

    private int interval;

    private int currentPosition;

    private boolean isLooping;

    private boolean isCanLoop;

    private boolean isAutoPlay = false;

    private int indicatorGravity;

    private int mPageMargin;

    private int mRevealWidth;

    private int mPageStyle = PageStyle.NORMAL;

    private IndicatorMargin mIndicatorMargin;

    private int mIndicatorVisibility;

    private int mScrollDuration;

    private int mRoundRadius;

    private boolean disableTouchScroll;

    private IndicatorOptions mIndicatorOptions;

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    public boolean isCanLoop() {
        return isCanLoop;
    }

    public void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
    }

    public int getIndicatorGravity() {
        return indicatorGravity;
    }

    public void setIndicatorGravity(int indicatorGravity) {
        this.indicatorGravity = indicatorGravity;
    }

    public int getIndicatorNormalColor() {
        return mIndicatorOptions.getNormalSliderColor();
    }

    public int getIndicatorCheckedColor() {
        return mIndicatorOptions.getCheckedSliderColor();
    }

    public int getNormalIndicatorWidth() {
        return (int) mIndicatorOptions.getNormalSliderWidth();
    }



    public void setIndicatorSliderColor(int normalColor,int checkedColor){
        mIndicatorOptions.setSliderColor(normalColor,checkedColor);
    }

    public void setIndicatorSliderWidth(int normalWidth,int checkedWidth){
        mIndicatorOptions.setSliderWidth(normalWidth,checkedWidth);
    }
    public int getCheckedIndicatorWidth() {
        return (int) mIndicatorOptions.getCheckedSliderWidth();
    }


    public IndicatorOptions getIndicatorOptions() {
        return mIndicatorOptions;
    }

    public int getPageMargin() {
        return mPageMargin;
    }

    public void setPageMargin(int pageMargin) {
        mPageMargin = pageMargin;
    }

    public int getRevealWidth() {
        return mRevealWidth;
    }

    public void setRevealWidth(int revealWidth) {
        mRevealWidth = revealWidth;
    }

    public int getIndicatorStyle() {
        return mIndicatorOptions.getIndicatorStyle();
    }

    public void setIndicatorStyle(int indicatorStyle) {
        mIndicatorOptions.setIndicatorStyle(indicatorStyle);
    }

    public int getIndicatorSlideMode() {
        return mIndicatorOptions.getSlideMode();
    }

    public void setIndicatorSlideMode(int indicatorSlideMode) {
        mIndicatorOptions.setSlideMode(indicatorSlideMode);
    }

    public float getIndicatorGap() {
        return mIndicatorOptions.getSliderGap();
    }

    public void setIndicatorGap(float indicatorGap) {
        mIndicatorOptions.setSliderGap(indicatorGap);
    }

    public float getIndicatorHeight() {
        return mIndicatorOptions.getSliderHeight();
    }

    public void setIndicatorHeight(int indicatorHeight) {
        mIndicatorOptions.setSliderHeight(indicatorHeight);
    }

    public int getPageStyle() {
        return mPageStyle;
    }

    public void setPageStyle(int pageStyle) {
        mPageStyle = pageStyle;
    }

    public IndicatorMargin getIndicatorMargin() {
        return mIndicatorMargin;
    }

    public void setIndicatorMargin(int left, int top, int right, int bottom) {
        mIndicatorMargin = new IndicatorMargin(left, top, right, bottom);
    }

    public int getRoundRectRadius() {
        return mRoundRadius;
    }

    public void setRoundRectRadius(int roundRadius) {
        this.mRoundRadius = roundRadius;
    }

    public int getScrollDuration() {
        return mScrollDuration;
    }

    public void setScrollDuration(int scrollDuration) {
        this.mScrollDuration = scrollDuration;
    }

    public int getIndicatorVisibility() {
        return mIndicatorVisibility;
    }

    public void setIndicatorVisibility(int indicatorVisibility) {
        mIndicatorVisibility = indicatorVisibility;
    }

    public boolean isDisableTouchScroll() {
        return disableTouchScroll;
    }

    public void setDisableTouchScroll(boolean disableTouchScroll) {
        this.disableTouchScroll = disableTouchScroll;
    }

    public void resetIndicatorOptions(){
        mIndicatorOptions.setCurrentPosition(0);
        mIndicatorOptions.setSlideProgress(0);
    }

    public int getOffScreenPageLimit() {
        return offScreenPageLimit;
    }

    public void setOffScreenPageLimit(int offScreenPageLimit) {
        this.offScreenPageLimit = offScreenPageLimit;
    }

    public static class IndicatorMargin {

        private int left, right, top, bottom;

        public IndicatorMargin(int left, int top, int right, int bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }

        public int getTop() {
            return top;
        }

        public int getBottom() {
            return bottom;
        }
    }
}
