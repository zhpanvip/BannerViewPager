package com.zhpan.bannerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.zhpan.bannerview.enums.IndicatorSlideMode;
import com.zhpan.bannerview.Utils.DpUtils;

/**
 * Created by zhpan on 2017/12/6.
 */
public class IndicatorView extends View {
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

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        normalColor = Color.parseColor("#000000");
        checkedColor = Color.parseColor("#ffffff");
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

    private void drawSlideStyle(Canvas canvas) {
        switch (mSlideStyle) {
            case NORMAL:
                slideProgress=0;
            case SMOOTH:
                slideProgress = (currentPosition == mPageSize - 1) ? 0 : slideProgress;
                break;
        }
        mPaint.setColor(checkedColor);
        canvas.drawCircle(maxRadius + (2 * mNormalRadius + mMargin) * currentPosition + (2 * mNormalRadius + mMargin) * slideProgress,
                height / 2f, mCheckedRadius, mPaint);
    }

    public void onPageSelected(int position) {
        if (mSlideStyle == IndicatorSlideMode.NORMAL) {
            this.currentPosition = position;
            invalidate();
        }
    }

    public void onPageScrolled(int position, float positionOffset) {
        if (mSlideStyle == IndicatorSlideMode.SMOOTH) {
            slideProgress = positionOffset;
            currentPosition = position;
            invalidate();
        }
    }

    public IndicatorView setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public IndicatorView setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
        return this;
    }

    public IndicatorView setPageSize(int pageSize) {
        this.mPageSize = pageSize;
        requestLayout();
        return this;
    }

    public IndicatorView setIndicatorRadius(float radiusDp, float checkedRadius) {
        this.mNormalRadius = radiusDp;
        this.mCheckedRadius = checkedRadius;
        return this;
    }

    public IndicatorView setIndicatorMargin(float margin) {
        if (margin > 0) {
            this.mMargin = margin;
        }
        return this;
    }

    public IndicatorView setSlideStyle(IndicatorSlideMode slideStyle) {
        mSlideStyle = slideStyle;
        return this;
    }
}
