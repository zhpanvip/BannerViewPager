package com.example.zhpan.circleviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.viewpager.holder.ViewHolder;
import com.example.viewpager.utils.ImageLoaderUtil;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

public class MyViewHolder implements ViewHolder<Object> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        mImageView = (ImageView) view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, Object data) {
        // 数据绑定
        if (data instanceof Integer)
            mImageView.setImageResource((Integer) data);
        else if (data instanceof String) {
            ImageLoaderUtil.loadImg(mImageView, (String) data,R.drawable.placeholder);
        }
    }
}
