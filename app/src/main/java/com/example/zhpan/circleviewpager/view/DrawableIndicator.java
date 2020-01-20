package com.example.zhpan.circleviewpager.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.core.graphics.drawable.DrawableCompat;

import com.zhpan.indicatorview.base.BaseIndicatorView;


/**
 * @ author : zhouweibin
 * @ time: 2019/12/18 17:04.
 * @ desc: 选中与未选中的图片长宽可能不一样
 **/
public class DrawableIndicator extends BaseIndicatorView {
    // 选中与未选中的图片
    private Bitmap mCheckedBitmap, mNormalBitmap;
    // 图片之间的间距
    private int mIndicatorPadding;
    // 选中图片的宽高
    private int mCheckedBitmapWidth, mCheckedBitmapHeight;
    //未选中图片的宽高
    private int mNormalBitmapWidth, mNormalBitmapHeight;
    private IndicatorSize mIndicatorSize;
    private boolean normalCanResize = true;
    private boolean checkCanResize=true;

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
        int maxHeight = Math.max(mCheckedBitmapHeight, mNormalBitmapHeight);
        int realWidth = mCheckedBitmapWidth + (mNormalBitmapWidth + mIndicatorPadding) * (getPageSize() - 1);
        setMeasuredDimension(realWidth, maxHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPageSize() > 1 && mCheckedBitmap != null && mNormalBitmap != null) {
            for (int i = 1; i < getPageSize() + 1; i++) {
                int left;
                int top;
                Bitmap bitmap = mNormalBitmap;
                int index = i - 1;
                if (index < getCurrentPosition()) {
                    left = (i - 1) * (mNormalBitmapWidth + mIndicatorPadding);
                    top = getMeasuredHeight() / 2 - mNormalBitmapHeight / 2;
                } else if (index == getCurrentPosition()) {
                    left = (i - 1) * (mNormalBitmapWidth + mIndicatorPadding);
                    top = getMeasuredHeight() / 2 - mCheckedBitmapHeight / 2;
                    bitmap = mCheckedBitmap;
                } else {
                    left = (i - 1) * mIndicatorPadding + (i - 2) * mNormalBitmapWidth + mCheckedBitmapWidth;
                    top = getMeasuredHeight() / 2 - mNormalBitmapHeight / 2;
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
        if (mCheckedBitmap != null) {
            if (mIndicatorSize != null) {
                if (mCheckedBitmap.isMutable()&& checkCanResize) {
                    mCheckedBitmap.setWidth(mIndicatorSize.checkedWidth);
                    mCheckedBitmap.setHeight(mIndicatorSize.checkedHeight);
                } else {
                    int width = mCheckedBitmap.getWidth();
                    int height = mCheckedBitmap.getHeight();
                    float scaleWidth = ((float) (mIndicatorSize.checkedWidth) / width);
                    float scaleHeight = ((float) (mIndicatorSize.checkedHeight) / height);
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    mCheckedBitmap = Bitmap.createBitmap(mCheckedBitmap, 0, 0, width, height, matrix, true);
                }
            }
            mCheckedBitmapWidth = mCheckedBitmap.getWidth();
            mCheckedBitmapHeight = mCheckedBitmap.getHeight();
        }

        if (mNormalBitmap != null) {
            if (mIndicatorSize != null) {
                if (mNormalBitmap.isMutable()&& normalCanResize) {
                    mNormalBitmap.setWidth(mIndicatorSize.normalWidth);
                    mNormalBitmap.setHeight(mIndicatorSize.normalHeight);
                } else {
                    int width = mNormalBitmap.getWidth();
                    int height = mNormalBitmap.getHeight();
                    float scaleWidth = ((float) (mIndicatorSize.normalWidth) / mNormalBitmap.getWidth());
                    float scaleHeight = ((float) (mIndicatorSize.normalHeight) / mNormalBitmap.getHeight());
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    mNormalBitmap = Bitmap.createBitmap(mNormalBitmap, 0, 0, width, height, matrix, true);
                }
            }
            mNormalBitmapWidth = mNormalBitmap.getWidth();
            mNormalBitmapHeight = mNormalBitmap.getHeight();
        }
    }

    public DrawableIndicator setIndicatorDrawable(@DrawableRes int normalDrawable, @DrawableRes int checkedDrawable) {
        mNormalBitmap = mCheckedBitmap = BitmapFactory.decodeResource(getResources(), normalDrawable);
        mCheckedBitmap = BitmapFactory.decodeResource(getResources(), checkedDrawable);
        if (mNormalBitmap == null) {
            mNormalBitmap = getBitmapFromVectorDrawable(getContext(), normalDrawable);
            normalCanResize =false;
        }
        if (mCheckedBitmap == null) {
            mCheckedBitmap = getBitmapFromVectorDrawable(getContext(), checkedDrawable);
            checkCanResize =false;
        }
        initIconSize();
        postInvalidate();
        return this;
    }

    public DrawableIndicator setIndicatorSize(int normalWidth, int normalHeight, int checkedWidth, int checkedHeight) {
        this.mIndicatorSize = new IndicatorSize(normalWidth, normalHeight, checkedWidth, checkedHeight);
        initIconSize();
        postInvalidate();
        return this;
    }

    public DrawableIndicator setIndicatorGap(int padding) {
        if (padding >= 0) {
            mIndicatorPadding = padding;
            postInvalidate();
        }
        return this;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    static class IndicatorSize {
        int normalWidth;
        int checkedWidth;
        int normalHeight;
        int checkedHeight;

        public IndicatorSize(int normalWidth, int normalHeight, int checkedWidth, int checkedHeight) {
            this.normalWidth = normalWidth;
            this.checkedWidth = checkedWidth;
            this.normalHeight = normalHeight;
            this.checkedHeight = checkedHeight;
        }
    }
}
