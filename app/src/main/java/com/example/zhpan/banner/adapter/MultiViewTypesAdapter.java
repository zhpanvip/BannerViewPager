package com.example.zhpan.banner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.zhpan.banner.R;
import com.example.zhpan.banner.net.BannerData;
import com.example.zhpan.banner.view.CornerImageView;
import com.example.zhpan.banner.viewholder.ServerImageViewHolder;
import com.example.zhpan.banner.viewholder.NewTypeViewHolder;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;
import com.zhpan.bannerview.utils.BannerUtils;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *   Created by zhpan on 2020/4/6.
 *   Description:Multiple view types adapter sample.
 * </pre>
 */
public class MultiViewTypesAdapter extends BaseBannerAdapter<BannerData, BaseViewHolder<BannerData>> {

    @Override
    protected void bindData(BaseViewHolder<BannerData> holder, BannerData data, int position, int pageSize) {
        if (holder instanceof ServerImageViewHolder) {
            CornerImageView imageView = holder.findView(R.id.banner_image);
            Glide.with(imageView).load(data.getImagePath()).placeholder(R.drawable.placeholder).into(imageView);
            BannerUtils.log("ServerImageViewHolder", "position:" + position);
        } else if (holder instanceof NewTypeViewHolder) {
            holder.setImageResource(R.id.image_view, data.getDrawable());
        }
    }

    @Override
    public BaseViewHolder<BannerData> createViewHolder(@NotNull ViewGroup parent, View itemView, int viewType) {
        if (viewType == BannerData.TYPE_NEW) {
            return new NewTypeViewHolder(itemView);
        }
        return new ServerImageViewHolder(itemView);
    }

    @Override
    public int getViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    protected View createItemView(ViewGroup parent, int viewType) {
        if (viewType == BannerData.TYPE_NEW) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_type, parent, false);
        }
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_net_image, parent, false);
    }
}

