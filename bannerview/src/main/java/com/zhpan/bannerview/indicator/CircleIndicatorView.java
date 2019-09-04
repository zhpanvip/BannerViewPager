package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zhpan.bannerview.Utils.DpUtils;
import com.zhpan.bannerview.enums.IndicatorSlideMode;

/**
 * Created by zhpan on 2017/12/6.
 */
public class CircleIndicatorView extends View implements IIndicator {
    private static final String tag = "IndicatorView";
    private int normalColor;
    private int checkedColor;
    private Paint mPaint;
    private int mPageSize;
    private float mNormalRadius;
    private float mCheckedRadius;
    private float maxRadius;
    private int height;
    private int currentPosition;
    private float mMargin;
    private IndicatorSlideMode mSlideStyle = IndicatorSlideMode.SMOOTH;
    private float slideProgress;

    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        normalColor = Color.parseColor("#8C18171C");
        checkedColor = Color.parseColor("#8C6C6D72");
        mPaint = new Paint();
        mPaint.setColor(normalColor);
        mPaint.setAntiAlias(true);
        mNormalRadius = DpUtils.dp2px(context, 4);
        mCheckedRadius = mNormalRadius;
        mMargin = mNormalRadius * 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        maxRadius = Math.max(mCheckedRadius, mNormalRadius);
        setMeasuredDimension((int) ((mPageSize - 1) * mMargin + 2 * maxRadius * mPageSize),
                (int) (2 * maxRadius));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mPageSize; i++) {
            mPaint.setColor(normalColor);
            canvas.drawCircle(maxRadius + (2 * mNormalRadius + mMargin) * i, height / 2f, mNormalRadius, mPaint);
        }
        drawSlideStyle(canvas);
    }

    @Override
    public void onPageSelected(int position) {
        if (mSlideStyle == IndicatorSlideMode.NORMAL) {
            currentPosition = position;
            slideProgress = 0;
            invalidate();
        } else if (mSlideStyle == IndicatorSlideMode.SMOOTH) {
            if (position == 0 && slideToRight) {
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

    private boolean slideToRight;
    private int prePosition;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mSlideStyle == IndicatorSlideMode.SMOOTH) {
            if ((prePosition == 0 && position == mPageSize - 1)) {
                slideToRight = false;
            } else if (prePosition == mPageSize - 1 && position == 0) {
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
    public void onPageScrollStateChanged(int state) {

    }

    private void drawSlideStyle(Canvas canvas) {
        mPaint.setColor(checkedColor);
        canvas.drawCircle(maxRadius + (2 * mNormalRadius + mMargin) * currentPosition + (2 * mNormalRadius + mMargin) * slideProgress,
                height / 2f, mCheckedRadius, mPaint);
    }


    public CircleIndicatorView setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public CircleIndicatorView setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
        return this;
    }

    public CircleIndicatorView setPageSize(int pageSize) {
        this.mPageSize = pageSize;
        requestLayout();
        return this;
    }

    public CircleIndicatorView setIndicatorRadius(float radiusDp, float checkedRadius) {
        this.mNormalRadius = radiusDp;
        this.mCheckedRadius = checkedRadius;
        return this;
    }

    public CircleIndicatorView setIndicatorMargin(float margin) {
        if (margin > 0) {
            this.mMargin = margin;
        }
        return this;
    }

    public CircleIndicatorView setSlideStyle(IndicatorSlideMode slideStyle) {
        mSlideStyle = slideStyle;
        return this;
    }
}
