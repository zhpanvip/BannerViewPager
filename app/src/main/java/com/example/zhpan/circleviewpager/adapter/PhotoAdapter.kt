package com.example.zhpan.circleviewpager.adapter

import android.view.View
import com.example.zhpan.circleviewpager.R
import com.example.zhpan.circleviewpager.viewholder.PhotoViewHolder
import com.zhpan.bannerview.base.BaseBannerAdapter


/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Description:
 * </pre>
 */
class PhotoAdapter : BaseBannerAdapter<Int, PhotoViewHolder>() {
    override fun onBind(holder: PhotoViewHolder?, data: Int?, position: Int, pageSize: Int) {
        holder?.onBind(data, position, pageSize)
    }

    override fun createViewHolder(itemView: View?, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(itemView!!)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_photo_view;
    }

}