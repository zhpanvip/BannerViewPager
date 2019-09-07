package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhpan.bannerview.enums.IndicatorSlideMode;
import com.zhpan.bannerview.utils.DpUtils;

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
    protected int mPageSize;
    /**
     * 未选中时Indicator颜色
     */
    protected int normalColor;
    /**
     * 选中时Indicator颜色
     */
    protected int checkedColor;
    /**
     * Indicator间距
     */
    protected float mIndicatorGap;
    /**
     * 从一个点滑动到另一个点的进度
     */
    protected float slideProgress;
    /**
     * 指示器当前位置
     */
    protected int currentPosition;
    /**
     * 指示器上一个位置
     */
    private int prePosition;
    /**
     * 是否是向右滑动，true向右，false向左
     */
    protected boolean slideToRight;
    /**
     * Indicator滑动模式，目前仅支持两种
     *
     * @see IndicatorSlideMode#NORMAL
     * @see IndicatorSlideMode#SMOOTH
     */
    protected IndicatorSlideMode mSlideMode = IndicatorSlideMode.SMOOTH;

    protected float normalIndicatorWidth;
    protected float checkedIndicatorWidth;

    public BaseIndicatorView(Context context) {
        super(context);
    }

    public BaseIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        normalIndicatorWidth = DpUtils.dp2px(8);
        checkedIndicatorWidth = normalIndicatorWidth;
        mIndicatorGap = normalIndicatorWidth;
        normalColor = Color.parseColor("#8C18171C");
        checkedColor = Color.parseColor("#8C6C6D72");
    }

    @Override
    public void onPageSelected(int position) {
        if (mSlideMode == IndicatorSlideMode.NORMAL) {
            currentPosition = position;
            slideProgress = 0;
            invalidate();
        } else if (mSlideMode == IndicatorSlideMode.SMOOTH) {
            if (position == 0 && slideToRight) {
                Log.e(tag, "slideToRight position-----》" + position);
                currentPosition = 0;
                slideProgress = 0;
                invalidate();

            } else if (position == mPageSize - 1 && !slideToRight) {
                currentPosition = mPageSize - 1;
                slideProgress = 0;
                invalidate();
            }
        }
    }

    private static final String tag = "BaseIndicatorView";

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mSlideMode == IndicatorSlideMode.SMOOTH) {
            if ((prePosition == 0 && position == mPageSize - 1)) {
                slideToRight = false;
            } else if (prePosition == mPageSize - 1 && position == 0) {
                Log.e(tag, "prePosition-----》" + prePosition);
                Log.e(tag, "position-----》" + position);
                slideToRight = true;
            } else {
                slideToRight = (position + positionOffset - prePosition) > 0;
            }
            //  TODO 解决滑动过快时positionOffset不会等0的情况
            if (positionOffset == 0) {
                prePosition = position;
            }
            if (!(position == mPageSize - 1 && slideToRight || (position == mPageSize - 1 && !slideToRight))) {
                slideProgress = (currentPosition == mPageSize - 1) && slideToRight ? 0 : positionOffset;
                currentPosition = position;
                invalidate();
            }
        }
    }

    @Override
    public void setPageSize(int pageSize) {
        this.mPageSize = pageSize;
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
     * @param gapRes Indicator间距
     */
    public void setIndicatorGap(int gapRes) {
        if (gapRes >= 0) {
            this.mIndicatorGap = gapRes;
        }
    }

    /**
     * @param slideMode Indicator滑动样式
     * @see IndicatorSlideMode#NORMAL
     * @see IndicatorSlideMode#SMOOTH
     */
    @Override
    public void setSlideMode(IndicatorSlideMode slideMode) {
        mSlideMode = slideMode;
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

    @Override
    public void onPageScrollStateChanged(int state) {

    }
//
//    @Nullable
//    @Override
//    protected Parcelable onSaveInstanceState() {
//        Parcelable superState = super.onSaveInstanceState();
//        SavedState savedState = new SavedState(superState);
//        savedState.currentPosition = this.currentPosition;
//        savedState.prePosition = this.prePosition;
//        savedState.slideProgress = this.slideProgress;
//        savedState.slideToRight = this.slideToRight;
//        return savedState;
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        SavedState savedState = (SavedState) state;
//        super.onRestoreInstanceState(state);
//        currentPosition = savedState.currentPosition;
//        prePosition = savedState.prePosition;
//        slideProgress = savedState.slideProgress;
//        slideToRight = savedState.slideToRight;
//    }
//
//    static class SavedState extends BaseSavedState {
//        int currentPosition;
//        int prePosition;
//        float slideProgress;
//        boolean slideToRight;
//
//        public SavedState(Parcelable source) {
//            super(source);
//        }
//
//        private SavedState(Parcel in) {
//            super(in);
//            currentPosition = in.readInt();
//            prePosition = in.readInt();
//            slideProgress = in.readFloat();
//            slideToRight = in.readByte() != 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            super.writeToParcel(dest, flags);
//            dest.writeInt(currentPosition);
//            dest.writeInt(prePosition);
//            dest.writeFloat(slideProgress);
//            dest.writeByte((byte) (slideToRight ? 1 : 0));
//        }
//
//        @SuppressWarnings("UnusedDeclaration")
//        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
//            @Override
//            public SavedState createFromParcel(Parcel in) {
//                return new SavedState(in);
//            }
//
//            @Override
//            public SavedState[] newArray(int size) {
//                return new SavedState[size];
//            }
//        };
//
//    }

}
