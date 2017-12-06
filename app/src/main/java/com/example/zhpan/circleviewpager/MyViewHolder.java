package com.example.zhpan.circleviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viewpager.holder.ViewHolder;
import com.example.zhpan.circleviewpager.utils.ImageLoaderUtil;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

public class MyViewHolder implements ViewHolder<Object> {
    private ImageView mImageView;
    private TextView mTvDescribe;

    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, null);
        mImageView = (ImageView) view.findViewById(R.id.banner_image);
        mTvDescribe= (TextView) view.findViewById(R.id.tv_describe);
        return view;
    }

    @Override
    public void onBind(Context context, Object data) {
        // 数据绑定
        if (data instanceof Integer)
            mImageView.setImageResource((Integer) data);
        else if (data instanceof DataBean) {
            DataBean dataBean= (DataBean) data;
            ImageLoaderUtil.loadImg(mImageView, dataBean.getUrl(),R.drawable.placeholder);
            mTvDescribe.setText(dataBean.getDescribe());
        }
    }
}
