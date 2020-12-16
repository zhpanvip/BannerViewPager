package com.example.zhpan.banner.adapter;

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

    @Override
    protected void bindData(BaseViewHolder<Integer> holder, Integer data, int position, int pageSize) {
        ItemSlideModelDataBindingBinding mItemViewDataBinding = DataBindingUtil.bind(holder.itemView);
        mItemViewDataBinding.bannerImage.setImageResource(data);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_slide_model_data_binding;
    }
}
