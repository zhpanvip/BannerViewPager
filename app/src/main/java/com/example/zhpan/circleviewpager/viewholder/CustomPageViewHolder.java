package com.example.zhpan.circleviewpager.viewholder;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.CustomBean;
import com.zhpan.bannerview.holder.ViewHolder;

public class CustomPageViewHolder extends RecyclerView.ViewHolder {
    private OnSubViewClickListener mOnSubViewClickListener;

    public CustomPageViewHolder(@NonNull View itemView) {
        super(itemView);
    }


//    @Override
//    public int getLayoutId() {
//        return R.layout.item_custom_view;
//    }
//
//    @Override
//    public void onBind(View itemView, CustomBean data, int position, int size) {
//        ImageView mImageView = itemView.findViewById(R.id.banner_image);
//        ImageView  mImageStart = itemView.findViewById(R.id.iv_logo);
//
//        mImageView.setImageResource(data.getImageRes());
//        mImageStart.setOnClickListener(view -> {
//            if (null != mOnSubViewClickListener)
//                mOnSubViewClickListener.onViewClick(view, position);
//        });
//        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mImageStart, "alpha", 0, 1);
//        alphaAnimator.setDuration(1500);
//        alphaAnimator.start();
//    }

    public void setOnSubViewClickListener(OnSubViewClickListener subViewClickListener) {
        mOnSubViewClickListener = subViewClickListener;
    }

    public interface OnSubViewClickListener {
        void onViewClick(View view, int position);
    }
}
