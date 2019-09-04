package com.zhpan.bannerview.indicator;

import android.content.Context;

import com.zhpan.bannerview.enums.IndicatorStyle;

public class IndicatorFactory {
    public static BaseIndicatorView createIndicatorView(Context context, IndicatorStyle indicatorStyle) {
        BaseIndicatorView indicatorView;
        switch (indicatorStyle) {
            case CIRCLE:
                indicatorView = new CircleIndicatorView(context);
                break;
            case DASH:
                indicatorView = new DashIndicatorView(context);
                break;
            default:
                indicatorView = new CircleIndicatorView(context);
                break;
        }
        return indicatorView;
    }
}
