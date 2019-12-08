package com.example.zhpan.circleviewpager.viewholder;

import android.view.View;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.zhpan.bannerview.holder.ViewHolder;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

public class PhotoViewHolder implements ViewHolder<Integer> {

    @Override
    public int getLayoutId() {
        return R.layout.item_photo_view;
    }

    @Override
    public void onBind(View itemView, Integer data, int position, int size) {
        PhotoView imageView = itemView.findViewById(R.id.banner_image);
        imageView.setImageResource(data);
        imageView.setOnClickListener(v -> Toast.makeText(itemView.getContext(), position + "  页面数" + size, Toast.LENGTH_SHORT).show());
    }
}
