package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.zhpan.bannerview.utils.DpUtils;

/**
 * Created by zhpan on 2017/12/6.
 */
public class DashIndicatorView extends BaseIndicatorView implements IIndicator {
    private Paint mPaint;
    private float sliderHeight;

    public DashIndicatorView(Context context) {
        this(context, null);
    }

    public DashIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(normalColor);
        mPaint.setAntiAlias(true);
        sliderHeight = normalIndicatorWidth / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) ((mPageSize - 1) *mIndicatorGap  + normalIndicatorWidth * mPageSize),
                (int) (sliderHeight));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(normalColor);
        for (int i = 0; i < mPageSize; i++) {
            mPaint.setColor(normalColor);
            canvas.drawRect(i * (normalIndicatorWidth) + i * +mIndicatorGap, 0, i * (normalIndicatorWidth) + i * +mIndicatorGap + normalIndicatorWidth, sliderHeight, mPaint);
        }
        drawSliderStyle(canvas);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
    }

    private void drawSliderStyle(Canvas canvas) {
        mPaint.setColor(checkedColor);
        float left = currentPosition * (checkedIndicatorWidth) + currentPosition * +mIndicatorGap + (checkedIndicatorWidth + mIndicatorGap) * slideProgress;
        canvas.drawRect(left, 0, left + checkedIndicatorWidth, sliderHeight, mPaint);
    }

    public DashIndicatorView setSliderHeight(float sliderHeight) {
        this.sliderHeight = DpUtils.dp2px(sliderHeight);
        return this;
    }
}
