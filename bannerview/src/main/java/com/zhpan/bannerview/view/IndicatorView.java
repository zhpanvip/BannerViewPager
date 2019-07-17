package com.zhpan.bannerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhpan on 2017/12/6.
 */

public class IndicatorView extends View {
    private int normalColor;
    private int checkedColor;
    private Paint mPaint;
    private boolean isChecked;


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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float radius = Math.min(width, height) / 2f;
        canvas.drawCircle(width / 2f, height / 2f, radius, mPaint);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        if (checked) {
            mPaint.setColor(checkedColor);
        } else {
            mPaint.setColor(normalColor);
        }
        isChecked = checked;
        invalidate();
    }

    public int getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public int getCheckedColor() {
        return checkedColor;
    }

    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }


}
