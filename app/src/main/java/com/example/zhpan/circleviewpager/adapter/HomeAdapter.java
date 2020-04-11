package com.example.zhpan.circleviewpager.adapter;

import android.view.View;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.net.BannerData;
import com.example.zhpan.circleviewpager.viewholder.BaseNetViewHolder;
import com.example.zhpan.circleviewpager.viewholder.NetViewHolder;
import com.example.zhpan.circleviewpager.viewholder.VideoViewHolder;
import com.zhpan.bannerview.BaseBannerAdapter;

/**
 * <pre>
 *   Created by zhpan on 2020/4/6.
 *   Description:
 * </pre>
 */
public class HomeAdapter extends BaseBannerAdapter<BannerData, BaseNetViewHolder> {
    @Override
    protected void onBind(BaseNetViewHolder holder, BannerData data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public BaseNetViewHolder createViewHolder(View itemView, int viewType) {
        if (viewType == BannerData.TYPE_VIDEO) {
            return new VideoViewHolder(itemView);
        }
        return new NetViewHolder(itemView);
    }

    @Override
    public int getViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == BannerData.TYPE_VIDEO) {
            return R.layout.item_video;
        }
        return R.layout.item_net;
    }
}

