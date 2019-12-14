package com.zhpan.bannerview.holder;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

public interface ViewHolder<T> {

    /**
     * @return Layout Resource of BannerViewPager item
     */
    @LayoutRes int getLayoutId();

    /**
     * @param itemView  ViewPager item View
     * @param data     实体类对象
     * @param position 当前位置
     * @param size     页面个数
     */
    void onBind(View itemView, T data, int position, int size);
}
