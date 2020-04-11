package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.net.BannerData;

import cn.jzvd.JzvdStd;

/**
 * <pre>
 *   Created by zhangpan on 2020/4/9.
 *   Description:
 * </pre>
 */
public class VideoViewHolder extends BaseNetViewHolder {
    private JzvdStd jzvdStd;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(BannerData data, int position, int pageSize) {
        jzvdStd = findView(R.id.banner_video);
        jzvdStd.setUp(data.getUrl(), data.getTitle());
    }

    public JzvdStd getJzvdStd() {
        return jzvdStd;
    }
}
