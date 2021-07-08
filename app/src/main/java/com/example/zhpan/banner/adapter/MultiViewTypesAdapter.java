package com.example.zhpan.banner.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zhpan.banner.R;
import com.example.zhpan.banner.net.BannerData;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * <pre>
 *   Created by zhpan on 2020/4/6.
 *   Description:Sample of multiple view types .
 * </pre>
 */
public class MultiViewTypesAdapter extends BaseBannerAdapter<BannerData> {

  @Override
  protected void bindData(BaseViewHolder<BannerData> holder, BannerData data, int position,
      int pageSize) {
    if (getViewType(position) == BannerData.TYPE_NEW) {
      holder.setImageResource(R.id.image_view, data.getDrawable());
    } else {
      ImageView imageView = holder.findViewById(R.id.banner_image);
      Glide.with(imageView)
          .load(data.getImagePath())
          .placeholder(R.drawable.placeholder)
          .into(imageView);
    }
  }

  @Override
  public int getLayoutId(int viewType) {
    if (viewType == BannerData.TYPE_NEW) {
      return R.layout.item_new_type;
    }
    return R.layout.item_net_image;
  }

  @Override
  public int getViewType(int position) {
    return mList.get(position).getType();
  }
}

