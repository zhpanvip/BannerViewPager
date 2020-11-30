package com.example.zhpan.banner.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.zhpan.banner.R;
import com.example.zhpan.banner.net.BannerData;
import com.example.zhpan.banner.viewholder.ServerImageViewHolder;
import com.example.zhpan.banner.viewholder.NewTypeViewHolder;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *   Created by zhpan on 2020/4/6.
 *   Description:
 * </pre>
 */
public class HomeAdapter extends BaseBannerAdapter<BannerData, BaseViewHolder<BannerData>> {

    @Override
    protected void onBind(BaseViewHolder<BannerData> holder, BannerData data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
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
    public int getLayoutId(int viewType) {
        if (viewType == BannerData.TYPE_NEW) {
            return R.layout.item_new_type;
        }
        return R.layout.item_net_image;
    }
}

