package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;


/**
 * Created by zhpan on 2017/12/6.
 */
public class CircleIndicatorView extends BaseIndicatorView {
    private static final String tag = "IndicatorView";
    private Paint mPaint;
    private float mNormalRadius;
    private float mCheckedRadius;
    private float maxRadius;
    private int height;

    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(normalColor);
        mPaint.setAntiAlias(true);
        mNormalRadius = normalIndicatorWidth / 2;
        mCheckedRadius = checkedIndicatorWidth / 2;
        mIndicatorGap = mNormalRadius * 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mNormalRadius = normalIndicatorWidth / 2;
        mCheckedRadius = checkedIndicatorWidth / 2;
        maxRadius = Math.max(mCheckedRadius, mNormalRadius);
        setMeasuredDimension((int) ((mPageSize - 1) * mIndicatorGap + 2 * maxRadius * mPageSize),
                (int) (2 * maxRadius));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mPageSize; i++) {
            mPaint.setColor(normalColor);
            canvas.drawCircle(maxRadius + (2 * mNormalRadius + mIndicatorGap) * i, height / 2f, mNormalRadius, mPaint);
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
        canvas.drawCircle(maxRadius + (2 * mNormalRadius + mIndicatorGap) * currentPosition + (2 * mNormalRadius + mIndicatorGap) * slideProgress,
                height / 2f, mCheckedRadius, mPaint);
    }


    /**
     * @param normalRadius  未选中时Indicator半径
     * @param checkedRadius 选中时Indicator半径
     */
    public void setIndicatorRadius(int normalRadius, int checkedRadius) {
        this.mNormalRadius = normalRadius;
        this.mCheckedRadius = checkedRadius;
    }
}
