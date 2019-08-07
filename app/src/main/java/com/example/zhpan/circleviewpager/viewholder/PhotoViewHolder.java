package com.example.zhpan.circleviewpager.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.zhpan.bannerview.holder.ViewHolder;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

public class PhotoViewHolder implements ViewHolder<Integer> {
    private PhotoView mImageView;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo_view, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(final Context context, Integer data, final int position, final int size) {
        // 数据绑定
        mImageView.setImageResource(data);
        mImageView.setOnClickListener(v -> Toast.makeText(context, position + "  页面数" + size, Toast.LENGTH_SHORT).show());
    }
}
