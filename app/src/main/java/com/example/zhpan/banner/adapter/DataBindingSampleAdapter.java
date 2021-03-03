package com.example.zhpan.banner.adapter;

import androidx.databinding.DataBindingUtil;

import com.example.zhpan.banner.R;
import com.example.zhpan.banner.databinding.ItemSlideModelDataBindingBinding;
import com.example.zhpan.banner.net.BannerData;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;


/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Description:使用DataBinding示例
 * </pre>
 */
public class DataBindingSampleAdapter extends BaseBannerAdapter<BannerData> {

    @Override
    protected void bindData(BaseViewHolder<BannerData> holder, BannerData data, int position, int pageSize) {
        ItemSlideModelDataBindingBinding dataBinding = DataBindingUtil.bind(holder.itemView);
        if (dataBinding != null) {
            dataBinding.setBannerData(data);
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_slide_model_data_binding;
    }
}
