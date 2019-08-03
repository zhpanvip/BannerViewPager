package com.example.zhpan.circleviewpager.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.bean.DataBean;
import com.example.zhpan.circleviewpager.R;
import com.zhpan.bannerview.holder.ViewHolder;
import com.example.zhpan.circleviewpager.utils.ImageLoaderUtil;

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

public class DataViewHolder implements ViewHolder<DataBean> {
    private ImageView mImageView;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(final Context context, DataBean data, final int position, final int size) {
        ImageLoaderUtil.loadImg(mImageView, data.getUrl(), R.drawable.placeholder);
    }
}
