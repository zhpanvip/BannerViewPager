package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Pair;

import androidx.annotation.Nullable;

import com.zhpan.bannerview.indicator.drawer.DrawController;
import com.zhpan.bannerview.manager.IndicatorOptions;

/**
 * <pre>
 *   Created by zhpan on 2019/11/23.
 *   Description:
 * </pre>
 */
public class IndicatorView extends BaseIndicatorView implements IIndicator {

    private DrawController mDrawController;

    public IndicatorView(Context context) {
        this(context,null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDrawController = new DrawController(getIndicatorOptions());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Pair<Integer, Integer> pair = mDrawController.measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(pair.first, pair.second);
    }

    @Override
    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        super.setIndicatorOptions(indicatorOptions);
        mDrawController.setIndicatorOptions(indicatorOptions);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawController.draw(canvas);
    }
}
