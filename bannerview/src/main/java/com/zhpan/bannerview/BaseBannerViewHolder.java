package com.zhpan.bannerview;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * @author DBoy
 * @date 2020/12/11
 * Class 描述 : 不做任何操作
 */
public class BaseBannerViewHolder<T> extends BaseViewHolder<T> {


    public BaseBannerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(T data, int position, int pageSize) {

    }
}
