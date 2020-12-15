package com.zhpan.bannerview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * @author DBoy
 * @date 2020/12/11
 * Description : A simple and easy to use adapter for BVP,
 * only single view type supported with this adapter.
 * Multiple view types need extends {@link BaseBannerAdapter}.
 */
public abstract class BaseSimpleAdapter<T> extends BaseBannerAdapter<T, BaseViewHolder<T>> {
    @Override
    public BaseViewHolder<T> createViewHolder(@NonNull ViewGroup parent, View itemView, int viewType) {
        return new BaseViewHolder<>(itemView);
    }
}
