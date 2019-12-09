package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.view.CornerImageView;
import com.zhpan.bannerview.holder.ViewHolder;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class ImageResourceViewHolder implements ViewHolder<Integer> {

    private int roundCorner;

    public ImageResourceViewHolder(int roundCorner) {
        this.roundCorner = roundCorner;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_slide_mode;
    }

    @Override
    public void onBind(View itemView, Integer data, int position, int size) {
        CornerImageView imageView = itemView.findViewById(R.id.banner_image);
        imageView.setImageResource(data);
        imageView.setRoundCorner(roundCorner);
    }

}
