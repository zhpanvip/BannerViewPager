package com.zhpan.bannerview.manager;

import com.zhpan.bannerview.constants.PageStyle;


public class BannerOptions {
    public static final int DEFAULT_SCROLL_DURATION = 500;

    private int interval;

    private int currentPosition;

    private boolean isLooping;

    private boolean isCanLoop;

    private boolean isAutoPlay = false;

    private int indicatorGravity;

    private int indicatorNormalColor;

    private int indicatorCheckedColor;

    private int normalIndicatorWidth;

    private int checkedIndicatorWidth;

    private int mPageMargin;

    private int mRevealWidth;

    private int mIndicatorStyle;

    private int mIndicatorSlideMode;

    private int indicatorGap;

    private int indicatorHeight;

    private boolean isCustomIndicator;

    private int mPageStyle = PageStyle.NORMAL;

    private IndicatorMargin mIndicatorMargin;

    private int mIndicatorVisibility;

    private int mScrollDuration;

    private int mRoundCorner;

    private boolean disableTouchScroll;

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
        return indicatorNormalColor;
    }

    public void setIndicatorNormalColor(int indicatorNormalColor) {
        this.indicatorNormalColor = indicatorNormalColor;
    }

    public int getIndicatorCheckedColor() {
        return indicatorCheckedColor;
    }

    public void setIndicatorCheckedColor(int indicatorCheckedColor) {
        this.indicatorCheckedColor = indicatorCheckedColor;
    }

    public int getNormalIndicatorWidth() {
        return normalIndicatorWidth;
    }

    public void setNormalIndicatorWidth(int normalIndicatorWidth) {
        this.normalIndicatorWidth = normalIndicatorWidth;
    }

    public int getCheckedIndicatorWidth() {
        return checkedIndicatorWidth;
    }

    public void setCheckedIndicatorWidth(int checkedIndicatorWidth) {
        this.checkedIndicatorWidth = checkedIndicatorWidth;
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
        return mIndicatorStyle;
    }

    public void setIndicatorStyle(int indicatorStyle) {
        mIndicatorStyle = indicatorStyle;
    }

    public int getIndicatorSlideMode() {
        return mIndicatorSlideMode;
    }

    public void setIndicatorSlideMode(int indicatorSlideMode) {
        mIndicatorSlideMode = indicatorSlideMode;
    }

    public int getIndicatorGap() {
        return indicatorGap;
    }

    public void setIndicatorGap(int indicatorGap) {
        this.indicatorGap = indicatorGap;
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
    }

    public boolean isCustomIndicator() {
        return isCustomIndicator;
    }

    public void setCustomIndicator(boolean customIndicator) {
        isCustomIndicator = customIndicator;
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

    public int getRoundCorner() {
        return mRoundCorner;
    }

    public void setRoundCorner(int roundCorner) {
        this.mRoundCorner = roundCorner;
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
