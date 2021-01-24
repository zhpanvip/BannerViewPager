package com.example.zhpan.banner.adapter;

import android.graphics.Rect;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.example.zhpan.banner.R;
import com.example.zhpan.banner.databinding.ItemSlideModeBinding;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * @author DBoy
 * @date 2020/12/11
 * Class 描述 : 使用ViewBinding示例
 */
public class ViewBindingSampleAdapter extends BaseBannerAdapter<Integer> {

    private final int mRoundCorner;

    private Rect mMarginRect;

    public ViewBindingSampleAdapter(int roundCorner) {
        mRoundCorner = roundCorner;
    }

    public ViewBindingSampleAdapter(int roundCorner, Rect marginRect) {
        mRoundCorner = roundCorner;
        mMarginRect = marginRect;
    }

    @Override
    protected void bindData(BaseViewHolder<Integer> holder, Integer data, int position, int pageSize) {
        //示例使用ViewBinding
        ItemSlideModeBinding viewBinding = ItemSlideModeBinding.bind(holder.itemView);
        viewBinding.bannerImage.setRoundCorner(mRoundCorner);
        viewBinding.bannerImage.setImageResource(data);
        if (mMarginRect != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewBinding.bannerImage.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            layoutParams.setMargins(SizeUtils.dp2px(mMarginRect.left),SizeUtils.dp2px(mMarginRect.top),SizeUtils.dp2px(mMarginRect.right),SizeUtils.dp2px(mMarginRect.bottom));
            viewBinding.bannerImage.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_slide_mode;
    }
}

