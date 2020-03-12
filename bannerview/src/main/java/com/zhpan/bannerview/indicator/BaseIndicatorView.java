package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;


import com.zhpan.bannerview.annotation.AIndicatorSlideMode;
import com.zhpan.bannerview.annotation.AIndicatorStyle;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.manager.IndicatorOptions;

/**
 * <pre>
 *   Created by zhangpan on 2019-09-04.
 *   Description:IndicatorView基类，处理了页面滑动。
 * </pre>
 */
public class BaseIndicatorView extends View implements IIndicator {

    private IndicatorOptions mIndicatorOptions;

    protected Paint mPaint;

    private ViewPager mViewPager;

    public BaseIndicatorView(Context context) {
        super(context);
    }

    public BaseIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorOptions = new IndicatorOptions();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onPageSelected(int position) {
        if (getSlideMode() == IndicatorSlideMode.NORMAL) {
            setCurrentPosition(position);
            setSlideProgress(0);
            invalidate();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (getSlideMode() != IndicatorSlideMode.NORMAL && getPageSize() > 1) {
            scrollSlider(position, positionOffset);
            invalidate();
        }
    }

    private void scrollSlider(int position, float positionOffset) {
        if (position % getPageSize() == getPageSize() - 1) { //   最后一个页面与第一个页面
            if (positionOffset < 0.5) {
                setCurrentPosition(position);
                setSlideProgress(0);
            } else {
                setCurrentPosition(0);
                setSlideProgress(0);
            }
        } else {    //  中间页面
            setCurrentPosition(position);
            setSlideProgress(positionOffset);
        }
    }


    @Override
    public void notifyDataChanged() {
        setupViewPager();
        requestLayout();
        invalidate();
    }

    private void setupViewPager() {
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(this);
            mViewPager.addOnPageChangeListener(this);
            if (mViewPager.getAdapter() != null)
                setPageSize(mViewPager.getAdapter().getCount());
        }
    }

    private void setPageSize(int pageSize) {
        mIndicatorOptions.setPageSize(pageSize);
    }

    public BaseIndicatorView setSliderColor(@ColorInt int normalColor, @ColorInt int selectedColor) {
        mIndicatorOptions.setSliderColor(normalColor, selectedColor);
        return this;
    }

    public BaseIndicatorView setSliderWidth(float sliderWidth) {
        mIndicatorOptions.setSliderWidth(sliderWidth);
        return this;
    }

    public BaseIndicatorView setSliderWidth(float normalSliderWidth, float selectedSliderWidth) {
        mIndicatorOptions.setSliderWidth(normalSliderWidth, selectedSliderWidth);
        return this;
    }

    public BaseIndicatorView setSliderGap(float sliderGap) {
        mIndicatorOptions.setSliderGap(sliderGap);
        return this;
    }

    public BaseIndicatorView setSlideMode(@AIndicatorSlideMode int slideMode) {
        mIndicatorOptions.setSlideMode(slideMode);
        return this;
    }

    public BaseIndicatorView setIndicatorStyle(@AIndicatorStyle int indicatorStyle) {
        mIndicatorOptions.setIndicatorStyle(indicatorStyle);
        return this;
    }

    public BaseIndicatorView setSliderHeight(float sliderHeight) {
        mIndicatorOptions.setSliderHeight(sliderHeight);
        return this;
    }

    public int getPageSize() {
        return mIndicatorOptions.getPageSize();
    }

    public int getNormalColor() {
        return mIndicatorOptions.getNormalSliderColor();
    }

    public int getCheckedColor() {
        return mIndicatorOptions.getCheckedSliderColor();
    }

    public float getIndicatorGap() {
        return mIndicatorOptions.getSliderGap();
    }

    public float getSlideProgress() {
        return mIndicatorOptions.getSlideProgress();
    }

    public int getCurrentPosition() {
        return mIndicatorOptions.getCurrentPosition();
    }

    public int getSlideMode() {
        return mIndicatorOptions.getSlideMode();
    }

    public float getNormalSliderWidth() {
        return mIndicatorOptions.getNormalSliderWidth();
    }

    public float getCheckedSliderWidth() {
        return mIndicatorOptions.getCheckedSliderWidth();
    }

    private void setSlideProgress(float slideProgress) {
        mIndicatorOptions.setSlideProgress(slideProgress);
    }

    private void setCurrentPosition(int currentPosition) {
        mIndicatorOptions.setCurrentPosition(currentPosition);
    }

    public void setupWithViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        notifyDataChanged();
    }

    public IndicatorOptions getIndicatorOptions() {
        return mIndicatorOptions;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        mIndicatorOptions = indicatorOptions;
    }
}
