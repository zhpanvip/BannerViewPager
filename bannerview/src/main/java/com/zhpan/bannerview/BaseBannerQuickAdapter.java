package com.zhpan.bannerview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * @author DBoy
 * @date 2020/12/11
 * Class 描述 : 快速创建adapter使用
 */
public abstract class BaseBannerQuickAdapter<T> extends BaseBannerAdapter<T, BaseBannerViewHolder<T>> {
    /**
     * 默认没有LayoutId 使用
     */
    protected final int NO_LAYOUT = -1;

    @Override
    protected void onBind(BaseBannerViewHolder<T> holder, T data, int position, int pageSize) {
        convert(holder, data, position, pageSize);
    }

    @Override
    public BaseBannerViewHolder<T> createViewHolder(@NonNull ViewGroup parent, View itemView, int viewType) {
        return new BaseBannerViewHolder<T>(itemView);
    }

    /**
     * 进行数据适配
     * @param holder 基础ViewHolder
     * @param data 数据
     * @param position 位置
     * @param pageSize 页面个数
     */
    protected abstract void convert(BaseBannerViewHolder<T> holder, T data, int position, int pageSize);

    /**
     * 如果使用的是布局id 重写此方法;
     * 如果使用ViewBinding 重写  createViewBinding(ViewGroup, int);此方法不需要重写
     */
    @Override
    public int getLayoutId(int viewType) {
        return NO_LAYOUT;
    }

}
