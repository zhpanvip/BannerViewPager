package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

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
        for (int i = 0; i < getPageSize(); i++) {
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
    }

//    private boolean isSlideToRight(int position, float positionOffset) {
//        int prePosition = mIndicatorOptions.getPrePosition();
//        if ((prePosition == 0 && position == getPageSize() - 1)) {
//            return false;
//        } else if (prePosition == getPageSize() - 1 && position == 0) {
//            return true;
//        } else {
//            return (position + positionOffset - prePosition) > 0;
//        }
//    }

    @Override
    public void setPageSize(int pageSize) {
        mIndicatorOptions.setPageSize(pageSize);
        requestLayout();
    }


    public int getPageSize() {
        return mIndicatorOptions.getPageSize();
    }

    public int getNormalColor() {
        return mIndicatorOptions.getNormalColor();
    }

    public int getCheckedColor() {
        return mIndicatorOptions.getCheckedColor();
    }

    public float getIndicatorGap() {
        return mIndicatorOptions.getIndicatorGap();
    }

    public float getSlideProgress() {
        return mIndicatorOptions.getSlideProgress();
    }

    public int getCurrentPosition() {
        return mIndicatorOptions.getCurrentPosition();
    }

    public void setCurrentPosition(int currentPosition) {
        mIndicatorOptions.setCurrentPosition(currentPosition);
    }

    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        mIndicatorOptions = indicatorOptions;
    }

//    public boolean isSlideToRight() {
//        return mIndicatorOptions.isSlideToRight();
//    }

    public int getSlideMode() {
        return mIndicatorOptions.getSlideMode();
    }

    public float getNormalIndicatorWidth() {
        return mIndicatorOptions.getNormalIndicatorWidth();
    }

    public float getCheckedIndicatorWidth() {
        return mIndicatorOptions.getCheckedIndicatorWidth();
    }

    private void setSlideProgress(float slideProgress) {
        mIndicatorOptions.setSlideProgress(slideProgress);
    }

//    private void setPrePosition(int prePosition) {
//        mIndicatorOptions.setPrePosition(prePosition);
//    }

//    private void setSlideToRight(boolean slideToRight) {
//        mIndicatorOptions.setSlideToRight(slideToRight);
//    }

    public IndicatorOptions getIndicatorOptions() {
        return mIndicatorOptions;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
