package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.view.CornerImageView;
import com.zhpan.bannerview.base.BaseViewHolder;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class ImageResourceViewHolder extends BaseViewHolder<Integer> {

    private int roundCorner;
    private CornerImageView imageView;

    public ImageResourceViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.banner_image);
    }

    @Override
    public void onBind(Integer data, int position, int pageSize) {
        imageView.setImageResource(data);
        imageView.setRoundCorner(roundCorner);
    }

    public void setRoundCorner(int roundCorner) {
        this.roundCorner = roundCorner;
    }
}
