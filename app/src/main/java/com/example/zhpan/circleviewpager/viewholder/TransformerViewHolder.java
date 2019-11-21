package com.example.zhpan.circleviewpager.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhpan.circleviewpager.R;
import com.zhpan.bannerview.holder.ViewHolder;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */
public class TransformerViewHolder implements ViewHolder<Integer> {
    private ImageView mImageView;
    private TextView mTextView;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        mTextView = view.findViewById(R.id.btn_start);

        return view;
    }

    @Override
    public void onBind(final Context context, Integer data, final int position, final int size) {
        mImageView.setImageResource(data);
        mTextView.setVisibility(View.VISIBLE);
    }
}
