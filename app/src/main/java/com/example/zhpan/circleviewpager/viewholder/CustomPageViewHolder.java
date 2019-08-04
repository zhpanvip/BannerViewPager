package com.example.zhpan.circleviewpager.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.CustomBean;
import com.zhpan.bannerview.holder.ViewHolder;

public class CustomPageViewHolder implements ViewHolder<CustomBean> {
    private ImageView mImageView;
    private TextView mTextView;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_view, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        mTextView = view.findViewById(R.id.tv_describe);
        return view;
    }

    @Override
    public void onBind(Context context, CustomBean data, int position, int size) {
        mImageView.setImageResource(data.getImageRes());
        mTextView.setText(data.getImageDescription());
    }
}
