package com.example.zhpan.banner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhpan.banner.databinding.ItemSlideModeBinding;
import com.zhpan.bannerview.BaseSimpleAdapter;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * @author DBoy
 * @date 2020/12/11
 * Class 描述 : 使用ViewBinding示例
 */
public class ViewBindingSampleAdapter extends BaseSimpleAdapter<Integer> {
    //圆角
    private final int mRoundCorner;
    //示例使用ViewBinding
    private ItemSlideModeBinding mItemViewBinding;
    public ViewBindingSampleAdapter(int roundCorner) {
        mRoundCorner = roundCorner;
    }

    @Override
    protected void bindData(BaseViewHolder<Integer> holder, Integer data, int position, int pageSize) {
        mItemViewBinding.bannerImage.setRoundCorner(mRoundCorner);
        mItemViewBinding.bannerImage.setImageResource(data);
    }

    @Override
    protected View createItemView(ViewGroup parent, int viewType) {
        mItemViewBinding = ItemSlideModeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return mItemViewBinding.getRoot();
    }

}

