package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by zhpan on 2017/12/6.
 *
 * @deprecated Use {@link IndicatorView} instead.
 */
@Deprecated
public class CircleIndicatorView extends BaseIndicatorView {

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
        mPaint.setColor(getNormalColor());
        mNormalRadius = getNormalIndicatorWidth() / 2;
        mCheckedRadius = getCheckedIndicatorWidth() / 2;
        getIndicatorOptions().setIndicatorGap(mNormalRadius * 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mNormalRadius = getNormalIndicatorWidth() / 2;
        mCheckedRadius = getCheckedIndicatorWidth() / 2;
        maxRadius = Math.max(mCheckedRadius, mNormalRadius);
        setMeasuredDimension((int) ((getPageSize() - 1) * getIndicatorGap() + 2 * (maxRadius + mNormalRadius * (getPageSize() - 1))),
                (int) (2 * maxRadius));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPageSize() > 1) {
            for (int i = 0; i < getPageSize(); i++) {
                mPaint.setColor(getNormalColor());
                canvas.drawCircle(maxRadius + (2 * mNormalRadius + getIndicatorGap()) * i, height / 2f, mNormalRadius, mPaint);
            }
            drawSliderStyle(canvas);
        }
    }

    private void drawSliderStyle(Canvas canvas) {
        mPaint.setColor(getCheckedColor());
        canvas.drawCircle(maxRadius + (2 * mNormalRadius + getIndicatorGap()) * getCurrentPosition() + (2 * mNormalRadius + getIndicatorGap()) * getSlideProgress(),
                height / 2f, mCheckedRadius, mPaint);
    }
}
