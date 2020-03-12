package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.zhpan.bannerview.constants.IndicatorSlideMode;

/**
 * Created by zhpan on 2017/12/6.
 *
 * @deprecated Use {@link IndicatorView} instead.
 */
@Deprecated
public class DashIndicatorView extends BaseIndicatorView {
    private float sliderHeight;
    private float maxWidth;
    private float minWidth;

    public DashIndicatorView(Context context) {
        this(context, null);
    }

    public DashIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setColor(getNormalColor());
        sliderHeight = getNormalSliderWidth() / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        maxWidth = Math.max(getNormalSliderWidth(), getCheckedSliderWidth());
        minWidth = Math.min(getNormalSliderWidth(), getCheckedSliderWidth());
        setMeasuredDimension((int) ((getPageSize() - 1) * getIndicatorGap() + maxWidth + (getPageSize() - 1) * minWidth),
                (int) (getSliderHeight()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPageSize() > 1) {
            for (int i = 0; i < getPageSize(); i++) {
                if (getSlideMode() == IndicatorSlideMode.SMOOTH) {
                    smoothSlide(canvas, i);
                } else {
                    normalSlide(canvas, i);
                }
            }
        }
    }


    private void normalSlide(Canvas canvas, int i) {
        if (getNormalSliderWidth() == getCheckedSliderWidth()) {
            mPaint.setColor(getNormalColor());
            float left = i * (getNormalSliderWidth()) + i * +getIndicatorGap();
            canvas.drawRect(left, 0, left + getNormalSliderWidth(), getSliderHeight(), mPaint);
            drawSliderStyle(canvas);
        } else {  //  仿支付宝首页轮播图的Indicator
            if (i < getCurrentPosition()) {
                mPaint.setColor(getNormalColor());
                float left = i * minWidth + i * getIndicatorGap();
                canvas.drawRect(left, 0, left + minWidth, getSliderHeight(), mPaint);
            } else if (i == getCurrentPosition()) {
                mPaint.setColor(getCheckedColor());
                float left = i * minWidth + i * getIndicatorGap();
                canvas.drawRect(left, 0, left + minWidth + (maxWidth - minWidth), getSliderHeight(), mPaint);
            } else {
                mPaint.setColor(getNormalColor());
                float left = i * minWidth + i * getIndicatorGap() + (maxWidth - minWidth);
                canvas.drawRect(left, 0, left + minWidth, getSliderHeight(), mPaint);
            }
        }
    }

    private void smoothSlide(Canvas canvas, int i) {
        mPaint.setColor(getNormalColor());
        float left = i * (maxWidth) + i * +getIndicatorGap() + (maxWidth - minWidth);
        canvas.drawRect(left, 0, left + minWidth, getSliderHeight(), mPaint);
        drawSliderStyle(canvas);
    }

    private void drawSliderStyle(Canvas canvas) {
        mPaint.setColor(getCheckedColor());
        float left = getCurrentPosition() * (maxWidth) + getCurrentPosition() * +getIndicatorGap() + (maxWidth + getIndicatorGap()) * getSlideProgress();
        canvas.drawRect(left, 0, left + maxWidth, getSliderHeight(), mPaint);
    }

    public float getSliderHeight() {
        if (getIndicatorOptions().getSliderHeight() > 0) {
            return getIndicatorOptions().getSliderHeight();
        }
        return sliderHeight;
    }
}
