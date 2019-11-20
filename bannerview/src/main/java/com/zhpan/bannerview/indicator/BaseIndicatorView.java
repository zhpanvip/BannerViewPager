package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhpan.bannerview.annotation.AIndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhangpan on 2019-09-04.
 *   Description:IndicatorView基类，处理了页面滑动。
 * </pre>
 */
public class BaseIndicatorView extends View implements IIndicator {
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

    /**
     * Indicator滑动模式，目前仅支持两种
     *
     * @see IndicatorSlideMode#NORMAL
     * @see IndicatorSlideMode#SMOOTH
     */
    private int slideMode;

    private float normalIndicatorWidth;
    private float checkedIndicatorWidth;

    protected Paint mPaint;

    public BaseIndicatorView(Context context) {
        super(context);
    }

    public BaseIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        normalIndicatorWidth = BannerUtils.dp2px(8);
        checkedIndicatorWidth = normalIndicatorWidth;
        indicatorGap = normalIndicatorWidth;
        normalColor = Color.parseColor("#8C18171C");
        checkedColor = Color.parseColor("#8C6C6D72");
        slideMode = IndicatorSlideMode.NORMAL;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onPageSelected(int position) {
        if (slideMode == IndicatorSlideMode.NORMAL) {
            currentPosition = position;
            slideProgress = 0;
            invalidate();
        } else if (slideMode == IndicatorSlideMode.SMOOTH) {
            if (position == 0 && slideToRight) {
                currentPosition = 0;
                slideProgress = 0;
                invalidate();
            } else if (position == pageSize - 1 && !slideToRight) {
                currentPosition = pageSize - 1;
                slideProgress = 0;
                invalidate();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (slideMode == IndicatorSlideMode.SMOOTH) {
            slideToRight = isSlideToRight(position, positionOffset);
            //  TODO 解决滑动过快时positionOffset不会等0的情况
            if (positionOffset == 0) {
                prePosition = position;
            }
            if (!(position == pageSize - 1)) {
                slideProgress = (currentPosition == pageSize - 1) && slideToRight ? 0 : positionOffset;
                currentPosition = position;
                invalidate();
            }
        }
    }

    private boolean isSlideToRight(int position, float positionOffset) {
        if ((prePosition == 0 && position == pageSize - 1)) {
            return false;
        } else if (prePosition == pageSize - 1 && position == 0) {
            return true;
        } else {
            return (position + positionOffset - prePosition) > 0;
        }
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        requestLayout();
    }

    @Override
    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    @Override
    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }


    /**
     * @param indicatorGap Indicator间距
     */
    public void setIndicatorGap(float indicatorGap) {
        if (indicatorGap >= 0) {
            this.indicatorGap = indicatorGap;
        }
    }

    /**
     * @param slideMode Indicator滑动样式
     * @see com.zhpan.bannerview.constants.IndicatorSlideMode#NORMAL
     * @see com.zhpan.bannerview.constants.IndicatorSlideMode#SMOOTH
     */
    @Override
    public void setSlideMode(@AIndicatorSlideMode int slideMode) {
        this.slideMode = slideMode;
    }

    /**
     * Indicator Slider width or the diameter of circle.
     *
     * @param normalIndicatorWidth  未选中Slider width
     * @param checkedIndicatorWidth 选中Slider width
     */
    @Override
    public void setIndicatorWidth(int normalIndicatorWidth, int checkedIndicatorWidth) {
        this.normalIndicatorWidth = normalIndicatorWidth;
        this.checkedIndicatorWidth = checkedIndicatorWidth;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getNormalColor() {
        return normalColor;
    }

    public int getCheckedColor() {
        return checkedColor;
    }

    public float getIndicatorGap() {
        return indicatorGap;
    }

    public float getSlideProgress() {
        return slideProgress;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

//    public int getPrePosition() {
//        return prePosition;
//    }

    public boolean isSlideToRight() {
        return slideToRight;
    }

    public int getSlideMode() {
        return slideMode;
    }

    public float getNormalIndicatorWidth() {
        return normalIndicatorWidth;
    }

    public float getCheckedIndicatorWidth() {
        return checkedIndicatorWidth;
    }


    @Override
    public void notifyDataChanged() {
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
