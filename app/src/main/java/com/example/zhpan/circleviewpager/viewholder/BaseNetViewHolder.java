package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.zhpan.circleviewpager.net.BannerData;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * <pre>
 *   Created by zhangpan on 2020/4/9.
 *   Description:
 * </pre>
 */
public class BaseNetViewHolder extends BaseViewHolder<BannerData> {

    public BaseNetViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(BannerData data, int position, int pageSize) {

    }
}
