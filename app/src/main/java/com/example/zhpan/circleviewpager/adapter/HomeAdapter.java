package com.example.zhpan.circleviewpager.adapter;

import android.view.View;
import android.widget.MediaController;

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
    private MediaController mMediaController;
    private BaseViewHolder<BannerData> mViewHolder;

    @Override
    protected void onBind(BaseViewHolder<BannerData> holder, BannerData data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public BaseViewHolder<BannerData> createViewHolder(View itemView, int viewType) {
        if (viewType == BannerData.TYPE_NEW) {
            return new NewTypeViewHolder(itemView);
        }
        mViewHolder = new NetViewHolder(itemView);
        return mViewHolder;
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

    public void setMediaController(MediaController mediaController) {
        mMediaController = mediaController;
    }

    public BaseViewHolder<BannerData> getViewHolder() {
        return mViewHolder;
    }

    public void setViewHolder(BaseViewHolder<BannerData> viewHolder) {
        mViewHolder = viewHolder;
    }
}

