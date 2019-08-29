package com.example.zhpan.circleviewpager.viewholder;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.DataBean;
import com.example.zhpan.circleviewpager.imageloader.ImageLoaderManager;
import com.example.zhpan.circleviewpager.imageloader.ImageLoaderOptions;
import com.zhpan.bannerview.holder.ViewHolder;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

public class TransformerViewHolder implements ViewHolder<Integer> {
    private ImageView mImageView;
    private SubViewClickListener mSubViewClickListener;
    private TextView mTextView;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        mTextView = view.findViewById(R.id.btn_start);
        mTextView.setOnClickListener(v -> {
            if (mSubViewClickListener != null) {
                mSubViewClickListener.onViewClick(v);
            }
        });
        return view;
    }

    @Override
    public void onBind(final Context context, Integer data, final int position, final int size) {
        mImageView.setImageResource(data);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTextView, "alpha", 0, 1);
        objectAnimator.setDuration(1500);
        objectAnimator.start();
        mTextView.setVisibility(View.VISIBLE);
    }

    public void setSubViewClickListener(SubViewClickListener subViewClickListener) {
        mSubViewClickListener = subViewClickListener;
    }

    public interface SubViewClickListener {
        void onViewClick(View view);
    }

}
