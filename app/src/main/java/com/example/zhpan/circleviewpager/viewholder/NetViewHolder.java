package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.net.BannerData;
import com.example.zhpan.circleviewpager.view.CornerImageView;
import com.zhpan.bannerview.holder.BaseViewHolder;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class NetViewHolder extends BaseViewHolder<BannerData> {

    public NetViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(BannerData data, int position, int pageSize) {
        CornerImageView imageView = itemView.findViewById(R.id.banner_image);
        imageView.setRoundCorner(BannerUtils.dp2px(0));
        Glide.with(imageView).load(data.getImagePath()).placeholder(R.drawable.placeholder).into(imageView);
    }
}
