package com.example.zhpan.circleviewpager.adapter;

import android.view.View;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.net.BannerData;
import com.example.zhpan.circleviewpager.viewholder.NetViewHolder;
import com.example.zhpan.circleviewpager.viewholder.NewTypeViewHolder;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

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
    public BaseViewHolder<BannerData> createViewHolder(View itemView, int viewType) {
        if (viewType == BannerData.TYPE_NEW) {
            return new NewTypeViewHolder(itemView);
        }
        return new NetViewHolder(itemView);
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
        return R.layout.item_net;
    }
}

