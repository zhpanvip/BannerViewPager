package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.net.BannerData;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * <pre>
 *   Created by zhangpan on 2020/4/9.
 *   Description:
 * </pre>
 */
public class NewTypeViewHolder extends BaseViewHolder<BannerData> {

    public NewTypeViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(BannerData data, int position, int pageSize) {
        setImageResource(R.id.image_view, data.getDrawable());
    }
}
