package com.example.zhpan.circleviewpager.adapter;

import android.view.View;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.base.BaseBannerAdapter;

/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Description:
 * </pre>
 */
public class ImageResourceAdapter extends BaseBannerAdapter<Integer, ImageResourceViewHolder> {
    private boolean needRoundCorner;

    public ImageResourceAdapter(boolean needRoundCorner) {
        this.needRoundCorner = needRoundCorner;
    }

    @Override
    protected void onBind(ImageResourceViewHolder holder, Integer data, int position, int pageSize) {
        holder.onBind(data, position, pageSize);
    }

    @Override
    public ImageResourceViewHolder createViewHolder(View itemView, int viewType) {
        ImageResourceViewHolder viewHolder = new ImageResourceViewHolder(itemView);
        if (needRoundCorner)
            viewHolder.setRoundCorner(itemView.getResources().getDimensionPixelOffset(R.dimen.dp_6));
        return viewHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_slide_mode;
    }
}
