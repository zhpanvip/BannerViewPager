package com.example.zhpan.banner.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.zhpan.banner.R;
import com.example.zhpan.banner.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BaseBannerAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Description:
 * </pre>
 */
public class ImageResourceAdapter extends BaseBannerAdapter<Integer, ImageResourceViewHolder> {

    private int roundCorner;

    public ImageResourceAdapter(int roundCorner) {
        this.roundCorner = roundCorner;
    }


    @Override
    protected void onBind(ImageResourceViewHolder holder, Integer data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public ImageResourceViewHolder createViewHolder(@NotNull ViewGroup parent, View itemView, int viewType) {
        return new ImageResourceViewHolder(itemView, roundCorner);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_slide_mode;
    }
}
