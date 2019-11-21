package com.example.zhpan.circleviewpager.viewholder;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.CustomBean;
import com.zhpan.bannerview.holder.ViewHolder;

public class CustomPageViewHolder implements ViewHolder<CustomBean> {
    private ImageView mImageView;
    private ImageView mImageStart;
    private OnSubViewClickListener mOnSubViewClickListener;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_view, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        mImageStart = view.findViewById(R.id.iv_logo);
        return view;
    }

    @Override
    public void onBind(Context context, CustomBean data, int position, int size) {
        mImageView.setImageResource(data.getImageRes());
        mImageStart.setOnClickListener(view -> {
            if (null != mOnSubViewClickListener)
                mOnSubViewClickListener.onViewClick(view, position);
        });
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mImageStart, "alpha", 0, 1);
        alphaAnimator.setDuration(1500);
        alphaAnimator.start();

    }

    public void setOnSubViewClickListener(OnSubViewClickListener subViewClickListener) {
        mOnSubViewClickListener = subViewClickListener;
    }

    public interface OnSubViewClickListener {
        void onViewClick(View view, int position);
    }
}
