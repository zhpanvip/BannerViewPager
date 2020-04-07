package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.view.CornerImageView;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class ImageResourceViewHolder extends BaseViewHolder<Integer> {

    public ImageResourceViewHolder(@NonNull View itemView,int roundCorner) {
        super(itemView);
        CornerImageView imageView = findView(R.id.banner_image);
        imageView.setRoundCorner(roundCorner);
    }

    @Override
    public void bindData(Integer data, int position, int pageSize) {
        setImageResource(R.id.banner_image, data);
    }
}
