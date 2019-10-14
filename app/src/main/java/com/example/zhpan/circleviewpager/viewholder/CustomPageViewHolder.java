package com.example.zhpan.circleviewpager.viewholder;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.CustomBean;
import com.zhpan.bannerview.holder.ViewHolder;

public class CustomPageViewHolder implements ViewHolder<CustomBean> {
    private ImageView mImageView;
    private TextView mTextView;
    private TextView mTvStart;
    private OnSubViewClickListener mOnSubViewClickListener;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_view, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        mTextView = view.findViewById(R.id.tv_describe);
        mTvStart = view.findViewById(R.id.btn_start);
        return view;
    }

    @Override
    public void onBind(Context context, CustomBean data, int position, int size) {
        mImageView.setImageResource(data.getImageRes());
        mTextView.setText(data.getImageDescription());
        mTvStart.setOnClickListener(view -> {
            if (null != mOnSubViewClickListener)
                mOnSubViewClickListener.onViewClick(view, position);
        });
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mTvStart, "alpha", 0, 1);
        alphaAnimator.setDuration(1500);

        ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mTextView, "translationX", -120, 0);
        translationAnim.setDuration(1500);
        translationAnim.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(mTextView, "alpha", 0, 1);
        alphaAnimator1.setDuration(1500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, translationAnim, alphaAnimator1);
        animatorSet.start();

    }

    public void setOnSubViewClickListener(OnSubViewClickListener subViewClickListener) {
        mOnSubViewClickListener = subViewClickListener;
    }

    public interface OnSubViewClickListener {
        void onViewClick(View view, int position);
    }
}
