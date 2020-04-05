package com.example.zhpan.circleviewpager.adapter;

import android.view.View;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.adapter.BaseBannerAdapter;

/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Description:
 * </pre>
 */
public class ImageResourceAdapter extends BaseBannerAdapter<Integer, ImageResourceViewHolder> {

    @Override
    protected void onBind(ImageResourceViewHolder holder, Integer data, int position, int pageSize) {
        holder.bind(data, position, pageSize);
    }

    @Override
    public ImageResourceViewHolder createViewHolder(View itemView) {
        return new ImageResourceViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_slide_mode;
    }
}
