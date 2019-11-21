package com.zhpan.bannerview.indicator;

import android.content.Context;

import com.zhpan.bannerview.annotation.AIndicatorStyle;
import com.zhpan.bannerview.constants.IndicatorStyle;


public class IndicatorFactory {
    public static BaseIndicatorView createIndicatorView(Context context, @AIndicatorStyle int indicatorStyle) {
        BaseIndicatorView indicatorView;
        if (indicatorStyle == IndicatorStyle.DASH) {
            indicatorView = new DashIndicatorView(context);
        } else {
            indicatorView = new CircleIndicatorView(context);
        }
        return indicatorView;
    }
}
