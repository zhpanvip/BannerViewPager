package com.example.zhpan.banner.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.zhpan.banner.R;
import com.example.zhpan.banner.net.BannerData;
import com.example.zhpan.banner.view.CornerImageView;
import com.zhpan.bannerview.BaseViewHolder;
import com.zhpan.bannerview.utils.BannerUtils;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class ServerImageViewHolder extends BaseViewHolder<BannerData> {

    public ServerImageViewHolder(@NonNull View itemView) {
        super(itemView);
        CornerImageView imageView = findView(R.id.banner_image);
        imageView.setRoundCorner(BannerUtils.dp2px(0));
    }
}
