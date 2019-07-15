package com.example.zhpan.circleviewpager.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhpan.circleviewpager.R;
import com.zhpan.viewpager.holder.ViewHolder;

public class LocalImageViewHolder implements ViewHolder<Integer> {
    private ImageView mImageView;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup,false);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, Integer data, int position, int size) {
        mImageView.setImageResource(data);
    }
}
