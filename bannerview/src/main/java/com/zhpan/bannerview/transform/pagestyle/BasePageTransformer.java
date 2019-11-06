package com.zhpan.bannerview.transform.pagestyle;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by zhy on 16/5/7.
 */
public abstract class BasePageTransformer implements ViewPager.PageTransformer {
    protected ViewPager.PageTransformer mPageTransformer = NonPageTransformer.INSTANCE;
    public static final float DEFAULT_CENTER = 0.5f;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void transformPage(View view, float position) {
        if (mPageTransformer != null) {
            mPageTransformer.transformPage(view, position);
        }

        pageTransform(view, position);
    }

    protected abstract void pageTransform(View view, float position);

}
