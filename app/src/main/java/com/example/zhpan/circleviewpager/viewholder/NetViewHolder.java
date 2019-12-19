package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;

import com.bumptech.glide.Glide;
import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.net.BannerData;
import com.example.zhpan.circleviewpager.view.CornerImageView;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class NetViewHolder implements ViewHolder<BannerData> {

    @Override
    public int getLayoutId() {
        return R.layout.item_net;
    }

    @Override
    public void onBind(View itemView, BannerData data, int position, int size) {
        CornerImageView imageView = itemView.findViewById(R.id.banner_image);
        imageView.setRoundCorner(BannerUtils.dp2px(0));
        Glide.with(imageView).load(data.getImagePath()).placeholder(R.drawable.placeholder).into(imageView);
    }
}
