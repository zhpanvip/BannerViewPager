package com.example.zhpan.banner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.zhpan.banner.R;
import com.example.zhpan.banner.databinding.ItemSlideModelDataBindingBinding;
import com.zhpan.bannerview.BaseSimpleAdapter;
import com.zhpan.bannerview.BaseViewHolder;


/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Description:使用DataBinding示例
 * </pre>
 */
public class DataBindingSampleAdapter extends BaseSimpleAdapter<Integer> {

    private ItemSlideModelDataBindingBinding mItemViewDataBinding;

    @Override
    protected void bindData(BaseViewHolder<Integer> holder, Integer data, int position, int pageSize) {
        mItemViewDataBinding.bannerImage.setImageResource(data);
    }

    @Override
    protected View createItemView(ViewGroup parent, int viewType) {
        mItemViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_slide_model_data_binding, parent, false);
        return mItemViewDataBinding.getRoot();
    }
}
