package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.view.CornerImageView;
import com.zhpan.bannerview.holder.BaseViewHolder;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class ImageResourceViewHolder extends BaseViewHolder<Integer> {

    private int roundCorner;

    public ImageResourceViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Integer data, int position, int pageSize) {
        CornerImageView imageView = itemView.findViewById(R.id.banner_image);
        imageView.setImageResource(data);
        imageView.setRoundCorner(roundCorner);
    }

    public void setRoundCorner(int roundCorner) {
        this.roundCorner = roundCorner;
    }
}
