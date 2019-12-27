package com.example.zhpan.circleviewpager.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.zhpan.bannerview.indicator.BaseIndicatorView;

/**
 * @ author : zhouweibin
 * @ time: 2019/12/18 17:04.
 * @ desc: 选中与未选中的图片长宽可能不一样
 **/
public class DrawableIndicator extends BaseIndicatorView {
    // 选中与未选中的图片
    private Bitmap mCheckedIcon, mNormalIcon;
    // 图片之间的间距
    private int mIndicatorPadding;
    // 选中图片的宽高
    private int mFocusIconWidth, mFocusIconHeight;
    //未选中图片的宽高
    private int mNormalIconWidth, mNormalIconHeight;

    public DrawableIndicator(Context context) {
        this(context, null);
    }

    public DrawableIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxHeight = Math.max(mFocusIconHeight, mNormalIconHeight);
        int realWidth = mFocusIconWidth + (mNormalIconWidth + mIndicatorPadding) * (getPageSize() - 1);
        setMeasuredDimension(realWidth, maxHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPageSize() > 1 || mCheckedIcon == null || mNormalIcon == null) {
            for (int i = 1; i < getPageSize() + 1; i++) {
                int left = 0;
                int top = 0;
                Bitmap bitmap = mNormalIcon;
                int index = i - 1;
                if (index < getCurrentPosition()) {
                    left = (i - 1) * (mNormalIconWidth + mIndicatorPadding);
                    top = getMeasuredHeight() / 2 - mNormalIconHeight / 2;
                } else if (index == getCurrentPosition()) {
                    left = (i - 1) * (mNormalIconWidth + mIndicatorPadding);
                    top = getMeasuredHeight() / 2 - mFocusIconHeight / 2;
                    bitmap = mCheckedIcon;
                } else {
                    left = (i - 1) * mIndicatorPadding + (i - 2) * mNormalIconWidth + mFocusIconWidth;
                    top = getMeasuredHeight() / 2 - mNormalIconHeight / 2;
                }
                drawIcon(canvas, left, top, bitmap);
            }
        }
    }

    private void drawIcon(Canvas canvas, int left, int top, Bitmap icon) {
        if (icon == null) {
            return;
        }
        canvas.drawBitmap(icon, left, top, null);
    }

    private void initIconSize() {
        if (mCheckedIcon != null) {
            mFocusIconWidth = mCheckedIcon.getWidth();
            mFocusIconHeight = mCheckedIcon.getHeight();
        }

        if (mNormalIcon != null) {
            mNormalIconWidth = mNormalIcon.getWidth();
            mNormalIconHeight = mNormalIcon.getHeight();
        }
    }

    public DrawableIndicator setFocusIcon(Bitmap icon) {
        mCheckedIcon = icon;
        initIconSize();
        postInvalidate();
        return this;
    }

    public DrawableIndicator setNormalIcon(Bitmap icon) {
        mNormalIcon = icon;
        initIconSize();
        postInvalidate();
        return this;
    }

    public DrawableIndicator setIndicatorPadding(int padding) {
        if (padding >= 0) {
            mIndicatorPadding = padding;
            postInvalidate();
        }
        return this;
    }
}
