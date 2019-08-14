package com.zhpan.bannerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.zhpan.bannerview.Utils.DpUtils;

/**
 * Created by zhpan on 2017/12/6.
 */
public class IndicatorView extends View {

    private int normalColor;
    private int checkedColor;
    private Paint mPaint;
    private int mPageSize;
    private float mRadius;
    private int height;
    private int selectPage;

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
        mRadius = DpUtils.dp2px(context, 4);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) (3 * mRadius * (mPageSize + 1)), (int) (2 * mRadius));
        /*if (widthMeasureSpecMode == MeasureSpec.AT_MOST&&heightMeasureSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(100, 100);
        } else if (widthMeasureSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) (mPageSize * 2 * mRadius + (mPageSize - 1) * mRadius), heightMeasureSpecSize);
        } else if (heightMeasureSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthMeasureSpecSize, (int) (2 * mRadius));
        }*/
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mPageSize; i++) {
            mPaint.setColor(selectPage == i ? checkedColor : normalColor);
            canvas.drawCircle(3 * mRadius * (i + 1), height / 2f, mRadius, mPaint);
        }
    }

    public void pageSelect(int selectPage) {
        this.selectPage = selectPage;
        invalidate();
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

    public IndicatorView setIndicatorRadius(float radiusDp) {
        this.mRadius = radiusDp;
        return this;
    }
}
