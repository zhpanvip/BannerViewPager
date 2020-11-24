package com.example.zhpan.banner.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.zhpan.banner.R;
import com.example.zhpan.banner.net.BannerData;
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
